package com.wintv.framework.common;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

public class MakeXML {
	
	public String makeXml(String message,boolean flag) throws Exception{
		StringBuffer bf = new StringBuffer();
		bf.append("<?xml version='1.0' encoding='UTF-8'?>");
		bf.append("<response>");
		if(message != null){
			if(flag){ //������ȷ��Ϣ
				bf.append("<message>");
				bf.append(message);
				bf.append("</message>");
			}else{ //���ش�����Ϣ
				bf.append("<error>");
				bf.append(message);
				bf.append("</error>");
			}
		}
		bf.append("</response>");
		return bf.toString();
	}
	
	public String makeXml(Object o,String message) throws Exception{
		StringBuffer bf = new StringBuffer();
		bf.append("<?xml version='1.0' encoding='UTF-8'?>");
		bf.append("<response>");
		if(message != null){
			bf.append("<message>");
			bf.append(message);
			bf.append("</message>");
		}
		if(o != null){
			parseObject(o,bf);
		}
		
		bf.append("</response>");
		return bf.toString();
	}
	
	public String makeXml(List list,String message) throws Exception{
		StringBuffer bf = new StringBuffer();
		bf.append("<?xml version='1.0' encoding='UTF-8'?>");
		bf.append("<response>");
		if(message != null){
			bf.append("<message>");
			bf.append(message);
			bf.append("</message>");
		}
		if(list != null){
			Iterator iter = list.iterator();
			while(iter.hasNext()){
				Object o = iter.next();
				parseObject(o,bf);
			}
		}
		bf.append("</response>");
		return bf.toString();
		
	}
	
	private void parseObject(Object o,StringBuffer bf) throws Exception{
		Class c = o.getClass();
		Field[] fs = c.getDeclaredFields();
		//System.out.println(o.getClass().getSimpleName().toLowerCase());
		bf.append("<"+c.getSimpleName().toLowerCase()+">");
		for(int i = 0;i < fs.length;i++){
			fs[i].setAccessible(true);
			bf.append("<"+fs[i].getName()+">"+fs[i].get(o)+
							"</"+fs[i].getName()+">");
		}
		bf.append("</"+c.getSimpleName().toLowerCase()+">");
	}
	
	
	
	public static void main(String[] args) throws Exception{
		
	}
}
