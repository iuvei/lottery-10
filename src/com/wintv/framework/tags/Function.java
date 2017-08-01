package com.wintv.framework.tags;


public class Function {
	
	public static boolean contains(String string,String serachString,String regex){
		if(string==null)
			return false;
		String[] args = string.split(regex);
		for(String arg :args){
			if(arg.equals(serachString))
				return true;
		}
		return false;
	}
	
	public static String speReplace(String str){
		return "";
	}
}
