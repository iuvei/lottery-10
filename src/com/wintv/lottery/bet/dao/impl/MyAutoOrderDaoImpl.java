package com.wintv.lottery.bet.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oracle.jdbc.OracleTypes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.framework.pojo.MyAutoOrder;
import com.wintv.lottery.bet.dao.MyAutoOrderDao;
import com.wintv.lottery.bet.vo.MyAutoOrderVO;
@SuppressWarnings("unchecked")
@Repository("myAutoOrderDao")
public class MyAutoOrderDaoImpl extends BaseHibernate  implements MyAutoOrderDao{
  private static final Log log=LogFactory.getLog(MyAutoOrderDaoImpl.class);
  /**普通用户定制自动跟单  2010-05-07 09:35
   * 1:定制成功
   * 3:已经定制过该金牌发起人的彩种,玩法
   * 2:已经定制满员 不能再定制
   * -1:系统报错
   **/
  public int  saveMyAutoOrder(MyAutoOrder po)throws DaoException{
        Connection conn=null;
		CallableStatement proc=null;
		try{
	        String sql = "{call BET_ENHANCEMENT.saveMyAutoOrder(?,?,?,?,?,?,?,?,?,?,?)}";
	        conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        proc = conn.prepareCall(sql);
	        proc.setLong(1,po.getKingId());
	        proc.setLong(2,po.getUserid());
	        proc.setBigDecimal(3,po.getMinMoney());
	        proc.setBigDecimal(4, po.getMaxMoney());
	        proc.setBigDecimal(5, po.getTxMoney());
	        proc.setString(6, po.getUsername());
	        proc.setString(7,po.getType());//投注方式  '1':单式  '2':复式
	        proc.setString(8,po.getIsLackOrder());//认购金额不足是否认购
	        proc.setLong(9,po.getKingUserId());//金牌发起人用户ID
	        proc.setString(10,po.getCategory());
	        proc.registerOutParameter(11,OracleTypes.NUMBER);
	        proc.execute();
	        return proc.getInt(11);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(proc,conn);
	    }
	    return -1;
	
  }
  /**是否允许跟单**/
  public int  isAllowGD(Long userId)throws DaoException{
		String sql="select count(1) cnt from t_king_sponsor t where t.userid=? and t.gd_status='1'  and t.already_dz_cnt<t.dz_cnt";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{	
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, userId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				long cnt=rs.getLong("cnt");
				if(cnt>0){
					return 1;
				}
		    }
		}catch(Exception e){
		   e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return 0;
	   
	}
	/**自动跟单-自动跟单人 2010-04-12  15:09**/
   public List findAutoOrderListByCategory(Map params)throws DaoException{
	Long kingUserId=(Long)params.get("kingUserId");
	String betCategory=(String)params.get("betCategory");
	StringBuilder sql=new StringBuilder("select  a.order_num,a.username,a.tx_money,a.type,a.order_time,a.category ");
	sql.append(" from  T_MY_AUTO_ORDER a ");
	sql.append(" where a.KING_USERID="+kingUserId+"  and a.category='"+betCategory+"'");
	log.info("***********************"+sql.toString());
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	List<MyAutoOrderVO> resultList=null;
	try{	
		conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		pstmt=conn.prepareStatement(sql.toString());
		//pstmt.setLong(1, kingUserId);
		//pstmt.setString(2, betCategory);
		rs=pstmt.executeQuery();
		resultList=new ArrayList<MyAutoOrderVO>();
		MyAutoOrderVO po=null;
		while(rs.next()){
			po=new MyAutoOrderVO();
			long orderNum=rs.getLong("order_num");
		    po.setOrderNum(orderNum);
		    String username=rs.getString("username");
		    po.setUsername(username);
		    String subscribeMoney=rs.getString("tx_money");//认购金额
		    po.setSubscribeMoney(subscribeMoney);
		    String type=rs.getString("type");
		    po.setType(type);
		    String orderTime=rs.getString("order_time");//定制时间
		    po.setOrderTime(orderTime);
		  resultList.add(po);
	    }
	}catch(Exception e){
	   e.printStackTrace();
	}finally{
		closeAll(rs,pstmt,conn);
	}
	log.info("resultList.size()="+resultList.size());
	return resultList;

	}

