package com.wintv.lottery.admin.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wintv.framework.pojo.BackendUser;
import com.wintv.lottery.admin.user.dao.AdminDao;
import com.wintv.lottery.admin.user.service.AdminService;

/**
 * 管理员用户模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@Service("adminService")
@SuppressWarnings("unchecked")
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDao adminDao;

	/**
	 * 根据角色获取相应管理用户列表
	 * 
	 * @return
	 */
	public List<BackendUser> getAdminUsersByRole(String role) {
		return adminDao.findBy("role", role);
	}
	/**
	 * 通过Id获取相应的管理员
	 * @param userId
	 * @return
	 */
	public BackendUser getAdminByUserId(Long userId){
		return adminDao.findUniqueBy("userid", userId);
	}
	/**
	 * 根据查询字段名与值获取相应管理用户列表
	 * 
	 * @return
	 */
	public List<BackendUser> getAdminUsersByQueryFiled(String filed,Object object){
		return adminDao.findBy(filed, object);
	}
}
