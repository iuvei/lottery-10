package com.wintv.framework.tags;


import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

public class Message extends Component {
	private static Logger logger = Logger.getLogger(Message.class);
	private static String name;
	private static java.util.Properties prop = new java.util.Properties();

	/**
	 * load config file
	 * 
	 */
	static{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("actionIn18_zh_CN.properties");
		try {
			prop.load(url.openStream());
		} catch (IOException e) {
			logger.error("Read abstractActionSupport.properties failed", e);
		}
	}
	
	/**
	 * get message form config file
	 * @param key get message key
	 * @param isNum if value is number, isNum
	 * @return
	 */
    public static String getMsg(String key, boolean isNum){
        if(isNum){
            return prop.getProperty(key) != null ? prop.getProperty(key) : "0";
        }else{
            return prop.getProperty(key) != null ? prop.getProperty(key) : "";
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Message(ValueStack arg0) {
		super(arg0);
	}

	/**
	 * do start tag.
	 */
	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		try {
			String str = prop.getProperty(name);
			writer.write(str == null ? name : str);
		} catch (IOException ex) {
			logger.error("do message tag failed", ex);
		}
		return result;
	}
}
