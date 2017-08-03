package com.wintv.lottery.xinshui.dao.test;

import java.util.HashMap;
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

import com.wintv.framework.pojo.Expert;

import com.wintv.lottery.xinshui.dao.XinshuiLogDao;
import com.wintv.lottery.xinshui.vo.XinshuiOrderVO;

	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
	@TransactionConfiguration(transactionManager="transactionManager") 
    @Transactional

    public class B2CProductDaoTest {}

