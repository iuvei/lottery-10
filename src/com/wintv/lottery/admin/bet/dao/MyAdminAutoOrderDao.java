package com.wintv.lottery.admin.bet.dao;

import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.MyAutoOrder;

public interface MyAdminAutoOrderDao extends BaseDao<MyAutoOrder,Long>{
	public Map findAutoOrderList(Map params)throws DaoException;

}
