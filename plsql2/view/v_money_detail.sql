create or replace view v_money_detail as
select
       v.tx_money tx_money,
       v.all_money all_money,
       v.tx_date tx_date,
       v.tx_type tx_type,
       d.zh_desc tx_name,
       v.order_id order_id,
       v.order_no order_no,
       v.category_type category_type,
       v.tx_user_id user_id
from t_Va_Transaction_Log v
join t_dictionary d on v.tx_type = d.code and d.type = 'TX_TYPE'
where v.tx_money <> 0
union all
select
      c.money,
      c.all_money,
      c.charge_time,
      '1',
      '账户充值',
      c.charge_id,
      c.order_no,
      c.category_type,
      c.user_id
from t_charge_log c
where c.status = '2'
union all
select
       w.tx_money,
       w.all_money,
       w.draw_time,
       '2',
       '账户取款',
       w.draw_id,
       w.order_no,
       w.category_type,
       w.tx_user_id
from t_withdraw_log w
where w.status = '3'

