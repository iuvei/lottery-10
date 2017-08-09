create or replace view v_expert_list as
select a.EXPERT_ID,a.EXPERT_NAME,to_char(a.CONTRACT_TIME,'yyyy-mm-dd') CONTRACT_TIME,
(select count(xo.XINSHUI_ORDER_ID) from  T_XINSHUI_ORDER xo where xo.TYPE='2' and xo.SOLD_USER_ID=a.USER_ID ) nc_cnt,
a.MONTH_PACK,a.WEEK_PACK,a.STATUS,
(select   count(1) from  T_C2C_PRODUCT w1  where  w1.IS_B2C='1' and  w1.status='2' and w1.TYPE='3' and w1.TX_USER_ID=a.USER_ID)EuAll ,
 (select   count(1) from  T_C2C_PRODUCT w2  where  w2.IS_B2C='1' and  w2.status='2' and w2.TYPE='3' and w2.TX_USER_ID=a.USER_ID  and  w2.PUB_TIME>ADD_MONTHS(SYSDATE,-1)) EuMonth,
  (select   count(1) from  T_C2C_PRODUCT w3  where  w3.IS_B2C='1' and  w3.status='2' and w3.TYPE='1' and w3.TX_USER_ID=a.USER_ID) AsiaAll,
  (select   count(1) from  T_C2C_PRODUCT w4  where  w4.IS_B2C='1' and  w4.status='2' and w4.TYPE='1' and w4.TX_USER_ID=a.USER_ID  and  w4.PUB_TIME>ADD_MONTHS(SYSDATE,-1)) AsiaMonth,
  (select   count(1) from  T_C2C_PRODUCT w5  where  w5.IS_B2C='1' and  w5.status='2' and w5.TYPE='2' and w5.TX_USER_ID=a.USER_ID) bigsamllAll ,
  (select   count(1) from  T_C2C_PRODUCT w6  where  w6.IS_B2C='1' and  w6.status='2' and w6.TYPE='2' and w6.TX_USER_ID=a.USER_ID  and  w6.PUB_TIME>ADD_MONTHS(SYSDATE,-1)) bigsamllMonth,
  (select   count(1) from  T_XINSHUI_ORDER w7  where  w7.TYPE='2' and  w7.ORDER_TYPE='3' and  w7.SOLD_USER_ID=a.USER_ID) packMonthOrderCntAll,
  (select   count(1) from  T_XINSHUI_ORDER w8  where  w8.TYPE='2' and  w8.ORDER_TYPE='3' and  w8.SOLD_USER_ID=a.USER_ID  and  w8.BUY_TIME>ADD_MONTHS(SYSDATE,-1)) packMonthOrderCntMonth,
  (select   count(1) from  T_XINSHUI_ORDER w9  where  w9.TYPE='2' and  w9.ORDER_TYPE='4' and  w9.SOLD_USER_ID=a.USER_ID) packWeekOrderCntAll,
   (select   count(1) from  T_XINSHUI_ORDER w0  where  w0.TYPE='2' and  w0.ORDER_TYPE='4' and  w0.SOLD_USER_ID=a.USER_ID  and  w0.BUY_TIME>ADD_MONTHS(SYSDATE,-1)) packWeekOrderCntMonth,
   (select sum(q.PRICE)  from  T_XINSHUI_ORDER q where  q.SOLD_USER_ID=a.USER_ID and q.TYPE='2' and  q.PAY_STATUS='3') soldMoney,
   (select max(p.PUB_TIME) from T_C2C_PRODUCT p where p.TX_USER_ID=a.USER_ID) lastPub  from T_EXPERT  a

