create or replace view v_backend_pub_usermanage as
select u.username,(select count(1) from T_C2C_PRODUCT t where t.TX_USER_ID=u.USERID  and  t.status is not null)  yccc,--Ԥ�ⳡ��(�������������)
 u.USER_GRADE,(select count(1) from T_C2C_PRODUCT t where    t.status='2'  and t.TX_USER_ID=u.USERID) zql,--//׼ȷ��
 (select count(XINSHUI_ORDER_ID) from T_XINSHUI_ORDER log,T_C2C_PRODUCT cp where
  log.product_id=cp.c2c_id   and  cp.status is not null and  log.SOLD_USER_ID=u.USERID)  zdgs,-- �ܶ�����,ҳ����ֻҪ���Գ��μ���
 (select sum(va.TX_MOSAIC_GOLDMONEY+va.TX_MONEY) from T_VA_TRANSACTION_LOG va  where va.CATEGORY_TYPE='11' and va.TARGET_USERID=u.USERID ) sold_money,  --���۶�
 (select sum(va.TX_MOSAIC_GOLDMONEY+va.TX_MONEY)  from T_VA_TRANSACTION_LOG va  where va.CATEGORY_TYPE='16' and va.TX_USER_ID=u.USERID)  peichang_money,--�⸶��
 (select  max(c2c.PUB_TIME) from  T_C2C_PRODUCT c2c  where  c2c.TX_USER_ID=u.USERID) last_time
 ,u.userid
 from  T_USER  u

