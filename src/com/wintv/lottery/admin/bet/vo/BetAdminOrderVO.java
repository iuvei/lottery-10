package com.wintv.lottery.admin.bet.vo;

public class BetAdminOrderVO {
	    public Long getBetId() {
		return betId;
	}
	public void setBetId(Long betId) {
		this.betId = betId;
	}
	public String getWiciType() {
		return wiciType;
	}
	public void setWiciType(String wiciType) {
		this.wiciType = wiciType;
	}
	public String getIsDingdan() {
		return isDingdan;
	}
	public void setIsDingdan(String isDingdan) {
		this.isDingdan = isDingdan;
	}
	public String getViciWay() {
		return viciWay;
	}
	public void setViciWay(String viciWay) {
		this.viciWay = viciWay;
	}
	public Long getBetNumber() {
		return betNumber;
	}
	public void setBetNumber(Long betNumber) {
		this.betNumber = betNumber;
	}
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public String getBetTime() {
		return betTime;
	}
	public void setBetTime(String betTime) {
		this.betTime = betTime;
	}
	public Long getBetUserid() {
		return betUserid;
	}
	public void setBetUserid(Long betUserid) {
		this.betUserid = betUserid;
	}
	public Long getBetMulti() {
		return betMulti;
	}
	public void setBetMulti(Long betMulti) {
		this.betMulti = betMulti;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSponsorType() {
		return sponsorType;
	}
	public void setSponsorType(String sponsorType) {
		this.sponsorType = sponsorType;
	}
	public String getIsFloor() {
		return isFloor;
	}
	public void setIsFloor(String isFloor) {
		this.isFloor = isFloor;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getFormatStr() {
		return formatStr;
	}
	public void setFormatStr(String formatStr) {
		this.formatStr = formatStr;
	}
	public int getSingleMoney() {
		return singleMoney;
	}
	public void setSingleMoney(int singleMoney) {
		this.singleMoney = singleMoney;
	}

	public Long getDivideCopys() {
		return divideCopys;
	}
	public void setDivideCopys(Long divideCopys) {
		this.divideCopys = divideCopys;
	}
	public int getFloorCopys() {
		return floorCopys;
	}
	public void setFloorCopys(int floorCopys) {
		this.floorCopys = floorCopys;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getRecruitUsers() {
		return recruitUsers;
	}
	public void setRecruitUsers(String recruitUsers) {
		this.recruitUsers = recruitUsers;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getBetCategory() {
		return betCategory;
	}
	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}
		private Long betId;//主键
		private String wiciType;//过关类型
		private String isDingdan;//是否定胆
		private String viciWay;//过关方式
		private Long betNumber;//注数
		private String betUsername;
		private String phaseNo;//期次
		private String betTime;//投注时间
		private Long betUserid;//投注人用户ID
		private Long betMulti;//倍数
		private String plan;//方案(胜胜 33 ..其他类似)
		private String type;//类型 :'1'单代 '2' 单合  '3 '复代 '4'复合
		private String sponsorType;//发起类型   '1':发起人   '2'  参与合买
	    private String isFloor;//是否保底
		private String planCode;//方案编号
		private String formatStr;//格式转换字符
		private int singleMoney;//单倍金额
		private String allMoney;//总金额
		public String getAllMoney() {
			return allMoney;
		}
		public void setAllMoney(String allMoney) {
			this.allMoney = allMoney;
		}
		private Long divideCopys;//分成份数
		private int floorCopys;//保底份数
		private String isOpen;//是否公开
		private String recruitUsers;//招股对象
	    private int subscribeCopys;
	    private String subscribeMoney;
		private String orderNo;//订单号
		private String betCategory;//彩种
		private double subRate;//认购比率
		private String  bonus;//奖金
		private int leavingCopys;//合买时剩余的份数
		private double progress;//进度
		public double getSubRate() {
			return subRate;
		}
		public void setSubRate(double subRate) {
			this.subRate = subRate;
		}
		private String orderStatus;//订单状态
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
		public String getZjStatus() {
			return zjStatus;
		}
		public void setZjStatus(String zjStatus) {
			this.zjStatus = zjStatus;
		}
		private String zjStatus;//中奖状态
		public String getBetUsername() {
			return betUsername;
		}
		public void setBetUsername(String betUsername) {
			this.betUsername = betUsername;
		}
		public int getSubscribeCopys() {
			return subscribeCopys;
		}
		public void setSubscribeCopys(int subscribeCopys) {
			this.subscribeCopys = subscribeCopys;
		}
		public String getSubscribeMoney() {
			return subscribeMoney;
		}
		public void setSubscribeMoney(String subscribeMoney) {
			this.subscribeMoney = subscribeMoney;
		}
		public String getBonus() {
			return bonus;
		}
		public void setBonus(String bonus) {
			this.bonus = bonus;
		}
		private String betMilitary;//投注战绩
		public String getBetMilitary() {
			return betMilitary;
		}
		public void setBetMilitary(String betMilitary) {
			this.betMilitary = betMilitary;
		}
		public int getLeavingCopys() {
			return leavingCopys;
		}
		public void setLeavingCopys(int leavingCopys) {
			this.leavingCopys = leavingCopys;
		}
		public double getProgress() {
			return progress;
		}
		public void setProgress(double progress) {
			this.progress = progress;
		}
		
}
