package com.wintv.framework.utils.mail;

import java.io.File;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class SendHtmlEmail {

	
	public static Boolean resetPass(Map<String,Object> param){
		String path = (String)param.get("email_gif_path");
		File bg_gif = new File(path + "/bg.gif");
		File icon_gif = new File(path + "/icon.gif");
		File logo_gif = new File(path + "/logo.gif");
		File bottom_gif = new File(path + "/bottom.gif");
		StringBuilder sb = null;
		HtmlEmail email = null;
		try {
			email = new HtmlEmail();
			sb = new StringBuilder();
			sb.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			sb.append("<html xmlns='http://www.w3.org/1999/xhtml'><head>");
			sb.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
			sb.append("<title>天彩</title>");
			sb.append("<style type='text/css'>");
			sb.append("*{ padding:0; margin:0; }");
			sb.append("body{ font-size:14px; font-family:'宋体', Arial, Helvetica, sans-serif; padding-top:10px; }");
			sb.append(".logo,.body{ width:600px; margin:0 auto; clear:both; }");
			sb.append(".body{ border:1px solid #ccc; border-top:none; background:url(cid:"+ email.embed(bg_gif) +") repeat-x top; }");
			sb.append(".body div{ background:url(cid:"+ email.embed(icon_gif) +") no-repeat left top; padding:50px; line-height:25px; }");
			
			sb.append(".body div b{ font-size:16px; }");
			sb.append(".body a{ color:blue; }");
			sb.append("</style></head><body>");
			sb.append("<div class='logo'><img src='cid:"+ email.embed(logo_gif)  +"' alt='天彩' /></div>");
			sb.append("<div class='body'><div><b>亲爱的"+ (String)param.get("username") +"：</b><br /> ");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;您好，感谢您使用天彩网的服务。<br />");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;您提交了遗忘密码的申请，您可以通过点击<a href='"+ (String)param.get("url") +"' target='_blank'>更改密码</a>进行密码修改。<br /><br />");
			sb.append("-----------------此信由天彩网系统发出系统不接收回信，请勿直接回复。");
			sb.append("如有任何疑问，请联系<a href='#' target='_blank'>天彩网客服</a>，或则访问天彩网<a href='http://www.18lot.com' target='_blank'>http://www.18lot.com</a>与我们取得联系。<br /><br />");
			sb.append("客服热线：400-800-8821    版权:18lot.com<br />");
			sb.append("<img src='cid:"+ email.embed(bottom_gif) +"' alt='' style='float:right;' />");
			sb.append("</div></div></body></html>");
			
			email.setHtmlMsg("修改用户密码");
		    email.setHostName("smtp.163.com");
		    email.addTo((String)param.get("sendTo"));
		    email.setFrom("hunan8413@163.com", "天彩网客服");
		    email.setAuthentication("hunan8413", "100.200");
		    email.setSubject("重设密码");
		    email.setCharset("GBK");
		    email.setMsg(sb.toString());
		    email.send();
		} catch (EmailException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
