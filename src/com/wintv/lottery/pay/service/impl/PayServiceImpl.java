package com.wintv.lottery.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.oscache.util.StringUtil;
import com.wintv.framework.common.Constants;
import com.wintv.framework.common.DictionaryDao;
import com.wintv.framework.common.OrderNoGenDao;
import com.wintv.framework.exception.AccountNotComplementException;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.exception.PaymentException;
import com.wintv.framework.pojo.Bank;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.CaijinDonate;
import com.wintv.framework.pojo.ChargeLog;
import com.wintv.framework.pojo.Dictionary;
import com.wintv.framework.pojo.PointTransactionLog;
import com.wintv.framework.pojo.SecurityIp;
import com.wintv.framework.pojo.SecurityPwd;
import com.wintv.framework.pojo.VaFrozenLog;
import com.wintv.framework.pojo.VirtualAccount;
import com.wintv.framework.pojo.WithdrawLog;
import com.wintv.framework.utils.DateUtil;
import com.wintv.framework.utils.Util;
import com.wintv.lottery.admin.log.dao.WithdrawCsLogAdminDao;
import com.wintv.lottery.pay.dao.BankDao;
import com.wintv.lottery.pay.dao.CaijinDonateDao;
import com.wintv.lottery.pay.dao.ChargeLogDao;
import com.wintv.lottery.pay.dao.PayDao;
import com.wintv.lottery.pay.dao.PointTransactionLogDao;
import com.wintv.lottery.pay.dao.SecurityPwdDao;
import com.wintv.lottery.pay.dao.VaFrozenLogDao;
import com.wintv.lottery.pay.dao.VaTransactionLogDao;
import com.wintv.lottery.pay.dao.VirtualAccountDao;
import com.wintv.lottery.pay.dao.WithdrawLogDao;
import com.wintv.lottery.pay.service.PayService;
import com.wintv.lottery.pay.vo.BankVo;
import com.wintv.lottery.pay.vo.ChargeLogVo;
import com.wintv.lottery.pay.vo.MoneyDetailVo;
import com.wintv.lottery.pay.vo.MosaicGoldVo;
import com.wintv.lottery.pay.vo.WithdrawLogVo;
import com.wintv.lottery.user.dao.UserDao;

@Service("payService")
public class PayServiceImpl implements PayService {

	@Autowired
	private VirtualAccountDao virtualAccountDao;
	@Autowired
	private VaTransactionLogDao vaTransactionLogDao;
	@Autowired
	private ChargeLogDao chargeLogDao;
	@Autowired
	private DictionaryDao dictionaryDao;
	@Autowired
	private WithdrawLogDao withdrawLogDao;
	@Autowired
	private PointTransactionLogDao pointTransactionLogDao;
	@Autowired
	private VaFrozenLogDao vaFrozenLogDao;
	@Autowired
	private OrderNoGenDao orderNoGenDao;
	@Autowired
	private WithdrawCsLogAdminDao withdrawCsLogAdminDao;
	@Autowired
	private CaijinDonateDao caijinDonateDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PayDao payDao;
	@Autowired
	private SecurityPwdDao securityPwdDao;
	@Autowired
	private BankDao bankDao;
	
