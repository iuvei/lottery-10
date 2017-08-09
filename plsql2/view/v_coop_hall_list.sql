create or replace view v_coop_hall_list as
select a.bet_id,a.phase_id,a.bet_username,a.all_money,a.divide_copys,a.already_buy_copys,u.bet_military
 ,u.title,a.floor_copys ,a.order_status,a.bet_categoty,a.end_time,a.phase_no,
 (a.subscribe_money+a.floor_copys*a.single_money)*100/a.all_money progress,--置顶百分比
 a.already_buy_copys*a.single_money*100/a.all_money allProgress--总进度
 ,a.floor_copys*a.single_money*100/a.all_money floor_percentage--保底百分比
 from t_bet_order a,t_user u  where  a.bet_userid=u.userid  and (a.type='2' or a.type='4')
 and a.already_buy_copys<a.divide_copys

