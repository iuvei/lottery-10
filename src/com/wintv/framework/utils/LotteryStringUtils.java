package com.wintv.framework.utils;
import org.apache.commons.lang.StringUtils;

import com.wintv.framework.exception.BadInputException;
public class LotteryStringUtils {
	public static boolean isNotEmpty(String str){
		String myStr= "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		if(myStr!=null){
         String[] arr=myStr.split("|");
         for(int i=0;i<arr.length;i++){
        	String ele=arr[i];
        	str=str.toLowerCase();
        	if(str.indexOf(ele)!=-1){
        		return false;
        	}
        }
		}
        return StringUtils.isNotEmpty(str);
	}
	public static boolean isBad(String str) throws BadInputException{
		String myStr= "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
        String[] arr=myStr.split("|");
        for(int i=0;i<arr.length;i++){
        	String ele=arr[i];
        	str=str.toLowerCase();
        	if(str.indexOf(ele)==-1){
        		throw new BadInputException("禁止攻击本站!");
        	}
        }
       return true;
	}
	

}
