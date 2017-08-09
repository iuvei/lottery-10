create or replace package body B2C is
--保存B2C专家资料
procedure  saveB2CExcpert(i_introduction in CLOB,--1
i_contractTime in varchar2,--2
expertName in varchar2,--3
i_weekPack in number,--4
i_monthPack in number,--5
i_seasonPack in number,--6
i_yearPack in number,--7
expertClass in varchar2,--8
i_photo varchar2,--9
isValuePack in char,--10
i_status in char
)
IS
dbException exception;
BEGIN
insert into T_EXPERT(EXPERT_ID,INTRODUCTION,CONTRACT_TIME,EXPERT_NAME,WEEK_PACK,MONTH_PACK,SEASON_PACK,YEAR_PACK,G_CLASS,PHOTO,
IS_VALUE_PACK,EXPERT_CODE,STATUS)
values(SEQ_EXPERT_ID.NEXTVAL,i_introduction,to_date(i_contractTime,'yyyy-mm-dd'),expertName,
i_weekPack,i_monthPack,i_seasonPack,i_yearPack,expertClass,i_photo,isValuePack,'ZJ'||SEQ_EXPERT_NO.NEXTVAL,i_status
);
commit;
exception 
when others then
raise dbException;
END;
--专家首页 加载目标专家  以及知名专家推荐  比如英超内参  亚洲联赛内参   2010-04-15 11:08
 procedure   loadExpertHomeData(
 i_expertId in NUMBER,--1专家ID
 expertIntroduction OUT  VARCHAR2,--2专家介绍
 allB2C OUT NUMBER,--3内参总数
 PACK OUT VARCHAR2,--4包周 月 季 年 价格
 SPACE_famousExpertList OUT SPACE.List_CUR,
 o_userName out varchar2,
 skilledRace out varchar2,--擅长联赛
 photo out varchar2,--专家头像 add by hikin yao
 winRatio out NUMBER --专家胜率
 )--5
 is
 begin
   select t.introduction,(select count(*) from t_c2c_product p  where  p.tx_user_id=t.expert_id),
   t.week_pack||','||t.month_pack||','||t.season_pack||','||t.year_pack,t.expert_name,t.skilled_race,t.photo,t.win_ratio
   into expertIntroduction,allB2C,PACK,o_userName,skilledRace,photo,winRatio
   from t_expert t where t.expert_id=i_expertId;
   
   open SPACE_famousExpertList for  select t.introduction,(select count(*) from t_c2c_product p  where p.is_b2c='1' and  p.tx_user_id=t.expert_id) cnt,
   t.week_pack||','||t.month_pack||','||t.season_pack||','||t.year_pack  pack,t.photo,t.g_class,t.expert_name,t.expert_id -- add [t.photo,t.g_class,t.expert_name,t.expert_id] by hikin yao
   
   from t_expert t where (t.expert_id>i_expertId or t.expert_id<i_expertId) order by t.g_class asc;
   
   
 
 end;
 
 
 
 
 procedure   b2CXinshuiPay(b2cOrderId in NUMBER,result OUT VARCHAR2)
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
   price NUMBER(20,2);--b2c心水价格
   begin
       --首先查看心水订单表
       select a.tx_money,a.tx_caijin,a.buy_user_id,a.price into txMoney,txCaijin,buyUserId,price  from  T_XINSHUI_ORDER  a  
       where a.xinshui_order_id=b2cOrderId;
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
       --select FROZEN_MONEY,FROZEN_MOSAIC_GOLDMONEY into alreadyFrozenMoney,alreadyFrozenCaijin from T_VA_FROZEN_LOG  tb where  
       --tb.CATEGORY_TYPE='10' and tb.order_id=b2cOrderId;
       
       
       SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,seqOrderNo FROM DUAL; 
       orderNO:=to_char(sysdate,'yyyymmdd')||'XS'||seqOrderNo||ranInt;--订单号
       
       
       IF  avaliable_money+avaliable_caijin <(txMoney+txCaijin) THEN
                  --dbms_output.put_line('----余额不足');
                  result:='4';
                  return;
        END IF;
        --应该减去alreadyFrozenMoney  alreadyFrozenCaijin
        UPDATE  T_VIRTUAL_ACCOUNT va set va.all_money=va.all_money-price+txMoney+txCaijin
        where va.tx_user_id=buyUserId; --既从彩金里冻结 又从金额里冻结
        --产生交易日志
        INSERT INTO  T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,MEMO,TX_TYPE,ALL_MONEY,MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
        VALUES(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,buyUserId,price-txMoney-txCaijin,sysdate,'后台代购心水','6',money,caijin,b2cOrderId,'10',orderNO,'1');        
               
        
       update  T_XINSHUI_ORDER  t  set  t.pay_status='2'  where  t.xinshui_order_id=b2cOrderId;
       result:='1';
       
       --exception
       --when others then
        --rollback;
        --raise dbException;
     
   end;
end B2C;
