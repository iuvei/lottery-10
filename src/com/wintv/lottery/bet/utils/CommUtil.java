package com.wintv.lottery.bet.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.wintv.framework.domain.base.Result;
import com.wintv.framework.exception.LotteryBizException;

public class CommUtil {
	/** 投注倍数正则表达式 */
	public static String betMultiRegEx = "^(([1-9][0-9])|[1-9])$";
	/** 胜负14场投注内容正则表达式 */
	public static String winDraw14RegEx = "^([013]{1,3})(,[013]{1,3}){12},([013]{1,3})$";
	/** 任选9场投注内容中数字统计正则表达式 */
	public static String optional9NumberCount = "\\*";
	/** 任选9场手选投注内容正则表达式 */
	public static String optional9RegEx = "^([0-9]+,[013]{1,3},0)(;[0-9]+,[013]{1,3},0){7};([0-9]+,[013]{1,3},0)$";
	/** 任选9场文件上传投注内容正则表达式 */
	public static String optional9UpLoadRegEx = "^(([013]{1,3})|\\*)(,(([013]{1,3})|\\*)){12},(([013]{1,3})|\\*)$";
	/** 6场半全场手选投注内容正则表达式 */
	public static String halfComplete6RegEx = "^([013]{1,3}-[013]{1,3})(,([013]{1,3}-[013]{1,3})){4},([013]{1,3}-[013]{1,3})$";
	/** 6场半全场投注内容正则表达式 */
	public static String halfComplete6UpLoadRegEx = "^([013]{1,3})(,([013]{1,3})){10},([013]{1,3})$";
	/** 4场进球手选投注内容正则表达式 */
	public static String goal4RegEx = "^([0-3]{1,4}-[0-3]{1,4})(,([0-3]{1,4}-[0-3]{1,4})){2}(,[0-3]{1,4}-[0-3]{1,4})$";
	/** 4场进球投注内容正则表达式 */
	public static String goal4UpLoadRegEx = "^([0-3]{1,4})(,([0-3]{1,4})){6},([0-3]{1,4})$";

	/** 各彩种上传投注内容正则表达式字符串数组 */
	public static String[] LOTTERY_UPLOAD_REGEX = { winDraw14RegEx,
			optional9UpLoadRegEx, halfComplete6UpLoadRegEx, goal4UpLoadRegEx };
	public static Integer[] LOTTERY_UPLOAD_FILE_MAXLINE = { 14, 9, 12, 8 };
	public static Integer[] LOTTERY_UPLOAD_FILE_MAXLINESIZE = { 4782969, 19683,
			531441, 531441 };

