create or replace package body LOTTERY_DAIGOU_PAY is
--代购的最新方法  2010-03-19 10点02分
PROCEDURE  bet_daigou_pay(betOrderId OUT NUMBER,userId IN NUMBER,txMoney IN NUMBER,useCaijin IN  CHAR,payResult  OUT NUMBER,categoryType IN  VARCHAR2,alreadyPay out NUMBER)
IS 
accountStatus VARCHAR2(100);--虚拟资金账户状态
ALLMoney NUMBER(20,2):=0.00;--账户总金额(包括已经冻结的部分)
frozenMoney  NUMBER(20,2):=0.00;--账户中已经冻结的金额
allCaijin NUMBER(20,2):=0.00;--账户彩金总金额(包括已经冻结的部分)
frozenCaijin  NUMBER(20,2):=0.00;--账户中已经冻结的彩金
availableMoney NUMBER(20,2):=0.00;--可用金额
availableCaijin NUMBER(20,2):=0.00;--可用彩金
payMoney NUMBER(20,2):=0.00;--应支付的金额
payCaijin NUMBER(20,2);--应支付的彩金
vatId NUMBER(20);--虚拟交易日志主键
ranInt VARCHAR2(300);--随机数
seqOrderNo NUMBER(20);
vaTransactionNo VARCHAR2(10000);--扣款流水号
dbException  EXCEPTION;--数据库操作异常

BEGIN
   select SEQ_BET_ORDER_ID.NEXTVAL  into  betOrderId  from dual;
   SELECT t.status,t.all_money,t.mosaic_gold ,t.frozen_money,t.frozen_mosaic_gold
   INTO accountStatus,ALLMoney,allCaijin ,frozenMoney,frozenCaijin FROM T_VIRTUAL_ACCOUNT t  WHERE  t.tx_user_id=userId;
   IF accountStatus='2' THEN
          dbms_output.put_line('账户已经被锁定');
        payResult:=5;
        RETURN;
   END IF;
   availableMoney:=ALLMoney-frozenMoney;--当前可用金额
   availableCaijin:=allCaijin-frozenCaijin;--当前可用彩金
   SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL,SEQ_VA_TRANSACTION_LOG_ID.Nextval
   INTO ranInt,seqOrderNo,vatId FROM DUAL; 
   vaTransactionNo:=to_char(sysdate,'yyyymmdd')||'ZC'||seqOrderNo||ranInt;--产生扣款流水号
   IF useCaijin='1'  THEN--使用彩金,余额+彩金仍然不足
       IF availableMoney+availableCaijin<txMoney THEN
             
             --冻结金额
            UPDATE  t_virtual_account  a  SET  a.frozen_mosaic_gold=a.frozen_mosaic_gold+availableCaijin,
             a.frozen_money=a.frozen_money+availableMoney
            WHERE  a.tx_user_id=userId;
            --产生冻结日志
            INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
            VALUES(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,userId,availableMoney,sysdate,'购买彩票','4',ALLMoney,allCaijin,availableCaijin,betOrderId,categoryType,vaTransactionNo,'1');
            alreadyPay:=availableMoney+availableCaijin;
            payResult:=4;
           
        RETURN;
       ELSE
           
           --扣款
           IF txMoney>=availableCaijin THEN
               payCaijin:=availableCaijin;
               payMoney:=txMoney-payCaijin;
           ELSE
              payCaijin:=availableCaijin;
              payMoney:=0.00;
           END IF;
           
           UPDATE  t_virtual_account  b  SET  b.all_money=b.all_money-payMoney,
           b.mosaic_gold=b.mosaic_gold-payCaijin
           WHERE  b.tx_user_id=userId;
           
           --产生交易日志
           INSERT INTO T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_MOSAIC_GOLDMONEY,ORDER_ID,ORDER_NO,TX_DATE,
           TX_TYPE,TX_TYPE_NAME,FLG,CATEGORY_TYPE)
           VALUES(vatId,userId,payMoney,payCaijin,betOrderId,vaTransactionNo,sysdate,'4','购买彩票',categoryType,'1');
           payResult:=1;
           alreadyPay:=payCaijin+payMoney;
       END IF;
       
   ELSIF useCaijin<>'2' THEN--不使用彩金,余额不足
       IF availableMoney <txMoney THEN
         
          --冻结金额
             UPDATE  t_virtual_account  a  SET  
             a.frozen_money=a.frozen_money+availableMoney
            WHERE  a.tx_user_id=userId;
            --产生冻结日志
            INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
            VALUES(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,userId,availableMoney,sysdate,'购买彩票','4',ALLMoney,allCaijin,0.00,betOrderId,categoryType,vaTransactionNo,'1');
            payResult:=4;
            alreadyPay:=availableMoney;
         RETURN;
       ELSE
          
           --扣款
           payMoney:=txMoney;
           payCaijin:=0.00;
           UPDATE  t_virtual_account  b  SET  b.all_money=b.all_money-payMoney
           WHERE  b.tx_user_id=userId;
           --产生交易日志
           INSERT INTO T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_MOSAIC_GOLDMONEY,ORDER_ID,ORDER_NO,TX_DATE,
           TX_TYPE,TX_TYPE_NAME,FLG,CATEGORY_TYPE)
           VALUES(vatId,userId,payMoney,payCaijin,betOrderId,vaTransactionNo,sysdate,'4','购买彩票','1',categoryType);
            alreadyPay:=payMoney;
            payResult:=1;
       END IF;
   END IF;
   
   
   EXCEPTION
   WHEN OTHERS THEN
     rollback;
     payResult:=-1;
   raise dbException;
