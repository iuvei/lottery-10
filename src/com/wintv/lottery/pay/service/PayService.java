package com.wintv.lottery.pay.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.exception.AccountNotComplementException;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.PaymentException;
import com.wintv.framework.pojo.Bank;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.CaijinDonate;
import com.wintv.framework.pojo.ChargeLog;
import com.wintv.framework.pojo.Dictionary;
import com.wintv.framework.pojo.VirtualAccount;
import com.wintv.framework.pojo.WithdrawLog;
import com.wintv.lottery.pay.vo.BankVo;
import com.wintv.lottery.pay.vo.ChargeLogVo;
import com.wintv.lottery.pay.vo.MoneyDetailVo;
import com.wintv.lottery.pay.vo.MosaicGoldVo;
import com.wintv.lottery.pay.vo.WithdrawLogVo;

/**
 * 账户接口 支付接口
 * 
 * @author 王金阶
 * 
 */
@SuppressWarnings("unchecked")
public interface PayService {

	/**
	 * 取款密码输入错误，错误次数加一 
	 * |userid 用户id 
	 * |pswd 密码
	 * 
	 * @throws DaoException
	 */
	public boolean authWithdrawPassword(Long userid, String pswd)
			throws DaoException;

	/**
	 * 取款密码输入错误，错误次数加一 
	 * |userid 用户id
	 * 
	 * @throws DaoException
	 */
	public void setErrorPwdTimes(Long userid) throws DaoException;

	/**
	 * 取款当天密码输入错误次数 
	 * |userid 用户id
	 * 
	 * DaoException
	 */
	public Long findErrorPwdTimes(Long userid) throws DaoException;

	/**
	 * 获取用户当天取款次数 
	 * |userId：用户ID
	 * 
	 * @throws DaoException
	 */
	public Long findWithdrawTimes(Long userid) throws PaymentException,
			DaoException;

	/**
	 * 获取用户账户信息 
	 * |userId：用户ID
	 * 
	 * @throws DaoException
	 */
	public VirtualAccount findVirtualAccount(Long userid)
			throws PaymentException, DaoException;

	/**
	 * 获取可取款的银行信息
	 * 
	 * @throws DaoException
	 */
	public List<BankVo> findWithdrawBank() throws PaymentException,
			DaoException;

	/**
	 * 获取可充值的银行信息
	 * 
	 * @throws DaoException
	 */
	public List<BankVo> findChargeBank() throws PaymentException, DaoException;

	/**
	 * 获取用户上一次取款的银行信息 
	 * |userId：用户ID
	 * 
	 * @throws DaoException
	 */
	public BankVo findLastChargeBank(Long userid) throws PaymentException,
			DaoException;

	/**
	 * 在转向网银或支付宝之前 保存充值日志,不涉及到更改账户 状态是:未支付  
	 * |userId：用户ID 
	 * |money：充值金额
	 * |fromBank：第三方名称 
	 * |fromBankCode：第三方银行CODE 
	 * |ip：充值时的IP地址 
	 * |payWay：充值方式
	 * 
	 * @throws DaoException
	 */
	public boolean saveOnlinePayLog(Map params) throws PaymentException,
			DaoException;

	/**
	 * 当从网银或支付宝返回后,通过第三方接口在线充值 涉及表： 
	 * 1.在转向第三方充值网站之前,往里面加一条日志充值日志(T_CHARGE_LOG),此记录状态为:1(未支付) 2(已支付) 
	 * 2. 当成功从第三方网站成功返回之后,更新用户自己的金额虚拟账号(T_VIRTUAL_ACCOUNT),同时更新充值日志为已经支付 
	 * |userId：用户ID 
	 * |money：充值金额 
	 * |status：1.未支付 2.成功支付 
	 * |orderNo：订单号
	 * 
	 * 返回: 是否成功
	 * 
	 * @throws DaoException
	 */
	public boolean onlinePay(Map params) throws PaymentException, DaoException;

	/**
	 * 取款 注意 对于手续费 应该通过T_DICTIONARY 表查询相关配置 因为手续费可能经常变动 只能先冻结
	 * 等到客服真正打到客户银行卡上才真正扣除 涉及表: 1插入一条取款日志(T_WITHDRAW_LOG) :状态为：1(未受理)
	 * 2.更新虚拟账户表的金额:用户的金额虚拟账号(T_VIRTUAL_ACCOUNT)，冻结取款部分金额 参数： 
	 * |userId：用户ID
	 * |money：取款金额 
	 * |fee：手续费 
	 * |bank：取款银行 
	 * |bankCode：银行代码 
	 * |bankProvince：银行所在省份
	 * |bankCity：银行所在城市 
	 * |branch：银行支行 
	 * |cardNum：银行账户 
	 * |withdrawIp：取款IP
	 * 
	 * 返回: 取款状态
	 */
	public String withdraw(Map params) throws AccountNotComplementException,
			DaoException;

