package com.wintv.lottery.xinshui.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BackendUser;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.XinshuiDetailVO;

@SuppressWarnings("unchecked")
public class XinshuiReleaseManageAction extends BaseAction {
	private static final long serialVersionUID = 5611197510400317981L;
	private Long parentId;//race父级Id
	private Long xinshuiId;//心水Id
	private Long againstId=-1L;//对阵赛事Id
	private Long userId=-1L;//发布人Id
	
	// 赛事选择查询参数
	private Long raceId = -1L;// 联赛/杯赛名称ID
	private String status;// 状态
	private String xinshuiSureMoney;//保证金
	private String xinshuiType;//心水类型
	private String xinshuiPrice;//心水价格
	
	private String startTimefrom;// 开赛时间开始
	private String starTimeto;// 开赛时间结束
	
	private String deployTimefrom;// 发布时间开始
	private String deployTimeto;// 发布时间结束

	private String queryFiled = "";// 按球队名
	private String queryFiledValue = "";// 检索关键字段值 按球队名

	private String orderFiled;// 排序字段
	private String orderType;// 排序类型'ASC'|'DESC'

	private Integer pageSize=10;// 页面显示数据条数
	private Integer startRow=0;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计
    
	private String zhDesc="";//心水中文说明
	@Autowired
	private XinshuiService xinshuiService;
	/**
	 * 获取当前用户心水列表
	 * 
	 */
	public String getCurrentUserXinshuiList() {
		try {
			Map params = generateXinshuiListQueryParamsMap();
			Integer total = 0;
			if (this.isCount == 1) {
				total = xinshuiService.findC2CProductSize(params);
			}
			Integer startRow=(Integer)params.get("startRow");
			Integer pageSize=(Integer)params.get("pageSize");
			List xinshuiList = xinshuiService.findC2CProductList(params,startRow,pageSize);
			if (isNotNull(xinshuiList, total)) {
				Map result = new HashMap();
				result.put("totalCount", total);
				result.put("dataList", xinshuiList);
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
	 * 根据心水Id获取某条心水详细
	 */
	public String getXinshuiDetail(){
		try {
			if (isNotNull(this.getXinshuiId())) {
				Long xinshuiId = this.getXinshuiId();
				XinshuiDetailVO result=xinshuiService.loadXinshuiDetailVO(xinshuiId);
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
	 * 判断当前c2c产品用户是否允许查看
	 */
	public String isAllowLook() {
		try {
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser)) {
				Integer result=0;//1:可以查看  0:没有权限查看  -1:没有登录
				if (isNotNull(this.getXinshuiId())) {
					result = xinshuiService.isAllowLook(this.getXinshuiId(),currentUser.getUserId());
					generateResult(1, MSG_SUCCESS, result);
				}else{
					generateResult(0, MSG_FAILURE, "errors");
				}
			} else {
				generateResult(1, MSG_SUCCESS, -1);
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 后台心水赛事页面查询参数
	 */
	private Map<String, Object> generateXinshuiListQueryParamsMap() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		UserCookie currentUser = (UserCookie)session.get("userCookie");
		if(isNotNull(currentUser)&&currentUser.getUserId()!=null){
			queryParams.put("currentUserId", currentUser.getUserId());
		}
		//queryParams.put("currentUserId", 162L);
		if (isNotNull(this.getRaceId()) && this.getRaceId() != -1L) {
			queryParams.put("raceId", this.getRaceId());
		}
		if (isNotNull(this.getAgainstId()) && this.getAgainstId() != -1L) {
			queryParams.put("againstId", this.getAgainstId());
		}
		if (isNotNull(this.getUserId()) && this.getUserId() != -1L) {
			queryParams.put("pubUserId", this.getUserId());
		}
		if (isNotNull(this.getStatus()) && !("0").equals(this.getStatus())) {
			queryParams.put("status", this.getStatus());
		}
		if (isNotNull(this.getXinshuiType()) && !("0").equals(this.getXinshuiType())) {
			queryParams.put("type", this.getXinshuiType());
		}
		if (isNotNull(this.getStartTimefrom()) && !("").equals(this.getStartTimefrom())) {
			queryParams.put("fromTime", this.getStartTimefrom());
		}
		if (isNotNull(this.getStarTimeto()) && !("").equals(this.getStarTimeto())) {
			queryParams.put("toTime", this.getStarTimeto());
		}
		
		if (isNotNull(this.getDeployTimefrom()) && !("").equals(this.getDeployTimefrom())) {
			queryParams.put("fromDay", this.getDeployTimefrom());
		}
		if (isNotNull(this.getDeployTimeto()) && !("").equals(this.getDeployTimeto())) {
			queryParams.put("toDay", this.getDeployTimeto());
		}
		
		if (isNotNull(this.getXinshuiSureMoney())&& !("-1").equals(this.getXinshuiSureMoney())) {
			String[] sureMoney=this.getXinshuiSureMoney().split("~");
			if(sureMoney.length==2){
				queryParams.put("minEnsureMoney", sureMoney[0]);
				queryParams.put("maxEnsureMoney", sureMoney[1]);
			}
		}
		if (isNotNull(this.getXinshuiPrice())&& !("-1").equals(this.getXinshuiPrice())) {
			String[] xinshuiPrice=this.getXinshuiPrice().split("~");
			if(xinshuiPrice.length==2){
				queryParams.put("minPrice", xinshuiPrice[0]);
				queryParams.put("maxPrice", xinshuiPrice[1]);
			}
		}
		if (isNotNull(this.getQueryFiled(),this.getQueryFiledValue()) && !("").equals(this.getQueryFiledValue())) {
			queryParams.put(this.getQueryFiled(), this.getQueryFiledValue());
		}
		
		if (isNotNull(this.getOrderFiled(), this.getOrderType())) {
			queryParams.put("flg", this.getOrderFiled());
			queryParams.put("sortType", this.getOrderType());
		}
		if (isNotNull(this.getPageSize(), this.getStartRow())) {
			queryParams.put("startRow", this.getStartRow());
			queryParams.put("pageSize", this.getPageSize());
		}
		return queryParams;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getXinshuiId() {
		return xinshuiId;
	}
	public void setXinshuiId(Long xinshuiId) {
		this.xinshuiId = xinshuiId;
	}
	public Long getAgainstId() {
		return againstId;
	}
	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getXinshuiSureMoney() {
		return xinshuiSureMoney;
	}
	public void setXinshuiSureMoney(String xinshuiSureMoney) {
		this.xinshuiSureMoney = xinshuiSureMoney;
	}
	public String getXinshuiType() {
		return xinshuiType;
	}
	public void setXinshuiType(String xinshuiType) {
		this.xinshuiType = xinshuiType;
	}
	public String getXinshuiPrice() {
		return xinshuiPrice;
	}
	public void setXinshuiPrice(String xinshuiPrice) {
		this.xinshuiPrice = xinshuiPrice;
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
	public String getDeployTimefrom() {
		return deployTimefrom;
	}
	public void setDeployTimefrom(String deployTimefrom) {
		this.deployTimefrom = deployTimefrom;
	}
	public String getDeployTimeto() {
		return deployTimeto;
	}
	public void setDeployTimeto(String deployTimeto) {
		this.deployTimeto = deployTimeto;
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
	public String getZhDesc() {
		return zhDesc;
	}
	public void setZhDesc(String zhDesc) {
		this.zhDesc = zhDesc;
	}
	
}
