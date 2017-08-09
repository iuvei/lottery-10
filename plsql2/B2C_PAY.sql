create or replace package body B2C_PAY is
--B2C 金额不足时 7天解冻  2010-04-19 10:54
procedure unFrozenB2COrder
is
buyTime varchar2(20);
now varchar2(10):=to_char(sysdate,'yyyy-mm-dd');
buyUserId number(20);
txMoney number(20,2);
txCaijin number(20,2);
allMoney number(20,2);
frozenMoney  number(20,2);
allCaijin number(20,2);
availableMoney number(20,2);
dbException exception;
begin
      for  v in (select t.xinshui_order_id,t.order_type,t.buy_user_id,t.buy_time,t.product_id,t.sold_user_id,t.sold_username,
          t.buy_username,t.tx_money,t.tx_caijin from  t_xinshui_order t where t.pay_status='1' and t.type='2' and (t.tx_money>0 or t.tx_caijin>0)) loop
          buyTime:=to_char(v.buy_time+7,'yyyy-mm-dd');
          buyUserId:=v.buy_user_id;
          txMoney:=v.tx_money;
          txCaijin:=v.tx_caijin;
          if  buyTime=now then
              update  t_virtual_account va  set  va.frozen_money=va.frozen_money-txMoney,va.frozen_mosaic_gold=va.frozen_mosaic_gold-txCaijin
              where  va.tx_user_id=buyUserId;
          --
            select  va.all_money,va.frozen_money,va.mosaic_gold  into allMoney,frozenMoney,allCaijin
            from t_virtual_account  va  where va.tx_user_id=buyUserId;--查询用户总金额，冻结金额
            availableMoney:=allMoney-frozenMoney;
           --产生解冻日志
          insert into T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY
          ,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG
         )values
           (SEQ_VA_FROZEN_LOG_ID.Nextval,buyUserId,availableMoney,sysdate,'购买专家投资报告7天解冻',allMoney,allCaijin,0,00,
           SEQ_XINSHUI_LOG_ID.Currval,'10',SEQ_ORDER_NO.NEXTVAL||'XS'||SEQ_ORDER_NO.NEXTVAL||to_char(DBMS_RANDOM.VALUE(100,999),'999'),'2'
           );
           
          end if;                                                           
      end loop;
      --resultvalue:=1;
exception
  when others then
     --resultvalue:=-1;
     rollback;
     raise dbException;
