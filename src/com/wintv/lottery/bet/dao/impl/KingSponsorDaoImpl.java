package com.wintv.lottery.bet.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.wintv.framework.common.OracleSqlUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.framework.pojo.User;
import com.wintv.lottery.bet.dao.KingSponsorDao;

@SuppressWarnings("unchecked")
@Repository("kingSponsorDao")
public class KingSponsorDaoImpl extends BaseHibernate<KingSponsor,Long> implements KingSponsorDao{
	public boolean  updateKingSponsor(long kingUserId)throws DaoException{
        String sql="update t_king_sponsor t  set t.already_dz_cnt=t.already_dz_cnt+1 where t.userid=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, kingUserId);
			pstmt.executeUpdate();
		 return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return false;
	
	}
	private static final Log log=LogFactory.getLog(KingSponsorDaoImpl.class);
	/**其他界面要用到自动跟单的详细列表通过3个参数读取一条自动跟单的详细信息  2010-04-29 09:46**/
	public KingSponsor  loadAutoOrder(Long userId,String betCategory,String flg)throws DaoException{
		StringBuilder sql=new StringBuilder("select a.id,a.zh_desc,a.username,a.already_dz_cnt,decode(a.status,'1','尚未审核','2','审核通过','3','审核未通过') status,decode(a.bet_category,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') betCategory,decode(a.flg,'1','单式合买','2','复式合买') flg from T_KING_SPONSOR a where a.userid=? and ");
		sql.append("  a.bet_category=? and a.flg=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		KingSponsor po=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, userId);
			pstmt.setString(2,betCategory);
			pstmt.setString(3,flg);
			rs=pstmt.executeQuery();
			while(rs.next()){
				po=new KingSponsor();
				String username=rs.getString("username");
				po.setUsername(username);
				po.setAlreadyDzCnt(rs.getLong("already_dz_cnt"));
				po.setBetCategory(rs.getString("betCategory"));
				po.setType(rs.getString("flg"));
				po.setZhDesc(rs.getString("zh_desc"));
				po.setStatus(rs.getString("status"));
				po.setId(rs.getLong("id"));
		   }
		 return po;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return null;
	}
	/**判断用户是否已经申请某彩种的玩法   2010-04-23 11:40
	 * 返回值:
	 *   false:已经申请,本次不能再申请
	 *   true:本次可以申请
	 **/
	public boolean  isAlreadyApply(Long userId,String betCategory,String flg)throws DaoException{
		StringBuilder sql=new StringBuilder("select count(1)  from T_KING_SPONSOR a where a.userid=? and ");
		sql.append(" (a.status='0' or a.status='1') and a.bet_category=? and a.flg=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, userId);
			pstmt.setString(2,betCategory);
			pstmt.setString(3,flg);
			rs=pstmt.executeQuery();
			while(rs.next()){
				return false;
		   }
		 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return true;
	}
	/**查询用户已经申请的彩种  以及玩法 2010-04-23 11:28**/
	public List<KingSponsor>  findAlreadyApplyCategoryList(Long userId)throws DaoException{
		StringBuilder sql=new StringBuilder("select a.bet_category,a.status,a.flg");
		sql.append("from T_KING_SPONSOR a where a.userid=?  ");
		//sql.append(" and (b.status='0' or b.status='1')");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<KingSponsor> resultList=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, userId);
			rs=pstmt.executeQuery();
			KingSponsor po=null;
			resultList=new ArrayList<KingSponsor>();
			while(rs.next()){
				String betCategory=rs.getString("bet_category");
				String status=rs.getString("status");
				po=new KingSponsor();
				po.setBetCategory(betCategory);
				po.setStatus(status);
				String type=rs.getString("flg");
				po.setType(type);
			  resultList.add(po);
		   }
		return resultList;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return null;
	}
	/**根据用户ID  查询主键  2010-04-22 17:39**/
	public Long  loadKingSponsorId(Long userId)throws DaoException{
		String sql="select t.id from t_king_sponsor t where t.userid=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, userId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				return rs.getLong("id");
		   }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return null;
	   
	}
	public long  findKingSize(Map params)throws DaoException{
		StringBuilder sql=new StringBuilder("select  count(1) cnt");
        sql.append("   from  T_KING_SPONSOR a where b.status='1' ");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{	
		    conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
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
	/**
	 * 自动跟单-我要跟单列表  2010-04-19 15:20
	 *betCategory:
	 *type:'1' '2'
	 *flg  是否满额
	 */
	public List<KingSponsor>  findKingList(Map params)throws DaoException{
		StringBuilder sql=new StringBuilder("select  b.userid,a.bet_category betCategoryValue,decode(a.bet_category,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') betCategory");
        sql.append(",b.bet_military,a.username,a.flg typeValue,decode(a.flg,'1','单式合买','2','复式合买') flg,a.ALREADY_DZ_CNT ,a.DZ_CNT,(select count(1)  from t_bet_order  bo where bo.sponsor_bet_id=a.userid) gd_cnt,NVL((select sum(bo.subscribe_money)  from t_bet_order  bo where bo.sponsor_bet_id=a.userid),0) gd_money   from  T_KING_SPONSOR a,T_USER b where a.status='1' and a.userid=b.userid ");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<KingSponsor> resultList=new ArrayList<KingSponsor>();
		try{	
			Integer startRow=(Integer)params.get("startRow");
			Integer pageSize=(Integer)params.get("pageSize");
			String betCategory=(String)params.get("betCategory");
			if(StringUtils.isNotEmpty(betCategory)){
				sql.append(" and a.bet_category='"+betCategory+"' ");
			}
			String type=(String)params.get("type");
			if(StringUtils.isNotEmpty(type)){
				sql.append(" and a.flg='"+type+"' ");
			}
			
			
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			sql=OracleSqlUtil.addQueryPageSizeCondition(sql, startRow, pageSize);
			
		
			pstmt=conn.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			KingSponsor po=null;
			while(rs.next()){
				po=new KingSponsor();
				String _betCategory=rs.getString("betCategory");//彩种
				po.setBetCategory(_betCategory);
				String _type=rs.getString("flg");//投注方式 显示汉字
				po.setType(_type);
				
				int gdCnt=rs.getInt("gd_cnt");//总跟单人次
				po.setGdCnt(gdCnt);
				 
				BigDecimal gdMoney=rs.getBigDecimal("gd_money");//跟单总金额
				po.setGdMoney(gdMoney);
				Long alreadyDzCnt=rs.getLong("ALREADY_DZ_CNT");//已经定制的人数
				po.setAlreadyDzCnt(alreadyDzCnt);
				Long dzCnt=rs.getLong("DZ_CNT");//最大定制人数,此值从字典表里获取,修改此值时必须用同步方法
				po.setDzCnt(dzCnt);
				String username=rs.getString("username");//金牌发起人用户名
				po.setUsername(username);
				long betMilitary=rs.getLong("bet_military");//投注战绩
				po.setBetMilitary(betMilitary);
				String betCategoryValue=rs.getString("betCategoryValue");
				po.setBetCategoryValue(betCategoryValue);//彩种数值   1,2,3,5,61,62,63,64,65  2010-04-29 13:50新增
				String typeValue=rs.getString("typeValue");
				po.setTypeValue(typeValue); //只显示数字 2010-04-29 13:50新增
				Long userid=rs.getLong("userid");
				po.setUserid(userid);
			  resultList.add(po);
		    }
		}catch(Exception e){
		   e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return resultList;
	}
	
	/**判断是否金牌发起人  2010-04-12 11:25**/
	public  long isKingSponsor(Long userId)throws DaoException{
        String sql="select  count(1) cnt from  T_KING_SPONSOR t where t.status='1' and t.USERID="+userId;
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
			
				return rs.getLong("cnt");
		   }
		}catch(Exception e){
			
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return 0L;
	}
	public KingSponsor  loadKingSponsorProperties(Long kingId)throws DaoException{
		String sql="select t.DZ_CNT,t.ALREADY_DZ_CNT from  T_KING_SPONSOR t.ID=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		KingSponsor po=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, kingId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				po=new KingSponsor();
				Long dzCnt=rs.getLong("DZ_CNT");
				po.setDzCnt(dzCnt);
				Long alreadyDZCnt=rs.getLong("ALREADY_DZ_CNT");
				po.setAlreadyDzCnt(alreadyDZCnt);
			}
		}catch(Exception e){
			
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return po;
	   
	}
	/**加载用户的投注战绩 用户名**/
	public User  loadUser(Long userId)throws DaoException{
		String sql="select t.bet_military,t.username,t.title  from T_USER t  where t.userid=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		User po=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, userId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				po=new User();
				long betMilitary=rs.getLong("BET_MILITARY");
				po.setBetMilitary(betMilitary);
				String username=rs.getString("username");
				po.setUsername(username);
				String title=rs.getString("TITLE");
				if(StringUtils.isNotEmpty(title)){
					po.setTitle(title);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	 return po;
   }

}
