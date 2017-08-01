package com.wintv.framework.domain.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.wintv.framework.pojo.User;

public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7374499827362221936L;
	private Long id;
	private Long version;
	private String name;
	private String desc;
	private Set<User> users = new HashSet<User>();
	private Set<Permission> permissions = new HashSet<Permission>();
	
    
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	
	public Role(String name, String desc){
		this.name = name;
		this.desc = desc;
	}
	
	public Role(){}

	
	public void addPermission(Permission permission){
		if(!getPermissions().contains(permission)){
			getPermissions().add(permission);
		}
	}
	
	public void removeResource(Permission permission){
		if(getPermissions().contains(permission)){
			getPermissions().remove(permission);
		}
	}
	
	

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public void addUser(User user){
		if(!getUsers().contains(user)){
			getUsers().add(user);
		}
	}
	
	public void removeUser(User user){
		if(getUsers().contains(user)){
			getUsers().remove(user);
		}
	}
	
	public Set<User> getUsers(){
		return users;
	}
	
	public void setUsers(Set<User> users){
		this.users = users;
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

	protected void setName(String name) {
		this.name = name;
	}


	public int hashCode() {
		return  31*name.hashCode();  
	}


	public boolean equals(Object o) {
		if (this == o) return true;
		if(!(o instanceof Role)) return false;
		final Role role = (Role)o;
		return  role.getName().equals(this.getName());
	}


	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		    .append("name", this.getName())
		    .append("desc", this.getDesc()).toString();
	}
    

}
