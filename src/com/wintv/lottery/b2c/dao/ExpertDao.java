package com.wintv.lottery.b2c.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Expert;
import com.wintv.lottery.b2c.vo.B2CProductVO;
import com.wintv.lottery.b2c.vo.ExpertVO;

public interface ExpertDao extends BaseDao<Expert,Long>{
	public long saveExpert(ExpertVO po)throws DaoException;
	/**专家首页  2010-04-15 11:01**/
	public Map loadExpertHomeData(Long expertId)throws DaoException;
	/**保存内参信息时,产生内参编号**/
	public String genExpertCode()throws DaoException;
	
	/**内参后台管理——内参管理界面  文档第10页  2010-03-15  10:34**/
	public Map findXinshuiList(Map params)throws DaoException;
	
	/**
	 * B2C产品详情
	 *
	 */
	public  B2CProductVO loadB2CProduct(Long b2cId)throws DaoException;
	
	/**内参订购管理  2010-03-15 11:18**/
	public Map findB2COrderList(Map params)throws DaoException;
	public List<Expert> findExpertList()throws DaoException;
}
