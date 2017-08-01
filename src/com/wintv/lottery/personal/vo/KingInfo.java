package com.wintv.lottery.personal.vo;

public class KingInfo {
  private String betCategory;//玩法
  private String type;//单式 还是复式
  private long dzCnt;//已经定制人数
public String getBetCategory() {
	return betCategory;
}
public void setBetCategory(String betCategory) {
	this.betCategory = betCategory;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public long getDzCnt() {
	return dzCnt;
}
public void setDzCnt(long dzCnt) {
	this.dzCnt = dzCnt;
}
}
