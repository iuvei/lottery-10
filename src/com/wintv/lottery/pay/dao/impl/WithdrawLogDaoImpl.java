package com.wintv.lottery.pay.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.SecurityPwd;
import com.wintv.framework.pojo.WithdrawLog;
import com.wintv.framework.utils.OracleSqlUtil;
import com.wintv.lottery.pay.dao.WithdrawLogDao;
import com.wintv.lottery.pay.vo.WithdrawLogVo;

@Repository("withdrawLogDao")
@SuppressWarnings("unchecked")
public class WithdrawLogDaoImpl extends BaseHibernate<WithdrawLog,Long> implements WithdrawLogDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Long findWithdrawTimes(Long userId) throws DaoException {
		Calendar cal = Calendar.getInstance();   
		Date date = new Date();   
		cal.setTime(date);   
		cal.set(Calendar.HOUR_OF_DAY, 0);   
		cal.set(Calendar.MINUTE, 0);   
		cal.set(Calendar.SECOND, 0);   
		cal.set(Calendar.MILLISECOND, 0);   
		Date begin = cal.getTime();   
		cal.add(Calendar.DATE, 1);   
		Date end = cal.getTime();
		
		String hql = "select count(w.drawId) from WithdrawLog w where w.txUserId = :userId AND w.drawTime >= :begin and w.drawTime < :end";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("userId", userId);   
		query.setParameter("begin", begin);   
		query.setParameter("end", end);  
		
		List list = query.list();
		
