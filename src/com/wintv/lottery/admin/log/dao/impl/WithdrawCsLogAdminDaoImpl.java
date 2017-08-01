package com.wintv.lottery.admin.log.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.User;
import com.wintv.framework.utils.OracleSqlUtil;
import com.wintv.lottery.admin.log.dao.WithdrawCsLogAdminDao;

@Repository("withdrawCsLogAdminDao")
@SuppressWarnings("unchecked")
public class WithdrawCsLogAdminDaoImpl extends BaseHibernate<CSHandleLog, Long>
		implements WithdrawCsLogAdminDao {
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
	public long saveCsHandleLog(Long userId,String userName,String userIP,String opType, String memo,
			Long foreignLogId, String foreignLogType) throws DaoException {
		CSHandleLog csLog = new CSHandleLog();
		csLog.setCsUserId(userId);
		csLog.setCsName(userName);
		csLog.setIp(userIP);
		csLog.setOpType(opType);
		csLog.setMemo(memo);
		csLog.setLogId(foreignLogId);
		csLog.setType(foreignLogType);
		csLog.setHandleTime(new Date());
		return  saveObject(csLog);
	}
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
	public List<CSHandleLog> findCsHandleLogListAdmin(Long orderLogId,String orderLogType,String opType){
		String hqlStr="from CSHandleLog cs where cs.logId =?  and cs.type =? order by handleTime desc";
		if(!"".equals(opType)){
			hqlStr+=" and cs.opType ='"+opType+"'";
		}
		Query query = this.getSession().createQuery(hqlStr);
		query.setLong(0, orderLogId);
		query.setString(1, orderLogType);
		if(!"".equals(opType)){
			query.setString(2, opType);
		}
		query.setMaxResults(20);
		return query.list();
	}
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
	public List<CSHandleLog> findCsHandleLogListAdmin(Long orderLogId,String orderLogType){
		return findCsHandleLogListAdmin(orderLogId,orderLogType,"");
	}
}