END;



--合买支付  其实只是冻结  要等到赛果处理才能进行结算
--返回值:
--1:正常 5:账户已经被锁定 4:余额不足 6:剩余份数为0
PROCEDURE  coop_pay(orderId OUT NUMBER,userId IN NUMBER,txMoney IN NUMBER,useCaijin IN  CHAR,payResult  OUT NUMBER,categoryType IN  VARCHAR2)
is 
accountStatus CHAR(1);--虚拟资金账户状态
ALLMoney NUMBER(20,2):=0.00;--账户总金额(包括已经冻结的部分)
frozenMoney  NUMBER(20,2):=0.00;--账户中已经冻结的金额
allCaijin NUMBER(20,2):=0.00;--账户彩金总金额(包括已经冻结的部分)
frozenCaijin  NUMBER(20,2):=0.00;--账户中已经冻结的彩金
availableMoney NUMBER(20,2):=0.00;--可用金额
availableCaijin NUMBER(20,2):=0.00;--可用彩金
payMoney NUMBER(20,2):=0.00;--应支付的金额
payCaijin NUMBER(20,2);--应支付的彩金
frozenID NUMBER(20);--虚拟交易日志主键
ranInt VARCHAR2(30);
seqOrderNo NUMBER(20);
frozenOrderNo VARCHAR2(1000);--冻结订单号
dbException  EXCEPTION;
begin
   select SEQ_BET_ORDER_ID.NEXTVAL  into  orderId  from dual;
   SELECT t.status,t.all_money,t.mosaic_gold ,t.frozen_money,t.frozen_mosaic_gold
   INTO accountStatus,ALLMoney,allCaijin ,frozenMoney,frozenCaijin FROM T_VIRTUAL_ACCOUNT t  WHERE  t.tx_user_id=userId;
   IF accountStatus='2' THEN
          dbms_output.put_line('账户已经被锁定');
        payResult:=5;
        rollback;
        RETURN;
   END IF;
   availableMoney:=ALLMoney-frozenMoney;--可用金额
   availableCaijin:=allCaijin-frozenCaijin;--可用彩金
   SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL,SEQ_VA_FROZEN_LOG_ID.NEXTVAL
   into ranInt,seqOrderNo,frozenID FROM DUAL; 
   frozenOrderNo:=to_char(sysdate,'yyyymmdd')||'ZC'||seqOrderNo||ranInt;--产生订单号
   IF useCaijin='1' AND availableMoney+availableCaijin<txMoney THEN--使用彩金,余额+彩金仍然不足
       payResult:=4;
       rollback;
       RETURN;
   ELSIF useCaijin<>'1' AND availableMoney+availableCaijin<txMoney  THEN--不使用彩金,余额不足
       payResult:=4;
       rollback;
       RETURN;
   ELSE
       --1.冻结金额 彩金
      IF useCaijin='1'  THEN--使用彩金
        IF availableCaijin>=txMoney THEN--彩金足够支付
            payMoney:=0;
            payCaijin:=txMoney;
            UPDATE  t_virtual_account  m  SET  m.frozen_mosaic_gold=m.frozen_mosaic_gold+txMoney
            WHERE  m.tx_user_id=userId;
         ELSE                           --彩金不足,同时使用彩金与金额支付
            payMoney:=txMoney-availableCaijin;
            payCaijin:=availableCaijin;
            UPDATE  t_virtual_account  m  SET  m.frozen_mosaic_gold=m.frozen_mosaic_gold+payCaijin,
            m.frozen_money=m.frozen_money+payMoney
            WHERE  m.tx_user_id=userId;
         END IF;
      ELSE
         --使用金额支付
            payMoney:=txMoney;
            payCaijin:=0.00;
            UPDATE  t_virtual_account  m  SET m.frozen_money=m.frozen_money+payMoney
            WHERE  m.tx_user_id=userId;
       END IF;
       --2.产生冻结日志
       INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,Frozen_Money,Frozen_Mosaic_Goldmoney,ORDER_ID,ORDER_NO,
       CATEGORY_TYPE,FLG,FROZEN_DATE,TX_TYPE)
       VALUES(frozenID,userId,payMoney,payCaijin,orderId,frozenOrderNo,categoryType,'1',sysdate,'4');
       payResult:=1;
      END IF;
   
   EXCEPTION
   WHEN OTHERS THEN
     rollback;
     payResult:=-1;
   raise dbException;
