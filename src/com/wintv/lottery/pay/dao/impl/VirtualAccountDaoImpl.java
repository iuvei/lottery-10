package com.wintv.lottery.pay.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import java.util.Iterator;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.PaymentException;
import com.wintv.framework.pojo.User;
import com.wintv.framework.pojo.VirtualAccount;
import com.wintv.framework.utils.OracleSqlUtil;
import com.wintv.lottery.pay.dao.VirtualAccountDao;

@Repository("virtualAccountDao")
public class VirtualAccountDaoImpl extends BaseHibernate<VirtualAccount,Long> implements VirtualAccountDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/** 
	 * 为用户创建虚拟账户
	 * 参数:
	 * account:用户基本信息  用户ID
	 * DaoException:数据库操作异常
	 * 返回:返回的用户账户的部分信息
	 */
	public VirtualAccount createVAAccount(Map params)throws DaoException{
		VirtualAccount account=null;
		try{
			account=new VirtualAccount();
			User user = (User)params.get("user");
			account.setTxUserId(user.getUserid());
			account.setUserName(user.getUsername());
			Long point = (Long)params.get("point");
			account.setPoint(point);
			//默认用户虚拟账户 状态是正常的
			account.setStatus("1");
			this.saveObject(account);
		}catch(DaoException e){
			throw new DaoException(e.getLocalizedMessage());
		}
		return account;
	}
	public void test(){
		String sql="select a.TX_MONEY  m, a.tx_user_id  userId  from   T_VA_TRANSACTION_LOG  a " +
				"union all  select   b.MONEY m,b.user_id  userId  from T_CHARGE_LOG b";
		SQLQuery q=this.getSession().createSQLQuery(sql);
	    List list=q.list();
	    for(Iterator e=list.iterator();e.hasNext();){
	    	Object[] row=(Object[])e.next();
	    	System.out.println(row[0].toString());
	    	System.out.println(row[1].toString());

	    }
		
	}
	/**
	 * 根据后台彩金捐赠订单和用户名列表 批量给用户增加彩金金额
	 * 
	 * @author hikin yao
	 * @param userNameList 
	 *            用户名列表 中间逗号分隔 格式为"name1,name2,name3"
	 * @param money
	 *            彩金金额
	 * @throws DaoException
	 */
	public Integer bathAddCaiJinDonateByUserNamesAdmin(String userNameList,BigDecimal money
			) throws DaoException {
		StringBuilder sqlStr = new StringBuilder(" update t_virtual_account c set c.mosaic_gold=c.mosaic_gold+"+money);
		sqlStr.append(" where c.tx_user_id in (select a.userid from t_user a where a.username in ( ");
		sqlStr.append(OracleSqlUtil.formatSqlAddCommas(userNameList));// 加单引号 格式化为"'name1','name2','name3'"
		sqlStr.append(" ) )" );
		Integer result = jdbcTemplate.update(sqlStr.toString());
		return result;
	}
	/**
	 * 根据用户ID返回一个用户账户信息
	 * @author hikin yao
	 * @param userId 用户Id
	 * @return
	 * @throws DaoException
	 */
	public VirtualAccount getUserAccountByUserId(Long userId) throws DaoException{
		return findUniqueBy("txUserId",userId);
	}
	public VirtualAccount findVirtualAccount(Long userid) throws PaymentException, DaoException {
		return (VirtualAccount)this.getSession().createQuery("from VirtualAccount t  where t.txUserId="+userid).list().iterator().next();
	}
}
