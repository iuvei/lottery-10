package com.wintv.lottery.admin.bet.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.OracleResultSet;

import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.MyAutoOrder;
import com.wintv.lottery.admin.bet.dao.MyAdminAutoOrderDao;

import com.wintv.lottery.admin.bet.vo.MyAutoOrderVO;
@Repository("myAdminAutoOrderDao")
public class MyAdminAutoOrderDaoImpl  extends BaseHibernate<MyAutoOrder,Long> implements MyAdminAutoOrderDao{
	public Map findAutoOrderList(Map params)throws DaoException{
        Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		String betWay=(String)params.get("betWay");//投注方式
		String key=(String)params.get("key");//关键字
		Long kingId=(Long)params.get("kingId");
		List<MyAutoOrderVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("  select a.AUTO_ORDER_ID,a.USERNAME,a.TYPE,to_char(a.ORDER_TIME,'yyyy-mm-dd hh24:mi') ORDER_TIME,a.MIN_MONEY,a.MAX_MONEY,a.TX_MONEY ");
	      queryList.append(",status  from  T_MY_AUTO_ORDER a  where a.KING_ID="+kingId);
	      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from T_MY_AUTO_ORDER b  where  b.KING_ID="+kingId);
	      String sql = "{ call bet.sp_Page(?,?,?,?,?,?)}";
	      proc = conn.prepareCall(sql);
	      proc.setInt(1, pageSize);//每页数量
	      proc.setInt(2, startRow);//页码 
	      proc.setString(3, queryList.toString());//取数据的sql
	      proc.setString(4,querySize.toString());//取数据个数的sql
	      proc.registerOutParameter(5, OracleTypes.INTEGER);//输出数据行数
	      proc.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR);//输出游标记录集
	      proc.execute();
	      int totalCount = proc.getInt(5);
	      rs = (OracleResultSet)proc.getObject(6);
	      resultList=new ArrayList<MyAutoOrderVO>();
	      MyAutoOrderVO po=null;
	      while (rs.next()) {
	    	 po=new MyAutoOrderVO();
	         Long autoOrderId=rs.getLong("AUTO_ORDER_ID");
	         po.setAutoOrderId(autoOrderId);
	         String username=rs.getString("USERNAME");
	         po.setUsername(username);
	         String type=rs.getString("TYPE");
	         po.setType(type);
	         String orderTime=rs.getString("ORDER_TIME");
	         po.setOrderTime(orderTime);
	         po.setOrderTime(orderTime);
	         String minMoney=rs.getString("MIN_MONEY");
	         po.setMinMoney(minMoney);
	         String maxMoney=rs.getString("MAX_MONEY");
	         po.setMaxMoney(maxMoney);
	         String txMoney=rs.getString("TX_MONEY");
	         po.setTxMoney(txMoney);
	         String status=rs.getString("status");
	         po.setStatus(status);
	        
	         resultList.add(po);
	      }
	      resultMap=new HashMap();
	      resultMap.put("totalCount", totalCount);
	      resultMap.put("resultList", resultList);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return resultMap;

}

}
