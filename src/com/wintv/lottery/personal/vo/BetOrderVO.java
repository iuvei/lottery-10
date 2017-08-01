package com.wintv.lottery.personal.vo;

public class BetOrderVO {
	    private Long betId;//主键
	    private String type;
	    private String betCategory;
	    private String betUsername;
	    private String allMoney;//总金额
	    private long hitChangci;
	    private String bonus;//奖金
	    private long partiCnt;//参与人数
	    private String status;
	    private String title;//头衔
	    private String phaseNo;//期次号码
	    private String progress;
	    private long betMilitary;//投注战绩
	    private String allProgress;//总进度
	    private String floorPercentage;//保底百分比
	    private String isTop;//是否置顶
	    private long hit;//命中场次
		public Long getBetId() {
			return betId;
		}
		public void setBetId(Long betId) {
			this.betId = betId;
		}
		public String getBetCategory() {
			return betCategory;
		}
		public void setBetCategory(String betCategory) {
			this.betCategory = betCategory;
		}
		public String getBetUsername() {
			return betUsername;
		}
		public void setBetUsername(String betUsername) {
			this.betUsername = betUsername;
		}
		public String getAllMoney() {
			return allMoney;
		}
		public void setAllMoney(String allMoney) {
			this.allMoney = allMoney;
		}
		public long getHitChangci() {
			return hitChangci;
		}
		public void setHitChangci(long hitChangci) {
			this.hitChangci = hitChangci;
		}
		public String getBonus() {
			return bonus;
		}
		public void setBonus(String bonus) {
			this.bonus = bonus;
		}
		public long getPartiCnt() {
			return partiCnt;
		}
		public void setPartiCnt(long partiCnt) {
			this.partiCnt = partiCnt;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getTitle() {
			if(null == title) 
				return "";
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getPhaseNo() {
			return phaseNo;
		}
		public void setPhaseNo(String phaseNo) {
			this.phaseNo = phaseNo;
		}
		public String getProgress() {
			return progress;
		}
		public void setProgress(String progress) {
			this.progress = progress;
		}
		public long getBetMilitary() {
			return betMilitary;
		}
		public void setBetMilitary(long betMilitary) {
			this.betMilitary = betMilitary;
		}
		public String getAllProgress() {
			return allProgress;
		}
		public void setAllProgress(String allProgress) {
			this.allProgress = allProgress;
		}
		
		public String getIsTop() {
			return isTop;
		}
		public void setIsTop(String isTop) {
			this.isTop = isTop;
		}
		public long getHit() {
			return hit;
		}
		public void setHit(long hit) {
			this.hit = hit;
		}
		public String getFloorPercentage() {
			return floorPercentage;
		}
		public void setFloorPercentage(String floorPercentage) {
			this.floorPercentage = floorPercentage;
		}
	
}
