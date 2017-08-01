package com.wintv.lottery.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.SecurityIp;
import com.wintv.lottery.user.dao.SecurityIpDao;
import com.wintv.lottery.user.service.SecurityIpService;

@Service("securityIpService")
public class SecurityIpServiceImpl implements SecurityIpService {
	
	@Autowired
	private SecurityIpDao securityIpDao;
	
	@Override
	public Long getTimes(String ip) throws DaoException {
		return securityIpDao.findRegisterTimes(ip);
	}

	@Transactional(rollbackFor = DaoException.class)
	public void setTimes(String ip) throws DaoException {
		List<SecurityIp> list = securityIpDao.findBy("ip", ip);
		
		if(list == null || 0 == list.size()) {
			SecurityIp securityIp = new SecurityIp();
			securityIp.setIp(ip);
			securityIp.setCnt(1L);
			securityIpDao.store(securityIp);
		} else {
			SecurityIp securityIp = (SecurityIp)list.get(0);
			securityIp.setCnt(securityIp.getCnt() + 1L);
			
			securityIpDao.store(securityIp);
		}
	}

	@Override
	public void cleanIpTable() throws DaoException {
		securityIpDao.cleanIpTable();
	}

}
