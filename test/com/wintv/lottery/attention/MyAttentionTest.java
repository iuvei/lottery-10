package com.wintv.lottery.attention;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.pojo.MyAttention;
import com.wintv.lottery.attention.service.MyAttentionService;
import com.wintv.lottery.bet.dao.BetDao;
import com.wintv.lottery.bet.dao.KingSponsorDao;
import com.wintv.lottery.bet.service.CanyuBuyService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager") 
@Transactional
public class MyAttentionTest {
	@Autowired
	private CanyuBuyService canyuBuyService;
	    
	@Test
	@Rollback(false)
	public void testMethod() {
		try {
			String a=this.canyuBuyService.isAttention(83L, 162L);
			log.info(a);
			boolean b=this.canyuBuyService.isGD(162L, 83L);
			log.info("b="+b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static final Log log=LogFactory.getLog(MyAttentionTest.class);
}
