package com.wintv.lottery.mycenter.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.mycenter.service.MylotteryService;
import com.wintv.lottery.mycenter.vo.LatestWinnerVO;
import com.wintv.lottery.mycenter.vo.UserCountVO;
import com.wintv.lottery.mycenter.vo.UserVO;


/**
 * 我的天彩
 * 
 * @author Arix04 by 2010-04-22
 * 
 * @version 1.0.0
 */

public class MylotteryAction extends BaseAction {

	private static final long serialVersionUID = -8148262812871995731L;
	
	@Autowired
	private MylotteryService mylotteryService;
	
	private UserVO user;
	private UserCountVO userCount;
	private List<LatestWinnerVO> latestWinnerList;
	
	public String excute() {
		return null;
	}
	
	public String mylottery() {
		try {
			UserCookie userCookie = (UserCookie)request.getSession().getAttribute("userCookie");
			user = mylotteryService.loadUserInfo(userCookie.getUserId());
			userCount = mylotteryService.loadUserCount(userCookie.getUserId());
			latestWinnerList = mylotteryService.loadLatestWinnerList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 加载未截止的投注记录
	 * @return
	 */
	public String loadBetRecordList() {
		try {
			UserCookie userCookie = (UserCookie)request.getSession().getAttribute("userCookie");
			generateResult(1, MSG_SUCCESS, mylotteryService.loadBetRecordList(userCookie.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 加载未截止的心水记录
	 * @return
	 */
	public String loadXinshuiRecordList() {
		try {
			UserCookie userCookie = (UserCookie)request.getSession().getAttribute("userCookie");
			generateResult(1, MSG_SUCCESS, mylotteryService.loadXinshuiRecordList(userCookie.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public UserCountVO getUserCount() {
		return userCount;
	}

	public void setUserCount(UserCountVO userCount) {
		this.userCount = userCount;
	}

	public List<LatestWinnerVO> getLatestWinnerList() {
		return latestWinnerList;
	}

	public void setLatestWinnerList(List<LatestWinnerVO> latestWinnerList) {
		this.latestWinnerList = latestWinnerList;
	}
}
