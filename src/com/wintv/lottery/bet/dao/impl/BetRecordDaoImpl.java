package com.wintv.lottery.bet.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.framework.common.OracleSqlUtil;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.dao.BetRecordDao;
import com.wintv.lottery.bet.utils.CommUtil;
import com.wintv.lottery.bet.vo.CanyuVO;

@SuppressWarnings("unchecked")
@Repository("betRecordDao")
public class BetRecordDaoImpl extends BaseHibernate implements  BetRecordDao{
	private static final Log log=LogFactory.getLog(BetRecordDaoImpl.class);
	 public BetOrder findProperties(Long betId)throws DaoException{
			Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			BetOrder po=null;
			try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      StringBuilder queryList=new StringBuilder("select t.bet_categoty,t.type from  t_bet_order t  where  t.bet_id=?");
		      pstmt = conn.prepareStatement(queryList.toString());
		      pstmt.setLong(1,betId);
		      rs=pstmt.executeQuery();
		      while (rs.next()) {
		    	  po=new BetOrder();
		    	  String betCategory=rs.getString("bet_categoty");
		    	  po.setBetCategory(betCategory);
		    	  String type=rs.getString("type");
		    	  po.setType(type);
		    	return po;
		      }
		    }catch(Exception e){
		    	e.printStackTrace();
		    }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		    return null;
		}
	/**我的认购明细 参与和买时  2010-04-08 17:43**/
    public List<CanyuVO> findMyBuyList(Long userId,Long betId)throws DaoException{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<CanyuVO> resultList=null;
		try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("select round(a.subscribe_copys*100/(select sp.divide_copys  from  t_bet_order sp  where sp.bet_id=?),2) auto_ratio,  a.bet_username,a.subscribe_copys,a.subscribe_money,a.bonus,to_char(a.bet_time,'yyyy-mm-dd hh24:mi')  bet_time from t_bet_order a");
	      queryList.append(" where a.BET_USERID=? and (a.sponsor_bet_id=? or a.bet_id=?)  order by  a.bet_time ");
	    
	      pstmt = conn.prepareStatement(queryList.toString());
	      pstmt.setLong(1,betId);
	      pstmt.setLong(2,userId);
	      pstmt.setLong(3,betId);
	      pstmt.setLong(4,betId);
	      rs=pstmt.executeQuery();
	      resultList=new ArrayList<CanyuVO>();
	      CanyuVO vo=null;
	      while (rs.next()) {
	    	  vo=new CanyuVO();
	    	  String betUsername=rs.getString("bet_username");
	    	  vo.setBetUsername(betUsername);
	    	  int subscribeCopys=rs.getInt("subscribe_copys");
	    	  vo.setSubscribeCopys(subscribeCopys);
	    	  String subscribeMoney=rs.getString("subscribe_money");
	    	  if(StringUtils.isNotEmpty(subscribeMoney)){
	    		subscribeMoney=CommUtil.getCurrency(subscribeMoney);
	    	    vo.setSubscribeMoney(subscribeMoney);
	    	  }else{
	    		vo.setSubscribeMoney("0");
	    	  }
	    	  String bonus=rs.getString("bonus");
	    	  if(StringUtils.isNotEmpty(bonus)){
	    		  bonus=CommUtil.getCurrency(bonus);
	    	      vo.setBonus(bonus);
	    	  }else{
	    		vo.setBonus("0");
	    	  }
	    	  String betTime=rs.getString("bet_time");
	    	  vo.setBetTime(betTime);
	    	  double autoRatio=rs.getDouble("auto_ratio");
	    	  String _autoRatio=CommUtil.myRound2(autoRatio,2);
	    	  vo.setAutoRatio(_autoRatio);
	    	  resultList.add(vo);
	     }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    return resultList;
	}
	/**根据期次ID,查询主客队**/
	public List<String> findHGList(Long phaseId)throws DaoException{
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 StringBuilder sql=new StringBuilder("select b.host_name,b.guest_name");
		 sql.append(" from T_LOTTERY_AGAINST a,T_AGAINST b where a.AGAINST_ID=b.AGAINST_ID and  a.PHASE_ID=?");
		 sql.append(" order by a.Changci ");
		 List<String> resultList=null;
		 try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, phaseId);
		      rs=pstmt.executeQuery();
		      resultList=new ArrayList<String>();
		      AgainstVo po=null;
		      while(rs.next()){
		    	  
		    	  String  hostName=rs.getString("host_name");
		    	  resultList.add(hostName);
		    	  String guestName=rs.getString("guest_name");
		    	  resultList.add(guestName);
		    
		      }
		   
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		return resultList;
	}
	public long findParticipateSize(Map params)throws DaoException{
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	    Long betId=(Long)params.get("betId");//期次
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql="select  count(1) cnt from t_bet_order a where ( a.SPONSOR_BET_ID=?  or a.BET_ID=?)";
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setLong(1,betId);
	      pstmt.setLong(2,betId);
	      rs=pstmt.executeQuery();
	      while (rs.next()) {
	    	return rs.getLong("cnt");
	      }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    return 0;
	}
	public List<CanyuVO> findParticipateList(Map params)throws DaoException{
		
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Integer startRow=(Integer)params.get("startRow");
		Integer pageSize=(Integer)params.get("pageSize");
		Long betId=(Long)params.get("betId");//发起人订单ID
	    List<CanyuVO> resultList=null;
		try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("select   a.bet_username,a.subscribe_copys,a.subscribe_money,a.bonus,to_char(a.bet_time,'yyyy-mm-dd hh24:mi')  bet_time from t_bet_order a");
	      queryList.append(" where ( a.SPONSOR_BET_ID=?  or a.BET_ID=? ) order by a.bet_time ");
	      
	      if(pageSize==null){
	    	  pstmt = conn.prepareStatement(queryList.toString());
	      }else{
	    	  StringBuilder sql=OracleSqlUtil.addQueryPageSizeCondition(queryList,startRow,pageSize);
	    	  pstmt = conn.prepareStatement(sql.toString());
	      }
    	 
	      pstmt.setLong(1,betId);
	      pstmt.setLong(2,betId);
	      rs=pstmt.executeQuery();
	      resultList=new ArrayList<CanyuVO>();
	      CanyuVO vo=null;
	      while (rs.next()) {
	    	  
	    	  vo=new CanyuVO();
	    	  String betUsername=rs.getString("bet_username");
	    	  vo.setBetUsername(betUsername);
	    	  int subscribeCopys=rs.getInt("subscribe_copys");
	    	  vo.setSubscribeCopys(subscribeCopys);
	    	  String subscribeMoney=rs.getString("subscribe_money");
	    	  if(StringUtils.isNotEmpty(subscribeMoney)){
	    		subscribeMoney=CommUtil.getCurrency(subscribeMoney);
	    	    vo.setSubscribeMoney(subscribeMoney);
	    	  }else{
	    		vo.setSubscribeMoney("0");
	    	  }
	    	  String bonus=rs.getString("bonus");
	    	  if(StringUtils.isNotEmpty(bonus)){
	    		  bonus=CommUtil.getCurrency(bonus);
	    	      vo.setBonus(bonus);
	    	  }else{
	    		vo.setBonus("0");
	    	  }
	    	  String betTime=rs.getString("bet_time");
	    	  vo.setBetTime(betTime);
	    	  resultList.add(vo);
	     }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    return resultList;
	}
	/**
	 * 根据期次ID查询14场对阵列表
	 */
	public List<String> findHostAgainstList(Map params)throws DaoException{
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 Long phaseId=(Long)params.get("phaseId");//期次表ID
		 StringBuilder sql=new StringBuilder("select b.host_name ");
		 sql.append(" from T_LOTTERY_AGAINST a,T_AGAINST b where a.AGAINST_ID=b.AGAINST_ID and  a.PHASE_ID=?");
		 sql.append(" order by a.Changci ");
		
		
		 List<String> resultList=null;
		 try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, phaseId);
		      rs=pstmt.executeQuery();
		      resultList=new ArrayList<String>();
		      while(rs.next()){
		    	  String  hostName=rs.getString("host_name");
		    	  resultList.add(hostName);
		      }
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		return resultList;
	}
	
	public List<Against> findAgainstList(Long phaseId)throws DaoException{
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 StringBuilder sql=new StringBuilder("select a.against_id,b.host_name,b.guest_name ");
		 sql.append(" from T_LOTTERY_AGAINST a,T_AGAINST b where a.AGAINST_ID=b.AGAINST_ID and  a.PHASE_ID=?");
		 sql.append(" order by a.Changci ");
		 List<Against> resultList=null;
		
		 try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, phaseId);
		      rs=pstmt.executeQuery();
		      resultList=new ArrayList<Against>();
		      Against po=null;
		      while(rs.next()){
		    	  po=new Against();
		    	  String  hostName=rs.getString("host_name");
		    	  po.setHostName(hostName);
		    	  String  guestName=rs.getString("guest_name");
		    	  po.setGuestName(guestName);
		    	  Long againstId=rs.getLong("against_id");
		    	  po.setAgainstId(againstId);
		    	  resultList.add(po);
		      }
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		return resultList;
	}
	/**
	 * 根据期次ID查询对阵列表
	 */
	public List<AgainstVo> findAgainstList(Map params)throws DaoException{
		 
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 Long phaseId=(Long)params.get("phaseId");//期次表ID
		  
		 StringBuilder sql=new StringBuilder("select a.against_id,b.host_name,b.guest_name,a.Changci ");
		 sql.append(" from T_LOTTERY_AGAINST a,T_AGAINST b where a.AGAINST_ID=b.AGAINST_ID and  a.PHASE_ID=?");
		 sql.append(" order by a.Changci ");
		 List<AgainstVo> resultList=null;
		 try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, phaseId);
		      rs=pstmt.executeQuery();
		      resultList=new ArrayList<AgainstVo>();
		      AgainstVo po=null;
		      while(rs.next()){
		    	  po=new AgainstVo();
		    	  String  hostName=rs.getString("host_name");
		    	  po.setHostName(hostName);
		    	  String guestName=rs.getString("guest_name");
		    	  po.setGuestName(guestName);
		    	  String changci=rs.getString("Changci");
		    	  po.setChangci(changci);
		    	  Long againstId=rs.getLong("against_id");
		    	  po.setAgainstId(againstId);
		    	  resultList.add(po);
		      }
		   
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		return resultList;
	}
	public String getTxtFile(Long id)throws DaoException{
		 
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 String sql="select  t.TXT_PATH from T_BET_ORDER t where t.BET_ID=?";
	     try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, id);
		      rs=pstmt.executeQuery();
		      while(rs.next()){
		    	return rs.getString("TXT_PATH");
		      }
		   
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		return null;
	}

}
