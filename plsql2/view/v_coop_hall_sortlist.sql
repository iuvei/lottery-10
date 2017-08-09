create or replace view v_coop_hall_sortlist as
select "BET_ID","PHASE_ID","BET_USERNAME","ALL_MONEY","DIVIDE_COPYS","ALREADY_BUY_COPYS","BET_MILITARY","TITLE","FLOOR_COPYS","ORDER_STATUS","BET_CATEGOTY","END_TIME","PHASE_NO","PROGRESS","ALLPROGRESS","FLOOR_PERCENTAGE"
    from v_coop_hall_list m  order by  m.progress desc,m.all_money desc

