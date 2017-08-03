package com.wintv.lottery.xinshui.service.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.utils.DateUtil;
import com.wintv.lottery.user.service.UserService;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.Top10Vo;
import com.wintv.lottery.xinshui.vo.XinshuiVo;

	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
	@TransactionConfiguration(transactionManager="transactionManager") 
    @Transactional

    public class XinshuiServiceTest {
	 
	    @Autowired
	    private XinshuiService xinShuiService;
	    @Autowired
	    private UserService userService;

	    @Test
	    //@Rollback(false) 
	    public void testCancelXinshuiOrder()throws Exception{
	    	Map params=new HashMap();
	    	params.put("flg","4");
	    	
	    	List<Top10Vo> list=this.xinShuiService.findXinshuiWinTop10List(params);
	    	for(Top10Vo po:list){
	    		 System.out.println(po.getUsername());
	    	}
	  
	  
	}
	}

