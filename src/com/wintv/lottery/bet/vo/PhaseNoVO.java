package com.wintv.lottery.bet.vo;

public class PhaseNoVO {
	private Long id;
    private String flg;//标志位  '0':过期 '1':当前期   '2':预售期次 
	private String phaseNo;
	private String singleDeadline;//单式截止时间
	private String mulDeadline;//复式截止时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFlg() {
		return flg;
	}
	public void setFlg(String flg) {
		this.flg = flg;
	}
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public String getSingleDeadline() {
		return singleDeadline;
	}
	public void setSingleDeadline(String singleDeadline) {
		this.singleDeadline = singleDeadline;
	}
	public String getMulDeadline() {
		return mulDeadline;
	}
	public void setMulDeadline(String mulDeadline) {
		this.mulDeadline = mulDeadline;
	}

}
