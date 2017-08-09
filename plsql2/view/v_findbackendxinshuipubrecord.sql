create or replace view v_findbackendxinshuipubrecord as
select

a.XINSHUI_NO,
a.TX_USERNAME,
a.HOST_NAME ,--主队
a.GUEST_NAME,--客队
a.RACE_NAME,
b.START_TIME,
a.ENSURE_MONEY,
  a.PRICE,
  decode(a.TYPE,'1','亚盘','2','大小盘') TYPE,
  (select count(1)  from T_XINSHUI_ORDER log where log.SOLD_USER_ID=a.TX_USER_ID
  and  log.product_id=a.c2c_id ) sold_cnt,--销售总量
  a.PUB_TIME ,--发布时间
  DECODE(a.STATUS, '1','发布中','2','赢','3','负','4','走','5','已关闭') STATUS--状态
  ,a.c2c_id,
  a.race_id,
  a.against_id,
  a.tx_user_id pub_userid
  from T_C2C_PRODUCT  a ,T_AGAINST b where  a.AGAINST_ID=b.AGAINST_ID

