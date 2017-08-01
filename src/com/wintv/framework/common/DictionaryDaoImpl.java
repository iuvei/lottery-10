package com.wintv.framework.common;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
 
import com.wintv.framework.pojo.Dictionary;

@SuppressWarnings("unchecked")
@Repository("dictionaryDao")
public class DictionaryDaoImpl extends BaseHibernate implements DictionaryDao{

	@Override
	public Dictionary loadDictionary(String code,String type) {
		Query q=this.getSession().createQuery("from  Dictionary dic where dic.code=? and dic.type=?");
		q.setString(0, code);
		q.setString(1, type);
		List list=q.list();
		if(list!=null&&list.size()==1){
			return (Dictionary)list.iterator().next();
		}
		return null;
	}
	/**
	 * 根据分类与CODE查询VALUE
	 */
	public String getValue(String code,String type) throws DaoException{
		SQLQuery q=this.getSession().createSQLQuery("select value from  t_dictionary dic where dic.code=? and dic.type=?");
		q.setString(0, code);
		q.setString(1, type);
		Object o=q.uniqueResult();
		
		return o.toString();
	}
	/**
	 * 根据字典类型获取相应字典列表
	 */
	public List<Dictionary> getDictionaryListByType(String type) throws DaoException{
		Query q=this.getSession().createQuery("from  Dictionary dic where dic.type=?");
		q.setString(0, type);
		List<Dictionary> result=q.list();
		if(result!=null){
			return result;
		}
		return null;
	}
	
}
