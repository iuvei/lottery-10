package com.wintv.lottery.pay.dao;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.SecurityPwd;

public interface SecurityPwdDao extends BaseDao<SecurityPwd,Long> {

	/** 
	 * 取款当天密码输入错误次数
	 * |userid 用户id
	 * 
	 * DaoException
	 */
	public Long findErrorPwdTimes(Long userid)throws DaoException;
}
