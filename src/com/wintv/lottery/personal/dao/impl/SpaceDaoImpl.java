package com.wintv.lottery.personal.dao.impl;

 
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.attention.vo.AttentionPlanVO;
import com.wintv.lottery.attention.vo.AttentionXinshuiVO;
import com.wintv.lottery.bet.utils.CommUtil;
import com.wintv.lottery.personal.dao.SpaceDao;
import com.wintv.lottery.personal.vo.BonusVO;
import com.wintv.lottery.personal.vo.DynaInfoVO;
import com.wintv.lottery.personal.vo.KingInfo;
import com.wintv.lottery.personal.vo.LastVisitedVO;
import com.wintv.lottery.personal.vo.SpaceVO;

@SuppressWarnings("unchecked")
@Repository("spaceDao")
public class SpaceDaoImpl  extends BaseHibernate implements SpaceDao{
	/**根据个人签名 2010-04-26 15:08**/
	public boolean  updateSignature(String newSignature,Long userId)throws DaoException{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="update t_user t set t.signature=? where t.userid=?";
		try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setString(1, newSignature);
			 pstmt.setLong(2, userId);
			 pstmt.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 本站热门心水推荐  2010-04-22 18:00
	 * 
	 **/
	public List findHotXinshui(Map params)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call SPACE.findHotXinshui(?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
	      proc.execute();
	      AttentionXinshuiVO vo=null;
          List<AttentionXinshuiVO> hotXinshuiList=new ArrayList<AttentionXinshuiVO>();
          rs = (ResultSet)proc.getObject(1);//本站热门心水推荐
          while(rs.next()){
        	  vo=new AttentionXinshuiVO();
				String hostName=rs.getString("host_name");
				vo.setHostName(hostName);
				String guestName=rs.getString("guest_name");
				vo.setGuestName(guestName);
				String xinshuiNo=rs.getString("xinshui_no");
				vo.setXinshuiNo(xinshuiNo);
				String price=rs.getString("price");
				price=CommUtil.getCurrency(price);
				vo.setPrice(price);
				String raceName=rs.getString("race_name");
				vo.setRaceName(raceName);
				String soldUserName=rs.getString("tx_username");
				vo.setSoldUserName(soldUserName);
				String startTime=rs.getString("start_time");
				vo.setStartTime(startTime);
				String ensureMoney=rs.getString("ensure_money");
				vo.setEnsureMoney(ensureMoney);
				int soldCnt=rs.getInt("sold_cnt");//购买人数
				vo.setSoldCnt(soldCnt);
				String winRatio=rs.getString("win_ratio");
				vo.setWinRatio(winRatio);
				hotXinshuiList.add(vo);
		  }
          
	     return hotXinshuiList;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	
	}
	/**本站热门方案推荐  2010-04-15 10:01**/
	public List findHotPlanList(String betCategory)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        List<AttentionPlanVO> resultList=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call SPACE.findHotPlanList(?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setString(1, betCategory);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
	      proc.execute();
	    
          rs = (ResultSet)proc.getObject(2);//他发起的投注
          resultList=new ArrayList<AttentionPlanVO>();
          AttentionPlanVO po=null;
          while(rs.next()){
        	     po=new AttentionPlanVO();
				 Long id=rs.getLong("bet_id");
				 po.setId(id);
			     String betCategoty=rs.getString("bet_category");
			     po.setBetCategoty(betCategoty);
				 String lotteryType=rs.getString("type");
				 po.setLotteryType(lotteryType);
				 String phaseNo=rs.getString("phase_no");
				 po.setPhaseNo(phaseNo);
				 String sponsorUsername=rs.getString("bet_username");
				 po.setSponsorUsername(sponsorUsername);
				 String stars=rs.getString("bet_military");
				 po.setStars(stars);
				 BigDecimal allMoney=rs.getBigDecimal("all_money");
				 po.setAllMoney(allMoney);
				 String progress=rs.getString("progress");
				 po.setProgress(progress);
			  resultList.add(po);
		  }
        
	     return resultList;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	
	}
	/**点击当前投注  2010-04-15 09:31**/
	public Map findCurrentBet(Long userId)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        Map resultMap=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call SPACE.findCurrentBet(?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setLong(1,userId);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
	     
	      proc.execute();
	      resultMap=new HashMap();
          rs = (ResultSet)proc.getObject(2);//他发起的投注
          List<AttentionPlanVO> curSponBetList=new ArrayList<AttentionPlanVO>();
          AttentionPlanVO po=null;
          while(rs.next()){
        	     po=new AttentionPlanVO();
				 Long id=rs.getLong("bet_id");
				 po.setId(id);
			     String betCategoty=rs.getString("bet_category");
			     po.setBetCategoty(betCategoty);
				 String lotteryType=rs.getString("type");
				 po.setLotteryType(lotteryType);
				 String phaseNo=rs.getString("phase_no");
				 po.setPhaseNo(phaseNo);
				 String sponsorUsername=rs.getString("bet_username");
				 log.info("bet_username="+sponsorUsername);
				 po.setSponsorUsername(sponsorUsername);
				 String stars=rs.getString("bet_military");
				 po.setStars(stars);
				 BigDecimal allMoney=rs.getBigDecimal("all_money");
				 po.setAllMoney(allMoney);
				 String progress=rs.getString("progress");
				 po.setProgress(progress);
				 curSponBetList.add(po);
		  }
          resultMap.put("curSponBetList", curSponBetList);
          List<AttentionPlanVO> curCanyuBetList=new ArrayList<AttentionPlanVO>();
          rs = (ResultSet)proc.getObject(3);//他参与的投注
          while(rs.next()){
        	     po=new AttentionPlanVO();
				 Long id=rs.getLong("bet_id");
				 po.setId(id);
			     String betCategoty=rs.getString("bet_category");
			     po.setBetCategoty(betCategoty);
				 String lotteryType=rs.getString("type");
				 po.setLotteryType(lotteryType);
				 String phaseNo=rs.getString("phase_no");
				 po.setPhaseNo(phaseNo);
				 String sponsorUsername=rs.getString("bet_username");
				 po.setSponsorUsername(sponsorUsername);
				 String stars=rs.getString("bet_military");
				 po.setStars(stars);
				 BigDecimal allMoney=rs.getBigDecimal("all_money");
				 po.setAllMoney(allMoney);
				 String progress=rs.getString("progress");
				 po.setProgress(progress);
				 curCanyuBetList.add(po);
		 }
          resultMap.put("curCanyuBetList", curCanyuBetList);
        
	     return resultMap;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	
	}
	/**
	 * 当前心水  2010-04-20 17:47
	 * 
	 **/
	public Map findCurXinshui(Long userId)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        Map resultMap=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call SPACE.findCurXinshui(?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setLong(1,userId);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
	     
	      proc.execute();
	      resultMap=new HashMap();
          List<AttentionXinshuiVO> curPubXinshuiList=new ArrayList<AttentionXinshuiVO>();
          rs = (ResultSet)proc.getObject(2);//他发布的心水
          AttentionXinshuiVO vo=null;
          while(rs.next()){
        	  vo=new AttentionXinshuiVO();
				String hostName=rs.getString("host_name");
				vo.setHostName(hostName);
				String guestName=rs.getString("guest_name");
				vo.setGuestName(guestName);
				String xinshuiNo=rs.getString("xinshui_no");
				vo.setXinshuiNo(xinshuiNo);
				String price=rs.getString("price");
				price=CommUtil.getCurrency(price);
				vo.setPrice(price);
				String raceName=rs.getString("race_name");
				vo.setRaceName(raceName);
				String soldUserName=rs.getString("tx_username");
				vo.setSoldUserName(soldUserName);
				String startTime=rs.getString("start_time");
				vo.setStartTime(startTime);
				String ensureMoney=rs.getString("ensure_money");
				vo.setEnsureMoney(ensureMoney);
				int soldCnt=rs.getInt("sold_cnt");//购买人数
				vo.setSoldCnt(soldCnt);
				String winRatio=rs.getString("win_ratio");
				vo.setWinRatio(winRatio);
			  curPubXinshuiList.add(vo);
			 }
          resultMap.put("curPubXinshuiList", curPubXinshuiList);
          List<AttentionXinshuiVO> curBuyXinshuiList=new ArrayList<AttentionXinshuiVO>();
          rs = (ResultSet)proc.getObject(3);//他购买的心水
          while(rs.next()){
        	  vo=new AttentionXinshuiVO();
				String hostName=rs.getString("host_name");
				vo.setHostName(hostName);
				String guestName=rs.getString("guest_name");
				vo.setGuestName(guestName);
				String xinshuiNo=rs.getString("xinshui_no");
				vo.setXinshuiNo(xinshuiNo);
				String price=rs.getString("price");
				price=CommUtil.getCurrency(price);
				vo.setPrice(price);
				String raceName=rs.getString("race_name");
				vo.setRaceName(raceName);
				String soldUserName=rs.getString("tx_username");
				vo.setSoldUserName(soldUserName);
				String startTime=rs.getString("start_time");
				vo.setStartTime(startTime);
				String ensureMoney=rs.getString("ensure_money");
				vo.setEnsureMoney(ensureMoney);
				int soldCnt=rs.getInt("sold_cnt");//购买人数
				vo.setSoldCnt(soldCnt);
				String winRatio=rs.getString("win_ratio");
				vo.setWinRatio(winRatio);
			  curBuyXinshuiList.add(vo);
		  }
          resultMap.put("curBuyXinshuiList", curBuyXinshuiList);
        
	     return resultMap;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	
	}
	/**点击首页**/
	public Map findHomeSpaceData(Long userId)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        Map resultMap=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call SPACE.findHomeSpaceData(?,?,?,?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setLong(1,userId);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(4,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(5,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(6,oracle.jdbc.OracleTypes.CURSOR);
	      proc.execute();
	      resultMap=new HashMap();
          rs = (ResultSet)proc.getObject(2);//他发起的投注
          List<AttentionPlanVO> curSponBetList=new ArrayList<AttentionPlanVO>();
          AttentionPlanVO po=null;
          while(rs.next()){
        	     po=new AttentionPlanVO();
				 Long id=rs.getLong("bet_id");
				 po.setId(id);
			     String betCategoty=rs.getString("bet_category");
			     po.setBetCategoty(betCategoty);
				 String lotteryType=rs.getString("type");
				 po.setLotteryType(lotteryType);
				 String phaseNo=rs.getString("phase_no");
				 po.setPhaseNo(phaseNo);
				 String sponsorUsername=rs.getString("bet_username");
				 po.setSponsorUsername(sponsorUsername);
				 String stars=rs.getString("bet_military");
				 po.setStars(stars);
				 BigDecimal allMoney=rs.getBigDecimal("all_money");
				 po.setAllMoney(allMoney);
				 String progress=rs.getString("progress");
				 po.setProgress(progress);
				 curSponBetList.add(po);
		  }
          resultMap.put("curSponBetList", curSponBetList);
          List<AttentionPlanVO> curCanyuBetList=new ArrayList<AttentionPlanVO>();
          rs = (ResultSet)proc.getObject(3);//他参与的投注
          while(rs.next()){
        	     po=new AttentionPlanVO();
				 Long id=rs.getLong("bet_id");
				 po.setId(id);
			     String betCategoty=rs.getString("bet_category");
			     po.setBetCategoty(betCategoty);
				 String lotteryType=rs.getString("type");
				 po.setLotteryType(lotteryType);
				 String phaseNo=rs.getString("phase_no");
				 po.setPhaseNo(phaseNo);
				 String sponsorUsername=rs.getString("bet_username");
				 po.setSponsorUsername(sponsorUsername);
				 String stars=rs.getString("bet_military");
				 po.setStars(stars);
				 BigDecimal allMoney=rs.getBigDecimal("all_money");
				 po.setAllMoney(allMoney);
				 String progress=rs.getString("progress");
				 po.setProgress(progress);
				 curCanyuBetList.add(po);
		 }
          resultMap.put("curCanyuBetList", curCanyuBetList);
          List<AttentionXinshuiVO> curPubXinshuiList=new ArrayList<AttentionXinshuiVO>();
          rs = (ResultSet)proc.getObject(4);//他发布的心水
          AttentionXinshuiVO vo=null;
          while(rs.next()){
        	  vo=new AttentionXinshuiVO();
				String hostName=rs.getString("host_name");
				vo.setHostName(hostName);
				String guestName=rs.getString("guest_name");
				vo.setGuestName(guestName);
				String xinshuiNo=rs.getString("xinshui_no");
				vo.setXinshuiNo(xinshuiNo);
				String price=rs.getString("price");
				price=CommUtil.getCurrency(price);
				vo.setPrice(price);
				String raceName=rs.getString("race_name");
				vo.setRaceName(raceName);
				String soldUserName=rs.getString("tx_username");
				vo.setSoldUserName(soldUserName);
				String startTime=rs.getString("start_time");
				vo.setStartTime(startTime);
				String ensureMoney=rs.getString("ensure_money");
				vo.setEnsureMoney(ensureMoney);
				int soldCnt=rs.getInt("sold_cnt");//购买人数
				vo.setSoldCnt(soldCnt);
			  curPubXinshuiList.add(vo);
			 }
          resultMap.put("curPubXinshuiList", curPubXinshuiList);
          List<AttentionXinshuiVO> curBuyXinshuiList=new ArrayList<AttentionXinshuiVO>();
          rs = (ResultSet)proc.getObject(5);//他购买的心水
          while(rs.next()){
        	  vo=new AttentionXinshuiVO();
				String hostName=rs.getString("host_name");
				vo.setHostName(hostName);
				String guestName=rs.getString("guest_name");
				vo.setGuestName(guestName);
				String xinshuiNo=rs.getString("xinshui_no");
				vo.setXinshuiNo(xinshuiNo);
				String price=rs.getString("price");
				price=CommUtil.getCurrency(price);
				vo.setPrice(price);
				String raceName=rs.getString("race_name");
				vo.setRaceName(raceName);
				String soldUserName=rs.getString("tx_username");
				vo.setSoldUserName(soldUserName);
				String startTime=rs.getString("start_time");
				vo.setStartTime(startTime);
				String ensureMoney=rs.getString("ensure_money");
				vo.setEnsureMoney(ensureMoney);
				int soldCnt=rs.getInt("sold_cnt");//购买人数
				vo.setSoldCnt(soldCnt);
			  curBuyXinshuiList.add(vo);
		  }
          resultMap.put("curBuyXinshuiList", curBuyXinshuiList);
          List<DynaInfoVO> dynaInfoList=new ArrayList<DynaInfoVO>();
          rs = (ResultSet)proc.getObject(6);//他的动态
          DynaInfoVO dpo=null;
          while(rs.next()){
        	  dpo=new DynaInfoVO();
        	  String phaseNo=rs.getString("phase_no");
        	  dpo.setPhaseNo(phaseNo);
        	  Long sponsorBetUserId=rs.getLong("bet_userid");
        	  dpo.setSponsorBetUserId(sponsorBetUserId);
        	  String username=rs.getString("bet_username");
        	  dpo.setUsername(username);
        	  String sponsorUsername=rs.getString("sponsorUsername");
        	  dpo.setSponsorUsername(sponsorUsername);
        	  String betCategory=rs.getString("bet_categoty");
        	  dpo.setBetCategory(betCategory);
        	  String allMoney=rs.getString("all_money");//方案总金额
        	  dpo.setAllMoney(allMoney);
        	  String betTime=rs.getString("jiange");//时间间隔
        	  dpo.setBetTime(betTime);
        	 dynaInfoList.add(dpo);
          }
          resultMap.put("dynaInfoList", dynaInfoList);
	     return resultMap;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	
	}
	/**访问个人中心  2010-04-14 16:19**/
	public boolean visteSpace(Long userId,Long spaceUserId)throws DaoException{
         Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 String sql="insert into T_SPACE_VISITED(ID,USER_ID,SPACE_USERID,VISIT_TIME) values (SEQ_SPACE_VISTED_ID.Nextval,?,?,sysdate)";
	     try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, userId);
		      pstmt.setLong(2, spaceUserId);
		    return pstmt.execute();
		  }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		return false;
	}
	public SpaceVO findSpaceData(Long userId)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        SpaceVO po=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call SPACE.findSpaceData(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setLong(1,userId);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(3,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(6,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(8,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(9,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(11,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(12,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(13,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(14,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(15,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(16,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(17,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(18,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(19,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.execute();
	      po=new SpaceVO();
          String regTime=proc.getString(2);
          po.setRegTime(regTime);
         
          long attentedCnt=proc.getLong(3);//被关注次数
          po.setAttentedCnt(attentedCnt);
         
          long attentCnt=proc.getLong(4);//他关注别人次数
          po.setAttentCnt(attentCnt);
          String username=proc.getString(5);
          po.setUsername(username);
          long betMilitary=proc.getLong(6);//他的购彩战绩
          po.setBetMilitary(betMilitary);
          long xinshuiMilitary=proc.getLong(7);//他的心水战绩
          po.setXinshuiMilitary(xinshuiMilitary);
         
          String spSuccess=proc.getString(9);//发单成功率
          po.setSpSuccess(spSuccess);
          long canYuCn=proc.getLong(10);//参与合买次数
          po.setCanYuCn(canYuCn);
          long zjCnt=proc.getLong(12);//总中奖次数 
          po.setZjCnt(zjCnt);
          long pubXinshui=proc.getLong(13);//3发布心水数 
          po.setPubXinshui(pubXinshui);
          long soldXinshuiCnt=proc.getLong(14);//4销售心水人数
	      po.setSoldXinshuiCnt(soldXinshuiCnt);
	      rs = (ResultSet)proc.getObject(8);//如果她是金牌发起人，则返回对应彩种已经定制跟单的人数 格式  胜负彩14场;21
	      List<KingInfo>  kingList=new ArrayList<KingInfo>();
	      KingInfo kingInfo=null;
	      while(rs.next()){
	    	  kingInfo=new KingInfo();
	    	  String betCategory=rs.getString("bet_category");
	    	  kingInfo.setBetCategory(betCategory);
	    	  long dzCnt=rs.getLong("gd_cnt");//已经定制人数
	    	  kingInfo.setDzCnt(dzCnt);
	    	 kingList.add(kingInfo);
	      }
	      po.setKingList(kingList);
	      rs = (ResultSet)proc.getObject(15);//如果她是金牌发起人，则返回对应彩种已经定制跟单的人数 格式  胜负彩14场;21
	      List<LastVisitedVO>  lastVisitedList=new ArrayList<LastVisitedVO>();
	      LastVisitedVO lastVisited=null;
	      while(rs.next()){
	    	  lastVisited=new LastVisitedVO();
	    	  String vUserName=rs.getString("username");
	    	  lastVisited.setUsername(vUserName);
	    	  long userGrade=rs.getLong("user_grade");
	    	  lastVisited.setUserGrade(userGrade);
	    	  long  _betMilitary=rs.getLong("bet_military");
	    	  lastVisited.setBetMilitary(_betMilitary);
	    	  long _xinshuiMilitary=rs.getLong("xinshui_military");
	    	  lastVisited.setXinshuiMilitary(_xinshuiMilitary);
	    	  lastVisitedList.add(lastVisited);
	      }
	      po.setLastVisitedList(lastVisitedList);
	      Long userGrade=proc.getLong(16);
	      po.setUserGrade(userGrade);
	      String bonusList=proc.getString(17);
	      po.setBonusList(bonusList);
	      //
	     rs = (ResultSet)proc.getObject(18);//最新中奖
	     List<BonusVO>  latestBonusList=new ArrayList<BonusVO>();
	     BonusVO vo=null;
	     while(rs.next()){
	    	 vo=new BonusVO();
	    	 String betCategory=rs.getString("lottery_name");
	    	 vo.setBetCategory(betCategory);
	    	 String phaseNo=rs.getString("phase");
	    	 vo.setPhaseNo(phaseNo);
	    	 String money=rs.getString("money");
	    	 vo.setMoney(money);
	    	 latestBonusList.add(vo);
	     }
	     po.setLatestBonusList(latestBonusList);
	     String signature=proc.getString(19);//个人中心的个人签名
	     po.setSignature(signature);
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return po;
	
	
	
	}
	private static final Log log=LogFactory.getLog(SpaceDaoImpl.class);

}
