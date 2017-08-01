package com.wintv.lottery.admin.bet.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.lottery.admin.bet.service.BetAdminService;
import com.wintv.lottery.bet.service.BetService;

@SuppressWarnings("serial")
public class BaseBetAction extends BaseAction {
	private Integer pageSize=10;// 页面显示数据条数
	private Integer startRow=1;// 查询开始行
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
	@Autowired
    private BetAdminService betAdminService;
	@Autowired
	private BetService betService;
	public BetAdminService getBetAdminService() {
		return betAdminService;
	}
	public void setBetAdminService(BetAdminService betAdminService) {
		this.betAdminService = betAdminService;
	}
	public BetService getBetService() {
		return betService;
	}
	public void setBetService(BetService betService) {
		this.betService = betService;
	}
	
}
