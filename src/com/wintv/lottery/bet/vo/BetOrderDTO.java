package com.wintv.lottery.bet.vo;

import com.wintv.framework.pojo.BetOrder;

public class BetOrderDTO  extends BetOrder{
	private String stars;
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public double getBaodi() {
		return baodi;
	}
	public void setBaodi(double baodi) {
		this.baodi = baodi;
	}
	public double getBaodiPer() {
		return baodiPer;
	}
	public void setBaodiPer(double baodiPer) {
		this.baodiPer = baodiPer;
	}
	private double baodi;
	private double baodiPer;
}
