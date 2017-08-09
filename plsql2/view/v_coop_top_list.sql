create or replace view v_coop_top_list as
select "BET_ID","PHASE_ID","BET_USERNAME","ALL_MONEY","DIVIDE_COPYS","ALREADY_BUY_COPYS","BET_MILITARY","TITLE","FLOOR_COPYS","ORDER_STATUS","BET_CATEGOTY","END_TIME","PHASE_NO","PROGRESS","ALLPROGRESS","FLOOR_PERCENTAGE"  from v_coop_hall_list t1 where  t1.bet_categoty='1' and
  t1.bet_id in
 (select min(b.bet_id) from v_coop_hall_list b

group by b.bet_username)

