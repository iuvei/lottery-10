package com.wintv.lottery.admin.b2c.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.pojo.C2CProduct;
import com.wintv.lottery.b2c.vo.ExpertVO;
import com.wintv.lottery.admin.bet.action.BaseBetAction;

import com.wintv.lottery.b2c.service.B2CService;
import com.wintv.lottery.b2c.vo.B2CProductVO;
import com.wintv.lottery.pay.service.PayService;

public class B2CAdminAction extends BaseBetAction {
	public String listExpertsPage()throws Exception{
		
		return SUCCESS;
	}
	
	public String addExpertHome()throws Exception{
		return "add.expert.home";
	}
	public void saveExpert()throws Exception{
        
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html;charset=utf-8");
        response.setHeader("Pragma","No-cache");
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control","no-cache"); 
        try{
		  
		  ExpertVO po=new ExpertVO();
		  String expertName=request.getParameter("expertName");
		  po.setExpertName(expertName);
		  String expertClass=request.getParameter("expertClass");
		
		  po.setExpertClass(expertClass);
		  String contractTime=request.getParameter("signTime");
		  po.setContractTime(contractTime);
		  String weekPack=request.getParameter("weekPack");
		  po.setWeekPack(weekPack);
		  String monthPack=request.getParameter("monthPack");
		  po.setMonthPack(monthPack);
		  String seasonPack=request.getParameter("seasonPack");
		  po.setSeasonPack(seasonPack);
		  String yearPack=request.getParameter("yearPack");
		  po.setYearPack(yearPack);
		  String introduction=request.getParameter("introduction");
		  po.setIntroduction(introduction);
		  String status=request.getParameter("status");
		  po.setStatus(status);
		  String isValuePack=request.getParameter("valuepack");
		  if(StringUtils.isEmpty(isValuePack)){
			  po.setIsValuePack("0");
		  }
		  po.setIsValuePack(isValuePack);
		  long expertId=this.b2CService.saveExpert(po);
		  if(expertId>0){
		    response.getWriter().println(expertId);
		  }else{
			response.getWriter().println("0");
		  }
			
        }catch(Exception e){
			e.printStackTrace();
			
		}
	
	}
	public String listExperts()throws Exception{
		Map params=new HashMap();
		params.put("startRow", this.getStartRow());
		params.put("pageSize", this.getPageSize());
		String monthPack=this.request.getParameter("monthPack");//包月人数
		if(StringUtils.isNotEmpty(monthPack)){
			params.put("monthPack", monthPack);
		}
		String weekPack=this.request.getParameter("weekPack");//包周人数
		if(StringUtils.isNotEmpty(weekPack)){
			params.put("weekPack", weekPack);
		}
		String soldMoney=this.request.getParameter("soldMoney");
		if(StringUtils.isNotEmpty(soldMoney)){
			params.put("soldMoney", soldMoney);
		}
		String flg=this.request.getParameter("flg");
		if(StringUtils.isNotEmpty(flg)){
			params.put("flg", flg);
		}
		String status=this.request.getParameter("status");
		if(StringUtils.isNotEmpty(status)){
			params.put("status", status);
		}
		String expertName=this.request.getParameter("expertName");
		if(StringUtils.isNotEmpty(expertName)){
			params.put("expertName", expertName);
		}
		String rate=this.request.getParameter("rate");
		if(StringUtils.isNotEmpty(rate)){
			params.put("rate", rate);
		}
		
		
		Map result=this.b2CService.findExpertList(params);
		generateResult(1, MSG_SUCCESS, result);
	  return SUCCESS;
	}
	/**专家内参发布——赛事及内参类型选择界面   文档第9页 2010-03-12 17:40**/
	public String findB2CAgainstList() throws Exception{
		List raceList=this.b2CService.findB2CRaceList();
		request.setAttribute("raceList", raceList);
		//
		Map params=new HashMap();
		List list=this.b2CService.findB2CAgainstList(params);
		request.setAttribute("list", list);
		
		return SUCCESS;
	}
	public String b2cPubMethod()throws Exception{
		
		return "b2c.pub";
	}
	public void loadPubInitData()throws Exception{
		response.setContentType("text/xml;charset=utf-8");
        response.setHeader("Pragma","No-cache");
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control","no-cache");    
		List experts=this.b2CService.findExpertList();
		String callback=request.getParameter("callback");
	    JSONArray jsonArray=JSONArray.fromObject(experts);
	    JSONObject jsobjcet = new JSONObject();
        jsobjcet.put("data", jsonArray);
        String xinshuiNo=this.b2CService.genExpertCode();
        jsobjcet.put("xinshuiNo", xinshuiNo);
        String againstId=request.getParameter("againstId");
        B2CProductVO po=this.b2CService.loadB2CProduct(new Long(againstId), null);
        jsobjcet.put("po", po);
        response.getWriter().write(callback+"("+jsobjcet.toString()+");");
	}
	
	public void saveB2CProduct()throws Exception{
		response.setContentType("text/xml;charset=utf-8");
        response.setHeader("Pragma","No-cache");
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control","no-cache");    
        String againstId=request.getParameter("againstId");
        String selectType=request.getParameter("selectType");
        String zhDesc=request.getParameter("zhDesc");
        String xinshuiNo=request.getParameter("xinshuiNo");
	    B2CProductVO vo=this.b2CService.loadB2CProduct(new Long(againstId), null);
	    C2CProduct po=new C2CProduct();
	    po.setRaceName(vo.getRaceName());
	    po.setHostName(vo.getHostName());
	    String expertName=request.getParameter("expertName");
	    po.setTxUsername(expertName);
	    String expertID=request.getParameter("expertID");
	    po.setTxUserId(new Long(expertID));
	    if("1".equals(selectType)||"2".equals(selectType)){
	    	po.setType("1");//亚盘
	    }else if("3".equals(selectType)||"4".equals(selectType)){
	    	po.setType("2");//大小盘
	    }else{
	    	po.setType("3");//欧赔
	    }
	    po.setSelectType(selectType);
	    po.setIsB2C("1");
	    po.setZhDesc(zhDesc);
	    po.setPubTime(new Date());
	    po.setStatus("1");
	    po.setXinshuiNo(xinshuiNo);
	    po.setAgainstId(new Long(againstId));
	    this.b2CService.saveB2C(po);
	    
        
        response.getWriter().write("心水发布成功!");
	}
    public String orderManageMethod()throws Exception{
		
		return SUCCESS;
	}
    public String listOrders()throws Exception{
      String flg=request.getParameter("flg");
      if("pay".equals(flg)){
    	  String b2cOrderId=request.getParameter("b2cOrderId");
    	  int result=this.payService.b2CXinshuiPay(new Long(b2cOrderId));
    	  response.setContentType("text/xml;charset=utf-8");
          response.setHeader("Pragma","No-cache");
          response.setDateHeader("Expires",0);
          response.setHeader("Cache-Control","no-cache");    
          response.getWriter().println(result);
          return null;
      }
	  Map params=new HashMap();
	  params.put("startRow", this.getStartRow());
	  params.put("pageSize", this.getPageSize());
	  Map result=this.b2CService.findB2COrderList(params);
	  generateResult(1, MSG_SUCCESS, result);
	 return SUCCESS;
	}
    
    
    
    @Autowired
    private PayService payService;
	@Autowired
	private B2CService b2CService;
	private static final Log log=LogFactory.getLog(B2CAdminAction.class);

}
