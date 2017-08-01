package com.wintv.lottery.scores;


import org.apache.commons.lang.StringUtils;


/**
 * @author zym
 * @version v 1.0 
 * */
public class XMLWriter {

	public static String getImmediateData(String xmlConstants){
		return (String)ResourceMap.getResource().get(xmlConstants);  
	}
	
	
	public static void createXML(){
		if(StringUtils.isNotBlank(getImmediateData(XMLConstants.bifen))){
			//创建XML文件
		}
	}
	
}
