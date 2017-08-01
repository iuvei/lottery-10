package com.wintv.lottery.admin.phase.tools;

import java.util.HashSet;
import java.util.Set;

public class Tools {
	public static boolean hasDuplicateItem(String[] arr){
		Set<String>  set=new HashSet<String>();
		for(String i:arr){
			if(!set.add(i)){
				return true;
			}
		}
		return false;
	}
	public static void main(String[] arr){
		String[] s={"1","3","5","9"};
		Tools t=new Tools();
		boolean b=t.hasDuplicateItem(s);
		
	}

}
