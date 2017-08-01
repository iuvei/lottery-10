package com.wintv.lottery.admin.bet.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wintv.framework.pojo.KingSponsor;
import com.wintv.lottery.admin.bet.utils.BetTools;
import com.wintv.lottery.admin.bet.vo.BetAdminDetailVO;
import com.wintv.lottery.admin.bet.vo.PhaseVO;


@SuppressWarnings("serial")
public class BetAdminAction extends BaseBetAction {
	public String listOrderPage()throws Exception{
		return SUCCESS;
	}
	public void getPhaseList()throws Exception{
	    String betCategory=request.getParameter("betCategory");
	    if(StringUtils.isNotEmpty(betCategory)){
				List<PhaseVO> phaseList=this.getBetAdminService().findPhaseList(new Long(betCategory));
				 JSONArray jsonArray = JSONArray.fromObject(phaseList);
			     JSONObject jsobjcet = new JSONObject();
			     jsobjcet.put("data", jsonArray);
			     String callback = request.getParameter("callback");  
		         response.setContentType("text/xml;charset=utf-8");
		         response.setHeader("Pragma","No-cache");
		         response.setDateHeader("Expires",0);
		         response.setHeader("Cache-Control","no-cache");    
			     response.getWriter().write(callback+"("+jsobjcet.toString()+");");
			}
		
	}
	/**
	 * 3.2.5投注系统后台：
     *投注订单管理列表
     * @return
	 * @throws Exception
	 */
	public String listBetOrders()throws Exception{
		
		Map params=new HashMap();
		params.put("startRow", this.getStartRow());
		params.put("pageSize", this.getPageSize());
		String isSubscribe=this.request.getParameter("isSubscribe");
		Map  result=null;
		if("1".equals(isSubscribe)){//认购信息（收起认购信息）
			String betId=this.request.getParameter("betId");
			params.put("betId", new Long(betId));
			result=getBetAdminService().findSubscribeList(params);
		}else{
		  String flgA=request.getParameter("flgA");
		  String fromTime=request.getParameter("fromTime");
		  String toTime=request.getParameter("toTime");
		  if("1".equals(flgA)){
			  if(StringUtils.isNotEmpty(fromTime)){
			    params.put("betFromTime", fromTime);
			  }
			  if(StringUtils.isNotEmpty(toTime)){
				    params.put("toToTime", toTime);
				  }
		  }
		  String betCategory=request.getParameter("betCategory");
		  if(StringUtils.isNotEmpty(betCategory)){
			  params.put("betCategory", betCategory);
			  String phaseId=request.getParameter("phaseId");
			  if(StringUtils.isNotEmpty(phaseId)){
				  params.put("phaseId", phaseId);
			  }
		  }
		 
		  String flgB=request.getParameter("flgB");
		  String zjStatus=request.getParameter("zjStatus");
		  if(StringUtils.isNotEmpty(zjStatus)){
			  
		  }
		  String orderStatus=request.getParameter("orderStatus");
		  String key=request.getParameter("key");
		  String buyWay=request.getParameter("buyWay");
		  String betWay=request.getParameter("betWay");
		  
		  result=getBetAdminService().findList(params);
		}
		generateResult(1, MSG_SUCCESS, result);
	  return SUCCESS;
	}
	/**
	 * 投注订单详情  55页 2010-03-09 15:30
	 * @return
	 * @throws Exception
	 */
	public String detailOrder()throws Exception{
		Long betId=new Long(this.request.getParameter("betId"));
		BetAdminDetailVO po=getBetAdminService().loadBetAdminDetailVO(betId);
		this.request.setAttribute("po",po);
		return "detail.url";
	}
	//---------------------------------3.2.6合买系统后台----------------------------------------------
	/**默认页面**/
	public String listCoopBuyOrdersMethod()throws Exception{
		return "coop.buy.order";
	}
	/***默认页面JS加载的数据*/
	public String listCoopBuyOrders()throws Exception{
		Map params=new HashMap();
		params.put("startRow", this.getStartRow());
		params.put("pageSize", this.getPageSize());
		String isSubscribe=this.request.getParameter("isSubscribe");
		Map  result=null;
		if("1".equals(isSubscribe)){//认购信息（收起认购信息）
			
			String betId=this.request.getParameter("betId");
			params.put("betId", new Long(betId));
			result=getBetAdminService().findCoopBuySubscribeList(params);
		}else{
		  String timeFlg=this.request.getParameter("timeFlg");
		  String timeFrom=this.request.getParameter("timeFrom");
		  String timeTo=this.request.getParameter("timeTo");
		  if("1".equals(timeFlg)){
			  if(StringUtils.isNotEmpty(timeFrom)){
			     params.put("betTimeFrom", timeFrom);
			  }
			  if(StringUtils.isNotEmpty(timeTo)){
				     params.put("betTimeTo", timeTo);
				  }
		  }
		  String betCategory=this.request.getParameter("betCategory");
		  if(StringUtils.isNotEmpty(betCategory)){
			  params.put("betCategory", betCategory);
		  }
		  String phaseNo=this.request.getParameter("phaseID");
		  if(StringUtils.isNotEmpty(phaseNo)){
			  log.info("phaseNo="+phaseNo);
			  params.put("phaseNo", phaseNo);
		  }
		  String keyFlg=this.request.getParameter("keyFlg");
		  String key=this.request.getParameter("key");
		  log.info("keyFlg="+keyFlg+" key="+key);
		  if("1".equals(keyFlg)){
			  if(StringUtils.isNotEmpty(key)){
				  params.put("key", key);
				  params.put("keyFlg", "1");
			  }
		  }else if("2".equals(keyFlg)){
			  if(StringUtils.isNotEmpty(key)){
				  params.put("key", key);
				  params.put("keyFlg", "2");
			  }
		  }
		  String betType=this.request.getParameter("betType");
		  if(StringUtils.isNotEmpty(betType)){
			  params.put("betType", betType);
		  }
		  String process=this.request.getParameter("process");
		  if(StringUtils.isNotEmpty(process)){
			 params.put("process", process);
		  }
		  String maxMoney=this.request.getParameter("maxMoney");
		  if(StringUtils.isNotEmpty(maxMoney)){
			  params.put("maxMoney", maxMoney);
		  }
		  String minMoney=this.request.getParameter("minMoney");
		  if(StringUtils.isNotEmpty(minMoney)){
			  params.put("minMoney", minMoney);
		  }
		  String coopBuyStatus=this.request.getParameter("coopBuyStatus");
		  if(StringUtils.isNotEmpty(coopBuyStatus)){
			  params.put("coopBuyStatus", coopBuyStatus);
		  }
		  result=getBetAdminService().findCoopBuyList(params);
		}
	    generateResult(1, MSG_SUCCESS, result);
	  return SUCCESS;
	}
	/**详情**/
	public String coopBuyDetailMethod()throws Exception{
		Long betId=new Long(this.request.getParameter("betId"));
		String type=this.request.getParameter("type");
		String betCategory=this.request.getParameter("betCategory");
		String wiciType=this.request.getParameter("wiciType");
		log.info("type="+type+"  betCategory="+betCategory+"  wiciType="+wiciType);
		BetAdminDetailVO po=getBetAdminService().loadCoopByDetailVO(betId, type, betCategory,wiciType);
		this.request.setAttribute("po",po);
		return "detail.coop.order.url";
	}
	public void getPhaseNoList()throws Exception{
		
		 String betCategory=this.request.getParameter("betCategory");
		 String phaseCategory=BetTools.toPhaseCategory(Integer.parseInt(betCategory));
		 String callback = request.getParameter("callback");  
         response.setContentType("text/xml;charset=utf-8");
         response.setHeader("Pragma","No-cache");
         response.setDateHeader("Expires",0);
         response.setHeader("Cache-Control","no-cache");    
		 Map params=new HashMap();
		 params.put("phaseCategory", phaseCategory);
		 List list=getBetService().findLatestPhaseList(params);
		 JSONArray jsonArray = JSONArray.fromObject(list);
	     JSONObject jsobjcet = new JSONObject();
	     jsobjcet.put("data", jsonArray);
	     response.getWriter().write(callback+"("+jsobjcet.toString()+");");
	}
	//-------------------------3.2.7自动跟单后台管理------------------------------------------------------
	/***金牌发起人管理列表:默认页面  2010-03-11 13:16**/
	public String listKingSponsorMethod()throws Exception{
		log.info("金牌发起人管理列表");
		return "king.sponsor.list";
	}
	/****金牌发起人管理列表;默认页面JS加载的数据 2010-03-11 13:16**/
	public String listKingSponsors()throws Exception{
		Map params=new HashMap();
		params.put("startRow", this.getStartRow());
		params.put("pageSize", this.getPageSize());
		Map  result=null;
		String betCategory=this.request.getParameter("betCategory");//彩种
		if(StringUtils.isNotEmpty(betCategory)){
			  params.put("betCategory", betCategory);
		}
		String gdStatus=this.request.getParameter("gdStatus");
		if(StringUtils.isNotEmpty(gdStatus)){
			  params.put("gdStatus", gdStatus);
		}
		String key=this.request.getParameter("key");
		if(StringUtils.isNotEmpty(key)){
			  params.put("key", key);
		}
		result=getBetAdminService().findKingList(params);
		generateResult(1, MSG_SUCCESS, result);
	  return SUCCESS;
	}
	/**金牌发起人管理详情  文档59页**/
	public String kingSponsorDetailMethod()throws Exception{
		Long kingId=new Long(this.request.getParameter("kingId"));
		log.info("JJ");
		Map params=new HashMap();
		params.put("kingId", new Long(kingId));
		KingSponsor po=getBetAdminService().loadKingSponsor(params);
		this.request.setAttribute("po",po);
		return "king.sponsor.detail.url";
	}
	/****金牌发起人管理列表;自动跟单列表 2010-03-11 13:16**/
	public String listMyAutoOrders()throws Exception{
		Map params=new HashMap();
		params.put("startRow", this.getStartRow());
		params.put("pageSize", this.getPageSize());
		Map  result=null;
		String kingId=this.request.getParameter("kingId");//彩种
		if(StringUtils.isNotEmpty(kingId)){
			  params.put("kingId", new Long(kingId));
		}
		String type=this.request.getParameter("type");
		if(StringUtils.isNotEmpty(type)){
			  params.put("type", type);
		}
		String key=this.request.getParameter("key");
		if(StringUtils.isNotEmpty(key)){
			  params.put("key", key);
		}
		result=getBetAdminService().findAutoOrderList(params);
		generateResult(1, MSG_SUCCESS, result);
	  return SUCCESS;
	}
	public void cancelMyAutoOrderMethod()throws Exception{
		
		 String autoOrderId=this.request.getParameter("autoOrderId");
		 
		 String callback = request.getParameter("callback");  
         response.setContentType("text/xml;charset=utf-8");
         response.setHeader("Pragma","No-cache");
         response.setDateHeader("Expires",0);
         response.setHeader("Cache-Control","no-cache");    
		 boolean b=this.getBetAdminService().updateMyAutoOrder(new Long(autoOrderId));
		 if(b){
	       response.getWriter().write("取消成功");
		 }else{
		   response.getWriter().write("取消失败");
		 }
	}
	
	
    private static final Log log=LogFactory.getLog(BetAdminAction.class);

}
