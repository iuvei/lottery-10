create or replace package body XINSHUI_PAY is
   --后台客服确认支付
   procedure   backendConfirmPay(orderId in NUMBER,result OUT VARCHAR2)
   is
   txMoney NUMBER(10,2);--应支付的金额
   txCaijin NUMBER(10,2);--应支付的彩金
   buyUserId NUMBER(20);--购买人用户ID
   money NUMBER(20,2);--总金额
   caijin NUMBER(20,2);--总彩金
   frozen_money  NUMBER(20,2);--已经冻结的金额
   frozen_caijin  NUMBER(20,2);--已经冻结的彩金
   avaliable_money NUMBER(20,2);--本次可用余额
   avaliable_caijin NUMBER(20,2);--本次可用彩金
   ranInt NUMBER(3);--三位随机数
   account_status char;--账户状态
   orderNO Varchar2(50);--订单号
   seqOrderNo Number(20);
   dbException exception;
   begin
       select a.tx_money,a.tx_caijin,a.buy_user_id into txMoney,txCaijin,buyUserId  from  T_XINSHUI_ORDER  a  
       where a.xinshui_order_id=orderId;
       --
       SELECT t.status,t.all_money,t.mosaic_gold ,t.frozen_money,t.frozen_mosaic_gold
       INTO account_status,money,caijin ,frozen_money,frozen_caijin
       FROM T_VIRTUAL_ACCOUNT t  WHERE  t.tx_user_id=buyUserId;
       dbms_output.put_line(money||'  '||caijin);
       --第一步判断账户状态
       IF account_status='2' THEN
          dbms_output.put_line('账户已经被锁定');
        result:='5';
        return;
       END IF;
       avaliable_money:=money-frozen_money;--本次可用余额
       avaliable_caijin:=caijin-frozen_caijin;--本次可用彩金
       SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL; 
       orderNO:=to_char(sysdate,'yyyymmdd')||seqOrderNo||ranInt;--订单号
        dbms_output.put_line('消费金额='||txMoney||txCaijin);
       
       IF  avaliable_money+avaliable_caijin <(txMoney+txCaijin) THEN
                  dbms_output.put_line('----余额不足');
                  result:='4';
                  return;
        END IF;
        UPDATE  T_VIRTUAL_ACCOUNT va set va.FROZEN_MOSAIC_GOLD=va.FROZEN_MOSAIC_GOLD+avaliable_caijin,--冻结所有可用彩金
                va.FROZEN_MONEY=va.FROZEN_MONEY+txMoney-avaliable_caijin 
                where va.tx_user_id=buyUserId; --既从彩金里冻结 又从金额里冻结
                --产生冻结日志
                INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
                values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,txMoney-avaliable_caijin,sysdate,null,'6',money,caijin,avaliable_caijin,orderId,'11',orderNO,'1');
                dbms_output.put_line('购买C2C心水，同时冻结彩金与金额');
        
       update  T_XINSHUI_ORDER  t  set  t.pay_status='2'  where  t.xinshui_order_id=orderId;
       result:='1';
       
       exception
       when others then
        rollback;
        raise dbException;
     
   end;
   
   --前台用户支付
   procedure   siteXinshuiPay(orderId in NUMBER,result OUT VARCHAR2)
   is
   txMoney NUMBER(10,2);--应支付的金额
   txCaijin NUMBER(10,2);--应支付的彩金
   buyUserId NUMBER(20);--购买人用户ID
   money NUMBER(20,2);--总金额
   caijin NUMBER(20,2);--总彩金
   frozen_money  NUMBER(20,2);--已经冻结的金额
   frozen_caijin  NUMBER(20,2);--已经冻结的彩金
   avaliable_money NUMBER(20,2);--本次可用余额
   avaliable_caijin NUMBER(20,2);--本次可用彩金
   ranInt NUMBER(3);--三位随机数
   account_status char;--账户状态
   orderNO Varchar2(50);--订单号
   seqOrderNo Number(20);
   alreadyFrozenMoney Number(10,2);
   alreadyFrozenCaijin  Number(10,2);
   dbException exception;
   begin
       --首先查看心水订单表
       select a.tx_money,a.tx_caijin,a.buy_user_id into txMoney,txCaijin,buyUserId  from  T_XINSHUI_ORDER  a  
       where a.xinshui_order_id=orderId;
       --
       SELECT t.status,t.all_money,t.mosaic_gold ,t.frozen_money,t.frozen_mosaic_gold
       INTO account_status,money,caijin ,frozen_money,frozen_caijin
       FROM T_VIRTUAL_ACCOUNT t  WHERE  t.tx_user_id=buyUserId;
       dbms_output.put_line(money||'  '||caijin);
       --第一步判断账户状态
       IF account_status='2' THEN
          dbms_output.put_line('账户已经被锁定');
        result:='5';
        return;
       END IF;
       avaliable_money:=money-frozen_money;--本次可用余额
       avaliable_caijin:=caijin-frozen_caijin;--本次可用彩金
       --查看以前是否支付(冻结)过部分金额或者彩金
       select FROZEN_MONEY,FROZEN_MOSAIC_GOLDMONEY into alreadyFrozenMoney,alreadyFrozenCaijin from T_VA_FROZEN_LOG  tb where  tb.CATEGORY_TYPE='11' and tb.order_id=orderId;
       
       
       SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL; 
       orderNO:=to_char(sysdate,'yyyymmdd')||seqOrderNo||ranInt;--订单号
        dbms_output.put_line('消费金额='||txMoney||txCaijin);
       
       IF  avaliable_money+avaliable_caijin <(txMoney+txCaijin) THEN
                  dbms_output.put_line('----余额不足');
                  result:='4';
                  return;
        END IF;
        --应该减去alreadyFrozenMoney  alreadyFrozenCaijin
        UPDATE  T_VIRTUAL_ACCOUNT va set va.FROZEN_MOSAIC_GOLD=va.FROZEN_MOSAIC_GOLD+avaliable_caijin-alreadyFrozenMoney,--冻结所有可用彩金
                va.FROZEN_MONEY=va.FROZEN_MONEY+txMoney-avaliable_caijin-alreadyFrozenCaijin
                where va.tx_user_id=buyUserId; --既从彩金里冻结 又从金额里冻结
                --产生冻结日志
                INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
                values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,txMoney-avaliable_caijin-alreadyFrozenMoney,sysdate,null,'6',money,caijin,0.00,orderId,'11',orderNO,'1');
                dbms_output.put_line('购买C2C心水，同时冻结彩金与金额');
        
       update  T_XINSHUI_ORDER  t  set  t.pay_status='2'  where  t.xinshui_order_id=orderId;
       result:='1';
       
       exception
       when others then
        rollback;
        raise dbException;
     
   end;
   
   -------------------------
   
   --前台用户支付,普通用户第一次支付
   procedure   myXinshuiPay(c2cId in NUMBER,buyUserId in NUMBER ,useCaijin in char,resultValue OUT VARCHAR2,buyUserName in varchar2)
   is
   txMoney NUMBER(10,2);--应支付的金额
   txCaijin NUMBER(10,2):=0.00;--应支付的彩金
   frozen_money  NUMBER(20,2);--已经冻结的金额
   frozen_caijin  NUMBER(20,2);--已经冻结的彩金
   money NUMBER(20,2);--总金额
   caijin NUMBER(20,2);--总彩金
   xinshuiOrderId NUMBER(20);--心水订单ID
   avaliable_money NUMBER(20,2);--本次可用余额
   avaliable_caijin NUMBER(20,2);--本次可用彩金
   ranInt NUMBER(3);--三位随机数
   account_status char;--账户状态
   orderNO Varchar2(50);--订单号
   seqOrderNo Number(20);
   alreadyFrozenMoney Number(10,2);
   alreadyFrozenCaijin  Number(10,2);
   xishu NUMBER(5);--保证金与心水价格的系数
   ensureMoney  NUMBER(20);--保证金
   soldUsername VARCHAR(90);
   soldUserId  NUMBER(20);--民间高手用户ID
   xinshui_price  NUMBER(20,2);
   dbException exception;
   phaseNo varchar2(60);
   ranInt2 varchar2(6);
   frozenOrderNO varchar2(50);--冻结表订单
   puUsername varchar2(60);--民间高手用户名
   pubUserId NUMBER(20);--民间高手用户ID
  
   begin
     --取得民间高手的用户名  用户ID
     ---select p.tx_username,p.tx_user_id into puUsername,pubUserId from T_C2C_PRODUCT p where  p.c2c_id=c2cId;
   
   
       --首先查看T_C2C_PRODUCT表,得到心水保证金，
      select  t.ENSURE_MONEY,t.TX_USERNAME,TX_USER_ID,a.phase,t.tx_username,t.tx_user_id
      into ensureMoney,soldUsername,soldUserId,phaseNo, puUsername,pubUserId
      
      from T_C2C_PRODUCT t ,T_XINSHUI_AGAINST  a
      where  t.C2C_ID=c2cId  and t.against_id=a.against_id;
      --价格系数(从T_DICTIONARY读取)
      select  t.VALUE into xishu  from  T_DICTIONARY t where  t.CODE='MARC2C_GIN_VS_PRICE' and t.TYPE='XINSHUI';
      SELECT t.status,t.all_money,t.mosaic_gold ,t.frozen_money,t.frozen_mosaic_gold
      INTO account_status,money,caijin ,frozen_money,frozen_caijin
      FROM T_VIRTUAL_ACCOUNT t  WHERE  t.tx_user_id=buyUserId;
      --第一步判断账户状态
      IF account_status='2' THEN
          dbms_output.put_line('账户已经被锁定');
        resultValue:='5';
        return;
      END IF;
       avaliable_money:=money-frozen_money;--本次可用余额
       avaliable_caijin:=caijin-frozen_caijin;--本次可用彩金
     IF useCaijin='1' THEN
        txCaijin:=avaliable_caijin;--如果使用彩金 则交易彩金即为可用彩金
     END IF;
     xinshui_price:=ensureMoney/xishu;--价格
     select SEQ_XINSHUI_LOG_ID.NEXTVAL into xinshuiOrderId from DUAL;
     SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL; 
     SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999') into ranInt2 FROM DUAL; 
     orderNO:=to_char(sysdate,'yyyymmdd')||seqOrderNo||ranInt;--产生订单号
     frozenOrderNO:=to_char(sysdate,'yyyymmdd')||seqOrderNo||ranInt2;--产生冻结表订单号
     IF useCaijin='1' THEN--使用彩金时 金额+彩金 不足以本次支付
       IF  avaliable_money+avaliable_caijin<xinshui_price THEN
                  dbms_output.put_line('----余额不足');
                 
             -----此时应该冻结所有金额 包括 彩金
             UPDATE  T_VIRTUAL_ACCOUNT va set va.FROZEN_MOSAIC_GOLD=va.mosaic_gold,
             va.FROZEN_MONEY=va.all_money where va.tx_user_id=buyUserId; --既从彩金里冻结 又从金额里冻结
            --产生订单
            INSERT INTO T_XINSHUI_ORDER(XINSHUI_ORDER_ID,TYPE,PRODUCT_ID,BUY_USER_ID,STATUS,PHASE,BUY_TIME,Pay_Status,ORDER_NO,SOLD_USER_ID,SOLD_USERNAME,BUY_USERNAME,TX_MONEY,TX_CAIJIN)
          values(xinshuiOrderId,'1',c2cId,buyUserId,'1',phaseNo,sysdate,'2',orderNO,pubUserId,puUsername,buyUserName,avaliable_money,avaliable_caijin);
          --产生冻结日志
          INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
          values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,avaliable_money,sysdate,'购买民间高手心水','6',money,caijin,avaliable_caijin,c2cId,'11',orderNO,'1');
                --dbms_output.put_line('购买C2C心水，同时冻结彩金与金额');
          update  T_XINSHUI_ORDER  t  set  t.pay_status='1'  where  t.xinshui_order_id=xinshuiOrderId;
               resultValue:='4';--余额不足
            return;
            
           
            
            ------
                 
       ELSE
          IF avaliable_caijin>xinshui_price THEN
              txCaijin:=xinshui_price;
              txMoney:=0;
          ELSE
              txCaijin:=avaliable_caijin;
              txMoney:=xinshui_price-avaliable_caijin;
          END IF;
          UPDATE  T_VIRTUAL_ACCOUNT va set va.FROZEN_MOSAIC_GOLD=va.FROZEN_MOSAIC_GOLD+txCaijin,
          va.FROZEN_MONEY=va.FROZEN_MONEY+txMoney where va.tx_user_id=buyUserId; --既从彩金里冻结 又从金额里冻结
          INSERT INTO T_XINSHUI_ORDER(XINSHUI_ORDER_ID,TYPE,PRODUCT_ID,BUY_USER_ID,STATUS,PHASE,BUY_TIME,Pay_Status,ORDER_NO ,SOLD_USER_ID,SOLD_USERNAME,BUY_USERNAME,TX_MONEY,TX_CAIJIN)
          values(xinshuiOrderId,'1',c2cId,buyUserId,'1',phaseNo,sysdate,'2',orderNO,pubUserId,puUsername,buyUserName,txMoney,txCaijin);
          
          
          --产生冻结日志
          INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
          values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,txMoney,sysdate,'购买民间高手心水','6',money,caijin,txCaijin,c2cId,'11',frozenOrderNO,'1');
                --dbms_output.put_line('购买C2C心水，同时冻结彩金与金额');
        
               update  T_XINSHUI_ORDER  t  set  t.pay_status='2'  where  t.xinshui_order_id=xinshuiOrderId;
               resultValue:='1';
           
           ----
       END IF;
     END IF;
     IF useCaijin='2' THEN
        IF  avaliable_money<xinshui_price THEN--不使用彩金时 金额 不足以本次支付
                  dbms_output.put_line('----余额不足');
               
                 -----此时应该冻结所有金额 包括 彩金
         UPDATE  T_VIRTUAL_ACCOUNT va set va.FROZEN_MONEY=va.all_money where va.tx_user_id=buyUserId; --既从彩金里冻结 又从金额里冻结
            --产生订单
         INSERT INTO T_XINSHUI_ORDER(XINSHUI_ORDER_ID,TYPE,PRODUCT_ID,BUY_USER_ID,STATUS,PHASE,BUY_TIME,Pay_Status,SOLD_USER_ID,SOLD_USERNAME,Buy_Username,TX_MONEY,TX_CAIJIN)
         values(xinshuiOrderId,'1',c2cId,buyUserId,'1',phaseNo,sysdate,'2' ,pubUserId,puUsername,buyUserName,avaliable_money,0.00);
          --产生冻结日志
         INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
         values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,avaliable_money,sysdate,'购买民间高手心水','6',money,caijin,0.00,c2cId,'11',frozenOrderNO,'1');
               
         update  T_XINSHUI_ORDER  t  set  t.pay_status='1'  where  t.xinshui_order_id=xinshuiOrderId;
         resultValue:='4';--余额不足
         return;
                
                
                -------
        ELSE
        txCaijin:=0.00;
        txMoney:=xinshui_price;
          --既从彩金里冻结 又从金额里冻结
          UPDATE  T_VIRTUAL_ACCOUNT va set va.FROZEN_MONEY=va.FROZEN_MONEY+txMoney where va.tx_user_id=buyUserId;
          INSERT INTO T_XINSHUI_ORDER(XINSHUI_ORDER_ID,TYPE,PRODUCT_ID,BUY_USER_ID,STATUS,PHASE,BUY_TIME,Pay_Status,SOLD_USER_ID,SOLD_USERNAME,BUY_USERNAME,TX_MONEY,TX_CAIJIN)
          values(xinshuiOrderId,'1',c2cId,buyUserId,'1',phaseNo,sysdate,'2',pubUserId,puUsername,buyUserName,txMoney,txCaijin);
          --产生冻结日志
          INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
          values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,txMoney,sysdate,'购买民间高手心水','6',money,caijin,0.00,c2cId,'11',frozenOrderNO,'1');
                --dbms_output.put_line('购买C2C心水，同时冻结彩金与金额');
          update  T_XINSHUI_ORDER  t  set  t.pay_status='2'  where  t.xinshui_order_id=xinshuiOrderId;
               resultValue:='1';
       END IF;
     END IF;
     exception
       when others then
        resultValue:='-1';
        rollback;
        raise dbException;
     
   end;
end XINSHUI_PAY;
