package com.wintv.framework.pojo;

import java.io.Serializable;

/**
 * 联赛或杯赛球队表
 * 
 *
 */
@SuppressWarnings("serial")
public class Race implements Serializable {

	private Long id;//主键
	private String name;//赛季名称或轮次(联赛与杯赛等)
	private Long parentId;
	private String type;
	private String prefix;
	private Long scheduleId;
	private Long districtId;
	private Long countryId;
	private String district;//关联区域名称
	private String country;//关联国家名称
	public Race(){}
	public Race(Long id,String name){
		this.id=id;
		this.name=name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	@Override
	public int hashCode() {
		if(parentId!=null){
		return super.hashCode()+id.hashCode()+name.hashCode()+parentId.hashCode();
		}else{
			return super.hashCode()+id.hashCode()+name.hashCode();
		}
	}
 
	@Override
	public boolean equals(Object obj) {
		if(this == obj)return true;
		if(obj == null)return false;
		if(getClass() != obj.getClass())return false;
		final Race other = (Race) obj;
		if(!id.equals(other.id))return false;
		if(!name.equals(other.getName()))return false;
		if(!parentId.equals(other.getParentId()))return false;
		return true;
	}
}