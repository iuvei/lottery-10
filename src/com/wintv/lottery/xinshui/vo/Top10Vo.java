package com.wintv.lottery.xinshui.vo;

public class Top10Vo {
	private Long rank;//排名
	private String username;//用户名
	private String souldCnt;//销售量
	private String changci;//场次
	private String winRatio;//胜率
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSouldCnt() {
		return souldCnt;
	}
	public void setSouldCnt(String souldCnt) {
		this.souldCnt = souldCnt;
	}
	public String getChangci() {
		return changci;
	}
	public void setChangci(String changci) {
		this.changci = changci;
	}
	public String getWinRatio() {
		return winRatio;
	}
	public void setWinRatio(String winRatio) {
		this.winRatio = winRatio;
	}
}