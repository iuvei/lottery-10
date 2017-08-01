package com.wintv.lottery.pay.dao.impl;

import java.math.BigDecimal;
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
import com.wintv.framework.exception.PaymentException;
import com.wintv.framework.pojo.ChargeLog;
import com.wintv.framework.utils.OracleSqlUtil;
import com.wintv.lottery.pay.dao.ChargeLogDao;
import com.wintv.lottery.pay.vo.BankVo;
import com.wintv.lottery.pay.vo.ChargeLogVo;

@Repository("chargeLogDao")
@SuppressWarnings("unchecked")
public class ChargeLogDaoImpl extends BaseHibernate<ChargeLog, Long> implements
		ChargeLogDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public BankVo findLastChargeBank(Long userid) throws PaymentException,
			DaoException {
		StringBuilder sql = new StringBuilder("select c.from_bank as bankName,c.from_bank_code as bankCode,b.img as imgUrl,b.url as bankUrl from t_charge_log c join t_bank b on c.from_bank_code = b.code where c.user_id = ");
		sql.append(userid);
		sql.append(" order by c.charge_time desc");

		System.out.println(sql);
		
		final List resultList = new ArrayList();
		
		jdbcTemplate.query(sql.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						BankVo bankVo = new BankVo();
						bankVo.setBankName(rs.getString("bankName"));
						bankVo.setBankCode(rs.getString("bankCode"));
						bankVo.setImgUrl(rs.getString("imgUrl"));
						bankVo.setBankUrl(rs.getString("bankUrl"));
						resultList.add(bankVo);
					}
				});
		if (resultList.size() > 0) {
			return (BankVo)resultList.get(0);
		} else {
			Map<String, String> data = new HashMap<String, String>();
			BankVo bankVo = new BankVo();// 默认是招行
			bankVo.setBankName("招商银行");
			bankVo.setBankCode("CMBC");
			bankVo.setImgUrl("bank/cmbc.gif");
			bankVo.setBankUrl("cmbc.url");
			return bankVo;
		}
	}

	/**
	 * 后台记录查询 并分页
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
	public List<ChargeLogVo> findChargeLogListAdmin(
			StringBuilder queryCondition, String orderFiled, String orderType,
			Integer startRow, Integer pageSize) throws DaoException {

		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(" select *  from v_admin_charge_list  c where 1=1 ");
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
						ChargeLogVo vo = new ChargeLogVo();
						vo.setChargeId(rs.getLong("chargeId"));// 记录ID
						vo.setOrderNO(rs.getString("orderNO"));// 订单号
						vo.setUserId(rs.getLong("userId"));// 用户ID
						vo.setUserName(rs.getString("userName"));// 用户名称
						vo.setUserGrade(rs.getString("userGrade")); // 用户等级
						vo.setChargeMoney(rs.getBigDecimal("chargeMoney"));
						vo.setChargeWay(rs.getString("chargeWay"));
						vo.setChargeBank(rs.getString("chargeBank"));
						vo.setChargeBankCode(rs.getString("chargeBankCode"));
						vo.setChargeStatus(rs.getString("chargeStatus"));
						vo.setChargeTime(rs.getString("chargeTime"));
						vo.setChargeIP(rs.getString("chargeIp"));
						resultList.add(vo);
					}
				});
		return resultList;
	}

	/**
	 * 后台统计符合查询条件的充值总记录数，总金额数
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 返回用户查询列表
	 */
	public Map<String, Object> countChargeLogAdmin(StringBuilder queryCondition)
			throws DaoException {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr
				.append(" select count(distinct c.chargeId) as totalCount,sum(c.chargeMoney) as totalMoney from v_admin_charge_list c where 1=1 ");
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
	 * 返回某条充值记录详细信息
	 * 
	 * @param chargeLogId
	 *            充值記錄id
	 * @return
	 */
	public ChargeLog getChargeLogDetailAdmin(Long chargeLogId)
			throws DaoException {
		Query query = this.getSession().createQuery(
				" from ChargeLog where chargeId = ? ");
		query.setLong(0, chargeLogId);
		return (ChargeLog) query.uniqueResult();
	}

	/**
	 * 后台更新充值记录状态
	 * 
	 * @param checkedIds
	 *            充值记录ID
	 * @param oldStatus
	 *            状态 1.已支付、2.未支付
	 * @param newStatus
	 *            状态 1.已支付、2.未支付
	 * @param payWay
	 *            支付方式
	 * @return 返回操作状态
	 * @throws DaoException
	 */
	public int updateChargeLogStatusAdmin(String checkedIds, String oldStatus,
			String newStatus, String payWay) throws DaoException {
		String sqlStr = " update t_charge_log t set t.status=" + newStatus
				+ " where t.charge_id in ( " + checkedIds + " ) and t.pay_way="
				+ payWay + " and t.status= " + oldStatus;
		Integer result = jdbcTemplate.update(sqlStr);
		return result;
	}

	/**
	 * 订单的充值金额转入到订单中的用户账户
	 * 
	 * @param userId
	 *            用户Id
	 * @param money
	 *            金额
	 * @param type
	 *            1:取款成功 用户账户总金额-money，用户账户冻结金额-money 2.撤销取款 用户账户冻结金额-money
	 * @return
	 */
	public int addChargeMoneyToUserAccountMoneyAdmin(Long userId, BigDecimal money)
			throws DaoException {
		StringBuilder sqlStr = new StringBuilder(
				" update t_virtual_account t set ");
		sqlStr.append(" t.all_money=t.all_money+" + money);
		sqlStr.append(" where t.tx_user_id= " + userId);
		Integer result = jdbcTemplate.update(sqlStr.toString());
		return result;
	}
}
