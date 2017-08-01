package com.wintv.lottery.xinshui.dao.impl;

import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.pojo.Expert;
import com.wintv.lottery.xinshui.dao.B2CExpertDao;

@Repository("b2CExpertDao")
@SuppressWarnings("unchecked")
public class B2CExpertDaoImpl extends BaseHibernate<Expert,Long> implements B2CExpertDao {


}

