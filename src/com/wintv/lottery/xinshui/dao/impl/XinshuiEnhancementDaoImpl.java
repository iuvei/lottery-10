package com.wintv.lottery.xinshui.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.xinshui.dao.XinshuiEnhancementDao;
@Repository("xinshuiEnhanceDao")
public class XinshuiEnhancementDaoImpl extends BaseHibernate  implements XinshuiEnhancementDao{
	/**
	 * 判断当前用户是否有权限查看该心水 2010-05-06 09:31
	 * 返回结果:
	 *  1:可以查看
	 *  0:没有权限查看
	 */
    public int isAllowLook(Long c2cId,Long userId)throws DaoException{
    	Connection conn=null;
    	PreparedStatement pstmt=null;
    	ResultSet rs=null;
    	try{
    		String sql="select count(1) cnt from  t_xinshui_order t where t.product_id=? and t.buy_user_id=?";
    		conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
    		pstmt=conn.prepareStatement(sql);
    		pstmt.setLong(1, c2cId);
    		pstmt.setLong(2, userId);
    		rs=pstmt.executeQuery();
    		while(rs.next()){
    		   return rs.getInt("cnt");
    		}
    	}catch(Exception e){
    	   e.printStackTrace();
    	}finally{
    		closeAll(rs,pstmt,conn);
    	}
    	return 0;
    }
	/**
	 * 根据对阵ID加载开赛时间
	 */
    public Date loadStartTime(Long againstId)throws DaoException{
    	Connection conn=null;
    	PreparedStatement pstmt=null;
    	ResultSet rs=null;
    	try{
    		String sql="select t.start_time  from t_against t where t.against_id=?";
    		conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
    		pstmt=conn.prepareStatement(sql);
    		pstmt.setLong(1, againstId);
    		rs=pstmt.executeQuery();
    		while(rs.next()){
    		   return rs.getDate("start_time");
    		}
    	}catch(Exception e){
    	   e.printStackTrace();
    	}finally{
    		closeAll(rs,pstmt,conn);
    	}
    	return null;
    }
    /**
	 * 根据对阵ID加载开赛时间
	 */
    public Date loadStartTimeByC2C(Long c2cId)throws DaoException{
    	Connection conn=null;
    	PreparedStatement pstmt=null;
    	ResultSet rs=null;
    	try{
    		String sql="select a.start_time  from t_against a,t_c2c_product b where a.against_id=b.against_id and b.c2c_id=?";
    		conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
    		pstmt=conn.prepareStatement(sql);
    		pstmt.setLong(1, c2cId);
    		rs=pstmt.executeQuery();
    		while(rs.next()){
    		   return rs.getDate("start_time");
    		}
    	}catch(Exception e){
    	   e.printStackTrace();
    	}finally{
    		closeAll(rs,pstmt,conn);
    	}
    	return null;
    }
}
