create or replace view v_backend_pub_usermanage as
select u.username,(select count(1) from T_C2C_PRODUCT t where t.TX_USER_ID=u.USERID)  yccc,--预测场次
 u.USER_GRADE,(select count(1) from T_C2C_PRODUCT t where    t.ANSWER='1'  and t.TX_USER_ID=u.USERID) zql,--//准确率
 (select count(XINSHUI_ID) from T_XINSHUI_LOG log where   log.SOLD_USER_ID=u.USERID)  zdgs,-- 总订购数,页面上只要除以场次即可
 (select sum(va.TX_MOSAIC_GOLDMONEY+va.TX_MONEY) from T_VA_TRANSACTION_LOG va  where va.CATEGORY_TYPE='11' and va.TARGET_USERID=u.USERID ) sold_money,  --销售额
 (select sum(va.TX_MOSAIC_GOLDMONEY+va.TX_MONEY)  from T_VA_TRANSACTION_LOG va  where va.CATEGORY_TYPE='16' and va.TX_USER_ID=u.USERID)  peichang_money,--赔付额
 (select  max(c2c.PUB_TIME) from  T_C2C_PRODUCT c2c  where  c2c.TX_USER_ID=u.USERID) last_time
 from  T_USER  u;
