package com.wintv.lottery.bet.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Tools {
	private static int[][] chip={{1,0,0,0,0,0},{3,1,0,0,0,0},{7,4,1,0,0,0},{15,11,5,1,0,0},{31,26,16,6,1,0},{63,57,42,22,7,1}};
	//根据数组算出单关相应的投注数
	private static int  f0(int[] arg){
		   int result=0;
		   for(int i=0;i<arg.length;i++){
		   	result+=arg[i];
		   }
		   return result;
	 }
	 //根据数组算出格式为"1串1"相应的注数
	private static int f1(int[] arg){
	   int  result=1;
	   for(int i=0;i<arg.length;i++){
	   	result*=arg[i];
	   }
	   return result;
	 }
	 //根据数组算出格式为"2串1"相应的注数
	private static int f2(int[] arg){
	    int result=0;
	    for(int i=0;i<arg.length;i++){
	        for(int j=i+1;j<arg.length;j++){
			result+=arg[i]*arg[j];
		}
	    }
	    return result;
	 }
	 //根据数组算出n串1相应的注数
	private static int f(int[] data,int n){
	   if(data.length==n||n==1){
	      return f1(data);
	   }else if(n==2){
	      return f2(data);
	   }else{
		   
	     int[] temp=new int[data.length-1];
	     for(int i=0;i<data.length-1;i++){
	        temp[i]=data[i];
	     }
	     return data[data.length-1]*f(temp,n-1)+f(temp,n);
	   }
	 }
	//获取m串n相应的关数
	private static int  f4(int m,int n){
		   for(int j=0;j<chip[m-1].length;j++){
		   	if(chip[m-1][j]==n){
				return (m-j);
			}        
		   }
		   return -1;
	}
	//获取m串n相应的注数 
	private static int ff(int[] data,int m,int n){
	    int result=0;
	    int length=0;
	    if(m>6){
	    	length=m-1;
	    }else{
	    	length=m-f4(m,n);
	    }
	    for(int i=m;i>length;i--){
	        if(i==1){
		   		result+=f0(data);
			}else{
		        result+=f(data,i);
			}
	    }
	   return result;
	}
	//求含定胆的m串n相应的注数
	 public static int gg(int[] a,int b[],int m,int n){
	    if(a.length==0){
	       return ff(b,m,n);
	    }
	    int count=0;
	    int result=0;
	    if(m>6){
	    	count=m-1;
	    }else{
	    	count=m-f4(m,n);
	    }
	    for(int i=m;i>count;i--){
	       if(i-a.length>0){
		  		result+=ff(a,a.length,1)*ff(b,i-a.length,1);
	       }else{
	          result+=ff(a,i,1);
		}
	    }
	   return result;
	}

public static long  getBetNumber(String betContent,String wiciWay){
	 String[]  betContents=betContent.split(";");//71,310,1;82,3,0;
	 if(betContents!=null&&betContents.length>0){
	     List<Integer> aList=new ArrayList<Integer>();
	     List<Integer> bList=new ArrayList<Integer>();
    	 for(int i=0;i<betContents.length;i++){
    		 String betContentValue=betContents[i];
    		 if(betContentValue!=null&&betContentValue.length()>0){
    		   String[] betContentArr=betContentValue.split(",");
    		   if(betContentArr!=null&&betContentArr.length>0){
    		     String danCode=betContentArr[2];//胆码
    		     String digital=betContentArr[1];//310
		         if("1".equals(danCode)){
		    	    aList.add(digital.length());
		         }else if("0".equals(danCode)){
		    	   bList.add(digital.length());
		         }
    		   }
    		 }
		 }
    	 int[] a=new int[aList.size()];
    	 for(int i=0;i<aList.size();i++){
    		 a[i]=aList.get(i);
    	 }
    	 int[] b=new int[bList.size()];
    	 for(int i=0;i<bList.size();i++){
    		 b[i]=bList.get(i);
    	 }
    	 String[] mn=wiciWay.split("串");
    	 int m=Integer.parseInt(mn[0].trim());
    	 int n=Integer.parseInt(mn[1].trim());
    	 long _betNumber=Tools.gg( a, b, m,n);
    	return _betNumber;
	 }
		 return 0;
}
	public static void main(String[]  args)throws Exception{
		 
		 String aaa="553,3,0;552,1,0;509,3,0;510,1,1;549,3,0;550,1,0;551,1,0";
		 String[] bbb=aaa.split(";");
		 int x=0,y=0;
		 List<Integer> aList=new ArrayList<Integer>();
		 List<Integer> bList=new ArrayList<Integer>();
		 for(int i=0;i<bbb.length;i++){
			 String[] temp=bbb[i].split(",");
			 if("1".equals(temp[2])){
				 aList.add(1);
			 }else{
				 bList.add(0);
			 }
			 
		 }
		 int[] a=new int[aList.size()];
		 int[] b=new int[bList.size()];
		 for(int i=0;i<a.length;i++){
			 a[i]=1;
		 }
		 for(int i=0;i<b.length;i++){
			 b[i]=1;
		 }
		 
		 //bbb=[];
		 //aa[i]=3;
		//gg(int[] a,int b[],int m,int n)
		
		 int m=9;
		 int n=1;
		 int result=gg( a, b, m, n);
		 //System.out.print(result);
		 
	
	
					
					
					
					
					
					
				
			//{getBetNum:gg,getGuanShu:f4};

    }
	


}
