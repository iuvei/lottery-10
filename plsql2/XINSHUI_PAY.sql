create or replace package body XINSHUI_PAY is
--2010 04-26 12:56 心水赛果出来后  进行结算
--返回 1表示结算成功
procedure  executeXinshuiResult(resultValue OUT  NUMBER,againstId  IN NUMBER)
is
selectType CHAR(1);--民间高手发布的心水结果
score VARCHAR2(20);--对阵比分
c2cId NUMBER(20);--C2C表主键
ensureMoney  NUMBER(20,2):=0.00;--保证金
dbException  exception;
payStatus  CHAR(1);--支付状态
frozenMoney NUMBER(20,2):=0.00;--冻结的金额
frozenCaijin  NUMBER(20,2):=0.00;--冻结的彩金
buyUserId NUMBER(20);--购买人用户ID
allMoney NUMBER(20,2):=0.00;
xinshuiOrderNO VARCHAR2(100);
ranInt VARCHAR2(10);
seqOrderNo  VARCHAR2(100);
buyUserCnt NUMBER(20):=0;--购买用户数量
minjianUserId NUMBER(20);--民间高手用户ID
orderNO VARCHAR2(100);
allCaijin NUMBER(20,2):=0.00;
xinshuiOrderId NUMBER(20);--心水订单ID
xinshuiTxOrderNO VARCHAR2(100);--心水交易号
xinshuiPeichang NUMBER(20,2);--心水赔偿
price  NUMBER(20,2);--心水价格
minjianUsername varchar2(100);
c2cType char(1);
panKouIndex varchar2(20);--心水的盘口
xinshuiResult varchar2(1);
begin
   for v in(select b.c2c_id,a.score,b.select_type,b.Ensure_Money,b.price,b.tx_username,b.type,b.pan_kou_index
   from T_AGAINST a,T_C2C_PRODUCT b  where a.AGAINST_ID=b.AGAINST_ID  and b.against_id=againstId) loop
        c2cType:=v.type;
        selectType:=v.select_type;
        score:=v.score;
        c2cId:=v.c2c_id;
        ensureMoney:=v.ensure_money;
        price:=v.price;
        minjianUsername:=v.tx_username;
        panKouIndex:=v.pan_kou_index;
        select count(1) into  buyUserCnt  from T_XINSHUI_ORDER  xo   where  xo.product_id=c2cId  and xo.pay_status='2';
        --产生解冻订单号
        SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL;
        orderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt;--订单号
        select t.Tx_User_Id  into minjianUserId from T_C2C_PRODUCT  t  where  t.c2c_id=c2cId;
        xinshuiResult:=tools.xinshuiResult(score,panKouIndex,c2cType);
        if buyUserCnt=0 or xinshuiResult='0' then--如果没有人购买则返回-1 或者是不赔补赚
          --解冻民间高手的保证金
          update  T_VIRTUAL_ACCOUNT  va  set va.Frozen_Money=va.Frozen_Money-ensureMoney where  va.Tx_User_Id=minjianUserId;
          --更新该心水状态为 5:'已关闭'
          update  T_C2C_PRODUCT  cp  set cp.Status='5'  where  cp.c2c_id=c2cId;
          --插入解冻日志
          insert into t_Va_Frozen_Log(FROZEN_ID,Tx_User_Id,Frozen_Money,Frozen_Date,Memo,Order_Id,Category_Type,Order_No,Flg,Tx_Type)
          values(SEQ_VA_FROZEN_LOG_ID.Nextval,minjianUserId,ensureMoney,sysdate,'心水退款',c2cId,'15',orderNO,'2','6');
          --对于
        end if;
        if buyUserCnt>0 then--有普通用户购买此心水
          if xinshuiResult=selectType then--主队赢
            update  t_c2c_product  cp  set cp.status='2' where  cp.c2c_id=c2cId;
             --解冻民间高手的保证金
            update  T_VIRTUAL_ACCOUNT tm set tm.Frozen_Money=tm.frozen_money-ensureMoney where  tm.Tx_User_Id=minjianUserId;
            --
            --民间高手发布的心水正确,把购买用户已经冻结的钱真正扣除,并收取10%的手续费
          for o in(select  n.pay_status,m.frozen_money,m.frozen_mosaic_goldmoney,n.buy_user_id,n.xinshui_order_id
            from T_VA_FROZEN_LOG  m,T_XINSHUI_ORDER  n
            where  m.order_id=n.xinshui_order_id  and n.product_id=c2cId) loop
             xinshuiOrderId:=o.xinshui_order_id;
             payStatus:=o.pay_status;
             frozenMoney:=o.frozen_money;
             frozenCaijin:=o.frozen_mosaic_goldmoney;
             buyUserId:=o.buy_user_id;
            IF payStatus='2' then
            select  h.all_money,h.mosaic_gold into allMoney,allCaijin from T_VIRTUAL_ACCOUNT   h where  h.tx_user_id=buyUserId;
            --插入解冻日志
            SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL;
            xinshuiOrderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt;--订单号

            INSERT INTO T_VA_FROZEN_LOG(FRoZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,TX_TYPE,ALL_MONEY,ORDER_ID,Order_NO,FROZEN_MOSAIC_GOLDMONEY,FLG)
            values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,frozenMoney,sysdate,'6',allMoney,xinshuiOrderId,xinshuiOrderNO,frozenCaijin,'2');
            --更新购买人的T_VIRTUAL_ACCOUNT
             update T_VIRTUAL_ACCOUNT  va  set va.Frozen_Money=va.frozen_money-frozenMoney,va.all_money=va.all_money-frozenMoney,
             va.mosaic_gold=va.mosaic_gold-frozenCaijin  where  va.tx_user_id=buyUserId;

            --插入交易日志T_VA_TRANSACTION_LOG
            SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL;
            xinshuiTxOrderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt;--订单号
            insert into  T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,TX_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,FLG,ORDER_NO,TX_TYPE_NAME,TARGET_USERID)
            values(SEQ_VA_TRANSACTION_LOG_ID.Nextval,buyUserId,frozenMoney,sysdate,'购买心水','6',allMoney,allCaijin,frozenCaijin,xinshuiOrderId,'11','1',xinshuiTxOrderNO,'购买心水',minjianUserId);
            --更新心水订单状态为'3':已扣款
            update  t_xinshui_order  tn  set tn.Pay_Status='3' where  tn.xinshui_order_id=xinshuiOrderId;
           ELSIF payStatus='1' THEN
              --对于只冻结部分金额心水订单直接解冻账户
               update T_VIRTUAL_ACCOUNT  va  set va.Frozen_Money=va.frozen_money-frozenMoney,va.all_money=va.all_money-frozenMoney,
                va.mosaic_gold=va.mosaic_gold-frozenCaijin  where  va.tx_user_id=buyUserId;
              --更新心水订单状态
              update  T_XINSHUI_ORDER txo set txo.Pay_Status='5'  where  txo.xinshui_order_id=xinshuiOrderId;
              --插入购买用户的解冻日志
               INSERT INTO T_VA_FROZEN_LOG(FRoZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,TX_TYPE,ALL_MONEY,ORDER_ID,Order_NO,FROZEN_MOSAIC_GOLDMONEY)
               values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,frozenMoney,sysdate,'6',allMoney,xinshuiOrderId,xinshuiOrderNO,frozenCaijin);
           END IF;
         end loop;


         insert  into T_XINSHUI_GAIN(ID,C2C_ID,ENSURE_MONEY,GAIN,PERCENTAGE,TX_USER_ID,TX_USER_NAME) values
         (SEQ_XINSHUI_GAIN_ID.Nextval,c2cId,ensureMoney,price*buyUserCnt*0.1,0.1,minjianUserId,minjianUsername);

            --扣除 民间高手心水利润的10%  或者是保证金的10% 添加到表T_XINSHUI_GAIN
 ----------------------------------------------以下是民间高手猜错的情况---------------------------------------------
         elsif xinshuiResult<>selectType then
            update  t_c2c_product  cp  set cp.status='3' where  cp.c2c_id=c2cId;
            --民间高手发布的心水错误,把购买用户已经冻结的钱 解冻  并使用民间高手的保证金赔偿购买人 并收取10%的手续费
            --扣除民间高手的保证金
            update  T_VIRTUAL_ACCOUNT tm set tm.Frozen_Money=tm.frozen_money-ensureMoney,
            tm.all_money=tm.all_money-ensureMoney
            where  tm.Tx_User_Id=minjianUserId;
            --
           xinshuiPeichang:=ensureMoney/buyUserCnt;--每个购买用户的心水赔偿
            --民间高手发布的心水错误,把购买用户已经冻结的钱解冻
          for o in(select  n.pay_status,m.frozen_money,m.frozen_mosaic_goldmoney,n.buy_user_id,n.xinshui_order_id
            from T_VA_FROZEN_LOG  m,T_XINSHUI_ORDER  n
            where  m.order_id=n.xinshui_order_id  and n.product_id=c2cId) loop
             xinshuiOrderId:=o.xinshui_order_id;
             payStatus:=o.pay_status;
             frozenMoney:=o.frozen_money;
             frozenCaijin:=o.frozen_mosaic_goldmoney;
             buyUserId:=o.buy_user_id;
            IF payStatus='2' then
            select  h.all_money,h.mosaic_gold into allMoney,allCaijin from T_VIRTUAL_ACCOUNT   h where  h.tx_user_id=buyUserId;
            --插入解冻日志
            SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL;
            xinshuiOrderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt;--订单号

            INSERT INTO T_VA_FROZEN_LOG(FRoZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,TX_TYPE,ALL_MONEY,ORDER_ID,Order_NO,FROZEN_MOSAIC_GOLDMONEY,FLG)
            values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,frozenMoney,sysdate,'6',allMoney,xinshuiOrderId,xinshuiOrderNO,frozenCaijin,'2');
            --更新购买人的T_VIRTUAL_ACCOUNT
             update T_VIRTUAL_ACCOUNT  va  set va.Frozen_Money=va.frozen_money-frozenMoney,va.all_money=va.all_money+xinshuiPeichang,
             va.mosaic_gold=va.mosaic_gold-frozenCaijin  where  va.tx_user_id=buyUserId;

            --插入交易日志T_VA_TRANSACTION_LOG
            SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL;
            xinshuiTxOrderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt;--订单号
            insert into  T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,TX_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,FLG,ORDER_NO,TX_TYPE_NAME,TARGET_USERID)
            values(SEQ_VA_TRANSACTION_LOG_ID.Nextval,buyUserId,xinshuiPeichang,sysdate,'心水补偿','7',allMoney+xinshuiPeichang,allCaijin,0.00,xinshuiOrderId,'11','1',xinshuiTxOrderNO,'购买心水',minjianUserId);
            --更新心水订单状态为'4':已赔付
            update  t_xinshui_order  tn  set tn.Pay_Status='4' where  tn.xinshui_order_id=xinshuiOrderId;
           ELSIF payStatus='1' THEN
              --对于只冻结部分金额心水订单直接解冻账户
               update T_VIRTUAL_ACCOUNT  va  set va.Frozen_Money=va.frozen_money-frozenMoney,va.all_money=va.all_money-frozenMoney,
                va.mosaic_gold=va.mosaic_gold-frozenCaijin  where  va.tx_user_id=buyUserId;
              --更新心水订单状态
              update  T_XINSHUI_ORDER txo set txo.Pay_Status='5'  where  txo.xinshui_order_id=xinshuiOrderId;
              --插入购买用户的解冻日志
               INSERT INTO T_VA_FROZEN_LOG(FRoZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,TX_TYPE,ALL_MONEY,ORDER_ID,Order_NO,FROZEN_MOSAIC_GOLDMONEY)
               values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,frozenMoney,sysdate,'6',allMoney,xinshuiOrderId,xinshuiOrderNO,frozenCaijin);
           END IF;
         end loop;

            --扣除 民间高手心水利润的10%  或者是保证金的10% 添加到表T_XINSHUI_GAIN
            insert  into T_XINSHUI_GAIN(ID,C2C_ID,ENSURE_MONEY,GAIN,PERCENTAGE,TX_USER_ID,TX_USER_NAME) values
         (SEQ_XINSHUI_GAIN_ID.Nextval,c2cId,ensureMoney,ensureMoney*0.1,0.1,minjianUserId,minjianUsername);

         end if;
        end if;

   end loop;
   resultValue:=1;
