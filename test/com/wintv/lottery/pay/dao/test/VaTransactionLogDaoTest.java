package com.wintv.lottery.pay.dao.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.lottery.pay.dao.VaTransactionLogDao;
import com.wintv.lottery.pay.vo.MoneyDetailVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager") 
@Transactional

public class VaTransactionLogDaoTest {

	@Autowired
	private VaTransactionLogDao vaTransactionLogDao;
	 
	@Test
	@Rollback(false)
	public void testFindMoneyDetailList() throws Exception{
	    	Map params = new HashMap();
	    	params.put("userid", 83L);
	    	params.put("transactionType", 1);
//	    	params.put("startDay", "2000-01-01 11:11:11");
//	    	params.put("endDay", DateUtil.getCurrentDate());
	    	
//	    	System.out.println(vaTransactionLogDao.findMoneyDetailList(params, 0, 20));
	    	System.out.println((vaTransactionLogDao.findMoneyDetailList(params, 1, 20).get(0)).getTxType());
	    	System.out.println((vaTransactionLogDao.findMosaicGoldList(params, 1, 20).get(0)).getTxType());
	 }
	
	@Test
	@Rollback(false)
	public void testOnlinePay() throws Exception{
	    	Map params = new HashMap();
	    	params.put("userid", 83L);
	    	params.put("transactionType", 1);
//	    	params.put("startDay", "2000-01-01 11:11:11");
//	    	params.put("endDay", DateUtil.getCurrentDate());
	    	
//	    	System.out.println(vaTransactionLogDao.findMoneyDetailList(params, 0, 20));
	    	System.out.println((vaTransactionLogDao.findMoneyDetailList(params, 1, 20).get(0)).getTxType());
	    	System.out.println((vaTransactionLogDao.findMosaicGoldList(params, 1, 20).get(0)).getTxType());
	 }
}
