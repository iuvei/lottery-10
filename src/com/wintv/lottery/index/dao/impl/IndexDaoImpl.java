package com.wintv.lottery.index.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.lottery.index.vo.BetOrderVO;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.index.dao.IndexDao;

import com.wintv.lottery.index.vo.ExpertVO;
import com.wintv.lottery.personal.vo.BonusVO;
/**
 * 网站首页 2010-04-21 10:33
 *
 */
@SuppressWarnings("unchecked")
@Repository("indexDao")
public class IndexDaoImpl extends BaseHibernate  implements IndexDao{
	//网站首页:左边部分 2010-04-23 10:33

	public Map leftData()throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        Map resultMap=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call LOTTERY_INDEX.leftData(?,?,?,?,?,?,?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(4,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(5,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(6,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(7,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(8,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(9,oracle.jdbc.OracleTypes.CURSOR);
	      proc.execute();
	      rs = (ResultSet)proc.getObject(1);
          List<BetOrderVO> beingSoldList=new ArrayList<BetOrderVO>();//足彩开售中
          BetOrderVO po=null;
          resultMap=new HashMap();
          while(rs.next()){
        	     po=new BetOrderVO();
        	     String betCategoryZH=rs.getString("cat");//彩种中文 
        	     po.setBetCategoryZH(betCategoryZH);
        	     String phaseNo=rs.getString("phase_no");
        	     po.setPhaseNo(phaseNo);
        	     String endTime=rs.getString("deadline");
        	     po.setEndTime(endTime);
        	     String betCategory=rs.getString("category");
        	     po.setBetCategory(betCategory);
        	     long phaseId=rs.getLong("phaseId");
        	     po.setPhaseId(phaseId);
        	     beingSoldList.add(po);
		  }
          resultMap.put("beingSoldList", beingSoldList);//足彩开售中
          String kjResult=proc.getString(2);
          resultMap.put("kjResult", kjResult);//最新开奖结果
          ////
          rs = (ResultSet)proc.getObject(3);
          List<BetOrderVO> latestZjList=new ArrayList<BetOrderVO>();//本站最新中奖
          while(rs.next()){
        	     po=new BetOrderVO();
        	     String betCategoryZH=rs.getString("lottery_name");//彩种 
        	     po.setBetCategoryZH(betCategoryZH);
        	     String phaseNo=rs.getString("phase");
        	     po.setPhaseNo(phaseNo);
        	     String betUsername=rs.getString("username");
        	     po.setBetUsername(betUsername);
        	     String bonus=rs.getString("money");
        	     po.setBonus(bonus);
        	     latestZjList.add(po);
		  }
          resultMap.put("latestZjList", latestZjList);//本站最新中奖
          //
          rs = (ResultSet)proc.getObject(4);
          List<BonusVO> zjTop14List=new ArrayList<BonusVO>();//总排行榜月(胜负彩14场)
          BonusVO vo=null;
          while(rs.next()){
        	     vo=new BonusVO();
        	     String money=rs.getString(1);
        	     vo.setMoney(money);
        	     String username=rs.getString(2);
        	     vo.setUsername(username);
        	     long userId=rs.getLong(3);
        	     vo.setUserId(userId);
        	     long allowDZ=rs.getLong(4);
        	     if(allowDZ>0){
        	    	 vo.setAllowDZ("1");
        	     }
        	     zjTop14List.add(vo);
		  }
          resultMap.put("zjTop14List", zjTop14List);
          //
          rs = (ResultSet)proc.getObject(5);
          List<BonusVO> zjTop9List=new ArrayList<BonusVO>();//总排行榜月(任9场)
          while(rs.next()){
        	  vo=new BonusVO();
     	     String money=rs.getString(1);
     	     vo.setMoney(money);
     	     String username=rs.getString(2);
     	     vo.setUsername(username);
     	    long userId=rs.getLong(3);
   	        vo.setUserId(userId);
   	     long allowDZ=rs.getLong(4);
	     if(allowDZ>0){
	    	 vo.setAllowDZ("1");
	     }
     	    zjTop9List.add(vo);
		  }
          resultMap.put("zjTop9List", zjTop9List);//总排行榜月(任9场)
          //
          rs = (ResultSet)proc.getObject(6);
          List<BonusVO> zjTop6List=new ArrayList<BonusVO>();//总排行榜月(足球单场)
          while(rs.next()){
        	  vo=new BonusVO();
      	     String money=rs.getString(1);
      	     vo.setMoney(money);
      	     String username=rs.getString(2);
      	     vo.setUsername(username);
      	   long userId=rs.getLong(3);
  	     vo.setUserId(userId);
  	   long allowDZ=rs.getLong(4);
	     if(allowDZ>0){
	    	 vo.setAllowDZ("1");
	     }
      	    zjTop6List.add(vo);
		  }
          resultMap.put("zjTop6List", zjTop6List);//总排行榜月(足球单场)
          ////////////////////////////////////////////////////////////////////
          rs = (ResultSet)proc.getObject(7);
          List<BonusVO>  coopTop14List=new ArrayList<BonusVO>();//合买排行榜(胜负彩14场)
          
          while(rs.next()){
        	     vo=new BonusVO();
        	     String money=rs.getString(1);
        	     vo.setMoney(money);
        	     String username=rs.getString(2);
        	     vo.setUsername(username);
        	     long userId=rs.getLong(3);
        	     vo.setUserId(userId);
        	     long allowDZ=rs.getLong(4);
        	     if(allowDZ>0){
        	    	 vo.setAllowDZ("1");
        	     }
        	     coopTop14List.add(vo);
		  }
          resultMap.put("coopTop14List", coopTop14List);
          //
          rs = (ResultSet)proc.getObject(8);
          List<BonusVO> coopTop9List=new ArrayList<BonusVO>();//合买排行榜(任9场)
          while(rs.next()){
        	  vo=new BonusVO();
     	     String money=rs.getString(1);
     	     vo.setMoney(money);
     	     String username=rs.getString(2);
     	     vo.setUsername(username);
     	    long userId=rs.getLong(3);
   	     vo.setUserId(userId);
   	  long allowDZ=rs.getLong(4);
	     if(allowDZ>0){
	    	 vo.setAllowDZ("1");
	     }
     	    coopTop9List.add(vo);
		  }
          resultMap.put("coopTop9List", coopTop9List);//合买排行榜(任9场)
          //
          rs = (ResultSet)proc.getObject(9);
          List<BonusVO> coopTop6List=new ArrayList<BonusVO>();//合买排行榜(足球单场)
          while(rs.next()){
        	  vo=new BonusVO();
      	     String money=rs.getString(1);
      	     vo.setMoney(money);
      	     String username=rs.getString(2);
      	     vo.setUsername(username);
      	   long userId=rs.getLong(3);
  	     vo.setUserId(userId);
  	   long allowDZ=rs.getLong(4);
	     if(allowDZ>0){
	    	 vo.setAllowDZ("1");
	     }
      	   coopTop6List.add(vo);
		  }
          resultMap.put("coopTop6List", coopTop6List);//合买排行榜(足球单场)
	     return resultMap;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	
	}
	/**
	 * 热门关键词 2010-04-23 09:26
	 *返回:
	 * List<String>
	 */
	public List<String>  findHotKeys()throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        List<String> resultList=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{call LOTTERY_INDEX.find_hot_keys(?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
	      proc.execute();
	      rs = (ResultSet)proc.getObject(1);
          resultList=new ArrayList<String>();
          while(rs.next()){
        	  String username=rs.getString("username");
        	  resultList.add(username);
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
	 * 精选方案  2010-04-21 09:17
	 *
	 */
	public List<BetOrderVO> findJingXuanPlanList(Map params)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        String betCategory=(String)params.get("betCategory");
        List<BetOrderVO> resultList=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call LOTTERY_INDEX.findJingXuanPlanList(?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setString(1, betCategory);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
	      proc.execute();
	    
          rs = (ResultSet)proc.getObject(2);
          resultList=new ArrayList<BetOrderVO>();
          BetOrderVO po=null;
          while(rs.next()){
        	     po=new BetOrderVO();
				 Long betId=rs.getLong("bet_id");
				 po.setBetId(betId);
			     long surplusCopys=rs.getLong("surplusCopys");
				 po.setSurplusCopys(surplusCopys);
				 String betUsername=rs.getString("bet_username");
				 po.setBetUsername(betUsername);
				 long betMilitary=rs.getLong("bet_military");
				 po.setBetMilitary(betMilitary);
				 String allMoney=rs.getString("all_money");
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
	//网站首页:名家足球推荐 2010-04-21 10:32
	public List<ExpertVO> findFamousExpertList()throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        List<ExpertVO> resultList=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call LOTTERY_INDEX.findFamousExpertList(?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
	      proc.execute();
	      rs = (ResultSet)proc.getObject(1);
          resultList=new ArrayList<ExpertVO>();
          ExpertVO po=null;
          while(rs.next()){
        	     po=new ExpertVO();
				 String expertName=rs.getString("expert_name");
				 po.setExpertName(expertName);
				 String introduction=rs.getString("introduction");
				 po.setIntroduction(introduction);
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
