package com.wintv.lottery.pay.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.PaymentException;
import com.wintv.framework.pojo.ChargeLog;
import com.wintv.lottery.pay.vo.BankVo;
import com.wintv.lottery.pay.vo.ChargeLogVo;

public interface ChargeLogDao extends BaseDao<ChargeLog,Long> {
	
	/**
	 * 获取用户上一次取款的银行信息
	 * |userId：用户ID 
	 * 
	 * @throws DaoException
	 */
	public BankVo findLastChargeBank(Long userid) throws PaymentException,DaoException; 
	
	/**
	 * 充值系统后台记录查询 并分页 
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
			Integer startRow, Integer pageSize) throws DaoException;
	/**
	 * 充值系统后台统计符合查询条件的充值总记录数，总金额数
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 返回用户查询列表
	 */
	public Map<String, Object> countChargeLogAdmin(StringBuilder queryCondition)
			throws DaoException;
	/**
	 * 充值系统后台 返回某条充值记录详细信息
	 * 
	 * @param chargeLogId
	 *            充值記錄id
	 * @return
	 */
	public ChargeLog getChargeLogDetailAdmin(Long chargeLogId) throws DaoException;
	
	/**
	 * 充值系统后台 后台更新充值记录状态
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
			String newStatus, String payWay) throws DaoException;
	
	/**
	 * 充值系统后台 订单的充值金额转入到订单中的用户账户
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
			throws DaoException;
}
