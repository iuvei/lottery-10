package com.wintv.lottery.personal.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.personal.dao.HistoryMilitaryDao;
import com.wintv.lottery.personal.vo.BetOrderVO;
import com.wintv.lottery.personal.vo.XinshuiVO;
@Repository("hisMiliDao")
public class HistoryMilitaryDaoImpl extends BaseHibernate implements  HistoryMilitaryDao{
	/**
	 * 心水历史记录 2010-04-20 15:57
	 *
	 */
	public Map  findHistoryXinshuiList(Map params)throws DaoException{
          Long userId=(Long)params.get("userId");
		  //String betCategory=(String)params.get("betCategory");
		 
		  StringBuilder sqlList=new StringBuilder("select t.race_name,to_char(r.start_time,'yy-mm-dd hh24:mi') start_time,r.host_name,r.guest_name,t.ensure_money,");
		  sqlList.append(" (select count(1) from t_xinshui_order xo  where xo.sold_user_id=t.tx_user_id and xo.product_id=t.c2c_id),");
		  sqlList.append(" t.ensure_money/(select dic.value from t_dictionary dic  where  dic.code='MARC2C_GIN_VS_PRICE' and dic.type='XINSHUI') price ");
		  sqlList.append(" ,decode(t.status,'','2','赢','3','负','4','走','5','已关闭') result ");
		  sqlList.append(" from  t_c2c_product t,t_against r where t.against_id=r.against_id and t.type='1'  and t.tx_user_id="+userId);
		  sqlList.append(" and to_char(t.pub_time,'yyyy-mm-dd')>=to_char(sysdate-7,'yyyy-mm-dd')");
		  
		  StringBuilder sqlSize=new StringBuilder("select  count(1) cnt ");
		  sqlSize.append(" from  t_c2c_product p where  p.tx_user_id="+userId+" and to_char(p.pub_time,'yyyy-mm-dd')>=to_char(sysdate-7,'yyyy-mm-dd')");
		 
		 
		  Connection conn=null;
		  CallableStatement proc=null;
		  ResultSet rs=null;
		  int startRow=(Integer)params.get("startRow");
	      int pageSize=(Integer)params.get("pageSize");
		  String sql = "{ call bet.sp_Page(?,?,?,?,?,?)}";
		  Map resultMap=new HashMap();
		  try{
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      proc = conn.prepareCall(sql);
		      proc.setInt(1, pageSize);//每页数量
		     
		      proc.setInt(2, startRow);//页码 
		      proc.setString(3, sqlList.toString());//取数据的sql
		      proc.setString(4,sqlSize.toString());//取数据个数的sql
		      proc.registerOutParameter(5, OracleTypes.NUMBER);//输出数据行数
		      proc.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR);//输出游标记录集
		      proc.execute();
		      long totalCount = proc.getLong(5);
		      rs = (OracleResultSet)proc.getObject(6);
		      XinshuiVO po=null;
		      List<XinshuiVO> resultList=new ArrayList<XinshuiVO>();
		      while(rs.next()){
		    	  po=new XinshuiVO();
			      String raceName=rs.getString("race_name");
			      po.setRaceName(raceName);
			      String startTime=rs.getString("start_time");
			      po.setStartTime(startTime);
			   
			      String hostName=rs.getString("host_name");
			      StringBuilder hostAndGuest=new StringBuilder();
			      hostAndGuest.append(hostName);
			      String guestName=rs.getString("guest_name");
			      hostAndGuest.append("&nbsp;VS&nbsp;");
			      hostAndGuest.append(guestName);
			      po.setHostAndGuest(hostAndGuest.toString());
			      String ensureMoney=rs.getString("ensure_money");
			      po.setEnsureMoney(ensureMoney);
			      String price =rs.getString("price");
			      po.setPrice(price);
			      String result=rs.getString("result");
			      po.setResult(result);
			    resultList.add(po);
			 }
		      resultMap.put("resultList", resultList);
		      resultMap.put("totalCount", totalCount);
		     return resultMap;
		   }catch(Exception e){
			  e.printStackTrace();
		  }finally{
			  closeAll(rs,proc,conn);
		  }
	     
