create or replace view v_admin_user_list as
select u.userid as userId,u.status as userStatus,va.status as userMoneyStatus,u.username as userName,u.name as name,u.user_grade as userGrade,
u.title as userHonor,u.reg_time as regTime,u.province as provinceCode,u.city as cityCode,u.reg_ip,u.qq,u.email,u.mp,u.idcard,
va.all_money as accountBalance,c.total_win_prize as totalWinPrize,c2c.releaseNum,
c.total_bet as totalBet,u.mp as userMobile,u.XINSHUI_MILITARY as heartWaterScore,c2c_sale.totalXinshuiSale as totalXinshuiSale,c2c_buy.totalXinshuiBuy as totalXinshuiBuy,
0 as togetherBuysScore,0 as releaseTogetherBuys,0 as togetherBuysSales,0 as arenaScore
from t_user u
left join t_virtual_account va
on u.userid=va.tx_user_id
left join
(  select p.tx_user_id as user_id, count(p.c2c_id) as releaseNum from t_c2c_product p group by p.tx_user_id ) c2c
on u.userid=c2c.user_id
left join
(  select xo.sold_user_id as user_id,sum(xo.tx_money)+sum(xo.tx_caijin) as totalXinshuiSale from t_xinshui_order xo group by xo.sold_user_id,xo.pay_status having xo.pay_status='2' ) c2c_sale --心水销售额
on u.userid=c2c_sale.user_id
left join
(  select xo.buy_user_id as user_id,sum(xo.tx_money)+sum(xo.tx_caijin) as totalXinshuiBuy from t_xinshui_order xo group by xo.buy_user_id,xo.pay_status having xo.pay_status='2') c2c_buy --心水购买额
on u.userid=c2c_buy.user_id
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

