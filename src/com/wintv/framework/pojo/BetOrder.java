package com.wintv.framework.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.wintv.framework.utils.DateUtil;

/**
 * 用户投注结果
 * 
 *
 */
@SuppressWarnings("serial")
public class BetOrder implements Serializable {
    private Long betId;//主键
	private String wiciType;//过关类型
	private String viciWay;//过关方式
	private Long betNumber;//注数
	private Long phaseId;//期次ID
	private Long kingId;//用于自动跟单使用  关联T_KING_SPONSOR表的主键
	private String phaseNo;//期次
	private Date betTime;//投注时间
	private Long betUserid;//投注人用户ID
	private Long betMulti;//倍数
	private String plan;//方案(胜胜 33 ..其他类似)
	private String type;//类型 :'1'单代 '2' 单合  '3 '复代 '4'复合
	private String sponsorType;//发起类型   '1':发起人   '2'  参与合买
    private String isFloor;//是否保底
	private String planCode;//方案编号
	private String formatStr;//格式转换字符
	private BigDecimal singleMoney;//单倍金额
	private BigDecimal allMoney;//总金额
	private Long divideCopys;//分成份数
	private Long floorCopys;//保底份数
	private Integer securityType;//保密类型:  1:完全公开  2:截止后公开   3:仅对跟单用户公开
	private String recruitUsers;//招股对象
	private String orderNo;//订单号
	/**
	 * 投注彩种    '1': 胜负14场     '2':任9场  '3':4场进球  '5':6 场半全场    
	 * '61':单场半全场  '62':单场比分 '63':单场胜平负  '64':单场上下单双 '65':单场总进球
	 */
	private String betCategory;
	private String orderStatus;//订单状态 '1'  :未支付、'2':待出票、'3':已出票、'4':已取消、'5':已过期
	private String zjStatus;//中奖状态    '1':'未开奖'  '2':'已返奖'  '3':'未中奖'
	private Long sponsorBetId;//参与合买时的发起人投注ID  关联BET_ID
	private Long subscribeCopys;//认购份数
	private BigDecimal subscribeMoney;//认购金额
	private BigDecimal bonus;//奖金
	private String betUsername;
	private Integer tcRate;//提成比例
	private Long alreadyBuyCopys;//合买时已经合买的份数
	private Date endTime;
	private String txtPath;//TXT文件路径
	private String planTitle;//方案标题(仅对发起方案使用)
	private String planDesc;//方案描述(仅对发起方案使用)
	private String betSelInfo;//投注选择信息  4-2, 3-1,2-2,1-11
	private Long correctChangCi;//猜对的场次
	private Long isHot;//热门推荐=1  普通方案=0
	public Integer getTcRate() {
		return tcRate;
	}
	public void setTcRate(Integer tcRate) {
		this.tcRate = tcRate;
	}
	public String getZjStatus() {
		return zjStatus;
	}
	public void setZjStatus(String zjStatus) {
		this.zjStatus = zjStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getBetCategory() {
		return betCategory;
	}
	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}

	public Long getBetNumber() {
		return betNumber;
	}
	public void setBetNumber(Long betNumber) {
		this.betNumber = betNumber;
	}
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

