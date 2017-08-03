package com.wintv.lottery.user.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.pojo.User;
import com.wintv.lottery.user.service.SecurityIpService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager") 
@Transactional

public class SecurityIpServiceTest {

	@Autowired
	private SecurityIpService securityIpService;

//	@Test
//    @Rollback(false)
//    public void testSecurityIp()throws Exception{
//
//    	Long cnt = this.securityIpService.getTimes("192.168.1.1");
//    	this.securityIpService.setTimes("192.168.1.1");
//    	System.out.println(cnt);
//	}
	
	@Test
    @Rollback(false)
    public void testDelete()throws Exception{
		this.securityIpService.cleanIpTable();
	}
}