	/**
	 * 用户撤销取款,根据取款订单号更新状态为:4.用户撤销 
	 * |orderNo:取款订单号
	 */
	public boolean userCancelWithdraw(Map params) throws DaoException;

	/**
	 * 个人取款记录查询 并分页: 
	 * 参数： 
	 * |userid:用户ID 
	 * startDay:起始日期 
	 * endDay:结束日期
	 * status:取款状态
	 * 
	 * 返回： 取款记录列表
	 */
	public List<WithdrawLog> findWithdrawLogList(Map params, int startRow,
			int pageSize) throws DaoException;

	/**
	 * 个人取款记录总条数查询
	 * 
	 * 返回： 取款记录总条数
	 */
	public Long findWithdrawLogListCount(Map params) throws DaoException;

	/**
	 * 个人资金明细查询:注意是T_CHARGE_LOG，T_VA_TRANSACTION_LOG T_WITHDRAW_LOG三个表通过unionall查询
	 * |userid:用户ID 
	 * startDay:起始日期 
	 * endDay:结束日期 
	 * transactionType:交易类型
	 * 
	 * @throws DaoException
	 */
	public List<MoneyDetailVo> findMoneyDetailList(Map params, int startRow,
			int pageSize) throws DaoException;

	/**
	 * 个人取款记录总收入统计 
	 * |userid:用户ID 
	 * startDay:起始日期 
	 * endDay:结束日期
	 * status:取款状态
	 * 
	 * 返回：
	 * moneyIncomeCount
	 * moneyIncomeSum
	 * 
	 * @throws DaoException
	 */
	public Map findMoneyIncomeSumCount(Map params) throws DaoException;
	
	/**
	 * 个人取款记录总支出统计 
	 * |userid:用户ID 
	 * startDay:起始日期 
	 * endDay:结束日期
	 * status:取款状态
	 * 
	 * 返回：
	 * moneyExpendCount
	 * moneyExpendSum
	 * 
	 * @throws DaoException
	 */
	public Map findMoneyExpendSumCount(Map params) throws DaoException;
	
	/**
	 * 个人资金明细记录总条数查询
	 * 
	 * 返回： 个人资金明细记录总条数
	 */
	public Long findMoneyDetailListCount(Map params) throws DaoException;

	/**
	 * 个人彩金明细查询: 表T_VA_TRANSACTION_LOG  
	 * |userid:用户ID 
	 * startDay:起始日期
	 * endDay:结束日期 
	 * transactionType:交易类型
	 * 
	 * @throws DaoException
	 */
	public List<MosaicGoldVo> findMosaicGoldList(Map params, int startRow,
			int pageSize) throws DaoException;

	/**
	 * 个人取款记录总收入统计 
	 * |userid:用户ID 
	 * startDay:起始日期 
	 * endDay:结束日期
	 * status:取款状态
	 * 
	 * 返回：
	 * moneyIncomeCount
	 * moneyIncomeSum
	 * 
	 * @throws DaoException
	 */
	public Map findMosaicGoldIncomeSumCount(Map params) throws DaoException;
	
	/**
	 * 个人取款记录总支出统计 
	 * |userid:用户ID 
	 * startDay:起始日期 
	 * endDay:结束日期
	 * status:取款状态
	 * 
	 * 返回：
	 * moneyExpendCount
	 * moneyExpendSum
	 * 
	 * @throws DaoException
	 */
	public Map findMosaicGoldExpendSumCount(Map params) throws DaoException;
	
	/**
	 * 个人彩金明细记录总条数查询
	 * 
	 * 返回： 个人彩金明细记录总条数
	 */
	public Long findMosaicGoldListCount(Map params) throws DaoException;

	/**
	 * 更新表用户的金额虚拟账号(T_VIRTUAL_ACCOUNT),插入一条积分日志到表T_POINT_TRANSACTION_LOG 
	 * |txUserId:用户ID 
	 * |txPointType：1增加 2 减 
	 * |point:积分余额
	 * 
	 * @throws DaoException
	 */
	public String updatePoint(Map params) throws DaoException;

	// ============================================================================================================

	/** ************ 支付后台管理系统****************** */

