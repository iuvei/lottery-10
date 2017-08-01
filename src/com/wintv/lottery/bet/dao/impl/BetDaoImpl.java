package com.wintv.lottery.bet.dao.impl;

import java.math.BigDecimal;
 
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.Iterator;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.OracleResultSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
 
import com.wintv.framework.common.OracleSqlUtil;
import com.wintv.framework.common.hibernate.BaseHibernate;
 
import com.wintv.framework.exception.BadInputException;
import com.wintv.framework.exception.DaoException;

import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.BetOrderChoice;
import com.wintv.framework.pojo.LotteryPhase;
import com.wintv.framework.utils.LotteryStringUtils;
import com.wintv.lottery.admin.bet.vo.BetAdminOrderVO;
import com.wintv.lottery.bet.dao.BetDao;
import com.wintv.lottery.bet.vo.BetOrderVO;
import com.wintv.lottery.bet.vo.BetTop10Vo;
import com.wintv.lottery.bet.vo.CoopOrder;
import com.wintv.lottery.bet.vo.PhaseNoVO;
import com.wintv.lottery.bet.vo.SuperSponsorVO;
import com.wintv.lottery.bet.vo.BetTop10Vo;
import com.wintv.lottery.index.vo.InGoalBetScaleVo;

/**
 * 投注模块DAO实现类
 *
 */
@SuppressWarnings("unchecked")
@Repository("betDao")
public class BetDaoImpl extends BaseHibernate implements BetDao {
	 
