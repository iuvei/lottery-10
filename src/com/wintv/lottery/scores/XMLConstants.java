package com.wintv.lottery.scores;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zym
 * @version v 1.0 
 */
public class XMLConstants extends Properties{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7743156808103710119L;

	
	public static String bifen = "bifen";
	
	public static String peilv = "peilv";
	
	
	private static class SingletonXMLPropertiesHolder{
		static Properties instance = getProperties();
	}

	public static Properties getInstance(){
		return SingletonXMLPropertiesHolder.instance;
	}
	
	private static Properties getProperties() {
		// TODO Auto-generated method stub
		Properties propertiesConstants = new Properties();
		try {
			InputStream in = (Class.forName(XMLConstants.class.getName())
					.getClassLoader().getResourceAsStream("XMLInstant.properties"));
				propertiesConstants.load(in);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertiesConstants;
	}
}