end;
--足彩单式代购
--orderId:投注订单ID
--userId投注人用户ID
--txMoney投注金额
PROCEDURE  daigou_pay(orderId IN NUMBER,userId IN NUMBER,txMoney IN NUMBER,useCaijin IN  CHAR,payResult  OUT NUMBER)
IS 
accountStatus VARCHAR2(100);--虚拟资金账户状态
ALLMoney NUMBER(20,2):=0.00;--账户总金额(包括已经冻结的部分)
frozenMoney  NUMBER(20,2):=0.00;--账户中已经冻结的金额
allCaijin NUMBER(20,2):=0.00;--账户彩金总金额(包括已经冻结的部分)
frozenCaijin  NUMBER(20,2):=0.00;--账户中已经冻结的彩金
availableMoney NUMBER(20,2):=0.00;--可用金额
availableCaijin NUMBER(20,2):=0.00;--可用彩金
payMoney NUMBER(20,2):=0.00;--应支付的金额
payCaijin NUMBER(20,2);--应支付的彩金
vatId NUMBER(20);--虚拟交易日志主键
ranInt VARCHAR2(300);--随机数
seqOrderNo NUMBER(20);
vaTransactionNo VARCHAR2(10000);--扣款流水号
dbException  EXCEPTION;--数据库操作异常
BEGIN
   SELECT t.status,t.all_money,t.mosaic_gold ,t.frozen_money,t.frozen_mosaic_gold
   INTO accountStatus,ALLMoney,allCaijin ,frozenMoney,frozenCaijin FROM T_VIRTUAL_ACCOUNT t  WHERE  t.tx_user_id=userId;
   IF accountStatus='2' THEN
          dbms_output.put_line('账户已经被锁定');
        payResult:=5;
        RETURN;
   END IF;
   availableMoney:=ALLMoney-frozenMoney;--当前可用金额
   availableCaijin:=allCaijin-frozenCaijin;--当前可用彩金
   SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL,SEQ_VA_TRANSACTION_LOG_ID.Nextval
   INTO ranInt,seqOrderNo,vatId FROM DUAL; 
   vaTransactionNo:=to_char(sysdate,'yyyymmdd')||'ZC'||seqOrderNo||ranInt;--产生扣款流水号
   IF useCaijin='1' AND availableMoney+availableCaijin<txMoney THEN--使用彩金,余额+彩金仍然不足
       payResult:=4;
       RETURN;
   ELSIF useCaijin='2' AND availableMoney+availableCaijin<txMoney  THEN--不使用彩金,余额不足
       payResult:=4;
       RETURN;
   ELSIF  availableMoney+availableCaijin>txMoney  THEN
       --1.扣款
      IF useCaijin='1'  THEN--使用彩金
        IF availableCaijin>=txMoney THEN--彩金足够支付
            payMoney:=0;
            payCaijin:=txMoney;
            UPDATE  t_virtual_account  m  SET  m.mosaic_gold=m.mosaic_gold-txMoney
            WHERE  m.tx_user_id=userId;
         ELSE                           --彩金不足,同时使用彩金与金额支付
            payMoney:=txMoney-availableCaijin;
            payCaijin:=availableCaijin;
            UPDATE  t_virtual_account  m  SET  m.mosaic_gold=m.mosaic_gold-availableCaijin,
            m.all_money=m.all_money-txMoney+availableCaijin
            WHERE  m.tx_user_id=userId;
         END IF;
      ELSE
         --使用金额支付
            payMoney:=txMoney;
            payCaijin:=0.00;
            UPDATE  t_virtual_account  m  SET m.all_money=m.all_money-txMoney
            WHERE  m.tx_user_id=userId;
       END IF;
       --2.产生交易日志
       INSERT INTO T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_MOSAIC_GOLDMONEY,ORDER_ID,ORDER_NO,TX_DATE,
       TX_TYPE,TX_TYPE_NAME,FLG,CATEGORY_TYPE)
       VALUES(vatId,userId,payMoney,payCaijin,orderId,vaTransactionNo,sysdate,'4','购买彩票','2','1');
      END IF;
   payResult:=1;
   EXCEPTION
   WHEN OTHERS THEN
     rollback;
     payResult:=-1;
   raise dbException;
END;
END LOTTERY_DAIGOU_PAY;