		if (list == null || 0 == list.size()) {
			return 0L;
		}
		return (Long)list.get(0);
	}

	@Override
	public List<WithdrawLog> findWithdrawLogList(Map params, int startRow, int pageSize)
			throws DaoException {
		StringBuilder hql = new StringBuilder("from WithdrawLog w where w.txUserId = :userid");
		
		if(params.get("startDay") != null) {
			hql.append(" and w.drawTime >= :startDay");
		}
		if(params.get("endDay") != null) {
			hql.append(" and w.drawTime <= :endDay");
		}
		if(params.get("status") != null) {
			hql.append(" and w.status like :status");
		}

		hql.append(" order by w.drawTime desc");
		
		Query query = this.getSession().createQuery(hql.toString()).setFirstResult(startRow - 1).setMaxResults(pageSize);
		
		Long userid = (Long)params.get("userid");
		
		query.setParameter("userid", userid);
		
		if(params.get("startDay") != null) {
			query.setParameter("startDay", params.get("startDay"));
		}
		if(params.get("endDay") != null) {
			query.setParameter("endDay", params.get("endDay"));
		}
		if(params.get("status") != null) {
			query.setParameter("status", params.get("status"));
		}
		
		return query.list();
	}
	
	@Override
	public Long findWithdrawLogListCount(Map params) throws DaoException {
		StringBuilder hql = new StringBuilder("select count(w.drawId) from WithdrawLog w where w.txUserId = :userid");
		
		if(params.get("startDay") != null) {
			hql.append(" and w.drawTime >= :startDay");
		}
		if(params.get("endDay") != null) {
			hql.append(" and w.drawTime <= :endDay");
		}
		if(params.get("status") != null) {
			hql.append(" and w.status like :status");
		}

		Query query = this.getSession().createQuery(hql.toString());
		
		Long userid = (Long)params.get("userid");
		
		query.setParameter("userid", userid);
		
		if(params.get("startDay") != null) {
			query.setParameter("startDay", params.get("startDay"));
		}
		if(params.get("endDay") != null) {
			query.setParameter("endDay", params.get("endDay"));
		}
		if(params.get("status") != null) {
			query.setParameter("status", params.get("status"));
		}
		
		return (Long)query.list().get(0);
	}
  /*********************后台取款管理****************************/

	/**
	 * 后台取款记录查询 并分页 涉及表:1取款日志(T_WITHDRAW_LOG) 2.T_USER 表联合查询 参数：
	 * 
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
	 * @return 返回用户查询列表
	 */
	public List<WithdrawLogVo> withdrawLogListAdmin(StringBuilder queryCondition,
			String orderFiled, String orderType, Integer startRow,
			Integer pageSize) throws DaoException {

		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(" select *  from V_ADMIN_DRAW_LIST  c where 1=1 ");
		sqlStr.append(queryCondition);
		sqlStr.append(" order by " + orderFiled + " " + orderType);
		// 添加Oracle查询分页条件
		sqlStr = OracleSqlUtil.addQueryPageSizeCondition(sqlStr, startRow,
				pageSize);
		System.out.println(sqlStr);
		final List resultList = new ArrayList();
		jdbcTemplate.query(sqlStr.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						WithdrawLogVo drawVO = new WithdrawLogVo();
						drawVO.setDrawLogId(rs.getLong("drawId"));// 取现记录ID
						drawVO.setOrderNO(rs.getString("orderNO"));// 订单号
						drawVO.setUserId(rs.getLong("userId"));// 用户ID
						drawVO.setUserName(rs.getString("userName"));// 用户名称
						drawVO.setUserGrade(rs.getString("userGrade")); // 用户等级
						drawVO.setDrawMoney(rs.getBigDecimal("drawMoney"));// 取现金额
						drawVO.setDrawFee(rs.getBigDecimal("drawFee"));// 取现手续费
						drawVO.setBankCode(rs.getString("bankCode"));// 银行代码
						drawVO.setBankName(rs.getString("bankName"));// 银行名称
						drawVO.setDrawStatus(rs.getString("drawStatus"));// 取现状态
						drawVO.setDrawTime(rs.getString("drawTime"));// 取现时间
						drawVO.setDrawIP(rs.getString("drawIp"));// 取现Ip
						resultList.add(drawVO);
					}
				});
		return resultList;
	}

	/**
	 * 后台统计符合查询条件的取款总记录数，总金额数
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 返回用户查询列表
	 */
	public Map<String, Object> countWithDrawLogAdmin(StringBuilder queryCondition)
			throws DaoException {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(" select count(distinct c.drawId) as totalCount,sum(c.drawMoney) as totalMoney from V_ADMIN_DRAW_LIST c where 1=1 ");
		sqlStr.append(queryCondition);
		final List resultList = new ArrayList();
		jdbcTemplate.query(sqlStr.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						Map<String, Object> data = new HashMap<String, Object>();
						data.put("totalCount", rs.getLong("totalCount"));// 总笔数
						data.put("totalMoney", rs.getBigDecimal("totalMoney"));// 总金额
						resultList.add(data);
					}
				});
		if (resultList.size() > 0) {
			return (Map<String, Object>) resultList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 后台取款明细查询: 表T_WITHDRAW_LOG 参数： dawId:取款主键
	 * 
	 * @throws DaoException
	 */
	public WithdrawLog loadWithDrawAdmin(Long dawId) throws DaoException {
		Query query = this.getSession().createQuery(
				" from WithdrawLog where drawId = ? ");
		query.setLong(0, dawId);
		return (WithdrawLog) query.uniqueResult();
	}

	/**
	 * 后台更改取款记录状态
	 * 
	 * @param checkedIds
	 *            选种取款记录ID
	 * @param newStatus
	 *            新状态1:未受理、2:已受理、3:已转账
	 * @param oldStatus
	 *            原始状态1:未受理、2:已受理、3:已转账
	 * @return 返回操作状态
	 * @throws DaoException
	 */
	public int updateDrawLogStatusAdmin(String checkedIds, String oldStatus,
			String newStatus) throws DaoException {
		String sqlStr = " update t_withdraw_log t set t.status=" + newStatus
				+ " where t.draw_id in ( " + checkedIds + " ) and t.status="
				+ oldStatus;
		Integer result = jdbcTemplate.update(sqlStr);
		return result;
	}

	/**
	 * 取款成功->冻结资金扣除,撤销取款->解除冻结资金
	 * 
	 * @param userId
	 *            用户Id
	 * @param money
	 *            金额
	 * @param type
	 *            success:取款成功 用户账户总金额-money，用户账户冻结金额-money cancel.撤销取款 用户账户冻结金额-money
	 * @return
	 */
	public int updateUserAccountMoneyAndFrozenMoneyAdmin(Long userId,
			BigDecimal money, String type) throws DaoException {
		StringBuilder sqlStr = new StringBuilder(
				" update t_virtual_account t set ");
		if ("success".equals(type)) {
			sqlStr.append(" t.all_money=t.all_money-" + money+" ,");
		}
		sqlStr.append(" t.frozen_money=t.frozen_money-" + money);
		sqlStr.append(" where t.tx_user_id= " + userId);
		Integer result = jdbcTemplate.update(sqlStr.toString());
		return result;
	}

}
