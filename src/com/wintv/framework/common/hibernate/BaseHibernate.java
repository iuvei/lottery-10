package com.wintv.framework.common.hibernate;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wintv.framework.common.GenericsUtils;
import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.page.Page;
import com.wintv.framework.common.hibernate.HibernateSessionFactory;

public class BaseHibernate<T, PK extends Serializable>  extends HibernateDaoSupport implements BaseDao<T,PK>{

	private Class<T> entityClass;

	protected Class<T> getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("unchecked")
	public BaseHibernate() {
		entityClass = GenericsUtils.getGenericClass(getClass());
	}
	
	public void storeAll(Collection<T> persistentObjects){
		getHibernateTemplate().saveOrUpdateAll(persistentObjects);
	}

	@SuppressWarnings("unchecked")
	public T read(PK id) {
		return (T) getHibernateTemplate().get(getEntityClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> read(final PK[] ids) {
		return (List<T>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createCriteria(getEntityClass()).add(
								Restrictions.in("id", ids)).list();
					}
				});
	}

	@SuppressWarnings("unchecked")
	public List<T> readAll() {
		return (List<T>) getHibernateTemplate().loadAll(getEntityClass());
	}

	public void store(T o) {
		getHibernateTemplate().saveOrUpdate(o);
	}
	
