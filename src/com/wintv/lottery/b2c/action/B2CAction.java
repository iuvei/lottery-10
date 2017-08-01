package com.wintv.lottery.b2c.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.admin.bet.action.BaseBetAction;
import com.wintv.lottery.b2c.service.B2CService;

/**
 * B2c专家推荐功能模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings( { "serial", "unchecked" })
public class B2CAction extends BaseBetAction {
	
	/**
	 * 专家推荐首页的 推荐的专家列表
	 * 
	 * @return
	 */
	public String getRecommendExpertList() {
		try {
			Map params = new HashMap<String, Object>();
			List dataList = b2CService.findRecommendExpertList(params);
			if (isNotNull(dataList)) {
				generateResult(1, MSG_SUCCESS, dataList);
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
	 * 专家推荐首页的 投资超值包
	 * 
	 * @return
	 */
	public String getValuePackList() {
		try {
			Map params = new HashMap<String, Object>();
			params.put("cnt", 2);
			List dataList = b2CService.findValuePackList(params);
			if (isNotNull(dataList)) {
				generateResult(1, MSG_SUCCESS, dataList);
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
	 * 判断当前b2c产品用户是否已经购买
	 * @deprecated
	 */
	public String isAlreadyBuyTheProduct() {
		try {
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser, this.getB2cId())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("c2cId", this.getB2cId());
				params.put("buyUserId", currentUser.getUserId());
				Integer result = 1;// b2CService.isAllowBuy(params) == true ? 1
				// : 2;//1.没有购买，允许购买 2.已经购买，不允许再购买
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
	 * 获取我的订单列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getMyOrderList() {		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser)) {
				params.put("buyUserId", currentUser.getUserId());
			}
			if (isNotNull(this.getPageSize(), this.getStartRow())) {
				params.put("startRow", this.getStartRow());
				params.put("pageSize", this.getPageSize());
			}
			// 判断用户是否已登录
				Long total = 0L;
				if (this.isCount == 1) {
					total = b2CService.countMyB2cOrderListSize(params);
				}
				List dataList =b2CService.getMyB2COrderList(params);
				if (isNotNull(dataList, total)) {
					Map result = new HashMap();
					result.put("totalCount", total);
					result.put("dataList", dataList);
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
	 * 您的内参订单 推荐的专家列表
	 * 
	 * @return
	 */
	public String getMyOrderExpertList() {
		try {
			Map params = new HashMap<String, Object>();
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser)) {
				params.put("buyUserId", currentUser.getUserId());
			}
			List dataList = b2CService.findMyOrderExpertList(params);
			
			if (isNotNull(dataList)) {
				Set result=new HashSet();
				result.addAll(dataList);
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
	 * 取消我的订单
	 */
	public String cancelMyB2cOrder() {
		try {
			if (isNotNull(orderId)) {
				int result =b2CService.cancelB2COrder(orderId); //myAttentionService.cancelAttention(attentionIdList.trim());
				generateResult(1, MSG_SUCCESS, result);
			}else{
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/****************** 专家详细页面 *********************/
	/**
	 * 获取专家详情
	 */
	public String getB2cDetail() {
		try {
			if (isNotNull(this.getExpertId())) {
				Map result =b2CService.loadExpertHomeData(this.getExpertId()); 
				if (isNotNull(result)) {
						generateResult(1, MSG_SUCCESS, result);
				} else {
						generateResult(0, MSG_FAILURE, "errors");
				}
			}else{
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
	 * 获取专家推荐的内参内容列表
	 */
	public String getB2cRecommendContentList() {	
		try {
			if (isNotNull(this.getExpertId())){
				Map params = generateQueryParamsMap();
				Long total = 0L;
				Boolean isAllowLook=isAllowLook(this.getExpertId());
				if (this.isCount == 1) {
					total = b2CService.findXinshuiSize(params);
				}
				if (null==isAllowLook){
					isAllowLook=false;
				}
				params.put("isAllowLook", isAllowLook);
				List dataList = b2CService.findXinshuiList(params);
				if (isNotNull(dataList, total)) {
					Map result = new HashMap();
					result.put("totalCount", total);
					result.put("isAllowLook", isAllowLook);
					result.put("dataList", dataList);
					generateResult(1, MSG_SUCCESS, result);
				} else {
					generateResult(0, MSG_FAILURE, "errors");
				}
			}else{
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
	 * 是否允许用户查看 专家产品详情
	 * @param expertId
	 * @return
	 * @throws Exception
	 */
	private Boolean isAllowLook(Long expertId) throws Exception{
		Boolean result=false;
		UserCookie currentUser = (UserCookie) session.get("userCookie");
		if (isNotNull(currentUser)) {
			result=b2CService.isAllowLook(currentUser.getUserId(), expertId);
		} else {
			result=false;
		}
		return result;
	}
	/**
	 * 专家详细页面 推荐的专家列表
	 * 
	 * @return
	 */
	public String getExpertList() {
		try {
			Map params = new HashMap<String, Object>();
			List dataList = b2CService.findRecommendExpertList(params);
			if (isNotNull(dataList)) {
				generateResult(1, MSG_SUCCESS, dataList);
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
	 * 生成查询参数
	 */
	private Map<String, Object> generateQueryParamsMap() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		UserCookie currentUser = (UserCookie) session.get("userCookie");
		if (isNotNull(currentUser)) {
			queryParams.put("userId", currentUser.getUserId());
			queryParams.put("isLogin", true);
		} else {
			queryParams.put("isLogin", false);
		}
		if (isNotNull(this.getPageSize(), this.getStartRow())) {
			queryParams.put("startRow", this.getStartRow());
			queryParams.put("pageSize", this.getPageSize());
		}
		if(isNotNull(expertId) && expertId != 0L) {
			queryParams.put("expertId", expertId);
		}
		
		if(StringUtils.isNotEmpty(sort) && !"0".equals(sort)) {
			queryParams.put("sort", sort);
		}
		if(StringUtils.isNotBlank(queryFieldValue)) {
			queryParams.put("key", queryFieldValue);
		}
		if(isNotNull(raceId) && raceId != 0L) {
			queryParams.put("raceId", raceId);
		}
		if (isNotNull(this.getStartTimefrom()) && !("").equals(this.getStartTimefrom())) {
			queryParams.put("fromTime", this.getStartTimefrom());
		}
		if (isNotNull(this.getStartTimeto()) && !("").equals(this.getStartTimeto())) {
			queryParams.put("toTime", this.getStartTimeto());
		}
		return queryParams;
	}
	@Autowired
	private B2CService b2CService;
	private Long b2cId;// b2c产品Id
	private Long orderId;// b2c订单Id
	private Integer pageSize = 10;// 页面显示数据条数
	private Integer startRow = 0;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计
	private String queryField = "txUsername";// 检索关键字段 联赛,订购人,订单编号,心水编号,主客队名
	private String queryFieldValue = "";// 检索关键字段值
	private String sort;//排序方式
	private String startTimefrom;// 开赛时间开始
	private String startTimeto;// 开赛时间结束
	private Long expertId;//专家Id
	private Long raceId = 0L;//联赛ID
	public Long getB2cId() {
		return b2cId;
	}

	public void setB2cId(Long id) {
		b2cId = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getStartTimefrom() {
		return startTimefrom;
	}

	public void setStartTimefrom(String startTimefrom) {
		this.startTimefrom = startTimefrom;
	}

	public String getStartTimeto() {
		return startTimeto;
	}

	public void setStartTimeto(String startTimeto) {
		this.startTimeto = startTimeto;
	}

	public Long getExpertId() {
		return expertId;
	}

	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}

	public Long getRaceId() {
		return raceId;
	}

	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}
	
	
}
