package com.wintv.framework.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.exception.DaoException;

public class BaseDaoSupport {
	protected SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	public Long saveObject(Object o)throws DaoException{
		try{
		 return (Long)this.getSession().save(o);
		}catch(Exception e){
			throw new DaoException(e.getLocalizedMessage());
		}
	}
	
}
