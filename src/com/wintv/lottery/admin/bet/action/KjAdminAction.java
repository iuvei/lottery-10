package com.wintv.lottery.admin.bet.action;

 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import com.wintv.lottery.admin.bet.service.KjAdminService;
import com.wintv.lottery.admin.bet.vo.BonusGov;
/**
 * 开奖管理列表（胜负彩类） 2010-05-05 15:28
 * @author Administrator
 *
 */
public class KjAdminAction extends BaseBetAction {
	
	public String listbonusgov()throws Exception{
		return SUCCESS;
	}

	public String kjList()throws Exception{
		 
		Map params=new HashMap();
		params.put("startRow", this.getStartRow());
		params.put("pageSize", this.getPageSize());
		String from=request.getParameter("from");
		if(StringUtils.isNotEmpty(from)){
			params.put("from", from);
		}
		String to=request.getParameter("to");
		if(StringUtils.isNotEmpty(to)){
			params.put("to", to);
		}
		String betCategory=request.getParameter("betCategory");
		if(StringUtils.isNotEmpty(betCategory)){
			params.put("betCategory", betCategory);
		}
		String phaseNo=request.getParameter("phaseNo");
	
		if(StringUtils.isNotEmpty(phaseNo)){
			params.put("phaseNo", phaseNo);
			 
		}
		String status=request.getParameter("status");
		if(StringUtils.isNotEmpty(status)){
			params.put("status", status);
		}
		
		List<BonusGov> resultList=kjAdminService.findBonusGovList(params);
		long totalCount=kjAdminService.findBonusGovSize(params);
		Map  resultMap=new HashMap();
		resultMap.put("totalCount", totalCount);
		resultMap.put("resultList", resultList);
		generateResult(1, MSG_SUCCESS, resultMap);
	  return SUCCESS;
	}
	public void findKJPhaseList()throws Exception{
		String betCategory=this.request.getParameter("betCategory");
		List<String> list=this.kjAdminService.findPhaseList(betCategory);
		 String callback = request.getParameter("callback");  
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/xml;charset=GBK");
	    response.setHeader("Pragma","No-cache");
	    response.setDateHeader("Expires",0);
	    response.setHeader("Cache-Control","no-cache"); 
	    
	    JSONArray jsonArray = JSONArray.fromObject(list);
        JSONObject jsobjcet = new JSONObject();
        jsobjcet.put("data", jsonArray);
        response.getWriter().write(callback+"("+jsobjcet.toString()+");");
	}
	@Resource
	private KjAdminService  kjAdminService;
}
