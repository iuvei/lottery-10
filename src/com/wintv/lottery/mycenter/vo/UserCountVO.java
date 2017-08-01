package com.wintv.lottery.mycenter.vo;

/*********
 * 您在天彩的动态统计
 * 
 * @author Arix04 2010-04-23
 * 
 */
public class UserCountVO {
	
	private Long dgUnPayCount; // 代购：未支付
	private Long dgUnDrawLotteryCount; // 代购：未开奖
	private Long dgLotteryCount; // 代购：已中奖
	private Long hmReleaseCount; // 合买：发起合买
	private Long hmJoinCount; // 合买：参与合买
	private Long hmUnDrawLotteryCount; // 合买：未开奖
	private Long hmLotteryCount; // 合买：已开奖
	private Long bxUnPayCount; // 购买心水：未支付
	private Long bxUnStartCount; // 购买心水：未开赛心水
	private Long bxHitCount; // 购买心水：命中心水
	private Long bxCompensateCount; // 购买心水：保证金赔偿
	private Long sxSuccessCount; // 销售心水：成功销售
	private Long sxUnStartCount; // 销售心水：未开赛心水
	private Long sxHitCount; // 销售心水：命中心水
	private Long sxCompensateCount; // 销售心水：保证金赔偿
	private Long zjSuccessCount; // 专家包月：成功购买
	
	public Long getDgUnPayCount() {
		return dgUnPayCount;
	}

	public void setDgUnPayCount(Long dgUnPayCount) {
		this.dgUnPayCount = dgUnPayCount;
	}

	public Long getDgUnDrawLotteryCount() {
		return dgUnDrawLotteryCount;
	}

	public void setDgUnDrawLotteryCount(Long dgUnDrawLotteryCount) {
		this.dgUnDrawLotteryCount = dgUnDrawLotteryCount;
	}

	public Long getDgLotteryCount() {
		return dgLotteryCount;
	}

	public void setDgLotteryCount(Long dgLotteryCount) {
		this.dgLotteryCount = dgLotteryCount;
	}

	public Long getHmReleaseCount() {
		return hmReleaseCount;
	}

	public void setHmReleaseCount(Long hmReleaseCount) {
		this.hmReleaseCount = hmReleaseCount;
	}

	public Long getHmJoinCount() {
		return hmJoinCount;
	}

	public void setHmJoinCount(Long hmJoinCount) {
		this.hmJoinCount = hmJoinCount;
	}

	public Long getHmUnDrawLotteryCount() {
		return hmUnDrawLotteryCount;
	}

	public void setHmUnDrawLotteryCount(Long hmUnDrawLotteryCount) {
		this.hmUnDrawLotteryCount = hmUnDrawLotteryCount;
	}

	public Long getHmLotteryCount() {
		return hmLotteryCount;
	}

	public void setHmLotteryCount(Long hmLotteryCount) {
		this.hmLotteryCount = hmLotteryCount;
	}

	public Long getBxUnPayCount() {
		return bxUnPayCount;
	}

	public void setBxUnPayCount(Long bxUnPayCount) {
		this.bxUnPayCount = bxUnPayCount;
	}

	public Long getBxUnStartCount() {
		return bxUnStartCount;
	}

	public void setBxUnStartCount(Long bxUnStartCount) {
		this.bxUnStartCount = bxUnStartCount;
	}

	public Long getBxHitCount() {
		return bxHitCount;
	}

	public void setBxHitCount(Long bxHitCount) {
		this.bxHitCount = bxHitCount;
	}

	public Long getBxCompensateCount() {
		return bxCompensateCount;
	}

	public void setBxCompensateCount(Long bxCompensateCount) {
		this.bxCompensateCount = bxCompensateCount;
	}

	public Long getSxSuccessCount() {
		return sxSuccessCount;
	}

	public void setSxSuccessCount(Long sxSuccessCount) {
		this.sxSuccessCount = sxSuccessCount;
	}

	public Long getSxUnStartCount() {
		return sxUnStartCount;
	}

	public void setSxUnStartCount(Long sxUnStartCount) {
		this.sxUnStartCount = sxUnStartCount;
	}

	public Long getSxHitCount() {
		return sxHitCount;
	}

	public void setSxHitCount(Long sxHitCount) {
		this.sxHitCount = sxHitCount;
	}

	public Long getSxCompensateCount() {
		return sxCompensateCount;
	}

	public void setSxCompensateCount(Long sxCompensateCount) {
		this.sxCompensateCount = sxCompensateCount;
	}

	public Long getZjSuccessCount() {
		return zjSuccessCount;
	}

	public void setZjSuccessCount(Long zjSuccessCount) {
		this.zjSuccessCount = zjSuccessCount;
	}
}
