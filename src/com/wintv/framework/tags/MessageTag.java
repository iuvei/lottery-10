package com.wintv.framework.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class MessageTag extends ComponentTagSupport {

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		return new Message(arg0); 
	}

	protected void populateParams() {
		super.populateParams();
		Message msg = (Message) component;
		msg.setName(name);
	}
}
