package com.wintv.framework.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Dictionary implements Serializable {

	

	private Long dicId;
	private String code;
	private String value;
	private String zhDesc;
	private String type;
	/**
	 * @return the dicId
	 */
	public Long getDicId() {
		return dicId;
	}
	/**
	 * @param dicId the dicId to set
	 */
	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the zhDesc
	 */
	public String getZhDesc() {
		return zhDesc;
	}
	/**
	 * @param zhDesc the zhDesc to set
	 */
	public void setZhDesc(String zhDesc) {
		this.zhDesc = zhDesc;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


}