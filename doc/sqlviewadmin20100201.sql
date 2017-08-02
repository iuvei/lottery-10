create or replace view v_admin_account_detail as
select a.tx_user_id as userId,a.all_money as currentMoney,a.frozen_money as frozenMoney,(a.mosaic_gold-a.frozen_mosaic_gold) as currentGold,a.point
 as currentPoint, bb.totalDrawMoney ,cc.totalPayMoney,dd.totalBetMoney,ee.totalWinMoney,ff.totalPoint,a.mosaic_gold as totalGold,gg.totalUseGold,(ff.totalPoint-a.point) as totalUsePoint
from t_virtual_account a
--当前账户余额，当前冻结资金,当前可用彩金，当前可有积分
left join
( select sum(b.tx_money) as totalDrawMoney ,b.tx_user_id  from t_withdraw_log b group by b.tx_user_id ) bb --求用户总取款金额
on a.tx_user_id=bb.tx_user_id
left join
( select sum(c.money) as totalPayMoney,c.user_id  from t_charge_log c group by c.user_id  ) cc   --求用户总充值金额
on a.tx_user_id=cc.user_id
left join
( select sum(d.tx_money) as totalBetMoney,d.tx_user_id from t_va_transaction_log d group by d.tx_user_id,d.tx_type having  d.tx_type=3 ) dd --求总投注金额
on a.tx_user_id=dd.tx_user_id
left join
( select sum(e.tx_money) as totalWinMoney,e.tx_user_id  from t_va_transaction_log e group by e.tx_user_id,e.tx_type having  e.tx_type=3 ) ee --求总中奖金额
on a.tx_user_id=ee.tx_user_id
left join
( select sum(f.tx_value) as totalPoint,f.tx_user_id  from t_point_transaction_log f group by f.tx_user_id,f.tx_type having f.tx_type<>6) ff --求总积分
on a.tx_user_id=ff.tx_user_id
left join
( select sum(g.tx_money) as totalUseGold,g.tx_user_id   from t_va_transaction_log g group by g.tx_user_id,g.tx_type having g.tx_type=5) gg --求总彩金
on a.tx_user_id=gg.tx_user_id



create or replace view v_admin_charge_list as
select a.charge_id as chargeId,a.order_no as orderNO, b.userid as userId,b.username as userName,b.user_grade as userGrade,
a.money as chargeMoney,a.pay_way as chargeWay,a.from_bank as chargeBank,a.from_bank_code as chargeBankCode,a.status as chargeStatus,
a.charge_time as chargeTime,a.ip as chargeIp
from t_charge_log a
left join t_user b
on a.user_id=b.userid

create or replace view v_admin_draw_list as
select a.draw_id as drawId,a.order_no as orderNO, b.userid as userId,b.username as userName,b.user_grade as userGrade,
a.tx_money as drawMoney,a.fee as drawFee,a.bank_code as bankCode,a.bank as bankName,a.status as drawStatus,a.draw_time as drawTime,a.withdraw_ip as drawIp
from t_withdraw_log a
left join t_user b
on a.tx_user_id=b.userid

create or replace view v_admin_user_detail as
select u.userid as userId,u.username as userName,u.user_grade as userGrade,u.title as userHonor,u.name as userRealName,
u.idcard,u.status as userStatus,va.status as moneyStatus,u.reg_ip as userRegIP,u.login_cnt as userLongInTimes,u.qq as qq ,
u.mp as userMobile,u.tel as userPhone,u.birthday as birthday,u.province as userProvince,
u.city as userCity,u.sex as sex,va.province as bankProvince,va.city as bankCity,va.bank_code,va.bank_name,va.card_num
from t_user u
left join t_virtual_account va
on u.userid=va.tx_user_id

create or replace view v_admin_user_list as
select u.userid as userId,u.status as userStatus,va.status as userMoneyStatus,u.username as userName,u.name as name,u.user_grade as userGrade,
u.title as userHonor,u.reg_time as regTime,u.province as provinceCode,u.city as cityCode,u.reg_ip,u.qq,u.email,u.mp,u.idcard,
va.all_money as accountBalance,c.total_win_prize as totalWinPrize,
c.total_bet as totalBet,u.mp as userMobile,u.military_score as heartWaterScore,0 as totalXinshuiSale,0 as totalXinshuiBuy
from t_user u
left join t_virtual_account va
on u.userid=va.tx_user_id
left join
(
select a.*,b.total_win_prize from --获取用户 总投注额和总中奖奖金
( select va_log.tx_user_id as user_id,sum(va_log.tx_money) as total_bet from t_va_transaction_Log va_log
        group by va_log.tx_user_id,va_log.tx_type having va_log.tx_type=1 ) a --总投注额
left join
( select va_log.tx_user_id as user_id,sum(va_log.tx_money) as total_win_prize from t_va_transaction_Log va_log
        group by va_log.tx_user_id,va_log.tx_type having va_log.tx_type=3 ) b -- 总中奖奖金
on a.user_id=b.user_id
) c
on u.userid=c.user_id

create or replace view v_admin_xinshui_total_sale as
select cc.user_id as userId, sum(cc.price) as totalXinshuiSale from (
select a1.user_id,a3.price,a3.product_id,a3.type from t_B2c_Expert a1 --b2c用户销售总额
left join t_b2c_product a2
on a1.expert_id=a2.expert_id
left join t_xinshui_log a3
on a2.b2c_id=a3.product_id
where a3.type=1
union all
select b1.tx_user_id,b2.price,b2.product_id,b2.type from t_c2c_product b1--c2c用户销售总额
left join t_xinshui_log b2
on b1.c2c_id=b2.product_id
where b2.type=2
) cc
group by cc.user_id



