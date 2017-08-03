package com.wintv.lottery.space;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.wintv.lottery.attention.vo.AttentionPlanVO;
import com.wintv.lottery.personal.dao.SpaceDao;
import com.wintv.lottery.personal.service.SpaceService;
import com.wintv.lottery.personal.vo.SpaceVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager") 
@Transactional
public class SpaceServiceTest {
	@Autowired
	private SpaceService spaceService;
	    
	@Test
	@Rollback(false)
	public void testMethod(){
		try {
			Map params=new HashMap();
			params.put("userId", 162L);
			params.put("startRow", 1);
			params.put("pageSize", 10);
			this.spaceService.findCurrentBet(162L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static final Log log=LogFactory.getLog(SpaceServiceTest.class);
}
