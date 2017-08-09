create or replace view V_BET_RECORD_LIST as
select
	o.bet_id betId,
	o.bet_categoty betCategory,
	case
		when o.sponsor_bet_id is null then o.bet_id
		when o.sponsor_bet_id is not null then o.sponsor_bet_id
	end sponsorUserId,
	case
		when o.sponsor_bet_id is null then o.bet_username
		when o.sponsor_bet_id is not null then (select b1.bet_username from t_bet_order b1 where b1.bet_id = o.sponsor_bet_id)
	end sponsorUsername,
	case
		when o.sponsor_bet_id is null then o.all_money
		when o.sponsor_bet_id is not null then (select b2.all_money from t_bet_order b2 where b2.bet_id = o.sponsor_bet_id)
	end allMoney,
	case 
		when o.sponsor_bet_id is null then o.phase_no
		when o.sponsor_bet_id is not null then (select b3.phase_no from t_bet_order b3 where b3.bet_id = o.sponsor_bet_id)
	end phaseNo,
	o.subscribe_money subscribeMoney,
	case 
		when o.order_status = 1 then '未支付' 
		when o.order_status = 2 then '待出票' 
		when o.order_status = 3 then '已出票' 
		when o.order_status = 4 then '已取消' 
		when o.order_status = 5 then '已过期' 
	end orderStatus,
	case
		when o.type = 1 or o.type = 3 then '-'
		when o.order_status = 4 then '已撤单'
		when o.divide_copys = o.already_buy_copys then '满员'
		when o.divide_copys != o.already_buy_copys then '未满员'
	end planStatus,
	to_char(o.bet_time,'yy-mm-dd hh:mm') betTime,
	case
		when o.plan_code is not null then o.plan_code
		when o.plan_code is null then o.order_no
	end planCode
from
	t_bet_order o
where
	o.bet_userid = 83
and
	o.zj_status = 1
