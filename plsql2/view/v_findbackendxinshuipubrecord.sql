create or replace view v_findbackendxinshuipubrecord as
select

a.XINSHUI_NO,
a.TX_USERNAME,
a.HOST_NAME ,--����
a.GUEST_NAME,--�Ͷ�
a.RACE_NAME,
b.START_TIME,
a.ENSURE_MONEY,
  a.PRICE,
  decode(a.TYPE,'1','����','2','��С��') TYPE,
  (select count(1)  from T_XINSHUI_ORDER log where log.SOLD_USER_ID=a.TX_USER_ID
  and  log.product_id=a.c2c_id ) sold_cnt,--��������
  a.PUB_TIME ,--����ʱ��
  DECODE(a.STATUS, '1','������','2','Ӯ','3','��','4','��','5','�ѹر�') STATUS--״̬
  ,a.c2c_id,
  a.race_id,
  a.against_id,
  a.tx_user_id pub_userid
  from T_C2C_PRODUCT  a ,T_AGAINST b where  a.AGAINST_ID=b.AGAINST_ID

