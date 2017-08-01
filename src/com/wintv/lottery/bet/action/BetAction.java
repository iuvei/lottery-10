package com.wintv.lottery.bet.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.bet.service.BetService;

/**
 * 投注模块action层
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
public class BetAction extends BaseAction {
	private static final long serialVersionUID = 2788342601778978522L;
	@Autowired
	private BetService betService;
	private String betContent;// 投注内容
	private String endTime;// 方案截止时间
	private String type;// '1'单代 '2' 单合  '3 '复代 '4'复合
	private String categoryType;// 1:足球单场,6:胜负彩,7:任九,8:6场半全场,9:4场进球) |购买b2c(10:) |购买c2c(11) 12:充值 13:取款 14:缴纳保证金15:心水保证金解冻
	private String betCategory;// 投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场 62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
	private String phaseNo;// 期次号
	private Long phaseId;//期次ID
	private String wiciType;// 过关类型
	private String wiciWay;// 过关方式
	private String allMoney;// 总投注金额
	private String betMulti;// 投注倍数
	private String isUseCaijin;// 是否使用彩金
	private String betNum;//总投注数
	private String divideCopys;//分成份数
	private String floorCopys;//保底份数
	private String subscribeCopys;//认购份数
	private String tcRate;//方案提成
	
	public String excute() {
		return SUCCESS;
	}

	/**
	 * 复试代购-立即投注 
	 * 
	 * @return  1:投注成功 -1:系统报错,不成功 4:余额不足 ，但仍然能投注 5:账户锁定
	 */
	public String saveBetOrderFSDG() {
		try {
			Map params = generateParamsMapFSDG();
			long result = betService.saveBetOrder(params);
			if (isNotNull(result)) {
				generateResult(1, MSG_SUCCESS, result);
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 后台参数
	 */
	private Map<String, Object> generateParamsMapFSDG() {
		UserCookie currentUser = (UserCookie) session.get("userCookie");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("betContent", this.getBetContent());
		queryParams.put("endTime", this.getEndTime());
		queryParams.put("categoryType", this.getCategoryType());
		queryParams.put("betCategory", this.getBetCategory());
		queryParams.put("phaseNo", this.getPhaseNo());
		queryParams.put("phaseId", this.getPhaseId());
		queryParams.put("wiciType", this.getWiciType());
		queryParams.put("wiciWay", this.getWiciWay());
//		queryParams.put("allMoney", this.getAllMoney());
		queryParams.put("betMulti", this.getBetMulti());
		queryParams.put("useCaijin", this.getIsUseCaijin());
		queryParams.put("type", this.getType());
		queryParams.put("betNum", this.getBetNum());
		if (isNotNull(currentUser)) {
			queryParams.put("betUserid", currentUser.getUserId());
			queryParams.put("betUsername", currentUser.getUsername());
		}
		return queryParams;
	}

	/**
	 * 复试合买-立即投注
	 * 
	 * @return  1:投注成功 -1:系统报错,不成功 4:余额不足 ，但仍然能投注 5:账户锁定
	 */
	public String saveBetOrderFSHM() {
		try {
			Map params = generateParamsMapFSHM();
			long result = betService.saveBetCoopOrder(params);
			if (isNotNull(result)) {
				generateResult(1, MSG_SUCCESS, result);
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 后台参数
	 */
	private Map<String, Object> generateParamsMapFSHM() {
		UserCookie currentUser = (UserCookie) session.get("userCookie");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("betContent", this.getBetContent());
		queryParams.put("endTime", this.getEndTime());
		queryParams.put("categoryType", this.getCategoryType());
		queryParams.put("betCategory", this.getBetCategory());
		queryParams.put("phaseNo", this.getPhaseNo());
		queryParams.put("phaseId", this.getPhaseId());
		queryParams.put("wiciType", this.getWiciType());
		queryParams.put("wiciWay", this.getWiciWay());
//		queryParams.put("allMoney", this.getAllMoney());
		queryParams.put("betMulti", this.getBetMulti());
		queryParams.put("useCaijin", this.getIsUseCaijin());
		queryParams.put("type", this.getType());
		queryParams.put("betNum", this.getBetNum());
		queryParams.put("divideCopys", this.getDivideCopys());
		queryParams.put("floorCopys", this.getFloorCopys());
		queryParams.put("subscribeCopys", this.getSubscribeCopys());
		queryParams.put("tcRate", this.getTcRate());
		if (isNotNull(currentUser)) {
			queryParams.put("betUserid", currentUser.getUserId());
			queryParams.put("betUsername", currentUser.getUsername());
		}
		return queryParams;
	}
	
	public String getBetContent() {
		return betContent;
	}

	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getBetCategory() {
		return betCategory;
	}

	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}

	public String getPhaseNo() {
		return phaseNo;
	}

	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}

	public String getWiciType() {
		return wiciType;
	}

	public void setWiciType(String wiciType) {
		this.wiciType = wiciType;
	}

	public String getWiciWay() {
		return wiciWay;
	}

	public void setWiciWay(String wiciWay) {
		this.wiciWay = wiciWay;
	}

	public String getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}

	public String getBetMulti() {
		return betMulti;
	}

	public void setBetMulti(String betMulti) {
		this.betMulti = betMulti;
	}

	public String getIsUseCaijin() {
		return isUseCaijin;
	}

	public void setIsUseCaijin(String isUseCaijin) {
		this.isUseCaijin = isUseCaijin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBetNum() {
		return betNum;
	}

	public void setBetNum(String betNum) {
		this.betNum = betNum;
	}

	public String getDivideCopys() {
		return divideCopys;
	}

	public void setDivideCopys(String divideCopys) {
		this.divideCopys = divideCopys;
	}

	public String getFloorCopys() {
		return floorCopys;
	}

	public void setFloorCopys(String floorCopys) {
		this.floorCopys = floorCopys;
	}

	public String getSubscribeCopys() {
		return subscribeCopys;
	}

	public void setSubscribeCopys(String subscribeCopys) {
		this.subscribeCopys = subscribeCopys;
	}

	public String getTcRate() {
		return tcRate;
	}

	public void setTcRate(String tcRate) {
		this.tcRate = tcRate;
	}

	public Long getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}
}
