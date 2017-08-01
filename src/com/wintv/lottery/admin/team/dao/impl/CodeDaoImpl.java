package com.wintv.lottery.admin.team.dao.impl;

import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Code;
import com.wintv.framework.pojo.Race;
import com.wintv.framework.pojo.Against;

import com.wintv.lottery.admin.team.dao.CodeDao;
import com.wintv.lottery.user.dao.UserDao;
@Repository("codeDao")
public class CodeDaoImpl extends BaseHibernate<Code,Long> implements CodeDao{

	
	
}
