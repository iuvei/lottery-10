/*
 * $Id: BaseAction.java,v 1.7 2010/03/05 06:46:54 arix04 Exp $
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in cnpliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wintv.framework.common;

import com.wintv.framework.domain.base.Result;
import com.wintv.framework.exception.WintvBizException;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author Steady Wang
 */
@SuppressWarnings("unchecked")
public class BaseAction extends ActionSupport implements SessionAware,
		ApplicationAware, ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = -7034821110544781529L;
	private Log log = LogFactory.getLog(BaseAction.class);
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map application;
	protected Map session;
	protected Result result;
	protected static final String MSG_SUCCESS = "success";
	protected static final String MSG_FAILURE = "failure";

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setApplication(Map value) {
		application = value;
	}

	public void setSession(Map value) {
		session = value;
	}

	protected void generateResult(int code, String message, Object response) {
		result = new Result();
		result.setCode(code);
		result.setMessage(message);
		result.setData(response);
	}

	protected void handleException(Exception e) {
		if (e instanceof WintvBizException) {
			log.error(e.getMessage());
			generateResult(1, e.getMessage(), null);
		} else {
			log.error(e.getMessage(), e);
			generateResult(1, MSG_FAILURE, null);
		}
	}

	/**
	 * 判断各个参数对象是否都不为null
	 * 
	 * @author hikin yao
	 * @param args
	 *            各个待判断参数
	 * @return 如果都不为null 则返回true 反之 回false
	 */
	public boolean isNotNull(Object... args) {
		boolean result = true;
		for (Object arg : args) {
			result = result && (null != arg);
		}
		return result;
	}
	
	protected void ajaxForAction(String args){
		PrintWriter pw = null;
		response.setContentType("text/html;charset=UTF-8");  
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.print(args);
		pw.flush(); 
		pw.close(); 
	}
}
