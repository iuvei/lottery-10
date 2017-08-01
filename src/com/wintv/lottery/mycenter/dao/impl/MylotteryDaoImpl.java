package com.wintv.lottery.mycenter.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.utils.Util;
import com.wintv.lottery.mycenter.dao.MylotteryDao;
import com.wintv.lottery.mycenter.vo.BetRecordVO;
import com.wintv.lottery.mycenter.vo.LatestWinnerVO;
import com.wintv.lottery.mycenter.vo.UserCountVO;
import com.wintv.lottery.mycenter.vo.UserVO;
import com.wintv.lottery.mycenter.vo.XinshuiRecordVO;

/**
 * 我的天彩-dao
 * 
 * @author Arix04 by 2010-04-22
 * 
 * @version 1.0.0
 */

@Repository("mylotteryDao")
@SuppressWarnings("unchecked")
public class MylotteryDaoImpl extends BaseHibernate implements MylotteryDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserVO loadUserInfo(Long userid) throws DaoException {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("select ");
		sqlStr.append("u.username username,");
		sqlStr.append("u.user_grade vipLevel,");
		sqlStr.append("v.all_money-v.frozen_money fund,");
		sqlStr.append("v.frozen_money+v.frozen_mosaic_gold frozenMoney,");
		sqlStr.append("v.mosaic_gold-v.frozen_mosaic_gold caijin,");
		sqlStr.append("v.point point,");
		sqlStr.append("case when u.idcard is null then '0' else '1' end isBindID,");
		sqlStr.append("case when u.email is null then '0' else '1' end  isBindEmail,");
		sqlStr.append("case when u.password_answer is null then '0' else '1' end  isSetPwdProtect,");
		sqlStr.append("case when u.withdraw_pwd is null then '0' else '1' end  isSetWithdrawPwd,");
		sqlStr.append("u.title title,");
		sqlStr.append("u.bet_military betLevel,");
		sqlStr.append("u.lt_military challengeLevel,");
		sqlStr.append("u.xinshui_military xinshuiLevel");
		sqlStr.append(" from ");
		sqlStr.append("t_user u");
		sqlStr.append(" join ");
		sqlStr.append("t_virtual_account v");
		sqlStr.append(" on ");
		sqlStr.append("u.userid = v.tx_user_id");
		sqlStr.append(" where ");
		sqlStr.append("u.userid = ?");
		
