package com.wintv.lottery.admin.xinshui.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.xinshui.service.XinshuiService;

/**
 * 心水发布人管理模块
 * 
 * @author Arix04
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class XinshuiDeployerAction extends BaseAction {
	private static final long serialVersionUID = 7542916105238654736L;

	private String userGrade;//用户级别
	private String orderPeopleNum;// 订购人数
	private String predictNum;//预测场次
	private String precisionRate;//准确率
	
	private String queryFiled = "";// 按球队名
	private String queryFiledValue = "";// 检索关键字段值 按球队名

	private String orderFiled;// 排序字段
	private String orderType;// 排序类型'ASC'|'DESC'

	private Integer pageSize=10;// 页面显示数据条数
	private Integer startRow=0;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计
	
	@Autowired
	private XinshuiService xinshuiService;
	
	public String excute() {
		return SUCCESS;
	}
	/**
	  * 后台心水发布人查询页面
	 */
	public String getXinshuiDeployers() {
		try {
			Map params = generateQueryParamsMap();
			Integer total = 0;
			if (this.isCount == 1) {
				total =xinshuiService.findBackendXinshuiPubSize(params);
			}
			List deployerList =xinshuiService.findBackendXinshuiPubList(params);
			if (isNotNull(deployerList, total)) {
				Map result = new HashMap();
				result.put("totalCount", total);
				result.put("dataList", deployerList);
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
	 * 后台心水发布人页面查询参数
	 */
	private Map<String, Object> generateQueryParamsMap() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		if (isNotNull(this.getUserGrade()) && !("0").equals(this.getUserGrade())) {
			queryParams.put("minUserGrade", this.getUserGrade());
			queryParams.put("maxUserGrade", this.getUserGrade());
		}
		if (isNotNull(this.getOrderPeopleNum())&& !("0").equals(this.getOrderPeopleNum())) {
			String[] orderPeopleNum=this.getOrderPeopleNum().split("~");
			if(orderPeopleNum.length==2){
				queryParams.put("minZdgs", orderPeopleNum[0]);
				queryParams.put("maxZdgs", orderPeopleNum[1]);
			}
		}
		if (isNotNull(this.getPredictNum())&& !("0").equals(this.getPredictNum())) {
			String[] predictNum=this.getPredictNum().split("~");
			if(predictNum.length==2){
				queryParams.put("minYccc", predictNum[0]);
				queryParams.put("maxYccc", predictNum[1]);
			}
		}
		if (isNotNull(this.getPrecisionRate())) {
			String[] precisionRate=this.getPrecisionRate().split("~");
			if(precisionRate.length==2){
				queryParams.put("minZql", precisionRate[0]);
				queryParams.put("maxZql", precisionRate[1]);
			}
		}

		if (isNotNull(this.getQueryFiledValue(),this.getQueryFiled()) && !("").equals(this.getQueryFiledValue())) {
			//queryParams.put("queryFiled", this.getQueryFiled());
			queryParams.put("username", this.getQueryFiledValue());
		}
		if (isNotNull(this.getOrderFiled(), this.getOrderType())) {
			queryParams.put("orderFiled", this.getOrderFiled());
			queryParams.put("orderType", this.getOrderType());
		}
		if (isNotNull(this.getPageSize(), this.getStartRow())) {
			queryParams.put("startRow", this.getStartRow());
			queryParams.put("pageSize", this.getPageSize());
		}
		return queryParams;
	}

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	public String getOrderPeopleNum() {
		return orderPeopleNum;
	}

	public void setOrderPeopleNum(String orderPeopleNum) {
		this.orderPeopleNum = orderPeopleNum;
	}

	public String getPredictNum() {
		return predictNum;
	}

	public void setPredictNum(String predictNum) {
		this.predictNum = predictNum;
	}

	public String getPrecisionRate() {
		return precisionRate;
	}

	public void setPrecisionRate(String precisionRate) {
		this.precisionRate = precisionRate;
	}

	public String getQueryFiled() {
		return queryFiled;
	}

	public void setQueryFiled(String queryFiled) {
		this.queryFiled = queryFiled;
	}

	public String getQueryFiledValue() {
		return queryFiledValue;
	}

	public void setQueryFiledValue(String queryFiledValue) {
		this.queryFiledValue = queryFiledValue;
	}

	public String getOrderFiled() {
		return orderFiled;
	}

	public void setOrderFiled(String orderFiled) {
		this.orderFiled = orderFiled;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	
}
