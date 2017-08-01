package com.wintv.lottery.wdlose.vo;

public class KjVO {
	private String kjNo;//开奖号码
	private String bonusPool;//奖池资金
	private String class1;//一等奖
	private String money1;//一等奖奖金
	private String class2;//二等奖
	private String money2;//二等奖奖金
	private String sales;//销量
	private String kjTime;//开奖日期
	private String deadline;//兑奖截止日期
	private long betNum1;//一等奖注数
	public String getKjNo() {
		return kjNo;
	}
	public void setKjNo(String kjNo) {
		this.kjNo = kjNo;
	}
	public String getBonusPool() {
		return bonusPool;
	}
	public void setBonusPool(String bonusPool) {
		this.bonusPool = bonusPool;
	}
	public String getClass1() {
		return class1;
	}
	public void setClass1(String class1) {
		this.class1 = class1;
	}
	public String getMoney1() {
		return money1;
	}
	public void setMoney1(String money1) {
		this.money1 = money1;
	}
	public String getClass2() {
		return class2;
	}
	public void setClass2(String class2) {
		this.class2 = class2;
	}
	public String getMoney2() {
		return money2;
	}
	public void setMoney2(String money2) {
		this.money2 = money2;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getKjTime() {
		return kjTime;
	}
	public void setKjTime(String kjTime) {
		this.kjTime = kjTime;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public long getBetNum1() {
		return betNum1;
	}
	public void setBetNum1(long betNum1) {
		this.betNum1 = betNum1;
	}

}
