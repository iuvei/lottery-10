package com.wintv.lottery.bet.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.wintv.lottery.admin.bet.vo.PhaseVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.dao.EnhancementDao;
import com.wintv.lottery.bet.vo.BetOrderVO;
@Repository("enhancementDao")
public class EnhancementDaoImpl extends BaseHibernate implements EnhancementDao{
	private static final Log log=LogFactory.getLog(EnhancementDaoImpl.class);
	/**
	 * --网站购彩大厅单场期次列表 2010-05-06 13:49
	 *
	 */
	public PhaseVO  findCurSinglePhase()throws DaoException{
        Connection conn=null;
 	    CallableStatement cstmt =null;
 	    ResultSet rs=null;
 	    PhaseVO po=null;
 	    try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call PHASE.loadCurSinglePhaseData(?,?)}"); 
			  cstmt.registerOutParameter(1, OracleTypes.NUMBER);
			  cstmt.registerOutParameter(2, OracleTypes.VARCHAR);
		      cstmt.execute();
			  long phaseId=cstmt.getLong(1);
		      String phaseNo=cstmt.getString(2);
		      po=new PhaseVO();
		      po.setPhaseId(phaseId);
		      po.setPhaseNo(phaseNo);
		  
		   return po;
	   } catch (Exception e){ 
                  e.printStackTrace();
	}finally{
		closeAll(rs,cstmt,conn);
	}
    return  null;
	
  
	}
	/**
	 * --网站购彩大厅期次列表 2010-05-06 13:49
	 * 参数:
	 * category:'6':胜负彩期次    '9':进球彩期次 '8':半全场期次
	 *
	 */
	public Map  findInAdvancePhaseMap(String category)throws DaoException{
        Connection conn=null;
 	    CallableStatement cstmt =null;
 	    ResultSet rs=null;
 	    Map resultMap=null;
 	    try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call PHASE.loadPhaseData(?,?,?,?)}"); 
			  cstmt.registerOutParameter(1, OracleTypes.NUMBER);
			  cstmt.registerOutParameter(2, OracleTypes.VARCHAR);
		      cstmt.registerOutParameter(3, OracleTypes.CURSOR);   
		      cstmt.setString(4,category);
			  cstmt.execute();
			  resultMap=new HashMap();
		      long phaseId=cstmt.getLong(1);
		      resultMap.put("phaseId", phaseId);
		      String phaseNo=cstmt.getString(2);
		      resultMap.put("phaseNo", phaseNo);
		      rs = (OracleResultSet)cstmt.getObject(3);
		      List<PhaseVO> list=new ArrayList<PhaseVO>();
		      PhaseVO po=null;
		      while(rs.next()){
		    	  po=new PhaseVO();
		    	  long _phaseId=rs.getLong("id");
		    	  po.setPhaseId(_phaseId);
		    	  String _phaseNo=rs.getString("phase_no");
		    	  po.setPhaseNo(_phaseNo);
		    	  list.add(po);
		      }
		      resultMap.put("list", list);
		   return resultMap;
	   } catch (Exception e){ 
                  e.printStackTrace();
	}finally{
		closeAll(rs,cstmt,conn);
	}
    return  null;
	
  
	}
	
	
	
	
	
	
	
	public boolean deleteRen9ChoiceList(Long betId)throws DaoException{
         Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 String sql="delete  from  t_bet_order_choice t where t.bet_order_id=?";
		 try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, betId);
		      pstmt.executeUpdate();
		    return true;
		  }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		return false;
	
	}
	public boolean updateBetOrder(Map params)throws DaoException{
         Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 String plan=(String)params.get("plan");
		 Long betId=(Long)params.get("betId");
		 Long userId=(Long)params.get("userId");
		 String sql=null;
		 sql="update t_bet_order t set t.plan=?  where t.bet_id=? and t.bet_userid=?";
		 try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setString(1, plan);
		      pstmt.setLong(2, betId);
		      pstmt.setLong(3, userId);
		      return pstmt.executeUpdate()>0?true:false;
		  }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		return false;
	
	}
	/**
	 * 根据期次ID查询对阵列表 2010-04-21 11:16
	 */
	public List<AgainstVo> findAgainstList(Map params)throws DaoException{
		 
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 Long phaseId=(Long)params.get("phaseId");//期次表ID
		 StringBuilder sql=new StringBuilder("select a.against_id,b.host_name,b.guest_name,a.Changci,");
		 sql.append(" to_char(b.start_time,'mm-dd hh24:mi') start_time,b.race_name from T_LOTTERY_AGAINST a,T_AGAINST b where a.AGAINST_ID=b.AGAINST_ID and  a.PHASE_ID=?");
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
		    	  String startTime=rs.getString("start_time");
		    	  po.setStartTime(startTime);
		    	  String raceName=rs.getString("race_name");
		    	  po.setRaceName(raceName);
		    	  resultList.add(po);
		      }
		   
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		return resultList;
	}
	  /**统计某一个人针对某一方案认购总份数  2010-04-21 09:56**/
	  public long getAllSubscribeCopys(Long userId,Long betId)throws DaoException{
            Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
		    try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      StringBuilder queryList=new StringBuilder("select sum(t.subscribe_copys)  cnt from  t_bet_order t ");
		      queryList.append(" where  (t.bet_id=? or t.sponsor_bet_id=? ) and t.bet_userid=?");
		      pstmt = conn.prepareStatement(queryList.toString());
		      pstmt.setLong(1,betId);
		      pstmt.setLong(2,betId);
		      pstmt.setLong(3,userId);
		      rs=pstmt.executeQuery();
		      while (rs.next()){
		    	  return rs.getLong("cnt");
		      }
		    }catch(Exception e){
		    	e.printStackTrace();
		    }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		    return 0;
		
		
	  }
	/**
	 * 稍后保存文件到数据库 2010-04-20 11;16
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public boolean uploadTxtPath(Map map)throws DaoException{
		Long userId=(Long)map.get("userId");
		Long betId=(Long)map.get("betId");
		String txtPath=(String)map.get("txtPath");
		String sql="update t_bet_order t set t.TXT_PATH='"+txtPath+"'  where t.BET_ID="+betId+" and t.BET_USERID="+userId;
		return this.updateBySql(sql);
	}
	public BetOrderVO loadBetOrder(Long betId)throws DaoException{
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		BetOrderVO po=null;
		try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("select  t.phase_id,t.bet_categoty,to_char(t.end_time,'yyyy-mm-dd hh24:mi') end_time,t.phase_no,t.bet_number,t.all_money,t.txt_path from t_bet_order t where t.bet_id=?");
	      pstmt = conn.prepareStatement(queryList.toString());
	      pstmt.setLong(1,betId);
	      rs=pstmt.executeQuery();
	      while (rs.next()){
	    	  po=new BetOrderVO();
	    	  String betCategory=rs.getString("bet_categoty");
	    	  po.setBetCategory(betCategory);
	    	  String phaseNo=rs.getString("phase_no");
	    	  po.setPhaseNo(phaseNo);
	    	  String allMoney=rs.getString("all_money");
	    	  po.setAllMoney(allMoney);
	    	  Long betNumber=rs.getLong("bet_number");
	    	  po.setBetNumber(betNumber);
	    	  String txtPath=rs.getString("txt_path");
	    	  po.setTxtPath(txtPath);
	    	  String endTime=rs.getString("end_time");
	    	  po.setEndTime(endTime);
	    	  String phaseId=rs.getString("phase_id");
	    	  po.setPhaseId(phaseId);
	    	return po;
	      }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    return null;
	
	}
	/**
	 * -撤单   2010-04-16 17:57
	 * 返回值:   1.撤单成功  2.进度超度50%不能撤单  4 非法操作    -1报错
	 *
	 */
	 public int cancelSpOrder(long userId,long betId)throws DaoException{
		    Connection conn=null;
	 	    CallableStatement cstmt =null;
	 	    try { 
				  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
				  cstmt = conn.prepareCall("{call BET_ENHANCEMENT.cancelSpOrder(?,?,?)}"); 
				  cstmt.setLong(1, userId);
				  cstmt.setLong(2, betId);
			      cstmt.registerOutParameter(3, OracleTypes.INTEGER);   
				  cstmt.execute();
			   int result= cstmt.getInt(3);
			   return result;
		   } catch (Exception e) { 
	                  e.printStackTrace();
				 
				  }finally{
					 try{
						  if(cstmt!=null){
							  cstmt.close();
						  }
						  if(conn!=null){
					        conn.close();
						  }
					  }catch(SQLException e){
						  e.printStackTrace();
					  }
				  }
	    return -1;
		
	  }
  /**
   * 保底   返回 4为非法操作   返回1 正常保底  2 保底份数太少 3保底份数太多
   *
   */
  public int floorBetOrder(long userId,long betId,long flooCopys)throws DaoException{
	    Connection conn=null;
 	    CallableStatement cstmt =null;
 	    try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call BET_ENHANCEMENT.floorBetOrder(?,?,?,?)}"); 
			  cstmt.setLong(1, userId);
			  cstmt.setLong(2, betId);
			  cstmt.setLong(3, flooCopys);
			  cstmt.registerOutParameter(4, OracleTypes.INTEGER);   
			  cstmt.execute();
		   return cstmt.getInt(4);
	   } catch (Exception e) { 
                  e.printStackTrace();
			 
			  }finally{
				 try{
					  if(cstmt!=null){
						  cstmt.close();
					  }
					  if(conn!=null){
				        conn.close();
					  }
				  }catch(SQLException e){
					  e.printStackTrace();
				  }
			  }
    return -1;
	
  }
}
