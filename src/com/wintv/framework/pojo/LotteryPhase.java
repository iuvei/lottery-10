package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
/**
 * 彩票期次
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class LotteryPhase implements Serializable {
    
	private Long id;//主键
	private Date soldTime;//开售时间
	private String phaseNo;//期次
	private Date mulDeadline;//复式截止时间
	private Date singleDeadline;//单式截止时间
	private Date tkpDeadline;//打票截至时间
	private String isCurrent;//是否为当前期次    同一彩种  同一时刻  不可能存在两种 "当期期次"
	private String category;//期次分类  '6':胜负彩期次    '9':进球彩期次 '8':半全场期次 '1':北京单场期次
	private String status;//期次状态 默认为'1'  期次未审核:期次状态  '1':期次未审核、'2':对阵未审核、'3':赛果未审核、'4':已公布、'5':已停止、'6':已作废、'7':已到期、'8':已完成
	private String username;//期次添加人
	private Date addTime;//添加时间
	private Set againstList;
	private Long viciDeadline;//普通过关截止时间(只针对北京单场)
	private Long comboVici;//组合与自由过关(只针对北京单场)
	/**
	 * 是否为当前期次,  A.(刚发布 尚未发布，此时 ISCURRENT='1')'1':为当前期次而且打票时间>当前时间
	 * 或者     打票时间>当前时间  且 预售时间<当前时间
	 */
	private String phaseType;//期次类型  为‘1’ 为当前期次  注意  不可靠
	private String kjNo;//开奖号码
	private Date  kjTime;//开奖日期
	private Integer kjStatus;//1:已经开奖  2  开奖 号码已经确认  3  尚未开奖
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getSoldTime() {
		return soldTime;
	}
	public void setSoldTime(Date soldTime) {
		this.soldTime = soldTime;
	}
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public Date getMulDeadline() {
		return mulDeadline;
	}
	public void setMulDeadline(Date mulDeadline) {
		this.mulDeadline = mulDeadline;
	}
	
	public Date getTkpDeadline() {
		return tkpDeadline;
	}
	public void setTkpDeadline(Date tkpDeadline) {
		this.tkpDeadline = tkpDeadline;
	}
	public String getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Set getAgainstList() {
		return againstList;
	}
	public void setAgainstList(Set againstList) {
		this.againstList = againstList;
	}
	public Long getViciDeadline() {
		return viciDeadline;
	}
	public void setViciDeadline(Long viciDeadline) {
		this.viciDeadline = viciDeadline;
	}
	public Long getComboVici() {
		return comboVici;
	}
	public void setComboVici(Long comboVici) {
		this.comboVici = comboVici;
	}
	public String getPhaseType() {
		return phaseType;
	}
	public void setPhaseType(String phaseType) {
		this.phaseType = phaseType;
	}
	public Date getSingleDeadline() {
		return singleDeadline;
	}
	public void setSingleDeadline(Date singleDeadline) {
		this.singleDeadline = singleDeadline;
	}
	public String getKjNo() {
		return kjNo;
	}
	public void setKjNo(String kjNo) {
		this.kjNo = kjNo;
	}
	public Date getKjTime() {
		return kjTime;
	}
	public void setKjTime(Date kjTime) {
		this.kjTime = kjTime;
	}
	public Integer getKjStatus() {
		return kjStatus;
	}
	public void setKjStatus(Integer kjStatus) {
		this.kjStatus = kjStatus;
	}

 
}