	@Override
	public boolean authWithdrawPassword(Long userid, String pswd)
			throws DaoException {
		return userDao.authWithdrawPassword(userid, Util.MD5(pswd));
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setErrorPwdTimes(Long userid) throws DaoException {
		List<SecurityPwd> list = securityPwdDao.findBy("userId", userid);
		
		if(list == null || 0 == list.size()) {
			SecurityPwd securityPwd = new SecurityPwd();
			securityPwd.setUserId(userid);
			securityPwd.setCnt(1L);
			securityPwdDao.store(securityPwd);
		} else {
			SecurityPwd securityPwd = (SecurityPwd)list.get(0);
			securityPwd.setCnt(securityPwd.getCnt() + 1L);
			securityPwdDao.store(securityPwd);
		}
	}
	
	@Override
	public Long findErrorPwdTimes(Long userid) throws DaoException {
		return securityPwdDao.findErrorPwdTimes(userid);
	}
	
	@Override
	public Long findWithdrawTimes(Long userid) throws PaymentException, DaoException {
		return withdrawLogDao.findWithdrawTimes(userid);
	}
	
	@Override
	public VirtualAccount findVirtualAccount(Long userid) throws PaymentException, DaoException {
		return virtualAccountDao.findUniqueBy("txUserId", userid);
	}
	
	@Override
	public List<BankVo> findChargeBank() throws PaymentException, DaoException {
		return bankDao.findChargeBank();
	}
	
	@Override
	public List<BankVo> findWithdrawBank() throws PaymentException,
			DaoException {
		return bankDao.findWithdrawBank();
	}
	
	@Override
	public BankVo findLastChargeBank(Long userid) throws PaymentException,
			DaoException {
		return chargeLogDao.findLastChargeBank(userid);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveOnlinePayLog(Map params) throws PaymentException,
			DaoException {
		try {
			Long userId = (Long)params.get("userId");
			BigDecimal money = (BigDecimal)params.get("money");
			String fromBank = (String)params.get("fromBank");
			String fromBankCode = (String)params.get("fromBankCode");
			String ip = (String)params.get("ip");
			String payWay = (String)params.get("payWay");
			String orderNo = orderNoGenDao.gen(Constants.ORDER_NO_CHARGE);
			
			//产生支付日志
			ChargeLog chargeLog = new ChargeLog();
			chargeLog.setUserId(userId);
			chargeLog.setMoney(money);
			chargeLog.setFromBank(fromBank);
			chargeLog.setFromBankCode(fromBankCode);
			chargeLog.setIp(ip);
			chargeLog.setPayWay(payWay);
			chargeLog.setOrderNo(orderNo);
			chargeLog.setStatus(Constants.CHARGE_NOT_PAID);
			chargeLog.setChargeTime(DateUtil.getCurrentDate());
			chargeLogDao.store(chargeLog);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean onlinePay(Map params) throws PaymentException, DaoException {
		Long userId = (Long)params.get("userId");
		BigDecimal money = (BigDecimal)params.get("money");
		String status = (String)params.get("status");
		String orderNo = (String)params.get("orderNo");
		
		//修改支付状态
		ChargeLog chargeLog = chargeLogDao.findUniqueBy("orderNo", orderNo);
		chargeLog.setMoney(money);
		chargeLog.setStatus(status);
		chargeLogDao.store(chargeLog);
		
		//支付成功后增加余额
		if(Constants.CHARGE_PAID.equals(status)) {
			VirtualAccount virtualAccount = virtualAccountDao.findUniqueBy("txUserId", userId);
			virtualAccount.setAllMoney(virtualAccount.getAllMoney().add(money));
			virtualAccountDao.store(virtualAccount);
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String withdraw(Map params) throws AccountNotComplementException,
			DaoException {
		Long userId = (Long)params.get("userId");
		Long times = withdrawLogDao.findWithdrawTimes(userId);
		
		if(times >= 3L) {
			return "2";//当天取款次数不能超过3次
		}
		
		if(securityPwdDao.findErrorPwdTimes(userId) >= 10) {
			return "3";//取款密码错误次数超过10次，当天不能取款
		}
		
		BigDecimal money = (BigDecimal)params.get("money");
		BigDecimal fee = (BigDecimal)params.get("fee");
		String bank = (String)params.get("bank");
		String bankCode = (String)params.get("bankCode");
		String bankProvince = (String)params.get("bankProvince");
		String bankCity = (String)params.get("bankCity");
		String withdrawIp = (String)params.get("withdrawIp");
		String branch = (String)params.get("branch");
		String cardNum = (String)params.get("cardNum");
		String orderNo = orderNoGenDao.gen(Constants.ORDER_NO_WITHDRAW);
		
		VirtualAccount virtualAccount = virtualAccountDao.findUniqueBy("txUserId", userId);
		
		if(!"1".equals(virtualAccount.getStatus())) {
			return "4";//该用户账户已经锁定，不能取款
		}
		
		if(StringUtil.isEmpty(bankCode) || StringUtil.isEmpty(bank) || StringUtil.isEmpty(cardNum)) {
			return "5";//银行信息绑定不全，不能取款
		}
		
		if(money.compareTo(virtualAccount.getAllMoney().subtract(virtualAccount.getFrozenMoney())) <= 0) {
			//产生取款日志
			WithdrawLog withdrawLog = new WithdrawLog();
			withdrawLog.setTxUserId(userId);
			withdrawLog.setTxMoney(money);
			withdrawLog.setFee(fee);
			withdrawLog.setBank(bank);
			withdrawLog.setBankCode(bankCode);
			withdrawLog.setBankProvince(bankProvince);
			withdrawLog.setBankCity(bankCity);
			withdrawLog.setWithdrawIp(withdrawIp);
			withdrawLog.setBranch(branch);
			withdrawLog.setCardNum(cardNum);
			withdrawLog.setOrderNo(orderNo);
			withdrawLog.setStatus(Constants.WITHDRAW_NOT_ACCEPTED);
			withdrawLog.setAllMoney(virtualAccount.getAllMoney());
			withdrawLog.setDrawTime(DateUtil.getCurrentDate());
			Long orderId = withdrawLogDao.saveObject(withdrawLog);
			
			//冻结金额
			virtualAccount.setFrozenMoney(virtualAccount.getFrozenMoney().add(money));
			virtualAccountDao.store(virtualAccount);
			
			//产生冻结日志
			VaFrozenLog vaFrozenLog = new VaFrozenLog();
			vaFrozenLog.setFrozenMoney(money);
			vaFrozenLog.setTxUserId(userId);
			vaFrozenLog.setOrderId(orderId);
			vaFrozenLog.setOrderNo(orderNo);
			vaFrozenLog.setTxType(Constants.TRANSACTION_WITHDRAW);
			vaFrozenLog.setCategoryType(Constants.ORDER_CATEGORY_WITHDRAW);
			vaFrozenLog.setFlg(Constants.FROZEN_FREEZE);
			vaFrozenLog.setAllMoney(virtualAccount.getAllMoney());
			vaFrozenLog.setFrozenDate(DateUtil.getCurrentDate());
			vaFrozenLogDao.store(vaFrozenLog);
		} else {
			return "6";//可用余额不足
		}
		
		return "1";//取款成功
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean userCancelWithdraw(Map params) throws DaoException {
		String orderNo = (String)params.get("orderNo");
		WithdrawLog withdrawLog = withdrawLogDao.findUniqueBy("orderNo", orderNo);
		
		if(Constants.WITHDRAW_NOT_ACCEPTED.equals(withdrawLog.getStatus())) {
			//修改状态为用户撤销
			withdrawLog.setStatus(Constants.WITHDRAW_USER_CANCEL);
			withdrawLogDao.store(withdrawLog);
			
			//解除冻结资金
			VirtualAccount virtualAccount = virtualAccountDao.findUniqueBy("txUserId", withdrawLog.getTxUserId());
			virtualAccount.setFrozenMoney(virtualAccount.getFrozenMoney().subtract(withdrawLog.getTxMoney()));
			virtualAccountDao.store(virtualAccount);
			
			//产生解冻日志
			VaFrozenLog vaFrozenLog = new VaFrozenLog();
			vaFrozenLog.setOrderNo(orderNo);
			vaFrozenLog.setAllMoney(virtualAccount.getAllMoney());
			vaFrozenLog.setFrozenDate(DateUtil.getCurrentDate());
			vaFrozenLog.setFrozenMoney(withdrawLog.getTxMoney());
			vaFrozenLog.setTxUserId(withdrawLog.getTxUserId());
			vaFrozenLog.setOrderId(withdrawLog.getDrawId());
			vaFrozenLog.setTxType(Constants.TRANSACTION_WITHDRAW);
			vaFrozenLog.setCategoryType(Constants.ORDER_CATEGORY_WITHDRAW);
			vaFrozenLog.setFlg(Constants.FROZEN_UNFREEZE);
			vaFrozenLogDao.store(vaFrozenLog);
		} else {
			return false;
		}
		return true;
	}
	
	@Override
	public List<WithdrawLog> findWithdrawLogList(Map params, int startRow, int pageSize)throws DaoException {
		return withdrawLogDao.findWithdrawLogList(params, startRow, pageSize);
	}

	@Override
	public Long findWithdrawLogListCount(Map params) throws DaoException {
		return withdrawLogDao.findWithdrawLogListCount(params);
	}
	
	@Override
	public List<MoneyDetailVo> findMoneyDetailList(Map params, int startRow, int pageSize)
			throws DaoException {
		 return vaTransactionLogDao.findMoneyDetailList(params, startRow, pageSize);
	}
	
	@Override
	public Map findMoneyIncomeSumCount(Map params) throws DaoException {
		return vaTransactionLogDao.findMoneyIncomeSumCount(params);
	}
	
	@Override
	public Map findMoneyExpendSumCount(Map params) throws DaoException {
		return vaTransactionLogDao.findMoneyExpendSumCount(params);
	}
	
	@Override
	public Long findMoneyDetailListCount(Map params) throws DaoException {
		return vaTransactionLogDao.findMoneyDetailListCount(params);
	}
	
	@Override
	public List<MosaicGoldVo> findMosaicGoldList(Map params, int startRow, int pageSize)
			throws DaoException {
		 return vaTransactionLogDao.findMosaicGoldList(params, startRow, pageSize);
	}

	@Override
	public Map findMosaicGoldIncomeSumCount(Map params) throws DaoException {
		return vaTransactionLogDao.findMosaicGoldIncomeSumCount(params);
	}
	
	@Override
	public Map findMosaicGoldExpendSumCount(Map params) throws DaoException {
		return vaTransactionLogDao.findMosaicGoldExpendSumCount(params);
	}

	@Override
	public Long findMosaicGoldListCount(Map params) throws DaoException {
		return vaTransactionLogDao.findMosaicGoldListCount(params);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String updatePoint(Map params) throws DaoException {
		Long txUserId = (Long)params.get("txUserId");
		String txPointType = (String)params.get("txPointType");
		Long point = (Long)params.get("point");
		VirtualAccount virtualAccount = virtualAccountDao.findUniqueBy("txUserId", txUserId);
		
		if(txPointType.equals("1")) {//增加积分
			virtualAccount.setPoint(virtualAccount.getPoint() + point);
		}
		if(txPointType.equals("2")) {//减少积分
			if(virtualAccount.getPoint() < point) {
				return Constants.TRANSACTION_POINT_NOT_ENOUGH;
			}
			virtualAccount.setPoint(virtualAccount.getPoint() - point);
		}
		
		PointTransactionLog pointTransactionLog = new PointTransactionLog();
		pointTransactionLog.setTxTime(DateUtil.getCurrentDate());
		pointTransactionLog.setTxType(txPointType);
		pointTransactionLog.setTxUserId(txUserId);
		pointTransactionLog.setTxValue(point);
		pointTransactionLog.setAllPoint(virtualAccount.getPoint());
		
		virtualAccountDao.store(virtualAccount);
		pointTransactionLogDao.store(pointTransactionLog);
		
		return Constants.TRANSACTION_SUCCESS;
	}

	/** ************ 支付后台管理系统 ********************** */

	/** ************* 取款系统后台管理系统 *************** */

	/**
	 * 取款系统后台-取款记录查询 并分页 涉及表:1取款日志(T_WITHDRAW_LOG) 2.T_USER 表联合查询 参数：
	 * 
	 * @author hikin yao
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
	public List<WithdrawLogVo> withdrawLogListAdmin(
			StringBuilder queryCondition, String orderFiled, String orderType,
			Integer startRow, Integer pageSize) throws DaoException {
		return withdrawLogDao.withdrawLogListAdmin(queryCondition, orderFiled,
				orderType, startRow, pageSize);
	}

	/**
	 * 取款系统后台-统计符合查询条件的取款总记录数
	 * 
	 * @author hikin yao
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 返回用户查询列表
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public Map<String, Object> countWithDrawLogAdmin(StringBuilder queryCondition)
			throws DaoException {
		return withdrawLogDao.countWithDrawLogAdmin(queryCondition);
	}

	/**
	 * 取款系统后台-取款明细查询: 表T_WITHDRAW_LOG 参数： dawId:取款主键
	 * 
	 * @author hikin yao
	 * @param withdrawId:取款主键
	 * @throws DaoException
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public WithdrawLog loadWithDrawAdmin(Long withdrawId) throws DaoException {
		return withdrawLogDao.loadWithDrawAdmin(withdrawId);
	}

	/**
	 * 取款系统后台-受理取款操作
	 * 
	 * @author hikin yao
	 * @param withdrawLog
	 *            取款操作详情
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * 
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public void acceptDrawMoneyAdmin(Long drawId, CSHandleLog csHandleLogVo)
			throws DaoException {
		WithdrawLog po = withdrawLogDao.loadWithDrawAdmin(drawId);
		System.out.println(po.getStatus());
		if (null != po && null != po.getStatus()
				&& po.getStatus().equals(Constants.WITHDRAW_NOT_ACCEPTED)) {
		// 1.更改用户订单状态
		po.setStatus(Constants.WITHDRAW_ACCEPTED);
		withdrawLogDao.store(po);
		csHandleLogVo.setLogId(drawId);
		csHandleLogVo.setOpType(Constants.CS_DRAW_OP_TYPE_ACCEPT);
		// 2.记录客服操作日志
		withdrawCsLogAdminDao.store(csHandleLogVo);
		}else{
			throw new LotteryBizException("订单状态必须为未受理，才可以进行受理取款操作");
		}
	}

	/**
	 * 取款系统后台-取款成功操作
	 * 
	 * @author hikin yao
	 * @param withdrawLog
	 *            取款操作详情
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * 
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public void drawMoneySuccessAdmin(Long drawId, CSHandleLog csHandleLogVo)
			throws DaoException {
		WithdrawLog po = withdrawLogDao.loadWithDrawAdmin(drawId);
		if (null != po && null != po.getStatus()
				&& po.getStatus().equals(Constants.WITHDRAW_ACCEPTED)) {
		// 1.执行扣除金额操作，冻结资金扣除
		withdrawLogDao.updateUserAccountMoneyAndFrozenMoneyAdmin(po
				.getTxUserId(), po.getTxMoney(), "success");
		
		// 2.更改用户订单状态：1:未受理、2:已受理、3:已转账 4.用户撤销 5.客服撤销
		po.setStatus(Constants.WITHDRAW_TRANSFERRED);
		//通过获取用户账户详细信息 来更新用户当前实时余额
		VirtualAccount userAccountInfo=virtualAccountDao.getUserAccountByUserId(po.getTxUserId());
		po.setAllMoney(userAccountInfo.getAllMoney());// 更新取款交易记录订单余额
		po.setDrawTime(new Date());
		withdrawLogDao.store(po);
		// 3.增加一条客服操作日志
		csHandleLogVo.setLogId(drawId);
		csHandleLogVo.setOpType(Constants.CS_DARW_OP_TYPE_SUCCESS);
		withdrawCsLogAdminDao.store(csHandleLogVo);
		// 4.增加一条解冻纪录
		addOneWithdrawUnFreezeLog(po, csHandleLogVo);
		}else{
			throw new LotteryBizException("订单状态必须为已受理，才可以进行取款成功操作");
		}
	}

	/**
	 * 取款系统后台-撤销取款操作
	 * 
	 * @author hikin yao
	 * @param withdrawLogVo
	 *            取款操作详情
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * 
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public void cancelDrawMoneyAdmin(Long drawId, CSHandleLog csHandleLogVo)
			throws DaoException {
		WithdrawLog po = withdrawLogDao.loadWithDrawAdmin(drawId);
		if (null != po && null != po.getStatus()
				&& po.getStatus().equals(Constants.WITHDRAW_ACCEPTED)) {
			// 1.撤销取款 用户账户冻结金额减去订单交易金额
			withdrawLogDao.updateUserAccountMoneyAndFrozenMoneyAdmin(po
					.getTxUserId(), po.getTxMoney(), "cancel");
			// 2.更改用户订单状态
			po.setMemo(csHandleLogVo.getMemo());
			po.setStatus(Constants.WITHDRAW_CS_CANCEL);
			withdrawLogDao.store(po);
			// 3.增加一条记录客服操作日志
			csHandleLogVo.setLogId(drawId);
			csHandleLogVo.setOpType(Constants.CS_DRAW_OP_TYPE_CANCEL);
			withdrawCsLogAdminDao.store(csHandleLogVo);
			// 4.增加一条解冻纪录
			addOneWithdrawUnFreezeLog(po, csHandleLogVo);
		}else{
				throw new LotteryBizException("订单状态必须为已受理，才可以进行撤销取款操作");
		}
	}

	/**
	 * 增加一条后台客户取款模块操作时产生的解冻纪录
	 * 
	 * @param po
	 * @param csHandleLogVo
	 *            客服信息
	 * @throws DaoException
	 */
	private void addOneWithdrawUnFreezeLog(WithdrawLog po,
			CSHandleLog csHandleLogVo) throws DaoException {
		VaFrozenLog vaFrozenLogVo = new VaFrozenLog();
		vaFrozenLogVo.setTxUserId(csHandleLogVo.getCsUserId());// 操作客服Id
		vaFrozenLogVo.setAllMoney(po.getAllMoney());// 剩余金额
		vaFrozenLogVo.setFlg(Constants.FROZEN_UNFREEZE);// 解冻标志
		vaFrozenLogVo.setFrozenMoney(po.getTxMoney());// 解冻金额
		vaFrozenLogVo.setFrozenDate(new Date());// 解冻日期
		vaFrozenLogVo.setOrderId(po.getDrawId());// 订单ID
		vaFrozenLogVo.setOrderNo(po.getOrderNo());// 订单号
		vaFrozenLogVo.setTxType(Constants.TRANSACTION_WITHDRAW);// 交易类型
		vaFrozenLogVo.setCategoryType(Constants.ORDER_CATEGORY_WITHDRAW);// 关联订单表类型
		vaFrozenLogDao.store(vaFrozenLogVo);
	}


	

	/********************* 充值系统后台管理系统 ***************************/
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
			Integer startRow, Integer pageSize) throws DaoException {
		return chargeLogDao.findChargeLogListAdmin(queryCondition, orderFiled,
				orderType, startRow, pageSize);
	}

	/**
	 * 充值系统后台统计符合查询条件的充值总记录数，总金额数
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 返回用户查询列表
	 */
	public Map<String, Object> countChargeLogAdmin(StringBuilder queryCondition)
			throws DaoException {
		return chargeLogDao.countChargeLogAdmin(queryCondition);
	}

	/**
	 * 充值系统后台 返回某条充值记录详细信息
	 * 
	 * @param chargeLogId
	 *            充值记录id
	 * @return
	 */
	public ChargeLog getChargeLogDetailAdmin(Long chargeLogId)
			throws DaoException {
		return chargeLogDao.getChargeLogDetailAdmin(chargeLogId);
	}

	/**
	 * 充值系统后台——到款确认 确认提交则将订单状态改为已支付,记录客服操作
	 * 
	 * @param chargeId
	 *            充值记录Id
	 * 
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * @param isCommit
	 *            客服是否确认提交,不提交则只执行操作日志记录 1.确认提交 2.不提交
	 * 
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public void confirmBankTransferAdmin(Long chargeId,
			CSHandleLog csHandleLog, String isCommit) throws DaoException {
		if (null!=isCommit&&"ok".equals(isCommit)) {
			ChargeLog po = chargeLogDao.getChargeLogDetailAdmin(chargeId);
			if (null != po && null != po.getStatus()
					&& po.getStatus().equals(Constants.CHARGE_NOT_PAID)) {
			// 1.资金到用户账户
			chargeLogDao.addChargeMoneyToUserAccountMoneyAdmin(po.getUserId(),
					po.getMoney());
			// 2.状态改为已支付
			po.setStatus(Constants.CHARGE_PAID);
			//3.通过获取用户账户详细信息 来更新用户当前实时余额
			VirtualAccount userAccountInfo=virtualAccountDao.getUserAccountByUserId(po.getUserId());
			po.setAllMoney(userAccountInfo.getAllMoney());// 更新充值交易记录订单余额
			po.setChargeTime(new Date());
			chargeLogDao.store(po);
			if(po.getPayWay().equals("3")){//银行转账
				csHandleLog.setOpType(Constants.CS_CHARGE_OP_TYPE_CONFIRM_BANK_TRANAFER);
			}else{
				csHandleLog.setOpType(Constants.CS_CHARGE_OP_TYPE_CONFIRM_PAY);
			}
		}else{
				throw new LotteryBizException("订单状态必须为未支付，才可以进行到款确认操作");
		}
	}
		// 记录客服操作日志 
		csHandleLog.setLogId(chargeId);
		withdrawCsLogAdminDao.store(csHandleLog);		
	}

	/**
	 * 充值系统后台 —— 添加一条充值记录
	 * 
	 * @param chargeLog
	 *            充值记录
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * @throws DaoException
	 */
	public void addOneChangeLogAdmin(ChargeLog chargeLog,
			CSHandleLog csHandleLog) throws DaoException{
		//添加充值订单
		String orderNo = orderNoGenDao.gen(Constants.ORDER_NO_CHARGE);
		chargeLog.setOrderNo(orderNo);
		chargeLog.setStatus(Constants.CHARGE_NOT_PAID);
		chargeLog.setChargeTime(new Date());
		Long chargeLogId=chargeLogDao.saveObject(chargeLog);
		
		// 记录客服操作日志 
		
		csHandleLog.setLogId(chargeLogId);
		csHandleLog.setOpType(Constants.CS_CHARGE_OP_TYPE_ADD_ORDER);		
		withdrawCsLogAdminDao.store(csHandleLog);
	}

	/*** ********** 彩金系统管理后台 *********/

	/**
	 * 彩金系统后台 -- 查询彩金捐赠列表
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
	 * @return 返回查询记录列表
	 * @throws DaoException
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public List<CaijinDonate> findCaiJinDonateListAdmin(
			StringBuilder queryCondition, String orderFiled, String orderType,
			Integer startRow, Integer pageSize) throws DaoException {
		return caijinDonateDao.findCaiJinDonateListAdmin(queryCondition,
				orderFiled, orderType, startRow, pageSize);
	}

	/**
	 * 彩金系统后台 -- 统计符合查询条件的彩金捐赠列表记录数
	 * 
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 统计结果
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public Map<String, Object> countCaiJinDonateListAdmin(
			StringBuilder queryCondition) throws DaoException {
		return caijinDonateDao.countCaiJinDonateListAdmin(queryCondition);
	}

	/**
	 * 彩金系统后台 -- 获取某条彩金系统详情
	 * 
	 * 
	 * @param caiJinId
	 *            彩金记录ID
	 * 
	 * @return 返回某条记录详情
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public CaijinDonate getCaiJinDonateDetailAdmin(Long caiJinId)
			throws DaoException {
		return caijinDonateDao.getCaiJinDonateDetailAdmin(caiJinId);
	}

	/**
	 * 彩金系统后台 -- 添加一条彩金捐赠记录
	 * 
	 * @param caijinDonate
	 * @throws DaoException
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public Long addOneCaiJinDonateLogAdmin(CaijinDonate caijinDonate,CSHandleLog csHandleLog)
			throws DaoException {
		caijinDonate.setApplyTime(new Date());
		caijinDonate.setCategoryType(Constants.ORDER_CATEGORY_MOSAIC_GOLD_ZS);
		caijinDonate.setOrderNo(orderNoGenDao.gen(Constants.ORDER_NO_MOSAIC_GOLD_ZS));
		caijinDonate.setStatus(Constants.CAIJIN_STATUS_UN_AUDIT);
		Long caijinLogId=caijinDonateDao.saveObject(caijinDonate);
		
		// 记录客服操作日志 		
		csHandleLog.setLogId(caijinLogId);
		csHandleLog.setOpType(Constants.CS_CAIJIN_OP_TYPE_ADD_ORDER);		
		withdrawCsLogAdminDao.store(csHandleLog);
		return 1L;
	}

	/**
	 * 彩金系统后台 -- 审核彩金订单
	 * 
	 * @param caiJinId
	 * @param csHandleLog
	 *            执行操作的客服信息
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public void auditCaiJinDonateAdmin(Long caiJinId,String auditReason,
			CSHandleLog csHandleLog) throws DaoException {
		CaijinDonate po = caijinDonateDao.getCaiJinDonateDetailAdmin(caiJinId);
		if (null != po && null != po.getStatus()
				&& po.getStatus().equals(Constants.CAIJIN_STATUS_UN_AUDIT)) {
			// 1.执行彩金捐赠批量操作
			this.payDao.saveUserDonate(caiJinId, auditReason);	
		
			// 2.添加审核人与审核时间
			po.setAuditUser(csHandleLog.getCsName());
			po.setAuditUserId(csHandleLog.getCsUserId());
			po.setAuditTime(new Date());
			po.setStatus(Constants.CAIJIN_STATUS_AUDIT);
			po.setAuditReason(auditReason);
			caijinDonateDao.store(po);		
			// 3.记录客服操作日志
			csHandleLog.setLogId(caiJinId);
			csHandleLog.setOpType(Constants.CS_CAIJIN_OP_TYPE_AUDIT);		
			withdrawCsLogAdminDao.saveObject(csHandleLog);
		}
	}

	/**
	 * 彩金系统后台 -- 撤销彩金订单
	 * 
	 * @param caijinDonate
	 * @param csHandleLog
	 *            执行操作的客服信息
	 */
	@Override
	@Transactional(rollbackFor = DaoException.class)
	public void cancleCaiJinDonateAdmin(Long caiJinId, String auditReason,
			CSHandleLog csHandleLog) throws DaoException {
		CaijinDonate po = caijinDonateDao.getCaiJinDonateDetailAdmin(caiJinId);
		if (null != po && null != po.getStatus()
				&& po.getStatus().equals(Constants.CAIJIN_STATUS_UN_AUDIT)) {
			// 1.状态改变为“已撤销”
			po.setStatus(Constants.CAIJIN_STATUS_CANCEL);
			po.setAuditReason(auditReason);
			po.setAuditUser(csHandleLog.getCsName());
			po.setAuditUserId(csHandleLog.getCsUserId());
			po.setAuditTime(new Date());
			po.setAuditReason(auditReason);
			caijinDonateDao.store(po);
			// 2.记录客服操作日志 '1':受理取款 '2':取款成功 3:撤销转账 4.到款确认 5.审核彩金 6.撤销彩金赠送
			csHandleLog.setLogId(caiJinId);
			csHandleLog.setOpType(Constants.CS_CAIJIN_OP_TYPE_CANCEL);	
			withdrawCsLogAdminDao.store(csHandleLog);
		}
	}
	
	/**
	 * 后台 查询客服操作记录
	 * 
	 * @param orderLogId
	 *            外键关联的订单logId
	 * @param orderLogType
	 *            外键关联log表类型
	 */
	public List<CSHandleLog> findCsHandleLogListAdmin(Long orderLogId,String orderLogType){
		return withdrawCsLogAdminDao.findCsHandleLogListAdmin(orderLogId,orderLogType);
	}
	/**
	 * 后台 保存客服操作记录
	 * 
	 * @param csHandleLog
	 *            执行操作的客服信息
	 */
	public Long saveCsHandleLogAdmin(CSHandleLog csHandleLog) throws DaoException{
		return withdrawCsLogAdminDao.saveObject(csHandleLog);
	}
	/**
	 * 检查用户输入的多个用户名是否是系统内用户
	 * @param userNames name1,name2,name3,name4
	 */
	public boolean checkInputUserNames(String userNames){
		List<Long> userIds=userDao.findUserIdsByUsername(userNames);
		if(userIds.size()==userNames.split(",").length){
			return true;
		}else{
			return false;
		}
	}
	/*************************银行模块************************/
	/**
	 * 获取所有银行
	 * @return
	 */
	public List<Bank> findAllBank(){
		return bankDao.findAllBank();
	}
	/**
	 * 更改银行状态
	 * 
	 * checkedIds 银行Id
	 * @param status
	 *            '1':可用  '2':不可用
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateBankStatus(String checkedIds, String status,Integer bankType)throws DaoException{
		if(bankType==1){
			return bankDao.updateChargeBankStatus(checkedIds, status);
		}else if(bankType==2){
			return bankDao.updateDrawBankStatus(checkedIds, status);	
		}else{
			throw new LotteryBizException("银行类型不正确");
		}
	}
	/**
	 * 根据字典类型获取相应字典列表
	 */
	public List<Dictionary> getDictionaryListByType(String type) throws DaoException{
		return dictionaryDao.getDictionaryListByType(type);
	}
	 /**
	 * 心水前台支付 2010 02-23 15:28 用户第一次购买心水
	 * 返回值:
	 *  "1":余额足够本次支付，支付成功
	 *  "4":余额不足 金额或彩金部分冻结
	 *  "5":用户账户锁定 禁止购买心水
	 * 
	 */
	public String  myXinshuiPay(Long c2cId,Long buyUserId,String useCaijin,String buyUserName)throws DaoException{
		return this.payDao.siteXinshuiPay(c2cId, buyUserId, useCaijin,buyUserName);
	}
	/**
	 * 心水前台支付 2010 03-01 11:48 续费
	 * 返回:
	 * 1:正常返回
	 * -1:报错
	 * 
	 */
	public int  continueXinshuiPay(Long xinshuiOrderId,String resultValue)throws DaoException{
		return this.payDao.continueXinshuiPay(xinshuiOrderId, resultValue);
	}
	/**
	 * 心水前台支付 2010 02-26 10:57前台普通取消自己的心水订单,单订单支付状态必须是:'1',未支付
	 * 返回:
	 * '1':成功取消订单
	 * '-1':取消订单失败
	 */
	public int  cancelXinshuiPay(Long xinshuiOrderId,Long buyUserId)throws DaoException{
		System.out.println("xinshuiOrderId="+xinshuiOrderId+" buyUserId="+ buyUserId);
		
		return this.payDao.cancelXinshuiPay(xinshuiOrderId, buyUserId);
	}
	//-----------------------b2c心水支付   2010-03-16 09:36-----------------------------------------------------
	public int  b2CXinshuiPay(Long b2cOrderId)throws DaoException{
		return this.payDao.b2CXinshuiPay(b2cOrderId);
	}
}




