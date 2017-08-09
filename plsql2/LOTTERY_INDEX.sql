create or replace package body LOTTERY_INDEX is
--热门关键词 2010-04-23 09:26
procedure find_hot_keys(hotList out List_CUR)
is
begin
  open hotList for select t.username from t_bet_hot_search t  where t.status='1' and  rownum<=6;
end;


--网站首页
procedure leftData(
  beingSoldList out List_CUR,--足彩开售中
  kj_result  out varchar2,--最新开奖结果
  lastest_zj_list out List_CUR,--本站最新中奖
  zj_top10_14list out List_CUR,--总排行榜月(胜负彩14场)
  zj_top10_9list out List_CUR,--总排行榜月(任9场)
  zj_top10_6list out List_CUR,--总排行榜月(足球单场)
  
  coop_top10_14list out List_CUR,--合买排行榜(胜负彩14场)
  coop_top10_9list out List_CUR,--合买排行榜(任9场)
  coop_top10_6list out List_CUR--合买排行榜(足球单场)
  
  
)
is
betNum1 number(20):=0;
betNum2 number(20):=0;
money1 number(20,2):=0;
money2 number(20,2):=0;
kjNo  varchar2(200):='';
phaseNo varchar2(200):='';


begin
--足彩开售中
open beingSoldList for 
select decode(t.category,'6','胜负彩','9','进球彩','8','半全场','1','足球单场') cat,t.phase_no,
--t.category,decode(t.id,'6','1','9','3','8','5','1','61') phaseId,
t.category,t.id phaseId, -- Modify  by hikin
case 
 when t.category in ('6','9','8') then
    to_char(t.mul_deadline,'yy-mm hh24:mi')
 when t.category='1' then
    '开赛前15分钟'
end deadline
from t_lottery_phase t  
where t.tkp_deadline<sysdate and t.status='4';
--最新开奖结果
--胜负彩
for v in( select  g.bet_num1,g.money1,g.bet_num2,g.money2,g.kj_no,g.phase from t_bonus_gov  g where g.lottery_type='1' and g.kj_time
=(select max(k.kj_time)  from t_bonus_gov k  where k.lottery_type='1')) loop
 betNum1:=v.bet_num1;
 money1:=v.money1;
 betNum2:=v.bet_num2;
 money2:=v.money2;
 kjNo:=v.kj_no;
 phaseNo:=v.phase;
end loop;
kj_result:=phaseNo||','||kjNo||','||betNum1||'-'||money1||','||betNum2||'-'||money2||';';
--
--进球彩
for v in(select  g.bet_num1,g.money1,g.kj_no,g.phase from t_bonus_gov  g where g.lottery_type='3' and g.kj_time
=(select max(k.kj_time)  from t_bonus_gov k  where k.lottery_type='3')) loop
 betNum1:=v.bet_num1;
 money1:=v.money1;
 kjNo:=v.kj_no;
 phaseNo:=v.phase;
end loop;
kj_result:=kj_result||phaseNo||','||kjNo||','||betNum1||'-'||money1||';';

--   
--半全场期次
for v in(select  g.bet_num1,g.money1,g.kj_no,g.phase from t_bonus_gov  g where g.lottery_type='5' and g.kj_time
=(select max(k.kj_time)  from t_bonus_gov k  where k.lottery_type='5')) loop
 betNum1:=v.bet_num1;
 money1:=v.money1;
 kjNo:=v.kj_no;
 phaseNo:=v.phase;
end loop;
kj_result:=kj_result||phaseNo||','||kjNo||','||betNum1||'-'||money1;
--   
open lastest_zj_list for 
  select * from(
  select h3.username,h3.lottery_name,h3.phase,h3.money from t_bonus h3 order by h3.kj_time desc)where rownum<=10;

----
open zj_top10_14list for 
select A.money,A.username,A.user_id,
(select count(1) from t_king_sponsor ks where ks.userid=A.user_id and ks.bet_category='1' and ks.status='1') allowDZ
from (
select sum(t.money) money,t.username,t.user_id
from  t_bonus t  where  t.lottery_type='1'  
and to_char(t.kj_time,'yyyy-mm-dd')>=to_char(add_months(sysdate,-1),'yyyy-mm-dd')
group by t.username,t.user_id  order by sum(t.money) desc) A where rownum<=10;

open zj_top10_9list for 
select B.money,B.username,B.user_id,
(select count(1) from t_king_sponsor ks where ks.userid=B.user_id and ks.bet_category='2' and ks.status='1') allowDZ
from (
select sum(t.money) money,t.username,t.user_id
from  t_bonus t  where t.lottery_type='2'  
and to_char(t.kj_time,'yyyy-mm-dd')>=to_char(add_months(sysdate,-1),'yyyy-mm-dd')
group by t.username,t.user_id order by sum(t.money) desc) B where rownum<=10;


