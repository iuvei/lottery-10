package com.wintv.lottery.admin.bet.vo;

import java.io.Serializable;
/**
 * 国家体彩中心提供的中奖情况(并非只本站用户  包括全国的用户)
 *
 *
 */
@SuppressWarnings("serial")
public class BonusGov  implements Serializable{
  private long id;//主键
  private String kjTime;//开奖时间
  private String addTime;//新增时间
  private String phaseNo;//期次号
  private String lotteryName;//彩种
  private String kjNo;//开奖号码
  private String money1;
  private String money2;
  private String status;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getPhaseNo() {
	return phaseNo;
}
public void setPhaseNo(String phaseNo) {
	this.phaseNo = phaseNo;
}
public String getLotteryName() {
	return lotteryName;
}
public void setLotteryName(String lotteryName) {
	this.lotteryName = lotteryName;
}
public String getKjNo() {
	return kjNo;
}
public void setKjNo(String kjNo) {
	this.kjNo = kjNo;
}
public String getMoney1() {
	return money1;
}
public void setMoney1(String money1) {
	this.money1 = money1;
}
public String getMoney2() {
	return money2;
}
public void setMoney2(String money2) {
	this.money2 = money2;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getKjTime() {
	return kjTime;
}
public void setKjTime(String kjTime) {
	this.kjTime = kjTime;
}
public String getAddTime() {
	return addTime;
}
public void setAddTime(String addTime) {
	this.addTime = addTime;
}

}
