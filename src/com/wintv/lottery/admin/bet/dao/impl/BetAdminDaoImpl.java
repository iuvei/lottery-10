package com.wintv.lottery.admin.bet.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.OracleResultSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.lottery.admin.bet.dao.BetAdminDao;
import com.wintv.lottery.admin.bet.vo.BetAdminDetailVO;
import com.wintv.lottery.admin.bet.vo.BetAdminOrderVO;
import com.wintv.lottery.admin.bet.vo.PhaseVO;


@Repository("betAdminDao")
public class BetAdminDaoImpl extends BaseHibernate<BetOrder,Long> implements BetAdminDao{
	/**
	 * 根据彩种查询期次信息  2010-04-13 16:49
	 * 参数：
	 *  betCategory：彩种
	 * @return
	 * @throws DaoException
	 */
	public List<PhaseVO> findPhaseList(Long betCategory)throws DaoException{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<PhaseVO>  resultList=null;
		  try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      String sql="select t.phase_no,t.id from t_lottery_phase t   where  t.category=?";
		      pstmt=conn.prepareStatement(sql);
		      pstmt.setLong(1,betCategory);
		      rs=pstmt.executeQuery();
		      resultList=new ArrayList<PhaseVO>();
		      PhaseVO po=null;
		      while(rs.next()){
		    	  po=new PhaseVO();
		    	  String  phaseNo=rs.getString("phase_no");
		    	  po.setPhaseNo(phaseNo);
		    	  long phaseId=rs.getLong("id");
		    	  po.setPhaseId(phaseId);
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
	 * 3.2.5投注系统后台：*投注订单管理列表  文档54页  2010-03-07  14:00
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public  Map findList(Map params)throws DaoException{
		Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		String betCategory=(String)params.get("betCategory");
		String betTimeFrom=(String)params.get("betTimeFrom");//起始按投注时间
		String betTimeTo=(String)params.get("betTimeTo");//结束按投注时间
		String phaseNo=(String)params.get("phaseNo");//期次
		List<BetAdminOrderVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("  select a.bet_id,a.order_no,a.plan_code,to_char(a.bet_time,'yy-mm-dd hh24:mi')bet_time,a.phase_no,a.sponsor_type,a.all_money,");
	      queryList.append(" decode(a.order_status,'1','未支付','2','待出票','3','已出票','4','已取消','5','已过期') order_status,") ;
	      //投注彩种    1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     61:单场半全场  62:单场比分 63:单场胜平负  64:单场上下单双 65:单场总进球
	      queryList.append(" decode(a.bet_categoty,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_categoty,");
	      queryList.append("a.type,");
	      queryList.append(" decode(a.zj_status, '1','未开奖','2','已返奖','3','未中奖') zj_status,a.bet_username,BONUS from T_BET_ORDER  a ");
	      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from T_BET_ORDER ");
	      if(StringUtils.isNotEmpty(betCategory)){
	    	  queryList.append("  and a.BET_CATEGOTY='"+betCategory+"'");
	    	  querySize.append("  and a.BET_CATEGOTY='"+betCategory+"'");
	      }
	      if(StringUtils.isNotEmpty(betTimeFrom)){
	    	  queryList.append("  and to_char(a.BET_TIME,'yyyy-mm-dd') >='"+betTimeFrom+"'");
	    	  querySize.append("  and to_char(a.BET_TIME,'yyyy-mm-dd') >='"+betTimeFrom+"'");
	      }
	      if(StringUtils.isNotEmpty(betTimeTo)){
	    	  queryList.append("  and to_char(a.BET_TIME,'yyyy-mm-dd') <='"+betTimeTo+"'");
	    	  querySize.append("  and to_char(a.BET_TIME,'yyyy-mm-dd') <='"+betTimeTo+"'");
	      }
	      if(StringUtils.isNotEmpty(phaseNo)){
	    	  queryList.append("  and a.PHASE_NO='"+phaseNo+"'  ");
	    	  querySize.append("  and a.PHASE_NO='"+phaseNo+"'  ");
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
	         String orderNo=rs.getString("order_no");
	         vo.setOrderNo(orderNo);
	         String betTime=rs.getString("bet_time");
	         vo.setBetTime(betTime);
	         betCategory=rs.getString("bet_categoty");
	         vo.setBetCategory(betCategory);
	         phaseNo=rs.getString("phase_no");
	      
	         vo.setPhaseNo(phaseNo);
	         String sponsorType=rs.getString("sponsor_type");
	         vo.setSponsorType(sponsorType);
	         String allMoney=rs.getString("all_money");
	         if(allMoney==null){
	        	 allMoney="0.00";
	         }else{
	        	 int b=allMoney.indexOf(".00");
	        	 if(b==-1){
	        		 allMoney+=".00";
	        	 }
	         }
	         vo.setAllMoney(allMoney);
	         String orderStatus=rs.getString("order_status");
	         vo.setOrderStatus(orderStatus);
	         String zjStatus=rs.getString("zj_status");
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
	 * 投注订单详情 对应文档55页
	 * @param betId
	 * @return
	 * @throws DaoException
	 */
	public BetAdminDetailVO  loadBetAdminDetailVO(Long betId)throws DaoException{
		StringBuilder sql=new StringBuilder("select t.ORDER_NO,t.PLAN_CODE,to_char(t.BET_TIME,'yyyy-mm-dd hh24:mi') BET_TIME,t.BET_CATEGOTY,t.PHASE_NO  ");
		sql.append(",t.ORDER_STATUS,to_char(t.ALL_MONEY) ALL_MONEY from T_BET_ORDER t where t.BET_ID=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		BetAdminDetailVO po=null;
	    try{
	    	
	    	conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	    	pstmt=conn.prepareStatement(sql.toString());
	    	pstmt.setLong(1, betId);
	        rs=pstmt.executeQuery();
	        while(rs.next()){
	        	po=new BetAdminDetailVO();
	        	String orderNo=rs.getString("ORDER_NO");
	        	po.setOrderNo(orderNo);
	        	String planCode=rs.getString("PLAN_CODE");//方案编号 
	        	po.setPlanCode(planCode);
	        	String betTime=rs.getString("BET_TIME");
	        	po.setBetTime(betTime);
	        	String betCategory=rs.getString("BET_CATEGOTY");
	        	po.setBetCategory(betCategory);
	        	String phaseNo=rs.getString("PHASE_NO");
	        	po.setPhaseNo(phaseNo);
	        	String orderStatus=rs.getString("ORDER_STATUS");
	        	po.setOrderStatus(orderStatus);
	        	String allMoney=rs.getString("ALL_MONEY");
	        	po.setAllMoney(allMoney);
	        	
	        }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    
	    return po;
	}
	/**
	 * 3.2.5投注系统后台：*认购信息（收起认购信息）  文档55页  2010-03-07  16:00
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public  Map findSubscribeList(Map params)throws DaoException{
		
		Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		Long sponsorBetId=(Long)params.get("betId");
		List<BetAdminOrderVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("  select  a.BET_USERNAME,a.SUBSCRIBE_COPYS,a.SUBSCRIBE_MONEY,a.BONUS,to_char(a.BET_TIME,'yyyy-mm-dd hh24:mi') BET_TIME");
	      queryList.append(" from T_BET_ORDER  a  where a.SPONSOR_BET_ID="+sponsorBetId);
	      
	      log.info("queryList**="+queryList.toString());
	      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from T_BET_ORDER b where  b.SPONSOR_BET_ID="+sponsorBetId);
	      
	      
	      
	      
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
	     
          resultList=new ArrayList<BetAdminOrderVO>();
          BetAdminOrderVO vo=null;
	      while (rs.next()) {
	    	 vo=new BetAdminOrderVO();
	         String betUsername=rs.getString("BET_USERNAME");
	         vo.setBetUsername(betUsername);
	         int subscribeCopys=rs.getInt("SUBSCRIBE_COPYS");
	         vo.setSubscribeCopys(subscribeCopys);
	         String subscribeMoney=rs.getString("SUBSCRIBE_MONEY");
	         if(subscribeMoney.indexOf(".00")==-1){
	        	 subscribeMoney+=".00";
	         }
	         vo.setSubscribeMoney(subscribeMoney);
	         String betTime=rs.getString("BET_TIME");
	         vo.setBetTime(betTime);
	         String bonus=rs.getString("bonus");
	         if(bonus!=null){
	        	 if(bonus.indexOf(".00")==-1){
	        		 bonus+=".00";
	        	 }
	         }else{
	        	 bonus="0.00";
	         }
	         vo.setBonus(bonus);
	       
	         resultList.add(vo);
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
	/**
	 * 3.2.6合买系统后台：2010-03-08 09:14 合买方案列表
	 */
	public  Map findCoopBuyList(Map params)throws DaoException{
		Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		String betCategory=(String)params.get("betCategory");//彩种
		String betTimeFrom=(String)params.get("betTimeFrom");//起始按投注时间
		String betTimeTo=(String)params.get("betTimeTo");//结束按投注时间
		String phaseNo=(String)params.get("phaseNo");//期次
		BigDecimal planMoneyMin=(BigDecimal)params.get("planMoneyMin");//案金额最小值
		BigDecimal planMoneyMax=(BigDecimal)params.get("planMoneyMax");//案金额最大值
		String coopBuyStatus=(String)params.get("coopBuyStatus");//合买状态：1满员  2未满员  3已撤单
		
		String key=(String)params.get("key");//关键字
		String keyFlg=(String)params.get("keyFlg");//
		
		String betType=(String)params.get("betType");//投注方式
		
		List<BetAdminOrderVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("  select a.WICI_TYPE,a.FLOOR_COPYS,a.SINGLE_MONEY,a.DIVIDE_COPYS,a.ALREADY_BUY_COPYS,a.type,a.bet_id,a.plan_code,to_char(a.bet_time,'yy-mm-dd hh24:mi') bet_time,");
	      //queryList.append(" decode(a.bet_categoty,'1','胜负14场','2','任9场','3','4场进球','4','4半全场','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_categoty,");
	      queryList.append(" a.bet_categoty,");
	      queryList.append(" a.phase_no,a.sponsor_type,a.all_money,a.order_status,");
	      queryList.append("a.zj_status,a.bet_username,b.BET_MILITARY  from T_BET_ORDER  a, T_USER b   where  a.BET_USERID=  b.userid and (a.type='2' or  a.type='4')  and  a.SPONSOR_BET_ID is null ");
	     
	      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from T_BET_ORDER  k where (k.type='2' or  k.type='4')  and  k.SPONSOR_BET_ID is null ");
	      if(StringUtils.isNotEmpty(betCategory)){
	    	  queryList.append("  and a.BET_CATEGOTY='"+betCategory+"'");
	    	  querySize.append("  and k.BET_CATEGOTY='"+betCategory+"'");
	      }
	      if(StringUtils.isNotEmpty(betTimeFrom)){
	    	  queryList.append("  and to_char(a.BET_TIME,'yyyy-mm-dd') >='"+betTimeFrom+"'");
	    	  querySize.append("  and to_char(k.BET_TIME,'yyyy-mm-dd') >='"+betTimeFrom+"'");
	      }
	      if(StringUtils.isNotEmpty(betTimeTo)){
	    	  queryList.append("  and to_char(a.BET_TIME,'yyyy-mm-dd') <='"+betTimeTo+"'");
	    	  querySize.append("  and to_char(k.BET_TIME,'yyyy-mm-dd') <='"+betTimeTo+"'");
	      }
	      if(StringUtils.isNotEmpty(phaseNo)){
	    	  queryList.append("  and a.PHASE_NO='"+phaseNo+"'  ");
	    	  querySize.append("  and k.PHASE_NO='"+phaseNo+"'  ");
	      }
	      if(planMoneyMin!=null){
	    	  queryList.append("  and a.ALL_MONEY>="+planMoneyMin);
	    	  querySize.append("  and k.ALL_MONEY>="+planMoneyMin);
	      }
	      if(planMoneyMax!=null){
	    	  queryList.append("  and a.ALL_MONEY <="+planMoneyMax);
	    	  querySize.append("  and k.ALL_MONEY <="+planMoneyMax);
	      }
	      if(StringUtils.isNotEmpty(coopBuyStatus)){
	    	  if("1".equals(coopBuyStatus)){//满员
	    		  queryList.append(" and a.ALREADY_BUY_COPYS=a.DIVIDE_COPYS ");
	    		  querySize.append(" and k.ALREADY_BUY_COPYS=k.DIVIDE_COPYS ");
	    		//queryList.append(" and (select  sum(w.SUBSCRIBE_MONEY) from  T_BET_ORDER  w   where  w.SPONSOR_BET_ID=a.BET_ID or a.BET_ID=a.BET_ID)=a.ALL_MONEY "); 
	    	    //querySize.append("  and (select  sum(w.SUBSCRIBE_MONEY) from  T_BET_ORDER  w   where  w.SPONSOR_BET_ID=a.BET_ID or a.BET_ID=a.BET_ID)=a.ALL_MONEY "); 
	    	  }else if("2".equals(coopBuyStatus)){//未满员
	    		  queryList.append(" and a.ALREADY_BUY_COPYS< a.DIVIDE_COPYS ");
	    		  querySize.append(" and k.ALREADY_BUY_COPYS< k.DIVIDE_COPYS ");
		    		//queryList.append(" and (select  sum(w.SUBSCRIBE_MONEY) from  T_BET_ORDER  w   where  w.SPONSOR_BET_ID=a.BET_ID or a.BET_ID=a.BET_ID )< a.ALL_MONEY "); 
		    	    //querySize.append("  and (select  sum(w.SUBSCRIBE_MONEY) from  T_BET_ORDER  w   where  w.SPONSOR_BET_ID=a.BET_ID or a.BET_ID=a.BET_ID )<a.ALL_MONEY "); 
		      }
	      }
	      if(StringUtils.isNotEmpty(key)){
	    	  if("1".equals(keyFlg)){
	    	    queryList.append("  and a.BET_USERNAME like '%"+key+"%'");
	    	    querySize.append("  and k.BET_USERNAME like '%"+key+"%'");
	    	  }
	      }
	      if(StringUtils.isNotEmpty(betType)){
	    	    if("a".equals(betType)){
	    	      queryList.append("  and (a.type='1'  or a.type='2') ");
	    	      querySize.append("  and (k.type='1'  or k.type='2') ");
	    	    }else if("b".equals(betType)){
	    	    	 queryList.append("  and (a.type='3'  or a.type='4') ");
		    	     querySize.append("  and (k.type='3'  or k.type='4')  ");
	    	    }
	      }
	      String maxMoney=(String)params.get("maxMoney");
	      String minMoney=(String)params.get("minMoney");
	      if(StringUtils.isNotEmpty(minMoney)){
	    	  queryList.append("  and a.all_money>=to_number('"+minMoney+"') ");
	    	  querySize.append("  and k.all_money>=to_number('"+minMoney+"') ");
	      }
	      if(StringUtils.isNotEmpty(maxMoney)){
	    	  queryList.append("  and a.all_money <=to_number('"+maxMoney+"') ");
	    	  querySize.append("  and k.all_money <=to_number('"+maxMoney+"') ");
	      }
	      String process=(String)params.get("process");
	      if(StringUtils.isNotEmpty(process)){
	        int processValue=Integer.parseInt(process);
	        int minValue=10*processValue+1;
	        if(processValue==0){
	        	minValue=0;
	        }
		    int maxValue=minValue+9;
		    if(processValue==0){
		    	maxValue=10;
	        }
		    queryList.append("  and a.ALREADY_BUY_COPYS/a.DIVIDE_COPYS>="+minValue/10);
   	        querySize.append("  and k.ALREADY_BUY_COPYS/k.DIVIDE_COPYS <="+maxValue/10);
	      }
	     
		  
	      String sql = "{ call bet.sp_Page(?,?,?,?,?,?)}";
	      proc = conn.prepareCall(sql);
	      proc.setInt(1, pageSize);//每页数量
	      proc.setInt(2, startRow);//页码 
	      proc.setString(3, queryList.toString());//取数据的sql
	      log.info(querySize.toString());
	      proc.setString(4,querySize.toString());//取数据个数的sql
	      proc.registerOutParameter(5, OracleTypes.INTEGER);//输出数据行数
	      proc.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR);//输出游标记录集
	      proc.execute();
	      int totalCount = proc.getInt(5);
	      rs = (OracleResultSet)proc.getObject(6);
	      resultList=new ArrayList<BetAdminOrderVO>();
          BetAdminOrderVO vo=null;
	      while (rs.next()) {
	    	 vo=new BetAdminOrderVO();
	         Long betId=rs.getLong("bet_id");
	         vo.setBetId(betId);
	         String planCode=rs.getString("plan_code");
	         vo.setPlanCode(planCode);
	         String betTime=rs.getString("bet_time");
	        
	         vo.setBetTime(betTime);
	         betCategory=rs.getString("bet_categoty");
	         vo.setBetCategory(betCategory.trim());
	         phaseNo=rs.getString("phase_no");
	       
	         vo.setPhaseNo(phaseNo);
	         String sponsorType=rs.getString("sponsor_type");
	         vo.setSponsorType(sponsorType);
	         String allMoney=rs.getString("all_money");
	         if(StringUtils.isNotEmpty(allMoney)){
	        	 if(allMoney.indexOf(".00")==-1){
	        		 allMoney+=".00";
	        	 }
	         }
	         vo.setAllMoney(allMoney);
	         String orderStatus=rs.getString("order_status");
	         vo.setOrderStatus(orderStatus);
	         String zjStatus=rs.getString("zj_status");
	         vo.setZjStatus(zjStatus);
	         String type=rs.getString("type");
	       
	         vo.setType(type.trim());
	         String betUsername=rs.getString("bet_username");
	         vo.setBetUsername(betUsername);
	         int betMilitary=rs.getInt("BET_MILITARY");
	         StringBuffer starts=new StringBuffer();
	         for(int j=0;j<betMilitary;j++){
	        	 starts.append("★");
	         }
	         vo.setBetMilitary(starts.toString());
	         int alreadyBuyCopys=rs.getInt("ALREADY_BUY_COPYS");
	         int divideCopys=rs.getInt("DIVIDE_COPYS");
	         int leavingCopys=divideCopys-alreadyBuyCopys;
	         vo.setLeavingCopys(leavingCopys);//剩余份数
	         int singleMoney=rs.getInt("SINGLE_MONEY");//单倍金额(合买时  即每一份多少钱)
	         vo.setSingleMoney(singleMoney);
	         
	        
	         double progress=(alreadyBuyCopys*100/divideCopys);
	         vo.setProgress(progress);
	         int floorCopys=rs.getInt("FLOOR_COPYS");//保底份数
	         
	         vo.setFloorCopys(floorCopys*100/divideCopys);
	         String wiciType=rs.getString("WICI_TYPE");
	         vo.setWiciType(wiciType);
	         resultList.add(vo);
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
	/**
	 * 合买方案详情 57页 2010-03-08 10:12
	 * type 类型 :'1'单代 '2' 单合  '3 '复代 '4'复合
	 * @param betId
	 * @return
	 * @throws DaoException
	 */
	public BetAdminDetailVO  loadCoopByDetailVO(Long betId,String type,String betCategory,String wiciType)throws DaoException{
		StringBuilder sql=new StringBuilder("select t.ORDER_NO,t.PLAN_CODE,to_char(t.BET_TIME,'yyyy-mm-dd hh24:mi') BET_TIME,t.BET_CATEGOTY,t.PHASE_NO  ");
		sql.append(",t.ORDER_STATUS,to_char(t.ALL_MONEY) ");
		if(betCategory.startsWith("6")){//足球单场 并且是复式时的截止时间
		  if("3".equals(type)||"4".equals(type)){
			  if("1".equals(wiciType)){//过关类型    '1':普通过关  '2': 组合过关   '3':自由过关 
		         sql.append(" ,(select  min(c.start_time)-t.VICI_DEADLINE/24/60 ");
			  }else if("2".equals(wiciType)||"3".equals(wiciType)){
				  sql.append(" ,(select  min(c.start_time)-t.COMBO_VICI/24/60 ");
			  }
		      sql.append(" from  t_lottery_phase a,t_lottery_against  b,t_against  c where  a.id=b.phase_id and  b.against_id=c.against_id  and a.id=t.PHASE_ID ) deadline ");
		  }else if("1".equals(type)||"2".equals(type)){
			  sql.append(",(select a.S_DEADLINE deadline  from  t_lottery_phase a,t_lottery_against  b,t_against  c where  a.id=b.phase_id and  b.against_id=c.against_id  and a.id=t.PHASE_ID ) deadline");
		  }
		}else{//其他彩种
			if("3".equals(type)||"4".equals(type)){//复式截止时间
				sql.append(",t.MUL_DEADLINE deadline ");
			}else if("1".equals(type)||"2".equals(type)){//单式截止时间
				sql.append(",t.S_DEADLINE deadline ");
			}
		}
		sql.append(",t.BET_USERNAME");
		sql.append(",case when t.ALREADY_BUY_COPYS=t.DIVIDE_COPYS then '满员' ");
		sql.append("when t.ALREADY_BUY_COPYS< t.DIVIDE_COPYS then  '未满员' ");
		sql.append(" else '已撤单'  end  coopBuyStatus ,t.BET_NUMBER,t.BET_MULTI,t.ALL_MONEY,");
		sql.append(" t.ALREADY_BUY_COPYS/t.DIVIDE_COPYS  tempo");//进度
		sql.append(",t.DIVIDE_COPYS ,ALL_MONEY/DIVIDE_COPYS,t.IS_FLOOR,t.TC_RATE,FLOOR_COPYS/DIVIDE_COPYS floor_Rate ");
		sql.append(" from T_BET_ORDER t,T_USER u where t.BET_USERID=u.USERID  and  t.BET_ID=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		BetAdminDetailVO po=null;
	    try{
	    	
	    	conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	    	log.info("|||||||="+sql.toString());
	    	pstmt=conn.prepareStatement(sql.toString());
	    	pstmt.setLong(1, betId);
	        rs=pstmt.executeQuery();
	        while(rs.next()){
	        	po=new BetAdminDetailVO();
	        	String orderNo=rs.getString("ORDER_NO");
	        	po.setOrderNo(orderNo);
	        	String planCode=rs.getString("PLAN_CODE");//方案编号 
	        	po.setPlanCode(planCode);
	        	String betTime=rs.getString("BET_TIME");
	        	po.setBetTime(betTime);
	            betCategory=rs.getString("BET_CATEGOTY");//投注彩种
	        	po.setBetCategory(betCategory);
	        	String phaseNo=rs.getString("PHASE_NO");
	        	po.setPhaseNo(phaseNo);
	        	String orderStatus=rs.getString("ORDER_STATUS");
	        	po.setOrderStatus(orderStatus);
	        	String allMoney=rs.getString("ALL_MONEY");
	        	po.setAllMoney(allMoney);
	        	po.setType(type);
	        }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    
	    return po;
	}
	/**
	 * 3.2.5投注系统后台：合买认购信息（收起认购信息）  文档57页  2010-03-07  16:00
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public  Map findCoopBuySubscribeList(Map params)throws DaoException{
		Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		Long betId=(Long)params.get("betId");//
		List<BetAdminOrderVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("  select  a.BET_USERNAME,a.SUBSCRIBE_COPYS,a.SUBSCRIBE_MONEY,a.SUBSCRIBE_COPYS/DIVIDE_COPYS sub_rate ,to_char(a.BET_TIME,'yyyy-mm-dd hh24:mi') BET_TIME ");
	      queryList.append(" from T_BET_ORDER  a  where a.SPONSOR_BET_ID="+betId);
	      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from T_BET_ORDER b where  b.SPONSOR_BET_ID="+betId);
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
	      log.info("***"+queryList.toString());
          resultList=new ArrayList<BetAdminOrderVO>();
          BetAdminOrderVO vo=null;
	      while (rs.next()) {
	    	 vo=new BetAdminOrderVO();
	         String betUsername=rs.getString("BET_USERNAME");
	         vo.setBetUsername(betUsername);
	         int subscribeCopys=rs.getInt("SUBSCRIBE_COPYS");
	         vo.setSubscribeCopys(subscribeCopys);
	         String subscribeMoney=rs.getString("SUBSCRIBE_MONEY");
	         vo.setSubscribeMoney(subscribeMoney);
	         String betTime=rs.getString("BET_TIME");
	         double subRate=rs.getDouble("sub_rate");
	         vo.setSubRate(subRate*100);//认购比率
	         vo.setBetTime(betTime);
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
	    return resultMap;
	}
	
	private static final Log log=LogFactory.getLog(BetAdminDaoImpl.class);

}
