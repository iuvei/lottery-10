create or replace package body history_military is
--7天合买历史战绩
procedure findHisMiliList(i_userId in number,bonusList out List_CUR)
is
begin
  null;
end;



procedure loadHistoryMilitary(i_userId in number,bonusList out varchar2)
is
spZJCnt number(20):=0;--发单中奖次数
allMoney  number(20,2):=0.00;--总奖金
begin
   bonusList:='';
     --胜负彩14场
   select count(1) zj_cnt,sum(t.money) all_bonus  into spZJCnt,allMoney
   from  t_bonus t  where t.user_id=i_userId and t.lottery_type='1' and t.flg='2';
   if allMoney is null then
     allMoney:=0;
   end if;
   bonusList:=bonusList||spZJCnt||','||allMoney||',';
   --任选9场
   select count(1) zj_cnt,sum(t.money) all_bonus  into spZJCnt,allMoney
   from  t_bonus t  where t.user_id=i_userId and t.lottery_type='2' and t.flg='2';
    if allMoney is null then
     allMoney:=0;
   end if;
   bonusList:=bonusList||spZJCnt||','||allMoney||',';
    --足球单场
   select count(1) zj_cnt,sum(t.money) all_bonus  into spZJCnt,allMoney
   from  t_bonus t  where t.user_id=i_userId and   instr(t.lottery_type,'6')>0 and t.flg='2';
   if allMoney is null then
     allMoney:=0;
   end if;
   bonusList:=bonusList||spZJCnt||','||allMoney||',';
   
    --6场半全场
   select count(1) zj_cnt,sum(t.money) all_bonus  into spZJCnt,allMoney
   from  t_bonus t  where t.user_id=i_userId and  t.lottery_type='5' and t.flg='2';
   if allMoney is null then
     allMoney:=0;
   end if;
   bonusList:=bonusList||spZJCnt||','||allMoney||',';
   
   --4场进球
   select count(1) zj_cnt,sum(t.money) all_bonus  into spZJCnt,allMoney
   from  t_bonus t  where t.user_id=i_userId and  t.lottery_type='3' and t.flg='2';
   if allMoney is null then
     allMoney:=0;
   end if;
   bonusList:=bonusList||spZJCnt||','||allMoney||',';
   
   
   
end;
end history_military;
