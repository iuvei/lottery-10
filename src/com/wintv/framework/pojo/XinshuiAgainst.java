package com.wintv.framework.pojo;

import java.util.Date;

/**
 * 
 * 后台客服通过筛选出热点赛事,提供给民间高手发布心水的赛程表
 *
 */
@SuppressWarnings("serial")
public class XinshuiAgainst implements java.io.Serializable {
    private Long id;//主键
	private Date deadline;//心水发布截至时间
    private Long againstId;//总的对阵ID  关联到表T_AGAINST
    private String xinshuiResult;//心水赛果
    private String panKou;//民间高手发布心水那一刻的盘口  心水结算时以此为准  2010-04-26 10:13
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public Long getAgainstId() {
		return againstId;
	}
	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}
	public String getXinshuiResult() {
		return xinshuiResult;
	}
	public void setXinshuiResult(String xinshuiResult) {
		this.xinshuiResult = xinshuiResult;
	}
	public String getPanKou() {
		return panKou;
	}
	public void setPanKou(String panKou) {
		this.panKou = panKou;
	}
	
}