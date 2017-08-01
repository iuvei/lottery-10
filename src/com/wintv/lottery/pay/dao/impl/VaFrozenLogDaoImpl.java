package com.wintv.lottery.pay.dao.impl;

import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.pojo.VaFrozenLog;
import com.wintv.lottery.pay.dao.VaFrozenLogDao;

@Repository("vaFrozenLogDao")
public class VaFrozenLogDaoImpl extends BaseHibernate<VaFrozenLog,Long> implements VaFrozenLogDao {

}
