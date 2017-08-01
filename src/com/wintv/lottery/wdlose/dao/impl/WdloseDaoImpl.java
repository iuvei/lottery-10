package com.wintv.lottery.wdlose.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.lottery.attention.vo.AttentionXinshuiVO;
import com.wintv.lottery.bet.utils.CommUtil;
import com.wintv.lottery.index.vo.BetOrderVO;
import com.wintv.lottery.wdlose.dao.WdloseDao;
import com.wintv.lottery.wdlose.vo.KjVO;

@Repository("wdloseDao")
public class WdloseDaoImpl  extends BaseHibernate implements WdloseDao{
	/**对应bet/wdlose.jsp的 【合买推荐】   2010-04-27 14:10
	 *  List<BetOrderVO>:list合买方案列表
	 *  String:deadline:截止时间
	 **/
	public Map  findJingxuanOrder(Long phaseId,String betcategory)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        Map resultMap=new HashMap();
        try{
          
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call wdlose.findJingXuanPlanList(?,?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setLong(1,phaseId);
	      proc.setString(2,betcategory);
	      proc.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
          proc.execute();
          List<BetOrderVO> resultList=new ArrayList<BetOrderVO>();
          rs=(ResultSet)proc.getObject(3);
          BetOrderVO po=null;
          while(rs.next()){
        	  po=new BetOrderVO();
        	  Long betId=rs.getLong("bet_id");
        	  po.setBetId(betId);
        	  String betUsername=rs.getString("bet_username");
        	  po.setBetUsername(betUsername);
        	  Long betMilitary=rs.getLong("bet_military");
        	  po.setBetMilitary(betMilitary);
        	  String allMoney=rs.getString("all_money");
        	  po.setAllMoney(allMoney);
        	  String planCode=rs.getString("plan_code");
        	  po.setPlanCode(planCode);
        	  String singleMoney=rs.getString("single_money");
        	  po.setSingleMoney(singleMoney);
        	  String floorPercentage=rs.getString("floorPercentage");
        	  po.setFloorPercentage(floorPercentage);
        	  String progress=rs.getString("progress");
        	  po.setProgress(progress);
        	resultList.add(po);
          }
          String deadline=proc.getString(4);
          resultMap.put("list", resultList);
          resultMap.put("deadline", deadline);
         return resultMap;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	
	}
	/**2010-05-06 14:50
	 * 参数：
	 * phaseNo:期次号
	 * 返回结果:
	 * {ren9Result,winlose14Result,开奖时间,截止时间}
	 * 
	 */
	public String[] bulletin14(String phaseNo)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call wdlose.bulletin14(?,?,?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.setString(3,phaseNo);
	      proc.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.execute();
	      String ren9Result=proc.getString(1);
          String winlose14Result=proc.getString(2);
          String kjTime=proc.getString(4);
          String deadline=proc.getString(5);
         return new String[]{ren9Result,winlose14Result,kjTime,deadline};
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	
	}
	public KjVO bulletin6(String phaseNo)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call wdlose.bulletin6(?,?,?,?,?,?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(3,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMBER);
	      proc.setString(5,phaseNo);
	      proc.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(8,oracle.jdbc.OracleTypes.NUMBER);
	      proc.execute();
	      KjVO po=new KjVO();
          String kjNo=proc.getString(1);
          po.setKjNo(kjNo);
          String bonusPool=proc.getString(2);
          po.setBonusPool(bonusPool);
          String money1=proc.getString(3);
          po.setMoney1(money1);
          String sales=proc.getString(4);
          po.setSales(sales);
          String kjTime=proc.getString(6);
          po.setKjTime(kjTime);
          String deadline=proc.getString(7);
          po.setDeadline(deadline);
          long betNum1=proc.getLong(8);
          po.setBetNum1(betNum1);
         return po;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(proc,conn);
	    }
	    return null;
	
	
	
	}
	/**开奖期次列表 2010-04-28 17:58
	 *参数：
	 *category:
	 *  1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     
	 *  61:单场半全场  62:单场比分 63:单场让球胜平负  64:单场上下单双 65:单场总进球
	 **/
	public List<String> findPhaseList(String category)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        List<String> list=new ArrayList<String>(); 
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call wdlose.findPhaseList(?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setString(1, category);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
	      proc.execute();
	      rs=(ResultSet)proc.getObject(2);
	      while(rs.next()){
	    	  String phaseNo=rs.getString("phase");
	    	  list.add(phaseNo);
	      }
         return list;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	
	}
	/***4场进球  2010-04-29 13:23*/
	public KjVO bulletin4(String phaseNo)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call wdlose.bulletin4(?,?,?,?,?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(3,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMBER);
	      proc.setString(5,phaseNo);
	      proc.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.execute();
	      KjVO po=new KjVO();
          String kjNo=proc.getString(1);
          po.setKjNo(kjNo);
          String bonusPool=proc.getString(2);
          po.setBonusPool(bonusPool);
          String money1=proc.getString(3);
          po.setMoney1(money1);
          String sales=proc.getString(4);
          String kjTime=proc.getString(6);
          po.setKjTime(kjTime);
          String deadline=proc.getString(7);
          po.setDeadline(deadline);
          po.setSales(sales);
         return po;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(proc,conn);
	    }
	    return null;
	
	
	
	}
	/**
	 * 
	 *参数：
	 * phaseNo:其次号
	 * betCategory：'6':胜负彩期次    '9':进球彩期次 '8':半全场期次 '1':北京单场期次
	 */
	public List<String>  findAgainst(String phaseNo,String  betCategory)throws DaoException{
		StringBuilder sql=new StringBuilder("select c.host_name from t_lottery_phase a,t_lottery_against b,t_against  c where a.id=b.phase_id  and b.against_id=c.against_id");
		sql.append(" and  a.phase_no=? and a.category=?  order by b.changci");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<String>  resultList=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1, phaseNo);
			pstmt.setString(2,betCategory);
			rs=pstmt.executeQuery();
			resultList=new ArrayList<String>();
			while(rs.next()){
				String hostName=rs.getString("host_name");
				resultList.add(hostName);
		   }
		 return resultList;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return null;
	}
	/**
	 * 4场进球彩主队与客队 2010-05-07 13:20
	 *参数:
	 * phaseNo:其次号
	 * 
	 */
	public List<String>  find4Against(String phaseNo)throws DaoException{
		StringBuilder sql=new StringBuilder("select c.host_name,c.guest_name from t_lottery_phase a,t_lottery_against b,t_against  c where a.id=b.phase_id  and b.against_id=c.against_id");
		sql.append(" and  a.phase_no=? and a.category='9'  order by b.changci");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<String>  resultList=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1, phaseNo);
			
			rs=pstmt.executeQuery();
			resultList=new ArrayList<String>();
			while(rs.next()){
				String hostName=rs.getString("host_name");
				resultList.add(hostName);
				String guestName=rs.getString("guest_name");
				resultList.add(guestName);
		   }
		 return resultList;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return null;
	}
}
