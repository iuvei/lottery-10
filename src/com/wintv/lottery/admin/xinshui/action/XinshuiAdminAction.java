package com.wintv.lottery.admin.xinshui.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wintv.framework.utils.DefaultCnToSpell;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BackendUser;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.Race;
import com.wintv.framework.pojo.User;
import com.wintv.lottery.admin.team.service.RaceService;
import com.wintv.lottery.user.service.UserService;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.XinshuiAgainstVO;
import com.wintv.lottery.xinshui.vo.XinshuiDetailVO;

/**
 * 心水管理模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class XinshuiAdminAction extends BaseAction {
	private static final long serialVersionUID = -3039635017892105822L;
	private Long parentId;//race父级Id
	
	private Long xinshuiId;//心水Id
	
	private Long againstId=-1L;//对阵赛事Id
	private Long userId=-1L;//发布人Id
	
	// 赛事选择查询参数
	private Long raceId = 0L;// 联赛/杯赛名称ID
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
	
	private String name="";//赛事名称
	private User user;//心水发布人
	private XinshuiAgainstVO xinshuiAgainst;//赛事信息
	
	@Autowired
	private XinshuiService xinshuiService;
	@Autowired
	private RaceService raceService;
	@Autowired
	private UserService userService;

	public String excute() {
		if (isNotNull(this.getUserId())&&this.getUserId()!=-1L) {
			user=userService.findUserByUserId(this.getUserId());
		}else if(isNotNull(this.getAgainstId())&&this.getAgainstId()!=-1L){
			try{
				xinshuiAgainst= xinshuiService.loadXinshuiAgainst(this.getAgainstId());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
    /**
     * 通过赛事名称模糊查询相应赛事
     * @return
     */
	public String getRacesByName(){
		try {
			List<Race> result = null;
			if (isNotNull(this.getName())) {
				result = raceService.findRaceList(this.getName());
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
	 * 获取所有联赛和杯赛信息
	 */
	public String getRacesList(){
		try {
			List<Race> result = raceService.findAllRace();
			if (isNotNull(result)) {
				formatRaceList(result);
				generateResult(1, MSG_SUCCESS, result);
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		}catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 获取联赛名称，轮次，赛季等信息 T_RACE 三级树形菜单
	 * 
	 */
	public String getRacesByParentId() {
		try {
			List<Race> result = null;
			if (isNotNull(this.getParentId())) {
				Long parentId = this.getParentId();
				if (parentId == -1L) {
					result = raceService.findAllRace();
				} else {
					result = raceService.findByParent(parentId);
				}
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
	 * 关闭心水操作
	 * 
	 */
	public String colseXinshui() {
		try {
			if (isNotNull(this.getXinshuiId())) {
				Long xinshuiId = this.getXinshuiId();
				CSHandleLog csHandleLog = generateCsHandleLog();
				xinshuiService.closeXinshui(xinshuiId,csHandleLog.getCsUserId(),csHandleLog.getCsName(),csHandleLog.getIp());
				generateResult(1, MSG_SUCCESS, "ok");
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
	 * 更新某条心水操作
	 * 
	 */
	public String updateXinshui() {
		try {
			if (isNotNull(this.getXinshuiId())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("c2cId", this.getXinshuiId());
				params.put("zhDesc",this.getZhDesc());
				xinshuiService.updateXinshui(params);
				generateResult(1, MSG_SUCCESS, "ok");
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
	 * 通过Id获取相应的心水列表数据
	 * 
	 */
	public String getXinshuiListById() {
		try {
			Map params = generateXinshuiListQueryParamsMap();
			Integer total = 0;
			if (this.isCount == 1) {
				total = xinshuiService.findBackendXinshuiPubRecordSize(params);
			}
			Integer pageSize=(Integer)params.get("pageSize");
			Integer startRow=(Integer)params.get("startRow");
			List xinshuiList = xinshuiService.findBackendXinshuiPubRecordList(params,startRow,pageSize);
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
	 * 生成客服心水模块操作记录
	 */
	private CSHandleLog generateCsHandleLog() {
		BackendUser admin = (BackendUser) session.get("admin");
		CSHandleLog csHandleLog = new CSHandleLog();
		csHandleLog.setCsUserId(admin.getUserid());
		csHandleLog.setCsName(admin.getName());
		csHandleLog.setIp(request.getRemoteAddr());
		csHandleLog.setMemo("心水关闭");
		csHandleLog.setHandleTime(new Date());
		return csHandleLog;
	}
	private void formatRaceList(List<Race> races){
			for(Race one:races){
				String raceName=one.getName();
				if(raceName!=null){
					one.setPrefix(DefaultCnToSpell.cnToSpell(raceName));
				}
			}
	}
	/**
	 * 后台心水赛事页面查询参数
	 */
	private Map<String, Object> generateXinshuiListQueryParamsMap() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		if (isNotNull(this.getRaceId()) && this.getRaceId() != 0L) {
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
			queryParams.put("startFrom", this.getStartTimefrom());
		}
		if (isNotNull(this.getStarTimeto()) && !("").equals(this.getStarTimeto())) {
			queryParams.put("startTo", this.getStarTimeto());
		}
		
		if (isNotNull(this.getDeployTimefrom()) && !("").equals(this.getDeployTimefrom())) {
			queryParams.put("pubFrom", this.getDeployTimefrom());
		}
		if (isNotNull(this.getDeployTimeto()) && !("").equals(this.getDeployTimeto())) {
			queryParams.put("pubTo", this.getDeployTimeto());
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
		if (isNotNull(this.getQueryFiledValue()) && !("").equals(this.getQueryFiledValue())) {
			queryParams.put("pubUsername", this.getQueryFiledValue());
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public RaceService getRaceService() {
		return raceService;
	}

	public void setRaceService(RaceService raceService) {
		this.raceService = raceService;
	}

	public Long getXinshuiId() {
		return xinshuiId;
	}

	public void setXinshuiId(Long xinshuiId) {
		this.xinshuiId = xinshuiId;
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

	public String getXinshuiSureMoney() {
		return xinshuiSureMoney;
	}

	public void setXinshuiSureMoney(String xinshuiSureMoney) {
		this.xinshuiSureMoney = xinshuiSureMoney;
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

	public String getZhDesc() {
		return zhDesc;
	}

	public void setZhDesc(String zhDesc) {
		this.zhDesc = zhDesc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public XinshuiAgainstVO getXinshuiAgainst() {
		return xinshuiAgainst;
	}
	public void setXinshuiAgainst(XinshuiAgainstVO xinshuiAgainst) {
		this.xinshuiAgainst = xinshuiAgainst;
	}
	

}
