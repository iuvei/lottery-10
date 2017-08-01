package com.wintv.lottery.b2c.utils;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public final class Tools {
  public  static final int ENGLISH_PREMIER_LEAGUE=1; //英超
  public  static final int ASIA_RACE=2;//亚洲联赛
  public  static int NORTH_EU_RACE=3;//北欧联赛
  public  static int SOUTH_AMERICA=4;//南美联赛
  public  static String addTime(Date endTime,String flg)throws Exception{
	  DateFormat df=new SimpleDateFormat("yyyy年MM月dd日");
	  Calendar c= GregorianCalendar.getInstance();
	  c.setTime(endTime);
	  if("1".equals(flg)){
	   c.add(Calendar.WEEK_OF_MONTH, 1);
	  }else if("2".equals(flg)){
		  c.add(Calendar.MONTH, 1);
	  }else if("3".equals(flg)){
		  c.add(Calendar.MONTH, 3);
	  }else if("4".equals(flg)){
		  c.add(Calendar.YEAR,1);
	  }
	  endTime=c.getTime();
	  return df.format(endTime);
  }
}
