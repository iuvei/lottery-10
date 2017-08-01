package com.wintv.framework.utils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Util {

	
	static int[] base64DecodeChars = new int[] { -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
		-1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
		12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1,
		-1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
		40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };
	
	
	
	
	
	
	
	
	/**
	 * MD5
	 */

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getDateByString(String dateString) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.parse(dateString);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	/**
	 * 全角空格�?2288，半角空格为32 其他字符半角(33-126)与全�?65281-65374)的对应关系是：均相差65248
	 * 
	 * 半角-->全角
	 * 
	 * @param src
	 *            要转换的包含全角的任意字符串
	 * @return 转换之后的字符串
	 */

	public static String toSemiangle(String src) {
		if ("".equals(src))
			return "";
		char[] c = src.toCharArray();
		for (int index = 0; index < c.length; index++) {
			if (c[index] == 34) {// 半角'
				c[index] = (char) 8220;
			} else if (c[index] == 39) {// 半角 "
				c[index] = (char) (8216);
			}
		}
		return String.valueOf(c);
	}

	/*
	 * public static String toSemiangle(String src) { if("".equals(src)) return
	 * ""; char[] c = src.toCharArray(); for (int index = 0; index < c.length;
	 * index++) { if (c[index] == 32) {// 半角空格 c[index] = (char) 12288; } else
	 * if (c[index] > 30 && c[index] < 127) {// 其他半角字符 c[index] = (char)
	 * (c[index] + 65248); } } return String.valueOf(c); }
	 */

	/**
	 * 全角--> 半角
	 */
	/*
	 * public static String toSemiangle(String src) { char[] c =
	 * src.toCharArray(); for (int index = 0; index < c.length; index++) { if
	 * (c[index] == 12288) {// 全角空格 c[index] = (char) 32; } else if (c[index] >
	 * 65280 && c[index] < 65375) {// 其他全角字符 c[index] = (char) (c[index] -
	 * 65248); } } return String.valueOf(c); }
	 */
	/**
	 * 将字符串name1,name2，，name3,,name4格式化成name1,name2,name3,name4
	 */
	public static String formatInputNames(String names) {
		String result = "";
		if (null != names && names.length() > 0) {
			String namesTemp = names.trim().replaceAll("，", ",");
			StringBuilder namesResult = new StringBuilder();
			for (String name : namesTemp.split(",")) {
				if (null != name && !name.equals("")) {
					namesResult.append(",").append(name);
				}
			}
			result = namesResult.toString().replaceFirst(",", "");
		}
		return result;
	}
	
	public static String strdecode(String str) {
		return utf_8to16(base64_decode(str));
	}
	
	public static String base64_decode(String str) {
		String out;
		int c1, c2, c3, c4;
		int i, len;

		len = str.length();
		i = 0;
		out = "";
		while (i < len) {
			/* c1 */
			do {
				c1 = base64DecodeChars[(int) str.charAt(i++) & 0xff];
			} while (i < len && c1 == -1);
			if (c1 == -1)
				break;

			/* c2 */
			do {
				c2 = base64DecodeChars[(int) str.charAt(i++) & 0xff];
			} while (i < len && c2 == -1);
			if (c2 == -1)
				break;

			out += (char) ((c1 << 2) | ((c2 & 0x30) >> 4));

			/* c3 */
			do {
				c3 = (int) (str.charAt(i++) & 0xff);
				if (c3 == 61)
					return out;
				c3 = base64DecodeChars[c3];
			} while (i < len && c3 == -1);
			if (c3 == -1)
				break;

			out += (char) (((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));

			/* c4 */
			do {
				c4 = (int) str.charAt(i++) & 0xff;
				if (c4 == 61)
					return out;
				c4 = base64DecodeChars[c4];
			} while (i < len && c4 == -1);
			if (c4 == -1)
				break;
			out += (char) (((c3 & 0x03) << 6) | c4);
		}
		return out;
	}
	
	public static String utf_8to16(String str) {
		String out;
		int i, len, c;
		int char2, char3;

		out = "";
		len = str.length();
		i = 0;
		while (i < len) {
			c = (char) str.charAt(i++);
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				// 0xxxxxxx
				out += str.charAt(i - 1);
				break;
			case 12:
			case 13:
				// 110x xxxx 10xx xxxx
				char2 = (int) str.charAt(i++);
				out += (char) (((c & 0x1F) << 6) | (char2 & 0x3F));
				break;
			case 14:
				// 1110 xxxx 10xx xxxx 10xx xxxx
				char2 = (int) str.charAt(i++);
				char3 = (int) str.charAt(i++);
				out += (char) (((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
				break;
			}
		}

		return out;
	}
	
	public static String getDecimalFormat(BigDecimal money){
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00");
		String outStr = fmt.format(money); 
		return outStr;
	}
}
