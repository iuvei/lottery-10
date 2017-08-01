package com.wintv.lottery.mycenter.service;

import java.util.List;

import com.wintv.framework.exception.DaoException;
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

@SuppressWarnings("unchecked")
public interface MylotteryService {
	
	/***
	 * 查询用户个人信息
	 */
	public UserVO loadUserInfo(Long userid) throws DaoException;
	
	/****
	 * 查询个人所有未截止的投注记录
	 */
	public List<BetRecordVO> loadBetRecordList(Long userid) throws DaoException;
	
	/****
	 * 查询个人所有未截止的心水购买记录
	 */
	public List<XinshuiRecordVO> loadXinshuiRecordList(Long userid) throws DaoException;
	
	/****
	 * 加载个人动态
	 * @param userid
	 * @return
	 * @throws DaoException
	 */
	public UserCountVO loadUserCount(Long userid) throws DaoException;
	
	/***
	 * 查询最新中奖记录
	 */
	public List<LatestWinnerVO> loadLatestWinnerList() throws DaoException;
}