end;
--b2c继续预订   2010-04-19 10:00
 procedure b2ccontinuebook
 is
 xinshuiOrderId number(20);
 orderType  char(1);
 availableMoney number(20,2);--可用金额
 buyUserId  number(20);--购买人用户ID
 allMoney number(20,2);
 frozenMoney  number(20,2);
 endTime  varchar2(10);
 now varchar2(10):=to_char(sysdate,'yyyy-mm-dd');
 payStatus char(1);
 orderNo varchar2(100);
 txMoney number(20,2);
 txCaijin number(20,2);
 price number(20,2);
 status char(1);
 allCaijin number(20,2);
 dbException exception;
 begin
   for  v in (select t.xinshui_order_id,t.order_type,t.buy_user_id,t.end_time,t.product_id,t.sold_user_id,t.sold_username,
    t.buy_username,t.price
     from  t_xinshui_order t  where  t.end_time=
(select max(m.end_time) from t_xinshui_order m where m.buy_user_id=t.buy_user_id  group by m.buy_user_id)) loop
        
        price:=v.price;
        orderType:=v.order_type;
        buyUserId:=v.buy_user_id;
        endTime:=to_char(v.end_time,'yyyy-mm-dd');
        select  va.all_money,va.frozen_money,va.mosaic_gold  into allMoney,frozenMoney,allCaijin
        from t_virtual_account  va  where va.tx_user_id=buyUserId;--查询用户总金额，冻结金额
        availableMoney:=allMoney-frozenMoney;
        if now=endTime then
           if  availableMoney>=price then--账户金额充足 就直接扣款
               update  t_virtual_account  v2 set v2.all_money=v2.all_money-price  where v2.tx_user_id=buyUserId;
               payStatus:='3';
           
           insert into T_XINSHUI_ORDER(xinshui_order_id,type,PRODUCT_ID,BUY_USER_ID,START_TIME,END_TIME,STATUS,
           BUY_TIME,PAY_STATUS,ORDER_NO,SOLD_USER_ID,SOLD_USERNAME,BUY_USERNAME,TX_MONEY,TX_CAIJIN,PRICE,ORDER_TYPE)
           values(SEQ_XINSHUI_LOG_ID.Nextval,'2',v.product_id,v.buy_user_id,sysdate,to_date(endTime),status,
           sysdate,payStatus,orderNo,v.SOLD_USER_ID,v.SOLD_USERNAME,v.BUY_USERNAME,txMoney,txCaijin,price,v.ORDER_TYPE);
               --产生交易日志
               insert into T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,TX_MOSAIC_GOLDMONEY
               ,ORDER_ID,CATEGORY_TYPE,FLG,ORDER_NO,TX_TYPE_NAME,TARGET_USERID)values
               (SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,buyUserId,price,sysdate,'订购专家投资报告续费','4',allMoney,allCaijin,0.00,
               SEQ_XINSHUI_LOG_ID.Currval,'1','2',SEQ_ORDER_NO.NEXTVAL||'XS'||SEQ_ORDER_NO.NEXTVAL||to_char(DBMS_RANDOM.VALUE(100,999),'999'),
               '购买专家投资报告',v.sold_user_id
               );
               
           end if;
           if availableMoney >0 and availableMoney < price then
              update  t_virtual_account  v2 set v2.frozen_money=v2.frozen_money+price  
              where v2.tx_user_id=buyUserId;
           --产生冻结日志
           insert into T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY
          ,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG
         )values
           (SEQ_VA_FROZEN_LOG_ID.Nextval,buyUserId,availableMoney,sysdate,'购买专家投资报告续费',allMoney,allCaijin,0,00,
           SEQ_XINSHUI_LOG_ID.Currval,'10',SEQ_ORDER_NO.NEXTVAL||'XS'||SEQ_ORDER_NO.NEXTVAL||to_char(DBMS_RANDOM.VALUE(100,999),'999'),'1'
           );
           end if;
          
        end if;
     end loop;   
     --resultvalue:=1;
exception
  when others then
     --resultvalue:=-1;
     rollback;
     raise dbException;
 
 
 end;
--b2c继续付款
procedure   b2c_pay_continue(b2cOrderId in NUMBER,resultValue OUT NUMBER)
   is
   alFroMoney NUMBER(10,2);--已经冻结的金额
   alFroCaijin NUMBER(10,2);--已经冻结的彩金
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
   price NUMBER(20,2);--b2c订单价格
   maxEndTime  Date;
   orderType VARCHAR2(1);
   expertId number(20);--专家ID
   begin
       --首先查看专家投资报告订单表
       select a.tx_money,a.tx_caijin,a.buy_user_id,a.price,a.order_type,a.product_id
       into alFroMoney,alFroCaijin,buyUserId,price,orderType,expertId
       
       from  T_XINSHUI_ORDER  a  
       where a.xinshui_order_id=b2cOrderId;
        
       SELECT t.status,t.all_money,t.mosaic_gold ,t.frozen_money,t.frozen_mosaic_gold
       INTO account_status,money,caijin ,frozen_money,frozen_caijin
       FROM T_VIRTUAL_ACCOUNT t  WHERE  t.tx_user_id=buyUserId;
       --第一步判断账户状态
       IF account_status='2' THEN
           resultValue:=5;
        return;
       END IF;
       avaliable_money:=money-frozen_money+alFroMoney;--本次可用余额
       avaliable_caijin:=caijin-frozen_caijin+alFroCaijin;--本次可用彩金
     
       SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL; 
       orderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt;--订单号
       
       IF  avaliable_money+avaliable_caijin <price THEN
                 
                  resultValue:=4;
                  return;
        END IF;
       
       
        ---
        for cv in(select max(g.end_time) end_time from t_xinshui_order  g  where  g.buy_user_id=buyUserId  
                         and g.pay_status='3' and g.product_id=expertId) loop
          maxEndTime:=cv.end_time;
        end loop;
        --
        if maxEndTime>sysdate then
                if orderType='1' then
                  maxEndTime:=maxEndTime+7;
                elsif orderType='2' then
                  maxEndTime:=add_months(maxEndTime,1);
                elsif orderType='3' then
                  maxEndTime:=add_months(maxEndTime,3);
                elsif orderType='4' then
                  maxEndTime:=add_months(maxEndTime,12);
                end if;
          end if;
          
          if maxEndTime is null or maxEndTime<sysdate then
                if orderType='1' then
                  maxEndTime:=sysdate+7;
                elsif orderType='2' then
                  maxEndTime:=add_months(sysdate,1);
                elsif orderType='3' then
                  maxEndTime:=add_months(sysdate,3);
                elsif orderType='4' then
                  maxEndTime:=add_months(sysdate,12);
                end if;
          end if;
        
        --产生交易日志
        INSERT INTO  T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,MEMO,TX_TYPE,
        ALL_MONEY,MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG,TX_MOSAIC_GOLDMONEY)
        VALUES(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,buyUserId,price-alFroCaijin,sysdate,'购买专家心水',
        '6',money,caijin,b2cOrderId,'10',orderNO,'1',alFroCaijin);        
               
        update  T_XINSHUI_ORDER  t  set  t.pay_status='3',t.end_time=maxEndTime
        where  t.xinshui_order_id=b2cOrderId;
        
        update t_virtual_account  va  set va.all_money=va.all_money-price+alFroCaijin,va.frozen_money=va.frozen_money-alFroMoney,
        va.mosaic_gold=va.mosaic_gold-alFroCaijin,va.frozen_mosaic_gold=va.frozen_mosaic_gold-alFroCaijin
        
         where va.tx_user_id=buyUserId;
        resultValue:=1;
       
       exception
       when others then
         rollback;
       raise dbException;
     
   end;


