package com.wintv.lottery.xinshui.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.XinshuiOrderVO;

@SuppressWarnings("unchecked")
public class XinshuiSaleManageAction extends BaseAction {

	private static final long serialVersionUID = -3849863729942902623L;

	@Autowired
	private XinshuiService xinshuiService;
	
	private Integer pageSize = 10;// 页面显示数据条数
	private Integer startRow = 0;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计
	private Long raceId = 0L;//联赛ID
	private Long xinshuiId;//心水ID
	private String payStatus = "0";//支付状态
	private String type = "0";//类型 亚盘,大小盘
	private String queryField = "txUsername";// 检索关键字段 发布人,订购人,订单编号,心水编号,主客队名
	private String queryFieldValue = "";// 检索关键字段值
	private String fromStartTime;//检索开赛起始时间
	private String toStartTime;//检索开赛结束时间
	private String fromOrderTime;//检索订单起始时间
	private String toOrderTime;//检索订单结束时间
	private String sort;//排序方式
	
	
	public String excute() {
		return SUCCESS;
	}
	
	/**
	 * 查询心水销售明细
	 * @return
	 */
	public String searchSaleList() {
		try {
			Map params = this.getParams();
			
			Integer totalCount = 0;//总记录数
			 
			if (this.isCount == 1) {
				totalCount = xinshuiService.findSoldOrderSize(params);
			}
			
			//查询心水订单明细
			List<XinshuiOrderVO> xinshuiSoldOrderList = xinshuiService.findSoldOrderList(params, startRow, pageSize);
			
			if (isNotNull(xinshuiSoldOrderList, totalCount)) {
				Map result = new HashMap();
				result.put("totalCount", totalCount);
				result.put("xinshuiSoldOrderList", xinshuiSoldOrderList);

				generateResult(1, MSG_SUCCESS, result);
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 获取页面参数
	 * @return
	 */
	public Map getParams() {
		Map params = new HashMap();
		Long userId = ((UserCookie) request.getSession().getAttribute("userCookie")).getUserId();
		params.put("currentUserId", userId);
		
		if(isNotNull(xinshuiId)) {
			params.put("c2cId", xinshuiId);
		}
		
		if(StringUtils.isNotEmpty(queryField) && StringUtils.isNotEmpty(queryFieldValue)) {
			params.put(queryField, queryFieldValue);
		}
		
		if(isNotNull(raceId) && raceId != 0L) {
			params.put("raceId", raceId);
		}
		
		if(StringUtils.isNotEmpty(payStatus) && !"0".equals(payStatus)) {
				params.put("payStatus", payStatus);
		}
		
		if(StringUtils.isNotEmpty(fromStartTime)) {
			params.put("fromStartTime", fromStartTime);
		}
		
		if(StringUtils.isNotEmpty(toStartTime)) {
			params.put("toStartTime", toStartTime);
		}
		
		if(StringUtils.isNotEmpty(fromOrderTime)) {
			params.put("fromOrderTime", fromOrderTime);
		}
		
		if(StringUtils.isNotEmpty(toOrderTime)) {
			params.put("toOrderTime", toOrderTime);
		}
		
		if(StringUtils.isNotEmpty(type) && !"0".equals(type)) {
			params.put("type", type);
		}
		
		if(StringUtils.isNotEmpty(sort) && !"0".equals(sort)) {
			params.put("sort", sort);
		}
		
		return params;
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

	public Integer getIsCount() {
		return isCount;
	}

	public void setIsCount(Integer isCount) {
		this.isCount = isCount;
	}

	public Long getRaceId() {
		return raceId;
	}

	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQueryField() {
		return queryField;
	}

	public void setQueryField(String queryField) {
		this.queryField = queryField;
	}

	public String getQueryFieldValue() {
		return queryFieldValue;
	}

	public void setQueryFieldValue(String queryFieldValue) {
		this.queryFieldValue = queryFieldValue;
	}

	public String getFromStartTime() {
		return fromStartTime;
	}

	public void setFromStartTime(String fromStartTime) {
		this.fromStartTime = fromStartTime;
	}

	public String getToStartTime() {
		return toStartTime;
	}

	public void setToStartTime(String toStartTime) {
		this.toStartTime = toStartTime;
	}

	public String getFromOrderTime() {
		return fromOrderTime;
	}

	public void setFromOrderTime(String fromOrderTime) {
		this.fromOrderTime = fromOrderTime;
	}

	public String getToOrderTime() {
		return toOrderTime;
	}

	public void setToOrderTime(String toOrderTime) {
		this.toOrderTime = toOrderTime;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Long getXinshuiId() {
		return xinshuiId;
	}

	public void setXinshuiId(Long xinshuiId) {
		this.xinshuiId = xinshuiId;
	}

}
