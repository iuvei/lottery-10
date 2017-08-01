package com.wintv.lottery.admin.phase.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.PrintWriter;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.pojo.BackendUser;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.LotteryPhase;
import com.wintv.lottery.admin.phase.service.PhaseService;
import com.wintv.lottery.admin.phase.tools.Tools;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;

@SuppressWarnings("serial")
public class PhaseManageAction extends BaseAction {
	private static final Log log=LogFactory.getLog(PhaseManageAction.class);
	public String index(){
		return SUCCESS;
	}
	public String findPhaseList(){
		try{
		   Map params=new HashMap();
		   String category=request.getParameter("category");
		   params.put("category", category);
		   String status=request.getParameter("status");
		   params.put("status", status);
		   String lotteryType=request.getParameter("lotteryType");
		   params.put("lotteryType", lotteryType);
		   List list=this.phaseService.findPhaseList(params,this.getStartRow(),this.getPageSize());
		   int totalCount=this.phaseService.findPhaseSize(params);
		   Map result = new HashMap();
		   result.put("totalCount", totalCount);
		   result.put("dataList", list);
		   generateResult(1, MSG_SUCCESS, result);
		 }catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String editPhase(){
		try{
		   String phaseId=request.getParameter("id");
		   Map resultMap=this.phaseService.loadLotteryPhase(new Long(phaseId));
		   LotteryPhasePO po=(LotteryPhasePO)resultMap.get("po");
		   request.setAttribute("po", po);
		  
		}catch(Exception e){
			e.printStackTrace();
		}
		 return "editPhase";
	}
	public String detail(){
		
		try{
		  String phaseId=request.getParameter("id");
		  Map resultMap=this.phaseService.loadLotteryPhase(new Long(phaseId));
		  LotteryPhasePO po=(LotteryPhasePO)resultMap.get("po");
		  List againstList=(List)resultMap.get("againstList");
		  request.setAttribute("po", po);
		  request.setAttribute("againstList", againstList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void againstListDetail(){
		try{
			  String phaseId=request.getParameter("id");
			  Map resultMap=this.phaseService.loadLotteryPhase(new Long(phaseId));
			  List againstList=(List)resultMap.get("againstList");
			  response.setCharacterEncoding("UTF-8"); 
			  response.setContentType("text/xml;charset=GBK");
		      response.setHeader("Pragma","No-cache");
		      response.setDateHeader("Expires",0);
		      response.setHeader("Cache-Control","no-cache"); 
		      //
		      JSONArray jsonArray = JSONArray.fromObject(againstList);   
		      //下面就是把存有查询结果的JSON对象返给页面  
		      PrintWriter out = response.getWriter();  
		      out.println(jsonArray);  
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	public void saveScoreList(){
		try{
			  String flg=request.getParameter("flg");
			  if("saveCsLog".equals(flg)){
				  CSHandleLog log=new CSHandleLog();
				  String memo=request.getParameter("memo");
				  log.setMemo(memo);
				  //BackendUser admin=(BackendUser)this.session.get("admin");
				  //Long csUserId=admin.getUserid();
				  Long csUserId=162L;
				  log.setCsUserId(csUserId);
				  log.setHandleTime(new Date());
				  String ip=request.getRemoteAddr();
				  log.setIp(ip);
				  Long id=this.phaseService.saveCSHandleLog(log);
				  response.setCharacterEncoding("UTF-8"); 
				  response.setContentType("text/xml;charset=GBK");
			      response.setHeader("Pragma","No-cache");
			      response.setDateHeader("Expires",0);
			      response.setHeader("Cache-Control","no-cache"); 
				  if(id!=null){
					 
				      response.getWriter().println("操作成功!");
				  }else{
					 
				      response.getWriter().println("操作失败!");
				  }
			  }else{
			  String pList=request.getParameter("pList");
			  String[] po=pList.split(";");
			  Map<String,String> params=null;
			  for(String against:po){
				  if(!",".equals(against)&&!";".equals(against)&&StringUtils.isNotEmpty(against)){
				    String[] againstPO=against.split(",");
				    String score=againstPO[0];
				    String againstId=againstPO[1];
				    params=new HashMap<String,String>();
				    params.put("againstId", againstId);
				    params.put("score", score);
				    params.put("flg","20");
				    this.phaseService.updatePhase(params);
				  }
			  }
			  response.setCharacterEncoding("UTF-8"); 
			  response.setContentType("text/xml;charset=GBK");
		      response.setHeader("Pragma","No-cache");
		      response.setDateHeader("Expires",0);
		      response.setHeader("Cache-Control","no-cache"); 
		      response.getWriter().println("保存比分成功!");
			  }
			}catch(Exception e){
				e.printStackTrace();
				try{
					  response.setCharacterEncoding("UTF-8"); 
					  response.setContentType("text/xml;charset=GBK");
				      response.setHeader("Pragma","No-cache");
				      response.setDateHeader("Expires",0);
				      response.setHeader("Cache-Control","no-cache"); 
				      response.getWriter().println("操作失败!");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			
			}
		
	}
	public void auditPhase(){
		try{
	   	  Map params=new HashMap();
	   	  Long id= new Long(request.getParameter("id"));
	   	  String flg=request.getParameter("flg");
		  params.put("flg", flg);
		  params.put("id", id);
		 
		  BackendUser admin=(BackendUser)session.get("admin");
		  boolean result=this.phaseService.updatePhase(params);
		  response.setCharacterEncoding("UTF-8"); 
		  response.setContentType("text/xml;charset=GBK");
	      response.setHeader("Pragma","No-cache");
	      response.setDateHeader("Expires",0);
	      response.setHeader("Cache-Control","no-cache"); 
		  if(result){
			  if("1".equals(flg)){
		        this.response.getWriter().println("审核成功!");
			  }else if("5".equals(flg)){
				this.response.getWriter().println("期次成功停止!");
			  }else if("6".equals(flg)){
				this.response.getWriter().println("期次成功作废!");
			  }
		  }else{
			  this.response.getWriter().println("操作失败");
		  }
		}catch(Exception e){
			e.printStackTrace();
	    }
	
	}
	public void savePhase(){
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/xml;charset=utf-8");
        response.setHeader("Pragma","No-cache");
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control","no-cache"); 
        try{
		  Map<String,String[]> params=this.request.getParameterMap();
		  String againstIdList=params.get("selAgainstId")[0];
		  String[] againstIdArr=againstIdList.split(";");
		  boolean  hasDupliItem=Tools.hasDuplicateItem(againstIdArr);
		  if(hasDupliItem){
			  response.getWriter().println("对阵有重复,保存失败!");
		  }else{
		   int result=this.phaseService.savePhase(params);
		   if(result==2){
			  response.getWriter().println("期次已经存在,请勿重复提交!");
		   }else if(result==1){
			  response.getWriter().println("保存成功!");
		   }else if(result==0){
			  response.getWriter().println("保存失败!");
		   }
		  }
		  
        }catch(Exception e){
			e.printStackTrace();
			try{
			  response.getWriter().println("保存失败!");
			}catch(Exception ex){ex.printStackTrace();}
		}
	}
	@Autowired
    private PhaseService phaseService;
	private Integer pageSize=5;// 页面显示数据条数
	private Integer startRow=0;// 查询开始行
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