--前台普通用户取消b2c心水订单2010-04-16 12:56
procedure   cancel_b2c_order( xinshuiOrderId in NUMBER,
                              resultValue OUT NUMBER)--返回值
is
txMoney number(20,2);
txCaijin  number(20,2);
buyUserId number(20);
ranInt  varchar2(6);
seqOrderNo varchar2(50);
orderNO varchar2(100);
dbException exception;
begin
--查询出已经冻结的金额  彩金
   select  a.tx_money,a.tx_caijin,a.buy_user_id  into txMoney,txCaijin,buyUserId  from  t_xinshui_order a
   where a.xinshui_order_id=xinshuiOrderId;
--改变订单状态为 已取消
   update t_xinshui_order b  set  b.pay_status='5' where b.xinshui_order_id=xinshuiOrderId;
   
--解冻
   update t_virtual_account  va  set va.frozen_money=va.frozen_money-txMoney,
   va.frozen_mosaic_gold=va.frozen_mosaic_gold-txCaijin
   where va.tx_user_id=buyUserId;
--产生解冻日志
 SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,
 seqOrderNo FROM DUAL; 
 orderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt;--产生订单号
 INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,
 ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
  values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,txMoney,sysdate,'取消心水','6',xinshuiOrderId,'10',orderNO,'2');
resultValue:=1;
exception 
when others then
         rollback;
         resultValue:=-1;
       raise dbException;
end;

