package com.wintv.lottery.cms.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.cms.dao.ArticleDao;
import com.wintv.lottery.cms.service.CmsService;

@Service("cmsService")
public class CmsServiceImpl implements CmsService{
	/**
	 * 按照分类查询文章列表 2010-03-16 14:04
	 * 
	 **/
	public Map findArticleList(Map params)throws DaoException{
		return this.articleDao.findArticleList(params);
	}
	
	@Autowired
	private ArticleDao articleDao;

}
