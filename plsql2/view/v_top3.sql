create or replace view v_top3 as
select B.WIN_RATIO,B.username,B.changci,cnt from(
     select A.WIN_RATIO,A.username,A.changci,cnt from (
     select
       (select count(1)  from   t_c2c_product X where  X.Status='2' and X.Tx_User_Id=u.userid
        and X.pub_time >=(select add_months(sysdate,-1) from dual)
       ) WIN_RATIO,
        u.username username,
        (select count(1)  from T_XINSHUI_ORDER  log where log.SOLD_USER_ID= u.userid ) cnt,  --cnt 心水购买数量
       (select count(1) from t_c2c_product  c2c where  c2c.tx_user_id=u.userid) changci
     from  T_USER  u ) A order by A.cnt desc )B  where rownum<=10

