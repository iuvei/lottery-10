create or replace package body XINSHUI is

   --客服取消赛事
   procedure  cancelAgainst(againstId in NUMBER,p_cs_user_id in NUMBER,p_cs_name in VARCHAR2,p_ip in VARCHAR2)
   is
   dbException exception;--数据库操作异常
   begin
      update  t_against  m set m.status='3'  where m.against_id=againstId;
    
    for v in(select t.c2c_id from t_c2c_product t where  t.against_id=againstId) loop
       closeXinshui(v.c2c_id,p_cs_user_id,p_cs_name,p_ip);
    end loop;
    exception
    when others then
       rollback;
       raise dbException;
   end cancelAgainst;
   


     --客服取消订单
     procedure  cancelXinshuiOrder(p_orderId in NUMBER,p_cs_user_id in NUMBER,p_cs_name in VARCHAR2,p_ip in VARCHAR2)
     is
        orderPayStatus varchar2(60);--订单支付状态
        txMoney        number(10,2);--应支付的金额
        txCaijin       number(10,2);--应支付的彩金
        buyUserId      number(20);--心水购买人用户ID
        dbException exception;--数据库访问异常
     begin
       select t.pay_status,t.tx_money,t.tx_caijin,t.buy_user_id  into  orderPayStatus,txMoney,txCaijin,buyUserId from T_XINSHUI_ORDER  t where t.XINSHUI_ORDER_ID=p_orderId;
       --1.更改订单状态
       update T_XINSHUI_ORDER t set t.pay_status='5'  where t.XINSHUI_ORDER_ID=p_orderId;
       IF orderPayStatus='2'  THEN
          --2.如果心水订单状态时已支付  则还要进行下列操作
          --2.1.对应已支付的心水订单，解冻账户(T_VIRTUAL_ACCOUNT) 
          --2.2.产生解冻日志
         insert into T_VA_FROZEN_LOG(FROZEN_ID,Tx_User_Id,FROZEN_MONEY,Frozen_Date,Frozen_Mosaic_Goldmoney,Order_Id,Category_Type,Flg,Tx_Type)
         values(Seq_Va_Frozen_Log_Id.Nextval,buyUserId,txMoney,sysdate,txCaijin,p_orderId,'11','2','7');
       
         update  T_VIRTUAL_ACCOUNT  t  set t.frozen_money=t.frozen_money-txMoney,t.Frozen_Mosaic_Gold=t.Frozen_Mosaic_Gold-txCaijin
         where  t.tx_user_id=buyUserId;
       
         --2.3.产生客服操作日志
         insert into t_cs_handle_log(id,cs_user_id,handle_time,cs_name,memo,ip)
         values(SEQ_CS_HANDLE_LOG_ID.NEXTVAL,p_cs_user_id,sysdate,p_cs_name,'客服取消心水订单',p_ip);
      END IF;  
      exception
      when others then
         rollback;
         raise dbException;
     
     end;
  
     --客服关闭心水
     procedure  closeXinshui(c2cId in NUMBER,p_cs_user_id in NUMBER,p_cs_name in VARCHAR2,p_ip in VARCHAR2)
     is
     dbException exception;
     ensureMoney number(10,2);
     frozenMoney number(10,2);--冻结金额
     price NUMBER(10,2);--心水价格
     userId NUMBER(20);--民间高手用户ID
     orderUserId NUMBER(20);--订购人用户ID
     txMoney NUMBER(10,2);--应支付的金额
     txCaijin NUMBER(10,2);--应支付的彩金
     xinshuiOrderId  NUMBER(20);--心水订单ID
     ranInt VARCHAR2(10);
     seqOrderNO VARCHAR2(100);
     xinshuiOrderNO VARCHAR2(100);
     begin
       --1.只有发布中的心水才能关闭 修改心水状态(T_C2C_PRODUCT.STATUS='5')
       update  T_C2C_PRODUCT t  set t.STATUS='5' where t.C2C_ID=c2cId  and t.status='1';
       --心水状态: '1':'发布中','2':'赢','3':'负','4':'走','5':'已关闭'
       --保证金解冻，用户购买资金解冻；
       --2.首先查询保证金,民间高手用户ID        保证金冻结
       select  t.ensure_money,t.tx_user_id,t.price  into ensureMoney,userId,price from  T_C2C_PRODUCT t  where  t.c2c_id=c2cId;
       update  t_virtual_account  t  set t.frozen_money=t.frozen_money-ensureMoney  where  t.tx_user_id=c2cId;
       --insert into  t_va_frozen_log()
       --3.a.查询T_XINSHUI_ORDER表的支付状态为已支付(PAY_STATUS='2')b.为每一位购买者解冻,还应该更新c.新增一条解冻日志
      for v in (select t.buy_user_id,t.TX_MONEY,t.TX_CAIJIN,t.Xinshui_Order_Id  from  T_XINSHUI_ORDER  t  where  t.product_id=c2cId and t.pay_status='2') loop
            orderUserId:=v.buy_user_id;--得到购买人的用户ID
            txMoney:=v.tx_money;--应支付的金额
            txCaijin:=v.tx_caijin;--应支付的彩金
            xinshuiOrderId:=v.xinshui_order_id;
            --解冻用于购买心水的金额与彩金
            update  t_va_frozen_log  f set f.frozen_money=f.frozen_money-txMoney,
            f.frozen_mosaic_goldmoney=f.frozen_mosaic_goldmoney-txCaijin
            where  f.tx_user_id=orderUserId;
            --产生解冻日志
             SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNO FROM DUAL; 
             xinshuiOrderNO:=to_char(sysdate,'yyyymmdd')||seqOrderNo||ranInt;--订单号
            ---
            insert into T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,Frozen_Money,Frozen_Mosaic_Goldmoney,Frozen_Date,MEMO,TX_TYPE,FLG,ORDER_ID,ORDER_NO)
            values(SEQ_VA_FROZEN_LOG_ID.Nextval,orderUserId,txMoney,txCaijin,sysdate,'心水关闭','6','2',xinshuiOrderId,xinshuiOrderNO);
            --更改心水订单状态 STATUS='3'  PAY_STATUS='5'
            update T_XINSHUI_ORDER  t set t.status='3',t.pay_status='5' where  t.buy_user_id=orderUserId;
            
            
       end loop;
       insert into t_cs_handle_log(ID,cs_user_id,handle_time,cs_name,ip)
       values(SEQ_WITHDRAW_CS_LOG_ID.Nextval,p_cs_user_id,sysdate,p_cs_name,p_IP);
     
       exception 
            when others then 
                    rollback;
                raise dbException;
       
       
     
     
     end closeXinshui;
  
  
  
  
  
     
     /***
   *民间高手心水列表查询
  */
  procedure  findXinshuiList(raceType in varchar2,  key in varchar2,list_cursor out sys_refcursor)
  is  
  begin
     open list_cursor for select  b.race_name,decode(a.type,'1','亚盘','2','大小盘'),b.start_time,b.host_name,b.guest_name,c.username,c.xinshui_military,a.ensure_money,a.price 
       ,(select e.WIN_RATIO from T_EXPERT e  where  e.user_id=c.userid) WIN_RATIO
      from t_c2c_product a, t_against b,t_user c
     where  a.against_id=b.against_id  and a.tx_user_id=c.userid;
  end findXinshuiList;
  --生成心水对阵
  procedure  genXinshuiAgainst(againstIds in varchar2,retValue out varchar2)
  is 
  againstList TOOLS.table_list:=Tools.table_list();
  againstId Number(20);
  deadline varchar2(10);--截至日期间隔
  startTime Date;--开赛时间
  dbException exception;--数据库操作异常
  dbMaxDay varchar2(10);--数据库里面期次的最大值
  begin
     againstList:=TOOLS.fn_split(againstIds,',');
     --心水发布截至时间间隔,统一从t_dictionary读取
     select t.value into deadline from t_dictionary  t where t.code='DEADLINE' and t.type='XINSHUI';
     select  max(ta.phase) into dbMaxDay from t_xinshui_against  ta  where ta.phase like to_char(sysdate,'yymmdd')||'%';
     IF dbMaxDay IS  NULL THEN 
          dbMaxDay:=to_char(sysdate,'yymmdd')||'1';
     ELSE
          IF dbMaxDay=to_char(sysdate,'yymmdd')||'9' THEN
               retValue:='10';
            return;
          END IF;
          dbMaxDay:=to_char(to_number(dbMaxDay)+1);
     END IF;
     FOR v IN againstList.FIRST..againstList.LAST  LOOP--遍历每一场对阵
       IF againstList(v) is not null THEN
         againstId:=againstList(v);--对应一场对阵
         --查询每一场对阵的开赛时间
         select a.start_time into  startTime  from t_against  a  where a.against_id=to_number(againstId);
         startTime:=startTime-to_number(deadline)/(24*60);
         insert into T_XINSHUI_AGAINST(ID,AGAINST_ID,DEADLINE,PHASE)
         values(SEQ_XINSHUI_SCHEDULE_ID.Nextval,to_number(againstId),startTime,dbMaxDay);
        
       END IF;   
          
     END LOOP;
     exception
     when others then 
       rollback;
       raise dbException;
       
  
  
  
  end genXinshuiAgainst;



end XINSHUI;
