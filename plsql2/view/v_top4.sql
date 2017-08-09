create or replace view v_top4 as
select (select count(1) from t_c2c_product  c2c where  c2c.tx_user_id=u.userid
         and c2c.status='2'
        ) WIN_RATIO,
        (select count(1) from t_c2c_product  c2c where  c2c.tx_user_id=u.userid) CHANGCI,
       u.username from  t_user u

