package com.wintv.lottery.bet.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.BetOrderChoice;
import com.wintv.lottery.bet.service.BetOrderRecordService;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.vo.CoopOrder;

@SuppressWarnings({"serial","unchecked"})
public class MyBetOrderAction extends BaseAction {
   public String execute()throws Exception{
	 String flg=request.getParameter("flg");
	 if("1".equals(flg)){
		 return SUCCESS;
	 }else if("2".equals(flg)){
		 return "daigou_list";
	 }else if("3".equals(flg)){
		 return "canyu_list";
	 }else if("4".equals(flg)){
		 return "faqi_list";
	 }
	 return SUCCESS;
   }
   public String detail()throws Exception{
	     String betOrderId=request.getParameter("id");
		 Map map=this.betOrderRecordService.loadMyBetRecord(new Long(betOrderId));
		 List<BetOrderChoice> orderChoiceList=(List<BetOrderChoice>)map.get("orderChoiceList");
		 BetOrder betOrder=(BetOrder)map.get("betOrder");
		 request.setAttribute("orderChoiceList",orderChoiceList);
		 request.setAttribute("betOrder",betOrder);
		 request.setAttribute("betCategory",betOrder.getBetCategory());
		 String sponsorType=betOrder.getSponsorType();//发起类型 '1':发起人 '2':参与合买  '0':代购
		 String stars=(String)map.get("stars");
		 request.setAttribute("stars",stars);
		if("1".equals(sponsorType)){
          double baodiPer=(Double)map.get("baodiPer");
          double baodi=(Double)map.get("baodi");
		  request.setAttribute("baodi",baodi);
		  request.setAttribute("baodiPer",baodiPer);
		  double progress=(Double)map.get("progress");
		  request.setAttribute("progress",progress);
		  String orderStatus=(String)map.get("orderStatus");
		  request.setAttribute("orderStatus",orderStatus);
		  String betCategory=betOrder.getBetCategory();
		 
		  if("61".equals(betCategory)){
			   request.setAttribute("wanfa","单场半全场");
		   }else if("62".equals(betCategory)){
			   request.setAttribute("wanfa","单场比分");
		   }else if("63".equals(betCategory)){
			   request.setAttribute("wanfa","单场让球胜平负");
		   }else if("64".equals(betCategory)){
			   request.setAttribute("wanfa","单场上下单双");
		   }else if("65".equals(betCategory)){
			   request.setAttribute("wanfa","单场总进球");
		   }
		  return "faqi.63.detail";
		}else{//  14场 代购
			 String betCategory=betOrder.getBetCategory();
			 if("1".equals(betCategory)||"2".equals(betCategory)){
			   Long zjCnt=(Long)map.get("zjCnt");
			   request.setAttribute("zjCnt",zjCnt);
			   String orderStatus=(String)map.get("orderStatus");
			   request.setAttribute("orderStatus",orderStatus);
			   String allBonus=(String)map.get("allBonus");
			   request.setAttribute("allBonus",allBonus);
			   
			 
			   Map p=new HashMap();
			   p.put("phaseId", betOrder.getPhaseId());
			   List againstList=this.betOrderRecordService.findAgainstList(p);
			   request.setAttribute("againstList", againstList);
			   request.setAttribute("wanfa","胜负14场");
			   
			   String plan=betOrder.getPlan();
			   String txtPath=betOrder.getPlan();
			   if(plan==null&&txtPath==null){
				   request.setAttribute("planStatus","0");
			   }else{
				   request.setAttribute("planStatus","1");
			   }
			   
			  return "daigou.14.detail"; 
			 }else if(betCategory.startsWith("6")){
				   Long zjCnt=(Long)map.get("zjCnt");
				   request.setAttribute("zjCnt",zjCnt);
				   String orderStatus=(String)map.get("orderStatus");
				   request.setAttribute("orderStatus",orderStatus);
				   String allBonus=(String)map.get("allBonus");
				   request.setAttribute("allBonus",allBonus);
				   if("61".equals(betCategory)){
					   request.setAttribute("wanfa","单场半全场");
				   }else if("62".equals(betCategory)){
					   request.setAttribute("wanfa","单场比分");
				   }else if("63".equals(betCategory)){
					   request.setAttribute("wanfa","单场让球胜平负");
				   }else if("64".equals(betCategory)){
					   request.setAttribute("wanfa","单场上下单双");
				   }else if("65".equals(betCategory)){
					   
					   request.setAttribute("wanfa","单场总进球");
				   }
				 return "single.daigou.detail";
			 }
			 
		 }
		 
		 return SUCCESS;
	   }
   public String cancelOrder()throws Exception{
       String id=this.request.getParameter("id");
	   
       response.setContentType("text/xml;charset=utf-8");
       response.setHeader("Pragma","No-cache");
       response.setDateHeader("Expires",0);
       response.setHeader("Cache-Control","no-cache");    
	   boolean status=this.betOrderRecordService.canvelMyOrder(new Long(id));
	   if(status){
         response.getWriter().write("取消成功!");
	   }else{
		 response.getWriter().write("取消失败!");
	   }
      return SUCCESS;
   }
   public String listMyBetOrder()throws Exception{
	  
	   Map params=new HashMap();
	   String flg=request.getParameter("flg");
	    params.put("flg", flg);
	    UserCookie currentUser = (UserCookie) session.get("userCookie");
	    Long betUserId=currentUser.getUserId();
	    params.put("betUserId", betUserId);
		params.put("startRow",this.getStartRow());
	    params.put("pageSize",this.getPageSize());
	    System.out.println("flg="+flg);
	    System.out.println("startRow="+this.getStartRow());
	    System.out.println("pageSize="+this.getPageSize());
	    String betCategory=this.request.getParameter("betCategoryID");
	    if(StringUtils.isNotEmpty(betCategory)){
	    	params.put("betCategory", betCategory);
	    }
	    String orderStatus=this.request.getParameter("orderStatusID");
	    if(StringUtils.isNotEmpty(orderStatus)){
	    	params.put("orderStatus", orderStatus);
	    }
	    String zjStatusID=this.request.getParameter("zjStatusID");
	    if(StringUtils.isNotEmpty(zjStatusID)){
	    	params.put("zjStatus", zjStatusID);
	    }
	    String betTimeFrom=this.request.getParameter("betTimeFrom");
	    if(StringUtils.isNotEmpty(betTimeFrom)){
	    	params.put("betTimeFrom", betTimeFrom);
	    }
	    String betTimeTo=this.request.getParameter("betTimeTo");
	    if(StringUtils.isNotEmpty(betTimeTo)){
	    	params.put("betTimeTo", betTimeTo);
	    }
	    Map resultMap=betService.findMyBetOrderList(params);
		generateResult(1, MSG_SUCCESS, resultMap);
	return SUCCESS;
   }
   /**合买者分页显示   2010-04-01 16:59**/
   public String listParticipate()throws Exception{
	   Map params=new HashMap();
	   String betId=request.getParameter("betId");
	   if(StringUtils.isNotEmpty(betId)){
	    	params.put("betId", new Long(betId));
	   }
	   params.put("startRow",this.getStartRow());
	   params.put("pageSize",20);
	   List resultList=betOrderRecordService.findParticipateList(params);
	   long totalCount=betOrderRecordService.findParticipateSize(params);
	   Map resultMap=new HashMap();
	   resultMap.put("totalCount", totalCount);
	   resultMap.put("resultList", resultList);
	   generateResult(1, MSG_SUCCESS, resultMap);
	return SUCCESS;
   }
   @Autowired
   private BetService  betService;
   @Autowired
   private BetOrderRecordService  betOrderRecordService;
   private int startRow=1;
   private int pageSize;
   public int getStartRow() {
	return startRow;
  }
  public void setStartRow(int startRow) {
	this.startRow = startRow;
  }
  public int getPageSize() {
	return pageSize;
  }
  public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
  }
  private static final Log log=LogFactory.getLog(MyBetOrderAction.class);
}
