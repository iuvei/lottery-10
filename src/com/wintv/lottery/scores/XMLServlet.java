package com.wintv.lottery.scores;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;


/**
 * @author zym
 * @version v 1.0
 */
public class XMLServlet extends HttpServlet{

	
	private static final long serialVersionUID = 8206348373503454283L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException {
		String xmlType = request.getParameter("xml");
		if(StringUtils.isBlank(xmlType))
			return;
		if(!ResourceMap.getResource().containsKey(xmlType))
			return;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");  
		String data = XMLWriter.getImmediateData(xmlType);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException {
		doPost(request, response);
	}
}
