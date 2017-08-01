package com.wintv.framework.common;

import java.util.List;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Dictionary;

@SuppressWarnings("unchecked")
public interface DictionaryDao{
	/**
	 * 根据类型与CODE查询数据字典
	 * @param type
	 * @param code
	 * @return
	 * @throws DaoException
	 */
	public  Dictionary loadDictionary(String type,String code);
	/**
	 * 根据分类与CODE查询VALUE
	 */
	public String getValue(String code,String type) throws DaoException;
	
	/**
	 * 根据字典类型获取相应字典列表
	 */
	public List<Dictionary> getDictionaryListByType(String type) throws DaoException;
}