--前台用户支付,普通用户第一次支付
--返回值:
--5:账户已经被锁定
procedure   b2c_pay_at_first(i_expertId in NUMBER,--专家ID
                                buyUserId in NUMBER ,--购买人用户ID
                                useCaijin in char,--是否使用彩金
                                resultValue OUT NUMBER,--返回值
                                buyUserName in varchar2,
                                orderType varchar2--1.包周  2.包月 3.包季 4. 包年
                                )
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
   expertName VARCHAR(90);--特约专家用户名
   expertUserId  NUMBER(20);--特约专家用户ID
   xinshui_price  NUMBER(20,2);
   dbException exception;
   v_sql varchar2(500);
   ranInt2 varchar2(6);
   frozenOrderNO varchar2(50);--冻结表订单
   endTime Date:=sysdate;--截止日期
   begin
      SELECT t.status,t.all_money,t.mosaic_gold ,t.frozen_money,t.frozen_mosaic_gold
      INTO account_status,money,caijin ,frozen_money,frozen_caijin
      FROM T_VIRTUAL_ACCOUNT t  WHERE  t.tx_user_id=buyUserId;
      --第一步判断账户状态
      IF account_status='2' THEN
          resultValue:=5;
        return;
      END IF;
      avaliable_money:=money-frozen_money;--本次可用余额
      avaliable_caijin:=caijin-frozen_caijin;--本次可用彩金
      IF useCaijin='1' THEN
        txCaijin:=avaliable_caijin;--如果使用彩金 则交易彩金即为可用彩金
      END IF;
     v_sql:='select ';
     if orderType='1' then
       v_sql:=v_sql||' e.week_pack ';
     elsif orderType='2' then
       v_sql:=v_sql||' e.month_pack ';
     elsif orderType='3' then
        v_sql:=v_sql||' e.season_pack ';
     elsif orderType='4' then
      v_sql:=v_sql||' e.year_pack ';
     end if;
     v_sql:=v_sql||',e.user_id,e.expert_name from t_expert e where e.expert_id=:1 ';
     dbms_output.put_line(v_sql);
      dbms_output.put_line('i_expertId='||i_expertId);
     execute immediate v_sql into xinshui_price,expertUserId,expertName using i_expertId;
     
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
            INSERT INTO T_XINSHUI_ORDER(XINSHUI_ORDER_ID,TYPE,PRODUCT_ID,
            BUY_USER_ID,STATUS,BUY_TIME,Pay_Status,ORDER_NO,
            SOLD_USER_ID,SOLD_USERNAME,BUY_USERNAME,TX_MONEY,TX_CAIJIN,PRICE,ORDER_TYPE)
          values(xinshuiOrderId,'2',i_expertId
            ,buyUserId,'1',sysdate,'1',orderNO,
            expertUserId,expertName,buyUserName,avaliable_money,avaliable_caijin,xinshui_price,orderType);
          --产生冻结日志
          INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
          values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,avaliable_money,sysdate,'购买民间高手心水','6',money,caijin,avaliable_caijin,xinshuiOrderId,'11',orderNO,'1');
                --dbms_output.put_line('购买C2C心水，同时冻结彩金与金额');
          update  T_XINSHUI_ORDER  t  set  t.pay_status='1'  where  t.xinshui_order_id=xinshuiOrderId;
               resultValue:=4;--余额不足
            return;
        ELSE
          IF avaliable_caijin>xinshui_price THEN
              txCaijin:=xinshui_price;
              txMoney:=0;
          ELSE
              txCaijin:=avaliable_caijin;
              txMoney:=xinshui_price-avaliable_caijin;
          END IF;
          --既从彩金里扣款 又从金额里扣款
          UPDATE  T_VIRTUAL_ACCOUNT va set va.Mosaic_Gold=va.Mosaic_Gold-txCaijin,
          va.All_Money=va.All_Money-txMoney where va.tx_user_id=buyUserId;
          --
          for p in(select dd.end_time from  t_xinshui_order  dd  where  dd.buy_user_id=buyUserId  and dd.product_id=i_expertId  and dd.pay_status='3') loop
             if p.end_time is not null then
                 endTime:=p.end_time;
             end if;
          end loop;
          if endTime>sysdate then
                if orderType='1' then
                  endTime:=endTime+7;
                elsif orderType='2' then
                  endTime:=add_months(endTime,1);
                elsif orderType='3' then
                  endTime:=add_months(endTime,3);
                elsif orderType='4' then
                  endTime:=add_months(endTime,12);
                end if;
          end if;
          if endTime is null or endTime<sysdate then
              if orderType='1' then
                  endTime:=endTime+7;
                elsif orderType='2' then
                  endTime:=add_months(endTime,1);
                elsif orderType='3' then
                  endTime:=add_months(endTime,3);
                elsif orderType='4' then
                  endTime:=add_months(endTime,12);
                end if;
          end if;
          --插入b2c订单
          INSERT INTO T_XINSHUI_ORDER
          (XINSHUI_ORDER_ID,TYPE,PRODUCT_ID,BUY_USER_ID,
          STATUS,BUY_TIME,Pay_Status,ORDER_NO ,
          SOLD_USER_ID,SOLD_USERNAME,BUY_USERNAME,
          TX_MONEY,TX_CAIJIN,PRICE,ORDER_TYPE,END_TIME)
          values(xinshuiOrderId,'2',i_expertId,buyUserId,'1',sysdate,'3',orderNO,
          expertUserId,expertName,buyUserName,txMoney,txCaijin,xinshui_price,orderType,endTime);
          --产生交易日志
          INSERT INTO T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,TX_TYPE,TX_MOSAIC_GOLDMONEY,ORDER_ID,
                                           CATEGORY_TYPE,FLG,ORDER_NO,TX_TYPE_NAME,TARGET_USERID,ALL_MONEY,MOSAIC_GOLDMONEY)
          values(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,buyUserId,txMoney,sysdate,'6',txCaijin,xinshuiOrderId,'10','1',orderNO,
          '购买专家投注报告',expertUserId,money,caijin);
                --dbms_output.put_line('购买C2C心水，同时冻结彩金与金额');
              resultValue:=1;
           END IF;
     END IF;
     IF useCaijin='2' THEN
        IF  avaliable_money<xinshui_price THEN--不使用彩金时 金额 不足以本次支付
         --dbms_output.put_line('----余额不足');
         -----此时应该冻结所有金额 包括 彩金
         UPDATE  T_VIRTUAL_ACCOUNT va set va.all_money=va.all_money where va.tx_user_id=buyUserId; --既从彩金里冻结 又从金额里冻结
         --产生订单
         INSERT INTO T_XINSHUI_ORDER(
         XINSHUI_ORDER_ID,TYPE,PRODUCT_ID,BUY_USER_ID,
         STATUS,BUY_TIME,Pay_Status,
         SOLD_USER_ID,SOLD_USERNAME,
         Buy_Username,TX_MONEY,TX_CAIJIN,ORDER_NO,ORDER_TYPE,PRICE)
         values(xinshuiOrderId,'2',i_expertId,buyUserId,
         '1',sysdate,'1' ,expertUserId,expertName,buyUserName,avaliable_money,0.00,orderNO,orderType,xinshui_price);
          --产生冻结日志
         INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
         values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,buyUserId,avaliable_money,sysdate,'购买专家心水','6',money,caijin,0.00,xinshuiOrderId,'10',frozenOrderNO,'1');
         resultValue:=4;--余额不足
         return;
                
        ELSE
        txCaijin:=0.00;
        txMoney:=xinshui_price;
          --扣除金额
          UPDATE  T_VIRTUAL_ACCOUNT va set va.all_money=va.all_money-txMoney where va.tx_user_id=buyUserId;
            for p in(select dd.end_time from  t_xinshui_order  dd  where  dd.buy_user_id=buyUserId  and dd.product_id=i_expertId  and dd.pay_status='3') loop
             if p.end_time is not null then
                 endTime:=p.end_time;
             end if;
          end loop;
          if endTime>sysdate then
                if orderType='1' then
                  endTime:=endTime+15;
                elsif orderType='2' then
                  endTime:=add_months(endTime,1);
                elsif orderType='3' then
                  endTime:=add_months(endTime,3);
                elsif orderType='4' then
                  endTime:=add_months(endTime,12);
                end if;
          end if;
          
          if endTime is null or endTime<sysdate then
              if orderType='1' then
                  endTime:=endTime+7;
                elsif orderType='2' then
                  endTime:=add_months(endTime,1);
                elsif orderType='3' then
                  endTime:=add_months(endTime,3);
                elsif orderType='4' then
                  endTime:=add_months(endTime,12);
                end if;
          end if;
          
        INSERT INTO T_XINSHUI_ORDER
          (XINSHUI_ORDER_ID,TYPE,PRODUCT_ID,BUY_USER_ID,
          STATUS,BUY_TIME,Pay_Status,ORDER_NO ,
          SOLD_USER_ID,SOLD_USERNAME,BUY_USERNAME,
          TX_MONEY,TX_CAIJIN,PRICE,ORDER_TYPE,END_TIME)
          values(xinshuiOrderId,'2',i_expertId,buyUserId,'1',sysdate,'3',orderNO,
          expertUserId,expertName,buyUserName,txMoney,txCaijin,xinshui_price,orderType,endTime);
          
          INSERT INTO T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,TX_TYPE,TX_MOSAIC_GOLDMONEY,ORDER_ID,
                                           CATEGORY_TYPE,FLG,ORDER_NO,TX_TYPE_NAME,TARGET_USERID,ALL_MONEY,MOSAIC_GOLDMONEY)
          values(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,buyUserId,txMoney,sysdate,'6',txCaijin,xinshuiOrderId,'10','1',orderNO,
          '购买专家投注报告',expertUserId,money,caijin);
        
          
          resultValue:=1;
       END IF;
     END IF;
     --exception
       --when others then
        --resultValue:=-1;
        --rollback;
        --raise dbException;
     
   end;
end B2C_PAY;
