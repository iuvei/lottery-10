package com.wintv.lottery;

import java.math.BigDecimal;

public class T {
	public static void main(String[] a){
		//
		Long userId=162L;
         StringBuilder sql=new StringBuilder();
		
		sql.append("select  tz.XINSHUI_MILITARY,");   //心水战绩
		sql.append("(select count(1) cnt from T_C2C_PRODUCT a1 where a1.TX_USER_ID="+userId+") yccc, ");//总预测场次
		sql.append("(select sum(TX_MONEY+TX_CAIJIN) cnt from T_XINSHUI_ORDER b1 where b1.BUY_USER_ID="+userId+") buyXinshuiCnt,");//购买高手心水总次数
		sql.append("(select  sum(TX_MONEY) from T_VA_TRANSACTION_LOG c3  where  c3.TX_TYPE='6' and  c3.FLG='1'  and  c3.TX_USER_ID="+userId+")  buyXinshuiMoney ,");//购买高手心水总金额
		sql.append("(select  sum(TX_MONEY) from T_VA_TRANSACTION_LOG c4  where  c4.TX_TYPE='7' and  c4.FLG='2'  and  c4.TX_USER_ID="+userId+")  xinshuiBuchang ,");//得到总赔偿金额
		sql.append("(select  count(1)  from T_C2C_PRODUCT d5,T_AGAINST e5  where  d5.AGAINST_ID=e5.AGAINST_ID and d5.TX_USER_ID="+userId+" and e5.START_TIME>sysdate) curPubChangci,");//当前发布场次
		sql.append("(select  count(1)  from T_XINSHUI_ORDER a6,T_C2C_PRODUCT b6,T_AGAINST c6  where  a6.PRODUCT_ID=b6.C2C_ID   and b6.AGAINST_ID=c6.AGAINST_ID ");
		sql.append("and c6.START_TIME>sysdate ");
		sql.append("and a6.SOLD_USER_ID="+userId+" and a6.PAY_STATUS='2') curSoldCnt,");//当前销售人数
		sql.append("(select  sum(TX_MONEY+TX_CAIJIN)  from T_XINSHUI_ORDER a7,T_C2C_PRODUCT b7,T_AGAINST c7  ");
		sql.append("where  a7.PRODUCT_ID=b7.C2C_ID  and b7.AGAINST_ID=c7.AGAINST_ID and c7.START_TIME>sysdate  and a7.SOLD_USER_ID="+userId+"  and a7.PAY_STATUS='2') curSoldMoney,");//当前销售金额
		sql.append("(select  sum(ENSURE_MONEY)  from T_C2C_PRODUCT a8,T_AGAINST b8  where  a8.AGAINST_ID=b8.AGAINST_ID ");
		sql.append("and a8.TX_USER_ID="+userId+"  and b8.START_TIME>sysdate) curEnsureMoney,");//当前保证金
		sql.append("(select  count(1)  from T_C2C_PRODUCT d9,T_AGAINST e9  where  d9.AGAINST_ID=e9.AGAINST_ID and d9.TX_USER_ID="+userId+"  and e9.START_TIME>sysdate) curPubChangci,");//总发布场次
		sql.append("(select  count(1)  from T_XINSHUI_ORDER aaa,T_C2C_PRODUCT bbb,T_AGAINST ccc  where  aaa.PRODUCT_ID=bbb.C2C_ID ");   
		sql.append("and bbb.AGAINST_ID=ccc.AGAINST_ID and ccc.START_TIME>sysdate  and aaa.SOLD_USER_ID="+userId+" and aaa.PAY_STATUS='2') curSoldCnt,");//总销售人数
		sql.append("(select  sum(TX_MONEY+TX_CAIJIN)  from T_XINSHUI_ORDER am,T_C2C_PRODUCT bm,T_AGAINST cm  where  am.PRODUCT_ID=bm.C2C_ID ");   
		sql.append("and bm.AGAINST_ID=cm.AGAINST_ID and cm.START_TIME>sysdate  and am.SOLD_USER_ID="+userId+" and am.PAY_STATUS='2') curSoldMoney  "); //总销售金额
		sql.append("from t_user tz where tz.USERID="+userId);

		System.out.println(sql.toString());
	}

}