	 public String advSearch4(Map params)throws DaoException{
	        Connection conn=null;
	        CallableStatement proc=null;
	        ResultSet rs=null;
	        String betCategory=(String)params.get("betCategory");
	        Long phaseId=(Long)params.get("phaseId");
	        String advSearch=(String)params.get("advSearch");
	        try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      String sql = "{ call BET_ADV_SEARCH.advSearch4(?,?,?,?)}";
		      proc   =(CallableStatement)conn.prepareCall(sql);    
		      proc.setString(1, betCategory);
		      proc.setLong(2, phaseId);
		      proc.setString(3,advSearch);
		      proc.registerOutParameter(4, OracleTypes.VARCHAR);    
	          proc.execute();
		      String result=proc.getString(4);
		      if(StringUtils.isNotEmpty(result)){
		    	 result=result.trim();
		    	  if(result.startsWith(",")){
		    		  result=result.substring(1,result.length());
		  		}
		    	
		      }
		    
		      if(StringUtils.isNotEmpty(result)){
		    	  result=result.trim();
		      }
		     
		     return result;
		  
	        }catch(Exception e){
		    	e.printStackTrace();
		    }finally{
		    	closeAll(rs,proc,conn);
		    }
		    return null;
		
		
		}
	 public String advSearch6(Map params)throws DaoException{
	        Connection conn=null;
	        CallableStatement proc=null;
	        ResultSet rs=null;
	        String betCategory=(String)params.get("betCategory");
	        Long phaseId=(Long)params.get("phaseId");
	        String advSearch=(String)params.get("advSearch");
	        try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      String sql = "{ call BET_ADV_SEARCH.advSearch6(?,?,?,?)}";
		      proc   =(CallableStatement)conn.prepareCall(sql);    
		      proc.setString(1, betCategory);
		      proc.setLong(2, phaseId);
		      proc.setString(3,advSearch);
		      proc.registerOutParameter(4, OracleTypes.VARCHAR);    
	          proc.execute();
		      String result=proc.getString(4);
		      if(StringUtils.isNotEmpty(result)){
		    	 result=result.trim();
		    	  if(result.startsWith(",")){
		    		  result=result.substring(1,result.length());
		  		}
		    	
		      }
		    
		      if(StringUtils.isNotEmpty(result)){
		    	  result=result.trim();
		      }
		     
		     return result;
		  
	        }catch(Exception e){
		    	e.printStackTrace();
		    }finally{
		    	closeAll(rs,proc,conn);
		    }
		    return null;
		
		
		}
	public BetOrder loadBetOrderById(Long betId)throws DaoException{
		return (BetOrder)getSession().get(BetOrder.class, betId);
	}
	public String load9Choice(long betId)throws DaoException{
		StringBuilder sql=new StringBuilder("select o.bet_plan,o.changci  from  t_bet_order_choice o where  o.bet_order_id=?");
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	    try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, betId);
			rs=pstmt.executeQuery();
			
			String[] result=new String[14];
			for(int i=0;i<=13;i++){
				result[i]="*";
			}
			boolean isEmpty=false;
			System.out.println("王金阶 betId="+betId);
			while(rs.next()){
				String betPlan=rs.getString("bet_plan");
				int changci=rs.getInt("changci");
				System.out.println("王金阶="+changci);
				result[changci-1]=betPlan;
				isEmpty=true;
		   }
		   if(!isEmpty){//可能是复式稍后选号 或者是单式  2010-04-13 14:28
				return null;
			}
		 
		  StringBuilder resultPlan=new StringBuilder();
		  for(int i=0;i<=13;i++){
			  String e=result[i];
			  resultPlan.append(e);
			  if(i<13){
			    resultPlan.append(",");
			  }
		  }
		  
		  
		  return resultPlan.toString();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		return null;
	}
	/**胜负彩14场高级搜索  2010-04-07 11:07**/
    public String advSearch14(Map params)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        String betCategory=(String)params.get("betCategory");
        Long phaseId=(Long)params.get("phaseId");
        String advSearch=(String)params.get("advSearch");
     
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{ call COOP_BET.ADVSEARCH14(?,?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setString(1, betCategory);
	      proc.setLong(2, phaseId);
	      proc.setString(3,advSearch);
	      proc.registerOutParameter(4, OracleTypes.VARCHAR);    
          proc.execute();
	      String result=proc.getString(4);
	      if(StringUtils.isNotEmpty(result)){
	    	 
	    	  result=result.trim();
	    	  if(result.startsWith(",")){
	    		  result=result.substring(1,result.length());
	  		}
	    	
	      }
	    
	      if(StringUtils.isNotEmpty(result)){
	    	  result=result.trim();
	      }
	     
	     return result;
	  
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	}
	/**胜负14场**/
	public String[] statPlan(String betCategory,Long phaseId)throws DaoException{
	
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = null;
	      if("1".equals(betCategory)){
	         sql = "{ call COOP_BET.statPlan(?,?,?)}";
	      }else if("2".equals(betCategory)){
	    	 sql = "{ call COOP_BET.statPlanRen(?,?,?)}";//任9
	      }else if("5".equals(betCategory)){
	    	 sql = "{ call COOP_BET.statPlan6(?,?,?)}";//6场半全场
	      }else if("3".equals(betCategory)){
	    	 sql = "{ call COOP_BET.statPlan4(?,?,?)}";//4场进球
	      }
	     
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setString(1, betCategory);
	      proc.setLong(2, phaseId);
	      proc.registerOutParameter(3, OracleTypes.VARCHAR);    
          proc.execute();
	      String result=proc.getString(3);
	      //log.info("WJJ="+result);
	      if(result.startsWith(";")){
	    	  result=result.substring(1,result.length()-1);
	      }
	      if(result.endsWith(";")){
	    	  result=result.substring(0,result.length()-2);
	      }
	     
	      String[] resultList=result.split(";");
	      for(int i=0;i<resultList.length;i++){
	    	  
	      }
	     return resultList;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	
	
	}
	/***根据期次ID查询复式截止时间  2010-04-01 1:00*/
	public String  getMulDeadline(Long phaseId)throws DaoException{
		String sql="select  to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi') MUL_DEADLINE from T_LOTTERY_PHASE t where t.ID=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	    try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1,phaseId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				return rs.getString("MUL_DEADLINE");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		return null;
	}
	public String loadBetOrderIds(String sql)throws DaoException{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder resultList=new StringBuilder();
		Set<Long> set=new HashSet<Long>();
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
		
			rs=pstmt.executeQuery();
			while(rs.next()){
				Long id=rs.getLong(1);
				set.add(id);
				//resultList.append(id);
				//resultList.append(",");
			}
			if(set.size()>0){
				for(Iterator<Long> e=set.iterator();e.hasNext();){
					Long id=e.next();
					resultList.append(id);
					resultList.append(",");
				}
				
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		return resultList.toString();
	}
	public BetOrder loadBetOrder(Long id)throws DaoException{
		return (BetOrder)this.getSession().load(BetOrder.class,id);
	}
	public BetOrderChoice loadBetOrderInfo(long betOrderId)throws DaoException{
		StringBuilder sql=new StringBuilder("select a.changci  from t_lottery_against a,t_lottery_phase b ");
        sql.append(" where a.phase_id=b.id and b.phase_no=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		BetOrderChoice po=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, betOrderId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				po=new BetOrderChoice();
				String betPlan=rs.getString("bet_plan");
				po.setBetPlan(betPlan);
				String hostName=rs.getString("host_name");
				po.setHostName(hostName);
				String guestName=rs.getString("guest_name");
				po.setGuestName(guestName);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		return po;
	}
	/**投注记录  单场:投注内容 2010-03-26 15:32**/
    public List<BetOrderChoice> loadMyBetOrderChoiceList(Long betOrderId,Long phaseId)throws DaoException{
		StringBuilder sql=new StringBuilder("select c.changci,b.host_name,b.guest_name,b.concede,a.bet_plan,a.dan_code,b.score,a.dan_code  from t_bet_order_choice a,t_against b ");
		sql.append(",t_lottery_against c where a.against_id=b.against_id and c.against_id=b.against_id   and a.bet_order_id=?  and c.phase_id=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<BetOrderChoice> resultList=null;
		try{
			resultList=new ArrayList<BetOrderChoice>();
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, betOrderId);
			pstmt.setLong(2, phaseId);
			rs=pstmt.executeQuery();
			BetOrderChoice po=null;
			while(rs.next()){
				po=new BetOrderChoice();
				Long changci=rs.getLong("changci");
				po.setChangci(changci);
				String betPlan=rs.getString("bet_plan");
				po.setBetPlan(betPlan);
				String hostName=rs.getString("host_name");
				po.setHostName(hostName);
				String guestName=rs.getString("guest_name");
				po.setGuestName(guestName);
				String concede=rs.getString("concede");
				po.setConcede(concede);
				String score=rs.getString("score");
				po.setScore(score);
				String danCode=rs.getString("dan_code");
				po.setDanCode(danCode);
			   resultList.add(po);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	  return resultList;
	   
	}

	
	/**金牌发起人订单,用于自动跟单
	 * 返回:
	 *  size:记录数
	 *  resultList;返回的数据列表
	 * @throws BadInputException 
	 */
	public List<SuperSponsorVO>  findSuperSponsorList(Map params)throws DaoException, BadInputException{
		Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		String category=(String)params.get("category");
		if(LotteryStringUtils.isBad(category)){
			throw new BadInputException("禁止攻击本站!");
		}
		
		List<SuperSponsorVO> resultList=null;
		Map resultMap=null;
	    try{
	      StringBuilder sqlQuery=new StringBuilder("select  t.ID,t.USERNAME,decode(t.TYPE,'1',单式合买  '2',复式合买) TYPE,t.ZH_DESC,t.USERID from T_SUPER_SPONSOR t  where  t.CATEGORY='"+category+"'");
	      String sql = "{ call bet.sp_Page(?,?,?,?,?,?)}";
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      proc = conn.prepareCall(sql);
	      proc.setInt(1, pageSize);        //每页数量
	      proc.setInt(2, startRow);          //页码 
	      proc.setString(3, sqlQuery.toString());//取数据的sql
	      proc.setString(4, "select  count(1) cnt from T_SUPER_SPONSOR t  where  t.CATEGORY='"+category+"'");//取数据个数的sql
	      proc.registerOutParameter(5, OracleTypes.INTEGER);      //输出数据行数
	      proc.registerOutParameter(6, OracleTypes.CURSOR);      //输出游标记录集
	      proc.execute();
	      int size = proc.getInt(5);
	      rs = (ResultSet)proc.getObject(6);
          resultList=new ArrayList<SuperSponsorVO>();
          SuperSponsorVO vo=null;
	      while (rs.next()) {
	         vo=new SuperSponsorVO();
	         Long id=rs.getLong("ID");
	         vo.setId(id);
	         String userName=rs.getString("USERNAME");
	         vo.setUsername(userName);
	         String type=rs.getString("TYPE");
	         vo.setType(type);
	         String  zhDesc=rs.getString("ZH_DESC");
	         vo.setZhDesc(zhDesc);
	         Long  userId=rs.getLong("USERID");
	         vo.setUserid(userId);
	         resultList.add(vo);
	      }
	      resultMap=new HashMap();
	      resultMap.put("size", size);
	      resultMap.put("resultList", resultList);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return resultList;
	}
	/**我的投注记录 2010-03-04 13:04  文档 41页
	 * 返回:
	 *  size:记录数
	 *  resultList;返回的数据列表
	 */
	public Map  findMyBetOrderList(Map params)throws DaoException{

		Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		String betCategory=(String)params.get("betCategory"); 
		String zjStatus=(String)params.get("zjStatus");
		String betTimeFrom=(String)params.get("betTimeFrom");//起始按投注时间
		String betTimeTo=(String)params.get("betTimeTo");//结束按投注时间
		String orderStatus=(String)params.get("orderStatus");//订单状态
		Long betUserId=(Long)params.get("betUserId");
		String flg=(String)params.get("flg");
		
		List<BetAdminOrderVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("select  a.bet_id,a.order_no,decode(sponsor_type,'1',a.plan_code,'2',a.order_no,a.order_no) plan_code,to_char(a.bet_time,'yy-mm-dd hh24:mi')bet_time,a.phase_no,a.sponsor_type,a.all_money,");
	      queryList.append(" decode(a.order_status,'1','未支付','2','待出票','3','已出票','4','已取消','5','已过期') order_status,") ;
	      //投注彩种    1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     61:单场半全场  62:单场比分 63:单场胜平负  64:单场上下单双 65:单场总进球
	      queryList.append(" decode(a.bet_categoty,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_categoty,");
	      queryList.append("a.type,a.DIVIDE_COPYS,");
	      //queryList.append(" case when a.type='2' or a.type='4' then ");
	      queryList.append(" decode(a.zj_status, '1','未开奖','2','已返奖','3','未中奖','未开奖') zj_status,a.bet_username,a.BONUS,a.SUBSCRIBE_MONEY,a.ALREADY_BUY_COPYS from T_BET_ORDER  a   where 1=1 ");
	      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from T_BET_ORDER  b  where 1=1 ");
	      if(StringUtils.isNotEmpty(betCategory)){
	    	  queryList.append("  and a.BET_CATEGOTY='"+betCategory+"'");
	    	  querySize.append("  and b.BET_CATEGOTY='"+betCategory+"'");
	      }
	      if(StringUtils.isNotEmpty(betTimeFrom)){
	    	  queryList.append("  and to_char(a.BET_TIME,'yyyy-mm-dd') >='"+betTimeFrom+"'");
	    	  querySize.append("  and to_char(b.BET_TIME,'yyyy-mm-dd') >='"+betTimeFrom+"'");
	      }
	      if(StringUtils.isNotEmpty(betTimeTo)){
	    	  queryList.append("  and to_char(a.BET_TIME,'yyyy-mm-dd') <='"+betTimeTo+"'");
	    	  querySize.append("  and to_char(b.BET_TIME,'yyyy-mm-dd') <='"+betTimeTo+"'");
	      }
	      if(StringUtils.isNotEmpty(orderStatus)){
	    	  queryList.append("  and a.ORDER_STATUS='"+orderStatus+"'  ");
	    	  querySize.append("  and b.ORDER_STATUS='"+orderStatus+"'  ");
	      }
	      if(StringUtils.isNotEmpty(zjStatus)){
	    	  queryList.append("  and a.ZJ_STATUS='"+zjStatus+"'  ");
	    	  querySize.append("  and b.ZJ_STATUS='"+zjStatus+"'  ");
	      }
	      
	      queryList.append("  and a.BET_USERID="+betUserId);
    	  querySize.append("  and b.BET_USERID="+betUserId);
	      if("2".equals(flg)){//我的代购
	    	  queryList.append("  and (a.TYPE='1'  or a.TYPE='3')  ");
	    	  querySize.append("  and (b.TYPE='1'  or b.TYPE='3')  ");
	      }else if("3".equals(flg)){//我参与的合买
	    	  queryList.append("  and a.SPONSOR_BET_ID>0  ");
	    	  querySize.append("  and b.SPONSOR_BET_ID>0  ");
	      }else if("4".equals(flg)){//我发起的合买
	    	  queryList.append("  and a.SPONSOR_TYPE='1'  ");
	    	  querySize.append("  and b.SPONSOR_TYPE='1'  ");
	      }
    	 
	      String sql = "{ call bet.sp_Page(?,?,?,?,?,?)}";
	      proc = conn.prepareCall(sql);
	      proc.setInt(1, pageSize);//每页数量
	     
	      proc.setInt(2, startRow);//页码 
	      proc.setString(3, queryList.toString());//取数据的sql
	      proc.setString(4,querySize.toString());//取数据个数的sql
	      proc.registerOutParameter(5, OracleTypes.INTEGER);//输出数据行数
	      proc.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR);//输出游标记录集
	      
	      
	      
	      proc.execute();
	      int size = proc.getInt(5);
	     
	      rs = (OracleResultSet)proc.getObject(6);
	       
          resultList=new ArrayList<BetAdminOrderVO>();
          BetAdminOrderVO vo=null;
	      while (rs.next()) {
	    	
	         vo=new BetAdminOrderVO();
	         Long betId=rs.getLong("bet_id");
	         vo.setBetId(betId);
	         String phaseNo=rs.getString("phase_no");
	         vo.setPhaseNo(phaseNo);
	         String orderNo=rs.getString("order_no");
	         vo.setOrderNo(orderNo);
	         String betTime=rs.getString("bet_time");
	         vo.setBetTime(betTime);
	         betCategory=rs.getString("bet_categoty");
	         vo.setBetCategory(betCategory);
	         
	         
	         String sponsorType=rs.getString("sponsor_type");
	         vo.setSponsorType(sponsorType);
	        
	         orderStatus=rs.getString("order_status");
	         vo.setOrderStatus(orderStatus);
	         
	         zjStatus=rs.getString("zj_status");
	         vo.setZjStatus(zjStatus);
	         String planCode=rs.getString("plan_code");
	         if(planCode==null){
	        	 planCode="";
	         }
	         vo.setPlanCode(planCode);
	         String type=rs.getString("type");
	         if(StringUtils.isNotEmpty(type)){
	           vo.setType(type.trim());
	         }
	         String betUsername=rs.getString("bet_username");
	         vo.setBetUsername(betUsername);
	         String bonus=rs.getString("BONUS");
	         if(StringUtils.isEmpty(bonus)){
	        	 bonus="0.00";
	         }
	         vo.setBonus(bonus);
	         String subscribeMoney=rs.getString("SUBSCRIBE_MONEY");
	         vo.setSubscribeMoney(subscribeMoney);
	         String allMoney=rs.getString("all_money");
	         if("1".equals(type)||"3".equals(type)){//代购
	        	 vo.setAllMoney(subscribeMoney);
	         }else if("2".equals(type)||"4".equals(type)){//合买
	        	 long divideCopys=rs.getLong("DIVIDE_COPYS");
	        	 long alreadyBuyCopys=rs.getLong("ALREADY_BUY_COPYS");
	        	 if("4".equals(orderStatus)){
	        		 vo.setProgress(-1);
	        	 }else{
	               if(divideCopys==alreadyBuyCopys){
	        		 vo.setProgress(1);
	        	   }else{
	        		 vo.setProgress(0);
	        	   }
	        	 }
	        	 vo.setAllMoney(rs.getString("all_money"));
	         }
	         if(StringUtils.isNotEmpty(allMoney)){
	        	 vo.setAllMoney(allMoney);
	         }
	         System.out.println("进度="+vo.getProgress());
	         resultList.add(vo);
	      }
	      resultMap=new HashMap();
	      resultMap.put("totalCount", size);
	      resultMap.put("resultList", resultList);
	    
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return resultMap;
	
	}
	/**
	 * 最近若干期的期次列表
	 * @param params
	 * @return
	 */
	public List<PhaseNoVO> findLatestPhaseList(Map params){
		 String phaseCategory=(String)params.get("phaseCategory");
		
		 StringBuilder sql=null;
		 if(phaseCategory!=null&&"689".indexOf(phaseCategory)!=-1){//'6':胜负彩期次 '9':进球彩期次 '8':半全场期次
			 sql=new StringBuilder("select t.ID,t.PHASE_NO, ");
		     sql.append(" case when to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi')> to_char(sysdate,'yyyy-mm-dd hh24:mi') and t.SOLD_TIME<=sysdate then '1' ");
		     sql.append(" when to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi')<= to_char(sysdate,'yyyy-mm-dd hh24:mi') and t.SOLD_TIME<=sysdate then '0' ");
		     sql.append(" when to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi')> to_char(sysdate,'yyyy-mm-dd hh24:mi') and t.SOLD_TIME>sysdate then '2' ");
		     sql.append(" end flg from  T_LOTTERY_PHASE  t  where  t.STATUS='4' and t.category<>'1' and t.category='"+phaseCategory+"'  order by t.SOLD_TIME  desc ");
		}else if("1".equals(phaseCategory)){
			sql=new StringBuilder("select t.ID,t.PHASE_NO, ");
			sql.append("case ");
			sql.append(" when  to_char((select max(a.start_time) from t_lottery_against  la,t_against a  where la.against_id=a.against_id and la.phase_id=t.id),'yyyymmddhh24:mi')<=to_char(sysdate-1/(24*4),'yyyymmddhh24:mi')"); 
			sql.append(" then '2' ");
			sql.append(" when  to_char((select max(a.start_time) from t_lottery_against  la,t_against a  where la.against_id=a.against_id and la.phase_id=t.id),'yyyymmddhh24:mi')>to_char(sysdate-1/(24*4),'yyyymmddhh24:mi') ");
			sql.append(" then '1' ");
			sql.append(" end flg  from  T_LOTTERY_PHASE  t  where  t.STATUS='4' and t.category='1' order by t.SOLD_TIME  desc ");
		  }
		 
		 
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
	   
	     List<PhaseNoVO> resultList=new ArrayList<PhaseNoVO>();
	     try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt = conn.prepareStatement(sql.toString());
		      rs=pstmt.executeQuery();
		      PhaseNoVO po=null;
		      while (rs.next()) {
		    	  po=new PhaseNoVO();
			      Long id=rs.getLong("ID");
			      po.setId(id);
			      String phaseNo=rs.getString("PHASE_NO");
			      po.setPhaseNo(phaseNo);
			      String flg=rs.getString("FLG");
			      po.setFlg(flg);
			    resultList.add(po);
			  }
			    
			 }catch(Exception e){
			    	e.printStackTrace();
			  }finally{
			    	closeAll(rs,pstmt,conn);
			  }
	     return resultList;
	}
	public Long saveCoopBuy(BetOrderVO po)throws DaoException{
        Connection conn=null;
		CallableStatement proc=null;
		Long betUserId=po.getBetUserid();//用户ID
		ResultSet rs=null;
	    try{
	      String sql = "{ call bet.sp_Page(?,?,?,?,?,?)}";
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      proc = conn.prepareCall(sql);
	      proc.registerOutParameter(6, OracleTypes.INTEGER);//
	      proc.execute();
	      int size = proc.getInt(5);
	      rs = (ResultSet)proc.getObject(6);
        
	      BetOrderVO vo=null;
	    
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
    }
	/**查询合买时剩余的份数  2010-03-22 10:38**/
	public Map getSurplusCopys(Long betOrderId)throws DaoException{
        Connection conn=null;
		PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    Map resultMap=null;
	    try{
	      resultMap=new HashMap();
	      String sql = "select t.Divide_Copys,t.already_buy_copys,t.SINGLE_MONEY,t.PLAN_CODE  from t_bet_order t  where  t.BET_ID=?";
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      pstmt = conn.prepareCall(sql);
	      pstmt.setLong(1, betOrderId);
	      rs=pstmt.executeQuery();
	      while(rs.next()){
	    	  long allCopys=rs.getLong("Divide_Copys");
	    	  long alreadyBuyCopys=rs.getLong("already_buy_copys");
	    	  BigDecimal singleMoney=rs.getBigDecimal("SINGLE_MONEY");
	    	  resultMap.put("singleMoney", singleMoney);//合买时每一份的价格
	    	  long  surplusCopys=allCopys-alreadyBuyCopys;
	    	  resultMap.put("surplusCopys", surplusCopys);
	    	  String planCode=rs.getString("PLAN_CODE");
	    	  resultMap.put("planCode", planCode);
	    	  return resultMap;
	      }
	      
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    return null;
    }
	/**参与合买列表  2010-03-26 11:00**/
	public long findCoopSize(Map params)throws DaoException{
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Long phaseId=(Long)params.get("phaseId");//期次
		String type=(String)params.get("type");//
		String betUsername=(String)params.get("betUsername");//发起人
		String status=(String)params.get("status");//状态 '1'：满员、'2':未满员、'3':已撤单
		String allMoney=(String)params.get("allMoney");//方案金额 0-100、100-500、501-1000、1000-600000
		String progress=(String)params.get("progress");//进度 0-30、30-60、60-90、90-100
		String isFloor=(String)params.get("isFloor");//保底 '1':已保底、'2'：未保底
		String betCategory=(String)params.get("betCategory");
		try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder querySize=new StringBuilder("  select  count(1) cnt from v_coop_list a where 1=1  ");
	      querySize.append("  and a.phase_id=?");
	      if(StringUtils.isNotEmpty(betUsername)){
	    	  querySize.append("  and a.bet_username like '%"+betUsername+"%'");
	      }
	      if(StringUtils.isNotEmpty(status)){
	    	  if("1".equals(status)){
	    		  querySize.append("  and a.already_buy_copys=a.divide_copys ");
	    	  }else if("2".equals(status)){
	    		  querySize.append("  and a.already_buy_copys < a.divide_copys and a.order_status<>'4' ");
	    	  }else if("3".equals(status)){
	    		  querySize.append("  and a.order_status='4' ");
	    	  }
	      }
	      if(StringUtils.isNotEmpty(allMoney)){
	    	  String[] allMoneys=allMoney.split("-");
	    	  Long min=new Long(allMoneys[0]);
	    	  Long max=new Long(allMoneys[1]);
	    	  querySize.append("  and a.all_money>="+min+" and a.all_money <="+max);
	      }
	      if(StringUtils.isNotEmpty(progress)){
	    	  String[] progressArr=progress.split("-");
	    	  Long min=new Long(progressArr[0]);
	    	  Long max=new Long(progressArr[1]);
	    	  querySize.append("  and a.already_buy_copys*100/a.divide_copys>="+min+" and a.already_buy_copys*100/a.divide_copys <="+max);
	      }
	      if("1".equals(isFloor)){
	    	  querySize.append("  and a.FLOOR_COPYS>0 ");
	      }else if("2".equals(isFloor)){
	    	  querySize.append("  and a.FLOOR_COPYS=0 ");
	      }
	      if(StringUtils.isNotEmpty(type)){
		    	
	    	  querySize.append("  and a.TYPE='"+type+"' ");
	      }
	      if(StringUtils.isNotEmpty(betCategory)){
	    	  querySize.append("  and a.bet_categoty='"+betCategory+"' ");
	      }
	      
	      String idList=(String)params.get("IdList");
	      if(StringUtils.isNotEmpty(idList)){
	        querySize.append("  and a.bet_id in ("+idList+")");
	      }
	      pstmt = conn.prepareStatement(querySize.toString());
	      pstmt.setLong(1, phaseId);
	      rs=pstmt.executeQuery();
	      while (rs.next()) {
	    	  return rs.getLong(1);
	      }
	    
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    return 0;
	
	
	}
	public List<CoopOrder> findCoopList(Map params)throws DaoException{
		
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Integer startRow=(Integer)params.get("startRow");
		Integer pageSize=(Integer)params.get("pageSize");
		Long phaseId=(Long)params.get("phaseId");//期次
		String type=(String)params.get("type");//
		String betUsername=(String)params.get("betUsername");//发起人
		String status=(String)params.get("status");//状态 '1'：满员、'2':未满员、'3':已撤单
		String allMoney=(String)params.get("allMoney");//方案金额 0-100、100-500、501-1000、1000-600000
		String progress=(String)params.get("progress");//进度 0-30、30-60、60-90、90-100
		String isFloor=(String)params.get("isFloor");//保底 '1':已保底、'2'：未保底
		String betCategory=(String)params.get("betCategory");
		
		List<CoopOrder> resultList=null;
		try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("  select a.order_status,a.plan,a.txt_path,a.end_time,a.security_type,a.wici_type,a.bet_id,a.phase_id,a.type,a.plan_code,a.bet_username,a.vici_way,a.all_money,a.bet_multi,a.divide_copys,a.already_buy_copys,a.single_money ,a.bet_military,a.floor_copys,a.already_buy_copys*100/a.divide_copys  coop_progress from v_coop_list a where 1=1  ");
	      queryList.append("  and a.phase_id="+phaseId);
	      if(StringUtils.isNotEmpty(betUsername)){
	    	  queryList.append("  and a.bet_username like '%"+betUsername+"%'");
	      }
	      if(StringUtils.isNotEmpty(status)){
	    	  if("1".equals(status)){
	    	     queryList.append("  and a.already_buy_copys=a.divide_copys ");
	    	  }else if("2".equals(status)){
	    		  queryList.append("  and a.already_buy_copys < a.divide_copys and a.order_status<>'4' ");
	    	  }else if("3".equals(status)){
	    		  queryList.append("  and a.order_status='4' ");
	    	  }
	      }
	      if(StringUtils.isNotEmpty(allMoney)){
	    	  String[] allMoneys=allMoney.split("-");
	    	  Long min=new Long(allMoneys[0]);
	    	  Long max=new Long(allMoneys[1]);
	    	  queryList.append("  and a.all_money>="+min+" and a.all_money <="+max);
	      }
	      if(StringUtils.isNotEmpty(progress)){
	    	  String[] progressArr=progress.split("-");
	    	  Long min=new Long(progressArr[0]);
	    	  Long max=new Long(progressArr[1]);
	    	  queryList.append("  and a.already_buy_copys*100/a.divide_copys>="+min+" and a.already_buy_copys*100/a.divide_copys <="+max);
	      }
	      if("1".equals(isFloor)){
	    	 queryList.append("  and a.FLOOR_COPYS>0 ");
	      }else if("2".equals(isFloor)){
	    	  queryList.append("  and a.FLOOR_COPYS=0 ");
	      }
	      if(StringUtils.isNotEmpty(type)){
		    	
	    	  queryList.append("  and a.TYPE='"+type+"' ");
	      }
	      if(StringUtils.isNotEmpty(betCategory)){
	    	  queryList.append("  and a.bet_categoty='"+betCategory+"' ");
	      }
	      String idList=(String)params.get("IdList");
	      if(StringUtils.isNotEmpty(idList)){
	      queryList.append("  and a.bet_id in ("+idList+")");
	      }
	      
	      String sort=(String)params.get("sort");
	      if(StringUtils.isNotEmpty(sort)){
	    	  queryList.append(" order by "+sort);
	      }else{
	         queryList.append("  order by a.already_buy_copys/a.divide_copys desc");
	      }
	     
	      StringBuilder sql=OracleSqlUtil.addQueryPageSizeCondition(queryList,startRow,pageSize);
    	  //log.info(sql.toString());
    	  pstmt = conn.prepareStatement(sql.toString());
	      rs=pstmt.executeQuery();
	      resultList=new ArrayList<CoopOrder>();
	      CoopOrder vo=null;
	      while (rs.next()) {
	    	     vo=new CoopOrder();
	    	     Long betId=rs.getLong("bet_id");
	    	     String plan=rs.getString("plan");
	    	     vo.setPlan(plan);
	    	 
		         vo.setBetId(betId);
		         String planCode=rs.getString("plan_code");
		         vo.setPlanCode(planCode);
		         allMoney=rs.getString("all_money");
		         vo.setAllMoney(allMoney);
		         long  betMilitary=rs.getLong("bet_military");
		         vo.setBetMilitary(betMilitary);
		         String viciWay=rs.getString("vici_way");
		         vo.setViciWay(viciWay);
		         long betMulti=rs.getLong("bet_multi");
		         vo.setBetMulti(betMulti);
		         long divideCopys=rs.getLong("divide_copys");
		         vo.setDivideCopys(divideCopys);
		         String singleMoney=rs.getString("single_money");
		         vo.setSingleMoney(singleMoney);
		         long alreadyBuyCopys=rs.getLong("already_buy_copys");
		         vo.setAlreadyBuyCopys(alreadyBuyCopys);
		         alreadyBuyCopys*=100;
		         double _progress=alreadyBuyCopys/divideCopys;
		       
		         long floorCopys=rs.getLong("floor_copys");
		         floorCopys*=100;
		         int floorProgress=Integer.parseInt(floorCopys/divideCopys+"");
		         status=_progress==100?"满员":"未满";//状态
		         String orderStatus=rs.getString("order_status");
		         if("4".equals(orderStatus)){
		        	 status="已撤单";
		         }
		         vo.setStatus(status);
		         betUsername=rs.getString("bet_username");
		         vo.setBetUsername(betUsername);
		         type=rs.getString("type");
		         vo.setType(type);
		      
		         int coop_progress=rs.getInt("coop_progress");
		         vo.setProgress(coop_progress+"%+"+floorProgress+"%(保)");
                 String wiciType=rs.getString("wici_type");
                 vo.setWiciType(wiciType);
                 int securityType=rs.getInt("security_type");
                 vo.setSecurityType(securityType);
                 Date endTime=rs.getDate("end_time");
                 vo.setEndTime(endTime);
                 String txtPath=rs.getString("txt_path");
                 vo.setTxtPath(txtPath);
                
		       resultList.add(vo);
	      }
	    
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    return resultList;
	
	
	}
	
	
	@Override
	public List<InGoalBetScaleVo> getCurBetScale(String phase) {
		StringBuilder sql_1 = new StringBuilder();
		List<Object> arg = new ArrayList<Object>();
		sql_1.append(" select t.plan from  t_bet_order t where t.BET_CATEGOTY =3 and t.phase_id = ? ");
		arg.add(Long.parseLong(phase));
		final List<String> list_r = new ArrayList<String>();
		//final StringBuilder sb_1 = new StringBuilder();
		jdbcTemplate.query(sql_1.toString(), arg.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				list_r.add(rs.getString("plan"));
			}
		});
		if(list_r.size()==0)
			return null;
		
		StringBuilder sql_2 = new StringBuilder();
		sql_2.append(" select b.HOST_NAME,b.GUEST_NAME from T_LOTTERY_AGAINST a,T_AGAINST b where a.AGAINST_ID=b.AGAINST_ID and  a.PHASE_ID=? order by a.changci ");
		//final StringBuilder sb_2 = new StringBuilder();
		final List<String> list_host_team = new ArrayList<String>(4);
		final List<String> list_guest_team = new ArrayList<String>(4);
		jdbcTemplate.query(sql_2.toString(), arg.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				list_host_team.add(rs.getString("HOST_NAME"));
				list_guest_team.add(rs.getString("GUEST_NAME"));
				//sb_2.append(rs.getString("HOST_NAME") + "|" + rs.getString("GUEST_NAME"));
			}
		});
		List<InGoalBetScaleVo> result = new ArrayList<InGoalBetScaleVo>(4);
		InGoalBetScaleVo vo = null;
		int b = 0;
		int[][][] a = getScaleByPlan(list_r);
		String[][][] s = new String[4][2][4];
		if(null == a )
			return null;
		for (int i = 0; i < 4; i++) {
			vo = new InGoalBetScaleVo();
			vo.setHost_team_name(list_host_team.get(i));
			vo.setGuest_team_name(list_guest_team.get(i));
			for (int j = 0; j < 2; j++) {
				b = a[i][j][0] + a[i][j][1] + a[i][j][2] + a[i][j][3];
				if(b != 0){
					for (int j2 = 0; j2 < 4; j2++) {
						s[i][j][j2] = new BigDecimal(a[i][j][j2]*100).divide(new BigDecimal(b), 2, BigDecimal.ROUND_HALF_UP).toString();
					}
				}
			}
//			vo.setBet_count_h_0(a[i][0][0]+"");
//			vo.setBet_count_h_1(a[i][0][1]+"");
//			vo.setBet_count_h_2(a[i][0][2]+"");
//			vo.setBet_count_h_3(a[i][0][3]+"");
//			
//			vo.setBet_count_g_0(a[i][1][0]+"");
//			vo.setBet_count_g_1(a[i][1][1]+"");
//			vo.setBet_count_g_2(a[i][1][2]+"");
//			vo.setBet_count_g_3(a[i][1][3]+"");
	
			
			vo.setBet_scale_h_0(s[i][0][0]);
			vo.setBet_scale_h_1(s[i][0][1]);
			vo.setBet_scale_h_2(s[i][0][2]);
			vo.setBet_scale_h_3(s[i][0][3]);
			
			vo.setBet_scale_g_0(s[i][1][0]);
			vo.setBet_scale_g_1(s[i][1][1]);
			vo.setBet_scale_g_2(s[i][1][2]);
			vo.setBet_scale_g_3(s[i][1][3]);
			
			result.add(vo);
		}
		
		return result;
	}
	
	private int[][][] getScaleByPlan(List<String> list){
		//第一维  第几场
		//第二维  主客球队
		//第三维  0123 数量
		int[][][] cs = new int[4][2][4];
		String plan_str = null;
		String[] plans = null;
		String[] bet_content = null;
		int length = list.size();
		for(int i =0;i< length;i++){
			plan_str = list.get(i);
			if(null == plan_str)continue;
			plans = plan_str.split(",");
			for(int j =0;j<plans.length;j++){
				bet_content = plans[j].split("-");
				
				if(bet_content[0].indexOf("0")!=-1)
					cs[j][0][0]++;
				if(bet_content[0].indexOf("1")!=-1)
					cs[j][0][1]++;
				if(bet_content[0].indexOf("2")!=-1)
					cs[j][0][2]++;
				if(bet_content[0].indexOf("3")!=-1)
					cs[j][0][3]++;
				
				if(bet_content[1].indexOf("0")!=-1)
					cs[j][1][0]++;
				if(bet_content[1].indexOf("1")!=-1)
					cs[j][1][1]++;
				if(bet_content[1].indexOf("2")!=-1)
					cs[j][1][2]++;
				if(bet_content[1].indexOf("3")!=-1)
					cs[j][1][3]++;
			}
		}
		return cs;
	}
	/**
	 * 合买排行 
	  *  flg='1':足球单场合买排行
     *  flg='2':足球单场代购排行
     *  flg='3':胜负彩胜负14合买排行
     *  flg='4':进球彩合买排行
     *  flg='5':胜负彩任9合买排行
     *  flg='6':胜负彩6场半全场合买排行
	 * @return
	 * @throws DaoException
	 */
	@Override
	public List<BetTop10Vo> findHmTop10List(Map params)
			throws DaoException {
        String flg=(String)params.get("flg");
		List resultList=new ArrayList();
		try { 
			 String sql=null;
			 if("1".equals(flg)){
			    sql="select  A.username,A.allMoney from (select t.username,sum(t.money) allMoney  from  t_bonus t where instr(t.lottery_type,'6')>0  and t.Flg='2'group by t.username order by sum(t.money) desc)A where rownum<=10";
			 }else if("2".equals(flg)){
				 sql="select  A.username,A.allMoney from (select t.username,sum(t.money) allMoney  from  t_bonus t where instr(t.lottery_type,'6')>0  and t.Flg='2'group by t.username order by sum(t.money) desc)A where rownum<=10";
			 }else if("3".equals(flg)){
				 sql="select  A.username,A.allMoney from (select t.username,sum(t.money) allMoney  from  t_bonus t where t.lottery_type='1'  and t.Flg='2' group by t.username order by sum(t.money) desc)A where rownum<=10";
			 }
			 else if("4".equals(flg)){
				 sql="select  A.username,A.allMoney from (select t.username,sum(t.money) allMoney  from  t_bonus t where t.lottery_type='3'  and t.Flg='2' group by t.username order by sum(t.money) desc)A where rownum<=10";
			 }
			 else if("5".equals(flg)){
				 sql="select  A.username,A.allMoney from (select t.username,sum(t.money) allMoney  from  t_bonus t where t.lottery_type='2'  and t.Flg='2' group by t.username order by sum(t.money) desc)A where rownum<=10";
			 }
			 else if("6".equals(flg)){
				 sql="select  A.username,A.allMoney from (select t.username,sum(t.money) allMoney  from  t_bonus t where t.lottery_type='5'  and t.Flg='2' group by t.username order by sum(t.money) desc)A where rownum<=10";
			 }
			 List<Object[]> list=this.getSession().createSQLQuery(sql).list();
			
			 BetTop10Vo vo=null;
			for(Object[] o : list){
				
				  vo=new BetTop10Vo();
				  String sales=o[1].toString();
				  String username=o[0].toString();
				  vo.setUsername(username);
				  vo.setSales(sales);				  				  
				  resultList.add(vo); 
			}
			
			   
			} catch (Exception e) { 
			   e.printStackTrace(); 
			  }


		
		

		return resultList;
	}
	@Override
	public String hm_search(String search_content, String betCategory) {
		
		
		return null;
	}
	
	
	private static final Log log=LogFactory.getLog(BetDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
}


