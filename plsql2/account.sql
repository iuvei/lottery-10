create or replace package body account is
   PROCEDURE  saveUserDonate(caijinId in number,auditReason in varchar2)
   is 
   userIdList TOOLS.table_list:= TOOLS.table_list();
   user_id number; --被赠送用户的用户ID
   tx_caijin number(20,2);--赠送彩金
   all_caijin number(20,2);--彩金余额
   user_list varchar2(3000);--用户名之间以,隔开
   username  t_user.username%TYPE;
   usernameList TOOLS.table_list;
   cursor c_user(p varchar2) is   
   select tb.userid  from t_user tb where tb.username = p;
   dbException exception;
   begin
   --更新T_CAIJIN_DONATE
   select  cd.money,cd.user_list  into  tx_caijin,user_list from  t_caijin_donate  cd where  cd.id=caijinId;
   update  t_caijin_donate  t set t.status='2' ,t.audit_reason=auditReason where t.id=caijinId;
   usernameList:=TOOLS.fn_split(user_list,',');--'abc,yu1'
   DBMS_OUTPUT.put_line(user_list);
   FOR v IN usernameList.FIRST .. usernameList.LAST LOOP
       --DBMS_OUTPUT.put_line (usernameList.NEXT(v));
       username:=usernameList(v);
          --查询用户ID
      -- DBMS_OUTPUT.put_line('26行'||username);
        open c_user(username);
        loop
          fetch c_user  into user_id;
          EXIT WHEN c_user%NOTFOUND; 
         
        end loop;
        close c_user;
        --更新虚拟账户
          update t_virtual_account t  set t.mosaic_gold=t.mosaic_gold+tx_caijin where t.tx_user_id=user_id;
     
       --查询彩金余额
         select tva.mosaic_gold into all_caijin    from  t_virtual_account tva where tva.tx_user_id=user_id;
     
         
         --插入用户-彩金赠送表
         insert into t_user_donate(UD_ID,DONATE_ID,USER_ID,ALL_CAIJIN) values(SEQ_USER_DONATE_ID.NEXTVAL,caijinId,user_id,all_caijin);
       
          
      

     
   end loop;
   
   exception 
   when others then
       rollback;
    raise dbException;
   
   


   
   
   end saveUserDonate;


----------------------------