	/**
	 * 正则表达式匹配
	 */
	public static boolean matcher(String regEx, String matcherContent) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(matcherContent);
		return m.find();
	}

	/**
	 * 相似字符在字符串中出现次数判断
	 */
	public static boolean matcherTimes(String regEx, String matcherContent,
			int times) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(matcherContent);
		int i = 0;
		while (m.find()) {
			i++;
		}
		return i == times;
	}

	/**
	 * 为上传文件自动分配文件名称，避免重复
	 * 
	 * @return String
	 */
	public static String generateFileName(String fileName) {
		// 获得当前时间
		DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		// 转换为字符串
		String formatDate = format.format(new Date());
		// 随机生成文件编号
		int random = new Random().nextInt(10000);
		// 获得文件后缀名称
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);
		// 组成一个新的文件名称
		return formatDate + random + extension;
	}

	public static String genString(String betPlan) {
		if (betPlan != null && betPlan.length() == 1) {
			return betPlan;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < betPlan.length(); i++) {
			char e = betPlan.charAt(i);
			sb.append(e);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
	
		return sb.toString();
	}

	/**
	 * 计算上传文件某一行的投注注数 betPlanLine 上传文件的某一行
	 * 
	 * @author Hikin Yao
	 * 
	 * @param oneLine
	 *            文件中一行字符串
	 */
	public static Integer calculateOneLineBetNum(String oneLine) {
		String curPlan = oneLine;
		Integer betNum = 0;
		if (null != curPlan && curPlan.length() > 0) {
			betNum = 1;
			curPlan = curPlan.trim();
			String[] betResults = curPlan.split(",");
			for (String oneBetResult : betResults) {
				if (!"*".equals(oneBetResult) && oneBetResult.length() > 0) {// 任选九场时，过滤掉"*"
					betNum = betNum * oneBetResult.length();
				}
			}
		}
		return betNum;
	}

	/**
	 * 验证各彩种上传的文件
	 * 
	 * @author Hikin Yao,sl Yuan
	 * @param doc
	 *            上传文件
	 * @param fileName
	 *            上传文件名
	 * @param lotteryType彩种类别
	 *            6=胜负彩, 7=任九, 8=6场半全场, 9=4场进球
	 */
	public static Result doValidator(File doc, String fileName,
			String lotteryType) throws LotteryBizException {
		Integer lotteryIndex = Integer.parseInt(lotteryType) - 6;
		Result result;
		StringBuilder message = new StringBuilder();
		// 设定上传文件最大值
		int uploadFileMaxSize = (LOTTERY_UPLOAD_FILE_MAXLINE[lotteryIndex] * 2 - 1)
				* LOTTERY_UPLOAD_FILE_MAXLINESIZE[lotteryIndex];
		// 用户所投总注数
		Integer totalBetNum = 0;
		// 匹配标志
		boolean matcherFlg = false;
		// 上传文件所在的路径名
		try {
			// 判断上传文件是否还存在
			if (doc != null && doc.exists()) {
			
				if (doc.length() < uploadFileMaxSize) {
					// 获得文件后缀名称
					int position = fileName.lastIndexOf(".");
					String extension = fileName.substring(position);
					if (!".txt".equals(extension)) {
						message.append("上传文件出错：上传文件应该是txt文件！");
					} else {
						Integer index=0;
						StringBuilder errorMessage=new StringBuilder();
						List uploadFile = FileUtils.readLines(doc);
						Iterator di = uploadFile.iterator();
						while (di.hasNext()) {
							index++;
							/* 对文件内容是否符合标准进行检查 */
							String line = (String) di.next();
							line=line.trim();
							if(!line.equals("")){
								matcherFlg = matcher(
										LOTTERY_UPLOAD_REGEX[lotteryIndex], line);
								if (lotteryIndex == 1) {// 任选九场时 加做只选九场格式验证
									matcherFlg = matcherFlg
											&& matcherTimes(optional9NumberCount,
													line, 5);
								}
								if(!matcherFlg) {// 格式不比配 提示错误信息 退出循环
									errorMessage.append(",").append(index);
									if(index%20==0){
									errorMessage.append("<br/>");
									}
								}else{
									// 计算用户总投注数
									totalBetNum += calculateOneLineBetNum(line);
								}
							}
						}//while循环结束
						//错误信息
						if(!"".equals(errorMessage.toString())) {
							String errorLines=errorMessage.toString();
							errorLines=errorLines.replaceFirst(",", "");
							message.append("上传文件中第<br/>["+errorLines+"]<br/>行出现格式错误：请检查是否出现特殊字符,并参考标准格式样本填写您要提交的文件！");;
						}
					}
				} else {
					message.append("上传文件出错：上传文件内容太大！");
				}

			} else {
				message.append("上传文件出错：上传文件不存在！");
			}
			// 设置返回结果
			if (!"".equals(message.toString())) {
				// 文件报错
				result = generateResult(0, "failure", message.toString());
			} else {
				// 返回用户数据
				result = generateResult(1, "success", totalBetNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return result;
	}
	/**
	 * 拼装返回结果对象
	 * 
	 * @author Hikin Yao
	 * 
	 */
	private static Result generateResult(int code, String message,
			Object response) {
		Result result = new Result();
		result.setCode(code);
		result.setMessage(message);
		result.setData(response);
		return result;
	}

	public static String getCurrency(String str) {
		NumberFormat n = NumberFormat.getCurrencyInstance();
		double d;
		String outStr = null;
		try {
			d = Double.parseDouble(str);
			outStr = n.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outStr;
	}
	public static String myRound2(double f,int n){   
		  NumberFormat   nbf=NumberFormat.getInstance();   
		  nbf.setMinimumFractionDigits(n);   
		  nbf.setMaximumFractionDigits(n);   
		  return   nbf.format(f);   
	}

	public static void main(String[] args) {
		// genString("012345");
		/*
		 * String
		 * aa="^([0-9]+,[013]{1,3},0)(;[0-9]+,[013]{1,3},0){12};([0-9]+,[013]{1,3},0)$";
		 * String regEx
		 * ="^([0-9]+,[013]{1,3},0)(;[0-9]+,[013]{1,3},0){7};([0-9]+,[013]{1,3},0)$";
		 * String testString ="505,310,0;505,310,0"; String
		 * betPlan="505,310,0;504,1,0;553,1,0;552,1,0;510,1,0;549,1,0;506,1,0;550,1,0;550,1,0";
		 * CommUtil name = new CommUtil(); if(name.matcher(name.optional9RegEx,
		 * testString)){ System.out.println("正确"); }else {
		 * System.out.println("错误"); } if(name.matcher(regEx, betPlan)){
		 * System.out.println("正确"); }else { System.out.println("错误"); }
		 */
		String aa = "013,113,111,113,113,110,113,113,111,110,111,113,113,111";
		System.out.println(aa.getBytes().length * 8);
	}
}
