package com.wintv.lottery.pay.service;

import java.math.BigDecimal;
import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.common.Constants;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.User;
import com.wintv.framework.utils.DateUtil;
import com.wintv.lottery.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager") 
@Transactional

public class PayServiceTest {
 
	@Autowired
	private PayService payService;
	@Test
	@Rollback(false)
	public void testFindCsHandleLogListAdmin() {
		try {
			Long c2cId=62L;
			Long buyUserId=162L;
			String useCaijin="1";
			//this.payService.myXinshuiPay(c2cId, buyUserId, useCaijin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    
}

