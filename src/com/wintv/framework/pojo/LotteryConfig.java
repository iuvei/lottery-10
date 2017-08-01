package com.wintv.framework.pojo;

import java.io.Serializable;
import java.lang.Long;


@SuppressWarnings("serial")
public class LotteryConfig implements Serializable {
    
	private Long lcId;//主键
	private String caiType;//彩种
	private String curPhase;//当前期
	private String lastPhase;//上一期
	private String nextPhase;//下一期
	private String status;//状态
	
	public LotteryConfig() {
	}

	
	public LotteryConfig(String caiType, String curPhase,
			String lastPhase, String nextPhase, String status) {
		this.caiType = caiType;
		this.curPhase = curPhase;
		this.lastPhase = lastPhase;
		this.nextPhase = nextPhase;
		this.status = status;
	}

	

	public Long getLcId() {
		return this.lcId;
	}

	public void setLcId(Long lcId) {
		this.lcId=lcId;
	}

	public String getCaiType() {
		return this.caiType;
	}

	public void setCaiType(String caiType) {
		this.caiType = caiType;
	}

	public String getCurPhase() {
		return this.curPhase;
	}

	public void setCurPhase(String curPhase) {
		this.curPhase = curPhase;
	}

	public String getLastPhase() {
		return this.lastPhase;
	}

	public void setLastPhase(String lastPhase) {
		this.lastPhase = lastPhase;
	}

	public String getNextPhase() {
		return this.nextPhase;
	}

	public void setNextPhase(String nextPhase) {
		this.nextPhase = nextPhase;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}