		final UserVO user = new UserVO();
		user.setUserid(userid);
	    final Object[] params = new Object[] {userid};
	    jdbcTemplate.query(sqlStr.toString(), params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
            	user.setUsername(rs.getString("username"));
            	user.setVipLevel(rs.getString("vipLevel"));
            	user.setFund(Util.getDecimalFormat(rs.getBigDecimal("fund")));
            	user.setFrozenMoney(Util.getDecimalFormat(rs.getBigDecimal("frozenMoney")));
            	user.setCaijin(Util.getDecimalFormat(rs.getBigDecimal("caijin")));
            	user.setPoint(rs.getBigDecimal("point").toString());
            	user.setIsBindID(rs.getString("isBindID"));
            	user.setIsBindEmail(rs.getString("isBindEmail"));
            	user.setIsSetPwdProtect(rs.getString("isSetPwdProtect"));
            	user.setIsSetWithdrawPwd(rs.getString("isSetWithdrawPwd"));
            	user.setTitle(rs.getString("title"));
            	user.setBetLevel(rs.getString("betLevel"));
            	user.setChallengeLevel(rs.getString("challengeLevel"));
            	user.setXinshuiLevel(rs.getString("xinshuiLevel"));
            }                      
	    });          
		return user;
	}

	@Override
	public List<BetRecordVO> loadBetRecordList(Long userid) throws DaoException {
		StringBuilder sqlStr = new StringBuilder("select betId,betCategory,sponsorBetId," +
				"sponsorUsername,allMoney,phaseNo,subscribeMoney,orderStatus,planStatus," +
				"betTime,planCode,alreadyBuyCopys,floorCopys,divideCopys from v_bet_record_list where betUserId = ");
		sqlStr.append(userid);
		final List resultList = new ArrayList();
		jdbcTemplate.query(sqlStr.toString(), resultList.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				BetRecordVO betRecordVO = new BetRecordVO();
				betRecordVO.setBetId(rs.getLong("betId"));
				betRecordVO.setBetCategory(rs.getString("betCategory"));
				betRecordVO.setSponsorBetId(rs.getLong("sponsorBetId"));
				betRecordVO.setSponsorUsername(rs.getString("sponsorUsername"));
				betRecordVO.setAllMoney(Util.getDecimalFormat(rs.getBigDecimal("allMoney")));
				betRecordVO.setPhaseNo(rs.getString("phaseNo"));
				betRecordVO.setSubscribeMoney(Util.getDecimalFormat(rs.getBigDecimal("subscribeMoney")));
				betRecordVO.setOrderStatus(rs.getString("orderStatus"));
				betRecordVO.setPlanStatus(rs.getString("planStatus"));
				betRecordVO.setBetTime(rs.getString("betTime"));
				betRecordVO.setPlanCode(rs.getString("planCode"));
				betRecordVO.setAlreadyBuyCopys(rs.getLong("alreadyBuyCopys"));
				betRecordVO.setFloorCopys(rs.getLong("floorCopys"));
				betRecordVO.setDivideCopys(rs.getLong("divideCopys"));
				resultList.add(betRecordVO);
			}
		});
		return resultList;
	}

	@Override
	public List<XinshuiRecordVO> loadXinshuiRecordList(Long userid) throws DaoException {
		StringBuilder sqlStr = new StringBuilder("select xinshuiId,orderId,orderNo," +
				"pubUserid,pubUsername,raceName,hostName,guestName,startTime," +
				"xinshuiNo,ensureMoney,price,buyTime,payStatus,xinshuiStatus from v_xinshui_record_list where buyUserid = ");
		sqlStr.append(userid);
		final List resultList = new ArrayList();
		jdbcTemplate.query(sqlStr.toString(), resultList.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				XinshuiRecordVO xinshuiRecordVO = new XinshuiRecordVO();
				xinshuiRecordVO.setXinshuiId(rs.getLong("xinshuiId"));
				xinshuiRecordVO.setOrderId(rs.getLong("orderId"));
				xinshuiRecordVO.setOrderNo(rs.getString("orderNo"));
				xinshuiRecordVO.setPubUserid(rs.getLong("pubUserid"));
				xinshuiRecordVO.setPubUsername(rs.getString("pubUsername"));
				xinshuiRecordVO.setRaceName(rs.getString("raceName"));
				xinshuiRecordVO.setHostName(rs.getString("hostName"));
				xinshuiRecordVO.setGuestName(rs.getString("guestName"));
				xinshuiRecordVO.setStartTime(rs.getString("startTime"));
				xinshuiRecordVO.setXinshuiNo(rs.getString("xinshuiNo"));
				xinshuiRecordVO.setEnsureMoney(Util.getDecimalFormat(rs.getBigDecimal("ensureMoney")));
				xinshuiRecordVO.setPrice(Util.getDecimalFormat(rs.getBigDecimal("price")));
				xinshuiRecordVO.setBuyTime(rs.getString("buyTime"));
				xinshuiRecordVO.setPayStatus(rs.getString("payStatus"));
				xinshuiRecordVO.setXinshuiStatus(rs.getString("xinshuiStatus"));
				resultList.add(xinshuiRecordVO);
			}
		});
		return resultList;
	}
	
	public UserCountVO loadUserCount(Long userid) throws DaoException {
		Connection conn = null;
		CallableStatement proc = null;
		UserCountVO userCount = new UserCountVO();
		try{
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			String sql = "{call BET_HALL.listdynamicinfo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			proc = (CallableStatement)conn.prepareCall(sql);
			proc.setLong(1, userid);
			proc.registerOutParameter(2, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(6, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(7, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(8, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(9, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(10, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(11, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(12, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(13, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(14, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(15, oracle.jdbc.OracleTypes.NUMBER);
			proc.registerOutParameter(16, oracle.jdbc.OracleTypes.NUMBER);
			proc.execute();
			userCount.setDgUnPayCount(proc.getLong(2));
			userCount.setDgUnDrawLotteryCount(proc.getLong(3));
			userCount.setDgLotteryCount(proc.getLong(4));
			userCount.setHmReleaseCount(proc.getLong(5));
			userCount.setHmJoinCount(proc.getLong(6));
			userCount.setHmUnDrawLotteryCount(proc.getLong(7));
			userCount.setHmLotteryCount(proc.getLong(8));
			userCount.setBxUnPayCount(proc.getLong(9));
			userCount.setBxUnStartCount(proc.getLong(10));
//			userCount.setBxHitCount(proc.getLong(""));
			userCount.setBxCompensateCount(proc.getLong(11));
			userCount.setSxSuccessCount(proc.getLong(12));
			userCount.setSxUnStartCount(proc.getLong(13));
			userCount.setSxHitCount(proc.getLong(14));
			userCount.setSxCompensateCount(proc.getLong(15));
			userCount.setZjSuccessCount(proc.getLong(16));
			return userCount;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(proc,conn);
		}
		return null;
	}

	@Override
	public List<LatestWinnerVO> loadLatestWinnerList() throws DaoException {
		String sql = "select * from (select t.user_id userid,t.username username,t.lottery_name lotteryName," +
				"t.money money,t.phase phaseNo from t_bonus t order by t.kj_time desc) where rownum <= 5";
		final List resultList = new ArrayList();
		jdbcTemplate.query(sql, resultList.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				LatestWinnerVO latestWinnerVO = new LatestWinnerVO();
				latestWinnerVO.setUserid(rs.getLong("userid"));
				latestWinnerVO.setUsername(rs.getString("username"));
				latestWinnerVO.setLotteryName(rs.getString("lotteryName"));
				latestWinnerVO.setMoney(Util.getDecimalFormat(rs.getBigDecimal("money")));
				latestWinnerVO.setPhaseNo(rs.getString("phaseNo"));
				resultList.add(latestWinnerVO);
			}
		});
		return resultList;
	}

//	public UserCountVO loadUserCount2(Long userid) throws DaoException {
//		String sql = "{call BET_HALL.listdynamicinfo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
//		final Long userId = userid;
//		Object obj = jdbcTemplate.execute(sql,new CallableStatementCallback(){
//			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
//				cs.setLong(1, userId);
//				cs.registerOutParameter(2, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(6, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(7, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(8, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(9, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(10, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(11, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(12, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(13, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(14, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(15, oracle.jdbc.OracleTypes.NUMBER);
//				cs.registerOutParameter(16, oracle.jdbc.OracleTypes.NUMBER);
//				cs.execute();
//				UserCountVO userCount = new UserCountVO();
//				userCount.setDgUnPayCount(cs.getLong(2));
//				userCount.setDgUnDrawLotteryCount(cs.getLong(3));
//				userCount.setDgLotteryCount(cs.getLong(4));
//				userCount.setHmReleaseCount(cs.getLong(5));
//				userCount.setHmJoinCount(cs.getLong(6));
//				userCount.setHmUnDrawLotteryCount(cs.getLong(7));
//				userCount.setHmLotteryCount(cs.getLong(8));
//				userCount.setBxUnPayCount(cs.getLong(9));
//				userCount.setBxUnStartCount(cs.getLong(10));
//				userCount.setBxCompensateCount(cs.getLong(11));
//				userCount.setSxSuccessCount(cs.getLong(12));
//				userCount.setSxUnStartCount(cs.getLong(13));
//				userCount.setSxHitCount(cs.getLong(14));
//				userCount.setSxCompensateCount(cs.getLong(15));
//				userCount.setZjSuccessCount(cs.getLong(16));
//				cs.close();
//				return userCount;
//			} 
//		});
//		
//		if(obj != null) {
//			return (UserCountVO)obj;
//		}
//		
//		return null;
//	}
	
}
