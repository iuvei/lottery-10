package com.wintv.lottery.user.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.SecurityIp;
import com.wintv.lottery.user.dao.SecurityIpDao;

@Repository("securityIpDao")
@SuppressWarnings("unchecked")
public class SecurityIpDaoImpl extends BaseHibernate<SecurityIp,Long> implements SecurityIpDao {

	@Override
	public Long findRegisterTimes(String ip) throws DaoException {
		Query query = this.getSession().createQuery(
				"from SecurityIp where ip=?");
		query.setString(0, ip);
		List list = query.list();
		if (list == null || 0 == list.size()) {
			return 0L;
		}
		SecurityIp securityIp = (SecurityIp) list.get(0);
		return securityIp.getCnt();
	}

	@Override
	public void cleanIpTable() throws DaoException {
		this.getSession().createQuery("delete SecurityIp").executeUpdate();
	}

}
