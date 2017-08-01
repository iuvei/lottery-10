package com.wintv.lottery.pay.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.SecurityPwd;
import com.wintv.lottery.pay.dao.SecurityPwdDao;

@Repository("securityPwdDao")
@SuppressWarnings("unchecked")
public class SecurityPwdDaoImpl extends BaseHibernate<SecurityPwd,Long> implements SecurityPwdDao {
	
	@Override
	public Long findErrorPwdTimes(Long userid)throws DaoException {
		Query query = this.getSession().createQuery(
				"from SecurityPwd where userId=?");
		query.setLong(0, userid);
		List list = query.list();
		if (list == null || 0 == list.size()) {
			return 0L;
		}
		SecurityPwd securityPwd = (SecurityPwd) list.get(0);
		return securityPwd.getCnt();
	}
}
