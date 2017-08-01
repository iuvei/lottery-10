package com.wintv.lottery.mycenter.vo;

/*******************************************************************************
 * 最新中奖
 * 
 * @author arix04 2010-04-26
 * 
 */

public class LatestWinnerVO {

	private Long userid;		//中奖人userid
	private String username;	//中奖人username
	private String lotteryName;	//彩种名
	private String money;		//奖金
	private String phaseNo;		//期次号

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPhaseNo() {
		return phaseNo;
	}

	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
}
