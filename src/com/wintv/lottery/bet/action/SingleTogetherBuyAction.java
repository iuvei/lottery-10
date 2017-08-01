package com.wintv.lottery.bet.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.utils.Util;
import com.wintv.lottery.bet.service.BetService;

/**
 * 足球单场-参与合买
 * 
 * @author Arix04 by 2010-03-30
 * 
 * @version 1.0.0
 */
public class SingleTogetherBuyAction extends BaseAction {
	private static final long serialVersionUID = -3361384831673624079L;
	
	@Autowired
	private BetService betService;
	
	private String betCategory;//投注彩种  61:单场半全场  62:单场比分 63:单场让球胜平负  64:单场上下单双 65:单场总进球
	private Long phaseId;//期次ID
	private Integer pageSize = 30;// 页面显示数据条数
	private Integer startRow = 1;// 查询开始行
	private String phaseNo;// 期次号
	private String betUsername;//发起人
	private String status;//状态 '1'：满员、'2':未满员、'3':已撤单
	private String type;//投注方式 '2':单式发起、'4':复式发起
	private String allMoney;//方案金额 0-100、100-500、501-1000、1000
	private String progress;//进度 0-30、30-60、60-90、90-100
	private String isFloor;//保底 '1':已保底、'2'：未保底
	private String sortValue;//排序字段
	private String sortType;//排序方式
	private String advSearch;//高级搜索查询
	
	private String keyword;//关键字
	private String sort;//排序字段
	
	public String togetherBuy() {
		return SUCCESS;
	}
	
	/**
	 * 参与合买
	 * @return
	 */
	public String findCoopList() {
		try {
			Map params = generateParamsMap();
			Map resultMap = betService.findCoopList(params);
			generateResult(1, MSG_SUCCESS, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 后台参数
	 */
	private Map<String, Object> generateParamsMap() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("startRow", startRow);
		queryParams.put("pageSize", pageSize);
		queryParams.put("phaseId", phaseId);
		queryParams.put("betCategory", betCategory);
		if(StringUtils.isNotEmpty(advSearch)) {
			queryParams.put("advSearch", advSearch);
		} else {
			if (StringUtils.isNotEmpty(betUsername)) {
				queryParams.put("betUsername", betUsername);
			}
			if (StringUtils.isNotEmpty(status)) {
				queryParams.put("status", status);
			}
			if (StringUtils.isNotEmpty(type)) {
				queryParams.put("type", type);
			}
			if (StringUtils.isNotEmpty(allMoney)) {
				queryParams.put("allMoney", allMoney);
			}
			if (StringUtils.isNotEmpty(progress)) {
				queryParams.put("progress", progress);
			}
			if (StringUtils.isNotEmpty(isFloor)) {
				queryParams.put("isFloor", isFloor);
			}
			
			if(StringUtils.isNotEmpty(sortValue) && StringUtils.isNotEmpty(sortType)) {
				if("planCode".equals(sortValue)) { sortValue = " a.plan_code"; }
				if("betUsername".equals(sortValue)) { sortValue = " a.bet_username"; }
				if("betMilitary".equals(sortValue)) { sortValue = " a.bet_military"; }
				if("type".equals(sortValue)) { sortValue = " a.type"; }
				if("allMoney".equals(sortValue)) { sortValue = " a.all_money"; }
				if("betMulti".equals(sortValue)) { sortValue = " a.bet_multi"; }
				if("remainCopys".equals(sortValue)) { sortValue = " (a.divide_copys-a.already_buy_copys)"; }
				if("singleMoney".equals(sortValue)) { sortValue = " a.single_money"; }
				if("rate".equals(sortValue)) { sortValue = " a.already_buy_copys/a.divide_copys"; }
				if("status".equals(sortValue)) { sortValue = " a.order_status,a.already_buy_copys/a.divide_copys"; }
				queryParams.put("sort", sortValue + " " + sortType);
			}
		}
		return queryParams;
	}

	public Long getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public String getPhaseNo() {
		return phaseNo;
	}

	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}

	public String getBetUsername() {
		return betUsername;
	}

	public void setBetUsername(String betUsername) {
		this.betUsername = betUsername;
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

	public String getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getIsFloor() {
		return isFloor;
	}

	public void setIsFloor(String isFloor) {
		this.isFloor = isFloor;
	}

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getBetCategory() {
		return betCategory;
	}

	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}

	public String getAdvSearch() {
		return advSearch;
	}

	public void setAdvSearch(String advSearch) {
		this.advSearch = advSearch;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		try {
			this.keyword = java.net.URLDecoder.decode(keyword,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		};
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
