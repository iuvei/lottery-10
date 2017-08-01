package com.wintv.lottery.pay.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.CaijinDonate;

public interface CaijinDonateDao extends BaseDao<CaijinDonate,Long>  {
	/**
	 * 彩金系统后台 -- 查询彩金捐赠列表
	 * @param queryCondition
	 *            查询条件
	 * @param orderFiled
	 *            排序字段
	 * @param orderType
	 *            排序类型 “ASC|DESC”
	 * @param startRow
	 *            查询开始行
	 * @param pageSize
	 *            页面记录数大小
	 * @return 返回查询记录列表
	 * @throws DaoException
	 */
	public List<CaijinDonate> findCaiJinDonateListAdmin(StringBuilder queryCondition, String orderFiled, String orderType,
			Integer startRow, Integer pageSize) throws DaoException;
	/**
	 * 彩金系统后台-统计符合查询条件的彩金捐赠列表记录数
	 * 
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 统计结果
	 */
	public Map<String, Object> countCaiJinDonateListAdmin(StringBuilder queryCondition)
			throws DaoException;
	/**
	 * 彩金系统后台-获取某条彩金系统详情
	 * 
	 * 
	 * @param caiJinId 彩金记录ID
	 *            
	 * @return 返回某条记录详情
	 */
	public CaijinDonate getCaiJinDonateDetailAdmin(Long caiJinId)
			throws DaoException;
	
	/**
	 * 彩金系统后台-更新某条彩金状态
	 * 
	 * @param caiJinId 彩金记录ID
	 * @param oldStatus 1.未审核 2.已审核 3.已撤销   
	 * @param newStatus 1.未审核 2.已审核 3.已撤销      
	 * @param memo 审核意见或撤销理由 
	 */
	public Integer updateCaiJinDonateStatusAdmin(String checkedIds, String oldStatus,String newStatus,String reason);
	
}
