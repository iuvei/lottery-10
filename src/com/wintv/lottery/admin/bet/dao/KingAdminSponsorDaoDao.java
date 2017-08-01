package com.wintv.lottery.admin.bet.dao;

import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.KingSponsor;

public interface KingAdminSponsorDaoDao extends BaseDao<KingSponsor,Long>{
	/**3.2.7自动跟单后台管理 文档-金牌发起人管理列表 58页**/
	public Map findList(Map params)throws DaoException;
	/**金牌发起人管理详情  59页 2010-03-11 14:07**/
	public KingSponsor  loadKingSponsor(Map params)throws DaoException;

}
