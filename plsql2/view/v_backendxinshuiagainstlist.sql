create or replace view v_backendxinshuiagainstlist as
select a.AGAINST_ID,  b.PHASE ,a.RACE_NAME,a.ROUNDS_NAME,a.HOST_NAME,a.GUEST_NAME,a.RACE_SEASON_NAME,a.START_TIME,
 (select count(*)  from T_C2C_PRODUCT t where t.AGAINST_ID=a.AGAINST_ID and t.select_type='1' ) host_win ,
 (select count(*)  from T_C2C_PRODUCT t where t.AGAINST_ID=a.AGAINST_ID and t.select_type='2' ) guest_win ,

 (select count(*)  from T_C2C_PRODUCT t where t.AGAINST_ID=a.AGAINST_ID and t.select_type='3' ) big_ball,
 (select count(*)  from T_C2C_PRODUCT t where t.AGAINST_ID=a.AGAINST_ID and t.select_type='4' ) small_ball,

 b.DEADLINE,
 case
 when a.START_TIME < sysdate then  cast('已完赛' as varchar(60))
 when a.STATUS='3' then  cast('已取消' as varchar(60))
 when  a.START_TIME >sysdate then  cast('未完赛' as varchar(60))

  end MYSTATUS
,a.race_id,

a.race_season_id
   from  T_AGAINST a,T_XINSHUI_AGAINST b where   a.AGAINST_ID=b.AGAINST_ID

