package com.wintv.lottery.bet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.BetHotSearch;
import com.wintv.lottery.bet.dao.HotSearchDao;
import com.wintv.lottery.bet.vo.HotSearchVO;


@SuppressWarnings("unchecked")
@Repository("hotSearchDao")
public class HotSearchDaoImpl extends BaseHibernate<BetHotSearch,Long> implements HotSearchDao{
	@SuppressWarnings("unused")
	private static final Log log=LogFactory.getLog(HotSearchDaoImpl.class);
	/**保存热门搜索  2010-03-26 13:55**/
	public boolean   saveHotSponsors(List<HotSearchVO> list)throws DaoException{
		    StringBuilder sql=new StringBuilder("insert into T_BET_HOT_SEARCH(HOT_ID,USERID,USERNAME,BET_ID,BET_CATEGORY) values(SEQ_HOT_SEARCH_ID.nextval,?,?,?,?)");
	        Connection conn=null;
			PreparedStatement pstmt=null;
		    try{
		    	conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		    	pstmt=conn.prepareStatement(sql.toString());
		    	for(HotSearchVO po:list){
				   pstmt.setLong(1,po.getUserId());
				   pstmt.setString(2,po.getUsername());
				   pstmt.setLong(3,po.getBetId());
				   pstmt.setString(4,po.getBetCategory());
				   pstmt.addBatch();
		    	}
				int[] arr=pstmt.executeBatch();
				return arr.length==list.size();
			 }catch(Exception e){
				e.printStackTrace();
			 }finally{
				closeAll(pstmt,conn);
			 }
			return false;
	}
	
	
	/**热门搜索金牌发起人列表  2010-03-26 13:55**/
	public List<HotSearchVO>   findHotOrders(Long phaseId)throws DaoException{
		    StringBuilder sql=new StringBuilder("select distinct t.USERNAME,t.Userid from T_BET_HOT_SEARCH t  ");
		    sql.append(" where rownum<=5");
	        Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			List<HotSearchVO> resultList=null;
		    try{
		    	conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		    	pstmt=conn.prepareStatement(sql.toString());
		    	rs=pstmt.executeQuery();
		    	resultList=new ArrayList<HotSearchVO>();
		    	HotSearchVO po=null;
		    	while(rs.next()){
		    		po=new HotSearchVO();
		    		String username=rs.getString("USERNAME");
		    		po.setUsername(username);
		    		Long userId=rs.getLong("Userid");
		    		po.setUserId(userId);
		    		resultList.add(po);
		    	}
		    	
			 }catch(Exception e){
				e.printStackTrace();
			 }finally{
				closeAll(pstmt,conn);
			 }
			return resultList;
		}
	

}
