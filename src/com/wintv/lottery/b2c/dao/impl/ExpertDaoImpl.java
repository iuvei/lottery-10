package com.wintv.lottery.b2c.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wintv.lottery.b2c.vo.ExpertVO;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.OracleResultSet;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.lottery.b2c.vo.ExpertVO;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Expert;
import com.wintv.lottery.attention.vo.AttentionPlanVO;
import com.wintv.lottery.b2c.dao.ExpertDao;
import com.wintv.lottery.b2c.vo.B2COrderVO;
import com.wintv.lottery.b2c.vo.B2CProductVO;
/**
 * 2010-03-15 09:47
 *
 *
 */
@Repository("expertDao")
public class ExpertDaoImpl extends BaseHibernate<Expert,Long> implements ExpertDao{
	
	public long saveExpert(ExpertVO po)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{call B2C.saveB2CExcpert(?,?,?,?,?,?,?,?,?,?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setString(1,po.getIntroduction());
	      proc.setString(2,po.getContractTime());
	      proc.setString(3,po.getExpertName());
	      proc.setLong(4,new Long(po.getWeekPack()));
	      proc.setLong(5,new Long(po.getMonthPack()));
	      proc.setLong(6,new Long(po.getSeasonPack()));
	      proc.setLong(7,new Long(po.getYearPack()));
	      proc.setString(8,po.getExpertClass());
	      proc.setString(9,po.getPhoto());
	      proc.setString(10,po.getIsValuePack());
	      proc.setString(11,po.getStatus());
	      proc.registerOutParameter(12,oracle.jdbc.OracleTypes.NUMBER);
	      proc.execute();
	      long expertId=proc.getLong(12);
	     return  expertId;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(proc,conn);
	    }
	    return 0;
	
	}
	/**专家首页  2010-04-15 11:01**/
	public Map loadExpertHomeData(Long expertId)throws DaoException{
        Connection conn=null;
        CallableStatement proc=null;
        ResultSet rs=null;
        Map resultMap=null;
        try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql = "{call B2C.loadExpertHomeData(?,?,?,?,?,?,?,?,?)}";
	      proc   =(CallableStatement)conn.prepareCall(sql);    
	      proc.setLong(1, expertId);
	      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.CLOB);
	      proc.registerOutParameter(3,oracle.jdbc.OracleTypes.NUMBER);
	      proc.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(5,oracle.jdbc.OracleTypes.CURSOR);
	      proc.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
	      proc.execute();
	      resultMap=new HashMap();
	      String introduction=proc.getString(2);//专家介绍 
	      long ncCnt=proc.getLong(3);//内参总数
	      ExpertVO expert=new ExpertVO();
	      expert.setIntroduction(introduction);
	      expert.setNcCnt(ncCnt);
	      String pack=proc.getString(4);
	      System.out.println("pack="+pack);
	      String[] packs=pack.split(",");
	      if(packs!=null&&packs.length>0){
	      String weekPack=packs[0];//包周价格 
	      if(StringUtils.isNotEmpty(weekPack)){
	    	  expert.setWeekPack(weekPack);
	      }
	      String monthPack=packs[1];//包月价格
	      if(StringUtils.isNotEmpty(monthPack)){
	    	  expert.setMonthPack(monthPack);
	      }
	      String seasonPack=packs[2];//包季价格
	      if(StringUtils.isNotEmpty(seasonPack)){
	    	  expert.setSeasonPack(seasonPack);
	      }
	      String yearPack=packs[3];//包周价格 
	      if(StringUtils.isNotEmpty(yearPack)){
	    	  expert.setYearPack(yearPack);
	      }
	      }
	      String expertName=proc.getString(6);//专家姓名
	      expert.setExpertName(expertName);
	      String expertClass=proc.getString(7);//专家姓名
	      expert.setExpertClass(expertClass);
	      String photo=proc.getString(8);//专家头像 add by hikin yao
	      expert.setPhoto(photo);
	      Double winRatio=proc.getDouble(9);//专家胜率 add by hikin yao
	      expert.setWinRatio(winRatio);
	      resultMap.put("expert", expert);
          rs = (ResultSet)proc.getObject(5);//他发起的投注
          List resultList=new ArrayList<ExpertVO>();
          ExpertVO po=null;
          while(rs.next()){
        	     po=new ExpertVO();
        	     introduction=rs.getString("introduction");
        	     po.setIntroduction(introduction);
        	     ncCnt=rs.getLong("cnt");
        	     po.setNcCnt(ncCnt);
        	     pack=rs.getString("pack");
        	    
        	    packs=pack.split(",");
        	    if(packs!=null&packs.length>0){
       	        String weekPack=packs[0];//包周价格 
       	        if(StringUtils.isNotEmpty(weekPack)){
       	        	po.setWeekPack(weekPack);
       	        }
       	        if(packs.length>1){
       	        String monthPack=packs[1];//包月价格
       	        if(StringUtils.isNotEmpty(monthPack)){
       	        	po.setMonthPack(monthPack);
       	        }
       	        }
       	       if(packs.length>2){
       	        String seasonPack=packs[2];//包季价格
       	        if(StringUtils.isNotEmpty(seasonPack)){
       	        	po.setSeasonPack(seasonPack);
       	        }
       	       }
       	      if(packs.length>3){
       	        String yearPack=packs[3];//包周价格 
       	        if(StringUtils.isNotEmpty(yearPack)){
       	        	po.setYearPack(yearPack);
       	        }
        	  }
        	 }
        	    String famousExpertPhoto=rs.getString("photo");//知名专家头像url
        	    String famousExpertClass=rs.getString("g_class");//知名专家分类
        	    String famousExpertName=rs.getString("expert_name");//知名专家名称
        	    Long famousExpertId=rs.getLong("expert_id");//知名专家Id
        	    po.setPhoto(famousExpertPhoto);
        	    po.setExpertClass(famousExpertClass);
        	    po.setExpertName(famousExpertName);
        	    po.setExpertId(famousExpertId);
			  resultList.add(po);
		  }
          resultMap.put("resultList", resultList);
	     return resultMap;
        }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return null;
	}
	
	
	
	
	
	
	
	
	
	
	/**保存内参信息时,产生内参编号**/
	public String genExpertCode()throws DaoException{
		  StringBuilder sb=null;
		  Connection conn=null;
		  PreparedStatement pstmt=null;
		  ResultSet rs=null;
		  try{
			sb=new StringBuilder();
			sb.append("N");
		    String sql="select  to_char(sysdate,'yymm'),SEQ_EXPERT_NO.NEXTVAL from dual";
		    conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		    pstmt=conn.prepareStatement(sql);
		    rs=pstmt.executeQuery();
		    while(rs.next()){
		    	String yymm=rs.getString(1);
		    	sb.append(yymm);
		    	String seq=rs.getString(2);
		    	sb.append(seq);
		    }
			
		  }catch(Exception e){
			 e.printStackTrace();
		  }finally{
			  closeAll(rs,pstmt,conn);
		  }
		  return sb.toString();
		}
	/**内参后台管理——内参管理界面  文档第10页  2010-03-15  10:34**/
	public Map findXinshuiList(Map params)throws DaoException{
        Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		Long soldUserId=(Long)params.get("soldUserId");//期次
		String status=(String)params.get("status");
		String from=(String)params.get("from");//发布起始时间
		String to=(String)params.get("to");//发布结束时间
	    String startTimeFrom=(String)params.get("startTimeFrom");//发布起始时间
		String startTimeTo=(String)params.get("startTimeTo");//发布结束时间
		String key=(String)params.get("key");
		List<B2CProductVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("select a.EXPERT_CODE,a.USER_ID,a.EXPERT_NAME,b.HOST_NAME,b.GUEST_NAME,b.RACE_NAME,c.START_TIME, ");
	      queryList.append(" b.TYPE,b.SELECT_TYPE,b.PUB_TIME,b.STATUS,b.CLICK ");
	      queryList.append("  from T_EXPERT  a,T_C2C_PRODUCT b,T_AGAINST c  where  a.USER_ID=b.TX_USER_ID and b.AGAINST_ID=c.AGAINST_ID ");
	      StringBuilder querySize=new StringBuilder(" select  count(*) cnt from T_EXPERT  a1,T_C2C_PRODUCT b1,T_AGAINST c1  where  a1.USER_ID=b1.TX_USER_ID and b1.AGAINST_ID=c1.AGAINST_ID ");
	      if(StringUtils.isNotEmpty(status)){
	    	  queryList.append(" and b.STATUS='"+status+"' ");
	    	  querySize.append(" and b1.STATUS='"+status+"' ");
	      }
	      if(StringUtils.isNotEmpty(from)){
	    	  queryList.append(" and  to_char(b.PUB_TIME,'yyyy-mm-dd') >='"+from+"' ");
	    	  querySize.append(" and  to_char(b1.PUB_TIME,'yyyy-mm-dd') >='"+from+"' ");
	      }
          if(StringUtils.isNotEmpty(to)){
        	  queryList.append(" and  to_char(b.PUB_TIME,'yyyy-mm-dd') <='"+to+"' ");
	    	  querySize.append(" and  to_char(b1.PUB_TIME,'yyyy-mm-dd') <='"+to+"' ");
	      }
          
          if(StringUtils.isNotEmpty(startTimeFrom)){
	    	  queryList.append(" and  to_char(b.PUB_TIME,'yyyy-mm-dd') >='"+startTimeFrom+"' ");
	    	  querySize.append(" and  to_char(b1.PUB_TIME,'yyyy-mm-dd') >='"+startTimeFrom+"' ");
	      }
          if(StringUtils.isNotEmpty(startTimeTo)){
        	  queryList.append(" and  to_char(c.START_TIME,'yyyy-mm-dd') <='"+startTimeTo+"' ");
	    	  querySize.append(" and  to_char(c1.START_TIME,'yyyy-mm-dd') <='"+startTimeTo+"' ");
	      }
          if(StringUtils.isNotEmpty(key)){
        	  queryList.append(" and (b.TX_USERNAME='"+key+"' or  b.HOST_NAME='"+key+"'  or b.RACE_NAME='"+key+"')");
	    	  querySize.append(" and (b1.TX_USERNAME='"+key+"' or  b1.HOST_NAME='"+key+"'  or b1.RACE_NAME='"+key+"')");
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
	      resultList=new ArrayList<B2CProductVO>();
	      B2CProductVO po=null;
	      while (rs.next()) {
	    	 po=new B2CProductVO();
	         String expertCode=rs.getString("EXPERT_CODE");
	         po.setExpertCode(expertCode);
	         String soldUsername=rs.getString("EXPERT_NAME");
	         po.setSoldUsername(soldUsername);//专家名
	         String hostName=rs.getString("HOST_NAME");
	         po.setHostName(hostName);
	         String guestName=rs.getString("GUEST_NAME");
	         po.setGuestName(guestName);
	         String raceName=rs.getString("RACE_NAME");
	         po.setRaceName(raceName);
	         String startTime=rs.getString("START_TIME");
	         po.setStartTime(startTime);
	         String type=rs.getString("TYPE");
	         po.setType(type);
	         String selectType=rs.getString("SELECT_TYPE");
	         po.setSelectType(selectType);
	         String pubTime=rs.getString("PUB_TIME");
	         po.setPubTime(pubTime);
	         String _status=rs.getString("STATUS");
	         po.setStatus(_status);
	         int click=rs.getInt("CLICK");
	         po.setClick(click);
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
	/**
	 * B2C产品详情
	 *
	 */
	public  B2CProductVO loadB2CProduct(Long b2cId)throws DaoException{

        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		B2CProductVO po=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder sql=new StringBuilder("select a.EXPERT_CODE,a.USER_ID,a.EXPERT_NAME,b.HOST_NAME,b.GUEST_NAME,b.RACE_NAME,c.START_TIME, ");
	      sql.append(" b.TYPE,b.SELECT_TYPE,b.PUB_TIME,b.STATUS,b.CLICK ");
	      sql.append("  from T_EXPERT  a,T_C2C_PRODUCT b,T_AGAINST c  where  a.USER_ID=b.TX_USER_ID and b.AGAINST_ID=c.AGAINST_ID  and b.C2C_ID="+b2cId);
	      pstmt = conn.prepareStatement(sql.toString());
	      while (rs.next()) {
	    	 po=new B2CProductVO();
	         String expertCode=rs.getString("EXPERT_CODE");
	         po.setExpertCode(expertCode);
	         String soldUsername=rs.getString("EXPERT_NAME");
	         po.setSoldUsername(soldUsername);//专家名
	         String hostName=rs.getString("HOST_NAME");
	         po.setHostName(hostName);
	         String guestName=rs.getString("GUEST_NAME");
	         po.setGuestName(guestName);
	         String raceName=rs.getString("RACE_NAME");
	         po.setRaceName(raceName);
	         String startTime=rs.getString("START_TIME");
	         po.setStartTime(startTime);
	         String type=rs.getString("TYPE");
	         po.setType(type);
	         String selectType=rs.getString("SELECT_TYPE");
	         po.setSelectType(selectType);
	         String pubTime=rs.getString("PUB_TIME");
	         po.setPubTime(pubTime);
	         String _status=rs.getString("STATUS");
	         po.setStatus(_status);
	         int click=rs.getInt("CLICK");
	         po.setClick(click);
	        
	      }
	   
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    return po;
	
	}
	/**内参订购管理  2010-03-15 11:18**/
	public Map findB2COrderList(Map params)throws DaoException{
        Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		String orderType=(String)params.get("orderType");//订购类型
		String payStatus=(String)params.get("payStatus");
		String from=(String)params.get("from");//发布起始时间
		String to=(String)params.get("to");//发布结束时间
	    String key=(String)params.get("key");
		List<B2COrderVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder(" select a.XINSHUI_ORDER_ID,a.ORDER_NO,a.BUY_USERNAME,a.ORDER_TYPE,a.SOLD_USERNAME,a.PRICE,to_char(a.BUY_TIME,'yy-mm-dd hh24:mi') BUY_TIME, ");
	      queryList.append(" to_char(a.END_TIME,'yy-mm-dd hh24:mi') END_TIME,a.PAY_STATUS from T_XINSHUI_ORDER a  where a.TYPE='2' ");
	      StringBuilder querySize=new StringBuilder(" select  count(*) cnt from  T_XINSHUI_ORDER b where  b.TYPE='2' ");
	      if(StringUtils.isNotEmpty(orderType)){
	    	  queryList.append(" and a.ORDER_TYPE='"+orderType+"' ");
	    	  querySize.append(" and b.ORDER_TYPE='"+orderType+"' ");
	      }
	      if(StringUtils.isNotEmpty(payStatus)){
	    	  queryList.append(" and a.PAY_STATUS='"+payStatus+"' ");
	    	  querySize.append(" and b.PAY_STATUS='"+payStatus+"' ");
	      }
	      if(StringUtils.isNotEmpty(from)){
	    	  queryList.append(" and  to_char(a.BUY_TIME,'yyyy-mm-dd')  >='"+from+"' ");
	    	  querySize.append(" and  to_char(b.BUY_TIME,'yyyy-mm-dd') >='"+from+"' ");
	      }
          if(StringUtils.isNotEmpty(to)){
        	  queryList.append(" and  to_char(a.BUY_TIME,'yyyy-mm-dd') <='"+to+"' ");
	    	  querySize.append(" and  to_char(b.BUY_TIME,'yyyy-mm-dd') <='"+to+"' ");
	      }
          if(StringUtils.isNotEmpty(key)){
        	  queryList.append(" and (a.BUY_USERNAME='"+key+"' or  a.SOLD_USERNAME='"+key+"'  or a.ORDER_NO='"+key+"')");
	    	  querySize.append(" and (b.BUY_USERNAME='"+key+"' or  b.SOLD_USERNAME='"+key+"'  or b.ORDER_NO='"+key+"')");
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
	      resultList=new ArrayList<B2COrderVO>();
	      B2COrderVO po=null;
	      while (rs.next()) {
	    	 po=new B2COrderVO();
	    	 Long b2cOrderId=rs.getLong("XINSHUI_ORDER_ID");
	    	 po.setXinshuiId(b2cOrderId);
	         String orderNo=rs.getString("ORDER_NO");
	         po.setOrderNo(orderNo);
	         String orderUsername=rs.getString("BUY_USERNAME");
	         po.setOrderUsername(orderUsername);
	         String _orderType=rs.getString("ORDER_TYPE");
	         po.setOrderType(_orderType);
	         String soldUsername=rs.getString("SOLD_USERNAME");
	         po.setSoldUsername(soldUsername);//专家名
	         String price=rs.getString("PRICE");
	         po.setPrice(price);
	         String orderTime=rs.getString("BUY_TIME");
	         po.setOrderTime(orderTime);
	         String endTime=rs.getString("END_TIME");
	         po.setEndTime(endTime);
	         String _payStatus=rs.getString("PAY_STATUS");
	         po.setPayStatus(_payStatus);
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
	public List<Expert> findExpertList()throws DaoException{
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	   
	    List list=null;
		try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      String sql="select t.Expert_Id,t.EXPERT_NAME from T_EXPERT t where  t.STATUS='1'";
	      pstmt=conn.prepareStatement(sql);
	      rs=pstmt.executeQuery();
	      Expert po=null;
	      list=new ArrayList();
	      while(rs.next()){
	    	  po=new Expert();
	    	  Long expertId=rs.getLong("Expert_Id");
	    	  po.setExpertId(expertId);
	    	  String expertName=rs.getString("EXPERT_NAME");
	    	  po.setExpertName(expertName);
	    	  list.add(po);
	      }
	      System.out.print(list.toString());
	      
		}catch(Exception e){
			e.printStackTrace();
		}finally{
	    	closeAll(rs,pstmt,conn);
	    }
		
		return list;
	}
	

}