	public Long saveObject(Object o)throws DaoException{
		try{
			
		 return (Long)this.getSession().save(o);
		}catch(Exception e){
			throw new DaoException(e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * update by sql
	 * 
	 * @param sql
	 * @return Affected the number of records
	 */
	@Deprecated
	public int updateBySQL(String sql) {
		int ret = 0;
		try {
			Session sess = HibernateSessionFactory.getSession();
			Transaction tx = sess.beginTransaction();
			SQLQuery queryObject = sess.createSQLQuery(sql.toString());
			ret = queryObject.executeUpdate();
			tx.commit();
		} catch (RuntimeException re) {
			re.printStackTrace(System.out);
			throw new RuntimeException("update by sql failed.", re);
		} finally {
			HibernateSessionFactory.closeSession();
			return ret;
		}
	}
	
	public void delete(PK id) {
		delete(read(id));
	}

	public void delete(PK[] ids) {
		delete(read(ids));
	}

	public void delete(T o) {
		getHibernateTemplate().delete(o);
	}

	public void delete(Collection<T> persistentObjects) {
		getHibernateTemplate().deleteAll(persistentObjects);
	}

	@SuppressWarnings("unchecked")
	public T findUniqueBy(String name, Object value) {
		String alias = getAlias(name);
		StringBuffer hsql = new StringBuffer();
		if (alias == null) {
			hsql.append("from ").append(getEntityClass().getName()).append(
					" where ").append(name).append("=?");
		} else {
			hsql.append("from ").append(getEntityClass().getName()).append(
					" as ").append(alias).append(" where ").append(name)
					.append("=?");
		}
		Query query = getSession().createQuery(hsql.toString());
		query.setParameter(0, value);
		return (T) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> findBy(String name, Object value) {
		String alias = getAlias(name);
		StringBuffer hsql = new StringBuffer();
		if (alias == null) {
			hsql.append("from ").append(getEntityClass().getName()).append(
					" where ").append(name).append("=?");
		} else {
			hsql.append("from ").append(getEntityClass().getName()).append(
					" as ").append(alias).append(" where ").append(name)
					.append("=?");
		}
		Query query = getSession().createQuery(hsql.toString());
		query.setParameter(0, value);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByLike(String name, String value) {
		String alias = getAlias(name);
		StringBuffer hsql = new StringBuffer();
		if (alias == null) {
			hsql.append("from ").append(getEntityClass().getName()).append(
					" where ").append(name).append("=?");
		} else {
			hsql.append("from ").append(getEntityClass().getName()).append(
					" as ").append(alias).append(" where ").append(name)
					.append(" like '%").append(value).append("%'");
		}
		Query query = getSession().createQuery(hsql.toString());
		return query.list();
	}

	private String getAlias(String name) {
		if (name == null || name.indexOf(".") == -1)
			return null;
		return StringUtils.substringBefore(name, ".");
	}

	public Page pagedQuery(String queryString, int pageNo, int pageSize) {
		return pagedQuery(queryString, null, pageNo, pageSize);
	}
	public Page pagedQueryWithQueryCache(String queryString, int pageNo, int pageSize,String cacheRegion) {
		return pagedQueryWithQueryCache(queryString, null, pageNo, pageSize, cacheRegion);
	}
	
	public List listedQuery(String queryString, Object[] args){
         Query query = getSession().createQuery(queryString);
		
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		
		return query.list();
	}
	public List listedQueryWithCache(String queryString, Object[] args,String cacheRegion){
        Query query = getSession().createQuery(queryString).setCacheable(true);
		if(StringUtils.isNotBlank(cacheRegion)){
			query.setCacheRegion(cacheRegion);
		}
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		
		return query.list();
	}
	
	public Page pagedQuery(String queryString, Object[] args, int pageNo,
			int pageSize) {
		Query query = getSession().createQuery(queryString);
		
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		int begin = queryString.toLowerCase().indexOf("select");
		int end = queryString.toLowerCase().indexOf("from");
		String objectString = queryString.substring(begin+6, end);
		objectString = (objectString.indexOf("distinct")>0)?objectString.substring(objectString.indexOf("distinct")+8,objectString.length()-1):objectString;
        String countQueryString = " select count (distinct " + objectString +") "+ removeOrders(removeSelect(queryString)) + ")";
		List countlist = getHibernateTemplate().find(countQueryString, args);
		int totalCount = ((Number) countlist.get(0)).intValue();
		
		return getPageResult(query, totalCount, pageNo, pageSize);
	}
	public Page pagedQueryWithQueryCache(String queryString, Object[] args, int pageNo,
			int pageSize,String cacheRegion) {
		Query query = getSession().createQuery(queryString).setCacheable(true);
		if(StringUtils.isNotBlank(cacheRegion)){
			query.setCacheRegion(cacheRegion);
		}
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		int begin = queryString.toLowerCase().indexOf("select");
		int end = queryString.toLowerCase().indexOf("from");
		String objectString = queryString.substring(begin+6, end);
		objectString = (objectString.indexOf("distinct")>0)?objectString.substring(objectString.indexOf("distinct")+8,objectString.length()-1):objectString;
        String countQueryString = " select count (distinct " + objectString +") "+removeOrders( removeSelect(queryString)) + ")";
		List countlist = getHibernateTemplate().find(countQueryString, args);
		int totalCount = (Integer) countlist.get(0);
		
		return getPageResult(query, totalCount, pageNo, pageSize);
	}
	private static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	private static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	

	private static Page getPageResult(Query q, int totalCount, int pageNo,
			int pageSize) {
		if (totalCount < 1)
			return new Page();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List list = q.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}
	public boolean updateBySql(String sql) {
		try {
			 Session sess =this.getSession();
			 SQLQuery queryObject = sess.createSQLQuery(sql.toString());
			return queryObject.executeUpdate()>0;
		} catch (HibernateException e) {
			
			throw new RuntimeException("update by sql failed.", e);
		}
	}
	public void closeAll(ResultSet rs,CallableStatement proc,Connection conn){
		try{
		   if(rs!=null){
			rs.close();
		   }
		   if(proc!=null){
			   proc.close();
		   }
		   if(conn!=null){
			   conn.close();
		   }
		}catch(SQLException e){
			e.printStackTrace();
			
		}
	}
	public void closeAll(ResultSet rs,PreparedStatement pstmt,Connection conn){
		try{
		   if(rs!=null){
			rs.close();
		   }
		   if(pstmt!=null){
			   pstmt.close();
		   }
		   if(conn!=null){
			   conn.close();
		   }
		}catch(SQLException e){
			e.printStackTrace();
			
		}
	}
	public void closeAll(PreparedStatement pstmt,Connection conn){
		try{
		 
		   if(pstmt!=null){
			   pstmt.close();
		   }
		   if(conn!=null){
			   conn.close();
		   }
		}catch(SQLException e){
			e.printStackTrace();
			
		}
	}
	public void closeAll(CallableStatement csmt,Connection conn){
		try{
		   if(csmt!=null){
			   csmt.close();
		   }
		   if(conn!=null){
			   conn.close();
		   }
		}catch(SQLException e){
			e.printStackTrace();
			
		}
	}
	
	
}
