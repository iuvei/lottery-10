create or replace view v_admin_draw_list as
select a.draw_id as drawId,a.order_no as orderNO, b.userid as userId,b.username as userName,b.user_grade as userGrade,
a.tx_money as drawMoney,a.fee as drawFee,a.bank_code as bankCode,a.bank as bankName,a.status as drawStatus,a.draw_time as drawTime,a.withdraw_ip as drawIp
from t_withdraw_log a
left join t_user b
on a.tx_user_id=b.userid

