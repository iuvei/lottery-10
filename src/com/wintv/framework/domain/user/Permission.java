package com.wintv.framework.domain.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



public class Permission  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8527470097645590764L;
	private Long id;
	private String name;
	private String descn;
	private Set<Role> roles = new HashSet<Role>();
	private Set<Resource> resources = new HashSet<Resource>();
	
	public Permission(){}
    
    public Permission(String name, String descn){
    	this.name = name;
    	this.descn = descn;
    }
    
    public void addRole(Role role){
    	if(!this.getRoles().contains(role)){
    		this.getRoles().add(role);
    	}
    }
    
    public void removeRole(Role role){
    	if(this.getRoles().contains(role)){
    		this.getRoles().remove(role);
    	}
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if(!(o instanceof Permission)) return false;
		final Permission permis = (Permission)o;
		    return permis.getName().equals(this.getName());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
	    .append("name", this.getName())
	    .append("desc", this.getDescn()).toString();
	}

}