PROCEDURE  pay(result out VARCHAR2,user_id in NUMBER,tx_money in NUMBER,type in char,useMosaicGold IN CHAR,order_id IN NUMBER)
is 
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
 BEGIN
   SELECT t.status,t.all_money,t.mosaic_gold ,t.frozen_money,t.frozen_mosaic_gold
   INTO account_status,money,caijin ,frozen_money,frozen_caijin
   FROM T_VIRTUAL_ACCOUNT t  WHERE  t.tx_user_id=user_id;
   dbms_output.put_line(money||'  '||caijin);
   --第一步判断账户状态
   IF account_status='2' THEN
       dbms_output.put_line('账户已经被锁定');
       result:='5';
      return;
   END IF;
   avaliable_money:=money-frozen_money;--本次可用余额
   avaliable_caijin:=caijin-frozen_caijin;--本次可用彩金
   --产生三位随即数
   SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL; 
   orderNO:=to_char(sysdate,'yyyymmdd')||seqOrderNo||ranInt;--订单号
   IF type in('1','2','3','4','5','6','7','8','9') THEN  --购买单式代购
       IF useMosaicGold is not null THEN --优先使用剩余彩金
            IF  avaliable_money+avaliable_caijin <tx_money THEN
                  dbms_output.put_line('----余额不足');
                  result:='4';
                  return;
            END IF;
            IF  tx_money<=avaliable_caijin THEN --剩余彩金足够本次交易
                UPDATE  T_VIRTUAL_ACCOUNT va set va.mosaic_gold=va.mosaic_gold-tx_money 
                where va.tx_user_id=user_id; --只从彩金字段里面扣除，无需从金额字段扣除
                INSERT INTO  T_VA_TRANSACTION_LOG(VAT_ID,TX_MONEY   ,TX_USER_ID,TX_DATE,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,TX_MOSAIC_GOLDMONEY,order_id)
                VALUES(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,0.00,user_id,   sysdate,type,   money,    caijin-tx_money, tx_money,order_id);
            ELSE
                UPDATE  T_VIRTUAL_ACCOUNT va set va.mosaic_gold=va.mosaic_gold-avaliable_caijin,
                va.all_money=va.all_money-tx_money+avaliable_caijin 
                where va.tx_user_id=user_id; --既从彩金里扣除 又从金额里扣除
                --新增交易日志
                INSERT INTO  T_VA_TRANSACTION_LOG(VAT_ID,TX_MONEY,TX_USER_ID,TX_DATE,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,TX_MOSAIC_GOLDMONEY,ORDER_ID)
                VALUES(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,TX_MONEY-avaliable_caijin,user_id,sysdate,type,money-tx_money+avaliable_caijin,caijin-avaliable_caijin,avaliable_caijin,order_id);
           END IF;
       ELSE --只使用金额,即使用户账户有彩金也不使用
            dbms_output.put_line('只使用金额............');
            IF  avaliable_money <tx_money THEN
                  dbms_output.put_line('金额余额不足......');
                  result:='4';
                  return;
            END IF;
                UPDATE  T_VIRTUAL_ACCOUNT va set 
                va.all_money=va.all_money-tx_money 
                where va.tx_user_id=user_id;--本次置扣除金额
                --新增交易日志
                INSERT INTO  T_VA_TRANSACTION_LOG(VAT_ID,TX_MONEY,TX_USER_ID,TX_DATE,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,TX_MOSAIC_GOLDMONEY,ORDER_ID)
                VALUES(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,TX_MONEY,user_id,sysdate,type,money-tx_money,caijin,0.00,order_id);
     END IF;
       
       
   END IF;
   IF type='15' THEN --买c2c心水冻结
      IF useMosaicGold is not null THEN --优先使用剩余彩金
            dbms_output.put_line('==优先使用彩金==');
            IF  avaliable_money+avaliable_caijin <tx_money THEN
                  dbms_output.put_line('----彩金余额不足');
                  result:='4';
                  return;
            END IF;
            IF  tx_money<=avaliable_caijin THEN --剩余彩金足够本次交易
                --先冻结彩金tx_money,彩金总量不变
                UPDATE  T_VIRTUAL_ACCOUNT va set va.FROZEN_MOSAIC_GOLD=va.FROZEN_MOSAIC_GOLD+tx_money
                where va.tx_user_id=user_id; --只从彩金字段里面冻结，无需从金额字段扣除
                INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
                values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,user_id,0.00,sysdate,null,'6',money,caijin,tx_money,order_id,'11',orderNO,'1');
                dbms_output.put_line('购买C2C心水，先冻结彩金');
            ELSE --彩金不足以本次支付,先冻结所可用彩金，然后再冻结金额
                UPDATE  T_VIRTUAL_ACCOUNT va set va.FROZEN_MOSAIC_GOLD=va.FROZEN_MOSAIC_GOLD+avaliable_caijin,--冻结所有可用彩金
                va.FROZEN_MONEY=va.FROZEN_MONEY+tx_money-avaliable_caijin 
                where va.tx_user_id=user_id; --既从彩金里冻结 又从金额里冻结
                --产生冻结日志
                INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
                values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,user_id,tx_money-avaliable_caijin,sysdate,null,'6',money,caijin,avaliable_caijin,order_id,'11',orderNO,'1');
                dbms_output.put_line('购买C2C心水，同时冻结彩金与金额');
            END IF;
       ELSE --只使用金额,即使用户账户有彩金也不使用
          
            dbms_output.put_line('只使用金额............');
            IF  avaliable_money <tx_money THEN
                  dbms_output.put_line('金额余额不足......');
                  result:='4';
                  return;
            END IF;
            --只冻结金额
                UPDATE  T_VIRTUAL_ACCOUNT va set 
                va.Frozen_Money=va.Frozen_Money+tx_money
                where va.tx_user_id=user_id; 
                --新增冻结日志
               INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
               values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,user_id,tx_money,sysdate,null,'6',money,caijin,0.00,order_id,'11',orderNO,'1');
               --插入心水订单T_XINSHUI_LOG
                
             
       
       END IF;
   END IF;
 
 


END pay;




























-----------------------------------
procedure Pagination(Pindex in number, --要显示的页数索引，从0开始
                       Psql in varchar2, --产生分页数据的查询语句
                       Psize in number, --每页显示记录数
                       Pcount out number, --返回的分页数
                       Prowcount out number, --返回的记录数
                       v_cur out list_cur --返回分页数据的游标
                       )
 is 
 
   -----
    v_sql VARCHAR2(1000);
    v_Pbegin number;
    v_Pend number;

  begin
     v_sql := 'select count(*) from (' || Psql || ')';
    execute immediate v_sql into Prowcount; --计算记录总数
    Pcount := ceil(Prowcount / Psize); --计算分页总数

    --显示任意页内容
    v_Pend := Pindex * Psize + Psize;
    v_Pbegin := v_Pend - Psize + 1;
  
    --Psql := 'select rownum as rn , t.* from pay_en_voucher t'; --要求必须包含rownum字段

    v_sql := 'select * from (' || Psql || ') where rn between ' || v_Pbegin || ' and ' || v_Pend;
             
    open v_cur for v_sql;
  end Pagination;

   
   
   
   
   
   
   
   
   
   
   
   





 
end account;
