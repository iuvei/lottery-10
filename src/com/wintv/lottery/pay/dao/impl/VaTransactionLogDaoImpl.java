package com.wintv.lottery.pay.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.VaTransactionLog;
import com.wintv.framework.utils.DateUtil;
import com.wintv.lottery.pay.dao.VaTransactionLogDao;
import com.wintv.lottery.pay.vo.MoneyDetailVo;
import com.wintv.lottery.pay.vo.MosaicGoldVo;

@Repository("vaTransactionLogDao")
public class VaTransactionLogDaoImpl extends BaseHibernate<VaTransactionLog,Long> implements VaTransactionLogDao {

/*
	@Override
	public List findMoneyDetailList(Map params, int startRow, int pageSize)
			throws DaoException {
		StringBuilder hql = new StringBuilder("from VaTransactionLog v where v.txUserId = :userid and v.txMoney != 0L");
		
		if(params.get("startDay") != null) {
			hql.append(" and v.txDate > :startDay");
		}
		if(params.get("endDay") != null) {
			hql.append(" and v.txDate < :endDay");
		}
		if(params.get("transactionType") != null) {
			hql.append(" and v.txType like :transactionType");
		}
		
		Query query = this.getSession().createQuery(hql.toString()).setFirstResult(startRow - 1).setMaxResults(pageSize);
		
		Long userid = (Long)params.get("userid");
		query.setParameter("userid", userid);
		
		if(params.get("startDay") != null) {
			query.setParameter("startDay", params.get("startDay"));
		}
		if(params.get("endDay") != null) {
			query.setParameter("endDay", params.get("endDay"));
		}
		if(params.get("transactionType") != null) {
			query.setParameter("transactionType", params.get("transactionType"));
		}
		
		return query.list();
		
	}

	@Override
	public List findMosaicGoldList(Map params, int startRow, int pageSize)
			throws DaoException {
		StringBuilder hql = new StringBuilder("from VaTransactionLog v where v.txUserId = :userid and v.txMosaicGold != 0L");
		
		if(params.get("startDay") != null) {
			hql.append(" and v.txDate > :startDay");
		}
		if(params.get("endDay") != null) {
			hql.append(" and v.txDate < :endDay");
		}
		if(params.get("transactionType") != null) {
			hql.append(" and v.txType like :transactionType");
		}
		
		Query query = this.getSession().createQuery(hql.toString()).setFirstResult(startRow - 1).setMaxResults(pageSize);
		Long userid = (Long)params.get("userid");
		query.setParameter("userid", userid);
		
		if(params.get("startDay") != null) {
			query.setParameter("startDay", params.get("startDay"));
		}
		if(params.get("endDay") != null) {
			query.setParameter("endDay", params.get("endDay"));
		}
		if(params.get("transactionType") != null) {
			query.setParameter("transactionType", params.get("transactionType"));
		}
		
		return query.list();
	}
*/

