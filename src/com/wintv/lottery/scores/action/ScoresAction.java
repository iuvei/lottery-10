package com.wintv.lottery.scores.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;

import com.wintv.framework.common.BaseAction;
import com.wintv.lottery.scores.ResourceMap;
import com.wintv.lottery.scores.XMLConstants;
import com.wintv.lottery.scores.XMLWriter;

public class ScoresAction  extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1431057227611808562L;
	
	private String scores_date;
	
	public String getScores_date() {
		return scores_date;
	}

	public void setScores_date(String scores_date) {
		this.scores_date = scores_date;
	}

	public String list(){
		scores_date = ResourceMap.getResource().get(XMLConstants.bifen);
		if(StringUtils.isBlank(scores_date)){
			//从数据库里读取数据
			scores_date = "read from database......";
		}
		return SUCCESS;
	}
	
	public void getScores(){
		String xmlType = request.getParameter("xml");
		if(StringUtils.isBlank(xmlType))
			return;
		if(!ResourceMap.getResource().containsKey(xmlType))
			return;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");  
		String data = XMLWriter.getImmediateData(xmlType);
		try {
			PrintWriter out = response.getWriter();
			out.print(data);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
