package com.wintv.lottery.scores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


/**
 * 
 * @author zym
 * @version v 1.0 
 *
 *  读取XML文件到内存，并插入数据库中 
 */

public class XMLReader{
	
	public static void read(String xmlType){
		URL urlfile;
		BufferedReader in;
		String inputLine = "";
		StringBuffer content = new StringBuffer();
		String url = XMLConstants.getInstance().getProperty(xmlType+".url");
		Map<String,String> map = ResourceMap.getResource();
		//ResourceMap.getResource().put(xmlType, "");
		try {
			urlfile = new URL(url);
			in = new BufferedReader(new InputStreamReader(urlfile.openStream()));
			inputLine = in.readLine();
			while (null != inputLine) {
				content.append(inputLine);
				inputLine = in.readLine();
			}
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			content = null;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			content = null;
			e.printStackTrace();
		}
		
		if(null != content){
			map.put(xmlType, content.toString());
			//将远程数据 写入本地数据库
		}else{
			//远程出现故障（从数据库读取数据）
			map.put(xmlType, "the first try get data");
		}
		inputLine = null;
		content = null;
		urlfile = null;
	}
}
