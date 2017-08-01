package com.wintv.framework.pojo;

@SuppressWarnings("serial")
public class BetHotSearch  implements java.io.Serializable {

	private Long hotId;//主键
	private Long userId; 
	private String username;
	private String status;   //状态  是否是热门搜索人
	public Long getHotId() {
		return hotId;
	}
	public void setHotId(Long hotId) {
		this.hotId = hotId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
