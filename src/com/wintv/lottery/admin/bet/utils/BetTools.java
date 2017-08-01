package com.wintv.lottery.admin.bet.utils;

public class BetTools {
	/**把投注的彩种转换成期次的彩种**/
	public static String toPhaseCategory(int  betCategory){
		//1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     61:单场半全场  62:单场比分 63:单场胜平负  64:单场上下单双 65:单场总进球
		String phaseCategory=null;
		switch(betCategory){
		case 1:
		case 2:
			phaseCategory="6";
			break;
		case 3:
			phaseCategory="9";
			break;
		case 5:
			phaseCategory="8";
			break;
		case 61:
		case 62:
		case 63:
		case 64:
		case 65:
			phaseCategory="1";
			break;
	   }
		return phaseCategory;
	}

}
