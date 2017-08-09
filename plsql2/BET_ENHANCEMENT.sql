create or replace package body BET_ENHANCEMENT is
--普通用户定制自动跟单  2010-05-06 15:38
procedure saveMyAutoOrder(
i_kingId in number,--关联T_KING_SPONSOR的主键
i_userid in number,--自动跟单定制人用户ID
i_minMoney in number,--金额最小值
i_maxMoney in number,--金额最大值
i_txMoney in number,
i_username in varchar2,--定制人用户名
i_type in varchar2,--投注方式  '1':单式  '2':复式
i_isLackOrder in varchar2,--认购金额不足是否认购
i_kingUserId in number,--金牌发起人用户ID
i_category in varchar2,
i_reult out number--返回结果
)
is
isAllowDZ  number(2):=0;
alreadyDzCnt  number(10):=0;
dbException exception;
dzCnt number(10):=300;
isAlreadyDZ number(2):=0;--用户是否已经定制过
begin
 for v in(select t.already_dz_cnt,dz_cnt from t_king_sponsor t 
 where t.id=i_kingId and t.gd_status='1'  and t.already_dz_cnt<t.dz_cnt) loop
   alreadyDzCnt:=v.already_dz_cnt;
   dzCnt:=v.dz_cnt;
 end loop;
 
 if alreadyDzCnt is null or alreadyDzCnt=0 then
       i_reult:=2;--已经定制满员 不能再定制
    return;
 elsif alreadyDzCnt>0 then
   --判断用户是否已经定制过该金牌发起人的彩种以及玩法
   select count(1) cnt into isAlreadyDZ from t_my_auto_order u where u.king_userid=i_kingUserId and u.category=i_category
   and u.type=i_type and u.userid=i_userid;
   if isAlreadyDZ>0 then
         i_reult:=3;--已经定制过该金牌发起人的彩种,玩法
      return;
   end if;
   alreadyDzCnt:=alreadyDzCnt+1;
   insert into t_my_auto_order(AUTO_ORDER_ID,KING_ID,userid,order_time,min_money,max_money,tx_money,
   username,type,order_num,is_lack_order,category,king_userid) values
   (SEQ_MY_AUTO_ORDER_ID.NEXTVAL,i_kingId,i_userid,sysdate,i_minMoney,i_maxMoney,i_txMoney,
   i_username,i_type,alreadyDzCnt,i_isLackOrder,i_category,i_kingUserId
   );
 end if;
 
 if alreadyDzCnt=dzCnt then
    update t_king_sponsor m set m.gd_status='2' ,m.already_dz_cnt=m.dz_cnt where m.id=i_kingId;
 else
    update t_king_sponsor m set m.already_dz_cnt=m.already_dz_cnt+1 where m.id=i_kingId;
 end if;
  i_reult:=1;
--exception
--when others then
 --raise dbException;
end;

--撤单   
--返回值:   1.撤单成功  2.进度超度50%不能撤单  4 非法操作    -1报错
procedure cancelSpOrder(i_userId  in number,
i_bet_orderId  in number,
retValue out number
)
is
alreadyBuyCopys NUMBER(20);
divideCopys  NUMBER(20);
spUserId number(20);--发起人
subscribeMoney  NUMBER(20,2);
parti_UserId NUMBER(20);
floorMoney NUMBER(20,2);
parti_BETID NUMBER(20);
categoryType VARCHAR2(5);
betCategory  VARCHAR2(5);
orderNO varchar2(100);

ranInt varchar2(10);
begin
select  t.already_buy_copys,t.divide_copys,t.bet_userid,t.subscribe_money,t.floor_copys*t.single_money,t.bet_categoty
into alreadyBuyCopys,divideCopys,spUserId,subscribeMoney,floorMoney,betCategory
from  t_bet_order t where t.bet_id=i_bet_orderId and t.sponsor_type='1';

if spUserId<>i_userId then
 retValue:=3;
   return;
end if;

if alreadyBuyCopys*2>=divideCopys then
   retValue:=2;
   return;
end if;
if betCategory='1' or betCategory='2' then
betCategory:='6';
end if;
if betCategory='3' then
betCategory:='9';
end if;
if betCategory='5' then
betCategory:='8';
end if;
if instr(betCategory,'6')=1 then
betCategory:='1';
end if;

