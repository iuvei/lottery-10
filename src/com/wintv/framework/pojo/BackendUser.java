package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * ��̨�û�
 * 网站后台用户
 *
 */
@SuppressWarnings("serial")
public class BackendUser implements Serializable {
    
	private Long userid;
	private String email;
	private String pwd;
	private String role;
	private String name;
	private String mp;
	private String status;
	private String qq;
	private Date regTime;
	private String deptCode;
	private String deptName;

	public BackendUser() {
	}

	 
	public BackendUser(String email, String pwd, String role) {
		this.email = email;
		this.pwd = pwd;
		this.role = role;
	}

	 
	public BackendUser(String email, String pwd, String role,
			String name, String mp, String status, String qq, Date regTime,
			String deptCode, String deptName) {
		this.email = email;
		this.pwd = pwd;
		this.role = role;
		this.name = name;
		this.mp = mp;
		this.status = status;
		this.qq = qq;
		this.regTime = regTime;
		this.deptCode = deptCode;
		this.deptName = deptName;
	}

	

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMp() {
		return this.mp;
	}

	public void setMp(String mp) {
		this.mp = mp;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}


	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getDeptCode() {
		return deptCode;
	}


	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

}