	/** ************ 取款管理后台****************** */

	/**
	 * 取款系统后台-取款明细查询: 表T_WITHDRAW_LOG 参数： dawId:取款主键
	 * 
	 * @author hikin yao
	 * @throws DaoException
	 */
	public WithdrawLog loadWithDrawAdmin(Long withdrawId) throws DaoException;

	/**
	 * 取款系统后台-取款记录查询 并分页 涉及表:1取款日志(T_WITHDRAW_LOG) 2.T_USER 表联合查询 参数： 充值订单查询 参数：
	 * username:用户名 minMoney：最大金额 maxMoney:最小金额 chargeType：充值方式
	 * 详情请进T_DICTIONARY的类别 ip:充值时的IP地址 status: 1.未支付 2.成功支付 返回: 客户充值列表
	 * 
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
			Integer startRow, Integer pageSize) throws DaoException;

	/**
	 * 取款系统后台-统计符合查询条件的取款总记录数
	 * 
	 * @author hikin yao
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 返回用户查询列表
	 */
	public Map<String, Object> countWithDrawLogAdmin(
			StringBuilder queryCondition) throws DaoException;

	/**
	 * 取款系统后台-取款成功操作
	 * 
	 * 客服处理取款订单 涉及表: (一)CS受理取款订单 修改取款日志的状态(T_WITHDRAW_LOG):订单状态为：2.已受理 3.已转账
	 * 4.用户撤销 5.客服撤销 并输入原因(对应MEMO字段) (二)CS已经把取款的钱打到客户银行卡上
	 * 修改取款日志的状态(T_WITHDRAW_LOG) 把冻结部分金额
	 * 真正从用户虚拟账户表(T_VIRTUAL_ACCOUNT)里扣除取款金额(应该同时 更新字段ALL_MONEY FROZEN_MONEY) 参数：
	 * userId:用户ID money：取款金额 csUserId:客服用户ID 返回: 取款状态
	 * 
	 * @author hikin yao
	 * 
	 * @param drawId
	 *            取款操作纪录ID
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * 
	 */
	@Transactional(rollbackFor = DaoException.class)
	public void drawMoneySuccessAdmin(Long drawId, CSHandleLog csHandleLog)
			throws DaoException;

	/**
	 * 取款系统后台-撤销取款操作
	 * 
	 * @author hikin yao
	 * @param drawId
	 *            取款操作详情
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * 
	 */
	public void cancelDrawMoneyAdmin(Long drawId, CSHandleLog csHandleLog)
			throws DaoException;

	/**
	 * 取款系统后台-受理取款操作
	 * 
	 * @author hikin yao
	 * @param drawId
	 *            取款操作详情
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * 
	 */
	public void acceptDrawMoneyAdmin(Long drawId, CSHandleLog csHandleLogVo)
			throws DaoException;

	/** ******** 充值系统管理后台 ****** */
	/**
	 * 充值系统后台记录查询 并分页
	 * 
	 * 充值订单查询 参数： username:用户名 minMoney：最大金额 maxMoney:最小金额 chargeType：充值方式
	 * 详情请进T_DICTIONARY的类别 ip:充值时的IP地址 status: 1.未支付 2.成功支付 返回: 客户充值列表
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
	 *            充值记录id
	 * @return
	 */
	public ChargeLog getChargeLogDetailAdmin(Long chargeLogId)
			throws DaoException;

