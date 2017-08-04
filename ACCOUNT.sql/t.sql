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
   dbException exception;
   begin
       select a.tx_money,a.tx_caijin,a.buy_user_id into txMoney,txCaijin,buyUserId  from  T_XINSHUI_LOG  a  
       where a.xinshui_id=orderId;
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
        
       update  T_XINSHUI_LOG  t  set  t.pay_status='2'  where  t.xinshui_id=orderId;
       result:='1';
       
       exception
       when others then
        rollback;
        raise dbException;
     
   end;