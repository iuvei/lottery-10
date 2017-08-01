package com.wintv.lottery.cms.dao;

import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Article;

public interface ArticleDao extends BaseDao<Article,Long>{
	/**
	 * 按照分类查询文章列表 2010-03-16 14:04
	 * 
	 **/
	public Map findArticleList(Map params)throws DaoException;
	
	

}
