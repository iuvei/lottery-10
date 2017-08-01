package com.wintv.lottery.user.dao;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.SecurityIp;

/**
 * 登陆IP验证操作接口
 * @author 杨华涛
 * @since 1.0
 */
public interface SecurityIpDao extends BaseDao<SecurityIp,Long> {
	/** 
	 * 同一IP注册当天注册次数
	 * 参数:
	 * ip:注册IP
	 * DaoException:系统异常,例如数据库操作异常等
	 * 返回:返回该IP当天注册次数
	 */
	public Long findRegisterTimes(String ip)throws DaoException;
	
	/** 
	 * 用于定时器每天定时清除当天临时IP记录
	 * DaoException:系统异常,例如数据库操作异常等
	 * 返回:返回该IP当天注册次数
	 */
	public void cleanIpTable() throws DaoException;
}