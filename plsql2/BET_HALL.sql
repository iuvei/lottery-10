create or replace package body BET_HALL is
--足球单场 针对杨总的方法  2010-04-24 14:41
procedure listDanchangALL(
  resultList out List_CUR--返回列表
)
is
phaseNo varchar2(50):=null;
allMoney number(20,2):=0;--发起金额
floorPercentage number(10,2):=0;--（自购+保底）
begin
for v in(
select p.phase_no from t_lottery_phase p where p.sold_time<=sysdate and p.tkp_deadline>sysdate 
and p.status='4' and p.category='1') loop
   phaseNo:=v.phase_no;
end loop;
--如果当期期次存在
if phaseNo is not null then
open resultList for 
select B.*,decode(B.bet_categoty,'61','单场半全场','62','单场比分','63','单场让球胜平负','64','单场上下单双','65','单场总进球') betCategoryZH  from(
  select * from(
  select t1.*,'1' isTop  from v_coop_hall_list t1 where  instr(t1.bet_categoty,'1')>0 and 
  t1.progress>=30  and  t1.all_money>=200 and t1.phase_no=phaseNo and t1.already_buy_copys<t1.divide_copys
  union 
  select t2.*,'0' isTop  from v_coop_hall_list t2 where  
  instr(t2.bet_categoty,'6')>0 and t2.phase_no=phaseNo and t2.already_buy_copys<t2.divide_copys
  )A order by A.progress,A.all_money desc ) B where rownum<=15;
  dbms_output.put_line('phaseNo='||phaseNo);
 return;
end if; 
--查询预售期次的期次号
for v in(
select p.phase_no from t_lottery_phase p where p.sold_time>sysdate and p.tkp_deadline>sysdate 
and p.status='4' and p.category='1') loop
   phaseNo:=v.phase_no;
end loop;

if phaseNo is not null then
dbms_output.put_line('phaseNo='||phaseNo);
open resultList for 
 select B.*,decode(B.bet_categoty,'61','单场半全场','62','单场比分','63','单场让球胜平负','64','单场上下单双','65','单场总进球') betCategoryZH
  from(
  select * from(
  select *  from v_coop_hall_list t1 where  instr(t1.bet_categoty,'6')>0 and 
  t1.progress>=30  and  t1.all_money>=200 and t1.phase_no=phaseNo and t1.already_buy_copys<t1.divide_copys
  union 
  select *  from v_coop_hall_list t2 where  instr(t2.bet_categoty,'6')>0 and t2.phase_no=phaseNo  
  and t2.already_buy_copys<t2.divide_copys
  )A order by A.progress,A.all_money desc ) B  where rownum<=15;
 return;
end if; 
 
end;

