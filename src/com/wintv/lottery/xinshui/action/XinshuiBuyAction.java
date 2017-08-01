package com.wintv.lottery.xinshui.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.Top10Vo;
import com.wintv.lottery.xinshui.vo.XinshuiSearchVO;

@SuppressWarnings("unchecked")
public class XinshuiBuyAction extends BaseAction {
	private static final long serialVersionUID = -8913657565962218305L;
	@Autowired
	private XinshuiService xinshuiService;
	private Long raceId = 0L;//联赛ID
	private Integer pageSize = 20;// 页面显示数据条数
	private Integer startRow = 0;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计
	private String queryField = "";// 检索关键字段 联赛,订购人,订单编号,心水编号,主客队名
	private String queryFieldValue = "";// 检索关键字段值
	private String sort;//排序方式
	
	private String startTimefrom;// 开赛时间开始
	private String startTimeto;// 开赛时间结束
	private String username;//用户名
	
	/****
	 * 获取c2c心水列表
	 * @return
	 */
	public String searchC2CList() {
		try {
			Map params = this.getParams();
			
			Integer totalCount = 0;//总记录数
			 
			if (this.isCount == 1) {
				totalCount = xinshuiService.findXinshuiSize(params);
			}
			
			//查询心水订单明细
			List<XinshuiSearchVO> xinshuiList = xinshuiService.findXinshuiList(params);
			
			if (isNotNull(xinshuiList, totalCount)) {
				Map result = new HashMap();
				result.put("totalCount", totalCount);
				result.put("xinshuiList", xinshuiList);

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
	
	/****
	 * 获取c2c心水top10
	 * @return
	 */
	public String searchC2CTop10List() {
		try {
			Map params = new HashMap();
			params.put("flg", (String)request.getParameter("flg"));
			
			//查询心水订单明细
			List<Top10Vo> C2CTop10VoList = xinshuiService.findXinshuiWinTop10List(params);
			
			if (isNotNull(C2CTop10VoList)) {
				Map result = new HashMap();
				result.put("C2CTop10VoList", C2CTop10VoList);

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
		
		params.put("startRow", startRow);
		params.put("pageSize", pageSize);
		
		if(StringUtils.isNotEmpty(queryField) && StringUtils.isNotEmpty(queryFieldValue)) {
			params.put("queryField", queryField);
			params.put("queryFieldValue", queryFieldValue);
		}
		
		if(isNotNull(raceId) && raceId != 0L) {
			params.put("raceId", raceId);
		}
		
		if(StringUtils.isNotEmpty(sort) && !"0".equals(sort)) {
			params.put("sort", sort);
		}
		
		if (isNotNull(this.getStartTimefrom()) && !("").equals(this.getStartTimefrom())) {
			params.put("startTimefrom", this.getStartTimefrom());
		}
		if (isNotNull(this.getStartTimeto()) && !("").equals(this.getStartTimeto())) {
			params.put("startTimeto", this.getStartTimeto());
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

	public Long getRaceId() {
		return raceId;
	}

	public void setRaceId(Long raceId) {
		this.raceId = raceId;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
