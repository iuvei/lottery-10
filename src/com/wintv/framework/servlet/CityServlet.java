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

import com.wintv.framework.dao.AreaDao;
import com.wintv.framework.pojo.Code;

public class CityServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4477869671139982053L;
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		ApplicationContext act = this.getApplicationContext(request);
		AreaDao areaDao =(AreaDao) act.getBean("areaDao");
		String provId = request.getParameter("provId");
		List<Code> cList = areaDao.findCityByProvID(Long.parseLong(provId));
		JSONArray js = new JSONArray();
		JSONObject obj = null;
		for(Code c : cList){
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
