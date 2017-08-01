package com.wintv.lottery.pay.dao;

import java.util.List;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.PaymentException;
import com.wintv.framework.pojo.Bank;
import com.wintv.lottery.pay.vo.BankVo;
/**
 * 
 * @author Hikin Yao
 *
 * @version 1.0.0
 */
public interface BankDao extends BaseDao<Bank,Long>{

	/**
	 * 获取可充值的银行信息
	 * 
	 * @throws DaoException
	 */
	public List<BankVo> findChargeBank() throws PaymentException, DaoException;
	
	/**
	 * 获取可取款的银行信息
	 * 
	 * @throws DaoException
	 */
	public List<BankVo> findWithdrawBank() throws PaymentException, DaoException;
	
	/**
	 * 更改充值银行状态
	 * 
	 * checkedIds 用户Id
	 * @param status
	 *            '1':可用  '2':不可用
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateChargeBankStatus(String checkedIds, String status)
			throws DaoException;
	/**
	 * 更改取款银行状态
	 * 
	 * checkedIds 用户Id
	 * @param status
	 *            '1':可用  '2':不可用
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateDrawBankStatus(String checkedIds, String status)
			throws DaoException;
	/**
	 * 获取所有银行
	 * @return
	 */
	public List<Bank> findAllBank();
}