	/**我的跟单管理统计总记录数  分页使用 2010-04-12 13:31
	 * 参数:
	 * commonUserId:普通用户ID
	 **/
	public long findMyAutoOrderSize(Long commonUserId)throws DaoException{
		StringBuilder sql=new StringBuilder("select count(1) cnt from ");
		sql.append(" T_MY_AUTO_ORDER a,T_KING_SPONSOR b,T_MY_AUTO_ORD_CATEGORY c");
		sql.append(" where a.KING_ID=b.ID and a.AUTO_ORDER_ID=c.AUTO_OR_ID  ");
		sql.append(" and  a.USERID=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<MyAutoOrderVO> resultList=null;
		try{	
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, commonUserId);
			rs=pstmt.executeQuery();
		    while(rs.next()){
			return rs.getLong("cnt");
		    }
		}catch(Exception e){
		   e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		return 0L;
	}
	/**我的跟单管理  2010-04-12 13:31
	 * 参数:
	 * commonUserId:普通用户ID
	 **/
	public List<MyAutoOrderVO> findMyAutoOrderList(Long commonUserId)throws DaoException{
		StringBuilder sql=new StringBuilder("select a.AUTO_ORDER_ID,b.USERNAME,a.CATEGORY,a.TYPE,a.MIN_MONEY,a.MAX_MONEY,a.TX_MONEY,a.IS_LACK_ORDER  from ");
		sql.append(" T_MY_AUTO_ORDER a,T_KING_SPONSOR b ");
		sql.append(" where a.KING_ID=b.ID and  a.USERID=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<MyAutoOrderVO> resultList=null;
		try{	
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, commonUserId);
			rs=pstmt.executeQuery();
			resultList=new ArrayList<MyAutoOrderVO>();
			MyAutoOrderVO po=null;
			while(rs.next()){
				po=new MyAutoOrderVO();
			    Long autoOrderId=rs.getLong("AUTO_ORDER_ID");//主键
			    po.setAutoOrderId(autoOrderId);
			    String kingUserName=rs.getString("USERNAME");//金牌发起人用户名
			    po.setKingUserName(kingUserName);
			    String category=rs.getString("CATEGORY");
			    po.setCategory(category);
			    String type=rs.getString("TYPE");//投注方式
			    po.setType(type);
			    String moneyRestrict;//金额限制
			    String subscribeMoney=rs.getString("TX_MONEY");//认购金额
			    String isLackOrder=rs.getString("IS_LACK_ORDER");//认购金额不足是否认购
			    po.setIsLackOrder(isLackOrder);
			  resultList.add(po);
		    }
		}catch(Exception e){
		   e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		return resultList;
	}
	/**返回某一金牌发起人的被跟单总次数
	 * 参数:
	 * kingUserId:金牌发起人用户ID
	 */
	public long  loadGDCntByKingUserId(Long kingUserId)throws DaoException{
		String sql="select count(1)  cnt from  T_MY_AUTO_ORDER t where  t.king_userid=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{	
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, kingUserId);
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
	/**判断当前用户是否为跟单用户  2010-04-02 11:16**/
	public boolean  isGenDanUser(Long kingUserId,Long commonUserId)throws DaoException{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select  count(1) cnt from T_MY_AUTO_ORDER a,T_KING_SPONSOR b where  a.STATUS='1'  and b.STATUS='1'  and a.king_id=b.id and b.userid="+kingUserId+" and a.userid="+commonUserId;
		
		try{
	        conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        pstmt=conn.prepareStatement(sql);
	     
	        rs=pstmt.executeQuery();
	        while(rs.next()){
	        	return  rs.getInt("cnt")>0?true:false;
	        }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
	    return false;
	} 
	/**添加自动跟单  2010-03-22 16:34**/
	public boolean  executeGz(Map params)throws DaoException{
		Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		Long spOrderId=(Long)params.get("spOrderId");
	    Long userId=(Long)params.get("sponsorUserId");
		BigDecimal  singleMoney=(BigDecimal)params.get("singleMoney");
		String type=(String)params.get("type");
		String categoryType=(String)params.get("categoryType");
	    try{
	        String sql = "{ call bet.autoGendan(?,?,?,?,?)}";
	        conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        proc = conn.prepareCall(sql);
	        proc.setLong(1, spOrderId);
	        proc.setLong(2, userId);
	        proc.setBigDecimal(3, singleMoney);
	        proc.setString(4, type);
	        proc.setString(5, categoryType);
	        return proc.execute();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,proc,conn);
	    }
	    return false;
	}
	/**统计某一超级发起人的跟单总数**/
	public long  loadGDCnt(Long userId)throws DaoException{
		String sql="select count(1)  cnt from  T_MY_AUTO_ORDER t where  t.king_id=( select b.id from  t_king_sponsor b where b.userid=?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{	
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, userId);
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
	
	/**金牌发起人管理----各彩种跟单管理2010-04-06 08:55**/
	public List  findKingCategory(Long userId)throws DaoException{
		StringBuilder sql=new StringBuilder("select  decode(a.bet_category,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') betCategory");
        sql.append(" ,decode(a.TYPE,'1','单式合买','2','复式合买') type,a.ALREADY_DZ_CNT ,a.DZ_CNT,(select count(1)  from t_bet_order  bo where bo.sponsor_bet_id=a.userid) gd_cnt,(select sum(bo.subscribe_money)  from t_bet_order  bo where bo.sponsor_bet_id=a.userid) gd_money   from  T_KING_SPONSOR a where a.status='1' ");
		sql.append(" and a.userid=?");
		//log.info("测试="+sql.toString());
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<KingSponsor> resultList=new ArrayList<KingSponsor>();
		try{	
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, userId);
			rs=pstmt.executeQuery();
			KingSponsor po=null;
			while(rs.next()){
				po=new KingSponsor();
				String betCategory=rs.getString("betCategory");//彩种
				po.setBetCategory(betCategory);
				String type=rs.getString("type");//投注方式
				po.setType(type);
				int gdCnt=rs.getInt("gd_cnt");//总跟单人次
				po.setGdCnt(gdCnt);
				log.info("跟单="+gdCnt);
				BigDecimal gdMoney=rs.getBigDecimal("gd_money");//跟单总金额
				po.setGdMoney(gdMoney);
				Long alreadyDzCnt=rs.getLong("ALREADY_DZ_CNT");//已经定制的人数
				po.setAlreadyDzCnt(alreadyDzCnt);
				
			
				Long dzCnt=rs.getLong("DZ_CNT");//最大定制人数,此值从字典表里获取,修改此值时必须用同步方法
				po.setDzCnt(dzCnt);
			  resultList.add(po);
		    }
		}catch(Exception e){
		   e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return resultList;
	   
	}
	/**金牌发起人资格审核管理  2010-04-06 09:25**/
	public List<KingSponsor>  findKingCategoryAudit(Long userId)throws DaoException{
		
		StringBuilder sql=new StringBuilder("select  decode(a.bet_category,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_category,");
        sql.append("decode(a.TYPE,'1','单式合买','2','复式合买') lottery_type,");
        sql.append("decode(a.status,'0','未审核','1','审核通过','2','审核未通过') lottery_status ");
        sql.append("from t_king_sponsor a  where a.status='2' and a.userid="+userId);
        //log.info(sql.toString());
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<KingSponsor> resultList=new ArrayList<KingSponsor>();
		try{	
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			//pstmt.setLong(1, userId);
			rs=pstmt.executeQuery();
			KingSponsor po=null;
			while(rs.next()){
				po=new KingSponsor();
				String betCategory=rs.getString("bet_category");//彩种
				po.setBetCategory(betCategory);
				String type=rs.getString("lottery_type");//投注方式
				po.setType(type);
				String lotteryStatus=rs.getString("lottery_status");
				po.setStatus(lotteryStatus);
			  resultList.add(po);
		    }
		}catch(Exception e){
		   e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return resultList;
	   
	}
}
