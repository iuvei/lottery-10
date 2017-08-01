package com.wintv.lottery.b2c.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.wintv.lottery.b2c.utils.Tools;
import com.wintv.framework.common.OracleSqlUtil;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.C2CProduct;
import com.wintv.framework.pojo.Expert;
import com.wintv.framework.pojo.Race;
import com.wintv.lottery.b2c.vo.B2COrderVO;
import com.wintv.lottery.b2c.vo.B2CProductVO;
import com.wintv.lottery.b2c.vo.ExpertOrderVO;
import com.wintv.lottery.b2c.vo.ExpertVO;
import com.wintv.lottery.b2c.dao.B2CDao;
import com.wintv.lottery.bet.utils.CommUtil;
import com.wintv.lottery.xinshui.vo.XinshuiAgainstVO;
@Repository("b2CDao")
public class B2CDaoImpl extends BaseHibernate<C2CProduct,Long> implements B2CDao{

	public Map  loadOrderDataInfo(Long userId,Long expertId,String orderType)throws DaoException{
        StringBuilder sql=new StringBuilder("select a.expert_name ");
        if("1".equals(orderType)){
        	sql.append(",a.week_pack pack ");
        }else if("2".equals(orderType)){
        	sql.append(",a.month_pack pack ");
        }else if("3".equals(orderType)){
        	sql.append(",a.season_pack  pack ");
        }else if("4".equals(orderType)){
        	sql.append(",a.year_pack pack ");
        }
	    sql.append(",(select  max(a.end_time) from  t_xinshui_order  a,t_expert b where   a.sold_user_id=b.expert_id and b.expert_id=?");
	    sql.append("   and a.buy_user_id=? and a.pay_status='3' ) endTime ");
	    sql.append(" from  t_expert  a  where a.expert_id=? ");
	    Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Map resultMap=null;
	    try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, expertId);
		      pstmt.setLong(2, userId);
		      pstmt.setLong(3, expertId);
		      rs=pstmt.executeQuery();
		      resultMap=new HashMap();
		      while(rs.next()){
		    	  String expertName=rs.getString("expert_name");
		    	  resultMap.put("expertName", expertName);
		    	  String pack=rs.getString("pack");
		    	  resultMap.put("price", pack);
		    	  Date endTime=rs.getDate("endTime");
		    	  log.info("endTime    **1 ="+endTime.toLocaleString());
		    	  Date now=new Date();
		    	  if(endTime!=null){
		    	    if(endTime.after(now)){
		    		  String orderTypeZH=null;
		    		  if("1".equals(orderType)){
		    			  orderTypeZH="包周续费";
		    		  }else if("2".equals(orderType)){
		    			  orderTypeZH="包月续费";
		    		  }else if("3".equals(orderType)){
		    			  orderTypeZH="包季续费";
		    		  }else if("4".equals(orderType)){
		    			  orderTypeZH="包年续费";
		    		  }
		    		  resultMap.put("orderTypeZH", orderTypeZH);
		    		  String validEndTime=Tools.addTime(endTime, orderType);
		    		  resultMap.put("endTime", validEndTime);
		    		  log.info("validEndTime=******** "+validEndTime);
		    	   }
		    	  }else{
		    		  String orderTypeZH=null;
		    		  if("1".equals(orderType)){
		    			  orderTypeZH="首次包周";
		    		  }else if("2".equals(orderType)){
		    			  orderTypeZH="首次包月";
		    		  }else if("3".equals(orderType)){
		    			  orderTypeZH="首次包季";
		    		  }else if("4".equals(orderType)){
		    			  orderTypeZH="首次包年";
		    		  }
		    		  resultMap.put("orderTypeZH", orderTypeZH);
		    		  String validEndTime=Tools.addTime(new Date(), orderType);
		    		  log.info("validEndTime="+validEndTime);
		    		  resultMap.put("endTime", validEndTime);
		    	  
		    	  }
		    	 return resultMap;
		      }
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 closeAll(rs,pstmt,conn);
	    }
		 return resultMap;
	
	}
	//前台普通用户取消b2c心水订单2010-04-16 12:56
	public int  cancelB2COrder(Long  xinshuiOrderId)throws DaoException{
		log.info("xinshuiOrderId="+xinshuiOrderId);
        Connection conn=null;
   	    CallableStatement cstmt =null;
   	    try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call B2C_PAY.cancel_b2c_order(?,?)}"); 
			  cstmt.setLong(1, xinshuiOrderId);
			  cstmt.registerOutParameter(2, OracleTypes.INTEGER);   
			  cstmt.execute();
		   return cstmt.getInt(2);
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

	
	public Map  loadDataInfo(Map params)throws DaoException{
		
		Long orderId=(Long)params.get("orderId");
		Long userId=null;
		Long expertId=null;
		String orderType=null;
		StringBuilder sql=null;
		if(orderId==null){
			 userId=(Long)params.get("userId");
		     expertId=(Long)params.get("expertId");
			 orderType=(String)params.get("orderType");
             sql=new StringBuilder("select a.expert_name ");
            if("1".equals(orderType)){
        	    sql.append(",a.week_pack pack ");
           }else if("2".equals(orderType)){
        	  sql.append(",a.month_pack pack ");
           }else if("3".equals(orderType)){
        	 sql.append(",a.season_pack  pack ");
           }else if("4".equals(orderType)){
        	sql.append(",a.year_pack pack ");
        }
	    sql.append(",(select  max(a.end_time) from  t_xinshui_order  a,t_expert b where   a.sold_user_id=b.expert_id and b.expert_id="+expertId);
	    sql.append("   and a.buy_user_id="+userId+" and a.pay_status='3' ) endTime ");
	    sql.append(" from  t_expert  a  where a.expert_id="+expertId);
	   }else{
		   sql=new StringBuilder("select  t.order_no,t.order_type,r.expert_name,t.price pack,t.order_type,t.end_time, ");
		   sql.append(" (select  max(a.end_time) from  t_xinshui_order  a,t_expert b where   a.sold_user_id=b.user_id and b.expert_id=t.product_id");
		   sql.append(" and a.buy_user_id=t.buy_user_id and a.pay_status='3' ) endTime  from   t_xinshui_order t,t_expert  r");
		   sql.append(" where t.product_id=r.expert_id and t.xinshui_order_id="+orderId);
	   }
		log.info("hikin---------="+sql.toString());
	    Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Map resultMap=null;
	    try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
//		      pstmt.setLong(1, expertId);
//		      pstmt.setLong(2, userId);
//		      pstmt.setLong(3, expertId);
		      rs=pstmt.executeQuery();
		      resultMap=new HashMap();
		      while(rs.next()){
		    	  if(orderId!=null){
		    		  String orderNo=rs.getString("order_no");
		    		  resultMap.put("orderNo", orderNo);
		    		  orderType=rs.getString("order_type");
		    	  }
		    	  String expertName=rs.getString("expert_name");
		    	  resultMap.put("expertName", expertName);
		    	  String pack=rs.getString("pack");
		    	  resultMap.put("price", pack);
		    	  Date endTime=rs.getDate("endTime");
		    	  Date now=new Date();
		    	  if(endTime!=null){
		    	    if(endTime.after(now)){
		    		  String orderTypeZH=null;
		    		  if("1".equals(orderType)){
		    			  orderTypeZH="包周续费";
		    		  }else if("2".equals(orderType)){
		    			  orderTypeZH="包月续费";
		    		  }else if("3".equals(orderType)){
		    			  orderTypeZH="包季续费";
		    		  }else if("4".equals(orderType)){
		    			  orderTypeZH="包年续费";
		    		  }
		    		  resultMap.put("orderTypeZH", orderTypeZH);
		    		  String validEndTime=Tools.addTime(endTime, orderType);
		    		  resultMap.put("endTime", validEndTime);
		    		  log.info("endTime="+endTime);
		    	   }else{
		    		   String orderTypeZH=null;
			    		  if("1".equals(orderType)){
			    			  orderTypeZH="首次包周";
			    		  }else if("2".equals(orderType)){
			    			  orderTypeZH="首次包月";
			    		  }else if("3".equals(orderType)){
			    			  orderTypeZH="首次包季";
			    		  }else if("4".equals(orderType)){
			    			  orderTypeZH="首次包年";
			    		  }
			    		  resultMap.put("orderTypeZH", orderTypeZH);
			    		  String validEndTime=Tools.addTime(new Date(), orderType);
			    		  resultMap.put("endTime", validEndTime);
			    		  log.info("endTime2="+endTime);
		    	   }
		    	   
		    	  }else{

		    		  String orderTypeZH=null;
		    		  if("1".equals(orderType)){
		    			  orderTypeZH="首次包周";
		    		  }else if("2".equals(orderType)){
		    			  orderTypeZH="首次包月";
		    		  }else if("3".equals(orderType)){
		    			  orderTypeZH="首次包季";
		    		  }else if("4".equals(orderType)){
		    			  orderTypeZH="首次包年";
		    		  }
		    		  resultMap.put("orderTypeZH", orderTypeZH);
		    		  String validEndTime=Tools.addTime(new Date(), orderType);
		    		  resultMap.put("endTime", validEndTime);
		    		  log.info("endTime2="+endTime);
		    	  }
		    	 return resultMap;
		      }
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 closeAll(rs,pstmt,conn);
	    }
		 return resultMap;
	
	}
	/**查询联赛名称 ID   2010-04-15 16:31**/
	public List<Race> findRaceList(Long expertId)throws DaoException{
		StringBuilder sql=new StringBuilder("select distinct a.race_name,a.race_id from  t_c2c_product a,t_expert c  ");
	    sql.append("  where  c.expert_id=?  and a.is_b2c='1' and a.tx_user_id=c.expert_id ");
	    Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Race>  raceList=null;
	    try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, expertId);
		      rs=pstmt.executeQuery();
		      Race po=null;
		      raceList=new ArrayList<Race>();
		      while(rs.next()){
		    	  po=new Race();
		    	  String raceName=rs.getString("race_name");
		    	  po.setName(raceName);
		    	  Long raceId=rs.getLong("race_id");
		    	  po.setId(raceId);
		    	  raceList.add(po);
		      }
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 closeAll(rs,pstmt,conn);
	    }
		 return raceList;
	}
	
	/**B2C 是否允许查看某专家的心水 2010-04-15 14:29**/
	public boolean isAllowLook(Long userId,Long expertId)throws DaoException{
        StringBuilder sql=new StringBuilder("select count(*) cnt from t_xinshui_order a,t_expert b ");
	    sql.append(" where a.sold_user_id=b.expert_id and  a.type='2' and a.buy_user_id=?  and b.expert_id=? ");
	    sql.append(" and to_char(sysdate,'yyyy-mm-dd')<to_char(a.end_time,'yyyy-mm-dd') ");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		log.info("hikin ="+sql.toString());
	    try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, userId);
		      pstmt.setLong(2, expertId);
		      rs=pstmt.executeQuery();
		      while(rs.next()){
		    	  long cnt= rs.getLong("cnt");
		    	  return cnt>0?true:false;
		      }
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 closeAll(rs,pstmt,conn);
	    }
		 return false;
	
	}
	/**专家-专家首页-某内参列表数据量统计： 2010-04-15 13:39**/
	public long findXinshuiSize(Map params)throws DaoException{
		Long expertId=(Long)params.get("expertId");
		String key=(String)params.get("key");
		StringBuilder sql=new StringBuilder("select count(1) cnt from  t_c2c_product a,t_against b,t_expert c ");
		sql.append(" where  c.expert_id=? and a.is_b2c='1' and a.against_id=b.against_id and a.tx_user_id=c.expert_id ");
		String  fromTime=(String)params.get("fromTime");
		String  toTime=(String)params.get("toTime");
		Long raceId=(Long)params.get("raceId");
		if(StringUtils.isNotEmpty(fromTime)){
			sql.append(" and to_char(b.start_time,'yyyy-mm-dd')>='"+fromTime+"' ");
		}
		if(StringUtils.isNotEmpty(toTime)){
			sql.append(" and to_char(b.start_time,'yyyy-mm-dd')<='"+toTime+"' ");
		}
		if(raceId!=null){
			sql.append(" and b.race_id="+raceId);
		}
		if(StringUtils.isNotEmpty(key)){
			sql.append(" and (a.host_name like '"+key+"' or  a.guest_name like '"+key+"'  or a.race_name   like '"+key+"') ");
		}
		log.info(sql.toString());
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	    try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, expertId);
		      rs=pstmt.executeQuery();
		      while(rs.next()){
		    	  return rs.getLong("cnt");
		      }
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 closeAll(rs,pstmt,conn);
	    }
		 return 0;
	}
	/**专家-专家首页-某内参列表分页： 2010-04-15 13:39**/
	public List<B2CProductVO> findXinshuiList(Map params)throws DaoException{
		Long expertId=(Long)params.get("expertId");
		String sort=(String)params.get("sort");
		String key=(String)params.get("key");
		Boolean isAllowLook=(Boolean)params.get("isAllowLook");//是否允许查看  add by Hikin Yao
		StringBuilder sql=new StringBuilder("select to_NUMBER(a.confident_index) confident_index,to_char(b.start_time,'yyyy-mm-dd hh24:mi') start_time,c.expert_code,b.host_name,b.guest_name,a.type,a.select_type,b.race_name,b.rounds_name,a.zh_desc ");
	    sql.append(" ,decode(a.status,'1','未完赛','2','赢','3','负','4','走') status ");
		sql.append(" from  t_c2c_product a,t_against b,t_expert c ");
	    sql.append(" where  c.expert_id=? and a.is_b2c='1' and a.against_id=b.against_id and a.tx_user_id=c.expert_id ");
	   
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<B2CProductVO> resultList=null;
		Integer startRow=(Integer)params.get("startRow");
		Integer pageSize=(Integer)params.get("pageSize");
		String  fromTime=(String)params.get("fromTime");
		String  toTime=(String)params.get("toTime");
		Long raceId=(Long)params.get("raceId");
		
		if(StringUtils.isNotEmpty(fromTime)){
			sql.append(" and to_char(b.start_time,'yyyy-mm-dd')>='"+fromTime+"' ");
		}
		if(StringUtils.isNotEmpty(toTime)){
			sql.append(" and to_char(b.start_time,'yyyy-mm-dd')<='"+toTime+"' ");
		}
		 
		if(raceId!=null){
			sql.append(" and b.race_id="+raceId);
		}
		if(StringUtils.isNotEmpty(key)){
			sql.append(" and (a.host_name like '"+key+"' or  a.guest_name like '"+key+"'  or a.race_name   like '"+key+"') ");
		}
		if(StringUtils.isNotEmpty(sort)){
		    	sql.append(" order by b.start_time desc");
		}
		 try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      sql=OracleSqlUtil.addQueryPageSizeCondition(sql,startRow,pageSize);
		      log.info("sql="+sql.toString());
		      pstmt=conn.prepareStatement(sql.toString());
		      pstmt.setLong(1, expertId);
		      rs=pstmt.executeQuery();
		      B2CProductVO po=null;
		      resultList=new ArrayList<B2CProductVO>();
		      while(rs.next()){
		    	  po=new B2CProductVO();
		    	  String startTime=rs.getString("start_time");
		    	  po.setStartTime(startTime);
		    	  int confiIndex=rs.getInt("confident_index");
		    	  po.setConfiIndex(confiIndex);
		    	  String expertCode=rs.getString("expert_code");
		    	  po.setExpertCode(expertCode);
		    	  String hostName=rs.getString("host_name");
		    	  po.setHostName(hostName);
		    	  String guestName=rs.getString("guest_name");
		    	  po.setGuestName(guestName);
		    	  String status=rs.getString("status");
		    	  po.setStatus(status);
		    	  String type=rs.getString("type");
		    	  po.setType(type);
		    	  if(null!=isAllowLook&&isAllowLook){//允许查看时 才将选择的结果放到po中去 add by Hikin Yao
			    	  String selectType=rs.getString("select_type");
			    	  po.setSelectType(selectType);
		    	  }
		    	  String raceName=rs.getString("race_name");
		    	  po.setRaceName(raceName);
		    	  String zhDesc=rs.getString("zh_desc");
		    	  po.setZhDesc(zhDesc);
		    	  String roundsName=rs.getString("rounds_name");
		    	  po.setRoundsName(roundsName);
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
	 * 投资超值包  2010-04-13 10:11
	 * 参数:
	 *     cnt:1 只返回一条  其他值:返回两条
	 *     
	 *
	 * @return
	 * @throws DaoException
	 */
	public List<ExpertOrderVO> findValuePackList(Map params)throws DaoException{
		int cnt=(Integer)params.get("cnt");
		StringBuilder sql=null;
		if(cnt==1){
			sql=new StringBuilder("select Expert_Id,EXPERT_NAME,INTRODUCTION,PHOTO,MONTH_PACK from (");
			sql.append("select a.Expert_Id,a.EXPERT_NAME,a.INTRODUCTION,a.PHOTO,a.MONTH_PACK from T_EXPERT a  where a.is_value_pack='1'  order by a.pub_time desc ) where rownum=1");
		}else{
			sql=new StringBuilder("select Expert_Id,EXPERT_NAME,INTRODUCTION,PHOTO,MONTH_PACK from (");
			sql.append("select a.Expert_Id,a.EXPERT_NAME,a.INTRODUCTION,a.PHOTO,a.MONTH_PACK from T_EXPERT a  where a.is_value_pack='1'  order by a.pub_time desc ) where rownum<=2");
	   }
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<ExpertOrderVO> resultList=null;
		 try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      rs=pstmt.executeQuery();
		      ExpertOrderVO po=null;
		      resultList=new ArrayList<ExpertOrderVO>();
		      while(rs.next()){
		    	  po=new ExpertOrderVO();
		    	  String expertName=rs.getString("EXPERT_NAME");
		    	  po.setExpertName(expertName);
		    	  String introduction=rs.getString("INTRODUCTION");
		    	  po.setIntroduction(introduction);
		    	  String photo=rs.getString("PHOTO");//add by hikin yao
		    	  po.setPhoto(photo);
		    	  String monthPack=rs.getString("MONTH_PACK");
		    	  po.setMonthPack(monthPack);
		    	  Long expertId=rs.getLong("Expert_Id");
		    	  po.setExpertId(expertId);
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
	 * 专家推荐  2010-04-13 10:11
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public List<ExpertOrderVO> findRecommendExpertList(Map params)throws DaoException{
		StringBuilder sql=new StringBuilder("select a.MONTH_PACK,a.EXPERT_ID,a.EXPERT_NAME,a.INTRODUCTION,a.PHOTO from T_EXPERT a where a.is_value_pack='0'  order by a.TYPE desc");// add [where a.is_value_pack='0'] by Hikin Yao
	    Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<ExpertOrderVO> resultList=null;
		 try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      rs=pstmt.executeQuery();
		      ExpertOrderVO po=null;
		      resultList=new ArrayList<ExpertOrderVO>();
		      while(rs.next()){
		    	  po=new ExpertOrderVO();
		    	  String expertName=rs.getString("EXPERT_NAME");
		    	  po.setExpertName(expertName);
		    	  String introduction=rs.getString("INTRODUCTION");
		    	  po.setIntroduction(introduction);
		    	  String photo=rs.getString("PHOTO");//add by hikin yao
		    	  po.setPhoto(photo);
		    	  Long expertId=rs.getLong("EXPERT_ID");
		    	  po.setExpertId(expertId);
		    	  String monthPack=rs.getString("MONTH_PACK");
		    	  if(StringUtils.isNotEmpty(monthPack)){
		    	    CommUtil.getCurrency(monthPack);
		    	    po.setMonthPack(monthPack);
		    	  }
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
	 * 您的内参订单  2010-04-13 09:22
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public List<ExpertOrderVO> findMyOrderExpertList(Map params)throws DaoException{
		/***** Begin  Add By Hikin Yao Begin ***/
		
		StringBuilder sql=new StringBuilder("select b.*,a.EXPERT_ID,a.EXPERT_NAME,a.PHOTO,a.INTRODUCTION  "); 
		sql.append(" from (select SOLD_USER_ID,max(c.END_TIME) as END_TIME from T_XINSHUI_ORDER c where c.type='2' and c.pay_status='3'");
		Long buyUserId=(Long)params.get("buyUserId");//购买用户Id
		if(null!=buyUserId){
			sql.append("  and c.BUY_USER_ID="+buyUserId);
		}
		sql.append(" group by c.SOLD_USER_ID ) b left join T_EXPERT a on b.SOLD_USER_ID=a.EXPERT_ID ");
		/*
		StringBuilder sql=new StringBuilder("select a.EXPERT_ID,a.EXPERT_NAME,a.PHOTO,a.INTRODUCTION,b.END_TIME from T_EXPERT a,T_XINSHUI_ORDER b  where a.USER_ID=b.SOLD_USER_ID");
		sql.append(" and b.type='2' and b.pay_status='3'");
		Long buyUserId=(Long)params.get("buyUserId");//购买用户Id
		if(null!=buyUserId){
			sql.append("  and b.BUY_USER_ID="+buyUserId);
		}*/
		log.info("sql="+sql.toString());
		/***** End   Add By Hikin Yao ***/
	    Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<ExpertOrderVO> resultList=null;
		 try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      pstmt=conn.prepareStatement(sql.toString());
		      //pstmt.setLong(parameterIndex, x)
		      rs=pstmt.executeQuery();
		      ExpertOrderVO po=null;
		      resultList=new ArrayList<ExpertOrderVO>();
		      while(rs.next()){
		    	  po=new ExpertOrderVO();
		    	  String expertName=rs.getString("EXPERT_NAME");
		    	  po.setExpertName(expertName);
		    	  String introduction=rs.getString("INTRODUCTION");
		    	  po.setIntroduction(introduction);
		    	  String photo=rs.getString("PHOTO");
		    	  po.setPhoto(photo);
		    	  String endTime=rs.getString("END_TIME");
		    	  po.setEndTime(endTime);
		    	  Long expertId=rs.getLong("EXPERT_ID");
		    	  po.setExpertId(expertId);
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
	 * 我的内参-内参订单管理列表数据
	 * @author Hikin Yao
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public List<B2COrderVO> getMyB2cOrderList(Map params) throws DaoException {
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		Long buyUserId=(Long)params.get("buyUserId");//购买用户Id
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("  select a.ORDER_TYPE,a.product_id,a.XINSHUI_ORDER_ID,a.ORDER_NO,a.SOLD_USERNAME,a.PRICE,to_char(a.BUY_TIME,'yyyy-mm-dd hh24:mi') ORDER_TIME,a.PAY_STATUS  ");
		sqlStr.append("  from T_XINSHUI_ORDER  a  where a.TYPE='2' and a.buy_time>=(select add_months(sysdate,-3) from dual ) ");
		if(null!=buyUserId){
			sqlStr.append(" and a.BUY_USER_ID="+buyUserId);
		}
		String orderFiled="a.PAY_STATUS";
		String orderType="asc";
		sqlStr.append(" order by " + orderFiled + " " + orderType);
		// 添加Oracle查询分页条件
		sqlStr = OracleSqlUtil.addQueryPageSizeCondition(sqlStr, startRow,pageSize);
		log.info("hikin select="+sqlStr);
		final List resultList = new ArrayList();
		jdbcTemplate.query(sqlStr.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						B2COrderVO vo = new B2COrderVO();
						Long xinshuiOrderId=rs.getLong("XINSHUI_ORDER_ID");
				         vo.setXinshuiOrderId(xinshuiOrderId);
				         Long productId=rs.getLong("product_id");
				         vo.setXinshuiId(productId);
				         String orderNo=rs.getString("ORDER_NO");
				         vo.setOrderNo(orderNo);
				         String soldUsername=rs.getString("SOLD_USERNAME");
				         vo.setSoldUsername(soldUsername);//专家名
				         String orderType=rs.getString("ORDER_TYPE");
				         vo.setOrderType(orderType);
				         String orderTime=rs.getString("ORDER_TIME");
				         vo.setOrderTime(orderTime);
				         String _payStatus=rs.getString("PAY_STATUS");
				         vo.setPayStatus(_payStatus);
				         String price =rs.getString("PRICE");
				         vo.setPrice(price);
						resultList.add(vo);
					}
				});
		return resultList;
	}
	
	/**
	 * 我的内参-统计内参订单管理纪录条数
	 * @author Hikin Yao
	 * @return 返回用户查询列表
	 */
	public Long countMyB2cOrderListSize(Map params)
			throws DaoException {
		Long buyUserId=(Long)params.get("buyUserId");//购买用户Id
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("  select count(distinct a.XINSHUI_ORDER_ID) as totalCount");
		sqlStr.append("  from T_XINSHUI_ORDER  a  where a.TYPE='2' and a.BUY_USER_ID="+buyUserId);
		log.info("hikin count="+sqlStr);
		final List resultList = new ArrayList();
		jdbcTemplate.query(sqlStr.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						resultList.add(rs.getLong("totalCount"));
					}
				});
		if (resultList.size() > 0) {
			return (Long)resultList.get(0);
		} else {
			return 0L;
		}
	}

	/**
	 * 判断用户是否允许购买
	 * 
	 * @author Hikin Yao
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public Long isAlreadyBuyTheProduct(Map params) throws DaoException{
		/*
		Long buyUserId=(Long)params.get("buyUserId");//购买用户Id
		Long expertId=(Long)params.get("expertId");//购买用户Id
		Integer orderType=(Integer)params.get("orderType");//购买用户Id
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("  select  count(distinct b.xinshui_order_id) as totalCount from T_XINSHUI_ORDER b  where b.type='2' and b.pay_status='3' ");
		sqlStr.append("  and b.order_type='"+orderType+"' and b.product_id='"+expertId+"' and b.buy_user_id='"+buyUserId+"' and sysdate<b.end_time");
		log.info("hikin isAlreadyBuyTheProduct="+sqlStr);
		final List resultList = new ArrayList();
		jdbcTemplate.query(sqlStr.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						resultList.add(rs.getLong("totalCount"));
					}
				});
		if(resultList.size() > 0) {
			return (Long)resultList.get(0);
		} else {
			return 0L;
		}*/
		return 0L;
	}
	/**B2C订购 按时间查询、按状态查询 文档第4页 **/
	public Map  findMyB2COrderList(Map params)throws DaoException{
        Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		Long soldUserId=(Long)params.get("soldUserId");//期次
		String payStatus=(String)params.get("payStatus");//1:"未支付"、2:"已支付"                        5:"已取消"
		String from=(String)params.get("from");//起始时间
		String to=(String)params.get("to");//结束时间
		List<B2COrderVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("  select a.ORDER_TYPE,a.XINSHUI_ORDER_ID,a.ORDER_NO,a.SOLD_USERNAME,a.PRICE,to_char(a.BUY_TIME,'yy-mm-dd hh24:mi') ORDER_TIME,a.PAY_STATUS  ");
	      queryList.append("  from T_XINSHUI_ORDER  a  where a.TYPE='2' and a.SOLD_USER_ID="+soldUserId);
	      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from T_XINSHUI_ORDER  b  where b.TYPE='2' and b.SOLD_USER_ID="+soldUserId);
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
	      resultList=new ArrayList<B2COrderVO>();
          B2COrderVO vo=null;
	      while (rs.next()) {
	    	 vo=new B2COrderVO();
	         Long xinshuiOrderId=rs.getLong("XINSHUI_ORDER_ID");
	         vo.setXinshuiOrderId(xinshuiOrderId);
	         String soldUsername=rs.getString("SOLD_USERNAME");
	         vo.setSoldUsername(soldUsername);//专家名
	         String orderType=rs.getString("ORDER_TYPE");
	         vo.setOrderType(orderType);
	         String orderTime=rs.getString("ORDER_TIME");
	         vo.setOrderTime(orderTime);
	         String _payStatus=rs.getString("PAY_STATUS");
	         vo.setPayStatus(_payStatus);
	         String price =rs.getString("PRICE");
	         vo.setPrice(price);
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
	 * 内参订购页面1 用户点击专家“订购”后弹出如下页面，用户点击“确认”则后台产生对应订单，此时订单为“未支付”状态。  文档第5页 2010-03-12 09:34
	 * @param orderId
	 * @return
	 * @throws DaoException
	 */
	public B2COrderVO loadB2COrder(Long orderId)throws DaoException{
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		B2COrderVO po=null;
		try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder sql=new StringBuilder("  select a.ORDER_TYPE,a.XINSHUI_ORDER_ID,a.SOLD_USERNAME,a.PRICE,to_char(a.START_TIME,'yy-mm-dd hh24:mi') ORDER_TIME  ");
	      sql.append("  from T_XINSHUI_ORDER  a  where  a.XINSHUI_ORDER_ID="+orderId);
	      pstmt=conn.prepareStatement(sql.toString());
	      rs=pstmt.executeQuery();
          while (rs.next()) {
        	  po=new B2COrderVO();
        	  String soldUsername=rs.getString("SOLD_USERNAME");
        	  po.setSoldUsername(soldUsername);
        	  String type=rs.getString("TYPE");
        	  po.setType(type);
        	  String orderTime=rs.getString("ORDER_TIME");
        	  po.setOrderTime(orderTime);
        	  String price=rs.getString("PRICE");
        	  po.setPrice(price);
         }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    return po;
	  }
//----------------------------------------内参后台管理——专家管理界面  文档第8页---------------------------------------------------------------------------------------
	/**
	 * 内参后台管理——专家管理界面说明：“发布记录”点击后即进入内参管理页面，列出对应专家的所有内参列表 8页面
	 */
	public Map  findExpertList(Map params)throws DaoException{
        Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		
		String status=(String)params.get("status");//状态
		String monthPack=(String)params.get("monthPack");//包月人数
		String weekPack=(String)params.get("weekPack");//包周人数
		List<ExpertVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("select a.* from  V_EXPERT_LIST a where 1=1 ");
	      
	      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from V_EXPERT_LIST  b  where 1=1 ");
	      if(StringUtils.isNotEmpty(status)){
	    	  queryList.append(" and a.STATUS='"+status+"' ");
	    	  querySize.append(" and b.STATUS='"+status+"' ");
	      }
	      if(StringUtils.isNotEmpty(monthPack)){
	    	  String[] monthPacks=monthPack.split("~");
	    	  if(monthPacks.length==2){
	    		  int min=Integer.parseInt(monthPacks[0]);
	    		  int max=Integer.parseInt(monthPacks[1]);
	    		  queryList.append(" and a.packMonthOrderCntAll>="+min+"  and a.packMonthOrderCntAll<="+max);
		    	  querySize.append(" and b.packMonthOrderCntAll>="+min+"  and b.packMonthOrderCntAll<="+max);
	    	  }else{
	    		  int max=Integer.parseInt(monthPacks[0]);
	    		  queryList.append(" and a.packMonthOrderCntAll<="+max);
		    	  querySize.append(" and b.packMonthOrderCntAll<="+max);
	    	  }
	    	 
	      }
	      if(StringUtils.isNotEmpty(weekPack)){
	    	  String[] weekPacks=weekPack.split("~");
	    	  if(weekPacks.length==2){
	    		  int min=Integer.parseInt(weekPacks[0]);
	    		  int max=Integer.parseInt(weekPacks[1]);
	    		  queryList.append(" and a.packWeekOrderCntAll>="+min+"  and a.packWeekOrderCntAll<="+max);
		    	  querySize.append(" and b.packWeekOrderCntAll>="+min+"  and b.packWeekOrderCntAll<="+max);
	    	  }else{
	    		  int max=Integer.parseInt(weekPacks[0]);
	    		  queryList.append(" and a.packWeekOrderCntAll<="+max);
		    	  querySize.append(" and b.packWeekOrderCntAll<="+max);
	    	  }
	    	 
	      }
	      String soldMoney=(String)params.get("soldMoney");
	      if(StringUtils.isNotEmpty(soldMoney)){
	    	  String[] soldMoneys=soldMoney.split("~");
	    	  if(soldMoneys.length==2){
	    		  int min=Integer.parseInt(soldMoneys[0]);
	    		  int max=Integer.parseInt(soldMoneys[1]);
	    		 
		    	  if(min==0){
		    		  queryList.append(" and (a.soldMoney is null or (a.soldMoney>="+min+"  and a.soldMoney<="+max+")) ");
			    	  querySize.append(" and (b.soldMoney is null or ( b.soldMoney>="+min+"  and b.soldMoney<="+max+")) ");
		    	  }else{
		    		  queryList.append(" and a.soldMoney>="+min+"  and a.soldMoney<="+max);
			    	  querySize.append(" and b.soldMoney>="+min+"  and b.soldMoney<="+max);
		    	  }
	    	  }else{
	    		  int min=Integer.parseInt(soldMoneys[0]);
	    		  queryList.append(" and a.soldMoney>="+min);
		    	  querySize.append(" and b.soldMoney>="+min);
	    	  }
	    	 
	      }
	      String expertName=(String)params.get("expertName");
	      if(StringUtils.isNotEmpty(expertName)){
	    	  queryList.append(" and a.EXPERT_NAME like'%"+expertName+"%'");
	    	  querySize.append(" and b.EXPERT_NAME like'%"+expertName+"%'");
	      }
	      String rate=(String)params.get("rate");
	      String flg=(String)params.get("flg");
	      if(StringUtils.isNotEmpty(flg)&&StringUtils.isNotEmpty(rate)){
	    	  String[] rates=rate.split("~");
	    	  int min=Integer.parseInt(rates[0]);
	    	  int max=Integer.parseInt(rates[1]);
	    	  if("1".equals(flg)){//亚盘
	    		  if(rates.length==2){
	    	        queryList.append(" and a.AsiaAll >="+min+" and a.AsiaAll <="+max);
	    	        querySize.append(" and b.AsiaAll >="+min+" and b.AsiaAll <="+max);
	    		  }else{
	    			  min=Integer.parseInt(rates[0]);
	    			  queryList.append(" and a.AsiaAll >="+min);
		    	      querySize.append(" and b.AsiaAll >="+min);
	    		  }
	    	  }else if("2".equals(2)){//大小盘
	    		  if(rates.length==2){
		    	        queryList.append(" and a.bigsamllAll >="+min+" and a.bigsamllAll <="+max);
		    	        querySize.append(" and b.bigsamllAll >="+min+" and b.bigsamllAll <="+max);
		    		  }else{
		    			  min=Integer.parseInt(rates[0]);
		    			  queryList.append(" and a.bigsamllAll >="+min);
			    	      querySize.append(" and b.bigsamllAll >="+min);
		    		  }
	    		  
	    	  }else if("3".equals(flg)){//欧盘
	    		  if(rates.length==2){
		    	        queryList.append(" and a.EuAll >="+min+" and a.EuAll <="+max);
		    	        querySize.append(" and b.EuAll >="+min+" and b.EuAll <="+max);
		    		  }else{
		    			  min=Integer.parseInt(rates[0]);
		    			  queryList.append(" and a.EuAll >="+min);
			    	      querySize.append(" and b.EuAll >="+min);
		    		  }
	    	  }
	      }
	      
	      
	      log.info(queryList.toString());
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
	      resultList=new ArrayList<ExpertVO>();
	      ExpertVO po=null;
	      while (rs.next()) {
	    	  po=new ExpertVO();
	    	  Long expertId=rs.getLong("EXPERT_ID");
	    	  po.setExpertId(expertId);
	    	  log.info("**expertId="+expertId);
	    	  String _expertName=rs.getString("EXPERT_NAME");
	    	  po.setExpertName(_expertName);
	    	  String _monthPack=rs.getString("MONTH_PACK");//包月价格
	    	  po.setMonthPack(_monthPack);
	    	  String _weekPack=rs.getString("WEEK_PACK");//包月价格
	    	  po.setWeekPack(_weekPack);
	    	  String _status=rs.getString("STATUS");//状态
	    	  po.setStatus(_status);
	    	  int ncCnt=rs.getInt("nc_cnt");//内参总数
	    	  po.setNcCnt(ncCnt);
	    	  String contractTime=rs.getString("CONTRACT_TIME");//签约时间
	    	  po.setContractTime(contractTime);
	    	 
	    	  String euAll=rs.getString("EuAll");
	    	  po.setEuAll(euAll);
	    	  String euMonth=rs.getString("EuMonth");
	    	  po.setEuMonth(euMonth);
	    	  
	 		 String asiaAll=rs.getString("AsiaAll");
	 		 po.setAsiaAll(asiaAll);
	 		 String asiaMonth=rs.getString("AsiaMonth");
	 		 po.setAsiaMonth(asiaMonth);
	 		 String bigsamllAll=rs.getString("bigsamllAll");
	 		 po.setBigsamllAll(bigsamllAll);
	 		 String bigsamllMonth=rs.getString("bigsamllMonth");
	 		 po.setBigsamllMonth(bigsamllMonth);

	 		 String packMonthOrderCntAll=rs.getString("packMonthOrderCntAll");
	 		 po.setPackMonthOrderCntAll(packMonthOrderCntAll);
	 		 String packMonthOrderCntMonth=rs.getString("packMonthOrderCntMonth");
	 		 po.setPackMonthOrderCntMonth(packMonthOrderCntMonth);

	 		 String packWeekOrderCntAll=rs.getString("packWeekOrderCntAll");
	 		 po.setPackWeekOrderCntAll(packWeekOrderCntAll);
	 		 String packWeekOrderCntMonth=rs.getString("packWeekOrderCntMonth");
	 		 po.setPackWeekOrderCntMonth(packWeekOrderCntMonth);

	 		 String _soldMoney=rs.getString("soldMoney");
	 		 po.setSoldMoney(_soldMoney);


	 		 String lastPub=rs.getString("lastPub");
	 		 po.setLastPub(lastPub);
	    	  
	    	  
	    	 
	    	  resultList.add(po);
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
	//------------------------------------------------
	/**专家内参发布——赛事及内参类型选择界面   文档第9页 2010-03-12 17:40**/
	public List<XinshuiAgainstVO> findB2CAgainstList(Map params) throws DaoException {
		
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 Long raceId=(Long)params.get("raceId");
		 String sort=(String)params.get("sort");//排序
		 List<XinshuiAgainstVO> resultList=null;
		 try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		     StringBuilder sql=new StringBuilder("select t.RACE_NAME,t.START_TIME,t.HOST_NAME,t.GUEST_NAME,t.DEADLINE,t.ASIA_PEI,t.BIG_SMALL  ");
		     sql.append(" ,t.AGAINST_ID from v_findAgainstList t where 1=1 ");
		     if(raceId!=null){
		    	 sql.append(" and t.RACE_ID="+raceId);
		     }
		     if(StringUtils.isNotEmpty(sort)){
		    	 sql.append(" order by t."+sort);
		    }
		    log.info(sql.toString());
		    pstmt=conn.prepareStatement(sql.toString());
	        XinshuiAgainstVO vo=null;
	        rs=pstmt.executeQuery();
	        resultList=new ArrayList<XinshuiAgainstVO>();
	     while(rs.next()){
	    	vo=new XinshuiAgainstVO();
	    	String raceName=rs.getString("RACE_NAME");
	    	vo.setRaceName(raceName);
	    	String startTime=rs.getString("START_TIME");
	    	vo.setStartTime(startTime);
	    	String hostName=rs.getString("HOST_NAME");
	    	vo.setHostName(hostName);
	    	String guestName=rs.getString("GUEST_NAME");
	    	vo.setGuestName(guestName);
	    	String deadline=rs.getString("DEADLINE");
	    	vo.setDeadline(deadline);
	    	//亚盘  大小盘  数据从何处而来?
	    	String asiaPei=rs.getString("ASIA_PEI");
	    	vo.setAsiaPei(asiaPei);
	    	String bigSmall=rs.getString("BIG_SMALL");
	    	vo.setBigSmall(bigSmall);
	    	vo.setAgainstId(rs.getLong("AGAINST_ID"));
	    	resultList.add(vo);
	     }
		 }catch(Exception e){
			 
		 }finally{
			 closeAll(rs,pstmt,conn);
		 }
	    return resultList;
	
	}
	/**获得b2c下拉框的联赛名称下列列表  2010-03-14 14:18**/
	public List<Race> findB2CRaceList() throws DaoException {
		
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 List<Race> resultList=null;
		 try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		     StringBuilder sql=new StringBuilder(" select t.RACE_NAME, t.RACE_ID   from v_findAgainstList t");
		     pstmt=conn.prepareStatement(sql.toString());
	         rs=pstmt.executeQuery();
	         resultList=new ArrayList<Race>();
	         Race po=null;
	       while(rs.next()){
	    	po=new Race();
	    	String raceName=rs.getString("RACE_NAME");
	    	po.setName(raceName);
	    	Long rsceId=rs.getLong("RACE_ID");
	    	po.setId(rsceId);
	    	
	    	resultList.add(po);
	     }
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 closeAll(rs,pstmt,conn);
		 }
	    return resultList;
	
	}
	public B2CProductVO  loadB2CProduct(Long againstId,Long expertUserId){
		     StringBuilder sb=new StringBuilder("select  a.RACE_NAME, to_char(a.START_TIME,'yy-mm-dd hh24:mi') START_TIME,a.HOST_NAME,a.GUEST_NAME,to_char(a.start_Time-to_number(( select t.value  from t_dictionary  t where t.code='DEADLINE' and t.type='XINSHUI'))/(24*60),'yyyy-mm-dd hh24:mi')  DEADLINE,");
		     sb.append(" (select t.img  from T_TEAM t where  t.ID=a.HOST_ID) host_img,(select t.img  from T_TEAM t where  t.ID=a.GUEST_ID) guest_Img ");
		     sb.append(",RACE_ID,b.phase   from T_AGAINST a,T_XINSHUI_AGAINST b ");
		     sb.append("  where a.AGAINST_ID=b.AGAINST_ID");
		     sb.append(" and b.AGAINST_ID="+againstId);
		     Connection conn=null;
			 PreparedStatement pstmt=null;
			 ResultSet rs=null;
			 B2CProductVO po=null;
			 try{
				conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			    pstmt=conn.prepareStatement(sb.toString());
		        rs=pstmt.executeQuery();
		        while(rs.next()){
		    	po=new B2CProductVO();
		    	String raceName=rs.getString("RACE_NAME");
		    	po.setRaceName(raceName);
		    	
		    	String hostName=rs.getString("HOST_NAME");
		    	po.setHostName(hostName);
		    	String guestName=rs.getString("GUEST_NAME");
		    	po.setGuestName(guestName);
		    	String startTime=rs.getString("START_TIME");
		    	po.setStartTime(startTime);
		     }
		     
			 }catch(Exception e){
				 e.printStackTrace();
			 }finally{
				 closeAll(rs,pstmt,conn);
			 }
			  return po;
	}
	
	private static final Log log=LogFactory.getLog(B2CDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
}
