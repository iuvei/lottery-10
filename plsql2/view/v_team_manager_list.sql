create or replace view v_team_manager_list as
select tt.host_name,tt.guest_name from
 ( select t.host_name,t.guest_name,t.race_season_id from t_against t where t.race_id = 61
    group by t.race_season_id,t.host_name,t.guest_name)tt
 group by tt.host_name,tt.guest_name

