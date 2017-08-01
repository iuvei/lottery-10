package com.wintv.lottery.xinshui.vo;

import java.io.Serializable;
@SuppressWarnings("serial")
public class BackendPubmanageVO   implements  Serializable{
	private Long id;//主键
	private String username;//用户名
	private Long userId;//发布人用户ID
	private String userGrade;//用户等级
	private Long yccc;//预测场次
	private Double zql;//准确率
	private Long zdgs;//总订购数
	private String soldMoney;//销售额
	private String peichangMoney;//赔付款
	private String lastTime;//最新发布时间
	private double orderCntPerChang;//场均订购数
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}
	public Long getYccc() {
		return yccc;
	}
	public void setYccc(Long yccc) {
		this.yccc = yccc;
	}
	public Double getZql() {
		return zql;
	}
	public void setZql(Double zql) {
		this.zql = zql;
	}
	public Long getZdgs() {
		return zdgs;
	}
	public void setZdgs(Long zdgs) {
		this.zdgs = zdgs;
	}
	public String getSoldMoney() {
		return soldMoney;
	}
	public void setSoldMoney(String soldMoney) {
		this.soldMoney = soldMoney;
	}
	public String getPeichangMoney() {
		return peichangMoney;
	}
	public void setPeichangMoney(String peichangMoney) {
		this.peichangMoney = peichangMoney;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public double getOrderCntPerChang() {
		return orderCntPerChang;
	}
	public void setOrderCntPerChang(double orderCntPerChang) {
		this.orderCntPerChang = orderCntPerChang;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
