package com.wintv.framework.pojo;
/**
 * 地区表（洲 国家 省直辖市，以及地级市）
 * 
 *
 */
@SuppressWarnings("serial")
public class Code implements java.io.Serializable {

	private Long id;//主键
	private String name;//中文名称(可能是省   地级市   国家   洲)
	private Long parentId;//父节点
	private String root;
	private Long zone;
	private String type;//类型'1':洲 '2':国家
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
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public Long getZone() {
		return zone;
	}
	public void setZone(Long zone) {
		this.zone = zone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) 
			return false;
		if (!(obj instanceof Code)) 
			return false;
		else {
			Code code = (Code) obj;
			if (null == this.getId() || null == code.getId()) 
				return false;
			else 
				return (this.getId().equals(code.getId()));
		}
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.getId().hashCode() + this.getName().hashCode();
	}

}