update t_bet_order m set m.order_status='4'  where m.bet_id=i_bet_orderId;
--解冻发起人的保证金与金额或者
update  t_virtual_account  tm   set tm.frozen_money=tm.frozen_money-floorMoney-subscribeMoney  where tm.tx_user_id=spUserId;
--
SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,orderNO FROM DUAL; 
orderNO:=to_char(sysdate,'yyyymmdd')||'ZC'||orderNO||ranInt;--产生订单号
INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,
   TX_TYPE,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,
   ORDER_NO,FLG)
   VALUES(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,spUserId,floorMoney+subscribeMoney,sysdate,'发起人撤单'
   ,'4',0.00,i_bet_orderId,
   betCategory,orderNO,'2');
--遍历参与人订单  然后分别为他们解冻
for v in(select tb.subscribe_money,tb.bet_userid,tb.bet_id from t_bet_order  tb  where tb.sponsor_bet_id=i_bet_orderId) loop
   subscribeMoney:=v.subscribe_money;
   parti_UserId:=v.bet_userid;
   parti_BETID:=v.bet_id;
   update t_virtual_account  tva  set tva.frozen_money=tva.frozen_money-subscribeMoney
   where  tva.tx_user_id=parti_UserId;
   --
  
   SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,orderNO FROM DUAL; 
  
   orderNO:=to_char(sysdate,'yyyymmdd')||'ZC'||orderNO||ranInt;--产生订单号
   --
   INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,
   TX_TYPE,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,
   ORDER_NO,FLG)
   VALUES(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,parti_UserId,subscribeMoney,sysdate,'发起人撤单'
   ,'4',0.00,parti_BETID,
   betCategory,orderNO,'2');
end loop;
retValue:=1;






end;
--保底   返回 4为非法操作   返回1 正常保底  2 保底份数太少 3保底份数太多
procedure floorBetOrder(
i_userId in number,
i_betId  in number,
i_flooCopys in number,
retValue out  number
)
is
betUserId number(20);
divideCopys number(20);
alreadyBuyCopys number(20);
f_copys number(20):=i_flooCopys;
singleMoney number(20,2);
ranInt varchar2(50);
orderNO varchar2(50);
betCategory varchar2(2);
categoryType varchar2(2);
begin
 select a.bet_userid,a.divide_copys,a.already_buy_copys,a.single_money,a.bet_categoty into betUserId,divideCopys,alreadyBuyCopys,singleMoney
 ,betCategory
 from t_bet_order  a where a.bet_id=i_betId and a.sponsor_type='1';
 
 if betUserId is null then
   retValue:=4;
    return;
 end if;
 if betUserId<>i_userId then--非法用户
     retValue:=4;
     return;
 end if;
 --if i_flooCopys>=divideCopys then--不许超过总份数
     --retValue:=3;
      --return;
 --end if;
 if alreadyBuyCopys>=divideCopys*0.8 then
    if i_flooCopys<(divideCopys-alreadyBuyCopys) then
       retValue:=2;--保底份数太少
        return;
    --else
       --f_copys:=divideCopys-alreadyBuyCopys;
    end if;
 end if;
 
 if alreadyBuyCopys<divideCopys*0.8 then
    if i_flooCopys<divideCopys*0.2 then
       retValue:=2;--保底份数太少
        return;
    else
       if i_flooCopys<(divideCopys-alreadyBuyCopys) then
          --f_copys:=divideCopys-alreadyBuyCopys;
       
           f_copys:=i_flooCopys;
       end if;
    end if;
 end if;
 update  t_bet_order  t  set t.floor_copys=f_copys where  t.bet_id=i_betId;
 --
 update  t_virtual_account  tm   set tm.frozen_money=tm.frozen_money+singleMoney*f_copys  where tm.tx_user_id=betUserId;
--
SELECT  to_char(DBMS_RANDOM.VALUE(100,999),'999'),SEQ_ORDER_NO.NEXTVAL into ranInt,orderNO FROM DUAL; 
orderNO:=to_char(sysdate,'yyyymmdd')||'ZC'||orderNO||ranInt;--产生订单号
if instr(betCategory,'6')>0 then
  categoryType:='1';
end if; 
if betCategory='1' then
  categoryType:='6';
end if;
if  betCategory='2' then
  categoryType:='7';
end if;

if  betCategory='3' then
  categoryType:='9';
end if;

if  betCategory='5' then
  categoryType:='8';
end if;

INSERT INTO T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,
   TX_TYPE,FROZEN_MOSAIC_GOLDMONEY,ORDER_ID,CATEGORY_TYPE,
   ORDER_NO,FLG)
   VALUES(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,i_userId,singleMoney*f_copys,sysdate,'发起人撤单'
   ,'4',0.00,i_betId,
   categoryType,orderNO,'2');
 --
 retValue:=1;
end;
end BET_ENHANCEMENT;
