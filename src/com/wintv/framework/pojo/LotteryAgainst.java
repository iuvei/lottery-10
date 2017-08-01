package com.wintv.framework.pojo;

import java.io.Serializable;
/**
 * 期次对阵信息
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class LotteryAgainst implements Serializable {

	private Long id;
	private Long againstId;//关联对阵表  T_AGAINST.ID
	private Long phaseId;
	private String resultNo;//开奖号码    只能是:    3 ,1 ,0
	private Long changci;//场次    由体彩中心提供   例如 20,21,22,...
    public String getResultNo() {
		return resultNo;
	}
	public void setResultNo(String resultNo) {
		this.resultNo = resultNo;
	}
	public LotteryAgainst() {
	}
    public LotteryAgainst(Long againstId, Long phaseId) {
		this.againstId = againstId;
		this.phaseId = phaseId;
	}
    public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getPhaseId() {
		return this.phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}
	public Long getAgainstId() {
		return againstId;
	}
	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}
	public Long getChangci() {
		return changci;
	}
	public void setChangci(Long changci) {
		this.changci = changci;
	}

}