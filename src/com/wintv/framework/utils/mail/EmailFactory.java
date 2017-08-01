package com.wintv.framework.utils.mail;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmailFactory {
	/**
	 * send email by spring email
	 * @param body email content
	 * @return if send successful returns true, else returns false
	 */
	public static Email getInstance(String config){
		ApplicationContext ac = new ClassPathXmlApplicationContext(config);
		Email mailSender = (Email) ac.getBean("emailSender");
		return mailSender;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Email email = EmailFactory.getInstance("email.xml");
	}

}
