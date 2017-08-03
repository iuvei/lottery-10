package com.wintv.lottery.bet;

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

import com.wintv.lottery.admin.bet.service.BetAdminService;
import com.wintv.lottery.admin.bet.vo.BetAdminOrderVO;
import com.wintv.lottery.bet.service.BetService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager") 
@Transactional
public class BetAdminServiceTest {
	@Autowired
	private BetService betAdminService;

	@Test
	@Rollback(false)
	public void testMM() {
		try {
		Map params=new HashMap();
		params.put("startRow", 1);
		params.put("pageSize", 3);
		params.put("betUserId", 83L);
		Map m=this.betAdminService.findMyBetOrderList(params);
		List<BetAdminOrderVO> resultList=(List)m.get("resultList");
		log.info("记录数="+resultList.size());
		for(BetAdminOrderVO po :resultList){
			log.info(po.getPhaseNo());
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static final Log log=LogFactory.getLog(BetAdminServiceTest.class);
}
