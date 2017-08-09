create or replace view v_coop_top14_list as
select "BET_ID","PHASE_ID","BET_USERNAME","ALL_MONEY","DIVIDE_COPYS","ALREADY_BUY_COPYS","BET_MILITARY","TITLE","FLOOR_COPYS","ORDER_STATUS","BET_CATEGOTY","END_TIME","PHASE_NO","PROGRESS","ALLPROGRESS","FLOOR_PERCENTAGE"  from(
select "BET_ID","PHASE_ID","BET_USERNAME","ALL_MONEY","DIVIDE_COPYS","ALREADY_BUY_COPYS","BET_MILITARY","TITLE","FLOOR_COPYS","ORDER_STATUS","BET_CATEGOTY","END_TIME","PHASE_NO","PROGRESS","ALLPROGRESS","FLOOR_PERCENTAGE"  from v_coop_hall_list t1 where  t1.bet_categoty='1' and
  t1.bet_id in
 (select min(b.bet_id) from v_coop_hall_list b

group by b.bet_username)  and t1.bet_categoty='1' and t1.all_money>=1000 and t1.progress>=30
order by t1.progress desc,t1.all_money desc
)where rownum<=15