--您在天彩的动态  2010-04-24 15;26
procedure listDynamicInfo(
  currentUserId in number,--用户ID
  --代购提醒
  unPayCnt out number,--未支付
  unKJCnt out number,--未开奖
  alreadyZJCnt out number,--已中奖
  --合买提醒
  spCnt  out number,--发起合买
  partiCnt  out number,--参与合买
  coopUnKJCnt out number,--未开奖
  coopAlreadyZJ out number,--已中奖
  --购买心水
  unPayXinshuiCnt out number,--购买心水： 未支付
  unStartXinshuiCnt out number,--未开赛心水
  ensureMoneyCnt out number,--保证金赔偿
  
  --销售心水
  successC2CSoldCnt out number,--成功销售
  soldC2CUnStartCnt out number,--未开赛心水
  soldHitCnt out number,--命中心水
  soldPCCnt out number,--保证金赔偿
 
  buyB2CCnt out number
)
is
begin
    unPayCnt:=0;--未支付
    for va in(select count(1) unPayCnt from t_bet_order a where a.order_status='1' and a.sponsor_type='0'
    and a.bet_userid=currentUserId) loop
        unPayCnt:=va.unPayCnt;
    end loop;
    --未开奖
    unKJCnt:=0;
    for vb in(select count(1) unKJCnt from t_bet_order b where b.zj_status='1' and b.sponsor_type='0'
    and b.bet_userid=currentUserId) loop
        unKJCnt:=vb.unKJCnt;
    end loop;
    --已中奖
    alreadyZJCnt:=0;
    for vc in(select count(1) alreadyZJCnt from t_bet_order c where c.zj_status='2' and c.sponsor_type='0'
    and c.bet_userid=currentUserId) loop
        alreadyZJCnt:=vc.alreadyzjcnt;
    end loop;
    --合买提醒： 
    --发起合买
    spCnt:=0;
    for vd in(select count(1) myspCnt from t_bet_order b where b.sponsor_type='1' and b.bet_userid=currentUserId) loop
        spCnt:=vd.myspCnt;
    end loop;
    --参与合买
    partiCnt:=0;
    for ve in(select count(1) mypartiCnt from t_bet_order b where (b.sponsor_type='1' or b.sponsor_type='2') and b.bet_userid=currentUserId) loop
        partiCnt:=ve.myparticnt;
    end loop;
    --未开奖
    coopUnKJCnt:=0;
    for vf in(select count(1) mycoopUnKJCnt from t_bet_order b where b.zj_status='3' and b.bet_userid=currentUserId) loop
        coopUnKJCnt:=vf.mycoopUnKJCnt;
    end loop;
    --已中奖
    coopAlreadyZJ:=0;
    for vg in(select count(1) mycoopAlreadyZJ from t_bet_order b where b.zj_status='2' and b.bet_userid=currentUserId) loop
        coopAlreadyZJ:=vg.mycoopAlreadyZJ;
    end loop;
    --购买心水： 未支付
    unPayXinshuiCnt:=0;
    for vx in(select count(1) myunPayXinshuiCnt  from t_xinshui_order  xo  where xo.buy_user_id=currentUserId and xo.pay_status='1') loop
        unPayXinshuiCnt:=vx.myunPayXinshuiCnt;
    end loop;
    --购买心水：未开赛心水
    unStartXinshuiCnt:=0;
    for vy in(select count(1) myunStartXinshuiCnt  from t_c2c_product  c 
    where  c.tx_user_id=currentUserId and c.race_start_time>sysdate) loop
        unStartXinshuiCnt:=vy.myunStartXinshuiCnt;
    end loop;
    --购买心水:保证金赔偿
    ensureMoneyCnt:=0;
    for vp in(select count(1) myensureMoneyCnt from  t_va_transaction_log tl  
    where tl.tx_user_id=currentUserId and tl.category_type='16'  and tl.flg='1')loop
        ensureMoneyCnt:=vp.myensureMoneyCnt;
    end loop;
    --销售心水： 成功销售
    successC2CSoldCnt:=0;
    for vp in(select count(1) mysuccessC2CSoldCnt from  t_xinshui_order xo where xo.sold_user_id=currentUserId
                 and xo.pay_status='3')loop
        successC2CSoldCnt:=vp.mysuccessC2CSoldCnt;
    end loop;
    --未开赛心水
    soldC2CUnStartCnt:=0;
    for vk in(select count(1) mySoldC2CUnStartCnt  from t_c2c_product  c2c 
    where c2c.tx_user_id=currentUserId and c2c.race_start_time>sysdate) loop
        soldC2CUnStartCnt:=vk.mySoldC2CUnStartCnt;
    end loop;
     --销售心水:命中心水
    soldHitCnt:=0;
    for vu in(select count(1) mySoldHitCnt  from t_c2c_product  c2c 
    where c2c.tx_user_id=currentUserId and  c2c.status='2') loop
        soldHitCnt:=vu.mySoldHitCnt;
    end loop;
    --保证金赔偿
    soldPCCnt:=0;
    for vw in(select count(1) mySoldPCCnt from  t_va_transaction_log tl  
    where tl.tx_user_id=currentUserId and tl.category_type='16'  and tl.flg='2')loop
        soldPCCnt:=vw.mySoldPCCnt;
    end loop;
    --专家包月： 成功购买
    buyB2CCnt:=0;
    for vn in (select count(1) myBuyB2CCnt from t_Xinshui_Order xo  where xo.buy_user_id=currentUserId and xo.pay_status='3') loop
      buyB2CCnt:=vn.mybuyb2ccnt;
    end loop;
    
    
end;

















--足球单场  2010-04-24 14:41
procedure listDanchang(
  betCategory in varchar2,--彩种
  resultList out List_CUR--返回列表
)
is
phaseNo varchar2(50):=null;
allMoney number(20,2):=0;--发起金额
floorPercentage number(10,2):=0;--（自购+保底）
begin

if   betCategory='62' then--单场比分
   allMoney:=300;
   floorPercentage:=30;
elsif betCategory='63' then --单场让球胜平负
   allMoney:=500;
   floorPercentage:=30;
elsif betCategory in ('61','64','65') then --单场上下单双,单场总进球,单场半全场
   allMoney:=200;
   floorPercentage:=30;
end if;

allMoney:=500;
floorPercentage:=30;

for v in(
select p.phase_no from t_lottery_phase p where p.sold_time<=sysdate and p.mul_deadline>sysdate
and p.status='4' and p.category='6') loop
   phaseNo:=v.phase_no;
end loop;

phaseNo := '2010038';

