package com.wintv.lottery.xinshui.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.Iterator;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.C2CProduct;
import com.wintv.framework.pojo.XinshuiOrder;
import com.wintv.framework.pojo.XinshuiAgainst;
import com.wintv.lottery.xinshui.dao.XinshuiLogDao;

import com.wintv.lottery.xinshui.vo.BackendXinshuiOrderVO;
import com.wintv.lottery.xinshui.vo.Top10Vo;
import com.wintv.lottery.xinshui.vo.XinshuiOrderDetailVO;
import com.wintv.lottery.xinshui.vo.XinshuiOrderVO;
import com.wintv.lottery.xinshui.vo.XinshuiAgainstVO;
import com.wintv.lottery.xinshui.vo.XinshuiSearchVO;
import com.wintv.lottery.xinshui.vo.XinshuiVo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Repository("xinshuiLogDao")
@SuppressWarnings("unchecked")
public class XinshuiLogDaoImpl extends BaseHibernate<XinshuiOrder,Long> implements XinshuiLogDao {
   /**
	 * 3.1.1心水发布页面,心水发布——赛事及心水类型选择界面
	 * 民间高手根据条件查询可以发布心水的对阵信息,对应表:T_XINSHUI_SCHEDULE,T_AGAINST
	 * raceId:赛事类型 RACE_ID
	 * sortType:排序方式
	 */
	@Override
	public List findAgainstList(Map params) throws DaoException {
		Long raceId=(Long)params.get("raceId");
		String sort=(String)params.get("sort");//排序
		StringBuilder sb=new StringBuilder("select t.RACE_NAME,t.START_TIME,t.HOST_NAME,t.GUEST_NAME,t.DEADLINE,t.ASIA_PEI,t.BIG_SMALL  ");
	    sb.append(" ,t.AGAINST_ID from v_findAgainstList t where 1=1 ");
		if(raceId!=null){
			sb.append(" and t.RACE_ID="+raceId);
		}
		if(StringUtils.isNotEmpty(sort)){
			sb.append(" order by t."+sort);
		}
		log.info(sb.toString());
	    
	    SQLQuery query=this.getSession().createSQLQuery(sb.toString());
	    List list= query.list();
	    List resultList=new ArrayList();
	    XinshuiAgainstVO vo=null;
	    for(Iterator e=list.iterator();e.hasNext();){
	    	Object[] obj=(Object[])e.next();
	    	vo=new XinshuiAgainstVO();
	    	String raceName=(String)obj[0];
	    	vo.setRaceName(raceName);
	    	String startTime=(String)obj[1];
	    	vo.setStartTime(startTime);
	    	String hostName=(String)obj[2];
	    	vo.setHostName(hostName);
	    	String guestName=(String)obj[3];
	    	vo.setGuestName(guestName);
	    	String deadline=(String)obj[4];
	    	vo.setDeadline(deadline);
	    	//亚盘  大小盘  数据从何处而来?
	    	String asiaPei=(String)obj[5];
	    	vo.setAsiaPei(asiaPei);
	    	String bigSmall=(String)obj[6];
	    	vo.setBigSmall(bigSmall);
	    	vo.setAgainstId(new Long(obj[7].toString()));
	    	resultList.add(vo);
	    }
	    return resultList;
	
	}
	/**
	 * 【心水发布——选择预测结果界面图示】 文档第4页
	 * 民间高手根据主键查询心水对阵信息  对应表:T_XINSHUI_AGAINST,T_AGAINST
	 * id:T_XINSHUI_AGAINST主键
	 */
	public XinshuiAgainstVO loadXinshuiAgainst(Long id)throws DaoException{
           StringBuilder sb=new StringBuilder("select  a.RACE_NAME, to_char(a.START_TIME,'yyyy-mm-dd hh24:mi') START_TIME,a.HOST_NAME,a.GUEST_NAME,to_char(a.start_Time-to_number(( select t.value  from t_dictionary  t where t.code='DEADLINE' and t.type='XINSHUI'))/(24*60),'yyyy-mm-dd hh24:mi')  DEADLINE,");
		   sb.append(" (select t.img  from T_TEAM t where  t.ID=a.HOST_ID) host_img,(select t.img  from T_TEAM t where  t.ID=a.GUEST_ID) guest_Img ");
		   sb.append(",RACE_ID,b.phase   from T_AGAINST a,T_XINSHUI_AGAINST b ");
		   sb.append("  where a.AGAINST_ID=b.AGAINST_ID");
		   sb.append(" and b.AGAINST_ID="+id);
		  
		   SQLQuery query=this.getSession().createSQLQuery(sb.toString());
		   Object[] arr=(Object[])query.uniqueResult();
		   XinshuiAgainstVO vo=null;
		   vo=new XinshuiAgainstVO();
		   vo.setRaceName((String)arr[0]);
		   vo.setStartTime((String)arr[1]);
		   vo.setHostName((String)arr[2]);
		   vo.setGuestName((String)arr[3]);
		   vo.setDeadline((String)arr[4]);
		   vo.setImg((String)arr[5]);
		   vo.setGuestImg((String)arr[6]);
		   vo.setRaceId(new Long(arr[7].toString()));
		   vo.setPhase((String)arr[8]);
		return vo;
	}
	
