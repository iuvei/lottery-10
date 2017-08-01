package com.wintv.lottery.admin.xinshui.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.common.Constants;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BackendUser;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.lottery.xinshui.service.XinshuiService;

/**
 * 心水赛事管理模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class XinshuiAgainstAdminAction extends BaseAction {
	private static final long serialVersionUID = -475572983865950898L;
	@Autowired
	private XinshuiService xinshuiService;
    private Long againstId;//对阵ID
	// 赛事选择查询参数
	private Long raceId = 0L;// 联赛/杯赛名称ID
	private Long raceSeason = 0L;// 赛季/届ID
	private Long rounds = 0L;// 轮次id
	private String status;// 状态
	private String fromTime;// 开赛时间开始
	private String toTime;// 开赛时间结束

	private String queryFiled = "";// 按球队名
	private String queryFileValue = "";// 检索关键字段值 按球队名

	private String orderFiled;// 排序字段
	private String orderType;// 排序类型'ASC'|'DESC'

	private Integer pageSize;// 页面显示数据条数
	private Integer startRow;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计

	private String checkedIds = "";// 选中的对阵赛事Id，中间用逗号分开

	public String excute() {
		return SUCCESS;
	}

	/**
	 * 生成心水赛事操作
	 */
	public String genXinshuiSchedule() {
		try {
			if (isNotNull(this.getCheckedIds())) {
				String result=xinshuiService.genXinshuiSchedule(this.getCheckedIds().trim());
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
	 * 后台心水赛事选择查询页面-赛事选择管理模块
	 * 
	 */
	public String getXinshuiAgainstChoiceList() {
		try {
			Map params = generateXinshuiAgainstListQueryParamsMap();
			List result = xinshuiService.findBackendAgainstList(params);
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
	 * 后台心水赛事查询页面-生成的赛事管理模块
	 * 
	 */
	public String getXinshuiAgainstList() {
		try {
			Map params = generateXinshuiAgainstListQueryParamsMap();
			Integer total = 0;
			if (this.isCount == 1) {
				total = xinshuiService.findBackendXinshuiAgainstSize(params);
			}
			List againstList = xinshuiService
					.findBackendXinshuiAgainstList(params);
			if (isNotNull(againstList, total)) {
				Map result = new HashMap();
				result.put("totalCount", total);
				result.put("dataList", againstList);
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
     * 赛事管理页面列表 点击”取消“操作
     */
	public String cancelAgainst() {
		try {
			if (isNotNull(this.getAgainstId())) {
				CSHandleLog csHandleLog = generateCsHandleLog();
				xinshuiService.cancelAgainst(this.getAgainstId(),csHandleLog);
				generateResult(1, MSG_SUCCESS, "取消操作成功");
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
		csHandleLog.setMemo("心水取消");
		csHandleLog.setHandleTime(new Date());
		return csHandleLog;
	}
	/**
	 * 后台心水赛事页面查询参数
	 */
	private Map<String, Object> generateXinshuiAgainstListQueryParamsMap() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		if (isNotNull(this.getRaceId()) && this.getRaceId() != 0L) {
			queryParams.put("raceId", this.getRaceId());
		}
		if (isNotNull(this.getRaceSeason()) && this.getRaceSeason() != 0L) {
			queryParams.put("raceSeason", this.getRaceSeason());
		}
		if (isNotNull(this.getRounds()) && this.getRounds() != 0L) {
			queryParams.put("rounds", this.getRounds());
		}
		if (isNotNull(this.getStatus()) && !("0").equals(this.getStatus())) {
			queryParams.put("status", this.getStatus());
		}
		if (isNotNull(this.getFromTime()) && !("").equals(this.getFromTime())) {
			queryParams.put("from", this.getFromTime());
		}
		if (isNotNull(this.getToTime()) && !("").equals(this.getToTime())) {
			queryParams.put("to", this.getToTime());
		}
		if (isNotNull(this.getQueryFileValue())
				&& !("").equals(this.getQueryFileValue())) {
			queryParams.put("teamName", this.getQueryFileValue());
		}
		if (isNotNull(this.getOrderFiled(), this.getOrderType())) {
			queryParams.put("orderFiled", this.getOrderFiled());
			queryParams.put("orderType", this.getOrderType());
		}
		if (isNotNull(this.getPageSize(), this.getStartRow())) {
			queryParams.put("startRow", this.getStartRow() - 1);
			queryParams.put("pageSize", this.getPageSize());
		}
		return queryParams;
	}

	public Long getRaceId() {
		return raceId;
	}

	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}

	public Long getRaceSeason() {
		return raceSeason;
	}

	public void setRaceSeason(Long raceSeason) {
		this.raceSeason = raceSeason;
	}

	public Long getRounds() {
		return rounds;
	}

	public void setRounds(Long rounds) {
		this.rounds = rounds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
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

	public Integer getIsCount() {
		return isCount;
	}

	public void setIsCount(Integer isCount) {
		this.isCount = isCount;
	}

	public String getCheckedIds() {
		return checkedIds;
	}

	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}

	public String getQueryFiled() {
		return queryFiled;
	}

	public void setQueryFiled(String queryFiled) {
		this.queryFiled = queryFiled;
	}

	public String getQueryFileValue() {
		return queryFileValue;
	}

	public void setQueryFileValue(String queryFileValue) {
		this.queryFileValue = queryFileValue;
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

	public Long getAgainstId() {
		return againstId;
	}

	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}

}
