package com.wintv.lottery.pay.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.wintv.framework.pojo.CaijinDonate;
import com.wintv.framework.utils.DateUtil;
import com.wintv.framework.utils.OracleSqlUtil;
import com.wintv.lottery.pay.dao.CaijinDonateDao;

@Repository("caijinDonateDao")
@SuppressWarnings("unchecked")
public class CaijinDonateDaoImpl extends BaseHibernate<CaijinDonate,Long> implements CaijinDonateDao  {
	@Autowired
	private JdbcTemplate jdbcTemplate;
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
			Integer startRow, Integer pageSize) throws DaoException{
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(" select *  from t_caijin_donate  c where 1=1 ");
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
						CaijinDonate vo = new CaijinDonate();
						vo.setId(rs.getLong("id"));					
						vo.setApplyTime(DateUtil.parseDate(rs.getString("apply_time")));
						vo.setApplyUserId(rs.getLong("apply_user_id"));
						vo.setReason(rs.getString("reason"));
						vo.setMoney(rs.getBigDecimal("money"));
						vo.setAllMoney(rs.getBigDecimal("all_money"));
						vo.setAuditUserId(rs.getLong("audit_user_id"));
						vo.setAuditTime(DateUtil.parseDate(rs.getString("audit_time")));
						vo.setStatus(rs.getString("status"));
						vo.setOrderNo(rs.getString("order_no"));
						vo.setApplyUser(rs.getString("apply_user"));
						vo.setAuditUser(rs.getString("audit_user"));
						vo.setDonateNum(rs.getLong("donate_num"));
						vo.setConcreteReason(rs.getString("concrete_reason"));
						vo.setAuditReason(rs.getString("audit_reason"));
						vo.setDeptCode(rs.getString("dept_code"));
						vo.setCategoryType(rs.getString("category_type"));
						vo.setUserList(rs.getString("user_list"));					
						resultList.add(vo);
					}
				});
		return resultList;
	}
	/**
	 * 彩金系统后台 -- 统计符合查询条件的彩金捐赠列表记录数
	 * 
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 统计结果
	 */
	public Map<String, Object> countCaiJinDonateListAdmin(StringBuilder queryCondition)
			throws DaoException{
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(" select count(distinct c.id) as totalCount,sum(c.all_money) as totalMoney from t_caijin_donate c where 1=1 ");
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
	 * 彩金系统后台-获取某条彩金系统详情
	 * 
	 * 
	 * @param caiJinId 彩金记录ID
	 *            
	 * @return 返回某条记录详情
	 */
	public CaijinDonate getCaiJinDonateDetailAdmin(Long caiJinId)
			throws DaoException{
		Query query = this.getSession().createQuery(" from CaijinDonate where id = ? ");
		query.setLong(0, caiJinId);
		return (CaijinDonate) query.uniqueResult();
	}
	/**
	 * 彩金系统后台-更新某条彩金状态
	 * 
	 * @param caiJinId 彩金记录ID
	 * @param oldStatus 1.未审核 2.已审核 3.已撤销   
	 * @param newStatus 1.未审核 2.已审核 3.已撤销      
	 * @param memo 审核意见或撤销理由 
	 */
	public Integer updateCaiJinDonateStatusAdmin(String checkedIds, String oldStatus,
			String newStatus,String reason){
		String sqlStr = " update t_caijin_donate t set t.status=" + newStatus
		+ " where t.id in ( " + checkedIds + " ) and t.audit_reason="
		+ reason + " and t.status= " + oldStatus;
		Integer result = jdbcTemplate.update(sqlStr);
		return result;
	}
}
