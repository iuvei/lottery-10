package com.wintv.lottery.pay.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;

public interface PayDao extends BaseDao {
	//b2c第二次支付    2010-04-16 11:12
	public int  b2cPayContinue(Long b2cOrderId)throws DaoException;
	//b2c第一次支付    2010-04-16 11:12
	public int  b2cPayAtFirst(Map params)throws DaoException;
	public String pay()throws DaoException;
	/**
	 * 领导确认彩金，并更改用户的彩金余额
	 * @param caijinId
	 * @param auditReason
	 * @throws DaoException
	 */
	public void saveUserDonate(Long caijinId ,String auditReason)throws DaoException;
	
	/**
	  * 心水后台确认支付
	  *参数：
	  *  orderId：心水订单ID,对应表T_XINSHUI_ORDER的主键
	  */
    public String backendXinshuiConfirmPay( Long orderId) throws DaoException;
    /**
	 * 心水前台支付 2010 02-23 15:28
	 * @return
	 */
	public String  siteXinshuiPay(Long c2cId,Long buyUserId,String useCaijin,String buyUserName)throws DaoException;
	/**
	 * 心水前台支付 2010 02-26 10:57前台普通取消自己的心水订单,单订单支付状态必须是:'1',未支付
	 * 返回:
	 * '1':成功取消订单
	 * '-1':取消订单失败
	 */
	public int  cancelXinshuiPay(Long xinshuiOrderId,Long buyUserId)throws DaoException;
	/**
	 * 心水前台支付 2010 03-01 11:48 续费
	 * @return
	 */
	public int  continueXinshuiPay(Long xinshuiOrderId,String resultValue)throws DaoException;
	/**
	 * 用户购买代购 2010 03-04 10:00
	 * 返回:
	 * '1':成功支付
	 * 
	 */
	public Map  lotteryDaiGouPay(Long betUserId,BigDecimal betMoney,String useCaijin,String categoryType)throws DaoException;
	/**
	 * 彩票模块  合买彩票 2010 03-19 11:12
	 * 返回:
	 * '1':成功支付
	 * 
	 */
	public long[]  lotteryCoopPay(Long betUserId,BigDecimal betMoney,String useCaijin,String categoryType)throws DaoException;
	
	//-----------------------b2c心水支付   2010-03-16 09:36-----------------------------------------------------
	public int  b2CXinshuiPay(Long b2cOrderId)throws DaoException;
	
}
