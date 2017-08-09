create or replace view v_top1 as
select B.WIN_RATIO,B.username,B.changci from(
     select A.WIN_RATIO,A.username,A.changci from (
     select
       (select count(1)  from   t_c2c_product X where  X.Status='2' and X.Tx_User_Id=u.userid
         and to_char(X.pub_time,'yyyymmdd')>=(select  to_char(add_months(sysdate,-1),'yyyymmdd') from dual)
       ) WIN_RATIO,
        u.username username,
       (select count(1) from t_c2c_product  c2c where  c2c.tx_user_id=u.userid) changci
     from  T_USER  u ) A order by A.WIN_RATIO desc )B  where rownum<=10

