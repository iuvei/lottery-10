package com.wintv.lottery.pay.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.WithdrawLog;
import com.wintv.lottery.pay.vo.WithdrawLogVo;

public interface WithdrawLogDao extends BaseDao<WithdrawLog,Long> {

	public Long findWithdrawTimes(Long userId) throws DaoException;
	
	/**
	* 个人取款记录查询 并分页  涉及表:1取款日志(T_WITHDRAW_LOG) 2.T_USER  3.T_VIRTUAL_ACCOUNT三个表联合查询
	* 参数：
	* |userid:用户ID
	* startDay:起始日期
	* endDay:结束日期
	* status:取款状态
	* 返回：
	* 取款记录列表
	*/
	public List<WithdrawLog> findWithdrawLogList(Map params,int startRow,int pageSize)throws DaoException;
	
	public Long findWithdrawLogListCount(Map params) throws DaoException;
	
	/**********************后台取款系统**************/
	/**
	 * 取款记录查询 并分页 涉及表:1取款日志(T_WITHDRAW_LOG) 2.T_USER 表联合查询 参数：
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
			Integer pageSize) throws DaoException;;

	/**
	 * 后台统计符合查询条件的取款总记录数
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 返回用户查询列表
	 */
	public Map<String, Object> countWithDrawLogAdmin(StringBuilder queryCondition)
			throws DaoException;

	/**
	 * 后台取款明细查询: 表T_WITHDRAW_LOG 参数： dawId:取款主键
	 * 
	 * @throws DaoException
	 */
	public WithdrawLog loadWithDrawAdmin(Long dawId) throws DaoException;

	/**
	 * 后台更改取款记录状态
	 * 
	 * @param checkedIds
	 *            选中取款记录ID
	 * @param newStatus
	 *            新状态1:未受理、2:已受理、3:已转账 4.用户撤销 5.客服撤销
	 * @param oldStatus
	 *            原始状态1:未受理、2:已受理、3:已转账 4.用户撤销 5.客服撤销
	 * @return 返回操作状态
	 * @throws DaoException
	 */
	public int updateDrawLogStatusAdmin(String checkedIds, String oldStatus,
			String newStatus) throws DaoException;

	/**
	 * 取款系统后台 取款成功->冻结资金扣除,撤销取款->解除冻结资金
	 * 
	 * @param userId
	 *            用户Id
	 * @param money
	 *            金额
	 * @param type
	 *            1:取款成功 用户账户总金额-money，用户账户冻结金额-money 
	 *            2.撤销取款 用户账户冻结金额-money 
	 * @return
	 */
	public int updateUserAccountMoneyAndFrozenMoneyAdmin(Long userId,
			BigDecimal money, String type) throws DaoException;
}
