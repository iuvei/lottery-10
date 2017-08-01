package com.wintv.lottery.b2c.vo;

import java.io.Serializable;
@SuppressWarnings("serial")
public class ExpertOrderVO  implements Serializable{
	private String expertName;
	private String photo;//照片路径,例如/upload/b2c/photo/userId/**.gif,jpg add by hikin yao
	private String introduction;
	private String endTime;
	private String monthPack;
	private Long expertId;//主键
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getMonthPack() {
		return monthPack;
	}
	public void setMonthPack(String monthPack) {
		this.monthPack = monthPack;
	}
	public Long getExpertId() {
		return expertId;
	}
	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expertId == null) ? 0 : expertId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ExpertOrderVO other = (ExpertOrderVO) obj;
		if (expertId == null) {
			if (other.expertId != null)
				return false;
		} else if (!expertId.equals(other.expertId))
			return false;
		return true;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
