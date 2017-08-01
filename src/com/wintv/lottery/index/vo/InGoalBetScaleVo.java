package com.wintv.lottery.index.vo;



/**
 * 进球彩 投注比例
 * */
public class InGoalBetScaleVo implements Comparable<InGoalBetScaleVo>{
	
	private String host_team_name;
	private String guest_team_name;
	
//	private String bet_count_h_0;
//	private String bet_count_h_1;
//	private String bet_count_h_2;
//	private String bet_count_h_3;
//	
//	private String bet_count_g_0;
//	private String bet_count_g_1;
//	private String bet_count_g_2;
//	private String bet_count_g_3;
	
	private String bet_scale_h_0;
	private String bet_scale_h_1;
	private String bet_scale_h_2;
	private String bet_scale_h_3;
	
	private String bet_scale_g_0;
	private String bet_scale_g_1;
	private String bet_scale_g_2;
	private String bet_scale_g_3;
	
	private String host_in_goal;       //开奖结果，主队进球
	private String guest_in_goal;      //客队
	//private int[][][] bet_count;
	//private String[][][] bet_scale;
	
	public String getHost_team_name() {
		return host_team_name;
	}
	public void setHost_team_name(String host_team_name) {
		this.host_team_name = host_team_name;
	}
	public String getGuest_team_name() {
		return guest_team_name;
	}
	public void setGuest_team_name(String guest_team_name) {
		this.guest_team_name = guest_team_name;
	}
	
	public String getBet_scale_h_0() {
		return bet_scale_h_0;
	}
	public void setBet_scale_h_0(String bet_scale_h_0) {
		this.bet_scale_h_0 = bet_scale_h_0;
	}
	public String getBet_scale_h_1() {
		return bet_scale_h_1;
	}
	public void setBet_scale_h_1(String bet_scale_h_1) {
		this.bet_scale_h_1 = bet_scale_h_1;
	}
	public String getBet_scale_h_2() {
		return bet_scale_h_2;
	}
	public void setBet_scale_h_2(String bet_scale_h_2) {
		this.bet_scale_h_2 = bet_scale_h_2;
	}
	public String getBet_scale_h_3() {
		return bet_scale_h_3;
	}
	public void setBet_scale_h_3(String bet_scale_h_3) {
		this.bet_scale_h_3 = bet_scale_h_3;
	}
	public String getBet_scale_g_0() {
		return bet_scale_g_0;
	}
	public void setBet_scale_g_0(String bet_scale_g_0) {
		this.bet_scale_g_0 = bet_scale_g_0;
	}
	public String getBet_scale_g_1() {
		return bet_scale_g_1;
	}
	public void setBet_scale_g_1(String bet_scale_g_1) {
		this.bet_scale_g_1 = bet_scale_g_1;
	}
	public String getBet_scale_g_2() {
		return bet_scale_g_2;
	}
	public void setBet_scale_g_2(String bet_scale_g_2) {
		this.bet_scale_g_2 = bet_scale_g_2;
	}
	public String getBet_scale_g_3() {
		return bet_scale_g_3;
	}
	public void setBet_scale_g_3(String bet_scale_g_3) {
		this.bet_scale_g_3 = bet_scale_g_3;
	}
	public String getHost_in_goal() {
		return host_in_goal;
	}
	public void setHost_in_goal(String host_in_goal) {
		this.host_in_goal = host_in_goal;
	}
	public String getGuest_in_goal() {
		return guest_in_goal;
	}
	public void setGuest_in_goal(String guest_in_goal) {
		this.guest_in_goal = guest_in_goal;
	}
	@Override
	public int compareTo(InGoalBetScaleVo o) {
		Character c = this.host_in_goal.charAt(0);
		Float this_scale = 0f;
		Float cur_scale  = 0f;
		switch (c) {
		case '0':  this_scale = Float.valueOf(this.bet_scale_h_0); break;
		case '1':  this_scale = Float.valueOf(this.bet_scale_h_1); break;
		case '2':  this_scale = Float.valueOf(this.bet_scale_h_2); break;
		case '3':  this_scale = Float.valueOf(this.bet_scale_h_3);break;
		default: break;
		}
		c = o.getHost_in_goal().charAt(0);
		switch (c) {
		case '0': cur_scale  = Float.valueOf(o.bet_scale_h_0); break;
		case '1': cur_scale  = Float.valueOf(o.bet_scale_h_1); break;
		case '2': cur_scale  = Float.valueOf(o.bet_scale_h_2); break;
		case '3': cur_scale  = Float.valueOf(o.bet_scale_h_3); break;
		default: break;
		}
		
		return this_scale.compareTo(cur_scale);
	}
	
	
	
}