	public String getViciWay() {
		return viciWay;
	}
	public void setViciWay(String viciWay) {
		this.viciWay = viciWay;
	}
	
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public Date getBetTime() {
		return betTime;
	}
	public void setBetTime(Date betTime) {
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
	public java.math.BigDecimal getSingleMoney() {
		return singleMoney;
	}
	public void setSingleMoney(java.math.BigDecimal singleMoney) {
		this.singleMoney = singleMoney;
	}
	public java.math.BigDecimal getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(java.math.BigDecimal allMoney) {
		this.allMoney = allMoney;
	}
	public Long getDivideCopys() {
		return divideCopys;
	}
	public void setDivideCopys(Long divideCopys) {
		this.divideCopys = divideCopys;
	}
	public Long getFloorCopys() {
		return floorCopys;
	}
	public void setFloorCopys(Long floorCopys) {
		this.floorCopys = floorCopys;
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
	public String getSponsorType() {
		return sponsorType;
	}
	public void setSponsorType(String sponsorType) {
		this.sponsorType = sponsorType;
	}
	public Long getSponsorBetId() {
		return sponsorBetId;
	}
	public void setSponsorBetId(Long sponsorBetId) {
		this.sponsorBetId = sponsorBetId;
	}
	public Long getSubscribeCopys() {
		return subscribeCopys;
	}
	public void setSubscribeCopys(Long subscribeCopys) {
		this.subscribeCopys = subscribeCopys;
	}
	public java.math.BigDecimal getSubscribeMoney() {
		return subscribeMoney;
	}
	public void setSubscribeMoney(java.math.BigDecimal subscribeMoney) {
		this.subscribeMoney = subscribeMoney;
	}
	public java.math.BigDecimal getBonus() {
		return bonus;
	}
	public void setBonus(java.math.BigDecimal bonus) {
		this.bonus = bonus;
	}
	public String getBetUsername() {
		return betUsername;
	}
	public void setBetUsername(String betUsername) {
		this.betUsername = betUsername;
	}
	public Long getAlreadyBuyCopys() {
		return alreadyBuyCopys;
	}
	public void setAlreadyBuyCopys(Long alreadyBuyCopys) {
		this.alreadyBuyCopys = alreadyBuyCopys;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}
	public Long getKingId() {
		return kingId;
	}
	public void setKingId(Long kingId) {
		this.kingId = kingId;
	}
	public String getTxtPath() {
		return txtPath;
	}
	public void setTxtPath(String txtPath) {
		this.txtPath = txtPath;
	}
	public BetOrder(){}
	public BetOrder(Map params){
		 
		   String betCategory=(String)params.get("betCategory");
		   this.setBetCategory(betCategory);
		   String endTime=(String)params.get("endTime");
		   if(StringUtils.isNotEmpty(endTime)){
			   Date _endTime=DateUtil.parseDate(endTime,"yyyy-MM-dd HH:mm");
			   this.setEndTime(_endTime);
		   }
		   String phaseNo=(String)params.get("phaseNo");
		   this.setPhaseNo(phaseNo);
		   Object phaseId=params.get("phaseId");//期次ID
		   if(phaseId!=null){
			   this.setPhaseId(new Long(phaseId.toString()));
		   }
		   String wiciType=(String)params.get("wiciType");
		   if(StringUtils.isNotEmpty(wiciType)){
			   this.setWiciType(wiciType);
		   }
		   String wiciWay=(String)params.get("wiciWay");
		   if(StringUtils.isNotEmpty(wiciWay)){
			   this.setViciWay(wiciWay);
		   }
		   String plan=(String)params.get("plan");
		   if(StringUtils.isNotEmpty(plan)){
			   this.setPlan(plan);
		   }
		   String txtPath=(String)params.get("txtPath");
		   if(StringUtils.isNotEmpty(txtPath)){
			   this.setTxtPath(txtPath);
		   }
		   String formatStr=(String)params.get("formatStr");
		   if(StringUtils.isNotEmpty(formatStr)){
			   this.setFormatStr(formatStr);
		   }
           String betMulti=(String)params.get("betMulti");
		   if(StringUtils.isNotEmpty(betMulti)){
			   this.setBetMulti(new Long(betMulti));
		   }
		   Long betUserid=(Long)params.get("betUserid");
		   if(betUserid!=null){
			   this.setBetUserid(betUserid);
		   }
		   String betUsername=(String)params.get("betUsername");
		   if(StringUtils.isNotEmpty(betUsername)){
			   this.setBetUsername(betUsername);
		   }
		   String divideCopys=(String)params.get("divideCopys");
		   if(StringUtils.isNotEmpty(divideCopys)){
		     this.setDivideCopys(new Long(divideCopys));
		   }
		   String type=(String)params.get("type");
		   this.setType(type);
		   String tcRate=(String)params.get("tcRate");
		   if(StringUtils.isNotEmpty(tcRate)){
			   this.setTcRate(new Integer(tcRate));
		   }
		   String securityType=(String)params.get("securityType");//保密类型:  1:完全公开  2:截止后公开   3:仅对跟单用户公开
		   if(StringUtils.isNotEmpty(securityType)){
			   this.setSecurityType(new Integer(securityType));
		   }
		   String betSelInfo=(String)params.get("betSelInfo");
		   if(StringUtils.isNotEmpty(betSelInfo)){
			   this.setBetSelInfo(betSelInfo);
		   }
		   String planTitle=(String)params.get("planTitle");//方案标题(仅对发起方案使用)
		   if(StringUtils.isNotEmpty(planTitle)){
			   this.setPlanTitle(planTitle);
		   }
		   String planDesc=(String)params.get("planDesc");//方案描述(仅对发起方案使用)
		   if(StringUtils.isNotEmpty(planDesc)){
			   this.setPlanDesc(planDesc);
		   }
		   Long sponsorBetId=(Long)params.get("sponsorBetId");
		   if(sponsorBetId!=null){
			   this.setSponsorBetId(sponsorBetId);
		   }
		   String singleMoney=(String)params.get("singleMoney");
		   if(StringUtils.isNotEmpty(singleMoney)){
			   this.setSingleMoney(new BigDecimal(singleMoney));
		   }
		  
		   BigDecimal allMoney=(BigDecimal)params.get("allMoney");
		   if(null != allMoney){
			   this.setAllMoney(allMoney);
		   }
		   String betNumber=(String)params.get("betNumber");
		   if(StringUtils.isNotEmpty(betNumber)){
			   this.setBetNumber(new Long(betNumber));
		   }
		   String subscribeMoney=(String)params.get("subscribeMoney");
		   if(StringUtils.isNotEmpty(subscribeMoney)){
			   this.setSubscribeMoney(new BigDecimal(subscribeMoney));
		   }
		   String subscribeCopys=(String)params.get("subscribeCopys");
		   if(StringUtils.isNotEmpty(subscribeCopys)){
			   this.setSubscribeCopys(new Long(subscribeCopys));
		   }
		   
		  //购买彩票(1:足球单场,6:胜负彩,7:任九,8:6场半全场,9:4场进球) |购买b2c(10:) |购买c2c(11) 12:充值 13:取款   14:缴纳保证金15:心水保证金解冻
		
		
		
	}
	public Integer getSecurityType() {
		return securityType;
	}
	public void setSecurityType(Integer securityType) {
		this.securityType = securityType;
	}
	public String getPlanTitle() {
		return planTitle;
	}
	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}
	public String getPlanDesc() {
		return planDesc;
	}
	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}
	public String getBetSelInfo() {
		return betSelInfo;
	}
	public void setBetSelInfo(String betSelInfo) {
		this.betSelInfo = betSelInfo;
	}
	public Long getCorrectChangCi() {
		return correctChangCi;
	}
	public void setCorrectChangCi(Long correctChangCi) {
		this.correctChangCi = correctChangCi;
	}
	public Long getIsHot() {
		return isHot;
	}
	public void setIsHot(Long isHot) {
		this.isHot = isHot;
	}
	
	
}