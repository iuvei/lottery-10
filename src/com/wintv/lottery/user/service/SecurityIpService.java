package com.wintv.lottery.user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.InvalidUsernamePasswordException;
import com.wintv.framework.exception.UserServiceException;
import com.wintv.framework.exception.UsernameAlreadyExistException;
import com.wintv.framework.exception.UsernameLockException;
import com.wintv.framework.pojo.User;

public interface SecurityIpService {
	
	/** 
	 * 同一IP注册当天注册次数
	 * 参数:
	 * ip:注册IP
	 * 异常：
	 * DaoException:系统异常,例如数据库操作异常等
	 * 返回:返回该IP当天注册次数
	 */
	public Long getTimes(String ip)throws DaoException;
	
	/** 
	 * 注册成功后记录该IP记录,如果此IP为当天第一次注册，添加一条记录，如果已经注册，则当天注册数加一
	 * 参数:
	 * ip:注册IP
	 * 异常：
	 * DaoException:系统异常,例如数据库操作异常等
	 * 返回:
	 */
	public void setTimes(String ip) throws DaoException;
	
	/** 
	 * 用于定时器每天定时清除当天临时IP记录
	 * DaoException:系统异常,例如数据库操作异常等
	 * 返回:返回该IP当天注册次数
	 */
	public void cleanIpTable() throws DaoException;
}
