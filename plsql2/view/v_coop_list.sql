create or replace view v_coop_list as
select a.bet_id,a.phase_id,a.type,a.plan_code,a.bet_username,a.vici_way,a.all_money,a.bet_multi,a.divide_copys,a.already_buy_copys,a.single_money
 ,u.bet_military,a.floor_copys ,a.order_status,a.wici_type,a.bet_categoty,a.security_type,a.end_time,a.txt_path,a.plan
 from t_bet_order a,t_user u  where  a.bet_userid=u.userid  and (a.type='2' or a.type='4')

