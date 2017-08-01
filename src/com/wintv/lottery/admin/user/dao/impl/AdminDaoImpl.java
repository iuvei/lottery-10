package com.wintv.lottery.admin.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.pojo.BackendUser;
import com.wintv.lottery.admin.user.dao.AdminDao;

/**
 * 网站后台管理员Dao
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@Repository("adminDao")
@SuppressWarnings("unchecked")
public class AdminDaoImpl extends BaseHibernate<BackendUser, Long> implements
		AdminDao {

}
