create or replace package body wdlose is
--4场进球  2010-04-27 11:40
procedure bulletin4(KJNO out varchar2,bonusPool out number,money1 out number,sales out number,i_phaseNo in varchar2)
is
begin
bonusPool:=0;
money1:=0;
KJNO:='';
sales:=0;
for v in(select  g.bet_num1,g.money1,g.kj_no,g.phase,g.sales,g.bonus_pool from t_bonus_gov  g where g.lottery_type='3' and g.phase=i_phaseNo
) loop
 bonusPool:=v.bonus_pool;
 money1:=v.money1;
 KJNO:=v.kj_no;
 sales:=v.sales;
end loop;

end;
--开奖期次列表
procedure  findPhaseList(
 i_category in varchar2,
 list out LOTTERY_INDEX.List_CUR
)
is
begin
  open list for 
  select B.phase  from ( select g.phase  from t_bonus_gov g where g.lottery_type=i_category  order by g.kj_time desc )B
  where rownum<=20;
end;
--网站首页:精选方案
procedure  findJingXuanPlanList(
phaseId in NUMBER,--期次ID
betCategory varchar2,--彩种
betList out LOTTERY_INDEX.List_CUR,--精选方案
deadline out varchar2--截止时间
)is
begin
  open betList for select a.bet_id,a.bet_username,c.bet_military,a.all_money,a.plan_code,
a.single_money,a.floor_copys*100/a.divide_copys floorPercentage,
  round((a.already_buy_copys*100)/a.divide_copys,0)  progress
      from t_bet_order a,t_user c  
      where a.bet_userid=c.userid and a.sponsor_type='1' 
      and a.end_time>sysdate and a.order_status='2'
      and a.is_hot=1   and a.already_buy_copys<a.divide_copys and a.phase_id=phaseId and a.bet_categoty=betCategory
      and rownum<=15;
 select  to_char(lp.mul_deadline,'yyyy-mm-dd hh24:mi') into deadline from t_lottery_phase lp where lp.id=phaseId;
end;
--半全场期次  2010-04-27 11:40
procedure bulletin6(KJNO out varchar2,bonusPool out number,money1 out number,sales out number,i_phaseNo in varchar2)
is
begin
bonusPool:=0;
money1:=0;
KJNO:='';
sales:=0;
for v in(select  g.bet_num1,g.money1,g.kj_no,g.phase,g.sales,g.bonus_pool from t_bonus_gov  g where g.lottery_type='5' and g.phase=i_phaseNo
) loop
 bonusPool:=v.bonus_pool;
 money1:=v.money1;
 KJNO:=v.kj_no;
 sales:=v.sales;
end loop;

end;

procedure bulletin14(ren9Result out varchar2,winlose14Result out varchar2,i_phaseNo in varchar2,
o_kjTime out varchar2,o_deadline out varchar2)
  is
  bonusPool varchar2(100);
  betNum1 varchar2(100);
  money1 number(20,2);
  betNum2 number(20);
  money2 number(20,2);
  kjNo varchar2(14);
  phaseNo varchar2(10);
  sales varchar2(100);
  
  begin
  --任选9场
  ren9Result:='';
  o_kjTime:='';
  o_deadline:='';
      for v in(select gov.kj_no,gov.bonus_pool,gov.sales,gov.bet_num1,gov.money1,gov.kj_time,gov.dead_line
      from t_bonus_gov gov  where gov.lottery_type='2' and gov.phase=i_phaseNo) loop
        if v.kj_no is not null then
           kjNo:=v.kj_no;
           bonusPool:=v.bonus_pool;
           sales:=v.sales;
           betNum1:=v.bet_num1;
           money1:=v.money1;
           o_kjTime:=to_char(v.kj_time,'yyyy-mm-dd');
           o_deadline:=to_char(v.dead_line,'yyyy-mm-dd');
        end if;
      end loop;
     ren9Result:=kjNo||','||bonusPool||','||sales||','||betNum1||'-'||money1;

--14场
winlose14Result:='';
for v in( select  g.bet_num1,g.money1,g.bet_num2,g.money2,g.kj_no,g.phase,g.sales
from t_bonus_gov  g where g.lottery_type='1' and g.phase=i_phaseNo) loop
if v.kj_no is not null then
 betNum1:=v.bet_num1;
 money1:=v.money1;
 betNum2:=v.bet_num2;
 money2:=v.money2;
 kjNo:=v.kj_no;
 phaseNo:=v.phase;
 sales:=v.sales;
end if;
end loop;
winlose14Result:=phaseNo||','||kjNo||','||betNum1||'-'||money1||','||betNum2||'-'||money2||','||sales;


                   

  end;
end wdlose;
