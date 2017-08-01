package com.wintv.lottery.attention.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.framework.common.OracleSqlUtil;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.attention.dao.MyAttentionDao;
import com.wintv.lottery.attention.vo.AttentionPlanVO;
import com.wintv.lottery.attention.vo.AttentionUserVO;
import com.wintv.lottery.attention.vo.AttentionXinshuiVO;
import com.wintv.lottery.bet.utils.CommUtil;


@SuppressWarnings("unchecked")
@Repository("myAttentionDao")
public class MyAttentionDaoImpl extends BaseHibernate implements MyAttentionDao {
	/**判断用户是否被当前用户关注 2010-04-12 15:18**/
	public String isAttention(Long userId,Long targetUserId)throws DaoException{
        String sql="select  count(1) cnt from T_MY_ATTENTION t  where t.USER_ID=? and t.TARGET_USERID=?";
        Connection conn=null;
		PreparedStatement pstmt=null;
	    try{
    	     conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setLong(1, userId);
			 pstmt.setLong(2, targetUserId);
			 ResultSet rs=pstmt.executeQuery(); 
			 while(rs.next()){
			   return rs.getLong("cnt")>0?"1":"0";
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return "0";
	
	}
	/**根据用户Id获取用户名 2010-04-12 15:18**/
	public String getUsernameByUserId(Long userId)throws DaoException{
        String sql="select  t.username from t_user t  where t.userid=?";
        Connection conn=null;
		PreparedStatement pstmt=null;
	    try{
    	     conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setLong(1, userId);
			 ResultSet rs=pstmt.executeQuery(); 
			 while(rs.next()){
			   return rs.getString("username");
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return null;
	
	}
	/**统计关注我的人数量 2010-04-12 09:48**/
	public long findMyAttentionSize(Long userId)throws DaoException{
		StringBuilder sql=new  StringBuilder("select  count(1) cnt from t_my_attention a  where a.TARGET_USERID=?");
        Connection conn=null;
		PreparedStatement pstmt=null;
	    try{
    	     conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setLong(1, userId);
			 ResultSet rs=pstmt.executeQuery(); 
			 while(rs.next()){
			   return rs.getLong("cnt");
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return 0;
	}
	/**取消关注  2010-04-05 15:53
	 *attentionId:主键
	 ***/
	public boolean cancelAttention(String  attentionIdList)throws DaoException{
		
        String sql="delete from T_MY_ATTENTION t where t.ATTENTION_ID  in ("+attentionIdList+")";
      
        
	    Connection conn=null;
		PreparedStatement pstmt=null;
		try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql);
			
			 return pstmt.executeUpdate()>=1;
		}catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return false;
	
	}
	/**关注人气榜 统计总记录数 2010-04-05 14:00**/
	public int findMyAttentionTop50Size(Map params)throws DaoException{
		StringBuilder queryList=new StringBuilder(" select count(1) cnt from (select distinct a.target_userid,b.username,b.xinshui_military,b.bet_military");
		queryList.append(" from t_my_attention  a,t_user b where a.target_userid=b.userid) ");
        Connection conn=null;
		PreparedStatement pstmt=null;
		try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(queryList.toString());
			 ResultSet rs=pstmt.executeQuery();
			 while(rs.next()){
				return rs.getInt("cnt");
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return 0;
	}
	/**关注人气榜 查询列表 2010-04-05 14:00**/
	public List<AttentionUserVO> findMyAttentionTop50List(Map params)throws DaoException{
		StringBuilder queryList=new StringBuilder(" select rownum,u.title,u.userid,u.username,b.attention_cnt from  T_USER u, (select    a.target_userid,count(a.target_userid) attention_cnt");
		queryList.append(" from t_my_attention  a group by a.target_userid ");
		queryList.append(" order by count(a.target_userid) ) b  where u.userid=b.target_userid order by  b.attention_cnt ");
	
        Connection conn=null;
		PreparedStatement pstmt=null;
		Integer startRow=(Integer)params.get("startRow");
		Integer pageSize=(Integer)params.get("pageSize");
		List<AttentionUserVO> resultList=new ArrayList<AttentionUserVO>();
	    StringBuilder sql=OracleSqlUtil.addQueryPageSizeCondition(queryList,startRow,pageSize);
		try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql.toString());
			 ResultSet rs=pstmt.executeQuery();
			 AttentionUserVO po=null;
			 while(rs.next()){
				 po=new AttentionUserVO();
				 long rownum=rs.getLong("rownum");
				 po.setRownum(rownum);
				 Long userid=rs.getLong("userid");
				 po.setUserId(userid);
			     String username=rs.getString("username");
			     po.setUsername(username);
				 String title=rs.getString("title");
				 po.setTitle(title);
				 long attentionCnt=rs.getLong("attention_cnt");
				 po.setAttentionCnt(attentionCnt);//关注人数
			   resultList.add(po);
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return resultList;
	}
	/**关注我的人总记录数统计 2010-04-05 14:00
	 * userId：当前用户ID
	 ***/
	public int findMyAttentionedUserSize(Map params)throws DaoException{
		
        String sql="select  count(1)cnt from t_my_attention a where a.target_userid=?";
        Connection conn=null;
		PreparedStatement pstmt=null;
	    Long userId=(Long)params.get("userId");
	    try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setLong(1, userId);
			 ResultSet rs=pstmt.executeQuery();
			
			 while(rs.next()){
				return rs.getInt("cnt");
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return 0;
	}
	/**关注我的人  查询列表 2010-04-05 14:00**/
	public List<AttentionUserVO> findMyAttentionedUserList(Map params)throws DaoException{
		StringBuilder queryList=new StringBuilder(" select  a.attention_id,a.user_id,a.username,b.title , ");
        queryList.append(" case when (select count(1) cnt2 from t_king_sponsor k,t_my_auto_order o where k.id=o.king_id and k.userid=a.target_userid and o.userid=a.user_id)>0 ");
        queryList.append(" then '是' else '否' end is_auto_order, ");
        queryList.append(" (select count(1) cnt3 from t_bet_order x start with x.bet_userid=a.target_userid connect by prior x.sponsor_bet_id=x.bet_id) participateCnt, ");
        queryList.append(" (select count(4) xinshuiCnt from t_xinshui_order xo where xo.sold_user_id=a.target_userid and xo.buy_user_id=a.user_id and xo.status in ('3','4')) buyMyXinshuiCnt ");
        queryList.append(" from t_my_attention  a,t_user b  where a.user_id=b.userid and a.target_userid=? ");
        Connection conn=null;
		PreparedStatement pstmt=null;
		Integer startRow=(Integer)params.get("startRow");
		Integer pageSize=(Integer)params.get("pageSize");
		Long userId=(Long)params.get("userId");
	    List<AttentionUserVO> resultList=new ArrayList<AttentionUserVO>();
	  
	    StringBuilder sql=OracleSqlUtil.addQueryPageSizeCondition(queryList,startRow,pageSize);
		try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setLong(1, userId);
			 ResultSet rs=pstmt.executeQuery();
			 AttentionUserVO po=null;
			 while(rs.next()){
				 po=new AttentionUserVO();
				 Long attentionId=rs.getLong("attention_id");
				 po.setAttentionId(attentionId);
				 Long userid=rs.getLong("user_id");
				 po.setUserId(userid);
			     String username=rs.getString("username");
			     po.setUsername(username);
				 String title=rs.getString("title");
				 po.setTitle(title);
				 String isAutoOrder=rs.getString("is_auto_order");
				 po.setIsAutoOrder(isAutoOrder);
				 int participateCnt=rs.getInt("participateCnt");
				 po.setParticipateCnt(participateCnt);
				 int buyMyXinshuiCnt=rs.getInt("buyMyXinshuiCnt");
				 po.setBuyMyXinshuiCnt(buyMyXinshuiCnt);
				resultList.add(po);
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return resultList;
	}
	/**我关注的人  查询列表 2010-04-05 14:00**/
	public int findMyAttentionUserSize(Map params)throws DaoException{
		String sql="select count(1) cnt from t_my_attention a where a.user_id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
        Long userId=(Long)params.get("userId");
	    try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setLong(1, userId);
			 ResultSet rs=pstmt.executeQuery();
			 while(rs.next()){
			   return rs.getInt("cnt");
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return 0;
	}
	/**我关注的人  查询列表 2010-04-05 14:00**/
	public List<AttentionUserVO> findMyAttentionUserList(Map params)throws DaoException{
		StringBuilder queryList=new StringBuilder(" select a.attention_id,a.target_userid,b.username,b.title,b.bet_military,b.xinshui_military ");
        queryList.append(" from t_my_attention a,t_user b  ");
        queryList.append(" where a.target_userid=b.userid and a.user_id=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		Integer startRow=(Integer)params.get("startRow");
		Integer pageSize=(Integer)params.get("pageSize");
		Long userId=(Long)params.get("userId");
	    List<AttentionUserVO> resultList=new ArrayList<AttentionUserVO>();
	    StringBuilder sql=OracleSqlUtil.addQueryPageSizeCondition(queryList,startRow,pageSize);
		try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setLong(1, userId);
			 ResultSet rs=pstmt.executeQuery();
			 AttentionUserVO po=null;
			 while(rs.next()){
				 po=new AttentionUserVO();
				 Long attentionId=rs.getLong("attention_id");
				 po.setAttentionId(attentionId);
				 Long targetUserId=rs.getLong("target_userid");
				 po.setTargetUserId(targetUserId);
			     String username=rs.getString("username");
			     po.setUsername(username);
				 String title=rs.getString("title");
				 po.setTitle(title);
				 int betMilitary=rs.getInt("bet_military");
				 po.setBetMilitary(betMilitary);
				 int xinshuiMilitary=rs.getInt("xinshui_military");
				 po.setXinshuiMilitary(xinshuiMilitary);
				resultList.add(po);
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return resultList;
	}
	/**我关注的方案  统计记录总数  2010-04-05 14:00**/
	public int findMyAttentionPlanSize(Map params)throws DaoException{
		StringBuilder sql=new  StringBuilder("select  count(1) cnt from t_my_attention a,t_bet_order o  ");
        sql.append("where a.target_userid=o.bet_userid  and o.sponsor_type='1'   and (o.type='2'  or o.type='4')  and a.user_id=?");
        Connection conn=null;
		PreparedStatement pstmt=null;
	    try{
    	   Long userId=(Long)params.get("userId");
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setLong(1, userId);
			 ResultSet rs=pstmt.executeQuery(); 
			 while(rs.next()){
			   return rs.getInt("cnt");
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return 0;
	}
	/**我关注的方案  查询列表 2010-04-05 14:00**/
	public List<AttentionPlanVO> findMyAttentionPlanList(Map params)throws DaoException{
		Long userId=(Long)params.get("userId");
		StringBuilder queryList=new StringBuilder("select a.bet_categoty, decode(a.bet_categoty,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_category ");
        queryList.append(",phase_no,a.type,a.bet_username,c.bet_military,a.all_money,round((a.already_buy_copys*100)/a.divide_copys,2)  progress,a.bet_id");
        queryList.append(" from t_bet_order  a,t_my_attention b,t_user c ");
        queryList.append(" where a.bet_userid=b.target_userid  and  b.target_userid=c.userid and a.sponsor_type='1' and b.user_id=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		Integer startRow=(Integer)params.get("startRow");
		Integer pageSize=(Integer)params.get("pageSize");
		List<AttentionPlanVO> resultList=new ArrayList<AttentionPlanVO>();
	    StringBuilder sql=OracleSqlUtil.addQueryPageSizeCondition(queryList,startRow,pageSize);
	  
		try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setLong(1, userId);
			 ResultSet rs=pstmt.executeQuery();
			 AttentionPlanVO po=null;
			 while(rs.next()){
				 po=new AttentionPlanVO();
				 Long id=rs.getLong("bet_id");
				 po.setId(id);
			     String lotteryCategory=rs.getString("bet_category");
			     po.setLotteryCategory(lotteryCategory);
				 String lotteryType=rs.getString("type");
				 po.setLotteryType(lotteryType);
				 String phaseNo=rs.getString("phase_no");
				 po.setPhaseNo(phaseNo);
				 String sponsorUsername=rs.getString("bet_username");
				 po.setSponsorUsername(sponsorUsername);
				 String stars=rs.getString("bet_military");
				 po.setStars(stars);
				 BigDecimal allMoney=rs.getBigDecimal("all_money");
				 po.setAllMoney(allMoney);
				 String progress=rs.getString("progress");
				 po.setProgress(progress);
				 String betCategoty=rs.getString("bet_categoty");
				 po.setBetCategoty(betCategoty);
				resultList.add(po);
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return resultList;
	}
	public boolean updateUserAttentionCnt(Long targetUserId)throws DaoException{
        StringBuilder sql=new StringBuilder("update t_user u set u.ATTENTION_CNT=u.ATTENTION_CNT+1 where u.USERID=?");
        sql.append(" where a.phase_id=b.id and b.phase_no=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		synchronized(targetUserId){
		  try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setLong(1,targetUserId);
			return pstmt.execute();
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
		}
		return false;
    }
	/**我关注的人-当前方案列表 2010-04-09 10:50**/
	public List<AttentionPlanVO> findCurPlanList(Map params)throws DaoException{
		StringBuilder queryList=new StringBuilder("select decode(a.bet_categoty,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_category ");
        queryList.append(",phase_no,a.type,a.bet_username,c.bet_military,a.all_money,(a.already_buy_copys*100)/a.divide_copys  progress,a.bet_id");
        queryList.append(" from t_bet_order a,t_user c ");
        queryList.append(" where a.bet_userid=c.userid and a.sponsor_type='1' and b.user_id=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		Long userId=(Long)params.get("userId");
	    List<AttentionPlanVO> resultList=new ArrayList<AttentionPlanVO>();
	    try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(queryList.toString());
			 pstmt.setLong(1, userId);
			 ResultSet rs=pstmt.executeQuery();
			 AttentionPlanVO po=null;
			 while(rs.next()){
				 po=new AttentionPlanVO();
				 Long id=rs.getLong("bet_id");
				 po.setId(id);
			     String lotteryCategory=rs.getString("bet_category");
			     po.setLotteryCategory(lotteryCategory);
				 String lotteryType=rs.getString("type");
				 po.setLotteryType(lotteryType);
				 String phaseNo=rs.getString("phase_no");
				 po.setPhaseNo(phaseNo);
				 String sponsorUsername=rs.getString("bet_username");
				 po.setSponsorUsername(sponsorUsername);
				 String stars=rs.getString("bet_military");
				 po.setStars(stars);
				 BigDecimal allMoney=rs.getBigDecimal("all_money");
				 po.setAllMoney(allMoney);
				 String progress=rs.getString("progress");
				 po.setProgress(progress);
				resultList.add(po);
			 }
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			closeAll(pstmt,conn);
		 }
	  return resultList;
	}
	/**我关注的人-当前心水列表 2010-04-09 10:50**/
	public List<AttentionXinshuiVO> findCurXinshuiList(Map params)throws DaoException{
		StringBuilder queryList=new StringBuilder("select  t.host_name,t.guest_name,t.xinshui_no,t.price,t.race_name,to_char(a.start_time,'mmdd hh24:mi') start_time,t.tx_username,t.ensure_money,");
		queryList.append(" (select  count(1) from t_xinshui_order  xo  where xo.sold_user_id=t.tx_user_id) sold_cnt,t.price ");
		queryList.append(" from  t_c2c_product  t,t_against  a where t.against_id=a.against_id and t.status='1' ");
		queryList.append(" where  t.tx_user_id=?");
		Connection conn=null;
		PreparedStatement pstmt=null;
		Long userId=(Long)params.get("userId");
	    List<AttentionXinshuiVO> resultList=new ArrayList<AttentionXinshuiVO>();
	    try{
			 conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			 pstmt=conn.prepareStatement(queryList.toString());
			 pstmt.setLong(1, userId);
			 ResultSet rs=pstmt.executeQuery();
			 AttentionXinshuiVO po=null;
			 while(rs.next()){
				po=new AttentionXinshuiVO();
				String hostName=rs.getString("host_name");
				po.setHostName(hostName);
				String guestName=rs.getString("guest_name");
				po.setGuestName(guestName);
				String xinshuiNo=rs.getString("xinshui_no");
				po.setXinshuiNo(xinshuiNo);
				String price=rs.getString("price");
				price=CommUtil.getCurrency(price);
				po.setPrice(price);
				String raceName=rs.getString("race_name");
				po.setRaceName(raceName);
				String soldUserName=rs.getString("tx_username");
				po.setSoldUserName(soldUserName);
				String startTime=rs.getString("start_time");
				po.setStartTime(startTime);
				String ensureMoney=rs.getString("ensure_money");
				po.setEnsureMoney(ensureMoney);
				int soldCnt=rs.getInt("sold_cnt");//购买人数
				po.setSoldCnt(soldCnt);
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
