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

import com.wintv.lottery.admin.phase.dao.impl.PhaseDaoImpl;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.vo.BetOrderVO;
import com.wintv.lottery.bet.vo.CoopOrder;
import com.wintv.lottery.bet.vo.PhaseNoVO;
import com.wintv.lottery.pay.service.PayService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager") 
@Transactional
public class BetTest {

	@Autowired
	private BetService betService;
	
	
	@Test
	@Rollback(false)
	public void testMethod() {
		try {
			Map params = new HashMap();
			params.put("phaseId",640L);
			params.put("startRow", 1);
			params.put("pageSize", 10);
			Map map=betService.findCoopList(params);
			List<CoopOrder> resultList=(List<CoopOrder>)map.get("resultList");
			System.out.println(resultList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static final Log log=LogFactory.getLog(BetTest.class);

}
