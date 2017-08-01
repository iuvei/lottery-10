package com.wintv.lottery.admin.bet.dao.impl;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.lottery.admin.bet.dao.KingAdminSponsorDaoDao;
import com.wintv.lottery.admin.bet.vo.KingAdminSponsorVO;
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
/**
 * 3.2.7自动跟单后台管理 文档 58页
*
 * @author Administrator
 *
 */
@Repository("kingAdminSponsorDaoDao")
public class KingAdminSponsorDaoImpl extends BaseHibernate<KingSponsor,Long> implements KingAdminSponsorDaoDao{
	/**3.2.7自动跟单后台管理 文档-金牌发起人管理列表 58页**/
	public Map findList(Map params)throws DaoException{
	        Connection conn=null;
			CallableStatement proc=null;
			ResultSet rs=null;
			int startRow=(Integer)params.get("startRow");
			int pageSize=(Integer)params.get("pageSize");
			String betCategory=(String)params.get("betCategory");//彩种
			String gdStatus=(String)params.get("gdStatus");//定制状态 定制自动跟单状态    '1':未满额   '2':已满额
		    String key=(String)params.get("key");//关键字
			List<KingAdminSponsorVO> resultList=null;
			Map resultMap=null;
		    try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      StringBuilder queryList=new StringBuilder("  select a.id,a.bet_categoty,a.username,a.BET_MILITARY ,a.DZ_CNT");
		      queryList.append(",a.ALREADY_DZ_CNT,");
		      queryList.append(" (select count(1) from T_BET_ORDER c  where  c.KING_ID=a.ID ) all_gd_cnt, ");//总跟单人次
		      queryList.append(" (select count(c.SUBSCRIBE_MONEY) from T_BET_ORDER c  where  c.KING_ID=a.ID ) all_gd_money ");//跟单总金额
		      queryList.append(" from  T_KING_SPONSOR a  where 1=1 ");
		      
		      
		      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from T_KING_SPONSOR b ");
		      querySize.append("  where 1=1 ");
		      if(StringUtils.isNotEmpty(betCategory)){
		    	  queryList.append("  and a.BET_CATEGOTY='"+betCategory+"'");
		    	  querySize.append("  and b.BET_CATEGOTY='"+betCategory+"'");
		      }
		      if("1".equals(gdStatus)){//
		    	  queryList.append("  and a.DZ_CNT>a.ALREADY_DZ_CNT ");
		    	  querySize.append("  and a.DZ_CNT>a.ALREADY_DZ_CNT ");
		      }else if("2".equals(gdStatus)){
		    	  queryList.append("  and a.DZ_CNT=a.ALREADY_DZ_CNT ");
		    	  querySize.append("  and a.DZ_CNT=a.ALREADY_DZ_CNT ");
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
		      resultList=new ArrayList<KingAdminSponsorVO>();
		      KingAdminSponsorVO vo=null;
		      while (rs.next()) {
		    	 vo=new KingAdminSponsorVO();
		         Long id=rs.getLong("id");
		         vo.setId(id);
		         betCategory=rs.getString("bet_categoty");
		         vo.setBetCategory(betCategory);
		         String username=rs.getString("username");
		         vo.setUsername(username);
		        
		         int alreadyDzCnt=rs.getInt("ALREADY_DZ_CNT");//已定制
		         vo.setAlreadyDzCnt(alreadyDzCnt);
		         int dzCnt=rs.getInt("DZ_CNT");
		         vo.setDzCnt(dzCnt);
		         Long allGdCnt=rs.getLong("all_gd_cnt");//总跟单人次
		         vo.setAllGdCnt(allGdCnt);
		         String allGdMoney=rs.getString("all_gd_money");//跟单总金
		         vo.setAllGdMoney(allGdMoney);
		         if(alreadyDzCnt==dzCnt){
		        	  gdStatus="未满额";
		        	  vo.setGdStatus(gdStatus);
		         }else{
		        	 gdStatus="已满额";
		        	  vo.setGdStatus(gdStatus);
		         }
		         int betMilitary=rs.getInt("BET_MILITARY");
		         StringBuffer  stars=new StringBuffer();
		         for(int i=0;i<betMilitary;i++){
		        	 stars.append("★");
		         }
		         vo.setBetMilitary(stars.toString());
		         
		        
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
	/**金牌发起人管理详情  59页 2010-03-11 14:07**/
	public KingSponsor  loadKingSponsor(Map params)throws DaoException{
	        Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			 KingSponsor po=null;
		    try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      Long kingId=(Long)params.get("kingId");
		      StringBuilder sql=new StringBuilder("  select a.id,a.bet_category,a.username ,a.ZH_DESC");
		      sql.append(" from  T_KING_SPONSOR a  where ID="+kingId);
		      log.info("王"+sql.toString());
		      pstmt=conn.prepareStatement(sql.toString());
		      rs=pstmt.executeQuery();
		     while (rs.next()) {
		    	  po=new KingSponsor();
		    	  String username=rs.getString("username");
		    	  po.setUsername(username);
		    	  String betCategory=rs.getString("bet_category");
		    	  po.setBetCategory(betCategory);
		    	  String zhDesc=rs.getString("ZH_DESC");
		    	  po.setZhDesc(zhDesc);
		     }
		    
		    }catch(Exception e){
		    	e.printStackTrace();
		    }finally{
		    	closeAll(rs,pstmt,conn);
		    }
		    return po;
	
	}
	//-------------------金牌发起人审核列表 60页---------------------
	public Map findKingAuthList(Map params)throws DaoException{
        Connection conn=null;
		CallableStatement proc=null;
		ResultSet rs=null;
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		String betCategory=(String)params.get("betCategory");//彩种
		String gdStatus=(String)params.get("gdStatus");//定制状态 定制自动跟单状态    '1':未满额   '2':已满额
	    String key=(String)params.get("key");//关键字
		List<KingAdminSponsorVO> resultList=null;
		Map resultMap=null;
	    try{
	      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	      StringBuilder queryList=new StringBuilder("  select a.id,a.bet_categoty,a.username,a.BET_MILITARY ,a.DZ_CNT");
	      queryList.append(",a.ALREADY_DZ_CNT,");
	      queryList.append(" (select count(1) from T_BET_ORDER c  where  c.KING_ID=a.ID ) all_gd_cnt, ");//总跟单人次
	      queryList.append(" (select count(c.SUBSCRIBE_MONEY) from T_BET_ORDER c  where  c.KING_ID=a.ID ) all_gd_money ");//跟单总金额
	      queryList.append(" from  T_KING_SPONSOR a  where 1=1 ");
	      
	      
	      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from T_KING_SPONSOR b ");
	      querySize.append("  where 1=1 ");
	      if(StringUtils.isNotEmpty(betCategory)){
	    	  queryList.append("  and a.BET_CATEGOTY='"+betCategory+"'");
	    	  querySize.append("  and b.BET_CATEGOTY='"+betCategory+"'");
	      }
	      if("1".equals(gdStatus)){//
	    	  queryList.append("  and a.DZ_CNT>a.ALREADY_DZ_CNT ");
	    	  querySize.append("  and a.DZ_CNT>a.ALREADY_DZ_CNT ");
	      }else if("2".equals(gdStatus)){
	    	  queryList.append("  and a.DZ_CNT=a.ALREADY_DZ_CNT ");
	    	  querySize.append("  and a.DZ_CNT=a.ALREADY_DZ_CNT ");
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
	      resultList=new ArrayList<KingAdminSponsorVO>();
	      KingAdminSponsorVO vo=null;
	      while (rs.next()) {
	    	 vo=new KingAdminSponsorVO();
	         Long id=rs.getLong("id");
	         vo.setId(id);
	         betCategory=rs.getString("bet_categoty");
	         vo.setBetCategory(betCategory);
	         String username=rs.getString("username");
	         vo.setUsername(username);
	        
	         int alreadyDzCnt=rs.getInt("ALREADY_DZ_CNT");//已定制
	         vo.setAlreadyDzCnt(alreadyDzCnt);
	         int dzCnt=rs.getInt("DZ_CNT");
	         vo.setDzCnt(dzCnt);
	         Long allGdCnt=rs.getLong("all_gd_cnt");//总跟单人次
	         vo.setAllGdCnt(allGdCnt);
	         String allGdMoney=rs.getString("all_gd_money");//跟单总金
	         vo.setAllGdMoney(allGdMoney);
	         if(alreadyDzCnt==dzCnt){
	        	  gdStatus="未满额";
	        	  vo.setGdStatus(gdStatus);
	         }else{
	        	 gdStatus="已满额";
	        	  vo.setGdStatus(gdStatus);
	         }
	         int betMilitary=rs.getInt("BET_MILITARY");
	         StringBuffer  stars=new StringBuffer();
	         for(int i=0;i<betMilitary;i++){
	        	 stars.append("★");
	         }
	         vo.setBetMilitary(stars.toString());
	         
	        
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
	private static final Log log=LogFactory.getLog(KingAdminSponsorDaoImpl.class);

}
