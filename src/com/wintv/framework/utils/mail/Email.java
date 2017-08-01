package com.wintv.framework.utils.mail;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Send email
 * 
 * @author zym
 * 
 */
public class Email {
	private static final Log log = LogFactory.getLog(Email.class);
	private String host = "smtp.163.com";
	private String from = "hunan8413@163.com";
	private boolean auth = true;
	private String authName = "hunan8413@163.com";
	private String authPassword = "100.200";
	private Session session = null;
	private MimeMessage message = null;
	private Map<String, String> header = null;
	
	public void reInitial(String host, String from, String authName, String authPassword) {
		this.host = host;
		this.from = from;
		this.authName = authName;
		this.authPassword = authPassword;
		initial();
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public boolean isAuth() {
		return auth;
	}
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getAuthPassword() {
		return authPassword;
	}
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}
	
	public Map<String, String> getHeader() {
		return header;
	}
	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
	
	
	public Email(){
	}
	
	/**
	 * initial email configuration
	 */
	public void initial(){
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", auth);
		SmtpAuthenticator smtpauth = new SmtpAuthenticator(authName, authPassword);
		session = Session.getDefaultInstance(props, smtpauth);
	}
	/**
	 * send email
	 * @param subject subject of email
	 * @param content content of email 
	 * @param to send to
	 * @return send success returns true, else returns false
	 */
	public boolean send(String subject, String content, String to) {
		initial();
		/** display debug info*/
		session.setDebug(false);
		message = new MimeMessage(session);
		try{
			
			message.setFrom(new InternetAddress(from));
			String[] tos = to.split(",");
			for(String toAddr : tos) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
			}
			message.setText(content);
			/*
			 * subject = StringUtil.getString(subject, "GB2312"); 
				subject = new String(Base64.encode((subject).getBytes())); 
				msg.setSubject("=?GB2312?B?" + subject + "?="); 

			 * */
			message.setSubject(subject);
			//message.setText(content);
			message.saveChanges();
			Iterator<String> it = header.keySet().iterator();
			while(it.hasNext()){
				String name = it.next();
				message.setHeader(name, header.get(name));
			}
			Transport.send(message);
		}catch(Exception ex){
			log.debug("send email failed:\n" + ex.getStackTrace());
			return false;
		}
		return true;
	}
	
	public static void main(String []args) throws Exception{
		ApplicationContext ac = new ClassPathXmlApplicationContext("email.xml");
		Email mailSender = (Email) ac.getBean("emailSender");
		mailSender.send("123", "<a href='http://www.baidu.com'>www.baidu.com</a>", "hunan8411@163.com");
	}
}