open zj_top10_6list for 
select C.money,C.username,C.user_id,
(select count(1) from t_king_sponsor ks where ks.userid=C.user_id and instr(ks.bet_category,'6')>0 and ks.status='1') allowDZ
 from (
select sum(t.money) money,t.username,t.user_id
from  t_bonus t  where  instr(t.lottery_type,'6')>0  
and to_char(t.kj_time,'yyyy-mm-dd')>=to_char(add_months(sysdate,-1),'yyyy-mm-dd')
group by t.username,t.user_id  order by sum(t.money) desc) C where rownum<=10;
--合买排行榜(胜负彩14场)
open coop_top10_14list for
select D.cnt,D.bet_username,D.bet_userid,
(select count(1) from t_king_sponsor ks where ks.userid=D.bet_userid and ks.bet_category='1' and ks.status='1') allowDZ
from (
select count(a.bet_id) cnt,b.bet_username,b.bet_userid  from  t_bet_order a,t_bet_order b 
where a.sponsor_bet_id=b.bet_id and a.sponsor_type='2'  and b.bet_categoty='1'
group by  b.bet_username,b.bet_userid order by count(a.bet_id) desc)D where rownum<=10;
--
open coop_top10_9list for
select E.cnt,E.bet_username,E.bet_userid,
(select count(1) from t_king_sponsor ks where ks.userid=E.bet_userid and ks.bet_category='2' and ks.status='1') allowDZ
from (
select count(a.bet_id) cnt,b.bet_username,b.bet_userid  from  t_bet_order a,t_bet_order b 
where a.sponsor_bet_id=b.bet_id and a.sponsor_type='2'  and b.bet_categoty='2'
group by  b.bet_username,b.bet_userid order by count(a.bet_id) desc) E where rownum<=10;
--
open coop_top10_6list for
select F.cnt,F.bet_username,F.bet_userid,
(select count(1) from t_king_sponsor ks where ks.userid=F.bet_userid and ks.bet_category='2' and ks.status='1') allowDZ
from (
select count(a.bet_id) cnt,b.bet_username,b.bet_userid  from  t_bet_order a,t_bet_order b 
where a.sponsor_bet_id=b.bet_id and a.sponsor_type='2'  and  instr(b.bet_categoty,'6')>0
group by  b.bet_username,b.bet_userid order by count(a.bet_id) desc) F where rownum<=10;




--

end;



--网站首页:合买方案 搜索
procedure  findSpPlanList(
betCategory in char,--玩法
phaseId in number,
minAllMoney  in number,
maxAllMoney  in number,
betList out List_CUR
)is
begin
  open betList for select a.bet_id,
  a.bet_username,c.bet_military,a.all_money,(a.divide_copys-a.already_buy_copys) surplusCopys,
  round((a.already_buy_copys*100)/a.divide_copys,0)  progress
      from t_bet_order a,t_user c  where a.bet_userid=c.userid 
      and a.sponsor_type='1' and a.is_hot=1 and a.bet_categoty=betCategory;
end;
--网站首页:精选方案
procedure  findJingXuanPlanList(
betCategory in char,--玩法
betList out List_CUR--精选方案
)is
begin
  open betList for select a.bet_id,
  a.bet_username,c.bet_military,a.all_money,(a.divide_copys-a.already_buy_copys) surplusCopys,
  round((a.already_buy_copys*100)/a.divide_copys,0)  progress
      from t_bet_order a,t_user c  
      where a.bet_userid=c.userid and a.sponsor_type='1' 
      and a.end_time>sysdate and a.order_status='2' and a.already_buy_copys<a.divide_copys
      and a.is_hot=1 and  instr(a.bet_categoty,betCategory)>0;
end;


--网站首页:名家足球推荐
procedure  findFamousExpertList(
  expertList out List_CUR
)is
begin
  open  expertList for
  select ep.expert_name,ep.introduction from  t_expert ep  where ep.type='1'  and rownum<=6;
end;


--民间高手推荐
procedure  findRecommendC2CList(
  c2cList out List_CUR
)
is
begin
open  c2cList for
select a.race_name,to_char(b.start_time,'yyyy-mm-dd hh24:mi') start_time,b.host_name,b.guest_name,a.ensure_money,
a.ensure_money/(select dic.value from t_dictionary dic  where  dic.code='MARC2C_GIN_VS_PRICE' and dic.type='XINSHUI') price,
(select count(1) from t_c2c_product c where c.tx_user_id=a.tx_user_id and a.status='2') trueXinshui
from t_c2c_product a,t_against b where a.against_id=b.against_id and a.is_recomend=1;

end;
end LOTTERY_INDEX;
