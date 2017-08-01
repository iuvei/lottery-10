package com.wintv.framework.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;

/**
 * 天彩网订单号生成工具类
 * @author Administrator
 *
 */
@Repository("orderNoGenDao")
public final class OrderNoGenDaoImpl extends BaseHibernate implements OrderNoGenDao{
	public String gen(String flg)throws DaoException{
	  try{
		StringBuilder sb=new StringBuilder();
		String time=new SimpleDateFormat("yyyyMMdd").format(new Date()); 
		sb.append(time);
		sb.append(flg);
		SQLQuery q=getSession().createSQLQuery("select  SEQ_ORDER_NO.NEXTVAL from dual");
		List list=q.list();
        Number n=(Number)list.iterator().next();
		String seq=n.toString();
		sb.append(seq);
		
	    int rValue=(int)(Math.random()*900)+100; 
		sb.append(rValue);
	   return sb.toString();
	  }catch(Exception e){
		 e.printStackTrace();
	  }
	  return null;
	}
	public static void main(String[] args){
		int rValue=(int)(Math.random()*900)+100; 
		System.out.println(rValue);
	}
	
}
