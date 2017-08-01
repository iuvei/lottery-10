package com.wintv.lottery.xinshui.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.wintv.framework.common.Constants;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.XinshuiAgainst;
import com.wintv.lottery.xinshui.dao.XinshuiScheduleDao;
import com.wintv.lottery.xinshui.vo.BackendPubRecordVO;
import com.wintv.lottery.xinshui.vo.BackendPubmanageVO;
import com.wintv.lottery.xinshui.vo.BackendXinshuiOrderVO;
import com.wintv.lottery.xinshui.vo.XinshuiAgainstVO;
import com.wintv.lottery.xinshui.vo.XinshuiDetailVO;

@Repository("xinshuiScheduleDao")
public class XinshuiScheduleDaoImpl extends BaseHibernate<XinshuiAgainst,Long> implements XinshuiScheduleDao {
	private static final Log log=LogFactory.getLog(XinshuiScheduleDaoImpl.class);
	 /**
     * 关闭心水 即把表T_C2C_PRODUCT.STATUS设置为'5'
     *参数:
     *c2cId:对阵表主键
     */
   public void closeXinshui(Long c2cId,Long csUserId,String csName,String ip)throws DaoException{
	    log.info("c2cId="+c2cId+"  csUserId="+csUserId+" csName="+csName+" ip="+ip );
        Connection conn=null;
   	    CallableStatement cstmt =null;
		try { 
			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			  cstmt = conn.prepareCall("{call XINSHUI.closeXinshui(?,?,?,?)}"); 
			  cstmt.setLong(1, c2cId);
			  cstmt.setLong(2, csUserId);
			  cstmt.setString(3,csName);
			  cstmt.setString(4,ip);
			  cstmt.execute();
			
		} catch (SQLException e) { 
			e.printStackTrace();
			  //throw new DaoException(e.getLocalizedMessage());
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
   
   }
   /**
	 * 【心水后台管理——心水订单管理界面】  分页统计2010-02-01 16:30
	 * 参数:
	 *  raceId:联赛名称
	 *  orderUsername:订购人用户名
     *  flg:'1':"订购人”    '2':“订单编号”  '3':“心水编号  '4':“发布人”  '5'
     *  type:'1':亚盘 '2':大小盘 '3':欧盘
	 */
	public int findBackendXinshuiOrderSize(Map params)throws DaoException{
	  try{
        Long raceId=(Long)params.get("raceId");
		
		String flg=(String)params.get("flg");
		String key=(String)params.get("key");
		
		StringBuilder sql=new StringBuilder("select count(1) cnt from  v_backend_xinshui_order t where 1=1 ");
        if(raceId!=null){//联赛名称
        	sql.append(" and  t.race_id="+raceId);
        }
        //类型
        String type=(String)params.get("type");
        if(StringUtils.isNotEmpty(type)){
        	sql.append(" and t.TYPE='"+type+"' ");
        }
        //状态
        String status=(String)params.get("status");
        if(StringUtils.isNotEmpty(status)){
        	sql.append(" and t.PAY_STATUS='"+status+"' ");
        }
        
        //"订购人”
        if("1".equals(flg)&&StringUtils.isNotEmpty(key)){
        	sql.append(" and  t.BUY_USERNAME like '%"+key+"%'");
        }
        //“订单编号”
        else if("2".equals(flg)&&StringUtils.isNotEmpty(key)){
        	sql.append(" and  t.ORDER_NO like '%"+key+"%'");
        }
        //“心水编号
        else if("3".equals(flg)&&StringUtils.isNotEmpty(key)){
        	sql.append(" and  t.XINSHUI_NO like '%"+key+"%'");
        }
        //“发布人”
        if("4".equals(flg)&&StringUtils.isNotEmpty(key)){
        	sql.append(" and  t.TX_USERNAME like '%"+key+"%'");
        }
        //“主客队名
        else if("5".equals(flg)&&StringUtils.isNotEmpty(key)){
        	sql.append(" and ( t.HOST_NAME like '%"+key+"%' or  t.GUEST_NAME like  '%"+key+"%') ");
        }
        //
        String fromStartTime=(String)params.get("fromStartTime");//起始开赛时间
        if(StringUtils.isNotEmpty(fromStartTime)){
        	sql.append(" and to_char(t.START_TIME,'yyyy-mm-dd') >='"+fromStartTime+"' ");
        }
        String toStartTime=(String)params.get("toStartTime");//结束开赛时间
        if(StringUtils.isNotEmpty(toStartTime)){
        	sql.append(" and to_char(t.START_TIME,'yyyy-mm-dd') <='"+toStartTime+"' ");
        }
        //
        String fromOrderTime=(String)params.get("fromOrderTime");//起始订购时间
        if(StringUtils.isNotEmpty(fromOrderTime)){
        	sql.append(" and to_char(t.BUY_TIME,'yyyy-mm-dd') >='"+fromOrderTime+"' ");
        }
        String toOrderTime=(String)params.get("toOrderTime");//结束订购时间
        if(StringUtils.isNotEmpty(toOrderTime)){
        	sql.append(" and to_char(t.BUY_TIME,'yyyy-mm-dd') <='"+toOrderTime+"' ");
        }
        Long c2cId=(Long)params.get("c2cId");
        if(c2cId!=null){
          sql.append(" and t.c2c_id="+c2cId);
        }
        Long buy_user_id=(Long)params.get("buy_user_id");
        if(buy_user_id!=null){
            sql.append(" and t.buy_user_id="+buy_user_id);
          }
       SQLQuery q=this.getSession().createSQLQuery(sql.toString());
       q.addScalar("cnt", Hibernate.INTEGER);
       Integer size=(Integer)q.uniqueResult();
       return size.intValue();
	}catch(Exception e){
		e.printStackTrace();
		throw new DaoException(e.getLocalizedMessage());
	}
	}
	/**
	 * 【心水后台管理——心水订单管理界面】2010-02-01 16:30
	 * 参数:
	 *  raceId:联赛名称
	 *  startRow:起始记录数
     *  pageSize:每页最大记录数
     *  orderUsername:订购人用户名
     *  flg:'1':"订购人”    '2':“订单编号”  '3':“心水编号  '4':“发布人”  '5'
     *  type:'1':亚盘 '2':大小盘 '3':欧盘
	 */
	public List findBackendXinshuiOrderList(Map params)throws DaoException{
		Long raceId=(Long)params.get("raceId");
		
		String flg=(String)params.get("flg");
		String key=(String)params.get("key");
		
		StringBuilder sql=new StringBuilder("select t.ORDER_NO,t.BUY_USERNAME,t.XINSHUI_NO,t.TX_USERNAME,t.HOST_NAME, t.GUEST_NAME, t.RACE_NAME, ");
        sql.append(" to_char(t.START_TIME,'yy-mm-dd hh24:mi') START_TIME, to_char(t.ENSURE_MONEY,'99999999.99')  ENSURE_MONEY, to_char(t.PRICE,'99999999.99') PRICE, to_char(t.BUY_TIME,'yy-mm-dd hh24:mi') BUY_TIME,cast(t.PAY_STATUS as varchar(60)) PAY_STATUS ");
        sql.append(" ,t.order_id, t.type  from  v_backend_xinshui_order t where 1=1 ");
        if(raceId!=null){//联赛名称
        	sql.append(" and  t.race_id=to_number("+raceId+") ");
        }
        //类型
        String type=(String)params.get("type");
        if(StringUtils.isNotEmpty(type)){
        	sql.append(" and t.TYPE='"+type+"' ");
        }
        //状态
        String status=(String)params.get("status");
        if(StringUtils.isNotEmpty(status)){
        	sql.append(" and t.PAY_STATUS='"+status+"' ");
        }
        
        //"订购人”
        if("1".equals(flg)&&StringUtils.isNotEmpty(key)){
        	sql.append(" and  t.BUY_USERNAME like '%"+key+"%'");
        }
        //“订单编号”
        else if("2".equals(flg)&&StringUtils.isNotEmpty(key)){
        	sql.append(" and  t.ORDER_NO like '%"+key+"%'");
        }
        //“心水编号
        else if("3".equals(flg)&&StringUtils.isNotEmpty(key)){
        	sql.append(" and  t.XINSHUI_NO like '%"+key+"%'");
        }
        //“发布人”
        if("4".equals(flg)&&StringUtils.isNotEmpty(key)){
        	sql.append(" and  t.TX_USERNAME like '%"+key+"%'");
        }
        //“主客队名
        else if("5".equals(flg)&&StringUtils.isNotEmpty(key)){
        	sql.append(" and  ( t.HOST_NAME like '%"+key+"%' or  t.GUEST_NAME like  '%"+key+"%') ");
        }
        //
        String fromStartTime=(String)params.get("fromStartTime");//起始开赛时间
        if(StringUtils.isNotEmpty(fromStartTime)){
        	sql.append(" and to_char(t.START_TIME,'yyyy-mm-dd') >='"+fromStartTime+"' ");
        }
        String toStartTime=(String)params.get("toStartTime");//结束开赛时间
        if(StringUtils.isNotEmpty(toStartTime)){
        	sql.append(" and to_char(t.START_TIME,'yyyy-mm-dd') <='"+toStartTime+"' ");
        }
        //
        String fromOrderTime=(String)params.get("fromOrderTime");//起始订购时间
        if(StringUtils.isNotEmpty(fromOrderTime)){
        	sql.append(" and to_char(t.BUY_TIME,'yyyy-mm-dd') >='"+fromOrderTime+"' ");
        }
        String toOrderTime=(String)params.get("toOrderTime");//结束订购时间
        if(StringUtils.isNotEmpty(toOrderTime)){
        	sql.append(" and to_char(t.BUY_TIME,'yyyy-mm-dd') <='"+toOrderTime+"' ");
        }
        Long c2cId=(Long)params.get("c2cId");
        if(c2cId!=null){
          sql.append(" and t.c2c_id="+c2cId);
        }
        Long buy_user_id=(Long)params.get("buy_user_id");
        if(buy_user_id!=null){
            sql.append(" and t.buy_user_id="+buy_user_id);
          }
        String orderFiled=(String)params.get("orderFiled");
		String orderType=(String)params.get("orderType");
		if (StringUtils.isNotEmpty(orderFiled)&&StringUtils.isNotEmpty(orderType)) {
			 sql.append("  order by t."+orderFiled+"  "+orderType);
		}
        
        log.info(sql.toString());
        
        
        SQLQuery query=this.getSession().createSQLQuery(sql.toString());
		//
		int startRow=Integer.parseInt(params.get("startRow").toString());
	    query.setFirstResult(startRow);
	    int pageSize=Integer.parseInt(params.get("pageSize").toString());
	    query.setMaxResults(pageSize);
	    List list=query.list();
		
	   
	    
		BackendXinshuiOrderVO vo=null;
	    List result=new ArrayList();
		for(Iterator e=list.iterator();e.hasNext();){
			Object[] obj=(Object[])e.next();
			vo=new BackendXinshuiOrderVO();
			 String _orderNo=(String)obj[0];//订单编号
			 vo.setOrderNo(_orderNo);
			 String _orderUsername=(String)obj[1];//订购人
			 vo.setOrderUsername(_orderUsername);
			 log.info("**_orderUsername="+_orderUsername);
			 String _xinshuiNo=(String)obj[2];//心水编号
			 vo.setXinshuiNo(_xinshuiNo);
			 String _pubUsername=(String)obj[3];//发布人
			 vo.setPubUsername(_pubUsername);
			 String hostName=(String)obj[4];//主队
			 vo.setHostName(hostName);
			 String  guestName=(String)obj[5];//客队
			 vo.setGuestName(guestName);
			 String raceName=(String)obj[6];//联赛名
			 vo.setRaceName(raceName);
			 DateFormat  df=new  java.text.SimpleDateFormat("yyyy-MM-dd");
			 String startTime=(String)obj[7];//开赛时间
			
			 vo.setStartTime(startTime);
			 String ensuremoney=(String)obj[8];//保证金
			 vo.setEnsuremoney(ensuremoney);
			 String price=(String)obj[9];//价格
			 vo.setPrice(price);
			
			
			 String orderTime=(String)obj[10];//订购时间
			 vo.setOrderTime(orderTime);
		     String  _status=(String)obj[11];//状态
			 vo.setStatus(_status);
			 Long orderId=new Long(obj[12].toString());
			 vo.setOrderId(orderId);
			 
			 vo.setType((String)obj[13]);
			
			 result.add(vo);
		}
		
	
		return result;
	}
	/**
	 * 【心水后台管理——心水编辑界面图示】2010-02-01 16:27
	 */
	public boolean updateXinshui()throws DaoException{
		
		return false;
	}
	
	
	
	/**
	 * 【心水后台管理——心水管理界面】分页记录统计
	 */
	public int findBackendXinshuiPubRecordSize(Map params)throws DaoException{
		    StringBuilder sql=new StringBuilder("select count(1)  cnt  from  v_findbackendxinshuipubrecord a  where 1=1  ");
			Long raceId=(Long)params.get("raceId");
			if(raceId!= null) {
			   sql.append("  and a.RACE_ID="+raceId);
			}
			String type=(String)params.get("type");
			if(StringUtils.isNotEmpty(type)){
				sql.append("  and a.TYPE='"+type+"'");
			}
			String status=(String)params.get("status");
			if(StringUtils.isNotEmpty(status)){
				sql.append("  and a.STATUS='"+status+"'");
			}
			//开赛时间
			String startFrom=(String)params.get("startFrom");
			if(StringUtils.isNotEmpty(startFrom)){
				sql.append(" and to_char(a.START_TIME,'yyyy-mm-dd')>='"+startFrom+"'");
			}
			String startTo=(String)params.get("startTo");
			if(StringUtils.isNotEmpty(startTo)){
				sql.append(" and to_char(a.START_TIME,'yyyy-mm-dd')<='"+startTo+"'");
			}
			//发布时间
			String pubFrom=(String)params.get("pubFrom");
			if(StringUtils.isNotEmpty(pubFrom)){
				sql.append(" and to_char(a.PUB_TIME,'yyyy-mm-dd')>='"+pubFrom+"'");
			}
			String pubTo=(String)params.get("pubTo");
			if(StringUtils.isNotEmpty(startTo)){
				sql.append(" and to_char(a.PUB_TIME,'yyyy-mm-dd')<='"+pubTo+"'");
			}
			
			//保证金
			String minEnsureMoney=(String)params.get("minEnsureMoney");
			if(StringUtils.isNotEmpty(minEnsureMoney)){
				sql.append(" and a.ENSURE_MONEY>=to_number('"+minEnsureMoney+"')");
			}
			String maxEnsureMoney=(String)params.get("maxEnsureMoney");
			if(StringUtils.isNotEmpty(maxEnsureMoney)){
				sql.append(" and a.ENSURE_MONEY<=to_number('"+maxEnsureMoney+"')");
			}
			//价格
			String minPrice=(String)params.get("minPrice");
			if(StringUtils.isNotEmpty(minPrice)){
				sql.append(" and a.PRICE>=to_number('"+minPrice+"')");
			}
			String maxPrice=(String)params.get("maxPrice");
			if(StringUtils.isNotEmpty(maxPrice)){
				sql.append(" and a.PRICE<=to_number('"+maxPrice+"')");
			}
			
			
			String pubUsername=(String)params.get("pubUsername");
			if(StringUtils.isNotEmpty(pubUsername)){
				sql.append(" and a.TX_USERNAME like '%"+pubUsername+"%' ");
			}
			Long againstId=(Long)params.get("againstId");
			if(againstId!=null){
				sql.append(" and a.against_id="+againstId);
			}
			
			Long pubUserId=(Long)params.get("pubUserId");//发布人用户ID
			if(pubUserId!=null){
			   sql.append(" and a.pub_userid="+pubUserId);
			}
			String orderFiled=(String)params.get("orderFiled");
			String orderType=(String)params.get("orderType");
			if (StringUtils.isNotEmpty(orderFiled)&&StringUtils.isNotEmpty(orderType)) {
				 sql.append("  order by a."+orderFiled+"  "+orderType);
			}
		
		//System.out.println("记录数目="+sql.toString());
		SQLQuery q=this.getSession().createSQLQuery(sql.toString());
		q.addScalar("cnt",Hibernate.LONG);
		Long size=(Long)q.uniqueResult();
		return size.intValue();
	}
	/**
	 * 【心水后台管理——心水管理界面】数据分页
	 * 参数：
	 * againstId：对阵ID
	 * pubUserId:发布人用户ID
	 */
	public List findBackendXinshuiPubRecordList(Map params,int startRow,int pageSize)throws DaoException{
        StringBuilder sql=new StringBuilder("select a.XINSHUI_NO,a.TX_USERNAME,a.HOST_NAME ,a.GUEST_NAME,a.RACE_NAME,to_char(a.START_TIME,'yy-mm-dd hh24:mi') START_TIME,a.ENSURE_MONEY,");
		sql.append(" a.PRICE, a.TYPE,a.sold_cnt,to_char(a.PUB_TIME,'yy-mm-dd hh24:mi') PUB_TIME,a.STATUS,a.c2c_id,a.race_id,a.against_id  from  v_findbackendxinshuipubrecord a  where 1=1 ");
		Long raceId=(Long)params.get("raceId");
		if(raceId!= null) {
		   sql.append("  and a.RACE_ID="+raceId);
		}
		String type=(String)params.get("type");
		if(StringUtils.isNotEmpty(type)){
			sql.append("  and a.TYPE='"+type+"'");
		}
		String status=(String)params.get("status");
		if(StringUtils.isNotEmpty(status)){
			sql.append("  and a.STATUS='"+status+"'");
		}
		//开赛时间
		String startFrom=(String)params.get("startFrom");
		if(StringUtils.isNotEmpty(startFrom)){
			sql.append(" and to_char(a.START_TIME,'yyyy-mm-dd')>='"+startFrom+"'");
		}
		String startTo=(String)params.get("startTo");
		if(StringUtils.isNotEmpty(startTo)){
			sql.append(" and to_char(a.START_TIME,'yyyy-mm-dd')<='"+startTo+"'");
		}
		//发布时间
		String pubFrom=(String)params.get("pubFrom");
		if(StringUtils.isNotEmpty(pubFrom)){
			sql.append(" and to_char(a.PUB_TIME,'yyyy-mm-dd')>='"+pubFrom+"'");
		}
		String pubTo=(String)params.get("pubTo");
		if(StringUtils.isNotEmpty(startTo)){
			sql.append(" and to_char(a.PUB_TIME,'yyyy-mm-dd')<='"+pubTo+"'");
		}
		
		//保证金
		String minEnsureMoney=(String)params.get("minEnsureMoney");
		if(StringUtils.isNotEmpty(minEnsureMoney)){
			sql.append(" and a.ENSURE_MONEY>=to_number('"+minEnsureMoney+"')");
		}
		String maxEnsureMoney=(String)params.get("maxEnsureMoney");
		if(StringUtils.isNotEmpty(maxEnsureMoney)){
			sql.append(" and a.ENSURE_MONEY<=to_number('"+maxEnsureMoney+"')");
		}
		//价格
		String minPrice=(String)params.get("minPrice");
		if(StringUtils.isNotEmpty(minPrice)){
			sql.append(" and a.PRICE>=to_number('"+minPrice+"')");
		}
		String maxPrice=(String)params.get("maxPrice");
		if(StringUtils.isNotEmpty(maxPrice)){
			sql.append(" and a.PRICE<=to_number('"+maxPrice+"')");
		}
		
		
		String pubUsername=(String)params.get("pubUsername");
		if(StringUtils.isNotEmpty(pubUsername)){
			sql.append(" and a.TX_USERNAME like '%"+pubUsername+"%' ");
		}
		Long againstId=(Long)params.get("againstId");
		if(againstId!=null){
			sql.append(" and a.against_id="+againstId);
		}
		
		Long pubUserId=(Long)params.get("pubUserId");//发布人用户ID
		if(pubUserId!=null){
		  sql.append(" and a.pub_userid="+pubUserId);
		}
		String orderFiled=(String)params.get("orderFiled");
		String orderType=(String)params.get("orderType");
		if (StringUtils.isNotEmpty(orderFiled)&&StringUtils.isNotEmpty(orderType)) {
			 sql.append("  order by a."+orderFiled+"  "+orderType);
		}
		log.info("记录列表="+sql.toString());
		SQLQuery q=this.getSession().createSQLQuery(sql.toString());
		q.setFirstResult(startRow);
		q.setMaxResults(pageSize);
		List list=q.list();
		List result=new ArrayList();
		BackendPubRecordVO vo=null;
		//DateFormat df=new SimpleDateFormat("yy-MM-dd HH:mm");

		for(Iterator e=list.iterator();e.hasNext();){
			vo=new BackendPubRecordVO();
			Object[] obj=(Object[])e.next();
			String xinshuiNo=(String)obj[0];//心水编号
			vo.setXinshuiNo(xinshuiNo);
			String _pubUsername=(String)obj[1];//发布人
			vo.setPubUsername(_pubUsername);
			String hotname=(String)obj[2];//主队
			vo.setHotname(hotname);
			String  guestName=(String)obj[3];//客队
			vo.setGuestName(guestName);
			String raceName=(String)obj[4];//联赛名
			vo.setRaceName(raceName);
			//Date _startTime=(Date)obj[5];
		    //String startTime=df.format(_startTime);//开赛时间
		    vo.setStartTime(obj[5].toString());
		    BigDecimal _ensuremoney=(BigDecimal)obj[6];
		    String ensuremoney=_ensuremoney.toString();//保证金
		    vo.setEnsuremoney(ensuremoney);
		    
		    BigDecimal _price=(BigDecimal)obj[7];
		    String price=_price.toString();//价格
		    vo.setPrice(price);
		    String _type=(String)obj[8];//类型
		    vo.setType(_type);
		    
		    BigDecimal _orderCnt=(BigDecimal)obj[9];
		    Long orderCnt=_orderCnt.longValue();//销售总量
		    vo.setOrderCnt(orderCnt);
		    //Date _pubTime=(Date)obj[10];
		    //String pubTime=df.format(_pubTime);//发布时间
		    vo.setPubTime(obj[10].toString());
		    String _status=(String)obj[11];//状态
		    vo.setStatus(_status);
		    vo.setId(new Long(obj[12].toString()));//心水ID
		    vo.setRaceId(new Long(obj[13].toString()));//赛事ID
		  
		    vo.setAgainstId(new Long(obj[14].toString()));
		    result.add(vo);
		}
		return result;
	}
	
	
	/**
	    * 【发布人管理】 分页统计 文档第13页   2010-02-04 11:55
	    * 参数:
	    *  grade:级别
	    *  yccc:预测场次
	    *  zql
	    * @return
	    * @throws DaoException
	    */
	   public int findBackendXinshuiPubSize(Map params)throws DaoException{
		   StringBuilder sql = new StringBuilder("select  count(1) cnt  from  v_backend_pub_usermanage t");
		   sql.append("  where t.user_grade>='"+Constants.MIN_MINJIAN_GAOSHOU_GRADE+"'");
		   String minZdgs=(String)params.get("minZdgs");//最小总订购数
		   if(StringUtils.isNotEmpty(minZdgs)){
			   sql.append(" and t.zdgs >=to_number('"+minZdgs+"') ");
		   }
		   String maxZdgs=(String)params.get("maxZdgs");//最大总订购数
		   if(StringUtils.isNotEmpty(maxZdgs)){
			   sql.append(" and t.zdgs  <=to_number('"+maxZdgs+"') ");
		   }
		   //用户级别
		   String minUserGrade=(String)params.get("minUserGrade");
		   if(StringUtils.isNotEmpty(minUserGrade)){
			   sql.append(" and t.user_grade >=to_number('"+minUserGrade+"') ");
		   }
		   String maxUserGrade=(String)params.get("maxUserGrade");
		   if(StringUtils.isNotEmpty(maxUserGrade)){
			   sql.append(" and t.user_grade  <=to_number('"+maxUserGrade+"') ");
		   }
		   
		   //预测场次
		   String minYccc=(String)params.get("minYccc");
		   if(StringUtils.isNotEmpty(minYccc)){
			   sql.append(" and t.yccc  >=to_number('"+minYccc+"') ");
		   }
		   String maxYccc=(String)params.get("maxYccc");
		   if(StringUtils.isNotEmpty(maxYccc)){
			   sql.append(" and t.yccc  <=to_number('"+maxYccc+"') ");
		   }
		   //准确率
		   String minZql=(String)params.get("minZql");
		   //log.info("准确率="+minZql);
		   if(StringUtils.isNotEmpty(minZql)){
			   sql.append(" and t.zql  >= to_number('"+minZql+"')*t.yccc ");
		   }
		   String maxZql=(String)params.get("maxZql");
		   if(StringUtils.isNotEmpty(maxZql)){
			   sql.append(" and t.zql  <= to_number('"+maxZql+"')*t.yccc ");
		   }
		  
		   //发布人
		   String username=(String)params.get("username");
		   if(StringUtils.isNotEmpty(username)){
			   sql.append(" and t.username like '%"+username+"%'");
		   }
		   //log.info("发布人管理统计="+sql.toString());
		   
		   SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		   query.addScalar("cnt",Hibernate.LONG);
	       Long size=(Long)query.uniqueResult();
	       return size.intValue();
	   }
	/**
	    * 【发布人管理】 文档第13页   2010-02-01 12:25
	    * 参数:
	    *  grade:级别
	    *  yccc:预测场次
	    *  zql
	    * @return
	    * @throws DaoException//t.yccc t.zdgs
	    */
	   public List findBackendXinshuiPubList(Map params)throws DaoException{
		   int startRow=Integer.parseInt(params.get("startRow").toString());
	       int pageSize=Integer.parseInt(params.get("pageSize").toString());
	       String minjianGaoshouGrade=(String)params.get("minjianGaoshouGrade");
		   StringBuilder sql = new StringBuilder("select   username,USER_GRADE,yccc,zql,zdgs,sold_money,peichang_money,last_time  ");
		   sql.append("  ,orderCntPerChang,userid,rowno  from  ");
		   sql.append(" (select m.username,m.USER_GRADE,m.yccc,m.zql,m.zdgs,m.sold_money,m.peichang_money,m.last_time  ");
		   sql.append("  ,m.orderCntPerChang,m.userid,rownum rowno  from  ");
		   
		   sql.append(" (select t.username,t.USER_GRADE,t.yccc,t.zql,t.zdgs,to_char(t.sold_money,'9999999999999') sold_money,to_char(t.peichang_money,'999999999999999') peichang_money,to_char(t.last_time,'yyyy-mm-dd') last_time  ");
		   sql.append("  ,case when t.yccc=0 then 0 when t.yccc>0 then  t.zdgs/t.yccc  end  orderCntPerChang,t.userid  from  ");
		   sql.append(" v_backend_pub_usermanage t  ");
		   
		   sql.append(" where  t.user_grade>='"+minjianGaoshouGrade+"'");//判断条件:user_grade>=
		   String minZdgs=(String)params.get("minZdgs");//最小总订购数
		   if(StringUtils.isNotEmpty(minZdgs)){
			   sql.append(" and t.zdgs >=to_number('"+minZdgs+"') ");
		   }
		   String maxZdgs=(String)params.get("maxZdgs");//最大总订购数
		   if(StringUtils.isNotEmpty(maxZdgs)){
			   sql.append(" and t.zdgs  <=to_number('"+maxZdgs+"') ");
		   }
		   //用户级别
		   String minUserGrade=(String)params.get("minUserGrade");
		   if(StringUtils.isNotEmpty(minUserGrade)){
			   sql.append(" and t.user_grade >=to_number('"+minUserGrade+"') ");
		   }
		   String maxUserGrade=(String)params.get("maxUserGrade");
		   if(StringUtils.isNotEmpty(maxUserGrade)){
			   sql.append(" and t.user_grade  <=to_number('"+maxUserGrade+"') ");
		   }
		   
		   //预测场次
		   String minYccc=(String)params.get("minYccc");
		   if(StringUtils.isNotEmpty(minYccc)){
			   sql.append(" and t.yccc  >=to_number('"+minYccc+"') ");
		   }
		   String maxYccc=(String)params.get("maxYccc");
		   if(StringUtils.isNotEmpty(maxYccc)){
			   sql.append(" and t.yccc  <=to_number('"+maxYccc+"') ");
		   }
		   //准确率
		   String minZql=(String)params.get("minZql");
		   if(StringUtils.isNotEmpty(minZql)){
			   sql.append(" and t.zql  >= to_number('"+minZql+"')*t.yccc ");
		   }
		   String maxZql=(String)params.get("maxZql");
		   if(StringUtils.isNotEmpty(maxZql)){
			   sql.append(" and t.zql  <= to_number('"+maxZql+"')*t.yccc ");
		   }
		 
		   //发布人
		   String username=(String)params.get("username");
		   if(StringUtils.isNotEmpty(username)){
			   sql.append(" and t.username like '%"+username+"%'");
		   }
		   
		   String orderFiled=(String)params.get("orderFiled");
		   String orderType=(String)params.get("orderType");
		   if (StringUtils.isNotEmpty(orderFiled)&&StringUtils.isNotEmpty(orderType)) {
			    if("orderCntPerChang".equals(orderFiled)){
			    	sql.append("  order by "+orderFiled+"  "+orderType);
			    }else{
			    	sql.append("  order by t."+orderFiled+"  "+orderType);
			    }
		   }
		   int endRow=startRow+pageSize;
		   sql.append(" ) m  where  rownum<="+endRow+")  where  rowno>"+startRow);
		   //log.info("发布人管理列表="+sql.toString());
		   SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		   //
		   List list=query.list();
	       BackendPubmanageVO vo=null;
	       List result=new ArrayList();
	       for(Iterator e=list.iterator();e.hasNext();){
	    	   vo=new BackendPubmanageVO();
	    	   Object[]  obj=(Object[])e.next();
	    	   String _username=(String)obj[0]; //发布人
	    	   vo.setUsername(_username);
	    	   
	    	   Character userGrade=(Character)obj[1];
	    	   vo.setUserGrade(userGrade.toString());
	    	   BigDecimal _yccc=(BigDecimal)obj[2]; //预测场次
	    	   if(_yccc!=null){
	    	     vo.setYccc(_yccc.longValue());
	    	   }else{
	    		   vo.setYccc(0L);
	    	   }
	    	   BigDecimal zql=(BigDecimal)obj[3];//准确率
	    	   if(zql.doubleValue()>0){
	    	     vo.setZql(zql.doubleValue()/vo.getYccc());
	    	   }
	    	   BigDecimal zdgs=(BigDecimal)obj[4];//总订购数
	    	   vo.setZdgs(zdgs.longValue());
	    	   String soldMoney=(String)obj[5];//销售额
	    	   vo.setSoldMoney(soldMoney);
	    	   String peichangMoney=(String)obj[6];//赔付款
	    	   vo.setPeichangMoney(peichangMoney);
	    	   String lastTime=(String)obj[7];//最新发布时间
	    	   vo.setLastTime(lastTime);
	    	   
	    	   BigDecimal orderCntPerChang=(BigDecimal)obj[8];
	    	   vo.setOrderCntPerChang(orderCntPerChang.doubleValue());
	    	   
	    	   vo.setUserId(new Long(obj[9].toString()));//发布人用户ID
	    	   result.add(vo);
	       }
	   	//System.out.println("**="+result.size());
		 return result;
	   }
	   
	 /**
	  * 心水后台管理——赛事选择界面图示
	  * 【后台客服】根据条件查询选择赛事信息,为进一步生成心水对阵做准备,对应视图:v_findbackendagainstlist
	  * 参数:
	  * raceId:赛事名称 RACE_ID
	  * raceSeason:赛季
	  * rounds:轮次
	  *status:状态,'已选择','未选择','已过期','已完成'
	  *from:起始时间
	  *to:结束时间
	  *teamName:球队名称
	  *orderFiled:排序字段
	  *orderType:排序类型'ASC'|'DESC'
	  * @return
	  */
	 public List findBackendAgainstList(Map params)throws DaoException{
		 StringBuilder sql = new StringBuilder("select a.AGAINST_ID, a.RACE_NAME, a.ROUNDS_NAME,a.RACE_SEASON_NAME,a.HOST_NAME,a.GUEST_NAME,to_char(a.START_TIME,'yy-mm-dd hh24:mi') START_TIME,");
		 	sql.append(" a.ASIA_PEI,a.BIG_SMALL,  cast(a.STATUS as varchar(60)) STATUS ");
		 	sql.append(" from  v_findbackendagainstlist a where 1=1 ");
			Long raceId=(Long)params.get("raceId");
			if(raceId!= null) {
			   sql.append("  and a.RACE_ID="+raceId);
			}
			Long raceSeason=(Long)params.get("raceSeason");
			if(raceSeason!=null){
				sql.append("  and a.RACE_SEASON_ID="+raceSeason);
			}
			Long rounds=(Long)params.get("rounds");
			if(rounds!=null){
				sql.append("  and  a.ROUNDS="+rounds);
			}
			String status=(String)params.get("status");
			if(StringUtils.isNotEmpty(status)){
				sql.append("  and  a.status='"+status+"'");
			}
			String from=(String)params.get("from");
			if(StringUtils.isNotEmpty(from)){
				sql.append(" and to_char(a.START_TIME,'yyyy-mm-dd')>='"+from+"'");
			}
			String to=(String)params.get("to");
			if(StringUtils.isNotEmpty(from)){
				sql.append(" and to_char(a.START_TIME,'yyyy-mm-dd')<='"+to+"'");
			}
			String teamName=(String)params.get("teamName");
			if(StringUtils.isNotEmpty(teamName)){
				sql.append(" and ( a.HOST_NAME like '%"+teamName+"%'  or a.GUEST_NAME  like '%"+teamName+"%' )");
			}
			String orderFiled=(String)params.get("orderFiled");
			String orderType=(String)params.get("orderType");
			if (StringUtils.isNotEmpty(orderFiled)&&StringUtils.isNotEmpty(orderType)) {
				sql.append("  order by a."+orderFiled+"  "+orderType);
			}
			//log.info(sql.toString());
	        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
	        List list=query.list();
	        XinshuiAgainstVO vo=null;
	        List result=new ArrayList();
	        for(Iterator e=list.iterator();e.hasNext();){
	        	Object[]  arr=(Object[])e.next();
	        	vo=new XinshuiAgainstVO();
	            vo.setAgainstId(new Long(arr[0].toString()));
	        	String raceName=(String)arr[1];
	        	vo.setRaceName(raceName);
	        	String _rounds=(String)arr[2];
	        	vo.setRoundsName(_rounds);
	        	
	        	String _raceSeason=(String)arr[3];
	        	vo.setRaceSeasonName(_raceSeason);
	        	
	        	String hostName=(String)arr[4];
	        	vo.setHostName(hostName);
	        	String guestName=(String)arr[5];
	        	vo.setGuestName(guestName);
	        	
	        	vo.setStartTime((String)arr[6]);
	        	
	        	vo.setAsiaPei((String)arr[7]);
	        	vo.setBigSmall((String)arr[8]);
	        	String  _status=(String)arr[9];
	        	vo.setStatus(_status);
	        	result.add(vo);
	        }
		return result;
	   }
	/**
	 * 3.1.1心水发布页面,心水发布——赛事及心水类型选择界面
	 * 民间高手根据条件查询可以发布心水的对阵信息,对应表:T_XINSHUI_SCHEDULE,T_AGAINST
	 * raceType:赛事类型 RACE_TYPE
	 * sortType:排序方式  按照对阵开赛时间  升序排列
	 */
	@Override
	public List findScheduleList(Map params) throws DaoException {
		StringBuilder sql = new StringBuilder("select  b.RACE_NAME,b.START_TIME,b.HOST_NAME,b.GUEST_NAME,a.DEADLINE from  T_AGAINST a,T_AGAINST  b where a.AGAINST_ID=b.AGAINST_ID");
		Long raceId=(Long)params.get("raceId");
		if(raceId!= null) {
		   sql.append("  where b.RACE_ID="+raceId);
		}
		sql.append(" order by  START_TIME");
        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		List list=query.list();
		Against against=null;
		List resultList=new ArrayList();
		for(Iterator e=list.iterator();e.hasNext();){
		    against=new Against();
			Object[]  obj=(Object[])e.next();
			String raceName=(String)obj[0];
			against.setRaceName(raceName);
			Date startTime=(Date)obj[1];
			against.setStartTime(startTime);
			String hostName=(String)obj[2];
			against.setHostName(hostName);
			String guestName=(String)obj[3];
			against.setGuestName(guestName);
			//大小盘的数据怎么回事
			resultList.add(against);
		}
	  return resultList;
	}
	 /**
	    * 赛事管理 本功能应该分页 2010-02-01 10:49 文档12页
	    * 参数:
	    * raceId:赛事ID
	    * startRow:起始记录数
	    * pageSize:每页最大记录数
	    * orderFiled:排序字段
	    *orderType:排序类型'ASC'|'DESC'
	    *status:状态:"未完赛"、"已完赛"、"已取消"
	    * 操作：
	    *  心水:点击后即进入心水管理页面，列出对应赛事的所有心水
	    *  取消:
	    */
	   public List findBackendXinshuiAgainstList(Map params)throws DaoException{
	
		   
		   
		    StringBuilder sql = new StringBuilder("select a.AGAINST_ID,a.PHASE,a.RACE_NAME,a.ROUNDS_NAME,a.HOST_NAME,a.GUEST_NAME,");
		   	sql.append(" a.RACE_SEASON_NAME, to_char(a.START_TIME,'yy-mm-dd hh24:mi') START_TIME,a.host_win,a.guest_win,a.big_ball,a.SMALL_BALL,to_char(a.DEADLINE,'yy-mm-dd hh24:mi') DEADLINE,a.MYSTATUS,a.race_id,a.race_season_id from v_backendxinshuiagainstlist a  where 1=1 ");
			Long raceId=(Long)params.get("raceId");
			if(raceId!= null) {
			   sql.append("  and a.RACE_ID="+raceId);
			}
			Long raceSeason=(Long)params.get("raceSeason");
			if(raceSeason!=null){
				sql.append("  and a.RACE_SEASON_ID="+raceSeason);
			}
			Long rounds=(Long)params.get("rounds");
			if(rounds!=null){
				sql.append("  and  a.ROUNDS="+rounds);
			}
			String from=(String)params.get("from");
			if(StringUtils.isNotEmpty(from)){
				sql.append(" and  a.START_TIME >=to_date('"+from+"','yyyy-mm-dd')");
			}
			String to=(String)params.get("to");
            if(StringUtils.isNotEmpty(to)){
            	sql.append(" and  a.START_TIME <= to_date('"+to+"','yyyy-mm-dd')");
			}
            String teamName=(String)params.get("teamName");
            if(StringUtils.isNotEmpty(teamName)){
            	sql.append(" and  (a.HOST_NAME like '%"+teamName+"%'  or  a.GUEST_NAME   like '%"+teamName+"%')");
            }
            String status=(String)params.get("status");
            if(StringUtils.isNotEmpty(status)){
            	sql.append(" and a.MYSTATUS='"+status+"'");
            }
            String orderFiled=(String)params.get("orderFiled");
			String orderType=(String)params.get("orderType");
			if (StringUtils.isNotEmpty(orderFiled)&&StringUtils.isNotEmpty(orderType)) {
				if("START_TIME".equalsIgnoreCase(orderFiled)){
				    sql.append("  order by a."+orderFiled+"  "+orderType);
				}else if("DEADLINE".equalsIgnoreCase(orderFiled)){
					sql.append("  order by a."+orderFiled+"  "+orderType);
				}
			}
            //log.info(sql.toString());
	        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
	        int startRow=Integer.parseInt(params.get("startRow").toString());
	        query.setFirstResult(startRow);
	        int pageSize=Integer.parseInt(params.get("pageSize").toString());
	        query.setMaxResults(pageSize);
	        List list=query.list();
	        XinshuiAgainstVO vo=null;
	        List result=new ArrayList();
	        for(Iterator e=list.iterator();e.hasNext();){
	        	Object[]  obj=(Object[])e.next();
	        	vo=new XinshuiAgainstVO();
	        	Long againstId=new Long(obj[0].toString());
	        	vo.setAgainstId(againstId);
	        	
	        	
	        	String phase=(String)obj[1];
	        	vo.setPhase(phase);//PHASE
	        	
	        	String raceName=(String)obj[2];
	        	vo.setRaceName(raceName);//RACE_NAME
	        	
	        	vo.setRoundsName((String)obj[3]);//ROUNDS_NAME
	        	
	        	vo.setHostName((String)obj[4]);//HOST_NAME
	        	vo.setGuestName((String)obj[5]);//GUEST_NAME
	        	
	        	vo.setRaceSeasonName((String)obj[6]);//RACE_SEASON_NAME
	        	//log.info("........WJJ "+(String)obj[6]);
	        	vo.setStartTime((String)obj[7]);//START_TIME
	        	
	        	vo.setHostWin(new Long(obj[8].toString()));//host_win
	        	vo.setGuestWin(new Long(obj[9].toString()));//guest_win
	        	
	        	vo.setBigBall(new Long(obj[10].toString()));//big_ball
	        	vo.setSmallBall(new Long(obj[11].toString()));//small_ball
	        	
	        	vo.setDeadline((String)obj[12]);//DEADLINE
	        	if(obj[13]!=null){
	        	  
	        	  vo.setStatus(obj[13].toString());//MYSTATUS
	        	}
	        	vo.setRaceId(new Long(obj[14].toString()));
	        	vo.setRaceSeason(new Long(obj[15].toString()));
	        	result.add(vo);
	        }
	        return result;
	   }
	   public int findBackendXinshuiAgainstSize(Map params)throws DaoException{
		   
		    StringBuilder sql = new StringBuilder("select  count(1) cnt  from v_backendxinshuiagainstlist a  where 1=1 ");
		    Long raceId=(Long)params.get("raceId");
			if(raceId!= null) {
			   sql.append("  and a.RACE_ID="+raceId);
			}
			Long raceSeason=(Long)params.get("raceSeason");
			if(raceSeason!=null){
				sql.append("  and a.RACE_SEASON_ID="+raceSeason);
			}
			Long rounds=(Long)params.get("rounds");
			if(rounds!=null){
				sql.append("  and  a.ROUNDS="+rounds);
			}
			String from=(String)params.get("from");
			if(StringUtils.isNotEmpty(from)){
				sql.append(" and  a.START_TIME >=to_date('"+from+"','yyyy-mm-dd')");
			}
			String to=(String)params.get("to");
            if(StringUtils.isNotEmpty(to)){
            	sql.append(" and  a.START_TIME <= to_date('"+to+"','yyyy-mm-dd')");
			}
           String teamName=(String)params.get("teamName");
           if(StringUtils.isNotEmpty(teamName)){
           	sql.append(" and  (a.HOST_NAME like '%"+teamName+"%'  or  a.GUEST_NAME   like '%"+teamName+"%')");
           }
           String status=(String)params.get("status");
           if(StringUtils.isNotEmpty(status)){
           	sql.append(" and a.MYSTATUS='"+status+"'");
           }
         
			
           System.out.println("------------------"+sql.toString());
	        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
	        query.addScalar("cnt",Hibernate.INTEGER);
	        Integer size=(Integer)query.uniqueResult();
	      return size.intValue();
	   }
	   /**
	     * 加载心水详情
	     * @param id
	     * @return
	     * @throws DaoException
	     */
	    public XinshuiDetailVO loadXinshuiDetailVO(Long c2cId)throws DaoException{
	    	  
	    	    StringBuilder sql = new StringBuilder("select t.XINSHUI_NO,t.TX_USERNAME,a.HOST_NAME,t.GUEST_NAME,to_char(a.START_TIME,'yy-mm-dd hh24:mi') START_TIME,t.RACE_NAME   ");
			    sql.append(",to_char(t.PUB_TIME,'yy-mm-dd hh24:mi') PUB_TIME, (select  count(1) from T_XINSHUI_ORDER  m  where   m.PRODUCT_ID=t.c2c_id) sold_cnt");
			    sql.append(",decode(t.STATUS,'1',cast('发布中' as varchar(60)),'2',cast('赢' as varchar(60)),'3',cast('负' as varchar(60)),'4',cast('走' as varchar(60)),'5',cast('已关闭' as varchar(60))) STATUS,to_char(t.ENSURE_MONEY,'99999999.99')ENSURE_MONEY ,to_char(t.PRICE,'999999.99') PRICE,t.ZH_DESC,SELECT_TYPE ");
			    sql.append(",(select t.img  from T_TEAM t where  t.ID=a.HOST_ID) host_img,(select t.img  from T_TEAM t where  t.ID=a.GUEST_ID) guest_Img ");
			    sql.append(" from  T_C2C_PRODUCT  t,T_AGAINST a where a.AGAINST_ID=t.AGAINST_ID and  t.C2C_ID="+c2cId);
			    Object[] o = (Object[])this.getSession().createSQLQuery(sql.toString()).uniqueResult();
			    XinshuiDetailVO vo=new XinshuiDetailVO();
			    vo.setXinshuiNo((String)o[0]);//XINSHUI_NO
			    vo.setTxUsername((String)o[1]);//TX_USERNAME
			    vo.setHostName((String)o[2]);
			    vo.setGuestName((String)o[3]);
			    vo.setStartTime((String)o[4]);
			    vo.setRaceName((String)o[5]);
			    vo.setPubTime((String)o[6]);//发布时间
			    Long orderCnt=new Long(o[7].toString());//购买人数
			    vo.setOrderCnt(orderCnt);
			    vo.setStatus((String)o[8]);
			    vo.setEnsureMoney((String)o[9]);
			    vo.setPrice((String)o[10]);
			    String zhDesc=(String)o[11];//心水说明
			    vo.setZhDesc(zhDesc);
			    String selectType=o[12].toString();
			    vo.setSelectType(selectType);
			    Object hostImg=o[13];
			    if(hostImg!=null){
			      vo.setHostImg(hostImg.toString());
	            }
			    Object guestImg=o[13];
			    if(guestImg!=null){
			    	vo.setGuestImg(guestImg.toString());
			    }
	    	return vo;
	    }
	    /**
	     * 客服取消心水订单  2010-02-05 15:31
	     * @param orderId
	     * @param log
	     * @throws DaoException
	     */
	    public void cancelXinshuiOrder(Long orderId,CSHandleLog  log)throws DaoException{
	    	Connection conn=null;
	    	CallableStatement cstmt =null;
	 		try { 
	 			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	 			  cstmt = conn.prepareCall("{call XINSHUI.cancelXinshuiOrder(?,?,?,?)}"); 
	 			  cstmt.setLong(1, orderId);
	 			  cstmt.setLong(2, log.getCsUserId());
	 			  cstmt.setString(3,log.getCsName());
	 			  cstmt.setString(4,log.getIp());
	 			  
	 			  cstmt.execute();
	 			
	 		} catch (SQLException e) { 
	 			e.printStackTrace();
	 			  //throw new DaoException(e.getLocalizedMessage());
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
	    
	    }
	    /**
	     * 客服取消赛事  2010-02-05 16:33
	     * 参数:
	     *  againstId:赛事ID
	     * @param log
	     * @throws DaoException
	     */
	    public void cancelAgainst(Long againstId,CSHandleLog  log)throws DaoException{
	    	Connection conn=null;
	    	CallableStatement cstmt =null;
	 		try { 
	 			  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	 			  cstmt = conn.prepareCall("{call XINSHUI.cancelAgainst(?,?,?,?)}"); 
	 			  cstmt.setLong(1, againstId);
	 			  cstmt.setLong(2, log.getCsUserId());
	 			  cstmt.setString(3,log.getCsName());
	 			  cstmt.setString(4,log.getIp());
	 			  
	 			  cstmt.execute();
	 			
	 		} catch (SQLException e) { 
	 			e.printStackTrace();
	 			  //throw new DaoException(e.getLocalizedMessage());
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
	    
	    }
	    
	   
}
