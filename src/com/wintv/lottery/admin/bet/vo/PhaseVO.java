package com.wintv.lottery.admin.bet.vo;

import java.io.Serializable;
@SuppressWarnings("serial")
public class PhaseVO  implements Serializable{
  private  long phaseId;
  private String phaseNo;
public long getPhaseId() {
	return phaseId;
}
public void setPhaseId(long phaseId) {
	this.phaseId = phaseId;
}
public String getPhaseNo() {
	return phaseNo;
}
public void setPhaseNo(String phaseNo) {
	this.phaseNo = phaseNo;
}
}