--exception
--when others then
   --resultValue:=-1;
   --rollback;
   --raise dbException;


end executeXinshuiResult;
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
   orderNO Varchar2(100);--订单号
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
       orderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt;--订单号
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

   --前台用户支付 续费
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
     orderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt;--产生订单号
     frozenOrderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt2;--产生冻结表订单号
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
          values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,avaliable_money,sysdate,'购买民间高手心水','6',money,caijin,avaliable_caijin,xinshuiOrderId,'11',orderNO,'1');
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
          values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,txMoney,sysdate,'购买民间高手心水','6',money,caijin,txCaijin,xinshuiOrderId,'11',frozenOrderNO,'1');
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
         INSERT INTO T_XINSHUI_ORDER(XINSHUI_ORDER_ID,TYPE,PRODUCT_ID,BUY_USER_ID,STATUS,PHASE,BUY_TIME,Pay_Status,SOLD_USER_ID,SOLD_USERNAME,Buy_Username,TX_MONEY,TX_CAIJIN,ORDER_NO)
         values(xinshuiOrderId,'1',c2cId,buyUserId,'1',phaseNo,sysdate,'2' ,pubUserId,puUsername,buyUserName,avaliable_money,0.00,orderNO);
          --产生冻结日志
         INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
         values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,avaliable_money,sysdate,'购买民间高手心水','6',money,caijin,0.00,xinshuiOrderId,'11',frozenOrderNO,'1');

         update  T_XINSHUI_ORDER  t  set  t.pay_status='1'  where  t.xinshui_order_id=xinshuiOrderId;
         resultValue:='4';--余额不足
         return;


                -------
        ELSE
        txCaijin:=0.00;
        txMoney:=xinshui_price;
          --既从彩金里冻结 又从金额里冻结
          UPDATE  T_VIRTUAL_ACCOUNT va set va.FROZEN_MONEY=va.FROZEN_MONEY+txMoney where va.tx_user_id=buyUserId;

          INSERT INTO T_XINSHUI_ORDER(XINSHUI_ORDER_ID,TYPE,PRODUCT_ID,BUY_USER_ID,STATUS,PHASE,BUY_TIME,Pay_Status,SOLD_USER_ID,SOLD_USERNAME,BUY_USERNAME,TX_MONEY,TX_CAIJIN,ORDER_NO)

         values(xinshuiOrderId,'1',c2cId,buyUserId,'1',phaseNo,sysdate,'2',pubUserId,puUsername,buyUserName,txMoney,txCaijin,orderNO);
          --产生冻结日志
          INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
          values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,txMoney,sysdate,'购买民间高手心水','6',money,caijin,0.00,xinshuiOrderId,'11',frozenOrderNO,'1');
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
   --前台普通用户 取消自己的心水订单(状态:未支付)
 procedure  canXinshuiPay(xinshuiOrderId IN NUMBER,userId IN NUMBER,resultValue OUT NUMBER)
 is
 payStatus CHAR(1);
 frozenMoney NUMBER(20,2):=0.00;--冻结的金额
 frozenCaijin NUMBER(20,2):=0.00;--冻结的彩金
 orderNo varchar2(50);--订单号
 allMoney NUMBER(20,2);--剩余金额
 allCaijin NUMBER(20,2);--剩余彩金
 vaFrozenId NUMBER(20);
 dbException exception;
 ranInt varchar2(30);
 seqOrderNo varchar2(100);
 myxception exception;
 begin
      select  t.pay_status into payStatus from t_xinshui_order t  where  t.buy_user_id=userId and  t.xinshui_order_id=xinshuiOrderId;
      select SEQ_VA_FROZEN_LOG_ID.Nextval into vaFrozenId from dual;
      if payStatus='1' then
          --查询T_VA_FROZEN_LOG 得到冻结的金额  冻结的彩金
         for v in (select  b.FROZEN_MONEY,b.FROZEN_MOSAIC_GOLDMONEY, b.ORDER_NO,b.ALL_MONEY,b.MOSAIC_GOLDMONEY
         from  T_VA_FROZEN_LOG b where  b.tx_user_id=userId  and b.order_id=xinshuiOrderId) loop
           frozenMoney:=v.frozen_money;
           frozenCaijin:=v.Frozen_Mosaic_Goldmoney;
          orderNo:=v.order_no;
          allMoney:=v.all_money;
          allCaijin:=v.mosaic_goldmoney;

          end loop;
        if frozenMoney>=0 OR frozenCaijin>=0 then

        --插入解冻日志
          SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL;
          orderNO:=to_char(sysdate,'yyyymmdd')||seqOrderNo||ranInt;--产生解冻订单号
          insert into T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,Frozen_Date,Frozen_Mosaic_Goldmoney,Flg,TX_TYPE,CATEGORY_TYPE,Order_Id,ORDER_NO)
          values(vaFrozenId,userId,frozenMoney,sysdate,frozenCaijin,'2','6','11',xinshuiOrderId,orderNO);
        --更改虚拟账户冻结金额与冻结彩金
        update t_virtual_account   va  set va.frozen_money=va.frozen_money-frozenMoney,
        va.frozen_mosaic_gold=va.frozen_mosaic_gold-frozenCaijin
        where  va.tx_user_id=userId;
        --更改心水订单的状态
        update t_xinshui_order t set t.pay_status='5'  where  t.xinshui_order_id=xinshuiOrderId;

        end if;
      end if;
      resultValue:=1;
      exception
      when others then
      rollback;
       resultValue:=-1;
      raise myxception;



 end;
end XINSHUI_PAY;
