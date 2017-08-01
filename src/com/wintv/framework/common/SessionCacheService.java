package com.wintv.framework.common;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionCacheService {
    private static SessionCacheService instance = new SessionCacheService();
    private SessionCacheService(){};
    private Map<String,String> map = new HashMap<String,String>();
    
    public static SessionCacheService getInstance(){
    	return instance;
    }
    
    public void addSession(String sessionId,String modulName){
    	synchronized(this){
    		map.put(sessionId,modulName);
    	}	
    }
    
    public void updateSession(String sessionId,String modulName){
    	synchronized(this){
    		map.remove(sessionId);
    	    map.put(sessionId, modulName);
    	}
    }
    
    public void removeSession(String sessionId){
    	synchronized(this){
    		map.remove(sessionId);
    	}	
    }
    
    public List<String> getKeys(){
    	synchronized(this){
    		List<String> result = new ArrayList<String>(); 
    		result.addAll(map.keySet());
    		return result;
    	}
    }
    
    public String getValue(String key){
    	return map.get(key);
    }
    
}

