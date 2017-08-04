create or replace package body LOTTERY_DAIGOU_PAY is
--足彩单式代购
--orderId:投注订单ID
--userId投注人用户ID
--txMoney投注金额
PROCEDURE  daigou_pay(orderId IN NUMBER,userId IN NUMBER,txMoney IN NUMBER,useCaijin IN  CHAR,payResult  OUT CHAR)
IS 
accountStatus CHAR(1);--虚拟资金账户状态
ALLMoney NUMBER(20,2):=0.00;--账户总金额(包括已经冻结的部分)
frozenMoney  NUMBER(20,2):=0.00;--账户中已经冻结的金额
allCaijin NUMBER(20,2):=0.00;--账户彩金总金额(包括已经冻结的部分)
frozenCaijin  NUMBER(20,2):=0.00;--账户中已经冻结的彩金
availableMoney NUMBER(20,2):=0.00;--可用金额
availableCaijin NUMBER(20,2):=0.00;--可用彩金
payMoney NUMBER(20,2):=0.00;--应支付的金额
payCaijin NUMBER(20,2);--应支付的彩金
vatId NUMBER(20);--虚拟交易日志主键
ranInt VARCHAR2(3);
seqOrderNo NUMBER(20);
vaTransactionNo VARCHAR2(50);
BEGIN
   SELECT t.status,t.all_money,t.mosaic_gold ,t.frozen_money,t.frozen_mosaic_gold
   INTO accountStatus,ALLMoney,allCaijin ,frozenMoney,frozenCaijin FROM T_VIRTUAL_ACCOUNT t  WHERE  t.tx_user_id=userId;
   IF accountStatus='2' THEN
          dbms_output.put_line('账户已经被锁定');
        payResult:='5';
        RETURN;
   END IF;
   availableMoney:=ALLMoney-frozenMoney;
   availableCaijin:=allCaijin-frozenCaijin;
   SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL,SEQ_VA_TRANSACTION_LOG_ID.Nextval
    into ranInt,seqOrderNo,vatId
    FROM DUAL; 
         vaTransactionNo:=to_char(sysdate,'yyyymmdd')||'ZC'||seqOrderNo||ranInt;--产生订单号
   IF useCaijin='1' AND availableMoney+availableCaijin<txMoney THEN--使用彩金,余额+彩金仍然不足
       payResult:='4';
       RETURN;
   ELSIF useCaijin='2' AND availableMoney+availableCaijin<txMoney  THEN--不使用彩金,余额不足
       payResult:='4';
       RETURN;
   ELSE
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
       INSERT INTO T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_MOSAIC_GOLDMONEY,ORDER_ID,ORDER_NO)
       VALUES(vatId,userId,payMoney,payCaijin,orderId,vaTransactionNo);
      END IF;
END;
END LOTTERY_DAIGOU_PAY;
