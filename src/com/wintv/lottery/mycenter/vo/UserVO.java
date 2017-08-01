package com.wintv.lottery.mycenter.vo;

/*********
 * 用户个人信息
 * 
 * @author Arix04 2010-04-23
 * 
 */

public class UserVO {
	
	private String username;// 用户名
	private Long userid;// 用户id
	private String fund;// 可用资金
	private String frozenMoney;// 冻结金额
	private String caijin;// 可用彩金
	private String point;// 积分
	private String vipLevel;// 会员等级
	private String betLevel;// 代购合买战绩
	private String xinshuiLevel;// 心水战绩
	private String challengeLevel;// 擂台战绩
	private String isBindID;// 是否绑定身份证 1 = 已绑定、0 = 未绑定
	private String isBindEmail;// 是否绑定身份证 1 = 已绑定、0 = 未绑定
	private String isSetPwdProtect;// 是否设置密码保护 1 = 已设置、0 = 未设置
	private String isSetWithdrawPwd;// 是否设置取款密码 1 = 已设置、0 = 未设置
	private String title;//头衔

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getFrozenMoney() {
		return frozenMoney;
	}

	public void setFrozenMoney(String frozenMoney) {
		this.frozenMoney = frozenMoney;
	}

	public String getCaijin() {
		return caijin;
	}

	public void setCaijin(String caijin) {
		this.caijin = caijin;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getBetLevel() {
		return betLevel;
	}

	public void setBetLevel(String betLevel) {
		this.betLevel = betLevel;
	}

	public String getXinshuiLevel() {
		return xinshuiLevel;
	}

	public void setXinshuiLevel(String xinshuiLevel) {
		this.xinshuiLevel = xinshuiLevel;
	}

	public String getChallengeLevel() {
		return challengeLevel;
	}

	public void setChallengeLevel(String challengeLevel) {
		this.challengeLevel = challengeLevel;
	}

	public String getIsBindID() {
		return isBindID;
	}

	public void setIsBindID(String isBindID) {
		this.isBindID = isBindID;
	}

	public String getIsBindEmail() {
		return isBindEmail;
	}

	public void setIsBindEmail(String isBindEmail) {
		this.isBindEmail = isBindEmail;
	}

	public String getIsSetPwdProtect() {
		return isSetPwdProtect;
	}

	public void setIsSetPwdProtect(String isSetPwdProtect) {
		this.isSetPwdProtect = isSetPwdProtect;
	}

	public String getIsSetWithdrawPwd() {
		return isSetWithdrawPwd;
	}

	public void setIsSetWithdrawPwd(String isSetWithdrawPwd) {
		this.isSetWithdrawPwd = isSetWithdrawPwd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
