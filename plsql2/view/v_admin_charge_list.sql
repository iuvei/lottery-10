create or replace view v_admin_charge_list as
select a.charge_id as chargeId,a.order_no as orderNO, b.userid as userId,b.username as userName,b.user_grade as userGrade,
a.money as chargeMoney,a.pay_way as chargeWay,a.from_bank as chargeBank,a.from_bank_code as chargeBankCode,a.status as chargeStatus,
a.charge_time as chargeTime,a.ip as chargeIp
from t_charge_log a
left join t_user b
on a.user_id=b.userid

