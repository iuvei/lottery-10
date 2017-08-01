package com.wintv.lottery.xinshui.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.pay.service.PayService;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.XinshuiOrderVO;

@SuppressWarnings("unchecked")
public class XinshuiBuyManageAction extends BaseAction {

	private static final long serialVersionUID = -459358251839334271L;

	@Autowired
	private XinshuiService xinshuiService;
	@Autowired
	private PayService payService;
	
	private Integer pageSize = 10;// 页面显示数据条数
	private Integer startRow = 0;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计
	private Long raceId = 0L;//联赛ID
	private String payStatus = "0";//支付状态 1.未支付 2.已支付 3.已扣款 4.已赔付 5.已取消
	private String queryField = "txUsername";// 检索关键字段 发布人,订购人,订单编号,心水编号,主客队名
	private String queryFieldValue = "";// 检索关键字段值
	private Long xinshuiOrderId;
	
	public String excute() {
		return SUCCESS;
	}
	
	/**
	 * 查询心水订单明细
	 * @return
	 */
	public String searchOrderList() {
		try {
			Map params = this.getParams();
			
			Integer totalCount = 0;//总记录数
			 
			if (this.isCount == 1) {
				totalCount = xinshuiService.findXinshuiOrderSize(params);
			}
			
			//查询心水订单明细
			List<XinshuiOrderVO> xinshuiOrderList = xinshuiService.findXinshuiOrderList(params, startRow, pageSize);
			
			if (isNotNull(xinshuiOrderList, totalCount)) {
				Map result = new HashMap();
				result.put("totalCount", totalCount);
				result.put("xinshuiOrderList", xinshuiOrderList);

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
	
	public String cancelXinshuiPay() {
		try {
			Long userId = ((UserCookie) request.getSession().getAttribute("userCookie")).getUserId();
			
			Integer flg = payService.cancelXinshuiPay(xinshuiOrderId,userId);
			
			if(flg == 1) {
				generateResult(1, MSG_SUCCESS, null);
			}
			if(flg == -1) {
				generateResult(0, MSG_FAILURE, null);
			}
		} catch (DaoException e) {
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
		params.put("userId", userId);
		
		if(StringUtils.isNotEmpty(queryField) && StringUtils.isNotEmpty(queryFieldValue)) {
			params.put(queryField, queryFieldValue);
		}
		
		if(isNotNull(raceId) && raceId != 0L) {
			params.put("raceId", raceId);
		}
		
		if(StringUtils.isNotEmpty(payStatus) && !"0".equals(payStatus)) {
				params.put("payStatus", payStatus);
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

	public Long getXinshuiOrderId() {
		return xinshuiOrderId;
	}

	public void setXinshuiOrderId(Long xinshuiOrderId) {
		this.xinshuiOrderId = xinshuiOrderId;
	}
	
}
