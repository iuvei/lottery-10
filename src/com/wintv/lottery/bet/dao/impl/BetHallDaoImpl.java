package com.wintv.lottery.bet.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleResultSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.bet.dao.BetHallDao;
import com.wintv.lottery.personal.vo.BetOrderVO;

@Repository("betHallDao")
public class BetHallDaoImpl extends BaseHibernate implements BetHallDao{
	/**足球单场 合买推荐方案(含置顶) 针对杨总的界面  2010-04-26 14:00**/
	public List<BetOrderVO> loadDanchangAll()throws DaoException{
		  
          Connection conn=null;
		  CallableStatement proc=null;
		  ResultSet rs=null;
	      String sql = "{ call BET_HALL.listDanchangALL(?) }";
		  try{
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      proc = conn.prepareCall(sql);
		      proc.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);//输出游标记录集
		      proc.execute();
		     
		      rs = (OracleResultSet)proc.getObject(1);
		      BetOrderVO po=null;
		      List<BetOrderVO> resultList=new ArrayList<BetOrderVO>();
		      while(rs.next()){
		    	  
		    	  po=new BetOrderVO();
		    	  Long betId=rs.getLong("bet_id");
		    	  po.setBetId(betId);
		    	  String betCategoryZH=rs.getString("betCategoryZH");
			      po.setBetCategory(betCategoryZH);
			      String phaseNo=rs.getString("phase_no");
			      po.setPhaseNo(phaseNo);
			      String betUsername=rs.getString("bet_username");
			      po.setBetUsername(betUsername);
			      String title=rs.getString("title");
			      po.setTitle(title);
			      long betMilitary=rs.getLong("bet_military");//投注战绩
			      po.setBetMilitary(betMilitary);
			      String allMoney=rs.getString("all_money");
			      po.setAllMoney(allMoney);
			      String allProgress=rs.getString("allProgress");
			      StringBuilder _allProgress=new StringBuilder(allProgress);
			      _allProgress.append("%");
			      String floorPercentage=rs.getString("floor_percentage");
			     
			      if(StringUtils.isNotEmpty(floorPercentage)){
			    	  if(!"0".equals(floorPercentage)){
			    	    _allProgress.append("+");
			            _allProgress.append(floorPercentage);
			            _allProgress.append("%(保)");
			    	  }
			           po.setAllProgress(_allProgress.toString());
			      }
			      String isTop=rs.getString("isTop");
			      po.setIsTop(isTop);
			      
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
	/**足球单场 合买推荐方案(含置顶)  2010-04-24 14:25
	 * 参数：
	 *     betCategory   61:单场半全场  62:单场比分 63:单场让球胜平负  64:单场上下单双 65:单场总进球
	 */
	public List<BetOrderVO> loadDanchang(String betCategory)throws DaoException{
          Connection conn=null;
		  CallableStatement proc=null;
		  ResultSet rs=null;
	      String sql = "{ call BET_HALL.listDanchang(?,?) }";
		  try{
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      proc = conn.prepareCall(sql);
		      proc.setString(1, betCategory);
		      proc.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);//输出游标记录集
		      proc.execute();
		      rs = (OracleResultSet)proc.getObject(2);
		      BetOrderVO po=null;
		      List<BetOrderVO> resultList=new ArrayList<BetOrderVO>();
		      while(rs.next()){
		    	  po=new BetOrderVO();
		    	  String betCategoryZH=null; 
		    	  if("61".endsWith(betCategory)){
		    		  betCategoryZH="单场半全场";
		    	  }else if("62".equals(betCategory)){
		    		  betCategoryZH="单场比分";
		    	  }else if("63".equals(betCategory)){
		    		  betCategoryZH="单场让球胜平负";
		    	  }else if("64".equals(betCategory)){
		    		  betCategoryZH="单场上下单双";
		    	  }else if("65".equals(betCategory)){
		    		  betCategoryZH="单场总进球";
		    	  }
		    	  po.setBetId(rs.getLong("bet_id"));
			      po.setBetCategory(betCategoryZH);
			      String phaseNo=rs.getString("phase_no");
			      po.setPhaseNo(phaseNo);
			      String betUsername=rs.getString("bet_username");
			      po.setBetUsername(betUsername);
			      String title=rs.getString("title");
			      po.setTitle(title);
			      long betMilitary=rs.getLong("bet_military");//投注战绩
			      po.setBetMilitary(betMilitary);
			      String allMoney=rs.getString("all_money");
			      po.setAllMoney(allMoney);
			      String allProgress=rs.getString("allProgress");
			      StringBuilder _allProgress=new StringBuilder(allProgress);
			      _allProgress.append("%");
			      String floorPercentage=rs.getString("floor_percentage");
			      if(StringUtils.isNotEmpty(floorPercentage)){
			    	  if(!"0".equals(floorPercentage)){
			    	    _allProgress.append("+");
			            _allProgress.append(floorPercentage);
			            _allProgress.append("%(保)");
			    	  }
			           po.setAllProgress(_allProgress.toString());
			      }
			      if(rs.getLong("DIVIDE_COPYS") != rs.getLong("ALREADY_BUY_COPYS"))
			    	  po.setStatus("未满");
			      String isTop=rs.getString("isTop");
			      po.setIsTop(isTop);
			      
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
	
   /**
    * 胜负彩14场 购彩大厅  2010-04-22 17:08
    */
   public List<BetOrderVO> loadWinLose14()throws DaoException{
	      Connection conn=null;
		  CallableStatement proc=null;
		  ResultSet rs=null;
	      String sql = "{ call BET_HALL.listWinLose14(?) }";
		  try{
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      proc = conn.prepareCall(sql);
		      proc.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);//输出游标记录集
		      proc.execute();
		      rs = (OracleResultSet)proc.getObject(1);
		      BetOrderVO po=null;
		      List<BetOrderVO> resultList=new ArrayList<BetOrderVO>();
		      while(rs.next()){
		    	  po=new BetOrderVO();
		    	  Long betId=rs.getLong("bet_id");
		    	  po.setBetId(betId);
			      po.setBetCategory("胜负14场");
			      String phaseNo=rs.getString("phase_no");
			      po.setPhaseNo(phaseNo);
			      String betUsername=rs.getString("bet_username");
			      po.setBetUsername(betUsername);
			      String title=rs.getString("title");
			      po.setTitle(title);
			      long betMilitary=rs.getLong("bet_military");//投注战绩
			      po.setBetMilitary(betMilitary);
			      String allMoney=rs.getString("all_money");
			      po.setAllMoney(allMoney);
			      String allProgress=rs.getString("allProgress");
			      StringBuilder _allProgress=new StringBuilder(allProgress);
			      _allProgress.append("%");
			      String floorPercentage=rs.getString("floor_percentage");
			      if(StringUtils.isNotEmpty(floorPercentage)){
			    	  if(!"0".equals(floorPercentage)){
			    	    _allProgress.append("+");
			            _allProgress.append(floorPercentage);
			            _allProgress.append("%(保)");
			    	  }
			           po.setAllProgress(_allProgress.toString());
			      }
			      String isTop=rs.getString("isTop");
			      po.setIsTop(isTop);
			      
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
   /**
    * 任选9场 购彩大厅  2010-04-25 13:40
    */
   public List<BetOrderVO> loadRen9()throws DaoException{
	      Connection conn=null;
		  CallableStatement proc=null;
		  ResultSet rs=null;
	      String sql = "{ call BET_HALL.listRen9(?) }";
		  try{
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      proc = conn.prepareCall(sql);
		      proc.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);//输出游标记录集
		      proc.execute();
		      rs = (OracleResultSet)proc.getObject(1);
		      BetOrderVO po=null;
		      List<BetOrderVO> resultList=new ArrayList<BetOrderVO>();
		      while(rs.next()){
		    	  po=new BetOrderVO();
		    	  Long betId=rs.getLong("bet_id");
		    	  po.setBetId(betId);
			      po.setBetCategory("任选9场");
			      String phaseNo=rs.getString("phase_no");
			      po.setPhaseNo(phaseNo);
			      String betUsername=rs.getString("bet_username");
			      po.setBetUsername(betUsername);
			      String title=rs.getString("title");
			      po.setTitle(title);
			      long betMilitary=rs.getLong("bet_military");//投注战绩
			      po.setBetMilitary(betMilitary);
			      String allMoney=rs.getString("all_money");
			      po.setAllMoney(allMoney);
			      String allProgress=rs.getString("allProgress");
			      StringBuilder _allProgress=new StringBuilder(allProgress);
			      _allProgress.append("%");
			      String floorPercentage=rs.getString("floor_percentage");
			      if(StringUtils.isNotEmpty(floorPercentage)){
			    	  if(!"0".equals(floorPercentage)){
			    	     _allProgress.append("+");
			             _allProgress.append(floorPercentage);
			             _allProgress.append("%(保)");
			    	  }
			    	  po.setAllProgress(_allProgress.toString());
			      }
			      String isTop=rs.getString("isTop");
			      po.setIsTop(isTop);
			      
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
}
