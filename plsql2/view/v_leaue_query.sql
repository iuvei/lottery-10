create or replace view v_leaue_query as
select t.parent_id,count(1) cnt from T_RACE t  where t.parent_id is not null
 group by  t.parent_id