		return null;
	
	}
	
	public Map  findHistoryBetList(Map params)throws DaoException{
		  Long userId=(Long)params.get("userId");
		  String betCategory=(String)params.get("betCategory");
		 
		  StringBuilder sqlList=new StringBuilder("select t.floor_copys,t.bet_username,t.all_money,t.bonus,");
		  sqlList.append(" decode(t.type,'1','单式代购','2','单式合买','3','复式代购','4','复式合买') type, ");
		  sqlList.append(" decode(t.bet_categoty,'1','胜负彩14场','2','任选9场','3','4场进球','5','6场半全场','61','单场半全场','62','单场比分', '63',");
		  sqlList.append(" '单场让球胜平负','64','单场上下单双','65','单场总进球') bet_categoty,");
		  sqlList.append(" (select count(1) from t_bet_order m where m.sponsor_bet_id=t.bet_id) partiCnt,");
		  sqlList.append(" t.order_status,t.already_buy_copys,t.divide_copys");
		  sqlList.append(" from  t_bet_order t where  t.bet_userid="+userId+" and to_char(t.bet_time,'yyyy-mm-dd')>=to_char(sysdate-7,'yyyy-mm-dd')");
		  sqlList.append(" and t.sponsor_type='1' ");
		  
		  StringBuilder sqlSize=new StringBuilder("select  count(1) cnt ");
		  sqlSize.append(" from  t_bet_order t where  t.bet_userid="+userId+" and to_char(t.bet_time,'yyyy-mm-dd')>=to_char(sysdate-7,'yyyy-mm-dd')");
		  sqlSize.append(" and t.sponsor_type='1' ");
		  if(StringUtils.isNotEmpty(betCategory)){
			  sqlList.append(" and t.bet_categoty='"+betCategory+"'");
			  sqlSize.append(" and t.bet_categoty='"+betCategory+"'");
		  }
		 
		  Connection conn=null;
		  CallableStatement proc=null;
		  ResultSet rs=null;
		  int startRow=(Integer)params.get("startRow");
	      int pageSize=(Integer)params.get("pageSize");
		  String sql = "{ call bet.sp_Page(?,?,?,?,?,?)}";
		  Map resultMap=new HashMap();
		  try{
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      proc = conn.prepareCall(sql);
		      proc.setInt(1, pageSize);//每页数量
		     
		      proc.setInt(2, startRow);//页码 
		      proc.setString(3, sqlList.toString());//取数据的sql
		      proc.setString(4,sqlSize.toString());//取数据个数的sql
		      proc.registerOutParameter(5, OracleTypes.NUMBER);//输出数据行数
		      proc.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR);//输出游标记录集
		      proc.execute();
		      long totalCount = proc.getLong(5);
		      rs = (OracleResultSet)proc.getObject(6);
		      BetOrderVO po=null;
		      List<BetOrderVO> resultList=new ArrayList<BetOrderVO>();
		      while(rs.next()){
		    	  po=new BetOrderVO();
		    	  betCategory=rs.getString("bet_categoty");
		    	  po.setBetCategory(betCategory);//彩种
			      String betUsername=rs.getString("bet_username");
			      po.setBetUsername(betUsername);
			      String type=rs.getString("type");
			      po.setType(type);//投注方式
			      
			      String allMoney=rs.getString("all_money");
			      po.setAllMoney(allMoney);
			      String bonus=rs.getString("bonus");
			      po.setBonus(bonus);
			      String orderStatus=rs.getString("order_status");
			      long alreadyBuyCopys=rs.getLong("already_buy_copys");
			      long divideCopys=rs.getLong("divide_copys");
			      long floorCopys=rs.getLong("floor_copys");
			      alreadyBuyCopys+=floorCopys;
			      if("4".equals(orderStatus)){
			    	  po.setStatus("已撤单");
			      }else{
			         if(alreadyBuyCopys>=divideCopys*0.9){
			    	    po.setStatus("已成功");
			         }else{
			    	    po.setStatus("失败");
			         }
			      }
			      long partiCnt=rs.getLong("partiCnt");
			      ++partiCnt;
			      po.setPartiCnt(partiCnt);//参与人数
			    resultList.add(po);
			 }
		      resultMap.put("resultList", resultList);
		      resultMap.put("totalCount", totalCount);
		     return resultMap;
		   }catch(Exception e){
			  e.printStackTrace();
		  }finally{
			  closeAll(rs,proc,conn);
		  }
	     
		return null;
	}
	/**
	 * 他的历史战绩  2010-04-20 14:20
	 * 参数:
	 * userId 用户ID
	 * 返回：
	 * 数组String[]:
	 * 胜负14场:
	 *   发单中奖次数:String[0]
	 *   总奖金:String[1]
	 * 任选9场:
	 *   发单中奖次数:String[2]
	 *   总奖金:String[3]
	 * 单场足彩:
     *   发单中奖次数:String[4]
	 *   总奖金:String[5]
	 *   
	 * 6场半全场:
     *   发单中奖次数:String[6]
	 *   总奖金:String[7]
	 * 4场进球
     *   发单中奖次数:String[8]
	 *   总奖金:String[9]
	 */
	public String[] loadHistoryMilitary(Long userId)throws DaoException{
	        Connection conn=null;
	        CallableStatement proc=null;
	        ResultSet rs=null;
	        try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      String sql = "{call history_military.loadHistoryMilitary(?,?)}";
		      proc   =(CallableStatement)conn.prepareCall(sql);    
		      proc.setLong(1, userId);
		      proc.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
		      proc.execute();
		      String introduction=proc.getString(2);
		      
		    return introduction.split(",");
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	closeAll(rs,proc,conn);
	        }
	        return null;
	}
	private static final Log log=LogFactory.getLog(HistoryMilitaryDaoImpl.class);

}
