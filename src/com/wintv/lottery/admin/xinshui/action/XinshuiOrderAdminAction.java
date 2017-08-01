package com.wintv.lottery.admin.xinshui.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BackendUser;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.User;
import com.wintv.lottery.user.service.UserService;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.XinshuiDetailVO;
import com.wintv.lottery.xinshui.vo.XinshuiOrderVO;
import com.wintv.framework.common.Constants;
/**
 * 心水订单管理模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class XinshuiOrderAdminAction extends BaseAction{
	private static final long serialVersionUID = 8864308748076180059L;
	private Long orderId;//订单Id
	private Long xinshuiId;//心水Id
	private Long userId;//心水购买人Id
	private String startTimefrom;// 开赛时间开始
	private String starTimeto;// 开赛时间结束

	private String orderTimefrom;// 订购时间开始
	private String orderTimeto;// 订购时间结束
	
	private Long raceId = 0L;// 联赛/杯赛名称ID
	private String status;// 状态
	private String xinshuiType;//心水类型
	
	private String queryFiled = "";// 按球队名
	private String queryFiledValue = "";// 检索关键字段值 按球队名

	private String orderFiled;// 排序字段
	private String orderType;// 排序类型'ASC'|'DESC'

	private Integer pageSize=10;// 页面显示数据条数
	private Integer startRow=0;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计
	private User user;//心水购买人
	private XinshuiDetailVO xinshui;//购买的心水信息
	@Autowired
	private XinshuiService xinshuiService;
	@Autowired
	private UserService userService;
	public String excute() {
		if (isNotNull(this.getUserId())) {
			user=userService.findUserByUserId(this.getUserId());
		}else if(isNotNull(this.getXinshuiId())){
			try{
				xinshui=xinshuiService.loadXinshuiDetailVO(this.getXinshuiId());
			}catch(Exception e){
				e.printStackTrace();
				return "input";
			}
		}
		return SUCCESS;
	}
	/**
	 * 获取心水订单列表
	 * 
	 */
	public String getXinshuiOrderList() {
		try {
			Map params = generateQueryParamsMap();
			Integer total = 0;
			if (this.isCount == 1) {
				total = xinshuiService.findBackendXinshuiOrderSize(params);
			}
			List orderList = xinshuiService.findBackendXinshuiOrderList(params);
			if (isNotNull(orderList, total)) {
				Map result = new HashMap();
				result.put("totalCount", total);
				result.put("dataList", orderList);
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
	 * 获取订单详情
	 */
	public String getOrderDetail() {
		try {
			if (isNotNull(this.getOrderId())) {
				Long orderId = this.getOrderId();
				//xinshuiService
				generateResult(1, MSG_SUCCESS, "orderId");
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
	 * 确认订单支付
	 */
	public String confirmOrderOperation() {//TODO
		try {
			if (isNotNull(this.getOrderId())) {
				Long orderId = this.getOrderId();
				CSHandleLog csHandleLog = generateCsHandleLog("确认心水订单操作");
				csHandleLog.setLogId(orderId);
				String result=xinshuiService.backendXinshuiConfirmPay(orderId, csHandleLog);
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
	/**
	 * 取消订单操作
	 */
	public String cancelOrderOperation() {
		try {
			if (isNotNull(this.getOrderId())) {
				Long orderId = this.getOrderId();
				CSHandleLog csHandleLog = generateCsHandleLog("取消心水订单操作");
				csHandleLog.setLogId(orderId);
				xinshuiService.cancelXinshuiOrder(orderId,csHandleLog);
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
	/**
	 * 生成客服心水订单模块操作记录
	 */
	private CSHandleLog generateCsHandleLog(String memo) {
		BackendUser admin = (BackendUser) session.get("admin");
		CSHandleLog csHandleLog = new CSHandleLog();
		csHandleLog.setCsUserId(admin.getUserid());
		csHandleLog.setCsName(admin.getName());
		csHandleLog.setIp(request.getRemoteAddr());
		csHandleLog.setMemo(memo);
		csHandleLog.setHandleTime(new Date());
		csHandleLog.setType(Constants.ORDER_CATEGORY_BUY_C2C);
		return csHandleLog;
	}
	/**
	 * 后台心水订购页面查询参数
	 */
	private Map<String, Object> generateQueryParamsMap() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		if (isNotNull(this.getRaceId()) && this.getRaceId() != 0L) {
			queryParams.put("raceId", this.getRaceId());
		}
		if (isNotNull(this.getXinshuiId()) && this.getXinshuiId() != -1L) {
			queryParams.put("c2cId",this.getXinshuiId());
		}
		if (isNotNull(this.getUserId())) {
			queryParams.put("buy_user_id",this.getUserId());
		}
		if (isNotNull(this.getStatus()) && !("0").equals(this.getStatus())) {
			queryParams.put("status", this.getStatus());
		}
		
		if (isNotNull(this.getStartTimefrom()) && !("").equals(this.getStartTimefrom())) {
			queryParams.put("fromStartTime", this.getStartTimefrom());
		}
		if (isNotNull(this.getStarTimeto()) && !("").equals(this.getStarTimeto())) {
			queryParams.put("toStartTime", this.getStarTimeto());
		}
		
		if (isNotNull(this.getOrderTimefrom()) && !("").equals(this.getOrderTimefrom())) {
			queryParams.put("fromOrderTime", this.getOrderTimefrom());
		}
		if (isNotNull(this.getOrderTimeto()) && !("").equals(this.getOrderTimeto())) {
			queryParams.put("toOrderTime", this.getOrderTimeto());
		}
		
		if (isNotNull(this.getQueryFiledValue(),this.getQueryFiled()) && !("").equals(this.getQueryFiledValue())) {
			queryParams.put("flg", this.getQueryFiled());
			queryParams.put("key", this.getQueryFiledValue());
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
	public String getStartTimefrom() {
		return startTimefrom;
	}
	public void setStartTimefrom(String startTimefrom) {
		this.startTimefrom = startTimefrom;
	}
	public String getStarTimeto() {
		return starTimeto;
	}
	public void setStarTimeto(String starTimeto) {
		this.starTimeto = starTimeto;
	}
	public String getOrderTimefrom() {
		return orderTimefrom;
	}
	public void setOrderTimefrom(String orderTimefrom) {
		this.orderTimefrom = orderTimefrom;
	}
	public String getOrderTimeto() {
		return orderTimeto;
	}
	public void setOrderTimeto(String orderTimeto) {
		this.orderTimeto = orderTimeto;
	}
	public Long getRaceId() {
		return raceId;
	}
	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getXinshuiType() {
		return xinshuiType;
	}
	public void setXinshuiType(String xinshuiType) {
		this.xinshuiType = xinshuiType;
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
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getXinshuiId() {
		return xinshuiId;
	}
	public void setXinshuiId(Long xinshuiId) {
		this.xinshuiId = xinshuiId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public XinshuiDetailVO getXinshui() {
		return xinshui;
	}
	public void setXinshui(XinshuiDetailVO xinshui) {
		this.xinshui = xinshui;
	}
	
}
