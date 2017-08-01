package com.wintv.framework.common;

public class Constants {
    public static final int TIME_INTERVAL=15;//对阵开赛前15分钟停止投注
	public static final String MIN_MINJIAN_GAOSHOU_GRADE = "5";//民间高手用户最低级别
	public static final String USERCOOKIE = "userCookie";

	public static final String PASS_ANSWER = "PASS_ANSWER";    //用户取回密码数据字典字段
	public static final String DICTIONARY_TYPE = "2"; 
	
	public static final String T_4_SCENE_GOAL = "1";// 四场进球(单式代购)投注结果
	public static final String T_4_SCENE_GOAL_COOP = "2";// 四场进球(单式合买)投注结果
	public static final String ALL_GOAL = "10";// 总进球数(单式代购)投注结果

	/** 交易类型说明,对应TX_TYPE */
	public static final String TRANSACTION_CHARGE = "1";// 账户充值
	public static final String TRANSACTION_WITHDRAW = "2";// 账户取款
	public static final String TRANSACTION_WIN = "3";// 中奖奖金
	public static final String TRANSACTION_BUY_LOTTERY = "4";// 购买彩票
	public static final String TRANSACTION_XINSHUI_REVENUE = "5";// 心水收入
	public static final String TRANSACTION_BUY_XINSHUI = "6";// 购买心水
	public static final String TRANSACTION_XINSHUI_COMPENSATION = "7";// 心水补偿
	public static final String TRANSACTION_KILL_ENSURE_MONEY = "8";// 保证金扣除
	public static final String TRANSACTION_MOSAIC_GOLD_ZS = "9";// 彩金赠送

	/** 交易返回类型* */
	public static final String TRANSACTION_SUCCESS = "1";// 交易成功
	public static final String TRANSACTION_MONEY_NOT_ENOUGH = "2";// 可用资金余额不足
	public static final String TRANSACTION_MOSAICGOLD_NOT_ENOUGH = "3";// 彩金余额不足
	public static final String TRANSACTION_POINT_NOT_ENOUGH = "4";// 积分不足
	public static final String TRANSACTION_MONEY_NOT_ENOUGH_TO_FROZEN = "5";//
	public static final String TRANSACTION_FROZENMONEY_NOT_ENOUGH = "6";//

	/** 订单名生成类型* */
	public static final String ORDER_NO_CHARGE = "CZ";// 账户充值
	public static final String ORDER_NO_WITHDRAW = "QK";// 账户取款
	public static final String ORDER_NO_WIN = "ZJ";// 中奖奖金
	public static final String ORDER_NO_BUY_LOTTERY = "GC";// 购买彩票
	public static final String ORDER_NO_XINSHUI_REVENUE = "XR";// 心水收入
	public static final String ORDER_NO_BUY_XINSHUI = "BX";// 购买心水
	public static final String ORDER_NO_XINSHUI_COMPENSATION = "XC";// 心水补偿
	public static final String ORDER_NO_KILL_ENSURE_MONEY = "KQ";// 保证金扣除
	public static final String ORDER_NO_MOSAIC_GOLD_ZS = "ZC";// 彩金赠送

	/** 关联订单表类型,对应CATEGORY_TYPE* */
	public static final String ORDER_CATEGORY_FBSINGLE = "1";//北京单场
																// T_FB_SINGLE
	public static final String ORDER_CATEGORY_WINNINGLOSING = "6";// 胜负彩
																	// T_WINNING_LOSING
	public static final String ORDER_CATEGORY_ARBITRARY_9 = "7";// 任九??为何没有任九
																// T_ARBITRARY_9
	public static final String ORDER_CATEGORY_SIXHALFCOMPLETE = "8";//半全场
																	// T_6_HALF_COMPLETE
	public static final String ORDER_CATEGORY_FOURSCENEGOAL = "9";//进球彩
																	// T_4_SCENE_GOAL
	public static final String ORDER_CATEGORY_BUY_B2C = "10";// 购买B2C
																// T_XINSHUI_LOG
	public static final String ORDER_CATEGORY_BUY_C2C = "11";// 购买C2C
																// T_XINSHUI_LOG
	public static final String ORDER_CATEGORY_CHARGE = "12";// 充值 T_CHARGE_LOG
	public static final String ORDER_CATEGORY_WITHDRAW = "13";// 取款
																// 关联表:T_WITHDRAW_LOG
	public static final String ORDER_CATEGORY_WIN = "14";// 中奖奖金 关联表:T_BONUS
	public static final String ORDER_CATEGORY_MOSAIC_GOLD_ZS ="15";// 彩金赠送
																	// 关联表:T_CAIJIN_DONATE
	public static final String ORDER_CATEGORY_ENSURY_MONEY_KQ ="16";//心水保证金赔款