	/**
	 * 充值系统后台——到款确认 确认提交则将订单状态改为已支付,记录客服操作
	 * 
	 * @param chargeLog
	 *            充值记录
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * @param isCommit
	 *            客服是否确认提交,不提交则只执行操作日志记录 1.确认提交 2.不提交
	 * 
	 */
	public void confirmBankTransferAdmin(Long chargeId,
			CSHandleLog csHandleLog, String isCommit) throws DaoException;

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
			CSHandleLog csHandleLog) throws DaoException;

	/**
	 * ********** 彩金系统管理后台 ********
	 */

	/**
	 * 彩金系统后台 -- 查询彩金捐赠列表
	 * 
	 * 后台客服彩金明细查询: 表T_VA_TRANSACTION_LOG 参数： userid:用户ID applyStartDay:彩金申请起始日期
	 * applyEndDay:彩金申请结束日期 applyUserId:申请人 auditStartDay:审核起始时间
	 * auditEndDay:审核结束时间 sortType:订单排序按照以下方式排序 1.申请时间由远到近、2.申请时间由近到远
	 * 3.审核时间由远到近、4.审核时间由近到远 5.取款金额由低到高、6.取款金额由高到低 orderNo:订单号 applyReason:申请理由
	 * minMoney:金额最小值 maxMoney:金额最大值
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
	public List<CaijinDonate> findCaiJinDonateListAdmin(
			StringBuilder queryCondition, String orderFiled, String orderType,
			Integer startRow, Integer pageSize) throws DaoException;

	/**
	 * 彩金系统后台-统计符合查询条件的彩金捐赠列表记录数
	 * 
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 统计结果
	 */
	public Map<String, Object> countCaiJinDonateListAdmin(
			StringBuilder queryCondition) throws DaoException;

	/**
	 * 彩金系统后台-获取某条彩金系统详情
	 * 
	 * 
	 * @param caiJinId
	 *            彩金记录ID
	 * 
	 * @return 返回某条记录详情
	 */
	public CaijinDonate getCaiJinDonateDetailAdmin(Long caiJinId)
			throws DaoException;

	/**
	 * 彩金系统后台 -- 添加一条彩金捐赠记录
	 * 
	 * @param caijinDonate
	 * @param csHandleLog
	 *            执行操作的客服信息
	 * @throws DaoException
	 */
	public Long addOneCaiJinDonateLogAdmin(CaijinDonate caijinDonate,CSHandleLog csHandleLog)
			throws DaoException;

	/**
	 * 彩金系统后台 -- 审核彩金订单
	 * 
	 * @param caiJinId
	 * @param auditReason
	 * @param csHandleLog
	 *            执行操作的客服信息
	 */
	public void auditCaiJinDonateAdmin(Long caiJinId, String auditReason,
			CSHandleLog csHandleLog) throws DaoException;

	/**
	 * 彩金系统后台 -- 撤销彩金订单
	 * 
	 * @param caijinDonate
	 * @param csHandleLog
	 *            执行操作的客服信息
	 */
	public void cancleCaiJinDonateAdmin(Long caiJinId, String auditReason,
			CSHandleLog csHandleLog) throws DaoException;

	/**
	 * 后台 查询客服操作记录
	 * 
	 * @param orderLogId
	 *            外键关联的订单logId
	 * @param orderLogType
	 *            外键关联log表类型
	 */
	public List<CSHandleLog> findCsHandleLogListAdmin(Long orderLogId,
			String orderLogType);
	/**
	 * 后台 保存客服操作记录
	 * 
	 * @param csHandleLog
	 *            执行操作的客服信息
	 */
	public Long saveCsHandleLogAdmin(CSHandleLog csHandleLog) throws DaoException;
	/**
	 * 检查用户输入的多个用户名是否是系统内用户
	 * @param userNames name1,name2,name3,name4
	 */
	public boolean checkInputUserNames(String userNames);
	
	/*************************银行模块************************/
	/**
	 * 获取所有银行
	 * @return
	 */
	public List<Bank> findAllBank();
	/**
	 * 更改银行状态
	 * 
	 * checkedIds 银行Id
	 * @param status
	 *            '1':可用  '2':不可用
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateBankStatus(String checkedIds, String status,Integer bankType)throws DaoException;
	/**
	 * 根据字典类型获取相应字典列表
	 */
	public List<Dictionary> getDictionaryListByType(String type) throws DaoException;
	/**
	 * 心水前台支付 2010 02-23 15:28 用户第一次购买心水
	 * 返回值:
	 *  "1":余额足够本次支付，支付成功
	 *  "4":余额不足 金额或彩金部分冻结
	 *  "5":用户账户锁定 禁止购买心水
	 * 
	 */
	public String  myXinshuiPay(Long c2cId,Long buyUserId,String useCaijin,String buyUserName)throws DaoException;
	/**
	 * 心水前台支付 2010 02-26 10:57前台普通取消自己的心水订单,单订单支付状态必须是:'1',未支付
	 * 返回:
	 * '1':成功取消订单
	 * '-1':取消订单失败
	 */
	public int  cancelXinshuiPay(Long xinshuiOrderId,Long buyUserId)throws DaoException;
	/**
	 * 心水前台支付 2010 03-01 11:48 续费
	 * 返回:
	 * 1:正常返回
	 * -1:报错
	 * 
	 */
	public int  continueXinshuiPay(Long xinshuiOrderId,String resultValue)throws DaoException;
	
	//-----------------------b2c心水支付   2010-03-16 09:36-----------------------------------------------------
	public int  b2CXinshuiPay(Long b2cOrderId)throws DaoException;
}
