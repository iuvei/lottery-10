package com.wintv.lottery.bet.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.common.Constants;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.utils.CommUtil;
import com.wintv.lottery.bet.vo.PhaseNoVO;

/**
 * 
 * @author sl Yuan
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class DuplexShoppingOptional9Action extends BaseAction {

	private static final long serialVersionUID = 48153536117786634L;

	@Autowired
	private BetService betService;
	// 代购截止时间
	private String mulDeadline;
	// 期次信息
	private PhaseNoVO phaseNoVO;
	// 任选9场的当前及预售期次号
	private List phaseAgainst;
	// 任选9场本期对阵列表
	private List againstList;
	// 对阵列表长度
	private int againstListLength;

	// 封装上传文件的属性
	private File doc;
	// 封装上传文件名称属性
	private String fileName;
	// 封装上传文件类型属性
	private String contentType;
	// 保存文件路径属性
	private String dir;
	// 保存文件名称属性
	private String targetFileName;

	public void setDoc(File file) {
		this.doc = file;
	}

	public void setDocFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setDocContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * 任选9场代购投注画面的初期显示。
	 * 
	 * @return String
	 */
	public String display() {
		Long phaseId = 1l;
		try {

			/* 查询任选9场的当前期及预售次号 */
			// 定义此次操作函数参数集合
			Map parameter1 = new HashMap();

			// 设定期次分类 '6':胜负彩期次 '9':进球彩期次 '8':半全场期次 '1':北京单场期次
			parameter1.put("phaseCategory",
					Constants.ORDER_CATEGORY_WINNINGLOSING);

			// 开始此次查询操作
			phaseAgainst = betService.findLatestPhaseList(parameter1);
			Iterator iterator = phaseAgainst.iterator();
			// 从菜单栏进入时页面初始化处理
			if (!"".equals(request.getParameter("phaseId"))
					&& request.getParameter("phaseId") != null) {
				phaseId = Long.parseLong(request.getParameter("phaseId"));

			}
			// 循环取得任选9场的当前期及预售次号
			while (iterator.hasNext()) {

				PhaseNoVO vo = (PhaseNoVO) iterator.next();

				if (phaseNoVO == null) {// 从菜单栏进入时页面初始化处理
					if (vo.getId().equals(phaseId)) {
						phaseNoVO = vo;
					}

				} else {// 选择对应期此后页面的刷新处理

					if (vo.getId().equals(phaseNoVO.getId())) {
						phaseNoVO = vo;
					}
					phaseId = phaseNoVO.getId();
				}
			}

			/* 查询任选9场的期次号 对阵列表 复式投注截止时间 */
			// 定义此次操作函数参数集合
			Map parameter2 = new HashMap();

			// 为此次操作函数的参数集合赋值
			parameter2.put("phaseId", phaseId);
			// parameter2.put("phaseCategory",
			// Constants.ORDER_CATEGORY_WINNINGLOSING);
			// parameter2.put("isCurrent", "1");

			// 开始此次查询操作
			Map phaseAgainstList = betService.findPhaseAgainstList(parameter2);
			if (phaseAgainstList != null) {
				LotteryPhasePO po = (LotteryPhasePO) phaseAgainstList.get("po");

				// 为代购截止时间设值
				mulDeadline = po.getMulDeadline();

				// 为任选9场本期对阵列表设值
				againstList = (List) phaseAgainstList.get("againstList");
				againstListLength = againstList.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}

		return SUCCESS;
	}

	public String execute() throws Exception {

		return SUCCESS;
	}

	/**
	 * 任选9场代购投注画面的投注动作。
	 * 
	 * @return String
	 */
	public String betting() {

		/** 从客户端传递过来的参数 */
		// 用户请求类型
		String actionType = request.getParameter("actionType");
		// 用户投注方案
		String plan = request.getParameter("plan");
		// 投注倍数
		String betMulti = request.getParameter("multiples");
		// 投注期号 (同时去掉多余字符)
		String phaseNo = request.getParameter("phaseNo").trim().split(" ")[0];
		// 投注期次ID
		String phaseId = request.getParameter("phaseId");
		// 是否使用彩金
		String useCaijin = request.getParameter("useCaijin");
		// TXT文件路径
		String txtPath;

		/** 返回给用户的执行结果 */
		// json值返回容器
		JSONObject jsonResult = new JSONObject();
		// 报错信息
		StringBuilder message = new StringBuilder("");

		/** 需要根据用户传递来的数据分析得出的参数 */
		// 投注金额
		BigDecimal totalAmount;
		// 投注数
		String betNum = "0";

		/** 本次代购投注的执行所需要的参数 */
		Map parameters = new HashMap();

		try {
			// 用户信息取得
			UserCookie user = (UserCookie) request.getSession().getAttribute(
					"userCookie");
			// 用户登录认证
			if (user != null && !"".equals(user)) {
				/* 对用户传入的"投注倍数"进行合法化验证 */
				if (!CommUtil.matcher(CommUtil.betMultiRegEx, betMulti.trim())) {

					/* 对非法数据进行报错处理 */
					message.append("请正确提交您的投注倍数！");
					jsonResult.put("message", message.toString());

				} else {

					/* 针对不同的投注方式对数据进行处理 */
					if ("primary".equals(actionType)) {// 用户手选式投注处理。

						/* 验证用户传来的投注方案 */
						if (!CommUtil.matcher(CommUtil.optional9RegEx, plan
								.trim())) {
							// if (!(CommUtil.matcher(CommUtil.optional9RegEx,
							// plan.trim()))) {
							message.append("请正确提交您的投注内容！");
							jsonResult.put("message", message.toString());
						} else {
							/* 解析用户所传的投注内容，并获得本次投注所要的参数。 */

							String[] palnArray = plan.split(";");
							int count = 1;
							for (int i = 0; i < palnArray.length; i++) {
								count *= palnArray[i].split(",")[1].length();
							}
							// 获得用户所投注的倍数
							betNum = Integer.toString(count);
							// 设定用户投注方案
							parameters.put("betContent", plan);
						}
					} else if ("advance".equals(actionType)) {// 用户上传投注处理
						this.doValidator(message, jsonResult);
						if ("".equals(message.toString())) {
							// 获得用户所投注的倍数
							betNum = jsonResult.get("betNum").toString();
							/* 保存上传的文件 */
							// 获得upload路径的实际目录
							String realPath = ServletActionContext
									.getServletContext().getInitParameter(
											"uploadPath");
							String targetDirectory = realPath+ "\\" + Constants.UPLOAD_PALN_FILE;
							// 生成保存文件的文件名称
							targetFileName = CommUtil.generateFileName(fileName);
							// 保存文件的路径
							setDir(targetDirectory + "\\" + 2 + "\\" + phaseId + "\\" + targetFileName);
							// 建立一个目标文件
							File target = new File(targetDirectory + "\\" + 2 + "\\" + phaseId, targetFileName);
							System.out.println(target.getAbsolutePath());
							// 将临时文件复制到目标文件
							FileUtils.copyFile(doc, target);
							/* 用户上传式投注 */
							txtPath = targetFileName;
							// 设定文件保存路径
							parameters.put("txtPath", txtPath);
						}

					} else {
						message.append("非法的用户请求！");
						jsonResult.put("message", message.toString());
					}

					if ("".equals(message.toString())) {
						/* 进行用户投注信息的设定 */
						// 设定投注彩种 '1': 胜负14场 '2':任9场 '3':4场进球 '5':6 场半全场
						// '61':单场半全场 '62':单场比分 '63':单场胜平负 '64':单场上下单双
						// '65':单场总进球
						parameters.put("betCategory", "2");
						// 设定方案截至时间
						parameters.put("endTime", mulDeadline);
						// 设定足彩期次
						parameters.put("phaseNo", phaseNo);
						// 设定足彩期次ID
						parameters.put("phaseId", phaseId);
						// 设定注倍数
						parameters.put("betMulti", betMulti);
						// 设定投注人用户ID
						parameters.put("betUserid", user.getUserId());
						// 设定投注人用户名字
						parameters.put("betUsername", user.getUsername());
						// 设定用户投注类型 :'1'单代 '2' 单合 '3 '复代 '4'复合
						parameters.put("type", "3");
						/* 设定用户在扣除金额时是否使用彩金 */
						if ("true".equals(useCaijin)) {
							// 使用彩金
							parameters.put("useCaijin", "1");
						} else {
							// 不使用彩金
							parameters.put("useCaijin", "0");
						}
						// 设定购买彩票 1:足球单场,6:胜负彩,7:任九,8:6场半全场,9:4场进球) |购买b2c(10:)
						// |购买c2c(11)
						// 12:充值 13:取款 14:缴纳保证金15:心水保证金解冻
						parameters.put("categoryType", "7");
						// 设定投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场
						// 62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
						// parameters.put("betContent", "2");
						// 设定投注数
						parameters.put("betNum", betNum);
						// 计算得出用户投注所需要的金额
						totalAmount = (new BigDecimal(betMulti)).multiply(
								new BigDecimal(betNum)).multiply(
								new BigDecimal(2));
						// 设定总金额
						parameters.put("allMoney", totalAmount);

						/* 进行用户投注信息的 */
						// 支付完后返回的参数 4：余额不足,不足以本次支付。 1：扣款成功。
						long josnresult = 0l;

						// 保存"场"的投注结果.
						josnresult = betService.saveBetOrder(parameters);
						// 投注过程数据库返回的结果
						String result = "";
						if (1 == josnresult) {

							result = SUCCESS;
						} else if (4 == josnresult) {
							result = "本次投递已经保存，但您的当前余额不足，请及时充值！";
						} else {
							result = "系统报错,不成功！";
						}
						jsonResult.put("result", result);
					}
				}
			} else {
				message.append("您还未登录，或者您的本次连接已过期，请登录后再投注！");
				jsonResult.put("message", message.toString());
			}
			ajaxForAction(jsonResult.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 响应任选9场代购投注画面的上传文件验证。
	 * 
	 * @return String
	 */
	public String validator() {
		// 错误信息
		StringBuilder message = new StringBuilder("");
		// json 返回结果
		JSONObject jsonResult = new JSONObject();

		try {

			this.doValidator(message, jsonResult);
			ajaxForAction(jsonResult.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 任选9场代购投注画面的上传文件验证。
	 * 
	 * @return String
	 */
	private void doValidator(StringBuilder message, JSONObject jsonResult) {
		// 设定上传文件最大值
		int uploadFileMaxSize = (9 * 2 - 1) * 19683;
		// 用户所投注数
		String betNum = "0";
		int caculatBetNum = 0;
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
						List uploadFile = FileUtils.readLines(doc);
						Iterator di = uploadFile.iterator();
						while (di.hasNext()) {
							int lineBetNum = 1;
							/* 对文件内容是否符合标准进行检查 */
							String line = (String) di.next();
							if (!(CommUtil.matcher(
									CommUtil.optional9UpLoadRegEx, line) && CommUtil
									.matcherTimes(
											CommUtil.optional9NumberCount,
											line, 5))) {
								message
										.append("上传文件出错：上传文件内容不符合标准，请按照标准格式样本填写您要提交的文件！");
								break;
							}
							String[] lineArray = line.split(",");
							for (int i = 0; i < lineArray.length; i++) {
								if (!lineArray[i].contains("*")) {
									lineBetNum *= lineArray[i].length();
								}
							}
							caculatBetNum += lineBetNum;
						}
						// 计算用户所投注数
						betNum = Integer.toString(caculatBetNum);
					}
				} else {
					message.append("上传文件出错：上传文件内容太大！");
				}

			} else {
				message.append("上传文件出错：上传文件不存在！");
			}
			// 设置json返回结果
			if (!"".equals(message.toString())) {
				// 文件报错
				jsonResult.put("message", message.toString());
			} else {
				// 返回用户数据
				jsonResult.put("betNum", betNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
	}

	public String getMulDeadline() {
		return mulDeadline;
	}

	public void setMulDeadline(String mulDeadline) {
		this.mulDeadline = mulDeadline;
	}

	public PhaseNoVO getPhaseNoVO() {
		return phaseNoVO;
	}

	public void setPhaseNoVO(PhaseNoVO phaseNoVO) {
		this.phaseNoVO = phaseNoVO;
	}

	public List getAgainstList() {
		return againstList;
	}

	public void setAgainstList(List againstList) {
		this.againstList = againstList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getTargetFileName() {
		return targetFileName;
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}

	public File getDoc() {
		return doc;
	}

	public int getAgainstListLength() {
		return againstListLength;
	}

	public void setAgainstListLength(int againstListLength) {
		this.againstListLength = againstListLength;
	}

	public void setPhaseAgainst(List phaseAgainst) {
		this.phaseAgainst = phaseAgainst;
	}

	public List getPhaseAgainst() {
		return phaseAgainst;
	}
}
