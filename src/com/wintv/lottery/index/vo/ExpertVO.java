package com.wintv.lottery.index.vo;

public class ExpertVO {
	private String expertClass;//专家分类
    private Long expertId;//主键
    private String introduction;//专家介绍 
    private String expertName;//专家姓名
	public String getExpertClass() {
		return expertClass;
	}
	public void setExpertClass(String expertClass) {
		this.expertClass = expertClass;
	}
	public Long getExpertId() {
		return expertId;
	}
	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
}
