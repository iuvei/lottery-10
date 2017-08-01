package com.wintv.lottery.pay.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.VaTransactionLog;
import com.wintv.lottery.pay.vo.MoneyDetailVo;
import com.wintv.lottery.pay.vo.MosaicGoldVo;

public interface VaTransactionLogDao extends BaseDao<VaTransactionLog,Long> {

	public List<MoneyDetailVo> findMoneyDetailList(Map params, int startRow, int pageSize) throws DaoException;
	
	public Map findMoneyIncomeSumCount(Map params) throws DaoException;
	
	public Map findMoneyExpendSumCount(Map params) throws DaoException;
	
	public Long findMoneyDetailListCount(Map params) throws DaoException;
	
	public List<MosaicGoldVo> findMosaicGoldList(Map params, int startRow, int pageSize) throws DaoException;

	public Map findMosaicGoldIncomeSumCount(Map params) throws DaoException;
	
	public Map findMosaicGoldExpendSumCount(Map params) throws DaoException;
	
	public Long findMosaicGoldListCount(Map params) throws DaoException;
}
