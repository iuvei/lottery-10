package com.wintv.framework.utils;


import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil extends GregorianCalendar{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4812583049021368089L;

	/**
	* �nian
	*/
	
	private final static Calendar instance = Calendar.getInstance();
	
	/**
	* �nian年
	*/
	public int year;
	/**
	 * 月
	 */
	public int month;
	/**
	 * �日
	 */
	public int day;
	/**
	 * 小时
	 */
	public int hours;
	/**
	 * 分钟
	 */
	public int minutes;
	/**
	 * �miao秒
	 */
	public int seconds;
	
	
	
	public DateUtil() {
		super();
	}

	/**
     * @param 构造日期  yyyy-MM-dd格式
     */
	
	public DateUtil(String date) {
		this(Integer.parseInt(date.substring(0, 4)),Integer.parseInt(date.substring(5, 7))-1,
				Integer.parseInt(date.substring(8, 10)));
	}

	/**
	 * 构造函数
	 * @param year
	 * @param month
	 * @param day
	 */
	public DateUtil(int year, int month, int day) {
		super(year,month-1,day);
	}

	/**
	 * 构造函数
	 * @param year
	 * @param month
	 * @param day
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	public DateUtil(int year,int month,int day,int hours,int minutes,int seconds) {
		super(year,month-1,day,hours,minutes,seconds);
	}

	/**
	 * 得到今天的时间
	 * @return 今天的时间
	 */
	@SuppressWarnings("deprecation")
	public static String today() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		return sdFormat.format(new Date());
	}

	

	/**
	 * 得到制定格式的时间(� yyyy-MM-dd HH:mm:ss)
	 * @return 返回时间 (yyyy-MM-dd HH:mm:ss)
	 */
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer tb = new StringBuffer();
		return sdf.format(this, tb, new FieldPosition(0)).toString();

	}

	/**
	 * 得到制定格式的时间
	 * @return 返回时间
	 */
	public String toString(String style) {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		StringBuffer tb = new StringBuffer();
		return sdf.format(this, tb, new FieldPosition(0)).toString();

	}

	/**
	 * 得到制定格式的时间(� yyyy-MM-dd HH:mm:ss or yyyy-MM-dd)
	 * @param if(isTime) 时间精确到秒(yyyy-MM-dd HH:mm:ss);
	 * @param else 时间精确到日(yyyy-MM-dd)
	 * @return 返回时间 (yyyy-MM-dd HH:mm:ss)  or (yyyy-MM-dd)
	 */

	public String toString(boolean isTime) {
		SimpleDateFormat sdf;
		if (isTime) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		StringBuffer tb = new StringBuffer();
		return sdf.format(this, tb, new FieldPosition(0)).toString();

	}

	/**
	 * 对时间进行格式化,只有长度为10或19的才返回真正的时间，其余为空
	 * @param dateString 时间类型.有两2008-12-10;�2008-12-10 12:24:03)
	 * @return 返回格式化后的时间有两�种：20081210;�?20081210122403)
	 */
	public static String getFmtString(String dateString) {
		String outDate = "";
		String y;
		String m;
		String d;
		String h;
		String mi;
		String s;
		if (dateString == null)
			return outDate;
		if (dateString.length() == 10 || dateString.length() == 19) {
			y = dateString.substring(0, 4);
			m = dateString.substring(5, 7);
			d = dateString.substring(8, 10);
			outDate = y + m + d;
			if (dateString.length() > 10) {
				h = dateString.substring(11, 13);
				mi = dateString.substring(14, 16);
				s = dateString.substring(17, 19);
				outDate += h + mi + s;
			}
		}
		return outDate;
	}

	/**
	 * 把时间转化为定制的格式如：2008-11-19,2008-11-20 24:12:30)
	 * @param outputDate �  进行转化的时间有两种 20021119; 20021120241230)
	 * @return 返回定制的时间格式有两种 2002-11-19； 2002-11-20 24:12:30)
	 */
	public static String getDateOutput(String outputDate) {
		String outDate = "";
		if (outputDate == null)
			return outDate;
		if (outputDate.trim().length() >= 8) {
			String year = outputDate.substring(0, 4);
			String month = outputDate.substring(4, 6);
			String day = outputDate.substring(6, 8);
			outDate = year + "-" + month + "-" + day;
			if (outputDate.trim().length() > 8) {
				String hour = outputDate.substring(8, 10);
				String minute = outputDate.substring(10, 12);
				outDate += " " + hour + ":" + minute;
				if (outputDate.trim().length() > 12) {
					String second = outputDate.substring(12, 14);
					outDate += ":" + second;
				}
			}
		}
		return outDate;
	}

	/**
	 * 得到当前的时间
	 * @param outputDate  
	 * @param style  
	 * @return 返回定制的时间格式
	 */
	public static String getDateOut(String outputDate, String style) {
		String outDate = "";
		if (outputDate == null)
			return outDate;
		if (outputDate.trim().length() >= 8) {
			String year = outputDate.substring(0, 4);
			String month = outputDate.substring(4, 6);
			String day = outputDate.substring(6, 8);
			outDate = year + style + month + style + day;
		}
		return outDate;
	}

	/**
	 * 把时间转化为定制的格式
	 * @param outputDate 
	 * @return 返回定制的时间格式
	 */
	public static String getDateOutputChn(String outputDate) {
		String outDate = "";
		if (outputDate == null)
			return outDate;
		if (outputDate.trim().length() >= 8) {
			String year = outputDate.substring(0, 4);
			String month = outputDate.substring(4, 6);
			String day = outputDate.substring(6, 8);
			outDate = year + "��" + month + "��" + day + "��";
		}
		return outDate;
	}

	/**
	 * 把时间转化为定制的格�式如：2002-11-19,2002-11-20 24:12:30)
	 * @param outputDate �  
	 * @return 返回定制的时间格式
	 */
	public static String getDateOutputChnMore(String outputDate) {
		String outDate = "";
		if (outputDate == null)
			return outDate;
		if (outputDate.trim().length() >= 8) {
			String year = outputDate.substring(0, 4);
			String month = outputDate.substring(4, 6);
			String day = outputDate.substring(6, 8);
			outDate = year + "��" + month + "��" + day + "��";
			if (outputDate.trim().length() > 8) {
				String hour = outputDate.substring(8, 10);
				String minute = outputDate.substring(10, 12);
				outDate += " " + hour + "ʱ" + minute + "��";
			}
		}
		return outDate;
	}

	/**
	 * 年份选择下拉框
	 * @param startYear 开始��年份
	 * @param endYear 结束年份
	 * @return 下拉框形式的String
	 */
	public static String toHtmlSelect(int startYear, int endYear) {
		String options = "";
		int length = endYear - startYear;
		if (length < 0) {
			length = 0;
		}
		for (int i = 0; i < length; i++) {
			options += "<option ";
			options += " value='" + startYear + "' >";
			startYear++;
			options += "" + startYear;
			options += "</option>\n";
		}
		return options;
	}

	/**
	 * 显示小时
	 * @param hour 传过来的时间参数
	 * @return String
	 */
	public static String toHtmlSelectWithHour(int hour) {
		String options = "";
		for (int i = 0; i <= 23; i++) {
			if (i < 10) {
				options += "<option value='0" + i + "'";
			} else {
				options += "<option value='" + i + "'";
			}
			if (i == hour) {
				options += " selected ";
			}
			options += ">";
			if (i < 10) {
				options += "0" + i;
			} else {
				options += i;
			}
			options += "</option>\n";
		}
		return options;
	}

	/**
	 * 显示分钟，以10分钟为一个单位
	 * @param hour 传过来的时间参数
	 * @return String
	 */
	public static String toHtmlSelectWithMinute(int minute) {
		String options = "";
		int minutes = Integer.parseInt((minute + "").substring(0, 1));
		for (int i = 0; i <= 5; i++) {
			options += "<option value='" + i + "0'";
			if (i == minutes) {
				options += " selected ";
			}
			options += ">";
			options += i + "0";
			options += "</option>\n";
		}
		return options;
	}

	/**
	 * 格式化字符串得到小时
	 * @param dateString 时间值共12
	 * @return Result
	 */
	public static int getFmtHour(String dateString) {
		int hour = 9;
		if (dateString == null)
			return hour;
		if (dateString.trim().length() >= 12) {
			hour = Integer.parseInt(dateString.substring(8, 10));
			return hour;
		} else {
			return hour;
		}
	}

	/**
	 * 格式化字符串得到分钟
	 * @param dateString 时间值共12
	 * @return Result
	 */
	public static int getFmtMinute(String dateString) {
		int minute = 0;
		if (dateString == null)
			return minute;
		if (dateString.trim().length() >= 12) {
			minute = Integer.parseInt(dateString.substring(10, 12));
			return minute;
		} else {
			return minute;
		}
	}


	/**
	 *  得到月份中文
	 *  @param int month
	 *  @return
	 */
	public static String getMonth_CN(int month) {
		String monthcn = "";
		switch (month) {
			case 1 :
				monthcn = "一";
				break;
			case 2 :
				monthcn = "二";
				break;
			case 3 :
				monthcn = "三��";
				break;
			case 4 :
				monthcn = "四";
				break;
			case 5 :
				monthcn = "五";
				break;
			case 6 :
				monthcn = "六";
				break;
			case 7 :
				monthcn = "七��";
				break;
			case 8 :
				monthcn = "八";
				break;
			case 9 :
				monthcn = "九��";
				break;
			case 10 :
				monthcn = "十";
				break;
			case 11 :
				monthcn = "十一��";
				break;
			case 12 :
				monthcn = "十二��";
				break;

		}
		return monthcn;
	}

	/**
	 * 根据日期获得格式化后的日期字符串
	 * @param Calendar 日期
	 * @param String 格式，如"yyyy-MM-dd HH:mm:ss"
	 * @return String 格式的日期
	 */
	@SuppressWarnings("static-access")
	public static String getString(Calendar cl, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cl.getInstance().getTime());
	}

	/**
	 * 获得当前服务器日期的格式化字符串
	 * @param String 日期格式
	 * @return String 格式后的当前服务器日期
	 */
	public static String getString(String format) {
		return getString(Calendar.getInstance(), format);
	}

	/**
	 * 根据�年月日获得格式化后的日期
	 * @param int �
	 * @param int 
	 * @param int 
	 * @param String 日期格式
	 * @return String 格式后的日期
	 */
	public static String getString(int y, int m, int d, String format) {
		Calendar cl = Calendar.getInstance();
		if (y != -1)
			cl.set(Calendar.YEAR, y);
		if (m != -1)
			cl.set(Calendar.MONTH, m);
		if (d != -1)
			cl.set(Calendar.DATE, d);
		return getString(cl, format);
	}

	/**
	 * 根据�?��年月日获得格式化后的日期
	 * @param int �
	 * @param int �
	 * @param int 
	 * @param int �
	 * @param int 
	 * @param int �
	 * @param String 日期格式
	 * @return String 格式后的日期
	 */
	public static String getString(
		int y,
		int m,
		int d,
		int h,
		int mm,
		int s,
		String format) {
		Calendar cl = Calendar.getInstance();
		if (y != -1)
			cl.set(Calendar.YEAR, y);
		if (m != -1)
			cl.set(Calendar.MONTH, m);
		if (d != -1)
			cl.set(Calendar.DATE, d);
		if (h != -1)
			cl.set(Calendar.HOUR, h);
		if (mm != -1)
			cl.set(Calendar.MINUTE, mm);
		if (s != -1)
			cl.set(Calendar.SECOND, s);
		return getString(cl, format);
	}

	/**
	 * 清除格式，得到所有是去除非数字字符后的字符串
	 * @param String 
	 * @return String 
	 */
	public static String trimFormat(String sdate) {
		if (sdate == null || sdate.length() == 0)
			return sdate;
		String bases = "0123456789";
		StringBuffer sb = new StringBuffer();
		char[] sc = sdate.toCharArray();
		for (int i = 0; i < sc.length; i++) {
			String temps = String.valueOf(sc[i]);
			if (bases.indexOf(temps) != -1)
				sb.append(temps);
		}
		return sb.toString();
	}


	/**
	 * 将指定格式的日期字符串转换成日期类型对象
	 * @param dateStr 
	 * @param format 
	 * @return 日期对象
	 */
	public static Date parseDate(String dateStr,String format) {
		if(dateStr == null || format == null) return null;
		DateFormat fmt = new SimpleDateFormat(format);
		try {
			return fmt.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 将格式为"yyyy-MM-dd HH:mm:ss"的日期字符串转换成一个日期
	 * @param dateStr 日期字符
	 * @return 日期对象
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 将一个日期对象转换成指定格式的字符串
	 * @param date 日期对象
	 * @param format 输出格式
	 * @return 格式化的日期字符
	 */
	public static String formatDate(Date date,String format) {
		if(date == null || format == null) return null;
		DateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}
	
	/**
	 * 将一个日期对象转换成� 格式yyyy-MM-dd HH:mm:ss"的字符串
	 * @param date 日期对象
	 * @return 格式化的日期字符
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatDateTimemm(Date date) {
		return formatDate(date,"yyyy-MM-dd HH:mm");
	}
	/**
	 * 将一个日期对象转换成 ��格式� HH:mm:ss"的字符串
	 * @param date 日期对象
	 * @return 格式化的日期字符
	 */
	public static String formatTime(Date date) {
		return formatDate(date,"HH:mm:ss");
	}
	
	/**
	 * 取得指定日期增加/减少（n为负数）n天后的日期�
	 * @param date
	 * @param n 要增加天数
	 * @return
	 */
	public static Date add(Date date,int n) {
		if(date == null) return null;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE,n);
		return cal.getTime();
	}
	
	/**
	 * 在当前日期下增加/减少n天后的日期
	 * @param n
	 * @return
	 */
	public static Date add(int n) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE,n);
		return cal.getTime();
	}
	
	/**
	 * 取得当前日期
	 * @return
	 */
	public static Date getCurrentDate () {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 将一个日期对象转换成� 格式� yyyy-MM-dd"的字符串
	 * @param date 日期对象
	 * @return 格式化的日期字符 
	 */
	public static String formatDate(Date date) {
		return formatDate(date,"yyyy-MM-dd");
	}
}