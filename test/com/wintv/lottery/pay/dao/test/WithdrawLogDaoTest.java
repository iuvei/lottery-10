package com.wintv.lottery.pay.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.lottery.pay.dao.WithdrawLogDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager") 
@Transactional

public class WithdrawLogDaoTest {
	@Autowired
	private WithdrawLogDao withdrawLogDao;
	 
	@Test
	@Rollback(false)
	public void testGetTimes() throws Exception {
		System.out.println(withdrawLogDao.findWithdrawTimes(83L));
	}
}
