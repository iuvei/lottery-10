package com.wintv.lottery.xinshui.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.C2CProduct;
import com.wintv.lottery.xinshui.dao.C2CProductDao;
import com.wintv.lottery.xinshui.vo.Top10Vo;
import com.wintv.lottery.xinshui.vo.XinshuiOrderVO;
import com.wintv.lottery.xinshui.vo.XinshuiSearchVO;

@Repository("c2CProductDao")
@SuppressWarnings("unchecked")
public class C2CProductDaoImpl extends BaseHibernate<C2CProduct,Long> implements C2CProductDao {
	private static final Log log=LogFactory.getLog(C2CProductDaoImpl.class);
	/**
	 * 
	*把心水赛事里面的对阵取消，不让它作为心水的对阵
	 */
	public boolean updateXinshuiAgainst(Long againstId)throws DaoException{
		return this.updateBySql("update  T_XINSHUI_AGAINST  t  set t.PUB_STATUS='3'  where  t.AGAINST_ID="+againstId);
	}
	public BigDecimal[]  findPayInfo(Long orderId,Long userId)throws DaoException{
		StringBuilder sb=new StringBuilder("select  a.tx_money+a.tx_caijin money, b.frozen_money+b.frozen_mosaic_goldmoney  alreadyPay ");
		sb.append(" from T_XINSHUI_ORDER  a,T_VA_FROZEN_LOG b  where  a.XINSHUI_ORDER_ID=b.order_id and b.category_type='11' ");
		sb.append(" and b.order_id="+orderId+" and b.TX_USER_ID="+userId);
		SQLQuery  q=this.getSession().createSQLQuery(sb.toString());
		
		Object[] o=(Object[])q.uniqueResult();
		BigDecimal money=(BigDecimal)o[0];
		BigDecimal alreadyPay=(BigDecimal)o[1];
		BigDecimal[]  arr=new BigDecimal[2];
		arr[0]=money;
		arr[1]=alreadyPay;
	  return arr;
	}
	public int              findC2CProductSize(Map params)throws DaoException{
		   Long raceId=(Long)params.get("raceId");
		   String status=(String)params.get("status");
		   String orderNo=(String)params.get("orderNo");
		   String xinshuiNo=(String)params.get("xinshuiNo");
		   String txUsername=(String)params.get("txUsername");
		   String team=(String)params.get("team");
		   Long currentUserId=(Long)params.get("currentUserId");//当前用户的用户ID
		   StringBuilder sb=new StringBuilder("select count(d.C2C_ID) cnt ");
			  sb.append(" from T_AGAINST a,T_XINSHUI_AGAINST b ,T_C2C_PRODUCT d");
		      sb.append("  where a.AGAINST_ID=b.AGAINST_ID  and  d.AGAINST_ID=b.AGAINST_ID and d.TX_USER_ID="+currentUserId);
		      if(raceId!=null){
		    	  sb.append(" and a.RACE_ID="+raceId);
		      }
		      if(StringUtils.isNotEmpty(status)){
		    	  sb.append(" and d.STATUS="+status);
		      }
		      if(StringUtils.isNotEmpty(orderNo)){
		    	  sb.append(" and c.ORDER_NO like '%"+orderNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(xinshuiNo)){
		    	  sb.append(" and d.XINSHUI_NO like '%"+xinshuiNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(txUsername)){
		    	  sb.append(" and d.TX_USERNAME like '%"+txUsername+"%'");
		      }
		      if(StringUtils.isNotEmpty(team)){
		    	  sb.append(" and ( a.HOST_NAME like '%"+team+"%'   or   a.GUEST_NAME like '%"+team+"%') ");
		      }
		      String fromDay=(String)params.get("fromDay") ;
		      if(fromDay!=null){
		    	  sb.append(" and  to_char(d.PUB_TIME,'yyyy-mm-dd') >='"+fromDay+"'");
		      }
		      String toDay=(String)params.get("toDay") ;
		      if(toDay!=null){
		    	  sb.append(" and to_char(d.PUB_TIME,'yyyy-mm-dd') <='"+toDay+"'");
		      }
		      
		      String minEnsureMoney=(String)params.get("minEnsureMoney");
		      String maxEnsureMoney=(String)params.get("maxEnsureMoney");
		      if(minEnsureMoney!=null){
		    	  sb.append(" and  d.ENSURE_MONEY >=to_number('"+minEnsureMoney+"') ");
		      }
		      if(maxEnsureMoney!=null){
		    	  sb.append(" and d.ENSURE_MONEY  <=to_char('"+maxEnsureMoney+"') ");
		      }
		      String minPrice=(String)params.get("minPrice");
		      if(minPrice!=null){
		    	  sb.append(" and d.PRICE >= to_number('"+minPrice+"') ");
		      }
		      String maxPrice=(String)params.get("maxPrice");
		      if(maxPrice!=null){
		    	  sb.append(" and d.PRICE <= to_number('"+maxPrice+"') ");
		      }
		      String fromTime=(String)params.get("fromTime");
		      String toTime=(String)params.get("toTime");
		      if(StringUtils.isNotEmpty(fromTime)){
		    	  sb.append(" and  to_char(a.START_TIME,'yyyy-mm-dd') >='"+fromTime+"'");
		      }
		      if(StringUtils.isNotEmpty(toTime)){
		    	  sb.append(" and  to_char(a.START_TIME,'yyyy-mm-dd') <='"+toTime+"'");
		      }
		      
		   try{
		   SQLQuery q=this.getSession().createSQLQuery(sb.toString());
		   Integer cnt= (Integer)q.addScalar("cnt",Hibernate.INTEGER).uniqueResult();
		     return cnt.intValue();
		   }catch(Exception e){
		    	throw new DaoException(e.getLocalizedMessage());
		   }
	}
	 /**【民间高手心水管理—发布管理】
	    * 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT,
	    * 参数：
	    *  raceId:   赛事名称 T_AGAINST.RACE_ID
	    *  startRow：起始记录数
	    *  endRow：  结束记录数
	    * 返回:
	    *  List<C2CProduct>
	    */
	   public List<C2CProduct> findC2CProductList(Map params, int startRow, int pageSize)throws DaoException{
		   List resultList=null;
		   Long raceId=(Long)params.get("raceId");
		   String status=(String)params.get("status");
		   String orderNo=(String)params.get("orderNo");
		   String xinshuiNo=(String)params.get("xinshuiNo");
		   String txUsername=(String)params.get("txUsername");
		   String team=(String)params.get("team");
		   String type=(String)params.get("type");
		   Long currentUserId=(Long)params.get("currentUserId");//当前用户的用户ID
		   
		   StringBuilder sb=new StringBuilder("select  d.XINSHUI_NO,d.TX_USERNAME,a.HOST_NAME,a.GUEST_NAME,a.RACE_NAME,to_char(a.START_TIME,'yy-mm-dd hh24:mi') START_TIME,");
			  sb.append(" to_char(d.ENSURE_MONEY,'99999999.99'),to_char(d.PRICE,'9999999.99') PRICE,d.TYPE,(select count(1) from T_XINSHUI_ORDER log  where log.SOLD_USER_ID="+currentUserId+" and log.product_id=d.c2c_id)  buy_cnt, ");
			  sb.append("  to_char(d.PUB_TIME,'yy-mm-dd hh24:mi') PUB_TIME,decode(d.STATUS,'1','发布中','2','赢','3','负','4','走','5','已关闭') STATUS,d.C2C_ID ");
		      sb.append(" from T_AGAINST a,T_XINSHUI_AGAINST b ,T_C2C_PRODUCT d");
		      sb.append("  where a.AGAINST_ID=b.AGAINST_ID  and  d.AGAINST_ID=b.AGAINST_ID  and d.TX_USER_ID="+currentUserId);
		      if(raceId!=null){
		    	  sb.append(" and a.RACE_ID="+raceId);
		      }
		      if(StringUtils.isNotEmpty(status)){
		    	  sb.append(" and d.STATUS="+status);
		      }
		      if(StringUtils.isNotEmpty(orderNo)){
		    	  sb.append(" and c.ORDER_NO like '%"+orderNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(xinshuiNo)){
		    	  sb.append(" and d.XINSHUI_NO like '%"+xinshuiNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(txUsername)){
		    	  sb.append(" and d.TX_USERNAME like '%"+txUsername+"%'");
		      }
		      if(StringUtils.isNotEmpty(team)){
		    	  sb.append(" and ( a.HOST_NAME like '%"+team+"%'   or   a.GUEST_NAME like '%"+team+"%' ) ");
		      }
		      String fromDay=(String)params.get("fromDay") ;
		      if(fromDay!=null){
		    	  sb.append(" and  to_char(d.PUB_TIME,'yyyy-mm-dd') >='"+fromDay+"'");
		      }
		      String toDay=(String)params.get("toDay") ;
		      if(toDay!=null){
		    	  sb.append(" and  to_char(d.PUB_TIME,'yyyy-mm-dd') <='"+toDay+"'");
		      }
		      if(StringUtils.isNotEmpty(type)){
		    	  sb.append(" and d.TYPE='"+type+"'");
		      }
		      
		      String minEnsureMoney=(String)params.get("minEnsureMoney");
		      String maxEnsureMoney=(String)params.get("maxEnsureMoney");
		      if(minEnsureMoney!=null){
		    	  sb.append(" and d.ENSURE_MONEY >= to_number('"+minEnsureMoney+"') ");
		      }
		      if(maxEnsureMoney!=null){
		    	  sb.append(" and d.ENSURE_MONEY  <= to_number('"+maxEnsureMoney+"') ");
		      }
		      String minPrice=(String)params.get("minPrice");
		      if(minPrice!=null){
		    	  sb.append(" and d.PRICE >= to_number('"+minPrice+"') ");
		      }
		      String maxPrice=(String)params.get("maxPrice");
		      if(maxPrice!=null){
		    	  sb.append(" and d.PRICE <= to_number('"+maxPrice+"') ");
		      }
		      String fromTime=(String)params.get("fromTime");
		      String toTime=(String)params.get("toTime");
		      if(StringUtils.isNotEmpty(fromTime)){
		    	  sb.append(" and  to_char(a.START_TIME,'yyyy-mm-dd') >='"+fromTime+"'");
		      }
		      if(StringUtils.isNotEmpty(toTime)){
		    	  sb.append(" and  to_char(a.START_TIME,'yyyy-mm-dd') <='"+toTime+"'");
		      }	
		      String flg=(String)params.get("flg");
		      String sortType=(String)params.get("sortType");
		      if("1".equals(flg)&&StringUtils.isNotEmpty(sortType)){
		    	  sb.append("  order by a.START_TIME "+sortType);
		      }else if("2".equals(flg)&&StringUtils.isNotEmpty(sortType)){
		    	  sb.append("  order by d.ENSURE_MONEY "+sortType);
		      }else if("3".equals(flg)&&StringUtils.isNotEmpty(sortType)){
		    	  sb.append("  order by d.PRICE "+sortType);
		      }else if("4".equals(flg)&&StringUtils.isNotEmpty(sortType)){
		    	  sb.append("  order by buy_cnt "+sortType);
		      }else if("5".equals(flg)&&StringUtils.isNotEmpty(sortType)){
		    	  sb.append("  order by d.PUB_TIME "+sortType);
		      }
		   try{
		   log.info("测试="+sb.toString());
		   SQLQuery query=this.getSession().createSQLQuery(sb.toString());
		   
		   query.setFirstResult(startRow);
		   query.setMaxResults(pageSize);
		   List list=query.list();
		   XinshuiOrderVO vo=null;
		   resultList=new ArrayList();
		   for(Iterator e=list.iterator();e.hasNext();){
			   vo=new XinshuiOrderVO();
			   Object[] arr=(Object[])e.next();
			   vo.setXinshuiNO((String)arr[0]);//心水编号
			   vo.setSoldUsername((String)arr[1]);//发布人
			   vo.setHostName((String)arr[2]);
			   vo.setGuestName((String)arr[3]);
			   vo.setRaceName((String)arr[4]);
			  
			   String startTime=(String)arr[5];
			   vo.setStartTime(startTime);
			  
			   String ensureMoney=(String)arr[6];
			   vo.setEnsureMoney(ensureMoney);
			   String price=(String)arr[7];
			   vo.setPrice(price.toString());
			   
			   vo.setType(arr[8].toString());
			   //buy_cnt
			   BigDecimal buyCnt=(BigDecimal)arr[9];
			   if(buyCnt!=null){
			     vo.setBuyCnt(buyCnt.longValue());
			   }else{
				   vo.setBuyCnt(0L);
			   }
			   vo.setPubTime((String)arr[10]);
			   
			   status=(String)arr[11];
			   vo.setStatus(status);
			   vo.setXinshuiId(new Long(arr[12].toString()));
			   resultList.add(vo);
		   }
	    }catch(Exception e){
	    	e.printStackTrace();
	    	//throw new DaoException(e.getLocalizedMessage());
	    }
	    return resultList;
	   }
	   /**【民间高手心水管理—销售订单查看】
	    * 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT,
	    * 参数：
	    *  raceId:   赛事名称 T_AGAINST.RACE_ID
	    *  startRow：起始记录数
	    *  endRow：  结束记录数
	    * 返回:
	    *  List<C2CProduct>
	    */
	   public List<XinshuiOrderVO> findSoldOrderList(Map params, int startRow, int pageSize)throws DaoException{
		   List resultList=null;
		   Long raceId=(Long)params.get("raceId");
		   Long c2cId=(Long)params.get("c2cId");
		   String payStatus=(String)params.get("payStatus");
		   String orderNo=(String)params.get("orderNo");
		   String xinshuiNo=(String)params.get("xinshuiNo");
		   String buyUsername=(String)params.get("buyUsername");//购买人用户名
		   String team=(String)params.get("team");
		   String type=(String)params.get("type");
		   Long currentUserId=(Long)params.get("currentUserId");//当前用户的用户ID
		   StringBuilder sb=new StringBuilder("select c.ORDER_NO,c.BUY_USERNAME , d.XINSHUI_NO,c.SOLD_USERNAME,a.HOST_NAME,a.GUEST_NAME,a.RACE_NAME,to_char(a.START_TIME,'yy-mm-dd hh24:mi') START_TIME,");
			  sb.append(" to_char(d.ENSURE_MONEY,'99999999.99') ENSURE_MONEY,to_char(d.PRICE,'9999999.99') PRICE,d.TYPE, ");
			  sb.append("  to_char(c.BUY_TIME,'yy-mm-dd hh24:mi') ORDER_TIME,c.PAY_STATUS,d.C2C_ID ");
		      sb.append(" from T_AGAINST a,T_XINSHUI_AGAINST b ,T_XINSHUI_ORDER c,T_C2C_PRODUCT d ");
		      sb.append("  where a.AGAINST_ID=b.AGAINST_ID  and  d.AGAINST_ID=b.AGAINST_ID  and c.PRODUCT_ID=d.C2C_ID  and c.SOLD_USER_ID="+currentUserId);//销售人ID为当前用户ID
		      if(raceId!=null){
		    	  sb.append(" and a.RACE_ID="+raceId);
		      }
		      if(c2cId!=null){
		    	  sb.append(" and d.C2C_ID="+c2cId);
		      }
		      if(StringUtils.isNotEmpty(type)){
		    	  sb.append(" and d.TYPE='"+type+"'");
		      }
		      if(StringUtils.isNotEmpty(payStatus)){
		    	  sb.append(" and c.PAY_STATUS='"+payStatus+"'");
		      }
		      //
		      
		      String fromStartTime=(String)params.get("fromStartTime");
		      if(StringUtils.isNotEmpty(fromStartTime)){
		    	  sb.append(" and to_char(a.START_TIME,'yyyy-mm-dd')>='"+fromStartTime+"' ");
		      }
		      String toStartTime=(String)params.get("toStartTime");
		      if(StringUtils.isNotEmpty(toStartTime)){
		    	  sb.append(" and to_char(a.START_TIME,'yyyy-mm-dd')<='"+toStartTime+"' ");
		      }
		      
		      //
		      String fromOrderTime=(String)params.get("fromOrderTime");
		      if(StringUtils.isNotEmpty(fromOrderTime)){
		    	  sb.append(" and to_char(c.BUY_TIME,'yyyy-mm-dd')>='"+fromOrderTime+"' ");
		      }
		      String toOrderTime=(String)params.get("toOrderTime");
		      if(StringUtils.isNotEmpty(toOrderTime)){
		    	  sb.append(" and to_char(c.BUY_TIME,'yyyy-mm-dd')<='"+toOrderTime+"' ");
		      }
		      if(StringUtils.isNotEmpty(orderNo)){
		    	  sb.append(" and c.ORDER_NO like '%"+orderNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(xinshuiNo)){
		    	  sb.append(" and d.XINSHUI_NO like '%"+xinshuiNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(buyUsername)){
		    	  sb.append(" and c.BUY_USERNAME like '%"+buyUsername+"%'");
		      }
		      if(StringUtils.isNotEmpty(team)){
		    	  sb.append(" and  (a.HOST_NAME like '%"+team+"%'   or   a.GUEST_NAME like '%"+team+"%') ");
		      }
		      String timeFlg=(String)params.get("timeFlg");//1.开赛时间  2.订购时间
		      String fromDay=(String)params.get("fromDay") ;
		      if(fromDay!=null){
		    	  if("1".equals(timeFlg)){//1.开赛时间
		    		  sb.append(" and  to_char(a.START_TIME,'yyyy-mm-dd') >='"+fromDay+"'");
		    	  }else if("2".equals(timeFlg)){//2.订购时间
		    		  sb.append(" and  to_char(c.BUY_TIME,'yyyy-mm-dd') >='"+fromDay+"'");
		    	  }
		      }
		      String toDay=(String)params.get("toDay") ;
		      if(toDay!=null){
		    	  if("1".equals(timeFlg)){//1.开赛时间
		    		  sb.append(" and  to_char(a.START_TIME,'yyyy-mm-dd') >='"+fromDay+"'");
		    	  }else if("2".equals(timeFlg)){//2.订购时间
		    	    sb.append(" and  to_char(c.BUY_TIME,'yyyy-mm-dd') <='"+toDay+"'");
		    	  }
		      }
		   try{
		   log.info("*********"+sb.toString());
		   SQLQuery query=this.getSession().createSQLQuery(sb.toString());
		   query.setFirstResult(startRow);
		   query.setMaxResults(pageSize);
		   List list=query.list();
		   XinshuiOrderVO vo=null;
		   resultList=new ArrayList();
		   for(Iterator e=list.iterator();e.hasNext();){
			   vo=new XinshuiOrderVO();
			   Object[] arr=(Object[])e.next();
			   vo.setOrderNo((String)arr[0]);
			   vo.setOrderUsername((String)arr[1]);
			   vo.setXinshuiNO((String)arr[2]);//心水编号
			   vo.setSoldUsername((String)arr[3]);//发布人
			   vo.setHostName((String)arr[4]);
			   vo.setGuestName((String)arr[5]);
			   vo.setRaceName((String)arr[6]);
			   String startTime=(String)arr[7];
			   vo.setStartTime(startTime);
			   String ensureMoney=(String)arr[8];
			   vo.setEnsureMoney(ensureMoney.trim());
			   String price=(String)arr[9];
			   vo.setPrice(price.trim());
			   vo.setType(arr[10].toString());
			   String orderTime=(String)arr[11];
			   vo.setOrderTime(orderTime);
			   
			   Character  _payStatus=(Character)arr[12];
			   vo.setPayStatus(_payStatus.toString());
			   vo.setXinshuiId(((BigDecimal)arr[13]).longValue());
			   resultList.add(vo);
		   }
	    }catch(Exception e){
	    	e.printStackTrace();
	    	//throw new DaoException(e.getLocalizedMessage());
	    }
	    return resultList;
	   }
	   /**【民间高手心水管理—销售订单记录数统计】
	    * 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT,
	    * 参数：
	    *  raceId:   赛事名称 T_AGAINST.RACE_ID
	    *  startRow：起始记录数
	    *  endRow：  结束记录数
	    * 返回:
	    *  List<C2CProduct>
	    */
	   public int findSoldOrderSize(Map params)throws DaoException{
		   Long raceId=(Long)params.get("raceId");
		   Long c2cId=(Long)params.get("c2cId");
		   String payStatus=(String)params.get("payStatus");
		   String orderNo=(String)params.get("orderNo");
		   String xinshuiNo=(String)params.get("xinshuiNo");
		   String buyUsername=(String)params.get("buyUsername");//购买人用户名
		   String team=(String)params.get("team");
		   Long currentUserId=(Long)params.get("currentUserId");//当前用户的用户ID
		   String type=(String)params.get("type");
		   StringBuilder sb=new StringBuilder("select count(c.XINSHUI_ORDER_ID) cnt ");
			  sb.append(" from T_AGAINST a,T_XINSHUI_AGAINST b ,T_XINSHUI_ORDER c,T_C2C_PRODUCT d ");
		      sb.append("  where a.AGAINST_ID=b.AGAINST_ID  and  d.AGAINST_ID=b.AGAINST_ID  and c.PRODUCT_ID=d.C2C_ID  and c.SOLD_USER_ID="+currentUserId);//销售人ID为当前用户ID
		      if(raceId!=null){
		    	  sb.append(" and a.RACE_ID="+raceId);
		      }
		      if(c2cId!=null){
		    	  sb.append(" and d.C2C_ID="+c2cId);
		      }
		      if(StringUtils.isNotEmpty(type)){
		    	  sb.append(" and d.TYPE='"+type+"'");
		      }
		      if(StringUtils.isNotEmpty(payStatus)){
		    	  sb.append(" and c.PAY_STATUS='"+payStatus+"'");
		      }
		      if(StringUtils.isNotEmpty(orderNo)){
		    	  sb.append(" and c.ORDER_NO like '%"+orderNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(xinshuiNo)){
		    	  sb.append(" and d.XINSHUI_NO like '%"+xinshuiNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(buyUsername)){
		    	  sb.append(" and c.BUY_USERNAME like '%"+buyUsername+"%'");
		      }
		      if(StringUtils.isNotEmpty(team)){
		    	  sb.append(" and ( a.HOST_NAME like '%"+team+"%'   or   a.GUEST_NAME like '%"+team+"%') ");
		      }
		      String timeFlg=(String)params.get("timeFlg");//1.开赛时间  2.订购时间
		      String fromDay=(String)params.get("fromDay") ;
		      if(fromDay!=null){
		    	  if("1".equals(timeFlg)){//1.开赛时间
		    		  sb.append(" and  to_char(a.START_TIME,'yyyy-mm-dd') >='"+fromDay+"'");
		    	  }else if("2".equals(timeFlg)){//2.订购时间
		    		  sb.append(" and  to_char(c.BUY_TIME,'yyyy-mm-dd') >='"+fromDay+"'");
		    	  }
		      }
		      String toDay=(String)params.get("toDay") ;
		      if(toDay!=null){
		    	  if("1".equals(timeFlg)){//1.开赛时间
		    		  sb.append(" and  to_char(a.START_TIME,'yyyy-mm-dd') >='"+fromDay+"'");
		    	  }else if("2".equals(timeFlg)){//2.订购时间
		    	    sb.append(" and  to_char(c.BUY_TIME,'yyyy-mm-dd') <='"+toDay+"'");
		    	  }
		      }
		   try{
		      SQLQuery q=this.getSession().createSQLQuery(sb.toString());
		      Integer cnt= (Integer)q.addScalar("cnt",Hibernate.INTEGER).uniqueResult();
		     return cnt.intValue();
		   }catch(Exception e){
			   throw new DaoException(e.getLocalizedMessage());
		   }
	   }
	   /**
		 * 【客服】选择从对阵表T_AGAINST里面 挑选若干场对阵，生成心水对阵列表
		 * 返回"10"表示不能再当天不能再生成心水赛事
		 * @param scheduleIds
		 * @throws DaoException
		 */
	    public String genXinshuiAgainst(String againstIds)throws DaoException{
	    	Connection conn=null;
	    	CallableStatement cstmt =null;
			try { 
				  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
				  cstmt = conn.prepareCall("{call XINSHUI.genXinshuiAgainst(?,?)}"); 
				  cstmt.setString(1,againstIds);
				  cstmt.registerOutParameter(2, OracleTypes.VARCHAR);   
				  cstmt.execute();
				 return cstmt.getString(2);
			} catch (SQLException e) { 
				  throw new DaoException(e.getLocalizedMessage());
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
	    }
	    public List findXinshuiList(Map params)throws DaoException{
	    	int startRow=(Integer)params.get("startRow");
	    	int pageSize=(Integer)params.get("pageSize");
	    	String queryField=(String)params.get("queryField");
	    	String queryFieldValue=(String)params.get("queryFieldValue");
	    	Long raceId=(Long)params.get("raceId");
	    	StringBuilder sql=new StringBuilder("select a.RACE_NAME, a.TYPE,to_char(b.START_TIME,'yyyy-mm-dd hh24:mi') START_TIME,b.HOST_NAME,b.GUEST_NAME,a.TX_USERNAME,a.ENSURE_MONEY ");
	    	
	    	sql.append(" ,a.PRICE ,");
	    	sql.append(" (select count(1)  from   t_c2c_product X where  X.status='2' and X.Tx_User_Id=a.TX_USER_ID and X.pub_time >=(select add_months(sysdate,-1) from dual)) win,");
	    	sql.append(" (select count(Y.C2C_ID)  from   t_c2c_product Y where  (Y.status='2' or Y.status='3')  and Y.Tx_User_Id=a.TX_USER_ID and Y.pub_time >=(select add_months(sysdate,-1) from dual)) allWin  ,");
	    	sql.append(" (select count(*) from T_XINSHUI_ORDER  xo  where  xo.SOLD_USER_ID=a.tx_user_id and xo.product_id=a.c2c_id ) sold_cnt  ");
	    	sql.append(" ,c.XINSHUI_MILITARY,a.C2C_ID,a.AINDEX,a.BINDEX,a.PAN_KOU_INDEX  from  T_C2C_PRODUCT a,T_AGAINST b,T_USER c where a.AGAINST_ID=b.AGAINST_ID and  c.USERID=a.TX_USER_ID ");
            if(StringUtils.isNotEmpty(queryField)){
		    	if("username".equals(queryField)){
	            	sql.append(" and  c.USERNAME like '%"+queryFieldValue+"%'");
	            }else  if("team".equals(queryField)){
	            	sql.append(" and  (a.HOST_NAME like '%"+queryFieldValue+"%'  or  a.GUEST_NAME like '%"+queryFieldValue+"%')");
	            }else  if("raceName".equals(queryField)){
	            	sql.append(" and  a.RACE_NAME like '%"+queryFieldValue+"%'");
	            	
	            /********专家推荐首页新加代码  Add By Hikin Yao 2010-04-15*******/
	            }else  if("allQueryField".equals(queryField)){//不指定关键字字段时查询 
	            	sql.append(" and ( c.USERNAME like '%"+queryFieldValue+"%' ");
	            	sql.append(" or  a.HOST_NAME like '%"+queryFieldValue+"%'  or  a.GUEST_NAME like '%"+queryFieldValue+"%'");
	            	sql.append(" or  a.RACE_NAME like '%"+queryFieldValue+"%' )");
	            }
            }
            String startTimefrom=(String)params.get("startTimefrom");//开赛时间 查询开始时间
            String startTimeto=(String)params.get("startTimeto");//开赛时间 查询结束时间
            log.info("hikin=  startTimefrom="+startTimefrom+"---- startTimeto="+startTimeto);
            if (StringUtils.isNotBlank(startTimefrom)) {
            	sql.append(" and ( b.START_TIME >= to_date('"+ startTimefrom+ "','yyyy-MM-dd HH24:mi:ss') )");
    		}
    		if (StringUtils.isNotBlank(startTimeto)) {
    			sql.append(" and ( b.START_TIME <= to_date('"+ startTimeto + "','yyyy-MM-dd HH24:mi:ss') )");
    		}
    		/**********专家推荐首页新加代码 结束*******/
    		
            if(raceId!=null){
            	sql.append("  and  b.RACE_ID="+raceId);
            }
            sql.append(" and a.STATUS='1'");
            log.info("&&="+sql.toString());
            SQLQuery q=this.getSession().createSQLQuery(sql.toString());
            q.setFirstResult(startRow);
            q.setMaxResults(pageSize);
            List<Object[]> list= q.list();
	        XinshuiSearchVO po=null;
	       
	        List result=new ArrayList();
	        for(Object[] arr:list){
	        	po=new XinshuiSearchVO();
	        	String raceName=arr[0].toString();
	        	po.setRaceName(raceName);
	        	String  type=arr[1].toString();
	        	po.setType(type);
	        	String startTime=arr[2].toString();
	        	po.setStartTime(startTime);
	        	String hostName=arr[3].toString();
	        	po.setHostName(hostName);
	        	String guestName=arr[4].toString();
	        	po.setGuestName(guestName);
	        	String txUsername=arr[5].toString();
	        	po.setUsername(txUsername);
	        	BigDecimal ensureMoney=(BigDecimal)arr[6];
	            po.setEnsureMoney(ensureMoney);
	        	BigDecimal price=(BigDecimal)arr[7];
	        	po.setPrice(price);
	        	int win=Integer.parseInt(arr[8].toString());
	        	int all=Integer.parseInt(arr[9].toString());
	        	if(all!=0){
	        		double ratio=win/all;
	        		BigDecimal winRatio = new BigDecimal(ratio).multiply(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_HALF_UP);
	                po.setWinRatio(winRatio.toString()+"%");

	            }else{
	               po.setWinRatio("0.0%");
	            }

	        	Long soldCnt=new Long(arr[10].toString());
	        	po.setCnt(soldCnt);
	        	int xinshuiMilitary=arr[11]!=null?Integer.parseInt(arr[11].toString()):0;
	        	po.setXinshuiMilitary(xinshuiMilitary);
	        	po.setC2cId(new Long(arr[12].toString()));
	        	String aindex=arr[13]==null?"":arr[13].toString();
	        	po.setAindex(aindex);
	        	String bindex=arr[14]==null?"":arr[14].toString();
	        	po.setBindex(bindex);
	        	String panKouIndex=arr[15]==null?"":arr[15].toString();
	        	po.setPanKouIndex(panKouIndex);
	        	result.add(po);
	        	
	        }
	        return result;
	    	
	    }
	    public int findXinshuiSize(Map params)throws DaoException{
	    	String queryField=(String)params.get("queryField");
	    	String queryFieldValue=(String)params.get("queryFieldValue");
	    	Long raceId=(Long)params.get("raceId");
	    	StringBuilder sql=new StringBuilder("select count(1)  cnt ");
	        sql.append(" from  T_C2C_PRODUCT a,T_AGAINST b where a.AGAINST_ID=b.AGAINST_ID ");
	        if("username".equals(queryField)){
            	sql.append(" and  a.TX_USERNAME like '%"+queryFieldValue+"%'");
            }else  if("team".equals(queryField)){
            	sql.append(" and  (a.HOST_NAME like '%"+queryFieldValue+"%'  or  a.GUEST_NAME like '%"+queryFieldValue+"%')");
            }else  if("raceName".equals(queryField)){
            	sql.append(" and  a.RACE_NAME like '%"+queryFieldValue+"%'");
            }
	        if(raceId!=null){
            	sql.append("  and  b.RACE_ID="+raceId);
            }
	        sql.append(" and a.STATUS='1'");
            log.info(sql.toString());
            SQLQuery q=this.getSession().createSQLQuery(sql.toString());
            q.addScalar("cnt", Hibernate.INTEGER);
            return (Integer)q.uniqueResult();
	    	
	    }
	    /**
	     * 判断用户是否已经购买了本心水
	     *参数:
	     *  buyUserId:当前用户ID
	     *  c2cId:心水ID
	     * 返回值：
	     *  true:表示现在可以购买该心水
	     *  false:禁止购买
	     */
	    public boolean  isAlreadyBuy(Map params)throws DaoException{
	    	Long buyUserId=(Long)params.get("buyUserId");
	    	Long c2cId=(Long)params.get("c2cId");
	    	log.info("buyUserId="+buyUserId);
	    	log.info("c2cId="+c2cId);
	    	StringBuilder sql=new StringBuilder("select  SOLD_USER_ID  id from T_XINSHUI_ORDER  a where a.BUY_USER_ID=? and a.PRODUCT_ID=?");
            sql.append(" union all select  b.c2c_id  id from T_C2C_PRODUCT  b where  b.Tx_User_Id=? and b.c2c_id=?");
	    	SQLQuery q=this.getSession().createSQLQuery(sql.toString());
	    	q.setLong(0, buyUserId);
	        q.setLong(1, c2cId);
	        q.setLong(2, buyUserId);
	        q.setLong(3, c2cId);
	    	List list=q.list();
	    	return list==null||list.size()==0;
	    }
	    
	   
}

