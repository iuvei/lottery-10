package com.wintv.lottery.admin.user.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.User;
import com.wintv.framework.utils.OracleSqlUtil;
import com.wintv.lottery.admin.user.dao.UserAdminDao;
import com.wintv.lottery.admin.user.vo.UserAccountInfoVO;
import com.wintv.lottery.admin.user.vo.UserAdminVO;
import com.wintv.lottery.admin.user.vo.UserInfoVO;
import com.wintv.lottery.admin.user.vo.UserXinshuiVO;
import com.wintv.framework.common.OnlineUserIdInSession;

/**
 * 
 * @author Hikin Yao
 *
 * @version 1.0.0
 */
@Repository("userAdminDao")
@SuppressWarnings("unchecked")
public class UserAdminDaoImpl extends BaseHibernate<User, Long> implements
		UserAdminDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 更改用户登录状态
	 * 
	 * checkedIds 用户Id
	 * @param status
	 *            1.正常 2.登录锁定
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateUserLoginStatus(String checkedIds, String status)
			throws DaoException {
		String sqlStr="update t_user t set t.status="+status+" where t.userid in ( "+checkedIds+" )";
		Integer result=jdbcTemplate.update(sqlStr);
		return result;
	}
	/**
	 * 更改用户在线状态
	 * 
	 * checkedIds 用户Id
	 * @param status
	 *            1.在线 2.不在线
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateUserOnlineStatus(String checkedIds, String status)
			throws DaoException {
		String sqlStr="update t_user t set t.is_online="+status+" where t.userid in ( "+checkedIds+" )";
		Integer result=jdbcTemplate.update(sqlStr);
		return result;
	}
	/**
	 * 更改用户资金状态
	 * 
	 * @userId 用户checkedIds
	 * @param status
	 *            1.正常 2.资金锁定
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateUserAccountStatus(String checkedIds, String status)
			throws DaoException {
		String sqlStr="update t_virtual_account t set t.status="+status+" where t.tx_user_id in ( "+checkedIds+" )";
		Integer result=jdbcTemplate.update(sqlStr);
		return result;
	}

	/**
	 * 按照条件对用户进行查询
	 * 
	 * @param queryCondition
	 *            查询条件字段有:注册时间区间,账户金额区间,投注金额区间,会员地区,关键字,用户等级,用户头衔,锁定状态
	 * @param orderFiled
	 *            排序字段 后台界面用户可选排序字段有：注册时间,账户金额,
	 * @param orderType
	 *            排序类型 “ASC|DESC”
	 * @param startRow
	 *            查询开始行
	 * @param pageSize
	 *            页面记录数大小
	 * @return 返回用户查询列表
	 */
	public List<UserAdminVO> searchUserList(StringBuilder queryCondition,
			String orderFiled, String orderType, Integer startRow,
			Integer pageSize) {

		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(" select * from v_admin_user_list c where 1=1 ");
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
						UserAdminVO userVO = new UserAdminVO();
						userVO.setUserId(rs.getLong("userId"));// 用户ID
						userVO.setUserStatus(rs.getString("userStatus"));// 用户登录状态
						userVO.setUserMoneyStatus(rs
								.getString("userMoneyStatus"));// 用户资金状态
						userVO.setUserName(rs.getString("userName"));// 用户名
						userVO.setUserGrade(rs.getString("userGrade"));// 用户等级
						userVO.setUserHonor(rs.getString("userHonor"));// 用户头衔
						userVO.setAccountBalance(rs
								.getBigDecimal("accountBalance"));// 用户余额
						userVO.setTotalWinPrize(rs
								.getBigDecimal("totalWinPrize"));// 总中奖金
						userVO.setTotalBet(rs.getBigDecimal("totalBet"));// 总投注额
						userVO.setUserMobile(rs.getString("userMobile"));// 会员手机
						
						userVO.setHeartWaterScore(rs.getBigDecimal("heartWaterScore"));//心水战绩
						userVO.setForecastPeriod(rs.getBigDecimal("releaseNum"));////预测场次
						
						userVO.setSaleHeartWater(rs
								.getBigDecimal("totalXinshuiSale"));// 销售心水
						userVO.setBuyHeartWater(rs
								.getBigDecimal("totalXinshuiBuy"));// 够心水额
						
						userVO.setTogetherBuysScore(rs.getBigDecimal("togetherBuysScore"));//合买战绩
						userVO.setReleaseTogetherBuys(rs.getBigDecimal("releaseTogetherBuys"));//发起合买
						userVO.setTogetherBuysSales(rs.getBigDecimal("togetherBuysSales"));//合买销售
						userVO.setArenaScore(rs.getBigDecimal("arenaScore"));//擂台战绩
						
						resultList.add(userVO);
					}
				});
		return resultList;
	}

	/**
	 * 统计符合查询条件的用户记录数
	 * 
	 * @param queryCondition
	 *            查询条件字段有:注册时间区间,账户金额区间,投注金额区间,会员地区,关键字,用户等级,用户头衔,锁定状态
	 * @return 返回根据检索条件统计当前会员数 默认统计总会员数
	 */
	public Integer countUsersBySearch(StringBuilder queryCondition) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("select count(*) from v_admin_user_list where 1=1");
		sqlStr.append(queryCondition);
		Query query = this.getSession().createSQLQuery(sqlStr.toString());
		BigDecimal result = (BigDecimal) query.uniqueResult();
		return Integer.parseInt(result.toString());
	}

	/**
	 * 获取用户基本信息与附加信息
	 * 
	 * @param userId
	 * @return 返回用户对象
	 */
	public UserInfoVO getUserInfo(Long userId) throws DaoException {
		try {
			String sqlStr = "select * from v_admin_user_detail u where u.userid="
					+ userId;
			final List resultList = new ArrayList();
			jdbcTemplate.query(sqlStr.toString(), resultList.toArray(),
					new RowCallbackHandler() {
						public void processRow(ResultSet rs)
								throws SQLException {
							UserInfoVO userInfoVO = new UserInfoVO();
							Long userId=rs.getLong("userId");
							userInfoVO.setUserId(userId);// 用户ID
							userInfoVO.setUserName(rs.getString("userName"));// 用户名
							userInfoVO.setUserGrade(rs.getString("userGrade"));// 用户等级
							userInfoVO.setUserHonor(rs.getString("userHonor"));// 用户头衔
							userInfoVO.setUserRealName(rs
									.getString("userRealName"));// 用户真实姓名
							userInfoVO.setUserIDCard(rs.getString("idcard"));// 用户身份证号
							userInfoVO
									.setUserStatus(rs.getString("userStatus"));// 用户登录状态
							userInfoVO.setUserMoneyStatus(rs
									.getString("moneyStatus"));// 用户资金状态
							userInfoVO.setUserRegIP(rs.getString("userRegIP"));// 用户注册IP
							userInfoVO.setUserLongInTimes(rs
									.getInt("userLongInTimes"));// 用户登录次数
							userInfoVO.setUserQQ(rs.getString("qq"));// 用户qq
							userInfoVO
									.setUserMobile(rs.getString("userMobile"));// 用户手机
							userInfoVO.setUserPhone(rs.getString("userPhone"));// 用户联系电话
							userInfoVO
									.setUserBirthday(rs.getString("birthday"));// 出生日期
							userInfoVO.setUserCity(rs.getString("userCity"));
							userInfoVO.setUserProvince(rs.getString("userProvince"));
							userInfoVO.setUserSex(rs.getString("sex").equals("2")?"女":"男");// 用户性别
							userInfoVO.setUserAccountBankProvince(rs
									.getString("bankProvince"));// 用户开户行省
							userInfoVO.setUserAccountBankCity(rs
									.getString("bankCity"));// 用户开户市
							userInfoVO.setUserAccountBank(rs
									.getString("bank_name"));// 用户开户银行
							userInfoVO.setUserBankCardNumber(rs
									.getString("card_num"));// 银行卡号
							//判断用户是否在线
							userInfoVO.setUserIsOnLine(OnlineUserIdInSession.isUserOnline(userId)==true?"1":"0");
							resultList.add(userInfoVO);
						}
					});
			if (null != resultList && resultList.size() > 0) {
				return (UserInfoVO) resultList.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	/**
	 * 获取会员账户信息
	 * 
	 * @param userId
	 *            所查询用户ID
	 * 
	 * @return 返回用户对象
	 */
	public UserAccountInfoVO getUserAccountInfo(Long userId) {
		String sqlStr = "select * from v_admin_account_detail a where a.userId="
				+ userId;
		final List resultList = new ArrayList();
		jdbcTemplate.query(sqlStr.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						UserAccountInfoVO userAccountInfoVO = new UserAccountInfoVO();
						userAccountInfoVO.setUserId(rs.getLong("userId"));// 用户ID
						userAccountInfoVO.setCurrentMoney(rs
								.getBigDecimal("currentMoney"));// 当前账户余额
						userAccountInfoVO.setFrozenMoney(rs
								.getBigDecimal("frozenMoney"));// 当前冻结资金
						userAccountInfoVO.setTotalDrawMoney(rs
								.getBigDecimal("totalDrawMoney"));// 总取款额

						userAccountInfoVO.setTotalPayMoney(rs
								.getBigDecimal("totalPayMoney"));// 总充值金额
						userAccountInfoVO.setTotalBetMoney(rs
								.getBigDecimal("totalBetMoney"));// 总投注金额
						userAccountInfoVO.setTotalWinMoney(rs
								.getBigDecimal("totalWinMoney"));// 总中奖金额

						userAccountInfoVO.setCurrentGold(rs
								.getBigDecimal("currentGold"));// 当前可用彩金
						userAccountInfoVO.setTotalGold(rs
								.getBigDecimal("totalGold"));// 总彩金
						userAccountInfoVO.setTotalUseGold(rs
								.getBigDecimal("totalUseGold"));// 总使用彩金

						userAccountInfoVO.setCurrentPoint(rs
								.getInt("currentPoint"));// 当前可用积分
						userAccountInfoVO
								.setTotalPoint(rs.getInt("totalPoint"));// 总积分
						userAccountInfoVO.setTotalUsePoint(rs
								.getInt("totalUsePoint"));// 总使用积分
						resultList.add(userAccountInfoVO);
					}
				});
		if (null != resultList && resultList.size() > 0) {
			return (UserAccountInfoVO) resultList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据用户选中的多个用户id 获取相应的多个用户
	 * 
	 * @param checkedIds
	 *            选择的多个用户Id，中间用逗号分隔
	 * @return 返回相应的用户列表
	 */
	public List<User> getUserListByUserIds(String checkedIds){
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("select * from t_user t where  t.userid in (" + checkedIds
				+ ")");
		System.out.println(sqlStr);
		final List resultList = new ArrayList();
		jdbcTemplate.query(sqlStr.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						User user = new User();
						user.setUserid(rs.getLong("USERID"));
						user.setUsername(rs.getString("USERNAME"));
						resultList.add(user);
					}
				});
		return resultList;
	}
	public UserXinshuiVO loadUserXinshuiInfo(Long userId){
		StringBuilder sql=new StringBuilder();
		
		sql.append("select  tz.XINSHUI_MILITARY as militaryScore,");   //心水战绩
		sql.append("(select count(1) cnt from T_C2C_PRODUCT a1 where a1.TX_USER_ID="+userId+") yccc, ");//总预测场次
		sql.append("(select sum(TX_MONEY+TX_CAIJIN) cnt from T_XINSHUI_ORDER b1 where b1.BUY_USER_ID="+userId+") buyXinshuiCnt,");//购买高手心水总次数
		sql.append("(select  sum(TX_MONEY) from T_VA_TRANSACTION_LOG c3  where  c3.TX_TYPE='6' and  c3.FLG='1'  and  c3.TX_USER_ID="+userId+")  buyXinshuiMoney ,");//购买高手心水总金额
		sql.append("(select  sum(TX_MONEY) from T_VA_TRANSACTION_LOG c4  where  c4.TX_TYPE='7' and  c4.FLG='2'  and  c4.TX_USER_ID="+userId+")  xinshuiBuchang ,");//得到总赔偿金额
		sql.append("(select  count(1)  from T_C2C_PRODUCT d5,T_AGAINST e5  where  d5.AGAINST_ID=e5.AGAINST_ID and d5.TX_USER_ID="+userId+" and e5.START_TIME>sysdate) curPubChangci,");//当前发布场次
		sql.append("(select  count(1)  from T_XINSHUI_ORDER a6,T_C2C_PRODUCT b6,T_AGAINST c6  where  a6.PRODUCT_ID=b6.C2C_ID   and b6.AGAINST_ID=c6.AGAINST_ID ");
		sql.append("and c6.START_TIME>sysdate ");
		sql.append("and a6.SOLD_USER_ID="+userId+" and a6.PAY_STATUS='2') curSoldCnt,");//当前销售人数
		sql.append("(select  sum(TX_MONEY+TX_CAIJIN)  from T_XINSHUI_ORDER a7,T_C2C_PRODUCT b7,T_AGAINST c7  ");
		sql.append("where  a7.PRODUCT_ID=b7.C2C_ID  and b7.AGAINST_ID=c7.AGAINST_ID and c7.START_TIME>sysdate  and a7.SOLD_USER_ID="+userId+"  and a7.PAY_STATUS='2') curSoldMoney,");//当前销售金额
		sql.append("(select  sum(ENSURE_MONEY)  from T_C2C_PRODUCT a8,T_AGAINST b8  where  a8.AGAINST_ID=b8.AGAINST_ID ");
		sql.append("and a8.TX_USER_ID="+userId+"  and b8.START_TIME>sysdate) curEnsureMoney,");//当前保证金
		sql.append("(select  count(1)  from T_C2C_PRODUCT d9,T_AGAINST e9  where  d9.AGAINST_ID=e9.AGAINST_ID and d9.TX_USER_ID="+userId+"  and e9.START_TIME>sysdate) totalPubChangci,");//总发布场次
		sql.append("(select  count(1)  from T_XINSHUI_ORDER aaa,T_C2C_PRODUCT bbb,T_AGAINST ccc  where  aaa.PRODUCT_ID=bbb.C2C_ID ");   
		sql.append("and bbb.AGAINST_ID=ccc.AGAINST_ID and ccc.START_TIME>sysdate  and aaa.SOLD_USER_ID="+userId+" and aaa.PAY_STATUS='2') totalSoldCnt,");//总销售人数
		sql.append("(select  sum(TX_MONEY+TX_CAIJIN)  from T_XINSHUI_ORDER am,T_C2C_PRODUCT bm,T_AGAINST cm  where  am.PRODUCT_ID=bm.C2C_ID ");   
		sql.append("and bm.AGAINST_ID=cm.AGAINST_ID and cm.START_TIME>sysdate  and am.SOLD_USER_ID="+userId+" and am.PAY_STATUS='2') totalSoldMoney,  "); //总销售金额
		sql.append(" (select sum(lo.tx_money) from t_va_transaction_log   lo   where lo.Category_Type='16' and lo.flg='2' and lo.tx_user_id="+userId+") totalPeichangMoney ");
		sql.append("from t_user tz where tz.USERID="+userId);
		//UserXinshuiVO vo=new UserXinshuiVO();
		System.out.println(sql);
		UserXinshuiVO vo=(UserXinshuiVO)jdbcTemplate.queryForObject(sql.toString(), new BeanPropertyRowMapper(UserXinshuiVO.class));
		return vo;
	}
}