	@Override
	public List<MoneyDetailVo> findMoneyDetailList(Map params, int startRow, int pageSize)
			throws DaoException {
		StringBuilder sql = new StringBuilder("select tx_money,all_money,to_char(tx_date,'yyyy-mm-dd hh24:mi:ss'),tx_type,tx_name,order_id,order_no,category_type,bet_id,xinshui_id,expert_id from V_MONEY_DETAIL where ");
		Long userid = (Long)params.get("userid");
		
		sql.append("user_id = " + userid);
		
		if(params.get("startDay") != null) {
			sql.append(" and tx_date > to_date('" + DateUtil.formatDateTime((Date)params.get("startDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("endDay") != null) {
			sql.append(" and tx_date < to_date('" + DateUtil.formatDateTime((Date)params.get("endDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("transactionType") != null) {
			sql.append(" and tx_type like '%" + params.get("transactionType") + "%'");
		}
		
		sql.append(" and tx_date > trunc(sysdate)-90 ");
		sql.append(" order by tx_date desc");
		
		Query query = this.getSession().createSQLQuery(sql.toString()).setFirstResult(startRow - 1).setMaxResults(pageSize);

		List<MoneyDetailVo> list= new ArrayList<MoneyDetailVo>();
	    for(Iterator e=query.list().iterator();e.hasNext();){
	    	Object[] row=(Object[])e.next();
	    	MoneyDetailVo moneyDetailVo = new MoneyDetailVo();
	    	moneyDetailVo.setUserid(userid);
	    	moneyDetailVo.setTxMoney((BigDecimal)row[0]);
	    	moneyDetailVo.setAllMoney((BigDecimal)row[1]);
	    	moneyDetailVo.setTxDate((String)row[2]);
	    	moneyDetailVo.setTxType(row[3].toString());
	    	moneyDetailVo.setTxName((String)row[4]);
	    	moneyDetailVo.setOrderId(Long.parseLong(row[5].toString()));
	    	moneyDetailVo.setOrderNo((String)row[6]);
	    	moneyDetailVo.setCategoryType((String)row[7]);
	    	if(row[8] != null) {
	    		moneyDetailVo.setBetId(Long.parseLong(row[8].toString()));
	    	}
	    	if(row[9] != null) {
	    		moneyDetailVo.setXinshuiId(Long.parseLong(row[9].toString()));
	    	}
	    	if(row[10] != null) {
	    		moneyDetailVo.setExpertId(Long.parseLong(row[10].toString()));
	    	}
	    	list.add(moneyDetailVo);
	    }
		
		return list;
	}
	
	@Override
	public Map findMoneyIncomeSumCount(Map params) throws DaoException {
		StringBuilder sql = new StringBuilder("select count(*),nvl(sum(tx_money),0) from V_MONEY_DETAIL where tx_type in ('1','3','5','7') and ");
		Long userid = (Long)params.get("userid");
		
		sql.append("user_id = " + userid);
		
		if(params.get("startDay") != null) {
			sql.append(" and tx_date > to_date('" + DateUtil.formatDateTime((Date)params.get("startDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("endDay") != null) {
			sql.append(" and tx_date < to_date('" + DateUtil.formatDateTime((Date)params.get("endDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("transactionType") != null) {
			sql.append(" and tx_type like '%" + params.get("transactionType") + "%'");
		}
		
		sql.append(" and tx_date > trunc(sysdate)-90 ");
		
		Query query = this.getSession().createSQLQuery(sql.toString());

		Object[] row=(Object[])query.list().iterator().next();
    	Map m = new HashMap();

    	m.put("moneyIncomeCount", ((BigDecimal)row[0]).longValue());
    	m.put("moneyIncomeSum", (BigDecimal)row[1]);
    	
		return m;
	}
	
	@Override
	public Map findMoneyExpendSumCount(Map params) throws DaoException {
		StringBuilder sql = new StringBuilder("select count(*),nvl(sum(tx_money),0) from V_MONEY_DETAIL where tx_type in ('2','4','6','8') and ");
		Long userid = (Long)params.get("userid");
		
		sql.append("user_id = " + userid);
		
		if(params.get("startDay") != null) {
			sql.append(" and tx_date > to_date('" + DateUtil.formatDateTime((Date)params.get("startDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("endDay") != null) {
			sql.append(" and tx_date < to_date('" + DateUtil.formatDateTime((Date)params.get("endDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("transactionType") != null) {
			sql.append(" and tx_type like '%" + params.get("transactionType") + "%'");
		}
		
		sql.append(" and tx_date > trunc(sysdate)-90 ");
		
		Query query = this.getSession().createSQLQuery(sql.toString());

		Object[] row=(Object[])query.list().iterator().next();
    	Map m = new HashMap();
    	m.put("moneyExpendCount", ((BigDecimal)row[0]).longValue());
    	m.put("moneyExpendSum", (BigDecimal)row[1]);
    	
		return m;
	}
	
	@Override
	public Long findMoneyDetailListCount(Map params) throws DaoException {
		StringBuilder sql = new StringBuilder("select count(*) from V_MONEY_DETAIL where ");
		Long userid = (Long)params.get("userid");
		
		sql.append("user_id = " + userid);
		
		if(params.get("startDay") != null) {
			sql.append(" and tx_date > to_date('" + DateUtil.formatDateTime((Date)params.get("startDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("endDay") != null) {
			sql.append(" and tx_date < to_date('" + DateUtil.formatDateTime((Date)params.get("endDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("transactionType") != null) {
			sql.append(" and tx_type like '%" + params.get("transactionType") + "%'");
		}
		
		sql.append(" and tx_date > trunc(sysdate)-90 ");
		
		Query query = this.getSession().createSQLQuery(sql.toString());
		
		return ((BigDecimal)query.list().get(0)).longValue();
	}
	
	@Override
	public List<MosaicGoldVo> findMosaicGoldList(Map params, int startRow, int pageSize)
			throws DaoException {
		StringBuilder sql = new StringBuilder("select tx_mosaic_gold,mosaic_gold,to_char(tx_date,'yyyy-mm-dd hh24:mi:ss'),tx_type,tx_name,order_id,order_no,category_type,bet_id,xinshui_id,expert_id from V_MOSAIC_GOLD_DETAIL where ");
		Long userid = (Long)params.get("userid");
		
		sql.append("user_id = " + userid);
		
		if(params.get("startDay") != null) {
			sql.append(" and tx_date > to_date('" + DateUtil.formatDateTime((Date)params.get("startDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("endDay") != null) {
			sql.append(" and tx_date < to_date('" + DateUtil.formatDateTime((Date)params.get("endDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("transactionType") != null) {
			sql.append(" and tx_type like '%" + params.get("transactionType") + "%'");
		}
		
		sql.append(" and tx_date > trunc(sysdate)-90 ");
		sql.append(" order by tx_date desc");
		
		Query query = this.getSession().createSQLQuery(sql.toString()).setFirstResult(startRow - 1).setMaxResults(pageSize);

		List<MosaicGoldVo> list= new ArrayList<MosaicGoldVo>();
	    for(Iterator e=query.list().iterator();e.hasNext();){
	    	Object[] row=(Object[])e.next();
	    	MosaicGoldVo mosaicGoldVo = new MosaicGoldVo();
	    	mosaicGoldVo.setUserid(userid);
	    	mosaicGoldVo.setTxMosaicGold((BigDecimal)row[0]);
	    	mosaicGoldVo.setMosaicGoldAllMoney((BigDecimal)row[1]);
	    	mosaicGoldVo.setTxDate((String)row[2]);
	    	mosaicGoldVo.setTxType(row[3].toString());
	    	mosaicGoldVo.setTxName((String)row[4]);
	    	mosaicGoldVo.setOrderId(Long.parseLong(row[5].toString()));
	    	mosaicGoldVo.setOrderNo((String)row[6]);
	    	mosaicGoldVo.setCategoryType((String)row[7]);
	    	if(row[8] != null) {
	    		mosaicGoldVo.setBetId(Long.parseLong(row[8].toString()));
	    	}
	    	if(row[9] != null) {
	    		mosaicGoldVo.setXinshuiId(Long.parseLong(row[9].toString()));
	    	}
	    	if(row[10] != null) {
	    		mosaicGoldVo.setExpertId(Long.parseLong(row[10].toString()));
	    	}
	    	list.add(mosaicGoldVo);
	    }
		
		return list;
	}

	@Override
	public Map findMosaicGoldIncomeSumCount(Map params) throws DaoException {
		StringBuilder sql = new StringBuilder("select count(*),nvl(sum(tx_mosaic_gold),0) from v_mosaic_gold_detail where tx_type in ('9') and ");
		Long userid = (Long)params.get("userid");
		
		sql.append("user_id = " + userid);
		
		if(params.get("startDay") != null) {
			sql.append(" and tx_date > to_date('" + DateUtil.formatDateTime((Date)params.get("startDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("endDay") != null) {
			sql.append(" and tx_date < to_date('" + DateUtil.formatDateTime((Date)params.get("endDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("transactionType") != null) {
			sql.append(" and tx_type like '%" + params.get("transactionType") + "%'");
		}
		
		sql.append(" and tx_date > trunc(sysdate)-90 ");
		
		Query query = this.getSession().createSQLQuery(sql.toString());

		Object[] row=(Object[])query.list().iterator().next();
    	Map m = new HashMap();
    	m.put("mosaicGoldIncomeCount", ((BigDecimal)row[0]).longValue());
    	m.put("mosaicGoldIncomeSum", (BigDecimal)row[1]);
    	
		return m;
	}
	
	@Override
	public Map findMosaicGoldExpendSumCount(Map params) throws DaoException {
		StringBuilder sql = new StringBuilder("select count(*),nvl(sum(tx_mosaic_gold),0) from v_mosaic_gold_detail where tx_type in ('4','6') and ");
		Long userid = (Long)params.get("userid");
		
		sql.append("user_id = " + userid);
		
		if(params.get("startDay") != null) {
			sql.append(" and tx_date > to_date('" + DateUtil.formatDateTime((Date)params.get("startDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("endDay") != null) {
			sql.append(" and tx_date < to_date('" + DateUtil.formatDateTime((Date)params.get("endDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("transactionType") != null) {
			sql.append(" and tx_type like '%" + params.get("transactionType") + "%'");
		}
		
		sql.append(" and tx_date > trunc(sysdate)-90 ");
		
		Query query = this.getSession().createSQLQuery(sql.toString());

		Object[] row=(Object[])query.list().iterator().next();
    	Map m = new HashMap();
    	m.put("mosaicGoldExpendCount", ((BigDecimal)row[0]).longValue());
    	m.put("mosaicGoldExpendSum", (BigDecimal)row[1]);
    	
		return m;
	}

	@Override
	public Long findMosaicGoldListCount(Map params) throws DaoException {
		StringBuilder sql = new StringBuilder("select count(*) from V_MOSAIC_GOLD_DETAIL where ");
		Long userid = (Long)params.get("userid");
		
		sql.append("user_id = " + userid);
		
		if(params.get("startDay") != null) {
			sql.append(" and tx_date > to_date('" + DateUtil.formatDateTime((Date)params.get("startDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("endDay") != null) {
			sql.append(" and tx_date < to_date('" + DateUtil.formatDateTime((Date)params.get("endDay")) + "','yyyy-mm-dd hh24:mi:ss')");
		}
		if(params.get("transactionType") != null) {
			sql.append(" and tx_type like '%" + params.get("transactionType") + "%'");
		}
		
		sql.append(" and tx_date > trunc(sysdate)-90 ");
		
		Query query = this.getSession().createSQLQuery(sql.toString());
		
		return ((BigDecimal)query.list().get(0)).longValue();
	}

}
