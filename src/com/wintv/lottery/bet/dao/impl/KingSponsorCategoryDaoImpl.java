package com.wintv.lottery.bet.dao.impl;

import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.pojo.KingSponsorCategory;
import com.wintv.lottery.bet.dao.KingSponsorCategoryDao;
@Repository("kingSpCatDao")
public class KingSponsorCategoryDaoImpl extends BaseHibernate<KingSponsorCategory,Long> implements  KingSponsorCategoryDao{

}
