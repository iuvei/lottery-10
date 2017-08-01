package com.wintv.lottery.wdlose.action;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.wdlose.service.WdloseService;
import com.wintv.lottery.wdlose.vo.KjVO;

/**
 * 
 * @author SL YUAN
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class WdloseResultsAction extends BaseAction {

	private static final long serialVersionUID = 48153536117786634L;

	@Autowired
	private WdloseService wdloseService;
	// 彩种
	private String lotteryType;
	// 胜负彩（任九）的期次号列表
	private List option9phaseList;
	// 6场半全场的期次号列表
	private List halfComplete6phaseList;
	// 进球彩的期次号列表
	private List jinQiuphaseList;
	// 任选9场 开奖内容
	private String[] option9Content = null;
	// 任选9场对阵
	private List option9AgainstList = null;
	// 六场半全场对阵
	private List halfComplete6AgainstList = null;
	// 进球彩对阵
	private List Goal4AgainstList = null;
	// 返回用户的JSON结果
	private JSONObject json = new JSONObject();

	/**
	 * 查询最近一期开奖信息。
	 * 
	 * @return String
	 */
	public String display() {
		try {
			// 根据彩种显示数据
			if ("".equals(lotteryType) || lotteryType == null) {
				// 彩种赋初始值
				lotteryType = "1";
			}
			/* 显示"胜负彩开奖公告"数据 */
			if (lotteryType.equals("1")) {

				/* 查询 任选9场 开奖信息 */
				// 任选9场开奖期次列表查询 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场
				// 61:单场半全场 62:单场比分 63:单场让球胜平负 64:单场上下单双 65:单场总进球
				option9phaseList = wdloseService.findPhaseList("1");
				// 任选9场 最近一期的开奖信息查询
				doLoadOption9();
				json.put("option9phaseList", option9phaseList);

				/* 查询 6场半全场 开奖信息 */
				// 6场半全场开奖期次列表查询 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场
				// 61:单场半全场 62:单场比分 63:单场让球胜平负 64:单场上下单双 65:单场总进球
				halfComplete6phaseList = wdloseService.findPhaseList("5");
				// 6场半全场 最近一期的开奖信息查询
				doLoadHalfComplete6();
				/* 返回用户所有 6场半全场 的JSON信息 */
				json.put("halfComplete6phaseList", halfComplete6phaseList);

				/* 显示"进球彩开奖公告"数据 */
			} else if (lotteryType.equals("2")) {

				/* 查询 进球彩 开奖信息 */
				// 任选9场开奖期次列表查询 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场
				// 61:单场半全场 62:单场比分 63:单场让球胜平负 64:单场上下单双 65:单场总进球
				jinQiuphaseList = wdloseService.findPhaseList("3");
				// 进球彩 最近一期的开奖信息查询
				doLoadGoal4();
				/* 返回用户所有 进球彩 的JSON信息 */
				json.put("jinQiuphaseList", jinQiuphaseList);
			}

			ajaxForAction(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 根据期次号加载 任9场 开奖信息
	 * 
	 * @return String
	 */
	public String loadOption9() {
		String phaseNo = request.getParameter("phaseNo");
		if (!"".equals(phaseNo) && phaseNo != null) {
			option9phaseList = new ArrayList();
			option9phaseList.add(phaseNo);
			doLoadOption9();
		}
		ajaxForAction(json.toString());
		return SUCCESS;
	}

	/**
	 * 根据期次号执行加载 任9场 开奖信息
	 * 
	 * @return String
	 */
	public String doLoadOption9() {
		try {
			// 任选9场最近一期的开奖内容查询
			option9Content = wdloseService.bulletin14(option9phaseList.get(0)
					.toString());
			// 任选9场对阵
			option9AgainstList = wdloseService.findHostList(option9phaseList
					.get(0).toString(), "6");
			/* 返回用户所有 任选9场 的JSON信息 */
			json.put("option9AgainstList", option9AgainstList);
			json.put("option9Content", option9Content);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 根据期次号加载 进球彩 开奖信息
	 * 
	 * @return String
	 */
	public String loadGoal4() {
		String phaseNo = request.getParameter("phaseNo");
		if (!"".equals(phaseNo) && phaseNo != null) {
			jinQiuphaseList = new ArrayList();
			jinQiuphaseList.add(phaseNo);
			doLoadGoal4();
		}
		ajaxForAction(json.toString());
		return SUCCESS;
	}

	/**
	 * 根据期次号执行加载 进球彩 开奖信息
	 * 
	 * @return String
	 */
	public String doLoadGoal4() {
		try {
			// 进球彩 最近一期的开奖内容查询
			KjVO vo = wdloseService
					.bulletin4(jinQiuphaseList.get(0).toString());
			// 进球彩对阵
			// betCategory：'6':胜负彩期次 '9':进球彩期次 '8':半全场期次 '1':北京单场期次
			Goal4AgainstList = wdloseService.find4Against(jinQiuphaseList
					.get(0).toString());
			Goal4AgainstList.add(vo.getKjTime());
			Goal4AgainstList.add(vo.getDeadline());
			Goal4AgainstList.add(vo.getKjNo());
			Goal4AgainstList.add(vo.getBonusPool());
			Goal4AgainstList.add(vo.getBetNum1());
			Goal4AgainstList.add(vo.getMoney1());
			Goal4AgainstList.add(vo.getSales());
			/* 返回用户所有 进球彩 的JSON信息 */
			json.put("Goal4AgainstList", Goal4AgainstList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 根据期次号加载 6场半全场 开奖信息
	 * 
	 * @return String
	 */
	public String loadHalfComplete6() {
		String phaseNo = request.getParameter("phaseNo");
		if (!"".equals(phaseNo) && phaseNo != null) {
			halfComplete6phaseList = new ArrayList();
			halfComplete6phaseList.add(phaseNo);
			doLoadHalfComplete6();
		}
		ajaxForAction(json.toString());
		return SUCCESS;
	}

	/**
	 * 根据期次号执行加载 6场半全场 开奖信息
	 * 
	 * @return String
	 */
	public String doLoadHalfComplete6() {
		try {
			// 6场半全场最近一期的开奖内容查询
			KjVO vo = wdloseService.bulletin6(halfComplete6phaseList.get(0)
					.toString());
			// 6场半全场对阵
			halfComplete6AgainstList = wdloseService.findHostList(
					halfComplete6phaseList.get(0).toString(), "8");
			// 添加对应的开讲信息
			halfComplete6AgainstList.add(vo.getKjTime());
			halfComplete6AgainstList.add(vo.getDeadline());
			halfComplete6AgainstList.add(vo.getKjNo());
			halfComplete6AgainstList.add(vo.getBonusPool());
			halfComplete6AgainstList.add(vo.getBetNum1());
			halfComplete6AgainstList.add(vo.getMoney1());
			halfComplete6AgainstList.add(vo.getSales());
			/* 返回用户所有 6场半全场 的JSON信息 */
			json.put("halfComplete6AgainstList", halfComplete6AgainstList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
}
