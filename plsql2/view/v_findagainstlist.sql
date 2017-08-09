create or replace view v_findagainstlist as
select  a.RACE_NAME,to_char(a.START_TIME,'yy-mm-dd hh24:mi')  START_TIME,a.HOST_NAME,a.GUEST_NAME,to_char(b.DEADLINE,'yyyy-mm-dd hh24:mi') DEADLINE,a.ASIA_PEI,a.BIG_SMALL
,a.race_id,a.against_id
      from T_AGAINST a,T_XINSHUI_AGAINST b
	    where a.against_id=b.against_id
      and a.status='1'  and a.start_time>sysdate

