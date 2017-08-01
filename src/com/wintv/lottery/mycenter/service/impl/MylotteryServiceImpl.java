package com.wintv.lottery.mycenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.mycenter.dao.MylotteryDao;
import com.wintv.lottery.mycenter.service.MylotteryService;
import com.wintv.lottery.mycenter.vo.BetRecordVO;
import com.wintv.lottery.mycenter.vo.LatestWinnerVO;
import com.wintv.lottery.mycenter.vo.UserCountVO;
import com.wintv.lottery.mycenter.vo.UserVO;
import com.wintv.lottery.mycenter.vo.XinshuiRecordVO;

/**
 * 我的天彩-service
 * 
 * @author Arix04 by 2010-04-22
 * 
 * @version 1.0.0
 */

@Service("mylotteryService")
public class MylotteryServiceImpl implements MylotteryService {

	@Autowired
	private MylotteryDao mylotteryDao;
	
	@Override
	public UserVO loadUserInfo(Long userid) throws DaoException {
		return mylotteryDao.loadUserInfo(userid);
	}

	@Override
	public List<BetRecordVO> loadBetRecordList(Long userid) throws DaoException {
		return mylotteryDao.loadBetRecordList(userid);
	}

	@Override
	public List<XinshuiRecordVO> loadXinshuiRecordList(Long userid) throws DaoException {
		return mylotteryDao.loadXinshuiRecordList(userid);
	}

	@Override
	public UserCountVO loadUserCount(Long userid) throws DaoException {
		return mylotteryDao.loadUserCount(userid);
	}

	@Override
	public List<LatestWinnerVO> loadLatestWinnerList() throws DaoException {
		return mylotteryDao.loadLatestWinnerList();
	}
}
