package com.wintv.lottery.admin.log.dao;

import java.util.List;
import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.exception.DaoException;
/**
 * 客户操作记录管理
 * @author Hikin Yao
 *
 * @version 1.0.0
 */
public interface WithdrawCsLogAdminDao extends BaseDao<CSHandleLog, Long> {
	/**
	 * 后台 保存后台客服操作记录
	 * 
	 * @param user
	 *            客服信息
	 * @param opType
	 *            客服操作类型
	 * @param memo
	 *            客服操作备注
	 * @param foreignLogId
	 *            外键关联log表主键ID
	 * @param foreignLogType
	 *            外键关联log 表类型 '1':T_WITHDRAW_LOG '2':T_VA_FROZEN_LOG
	 */
	public long saveCsHandleLog(Long userId, String userName, String userIP,
			String opType, String memo, Long foreignLogId, String foreignLogType)
			throws DaoException;

	/**
	 * 后台 查询客服操作记录
	 * 
	 * @param orderLogId
	 *            外键关联的订单logId
	 * @param orderLogType
	 *            外键关联log表类型
     * @param opType
	 *            客服操作类型
	 */
	public List<CSHandleLog> findCsHandleLogListAdmin(Long orderLogId,String orderLogType,String opType);
	/**
	 * 后台 查询客服操作记录
	 * 
	 * @param orderLogId
	 *            外键关联的订单logId
	 * @param orderLogType
	 *            外键关联log表类型
	 */
	public List<CSHandleLog> findCsHandleLogListAdmin(Long orderLogId,String orderLogType);
}
