create or replace view v_findbackendagainstlist as
select a.AGAINST_ID,  a.RACE_NAME, a.ROUNDS_NAME,a.RACE_SEASON_NAME,a.HOST_NAME,a.GUEST_NAME,a.START_TIME,
  a.ASIA_PEI,a.BIG_SMALL,case  when  (select c.AGAINST_ID  from t_xinshui_against c  where  c.AGAINST_ID=a.against_id) is not  null  and  a.START_TIME >sysdate  then '已选择'
  when   (select b.AGAINST_ID  from t_xinshui_against b  where  b.AGAINST_ID=a.against_id) is null  and  a.START_TIME >sysdate   then  '未选择'
  when  (select b.AGAINST_ID  from t_xinshui_against b  where  b.AGAINST_ID=a.against_id) is null  and  a.START_TIME < sysdate   then '已过期'
  when  (select c.AGAINST_ID  from t_xinshui_against c  where  c.AGAINST_ID=a.against_id) is not  null  and  a.START_TIME < sysdate   then '已完成'   end  status
  ,a.RACE_SEASON_ID
  ,a.ROUNDS
  ,a.RACE_ID
  from  T_AGAINST a  where   a.start_time > sysdate
  and not exists(select a.against_id  from  t_xinshui_against txa   where  txa.against_id=a.against_id)

