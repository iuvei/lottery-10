package com.wintv.lottery.phase.service;

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
import com.wintv.lottery.admin.phase.service.PhaseService;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;
import com.wintv.lottery.user.service.UserService;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.XinshuiVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager") 
@Transactional
public class PhaseTest {
	    @Autowired
	    private PhaseService phaseService;
	   
	    @Test
	    //@Rollback(false) 
	    public void testCancelXinshuiOrder()throws Exception{
	      Map params=new HashMap();
	      params.put("category","1");
	      params.put("status", "1");
	      int cnt=this.phaseService.findPhaseSize(params);
	      System.out.println("cnt="+cnt);
	      
	}
}
