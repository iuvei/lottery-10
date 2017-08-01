package com.wintv.lottery.bet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Bonus;
import com.wintv.lottery.bet.dao.BonusDao;
import com.wintv.lottery.bet.utils.CommUtil;

@SuppressWarnings("unchecked")
@Repository("bonusDao")
public class BonusDaoImpl extends BaseHibernate<Bonus,Long> implements BonusDao{
	private static final Log log=LogFactory.getLog(BonusDaoImpl.class);
	/**根据用户ID查询 
	 * 1.中奖次数：zjCnt
	 * 2.总奖金：  allBonus
	 **/
	public Map  findMyBonus(Long userId)throws DaoException{
		String sql="select count(1) zjCnt,sum(t.money) allBonus from t_bonus t where  t.user_id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Map resultMap=null;
		try{
			resultMap=new HashMap();
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, userId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				long zjCnt=rs.getLong("zjCnt");//中奖次数
				resultMap.put("zjCnt", zjCnt);
				String allBonus=rs.getString("allBonus");//总奖金
				if(StringUtils.isNotEmpty(allBonus)){
				    allBonus=CommUtil.getCurrency(allBonus);
				}else{
				   allBonus="0";
				}
				resultMap.put("allBonus", allBonus);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
	   return resultMap;
	   
	}

}
