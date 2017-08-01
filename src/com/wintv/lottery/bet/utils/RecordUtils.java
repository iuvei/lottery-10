package com.wintv.lottery.bet.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.bet.dao.BetDao;
import com.wintv.lottery.bet.dao.MyAutoOrderDao;
import com.wintv.lottery.bet.vo.CoopOrder;

public class RecordUtils {
	public static List<CoopOrder>  toList(List<CoopOrder> resultList,MyAutoOrderDao myAutoOrderDao,Long userId)throws DaoException{
		
		//如果是胜负彩14场
		  for(CoopOrder po:resultList){
			String type=po.getType();
			int securityType=po.getSecurityType();
		
			if(securityType==1){//完全公开
				String plan=null;
				if("2".equals(type)){//单式合买
					String txtPath=po.getTxtPath();//txt文件名称
					if(StringUtils.isNotEmpty(txtPath)){
						plan="单式上传（公开）<a href='/lottery/bet/downloadTxt.html?id="+po.getBetId()+"'>下载</a>";
					}else{
						plan="单式-未上传";
					}
					po.setPlan(plan);
				}else if("4".equals(type)){//复式合买
				    plan=po.getPlan();
				   
					if(StringUtils.isEmpty(plan)){
						plan="复式-未提交";
					
					}else{
					  po.setPlan("复式-方案尚未提交");
					 
					}
					po.setPlan(plan);
				}
			}
			else if(securityType==2){//截止后公开
				if("4".equals(type)){//复式合买
				  String plan=po.getPlan();
				  Date endTime=po.getEndTime();
				  Date now=new Date();
				  if(StringUtils.isEmpty(plan)){
					  plan="复式-未提交-截止后公开";
				  }else{
					 if(endTime.after(now)){
				        plan="复式选号（截止后公开）";
				     }
				  }
				  po.setPlan(plan);
				}else{//单式合买
					  String txtPath=po.getTxtPath();
					  Date endTime=po.getEndTime();
					  Date now=new Date();
					  String plan=null;
					  if(StringUtils.isEmpty(txtPath)){
						  plan="单式-未上传-截止后公开";
					  }else{
						 if(endTime.after(now)){
					        plan="复式选号（截止后公开）";
					     }else{
					    	 plan="单式上传（截止后公开）<a href='/lottery/bet/downloadTxt.html?id='"+po.getBetId()+">下载</a>";
					     }
					  }
					  po.setPlan(plan);
				}
			 }else if(securityType==3){
				
				Long sponsorUserId=po.getBetUserid();//发起人用户ID
				boolean isGD=myAutoOrderDao.isGenDanUser(sponsorUserId, userId);
				String plan=po.getPlan();
				String txtPath=po.getTxtPath();
				if("2".equals(type)){//单式合买
					if(StringUtils.isNotEmpty(txtPath)){
						if(isGD){
							plan="单式上传<a href='./bet/downloadTxt.html?id='"+po.getBetId()+">下载</a>";
						}else{
							plan="单式上传(仅对跟单用户公开)";
						}
					}else{
						plan="单式-未上传-跟单用户公开";
					}
				}else if("4".equals(type)){
					
					if(StringUtils.isNotEmpty(plan)){
						if(!isGD){
							plan="复式选号（跟单用户公开）";
						}
					}else{
						plan="复式-未提交-跟单用户公开";
					}
					
				}
				po.setPlan(plan);
				
			}
		 }
		return resultList;
	}
public static List<CoopOrder>  toOptional9List(List<CoopOrder> resultList,MyAutoOrderDao myAutoOrderDao,Long userId,BetDao betDao)throws DaoException{
		//如果是任9场
		  for(CoopOrder po:resultList){
			String type=po.getType();
			int securityType=po.getSecurityType();
		    if(securityType==1){//完全公开
				String plan=null;
				if("2".equals(type)){//单式合买
					String txtPath=po.getTxtPath();//txt文件名称
					if(StringUtils.isNotEmpty(txtPath)){
						plan="单式上传（公开）<a href='/lottery/bet/downloadTxt.html?id="+po.getBetId()+"'>下载</a>";
					}else{
						plan="单式-未上传";
					}
					po.setPlan(plan);
				}else if("4".equals(type)){//复式合买
				    plan=betDao.load9Choice(po.getBetId());
				   
					if(StringUtils.isEmpty(plan)){
						plan="复式-未提交";
					
					}else{
					  po.setPlan("复式-方案尚未提交");
					 
					}
					po.setPlan(plan);
				}
			}
			else if(securityType==2){//截止后公开
				if("4".equals(type)){//复式合买
				  String plan=po.getPlan();
				  Date endTime=po.getEndTime();
				  Date now=new Date();
				  if(StringUtils.isEmpty(plan)){
					  plan="复式-未提交-截止后公开";
				  }else{
					 if(endTime.after(now)){
				        plan="复式选号（截止后公开）";
				     }
				  }
				  po.setPlan(plan);
				}else{//单式合买
					  String txtPath=po.getTxtPath();
					  Date endTime=po.getEndTime();
					  Date now=new Date();
					  String plan=null;
					  if(StringUtils.isEmpty(txtPath)){
						  plan="单式-未上传-截止后公开";
					  }else{
						 if(endTime.after(now)){
					        plan="复式选号（截止后公开）";
					     }else{
					    	 plan="单式上传（截止后公开）<a href='/lottery/bet/downloadTxt.html?id='"+po.getBetId()+">下载</a>";
					     }
					  }
					  po.setPlan(plan);
				}
			 }else if(securityType==3){
				
				Long sponsorUserId=po.getBetUserid();//发起人用户ID
				boolean isGD=myAutoOrderDao.isGenDanUser(sponsorUserId, userId);
				String plan=betDao.load9Choice(po.getBetId());
				String txtPath=po.getTxtPath();
				if("2".equals(type)){//单式合买
					if(StringUtils.isNotEmpty(txtPath)){
						if(isGD){
							plan="单式上传<a href='./bet/downloadTxt.html?id='"+po.getBetId()+">下载</a>";
						}else{
							plan="单式上传(仅对跟单用户公开)";
						}
					}else{
						plan="单式-未上传-跟单用户公开";
					}
				}else if("4".equals(type)){
					if(StringUtils.isNotEmpty(plan)){
						if(!isGD){
							plan="复式选号（跟单用户公开）";
						}
					}else{
						plan="复式-未提交-跟单用户公开";
					}
					
				}
				po.setPlan(plan);
				
			}
		 }
		return resultList;
	}


}
