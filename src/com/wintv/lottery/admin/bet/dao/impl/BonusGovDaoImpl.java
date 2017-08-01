package com.wintv.lottery.admin.bet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.OracleSqlUtil;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.admin.bet.dao.BonusGovDao;
import com.wintv.lottery.admin.bet.vo.BonusGov;

/**
 * 开奖管理列表
 * @since 2010-05-05 13:37
 *
 */
@Repository("bonusGovDao")
public class BonusGovDaoImpl extends BaseHibernate implements BonusGovDao{
	/**
	 * 审核
	 * 参数：
	 *  id:主键
	 */
	public boolean updateBonusGov(Long id)throws DaoException{ 
		  Connection conn=null;
		  PreparedStatement pstmt=null;
		  try{
			  String sql="update t_bonus_gov t set t.status='1'  where t.id=?";
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  pstmt=conn.prepareStatement(sql.toString());
			  pstmt.setLong(1,id);
			  pstmt.executeUpdate();
		      return true;
		    }catch(SQLException e){
			  e.printStackTrace();
		  }finally{
			  closeAll(pstmt,conn);
		  }
		  return false;
	 }
	/**
	 *参数:
	 * lotteryType:'1':胜负14场、'2':任选9场、'3':4 场进球 '5':6 场半全场
	 * from:起始时间
	 * to:结束时间
	 * 
	 */
  public long findBonusGovSize(Map params)throws DaoException{

		 
	  Connection conn=null;
	  PreparedStatement pstmt=null;
	  ResultSet rs= null;
	  try{
		  String from=(String)params.get("from");//开奖日期起始时间
		  String to=(String)params.get("to");//开奖日期结束时间
		  String betCategory=(String)params.get("betCategory");//彩种
		 String phaseNo=(String)params.get("phaseNo");//期次号
		  String status=(String)params.get("status");//状态
		  StringBuilder sql=new StringBuilder("select count(1) cnt from t_bonus_gov t  where 1=1 ");
		  if(StringUtils.isNotEmpty(from)){
			  sql.append(" and t.kj_time>=to_date('"+from+"','yyyy-mm-dd') ");
		  }
		  if(StringUtils.isNotEmpty(to)){
			  sql.append(" and t.kj_time<=to_date('"+to+"','yyyy-mm-dd') ");
		  }
		  if(StringUtils.isNotEmpty(betCategory)){//彩种
			  sql.append(" and t.lottery_type='"+betCategory+"' ");
		  }
		  if(StringUtils.isNotEmpty(phaseNo)){//期次号
			  sql.append(" and t.phase='"+phaseNo+"' ");
		  }
		  if(StringUtils.isNotEmpty(status)){//状态
			  sql.append(" and t.status='"+status+"' ");
		  }
		  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		  pstmt=conn.prepareStatement(sql.toString());
		  rs=pstmt.executeQuery();
	      while(rs.next()){
	    	  return rs.getLong("cnt");
	      }
	    
	  }catch(SQLException e){
		  e.printStackTrace();
	  }finally{
		  closeAll(rs,pstmt,conn);
	  }
	  return 0;
  
  }
	/**
	 *参数:
	 * lotteryType:'1':胜负14场、'2':任选9场、'3':4 场进球 '5':6 场半全场
	 * from:起始时间
	 * to:结束时间
	 * 
	 */
  public List<BonusGov> findBonusGovList(Map params)throws DaoException{
	 
	  Connection conn=null;
	  PreparedStatement pstmt=null;
	  ResultSet rs= null;
	  List<BonusGov> resutList=null;
	  try{
		  String from=(String)params.get("from");//开奖日期起始时间
		  String to=(String)params.get("to");//开奖日期结束时间
		  String betCategory=(String)params.get("betCategory");//彩种
		  int startRow=(Integer)params.get("startRow");
		  int pageSize=(Integer)params.get("pageSize");
		  String phaseNo=(String)params.get("phaseNo");//期次号
		  String status=(String)params.get("status");//状态
		  StringBuilder sql=new StringBuilder("select t.id,to_char(t.kj_time,'yyyy-mm-dd') kj_time,t.lottery_name,t.phase,t.kj_no,t.money1,t.money2,t.status from t_bonus_gov t where 1=1 ");
		  if(StringUtils.isNotEmpty(from)){
			  sql.append(" and t.kj_time>=to_date('"+from+"','yyyy-mm-dd') ");
		  }
		  if(StringUtils.isNotEmpty(to)){
			  sql.append(" and t.kj_time<=to_date('"+to+"','yyyy-mm-dd') ");
		  }
		  if(StringUtils.isNotEmpty(betCategory)){//彩种
			  sql.append(" and t.lottery_type='"+betCategory+"' ");
		  }
		  if(StringUtils.isNotEmpty(phaseNo)){//期次号
			  sql.append(" and t.phase='"+phaseNo+"' ");
		  }
		  if(StringUtils.isNotEmpty(status)){//状态
			  sql.append(" and t.status='"+status+"' ");
		  }
		  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		  sql=OracleSqlUtil.addQueryPageSizeCondition(sql,startRow,pageSize);
		 
		  pstmt=conn.prepareStatement(sql.toString());
		  rs=pstmt.executeQuery();
	      BonusGov po=null;
	      resutList=new ArrayList<BonusGov>();
	      while(rs.next()){
	    	  po=new BonusGov();
	    	  long id=rs.getLong("id");
	    	  po.setId(id);
	    	  String kjTime=rs.getString("kj_time");
	    	  po.setKjTime(kjTime);
	    	  String lotteryName=rs.getString("lottery_name");
	    	  po.setLotteryName(lotteryName);
	    	  phaseNo=rs.getString("phase");
	    	  po.setPhaseNo(phaseNo);
	    	  String kjNo=rs.getString("kj_no");
	    	  po.setKjNo(kjNo);
	    	  String money1=rs.getString("money1");
	    	  po.setMoney1(money1);
	    	  String money2=rs.getString("money2");
	    	  po.setMoney2(money2);
	    	resutList.add(po);
	      }
	     return resutList;
	  }catch(SQLException e){
		  e.printStackTrace();
	  }finally{
		  closeAll(rs,pstmt,conn);
	  }
	  return null;
  }
}
