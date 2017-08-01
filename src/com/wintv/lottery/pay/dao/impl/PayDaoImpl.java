package com.wintv.lottery.pay.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.pay.dao.PayDao;

@Repository("payDao")
public class PayDaoImpl extends BaseHibernate implements PayDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	 /**
	  * 心水后台确认支付
	  *参数：
	  *  orderId：心水订单ID,对应表T_XINSHUI_ORDER的主键
	  */
     public String backendXinshuiConfirmPay( Long orderId) throws DaoException {
		Connection conn=null;
   	    CallableStatement cstmt =null;
		try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call XINSHUI_PAY.backendConfirmPay(?,?)}"); 
			  cstmt.setLong(1, orderId);
			  cstmt.registerOutParameter(2, OracleTypes.VARCHAR);   
			  cstmt.execute();
			 return cstmt.getString(2);
			
		} catch (SQLException e) { 
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
        return null;
	}
	
	
	@Override
	public String pay() throws DaoException {
		
		String sql = "select User_Id, User_Name, Address, ProvUser_Level, User_Phone, cRegion from tUser where User_Name like '%银行%'"; 
		String proc = "{call lottery.pay(?, ?, ?)}"; 
		//result out VARCHAR2,user_id in NUMBER,tx_money in NUMBER,type in char,useMosaicGold IN CHAR,order_id IN NUMBER
		Object obj = jdbcTemplate.execute(proc, new CallableStatementCallback() { 
		public Object doInCallableStatement(CallableStatement arg0) throws SQLException,  DataAccessException { 
		          arg0.setString(1, "1"); 
		          if(arg0.execute()){ 
		              ResultSet rs = arg0.getResultSet(); 
		              System.out.println(rs.next()); 
		          } 
		          return null; 
		        } 
		}); 

		return null;
	}
	/**
	 * 领导确认彩金，并更改用户的彩金余额
	 * @param caijinId
	 * @param auditReason
	 * @throws DaoException
	 */
	public void saveUserDonate(Long caijinId ,String auditReason)throws DaoException{
		Connection conn=null;
		CallableStatement cstmt=null;
		try{
		  conn =this.getSession().connection();
		  cstmt = conn.prepareCall("{call account.saveUserDonate(?,?)}"); 
		  cstmt.setLong(1,caijinId);
		  cstmt.setString(2, auditReason);
		  cstmt.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
			cstmt.close();
			conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * 心水前台支付 2010 02-23 15:28 第一次支付
	 * @return
	 */
	public String  siteXinshuiPay(Long c2cId,Long buyUserId,String useCaijin,String buyUserName)throws DaoException{
        Connection conn=null;
   	    CallableStatement cstmt =null;
		try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call XINSHUI_PAY.myXinshuiPay(?,?,?,?,?)}"); 
			  cstmt.setLong(1, c2cId);
			  cstmt.setLong(2, buyUserId);
			  cstmt.setString(3,useCaijin);
			  cstmt.registerOutParameter(4, OracleTypes.VARCHAR);   
			  cstmt.setString(5, buyUserName);
			  cstmt.execute();
			 return cstmt.getString(4);
			
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
      return null;
	
	}
	/**
	 * 心水前台支付 2010 03-01 11:48 续费
	 * @return
	 */
	public int  continueXinshuiPay(Long xinshuiOrderId,String resultValue)throws DaoException{
        Connection conn=null;
   	    CallableStatement cstmt =null;
		try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call XINSHUI_PAY.siteXinshuiPay(?,?)}"); 
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
	/**
	 * 心水前台支付 2010 02-26 10:57前台普通取消自己的心水订单,单订单支付状态必须是:'1',未支付
	 * 返回:
	 * '1':成功取消订单
	 * '-1':取消订单失败
	 */
	public int  cancelXinshuiPay(Long xinshuiOrderId,Long buyUserId)throws DaoException{
		System.out.println("******xinshuiOrderId="+xinshuiOrderId);
        Connection conn=null;
   	    CallableStatement cstmt =null;
		try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call XINSHUI_PAY.canXinshuiPay(?,?,?)}");
			  cstmt.setLong(1, xinshuiOrderId);
			  cstmt.setLong(2, buyUserId);
			  cstmt.registerOutParameter(3, OracleTypes.NUMBER);   
			  cstmt.execute();
			 return cstmt.getInt(3);
			
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
	/**
	 * 彩票模块-代购 2010 03-19 10:40
	 * 返回:
	 * '1':成功支付
	 * 
	 */
	public Map  lotteryDaiGouPay(Long betUserId,BigDecimal betMoney,String useCaijin,String categoryType)throws DaoException{
        Connection conn=null;
   	    CallableStatement cstmt =null;
   	    Map resultMap=null;
		try { 
			  resultMap=new HashMap();
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call LOTTERY_DAIGOU_PAY.bet_daigou_pay(?,?,?,?,?,?,?)}"); 
			  cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			  cstmt.setLong(2, betUserId);
			  cstmt.setBigDecimal(3, betMoney);
			  cstmt.setString(4, useCaijin);
			  cstmt.registerOutParameter(5, OracleTypes.INTEGER);
			  cstmt.setString(6, categoryType);
			  cstmt.registerOutParameter(7, OracleTypes.DOUBLE);
			  cstmt.execute();
			  long payResult=cstmt.getLong(5);
			  BigDecimal alreadyPay=cstmt.getBigDecimal(7);
			  long betOrderId=cstmt.getLong(1);
		      resultMap.put("payResult", payResult);
			  resultMap.put("alreadyPay", alreadyPay);
			  resultMap.put("betOrderId", betOrderId);
			  
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
      return  resultMap;
	
	}
	/**
	 * 彩票模块 用户合买彩票 2010 03-05 17:07
	 * 返回:
	 * '1':成功支付
	 * 
	 */
	public long[]  lotteryCoopPay(Long betUserId,BigDecimal betMoney,String useCaijin,String categoryType)throws DaoException{
		Connection conn=null;
   	    CallableStatement cstmt =null;
		try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call LOTTERY_DAIGOU_PAY.coop_pay(?,?,?,?,?,?)}"); 
			  cstmt.registerOutParameter(1, OracleTypes.INTEGER);  
			  cstmt.setLong(2, betUserId);
			  cstmt.setBigDecimal(3, betMoney);
			  cstmt.setString(4, useCaijin);
			  cstmt.registerOutParameter(5, OracleTypes.INTEGER);   
			  cstmt.setString(6, categoryType);
			  cstmt.execute();
			  long payResult=cstmt.getLong(5);
			  long betOrderId=cstmt.getLong(1);
			  long[] resultArr=new long[]{payResult,betOrderId};
			return resultArr;
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
      return null;
	
	}
	
	//-----------------------b2c心水支付   2010-03-16 09:36-----------------------------------------------------
	public int  b2CXinshuiPay(Long b2cOrderId)throws DaoException{
        Connection conn=null;
   	    CallableStatement cstmt =null;
		try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call B2C.b2CXinshuiPay(?,?)}"); 
			  cstmt.setLong(1, b2cOrderId);
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
	//b2c第一次支付    2010-04-16 11:12
	public int  b2cPayAtFirst(Map params)throws DaoException{
        Connection conn=null;
   	    CallableStatement cstmt =null;
   	    Long expertId=(Long)params.get("expertId");
   	    Long buyUserId=(Long)params.get("buyUserId");
   	    String useCaijin=(String)params.get("useCaijin");
   	    String buyUserName=(String)params.get("buyUserName");
   	    String orderType=(String)params.get("orderType");

		try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call B2C_PAY.b2c_pay_at_first(?,?,?,?,?,?)}"); 
			  cstmt.setLong(1, expertId);
			  cstmt.setLong(2, buyUserId);
			  cstmt.setString(3, useCaijin);
			  cstmt.registerOutParameter(4, OracleTypes.INTEGER);   
			  cstmt.setString(5, buyUserName);
			  cstmt.setString(6, orderType);
			  cstmt.execute();
		   return cstmt.getInt(4);
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
	//b2c第二次支付    2010-04-16 11:12
	public int  b2cPayContinue(Long b2cOrderId)throws DaoException{
        Connection conn=null;
   	    CallableStatement cstmt =null;
   	  
		try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call B2C_PAY.b2c_pay_continue(?,?)}"); 
			  cstmt.setLong(1, b2cOrderId);
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

	public static void main(String[] args) {
		PayDaoImpl p = new PayDaoImpl();
	}
}
