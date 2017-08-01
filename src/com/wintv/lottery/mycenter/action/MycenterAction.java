package com.wintv.lottery.mycenter.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;

/**
 * mycenter
 * 
 * @author Arix04 by 2010-04-22
 * 
 * @version 1.0.0
 */

public class MycenterAction extends BaseAction {

	private static final long serialVersionUID = -8148262812871995731L;

	private String targetUrl = "/hall.html"; 
	
	public String excute() {
		return null;
	}
	
	public String mycenter() {
		try {
			targetUrl = targetUrl.replace('|', '&');
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
}
