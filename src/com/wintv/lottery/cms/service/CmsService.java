package com.wintv.lottery.cms.service;

import java.util.Map;

import com.wintv.framework.exception.DaoException;

public interface CmsService {
	/**
	 * 按照分类查询文章列表 2010-03-16 14:04
	 * 
	 **/
	public Map findArticleList(Map params)throws DaoException;
	

}
