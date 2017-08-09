create or replace view v_admin_user_detail as
select u.userid as userId,u.username as userName,u.user_grade as userGrade,u.title as userHonor,u.name as userRealName,
u.idcard,u.status as userStatus,va.status as moneyStatus,u.reg_ip as userRegIP,u.login_cnt as userLongInTimes,u.qq as qq ,
u.mp as userMobile,u.tel as userPhone,u.birthday as birthday,u.province as userProvince,
u.city as userCity,u.sex as sex,va.province as bankProvince,va.city as bankCity,va.bank_code,va.bank_name,va.card_num
from t_user u
left join t_virtual_account va
on u.userid=va.tx_user_id