	@Deprecated
	public List findBuyXinshuiLogList(Map params, int startRow, int pageSize)
			throws DaoException {
		StringBuilder sql = new StringBuilder("select * from xinshui_sold_log where ");
		int length = sql.length();
		
		if(params.get("type") != null) {
			sql.append("TYPE like '%" + params.get("type") + "%' and ");
		}
		if(params.get("productName") != null) {
			sql.append("PRODUCT_NAME like '%" + params.get("productName") + "%' and ");
		}
		if(params.get("sellerName") != null) {
			sql.append("SELLER_NAME like '%" + params.get("sellerName") + "%' and ");
		}
		if(params.get("buyerName") != null) {
			sql.append("BUYER_NAME like '%" + params.get("buyerName") + "%' and ");
		}
		if(params.get("fromTime") != null) {
			sql.append("START_TIME > to_date('" + params.get("fromTime") + "','yyyy-mm-dd') and ");
		}
		if(params.get("toTime") != null) {
			sql.append("START_TIME < to_date('" + params.get("toTime") + "','yyyy-mm-dd') and ");
		}
		
		if(sql.length() > length) {
			sql.delete(sql.length() - 5, sql.length());
		} else {
			sql.delete(sql.length() - 7, sql.length());
		}
		
		List query = jdbcTemplate.query(sql.toString(), new Object[] {},
			new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					XinshuiVo xinshuiVo = new XinshuiVo();
					xinshuiVo.setXinshuiId(rs.getLong("XINSHUI_ORDER_ID"));
					xinshuiVo.setType(rs.getLong("TYPE"));
					xinshuiVo.setProductName(rs.getString("PRODUCT_NAME"));
					xinshuiVo.setSellerName(rs.getString("SELLER_NAME"));
					xinshuiVo.setBuyerName(rs.getString("BUYER_NAME"));
					xinshuiVo.setProductId(rs.getLong("PRODUCT_ID"));
					xinshuiVo.setSellerId(rs.getLong("SELLER_ID"));
					xinshuiVo.setBuyerId(rs.getLong("BUYER_ID"));
					xinshuiVo.setStartTime(rs.getDate("START_TIME"));
					xinshuiVo.setEndTime(rs.getDate("END_TIME"));
					xinshuiVo.setPrice(rs.getBigDecimal("PRICE"));
					xinshuiVo.setPhase(rs.getString("PHASE"));
					return xinshuiVo;
				}
			}
		);
		return query;
	}

	
	
    /** 民间高手各种类型的排行
     * 参数:
     *  flg='1':民间高手胜率排行（月）
     *  flg='2':--民间高手胜率排行（周）
     *  flg='3':心水销售排行（月）
     *  flg='4':民间高手总排行
     * 
     */
	@Override
	public List<Top10Vo> findXinshuiWinTop10List(Map params)
			throws DaoException {
        String flg=(String)params.get("flg");
		List resultList=new ArrayList();
		try { 
			 String sql=null;
			 if("1".equals(flg)){
			    sql="select WIN_RATIO,username,changci from v_top1";
			 }else if("2".equals(flg)){
				 sql="select WIN_RATIO,username,changci from v_top2";
			 }else if("3".equals(flg)){
				 sql="select WIN_RATIO,username,changci,cnt from v_top3";
			 }else if("4".equals(flg)){
				 sql="select WIN_RATIO,username,changci from (select WIN_RATIO,username,changci from v_top4  order  by  WIN_RATIO desc)  where  rownum<=10";
			 }
			 List<Object[]> list=this.getSession().createSQLQuery(sql).list();
			
			 Top10Vo vo=null;
			 for(Object[] o : list){
				 if("1".equals(flg)||"2".equals(flg)||"4".equals(flg)){
				  vo=new Top10Vo();
				  BigDecimal  winRatio=(BigDecimal)o[0];
				  String username=o[1].toString();
				  vo.setUsername(username);
				  int changci=Integer.parseInt(o[2].toString());
				  if(changci>0){
					  vo.setChangci(String.valueOf(changci));
					  //
					  log.info(winRatio.intValue()+"   "+changci);
					  //
					  double d=winRatio.intValue()/changci;
					
					  
					  DecimalFormat df = new DecimalFormat();
				      df.setMaximumFractionDigits(2);
				      df.setMinimumFractionDigits(2);
				      String k = df.format(winRatio.intValue()* 100/changci)+ "%";


					 


					  vo.setWinRatio(k);
				 }else{
					 vo.setChangci("0");
					 vo.setWinRatio("0.0%");
				 }
				  
				 }else if("3".equals(flg)){
					 vo=new Top10Vo();
					  BigDecimal  winRatio=(BigDecimal)o[0];
					  String username=o[1].toString();
					  vo.setUsername(username);
					  int changci=Integer.parseInt(o[2].toString());
					  if(changci>0){
						  vo.setChangci(String.valueOf(changci));
						  double d=winRatio.intValue()/changci;
						  d*=100;
						  vo.setWinRatio(d+"%");
					 }else{
						 vo.setChangci("0");
						 vo.setWinRatio("0.0%");
					 }
					  
					 BigDecimal cnt=(BigDecimal)o[3];
					 String souldCnt=cnt==null?"0":cnt.toString();
					 vo.setSouldCnt(souldCnt);
				 }
				 resultList.add(vo);
			 }
			
			   
			} catch (Exception e) { 
			   e.printStackTrace(); 
			  }


		
		

		return resultList;
	}
	/**【普通用户】搜索心水
	 * 涉及到的表T_C2C_PRODUCT,
	 * 参数：
	 * raceId：赛事类型(RACE_ID)
	 * key:关键字  发布人..
	 * 返回:
	 *  List<C2CProduct>
	 */
	public List<C2CProduct> findC2CProductList(Map params)throws DaoException{
		List list=null;
		Connection conn =null;
		CallableStatement cstmt = null;
		try { 
			  String raceType=(String)params.get("raceType");
			  String key=(String)params.get("key");
			  Session session =this.getSession();
			  conn =session.connection();
			  cstmt = conn.prepareCall("{call XINSHUI.findXinshuiList(?,?,?)}"); 
			  
			  
			  
			  cstmt.setString(1,raceType);
			  cstmt.setString(2, key);
			  cstmt.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR); 
			  cstmt.execute();
			  ResultSet rs=(ResultSet)cstmt.getObject(3);
              list=new ArrayList();
              XinshuiSearchVO po=null;
			  while(rs.next()){
				  po=new XinshuiSearchVO();
				  String raceName=rs.getString("race_name");
				  po.setRaceName(raceName);
				  list.add(po);
				  //log.info("raceName="+raceName);
			  }
			   
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
			  return list;
	}
	
	   
	   /**【普通用户】心水管理—订单管理
	    * 用户根据条件进行心水查询,并分页  对应表:T_XINSHUI_ORDER,T_C2C_PRODUCT
	    * 参数:
	    * raceId:赛事名称 T_AGAINST.RACE_ID
	    * payStatus:支付状态
	    * orderNo:订单编号 对应表T_XINSHUI_ORDER.ORDER_NO
	    * xinshuiNo:心水编号T_C2C_PRODUCT.XINSHUI_NO
	    * txUsername:发布人  对应表T_C2C_PRODUCT.TX_USERNAME
	    * hostTeam,guestTeam:主客队名
	    * userId:当前用户，即心水购买人
	    *返回：
	    *心水购买信息
	    */
	   public List<XinshuiOrderVO> findXinshuiOrderList(Map params,int startRow,int pageSize)throws DaoException{
		   List resultList=null;
		   Long raceId=(Long)params.get("raceId");
		   Long c2cId=(Long)params.get("c2cId");
		   String payStatus=(String)params.get("payStatus");
		   String orderNo=(String)params.get("orderNo");
		   String xinshuiNo=(String)params.get("xinshuiNo");
		   String txUsername=(String)params.get("txUsername");
		   String team=(String)params.get("team");
		   Long userId=(Long)params.get("userId");//当前用户ID
		   StringBuilder sb=new StringBuilder("select  c.ORDER_NO,c.SOLD_USERNAME,a.RACE_NAME,a.HOST_NAME, to_char(a.START_TIME,'yy-mm-dd hh24:mi') START_TIME,");
		   sb.append(" a.GUEST_NAME,d.XINSHUI_NO,to_char(d.ENSURE_MONEY,'999999.99')  ENSURE_MONEY,to_char(d.PRICE,'999999.99') PRICE,  to_char(c.BUY_TIME,'yy-mm-dd hh24:mi')  BUY_TIME,");
		   //sb.append(" decode(c.PAY_STATUS,'1','未支付','2','已支付','3','已扣款','4','已赔付','5','已取消') PAY_STATUS, ");
		   sb.append(" c.PAY_STATUS, ");//因为页面上需要根据是否已经支付  如果支付状态为"未支付",则显示支付按钮 否则隐藏支付按钮
		   //sb.append(" decode(d.STATUS,'1','未开赛','2','赢','3','负','4','走') STATUS ");
		   sb.append(" d.STATUS, ");
		   sb.append(" d.C2C_ID ,c.XINSHUI_ORDER_ID ");
		   sb.append(" from T_AGAINST a,T_XINSHUI_AGAINST b ,T_XINSHUI_ORDER c,T_C2C_PRODUCT d");
		   sb.append("  where a.AGAINST_ID=b.AGAINST_ID  and  d.AGAINST_ID=b.AGAINST_ID  and c.PRODUCT_ID= d.C2C_ID  and c.BUY_USER_ID="+userId);
		      if(raceId!=null){
		    	  sb.append(" and a.RACE_ID="+raceId);
		      }
		      if(c2cId!=null){
		    	  sb.append(" and d.C2C_ID="+c2cId);
		      }
		      if(StringUtils.isNotEmpty(payStatus)){
		    	  sb.append(" and  c.PAY_STATUS="+payStatus);
		      }
		      if(StringUtils.isNotEmpty(orderNo)){
		    	  sb.append(" and c.ORDER_NO like '%"+orderNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(xinshuiNo)){
		    	  sb.append(" and d.XINSHUI_NO like '%"+xinshuiNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(txUsername)){
		    	  sb.append(" and d.TX_USERNAME like '%"+txUsername+"%'");
		      }
		      if(StringUtils.isNotEmpty(team)){
		    	  sb.append(" and (a.HOST_NAME like '%"+team+"%'   or   a.GUEST_NAME like '%"+team+"%')");
		      }
		      
		   try{
		   //log.info(sb.toString());
		   SQLQuery query=this.getSession().createSQLQuery(sb.toString());
		   query.setFirstResult(startRow);
		   query.setMaxResults(pageSize);
		   List list=query.list();
		   XinshuiOrderVO vo=null;
		   resultList=new ArrayList();
		   for(Iterator e=list.iterator();e.hasNext();){
			   vo=new XinshuiOrderVO();
			   Object[] arr=(Object[])e.next();
			   vo.setOrderNo((String)arr[0]);
			   vo.setSoldUsername((String)arr[1]);
			   vo.setRaceName((String)arr[2]);
			   vo.setHostName((String)arr[3]);
			   
			   vo.setGuestName((String)arr[5]);
			   String startTime=(String)arr[4];
			   
			   vo.setStartTime(startTime);
			   vo.setXinshuiNO((String)arr[6]);//
			  // log.info("***********************************"+(String)arr[6]);
			   String ensureMoney=(String)arr[7];
			   vo.setEnsureMoney(ensureMoney.trim());
			   String price=(String)arr[8];
			   vo.setPrice(price.trim());
			   String orderTime=(String)arr[9];
			   vo.setOrderTime(orderTime);
			   Character _payStatus=(Character)arr[10];
			   vo.setPayStatus(_payStatus.toString());
			   Character status=(Character)arr[11];
			   vo.setStatus(status.toString());
			   vo.setXinshuiId(((BigDecimal)arr[12]).longValue());
			   Long xinshuiOrderId=new Long(arr[13].toString());
			   vo.setXinshuiOrderId(xinshuiOrderId);
			  
			   resultList.add(vo);
		   }
	    }catch(Exception e){
	    	e.printStackTrace();
	    	throw new DaoException(e.getLocalizedMessage());
	    }
	    return resultList;
	   }
	   public int findXinshuiOrderSize(Map params) throws DaoException {
		   
		   Long raceId=(Long)params.get("raceId");
		   Long c2cId=(Long)params.get("c2cId");
		   String payStatus=(String)params.get("payStatus");
		   String orderNo=(String)params.get("orderNo");
		   String xinshuiNo=(String)params.get("xinshuiNo");
		   String txUsername=(String)params.get("txUsername");
		   String team=(String)params.get("team");
		   Long userId=(Long)params.get("userId");//当前用户ID
		   StringBuilder sb=new StringBuilder("select  count(1) cnt ");
		   sb.append(" from T_AGAINST a,T_XINSHUI_AGAINST b ,T_XINSHUI_ORDER c,T_C2C_PRODUCT d");
		   sb.append("  where a.AGAINST_ID=b.AGAINST_ID  and  d.AGAINST_ID=b.AGAINST_ID  and c.PRODUCT_ID= d.C2C_ID  and c.BUY_USER_ID="+userId);
		      if(raceId!=null){
		    	  sb.append(" and a.RACE_ID="+raceId);
		      }
		      if(c2cId!=null){
		    	  sb.append(" and d.C2C_ID="+c2cId);
		      }
		      if(StringUtils.isNotEmpty(payStatus)){
		    	  sb.append(" and c.PAY_STATUS="+payStatus);
		      }
		      if(StringUtils.isNotEmpty(orderNo)){
		    	  sb.append(" and c.ORDER_NO like '%"+orderNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(xinshuiNo)){
		    	  sb.append(" and d.XINSHUI_NO like '%"+xinshuiNo+"%'");
		      }
		      if(StringUtils.isNotEmpty(txUsername)){
		    	  sb.append(" and d.TX_USERNAME like '%"+txUsername+"%'");
		      }
		      if(StringUtils.isNotEmpty(team)){
		    	  sb.append(" and (a.HOST_NAME like '%"+team+"%'   or   a.GUEST_NAME like '%"+team+"%')");
		      }
		      
		   try{
		   SQLQuery query=this.getSession().createSQLQuery(sb.toString());
		   query.addScalar("cnt",Hibernate.INTEGER);
		   return (Integer)query.uniqueResult();
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		   
		   return 0;
		   
	   }
	   /**
	    * 【普通用户】加载心水订购页面所显示的信息
	    * 参数:
	    * id:T_C2C_PRODUCT.ID
	    */
	   public XinshuiOrderVO loadXinshuiDetail(Long id)throws DaoException{
		   StringBuilder sql=new StringBuilder("select b.HOST_NAME,b.GUEST_NAME,decode(a.TYPE,'1','亚盘','2','大小盘') TYPE,a.TX_USERNAME,to_char(b.START_TIME,'yyyy-mm-dd hh24:mi') START_TIME,to_char(a.PRICE,'999999.99'),to_char(a.ENSURE_MONEY,'999999.99') ENSURE_MONEY  from  T_C2C_PRODUCT a,T_AGAINST b where a.AGAINST_ID=b.AGAINST_ID  and a.C2C_ID=?");
		   Query q=getSession().createSQLQuery(sql.toString());
		   q.setLong(0, id);
		   Object[] obj=(Object[])q.uniqueResult();
		   XinshuiOrderVO vo=new XinshuiOrderVO();
		   vo.setHostName((String)obj[0]);
		   vo.setGuestName((String)obj[1]);
		   vo.setType((String)obj[2]);
		   vo.setSoldUsername((String)obj[3]);
		   vo.setStartTime((String)obj[4]);
		   vo.setPrice((String)obj[5]);
		   vo.setEnsureMoney((String)obj[6]);
		   return vo;
	   }
	   /**
	     * 心水二次支付详情
	     * @param xinshuiOrderId
	     * @return
	     * @throws DaoException
	     */
	    public XinshuiOrderDetailVO loadXinshuiOrderDetail(Long xinshuiOrderId)throws DaoException{
	    	XinshuiOrderDetailVO vo=new XinshuiOrderDetailVO();
	    	StringBuilder sql=new StringBuilder("select  a.order_no,b.tx_username,b.race_name,b.host_name,b.guest_name,c.start_time,b.ensure_money,b.price,");
            sql.append(" a.tx_money,a.tx_caijin from  T_XINSHUI_ORDER a,T_C2C_PRODUCT b,t_Against c ");
            sql.append(" where  a.product_id=b.c2c_id  and b.against_id=c.against_id and a.xinshui_order_id=?");
            SQLQuery q=this.getSession().createSQLQuery(sql.toString());
            q.setLong(0, xinshuiOrderId);
            Object[] o=(Object[])q.list().iterator().next();
            String orderNo=o[0].toString();
            vo.setOrderNo(orderNo);
            String pubUsername=o[1].toString();//发布人
            vo.setPubUsername(pubUsername);
            String raceName=o[2].toString();
            vo.setRaceName(raceName);
            String hostName=o[3].toString();
            vo.setHostName(hostName);
            String guestName=o[4].toString();
            vo.setGuestName(guestName);
            String startTime=o[5].toString();
            vo.setStartTime(startTime);
            String ensureMoney=o[6].toString();
            vo.setEnsuremoney(ensureMoney);
            BigDecimal price=(BigDecimal)o[7];
            vo.setPrice(price.toString());
            BigDecimal txMoney=(BigDecimal)o[8];
            BigDecimal txCaijin=(BigDecimal)o[9];
            price.subtract(txMoney);
            price.subtract(txCaijin);
            vo.setContineuMoney(price.toString());
            return vo;
	    }
	  
	   private static final Log log=LogFactory.getLog(XinshuiLogDaoImpl.class);
		 
		@Autowired
		private JdbcTemplate jdbcTemplate;

}