	/** 冻结类型* */
	public static final String FROZEN_FREEZE = "1";// 冻结
	public static final String FROZEN_UNFREEZE = "2";// 解冻

	/** 取款状态* */
	public static final String WITHDRAW_NOT_ACCEPTED = "1";// 未受理
	public static final String WITHDRAW_ACCEPTED = "2";// 已受理
	public static final String WITHDRAW_TRANSFERRED = "3";// 已转账
	public static final String WITHDRAW_USER_CANCEL = "4";// 用户取消
	public static final String WITHDRAW_CS_CANCEL = "5";// 客服取消

	/** 支付状态* */
	public static final String CHARGE_NOT_PAID = "1";// 未支付
	public static final String CHARGE_PAID = "2";// 已支付

	/** 彩金订单状态* */
	public static final String CAIJIN_STATUS_UN_AUDIT = "1";// 未审核
	public static final String CAIJIN_STATUS_AUDIT = "2";// 已审核
	public static final String CAIJIN_STATUS_CANCEL = "3";// 已撤销

	/** 期次分类 * */
	public static final String PHASE_WINNING_LOSING = "1";// 胜负彩
	public static final String PHASE_GOAL = "2";// 进球彩期次
	public static final String PHASE_HALF_COMPLETE = "3";// 半全场期次
	public static final String PHASE_BEIJING_SINGLE = "4";// 北京单场期次
	public static final String PHASE_SPLENDID_SINGLE = "5";// 竞彩单场
	/** 期次状态* */
	public static final String PHASE_UN_AUDIT = "1";// 期次未审核
	public static final String PHASE_ALREADY_PUBLISHED = "4";//
	

	/** 后台客服-充值模块操作类型* */
	public static final String CS_CHARGE_OP_TYPE_ADD_ORDER = "1";// 客服添加一条充值订单
	public static final String CS_CHARGE_OP_TYPE_CONFIRM_PAY = "2";// 确认到款
	public static final String CS_CHARGE_OP_TYPE_CONFIRM_BANK_TRANAFER = "3";// 确认银行转账

	/** 后台客服-取款模块操作类型* */
	public static final String CS_DRAW_OP_TYPE_ACCEPT = "1";// 客服受理取款操作
	public static final String CS_DARW_OP_TYPE_SUCCESS = "2";// 执行取款成功操作
	public static final String CS_DRAW_OP_TYPE_CANCEL = "3";// 撤销取款操作

	/** 后台客服-彩金模块操作类型* */
	public static final String CS_CAIJIN_OP_TYPE_ADD_ORDER = "1";// 客服添加彩金订单
	public static final String CS_CAIJIN_OP_TYPE_AUDIT = "2";// 审核彩金
	public static final String CS_CAIJIN_OP_TYPE_CANCEL = "3";// 撤销彩金赠送
	/** 银行状态***/
	public static final String BANK_STATUS_ENABLE="1";//可用
	public static final String BANK_STATUS_DISABLE="2";//不可用
	
	/**心水支付状态**/
	public static final String XINSHUI_PAY_STATUS_UN_PAY="1";//未支付
	public static final String XINSHUI_PAY_STATUS_ALREADY_PAY="2";//已支付
	public static final String XINSHUI_PAY_STATUS_ALREADY_KQ="3";//已扣款
	public static final String XINSHUI_PAY_STATUS_PAYOFF="4";//已赔付
	public static final String XINSHUI_PAY_STATUS_CANCEL="5";//已取消
	/**心水状态**/
	public static final String XINSHUI_STATUS_NO_START="1";//未开赛
	public static final String XINSHUI_STATUS_WINNING="2";//赢
	public static final String XINSHUI_STATUS_LOSING="3";//负
	public static final String XINSHUI_STATUS_GOOUT="4";//走
	
	/**球队类型**/
	public static final String TEAM_LEAGUE_MATCHES = "1"; //联赛队
	public static final String TEAM_COUNTRY = "2";       //国家队
	public static final String TEAM_OTHER="3";           //其他队
	
	public static final String CUP_TEAM_PREFIX="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
	
	public static final String FILE_TYPE = "txt";
	public static final Long FILE_SIZE = 102400L;
	/**用户投注类型*/
	public static final String USER_BET_TYPE_WINDRAW14_MULTIPLE ="3"; //胜负14场复式代购
	/**投注模块上传方案文件夹*/
	public static final String UPLOAD_PALN_FILE="plan"; 
}
