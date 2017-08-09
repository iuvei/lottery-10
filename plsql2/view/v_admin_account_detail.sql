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

