package com.wintv.framework.domain.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;




public class Resource  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8794889316967678835L;
	private Long id;
	private String name;
	private String desc; 
	private String type;
	private String resString;
	private Set<Permission> permissions = new HashSet<Permission>();
	
	public Resource(){}
	
	public Resource(String name, String desc, String type, String resString){
		this.name = name;
		this.desc = desc;
		this.type = type;
		this.resString = resString;
	}
	
	
	public void addPermission(Permission permis){
		if(!getPermissions().contains(permis)){
			getPermissions().add(permis);
		}
	}
	
	public void removePermission(Permission permis){
		if(getPermissions().contains(permis)){
			getPermissions().remove(permis);
		}
	}
	
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResString() {
		return resString;
	}

	public void setResString(String resString) {
		this.resString = resString;
	}

	public int hashCode() {
		return name.hashCode();
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if(!(o instanceof Resource)) return false;
		final Resource res = (Resource)o;
		return res.getName().equals(this.getName()) && res.getType().equals(this.getType());
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		    .append("name", this.getName())
		    .append("desc", this.getDesc())
		    .append("type",this.getType())
		    .append("resString", this.getResString()).toString();
	}


}
