package com.wintv.lottery.user.service.test;

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

import com.wintv.framework.pojo.User;
import com.wintv.lottery.user.service.UserService;

	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
	@TransactionConfiguration(transactionManager="transactionManager") 
    @Transactional

    public class UserServiceTest {
	 
	    @Autowired
	    private UserService userService;
	    
	    @Test
	    @Rollback(false) 
	    public void testRegister()throws Exception{
	    	User user=new User();
	    	user.setUsername("arix079");
	    	user.setEmail("yht7@wintv.cn");
	    	user.setLoginPassword("123456");
	    	user.setMp("123");
	    	user.setUserGrade("1");
	    	user.setName("杨华涛");
	    	this.userService.register(user);
	    }
	    
	    @Test
	    @Rollback(false) 
	    public void testLogin()throws Exception{
	    	this.userService.authLogin("arix04", "123456");
	    }
	    
	    @Test
	    @Rollback(false) 
	    public void testIsExistUser()throws Exception{
	    	this.userService.isExistUser("arix04");
	    }
	    
	    @Test
	    @Rollback(false) 
	    public void testLockUser()throws Exception{
	    	Map params = new HashMap();
	    	params.put("userid", 104L);
	    	params.put("stauts", "1");
	    	this.userService.lockUser(params);
	    }
	}

