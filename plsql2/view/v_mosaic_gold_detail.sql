create or replace view v_mosaic_gold_detail as
select
    v.tx_mosaic_goldmoney tx_mosaic_gold,
    v.mosaic_goldmoney mosaic_gold,
    v.tx_date tx_date,
    v.tx_type tx_type,
    v.tx_type_name tx_name,
    v.order_id order_id,
    v.order_no order_no,
    v.tx_user_id user_id,
    v.category_type category_type
from t_Va_Transaction_Log v
where v.tx_mosaic_goldmoney <> 0
union all
select
    c.money,
    u.all_caijin,
    c.audit_time,
    '9',
    '²Ê½ðÔùËÍ',
    c.id,
    c.order_no,
    u.user_id,
    c.category_type
from t_caijin_donate c
join t_user_donate u on c.id = u.donate_id

