package com.wintv.lottery.scores;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author zym
 * @version v 1.0 
 */
public class ResourceMap {
	
	private static volatile String bifenData = "";
	private static volatile String peilvData = "";
	private  static Map<String,String> resource = new HashMap<String,String>();;

	static {
		resource.put(XMLConstants.bifen, bifenData);
		resource.put(XMLConstants.peilv, peilvData);
	}
	
	public static Map<String, String> getResource() {
		return resource;
	}
	
}