--如果当期期次存在
if phaseNo is not null then
open resultList for 
select * from(
  select * from(
  select t1.*,'1' isTop  from v_coop_hall_list t1 where  t1.bet_categoty=betCategory and 
  t1.progress>=floorPercentage  and  t1.all_money>=allMoney and t1.phase_no=phaseNo and t1.already_buy_copys<t1.divide_copys
  union 
  select t2.*,'0' isTop  from v_coop_hall_list t2 where  t2.bet_categoty=betCategory and t2.phase_no=phaseNo and t2.already_buy_copys<t2.divide_copys
  )A order by A.progress,A.all_money desc ) where rownum<=15;
 return;
end if; 
--查询预售期次的期次号
for v in(
select p.phase_no from t_lottery_phase p where p.sold_time>sysdate and p.mul_deadline>sysdate 
and p.status='4' and p.category='6') loop
   phaseNo:=v.phase_no;
end loop;

if phaseNo is not null then
open resultList for 
 select * from(
  select * from(
  select t1.*,'1' isTop  from v_coop_hall_list t1 where  t1.bet_categoty=betCategory and 
  t1.progress>=floorPercentage  and  t1.all_money>=allMoney and t1.phase_no=phaseNo and t1.already_buy_copys<t1.divide_copys
  union 
  select t2.*, '0' isTop  from v_coop_hall_list t2 where  t2.bet_categoty=betCategory and t2.phase_no=phaseNo  and t2.already_buy_copys<t2.divide_copys
  )A order by A.progress,A.all_money desc ) where rownum<=15;
 return;
end if; 
 
end;






--------------------------------------------------------
--胜负彩14场
procedure listWinLose14(
  resultList out List_CUR
)
is
phaseNo varchar2(50):=null;
begin
for v in(
select p.phase_no from t_lottery_phase p where p.sold_time<=sysdate and p.mul_deadline>sysdate 
and p.status='4' and p.category='6') loop
   phaseNo:=v.phase_no;
end loop;
--如果当期期次存在
if phaseNo is not null then
open resultList for 
   select * from (
   select n.*,'1' isTop from v_coop_top14_list n  where n.PHASE_NO=phaseNo and n.already_buy_copys<n.divide_copys
   union
     select h.*,'0' isTop from v_coop_hall_sortList h where  h.bet_categoty='1'  and h.PHASE_NO=phaseNo and h.already_buy_copys<h.divide_copys
   )
   where rownum<=15;
 return;
end if; 
--查询预售期次的期次号
for v in(
select p.phase_no from t_lottery_phase p where p.sold_time>sysdate and p.mul_deadline>sysdate 
and p.status='4' and p.category='6') loop
   phaseNo:=v.phase_no;
end loop;

if phaseNo is not null then
open resultList for 
 select * from (
   select n.*,'1' isTop  from v_coop_top14_list n  where n.PHASE_NO=phaseNo and n.already_buy_copys<n.divide_copys
   union
     select h.*,'0' isTop from v_coop_hall_sortList h where  h.bet_categoty='1'  and h.PHASE_NO=phaseNo and h.already_buy_copys<h.divide_copys
   )
   where rownum<=15;
 return;
end if; 
 
end;
-------------------------------------------------------------------------------
procedure listRen9(
  resultList out List_CUR
)
is
phaseNo varchar2(50):=null;
begin
for v in(
select p.phase_no from t_lottery_phase p where p.sold_time<=sysdate and p.mul_deadline>sysdate 
and p.status='4' and p.category='6') loop
   phaseNo:=v.phase_no;
end loop;
--如果当期期次存在
if phaseNo is not null then
open resultList for 
select * from(
  select * from(
  select t1.*,'1' isTop  from v_coop_hall_list t1 where  t1.bet_categoty='2' and 
  t1.progress>=30  and  t1.all_money>=800 and t1.phase_no=phaseNo  and t1.already_buy_copys<t1.divide_copys
  union 
  select t2.*,'0' isTop  from v_coop_hall_list t2 where  t2.bet_categoty='2' and t2.phase_no=phaseNo  and t2.already_buy_copys<t2.divide_copys
  )A order by A.progress,A.all_money desc ) where rownum<=15;
 return;
end if; 
--查询预售期次的期次号
for v in(
select p.phase_no from t_lottery_phase p where p.sold_time>sysdate and p.mul_deadline>sysdate 
and p.status='4' and p.category='6') loop
   phaseNo:=v.phase_no;
end loop;

if phaseNo is not null then
open resultList for 
 select * from(
  select * from(
  select t1.*,'1' isTop  from v_coop_hall_list t1 where  t1.bet_categoty='2' and 
  t1.progress>=30  and  t1.all_money>=800 and t1.phase_no=phaseNo and t1.already_buy_copys<t1.divide_copys
  union 
  select t2.*,'0' isTop  from v_coop_hall_list t2 where  t2.bet_categoty='2' and t2.phase_no=phaseNo  and t2.already_buy_copys<t2.divide_copys
  )A order by A.progress,A.all_money desc ) where rownum<=15;
 return;
end if; 
 
end;

end BET_HALL;
