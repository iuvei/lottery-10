package com.wintv.lottery.admin.phase.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.common.Constants;
import com.wintv.framework.pojo.Race;
import com.wintv.lottery.admin.phase.service.PhaseService;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
@SuppressWarnings("serial")
public class IndexAction extends BaseAction {
	private static final Log log=LogFactory.getLog(IndexAction.class);
	public String main() {
		
		String category=request.getParameter("category");
		//log.info("category="+category);
		if(Constants.ORDER_CATEGORY_FBSINGLE.equals(category)){//北京单场 1
			return "1";
		}
		if(Constants.ORDER_CATEGORY_SIXHALFCOMPLETE.equals(category)){//半全场 8
			return "8";
		}
		if(Constants.ORDER_CATEGORY_FOURSCENEGOAL.equals(category)){//进球彩 9
			return "9";
		}
		//默认是胜负彩 6
		return "6";
	}
	/**
	 * 
	 * 根据条件查询对阵列表
	 */
	public String queryAgainst() {
		log.info("王金阶---");
		List areaList=this.phaseService.findDistrict(null);
		request.setAttribute("areaList",areaList);
		Map params=new HashMap();
		int startRow=0;
		params.put("startRow", startRow);
		int pageSize=10;
		params.put("pageSize", pageSize);
		List  againstList=this.phaseService.findAgainst(params);
		int totalCount=this.phaseService.findAgainsSize(params);
		Map result = new HashMap();
		result.put("totalCount", totalCount);
		result.put("againstList", againstList);
		generateResult(1, MSG_SUCCESS, result);
		return SUCCESS;
	}
	public void geJsonData()throws Exception{
		 String id=this.request.getParameter("id");
		 String flg=request.getParameter("flg");
		 log.info("正在获取获取国家....flg="+flg+" "+flg.length());
		 List list=null;
		 Map params=new HashMap();
		 String callback = request.getParameter("callback");  
         response.setContentType("text/xml;charset=utf-8");
         response.setHeader("Pragma","No-cache");
         response.setDateHeader("Expires",0);
         response.setHeader("Cache-Control","no-cache");    
		if("1".equals(flg)){//获取国家
			log.info("正在获取获取国家....");
			params.put("flg", "1");
		    list=phaseService.findDistrict(new Long(id));
		    JSONArray jsonArray = JSONArray.fromObject(list);
	        JSONObject jsobjcet = new JSONObject();
	        jsobjcet.put("data", jsonArray);
	        response.getWriter().write(callback+"("+jsobjcet.toString()+");");
		}else if("2".equals(flg)){//根据国家ID 获取联赛
			params=new HashMap();
			params.put("flg", "2");
			params.put("countId", new Long(id));
		  }else if("3".equals(flg)){//根据联赛ID 获取赛季资料
			params=new HashMap();
			params.put("raceId", new Long(id));
			params.put("flg", "3");
		  }else if("4".equals(flg)){
			params=new HashMap();
			params.put("raceSeasonId", new Long(id));
			params.put("flg", "4");
		}else if("5".equals(flg)){
			if(StringUtils.isNotEmpty(id)&&(!"undefined".equals(id))){
				String category=request.getParameter("category");
				AgainstVo po=this.phaseService.loadAgainstById(category,new Long(id));
			    JSONObject poJSON = new JSONObject();
			    poJSON.put("po", po);
	            response.getWriter().write(callback+"("+poJSON.toString()+");");
	       }
		}
		if(!"5".equals(flg)&&!"1".equals(flg)){
		  list=phaseService.findRaceList(params);
		  for(int i=0;i<list.size();i++){
			  Race po=(Race)list.get(i);
			  log.info(po.getName());
		  }
		  JSONArray jsonArray = JSONArray.fromObject(list);
          JSONObject jsobjcet = new JSONObject();
          jsobjcet.put("data", jsonArray);
          response.getWriter().write(callback+"("+jsobjcet.toString()+");");
        
		}
       }
	public String findAgainst(){
		String areaId=request.getParameter("areaId");
	    String countryId=request.getParameter("countryId");
		String raceId=request.getParameter("raceId");
		String raceSeasonId=request.getParameter("raceSeasonId");
		String roundsId=request.getParameter("roundsId");
		Map params=new HashMap();
		if(StringUtils.isNotEmpty(areaId)){
			params.put("areaId", areaId);
		}
		if(StringUtils.isNotEmpty(countryId)){
			params.put("countryId", new Long(countryId));
		}
		if(StringUtils.isNotEmpty(raceId)){
			params.put("raceId", new Long(raceId));
		}
		if(StringUtils.isNotEmpty(raceSeasonId)){
			params.put("raceSeasonId", new Long(raceSeasonId));
		}
		if(StringUtils.isNotEmpty(roundsId)){
			params.put("roundsId", new Long(roundsId));
		}
		params.put("startRow", this.getStartRow());
		params.put("pageSize", this.getPageSize());
		List againstList=this.phaseService.findAgainst(params);
		int totalCount=this.phaseService.findAgainsSize(params);
		Map result = new HashMap();
		result.put("totalCount", totalCount);
		result.put("againstList", againstList);
		generateResult(1, MSG_SUCCESS, result);
	  return SUCCESS;
	}
	
	@Autowired
    private PhaseService phaseService;
	private Integer pageSize=10;// 页面显示数据条数
	private Integer startRow=1;// 查询开始行
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	
}
