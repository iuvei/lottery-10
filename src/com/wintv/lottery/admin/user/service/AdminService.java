package com.wintv.lottery.admin.user.service;

import java.util.List;

import com.wintv.framework.pojo.BackendUser;

/**
 * 管理员用户模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public interface AdminService {
	/**
	 * 根据角色获取相应管理用户列表
	 * 
	 * @return
	 */
	public List<BackendUser> getAdminUsersByRole(String role);

	/**
	 * 通过Id获取相应的管理员
	 */
	public BackendUser getAdminByUserId(Long userId);
	/**
	 * 根据查询字段名与值获取相应管理用户列表
	 * 
	 * @return
	 */
	public List<BackendUser> getAdminUsersByQueryFiled(String filed,Object object);
}
