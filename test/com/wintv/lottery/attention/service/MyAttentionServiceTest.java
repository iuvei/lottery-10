package com.wintv.lottery.attention.service;

import static org.junit.Assert.*;

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

import com.wintv.lottery.attention.vo.AttentionPlanVO;
import com.wintv.lottery.attention.vo.AttentionUserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager") 
@Transactional
public class MyAttentionServiceTest {
	@Autowired
	private MyAttentionService myAttentionService;
	/*
	@Test
	@Rollback(false)
	public void testFindMyAttentionTop50Size() {
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("userId",162L);
			long result = myAttentionService.findMyAttentionTop50Size(queryParams);
			System.out.println("--"+result+"--");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Rollback(false)
	public void testFindMyAttentionTop50List() {
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("userId",162L);
			queryParams.put("startRow",0);
			queryParams.put("pageSize", 10);
			List<AttentionUserVO>  result = myAttentionService.findMyAttentionTop50List(queryParams);
			System.out.println("--"+result.size()+"--");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Rollback(false)
	public void testFindMyAttentionUserSize() {
		fail("Not yet implemented");
	}

	@Test
	@Rollback(false)
	public void testFindMyAttentionUserList() {
		fail("Not yet implemented");
	}*/
	/**
	 * 获取我关注的方案列表
	 */
	@Test
	@Rollback(false)
	public void testFindMyAttentionPlanSize() {
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("userId",162L);
			long result = myAttentionService.findMyAttentionPlanSize(queryParams);
			System.out.println("--"+result+"--");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Rollback(false)
	public void testFindMyAttentionPlanList() {
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("userId",162L);
			queryParams.put("startRow",0);
			queryParams.put("pageSize", 10);
			List<AttentionPlanVO>  result = myAttentionService.findMyAttentionPlanList(queryParams);
			System.out.println("--"+result.size()+"--");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	@Test
	@Rollback(false)
	public void testUpdateUserAttentionCnt() {
		fail("Not yet implemented");
	}

	@Test
	@Rollback(false)
	public void testFindMyAttentionedUserSize() {
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("userId",162L);
			long result = myAttentionService.findMyAttentionedUserSize(queryParams);
			System.out.println("--"+result+"--");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Rollback(false)
	public void testFindMyAttentionedUserList() {
		try {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("userId",162L);
			queryParams.put("startRow",0);
			queryParams.put("pageSize", 10);
			List<AttentionUserVO>  result = myAttentionService.findMyAttentionedUserList(queryParams);
			System.out.println("--"+result.size()+"--");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Rollback(false)
	public void testCancelAttention() {
		fail("Not yet implemented");
	}

	@Test
	@Rollback(false)
	public void testFindCurPlanList() {
		fail("Not yet implemented");
	}
	*/
}
