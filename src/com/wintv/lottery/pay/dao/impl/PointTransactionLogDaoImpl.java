package com.wintv.lottery.pay.dao.impl;

import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.pojo.PointTransactionLog;
import com.wintv.lottery.pay.dao.PointTransactionLogDao;

@Repository("pointTransactionLogDao")
public class PointTransactionLogDaoImpl extends BaseHibernate<PointTransactionLog,Long> implements PointTransactionLogDao {

}
