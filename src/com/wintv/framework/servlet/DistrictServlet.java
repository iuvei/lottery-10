package com.wintv.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wintv.framework.dao.DistrictDao;
import com.wintv.framework.pojo.District;

@SuppressWarnings("serial")
public class DistrictServlet  extends HttpServlet{

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		ApplicationContext act = this.getApplicationContext(request);
		DistrictDao districtDao =(DistrictDao) act.getBean("districtDao");
		String pId = request.getParameter("pId");
		
		List<District> dList = districtDao.findBy("parentId", Long.parseLong(pId));
		JSONArray js = new JSONArray();
		JSONObject obj = null;
		for(District c : dList){
			obj = new JSONObject();
			obj.put("id", c.getId());
			obj.put("name", c.getName());
			js.add(obj);
		}
		response.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = response.getWriter();  
		out.print(js.toString()); 
		out.flush(); 
		out.close(); 

	}
	
	public void init() throws ServletException {
		// Put your code here
	}
	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	public ApplicationContext getApplicationContext(HttpServletRequest request){
		return WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request, response);
	}
}
