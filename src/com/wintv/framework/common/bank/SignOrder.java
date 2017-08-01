package com.wintv.framework.common.bank;

import com.wintv.framework.common.RealPath;
import com.wintv.framework.common.bank.bean.Sign;


public class SignOrder {

	
	
	public static Sign SignOrder(String OrdId, String TransAmt) {
		Sign sign = new Sign();
		
		//初始化key文件�? 
		chinapay.PrivateKey key=new chinapay.PrivateKey(); 
		chinapay.SecureLink t; 
		boolean flag; 

		RealPath realPath = new RealPath();
		
		flag=key.buildKey(sign.getMerId(), 0, realPath.getRealPath()+"MerPrk.key"); 
		
		if (flag==false) 
		{ 
			System.out.println("build key error!"); 
			return null; 
		} 

		t=new chinapay.SecureLink (key); 

		String TransDate = "";
		java.text.SimpleDateFormat f=new java.text.SimpleDateFormat("yyyyMMdd");
		TransDate = f.format(new java.util.Date());
		
		// 对订单的签名
		String ChkValue= t.signOrder(sign.getMerId(), OrdId, TransAmt, sign.getCuryId(), TransDate, sign.getTransType()); 
		
		sign.setTransDate(TransDate);
		sign.setOrdId(OrdId);
		sign.setTransAmt(TransAmt);
		sign.setChkValue(ChkValue);
		
		return sign;
		
	}
	
	public static void main(String[] args) {
		System.out.println(SignOrder.SignOrder("0838021231231233", "000000000001").getChkValue());
	}
}
