package com.wintv.lottery.personal.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.common.Constants;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;
import com.wintv.lottery.attention.service.MyAttentionService;
import com.wintv.lottery.attention.vo.AttentionPlanVO;
import com.wintv.lottery.attention.vo.AttentionUserVO;
import com.wintv.lottery.attention.vo.AttentionXinshuiVO;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.utils.CommUtil;
import com.wintv.lottery.bet.vo.PhaseNoVO;
import com.wintv.lottery.personal.dao.SpaceDao;
import com.wintv.lottery.personal.service.SpaceService;
import com.wintv.lottery.personal.vo.BetOrderVO;
import com.wintv.lottery.personal.vo.DynaInfoVO;
import com.wintv.lottery.personal.vo.SpaceVO;
import com.wintv.lottery.personal.vo.XinshuiVO;
import com.wintv.lottery.user.service.UserService;

/**
 * 
 * @author sl Yuan
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class PersonallCentreAction extends BaseAction {

	private static final long serialVersionUID = 48153536117786634L;

	@Autowired
	private SpaceService spaceService;
	@Autowired
	private MyAttentionService myAttentionService;
	// 个人id
	private String personalId;
//	// 个人姓名
//	private String personalName;
	// 个人签名
	private String signature;
	//获奖记录
	private String [] bonusList ;
	//他的历史战绩
	private String [] historyRecord;
	private SpaceVO spaceVO;
	// 是否系统用户
	private boolean isSysUser = false;
	// 用户请求类型 1：主页 2：当前投注 3：当前心水 4：历史战绩 5：他的关注
	private String type;

	/**
	 * 个人中心画面的初期显示。
	 * 
	 * @return String
	 */
	public String display() {
		// 获取查看用户的id
		personalId = request.getParameter("u");
		// 获取用户请求的类型
		type = request.getParameter("type");
		System.out.println("personalId>>" + personalId);
		System.out.println("type>>" + type);
		// 用户请求类型没有传值的时候默认为用户请求到主页
		if ("".equals(type) || type == null) {
			type = "1";
		}
		try {
			if (!("".equals(personalId) || personalId == null)) {
				/* 获取系统用户id */
				Object user = request.getSession().getAttribute("userCookie");
				Long userId = 0l;
				if (user != null && !"".equals(user)) {
					userId = ((UserCookie) user).getUserId();
				}
				/*判断用户是否是浏览自己的个人中心页面*/
				if (personalId.equals(Long.toString(userId))) {
					isSysUser = true;
				}
				//获取"个人中心"画面显示要的数据。
				spaceVO = spaceService.findSpaceData(Long.parseLong(personalId));
				if (spaceVO != null && !"".equals(spaceVO)) {
					if (spaceVO.getBonusList() !=null && !"".equals(spaceVO.getBonusList())) {
						bonusList = spaceVO.getBonusList().split(",");
					}
				}
				//获取"他的历史战绩"显示用的数据
				historyRecord = spaceService.loadHistoryMilitary(Long.parseLong(personalId));
				
				//系统用户访问别人的"个人中心"画面时保存此次访问。
				if (!isSysUser && user != null && !"".equals(user)) {
					spaceService.visteSpace(userId, Long.parseLong(personalId));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 加载"主页"的页面内容
	 * 
	 * @return String
	 * */
	public String loadIndexContent() {
		// 获取被查看用户的id
 		System.out.println("personalId>>>"+personalId);
		personalId= request.getParameter("personalId");
		try {
			Map indexMap= spaceService.findHomeSpaceData(Long.parseLong(personalId));
			//他发起的合买列表
			List curSponBetList = (List)indexMap.get("curSponBetList");
			//他参与的合买列表
			List curCanyuBetList = (List)indexMap.get("curCanyuBetList");
			//他发布的心水列表
			List curPubXinshuiList = (List)indexMap.get("curPubXinshuiList");
			//他参与的心水列表
			List curBuyXinshuiList = (List)indexMap.get("curBuyXinshuiList");
			//他的动态列表
			List dynaInfoList =(List)indexMap.get("dynaInfoList");
			//返回用户的json结果
			JSONObject json = new JSONObject(); 
			//他发起的合买json列表
			JSONArray jsonCurSponBetList = new JSONArray();
			for (int i = 0; i < curSponBetList.size(); i++) {
				JSONObject member = new JSONObject();
				AttentionPlanVO voBean = (AttentionPlanVO)curSponBetList.get(i);
				member.put("id", voBean.getId());
				member.put("lotteryCategory", voBean.getLotteryCategory());
				member.put("lotteryType", changeBetTpye(voBean.getLotteryType()));
				//期次号
				member.put("phaseNo", voBean.getPhaseNo());
				//发起人用户名
				member.put("sponsorUsername", voBean.getSponsorUsername());
				member.put("stars", voBean.getStars());//用户投注级别
				//方案总金额
				member.put("allMoney", voBean.getAllMoney());
				//进度
				member.put("progress", voBean.getProgress());
				//投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场
				//       62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
				member.put("betCategoty", voBean.getBetCategoty());
				jsonCurSponBetList.put(i,member);
			}
			//他参与的合买json列表
			JSONArray jsonCurCanyuBetList = new JSONArray();
			for (int j = 0; j < curCanyuBetList.size(); j++) {
				JSONObject member = new JSONObject();
				AttentionPlanVO voBean = (AttentionPlanVO)curCanyuBetList.get(j);
				member.put("id", voBean.getId());
				member.put("lotteryCategory", voBean.getLotteryCategory());
				member.put("lotteryType", changeBetTpye(voBean.getLotteryType()));
				//期次号
				member.put("phaseNo", voBean.getPhaseNo());
				//发起人用户名
				member.put("sponsorUsername", voBean.getSponsorUsername());
				//用户投注级别
				member.put("stars", voBean.getStars());
				//方案总金额
				member.put("allMoney", voBean.getAllMoney());
				//进度
				member.put("progress", voBean.getProgress());
				//投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场
				//       62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
				member.put("betCategoty", voBean.getBetCategoty());
				jsonCurCanyuBetList.put(j,member);
			}
			//他发布的心水json列表
			JSONArray jsonCurPubXinshuiList = new JSONArray();
			for (int k = 0; k < curPubXinshuiList.size(); k++) {
				JSONObject member = new JSONObject();
				AttentionXinshuiVO voBean = (AttentionXinshuiVO)curPubXinshuiList.get(k);
				member.put("productId", voBean.getProductId());//c2c主键
				member.put("soldUserId", voBean.getSoldUserId());//民间高手用户ID
			    member.put("price", voBean.getPrice());//心水价格
				member.put("hostName", voBean.getHostName());//主队名称
				member.put("guestName", voBean.getGuestName());//客队名称
				member.put("xinshuiNo", voBean.getXinshuiNo());//心水编号
				member.put("raceName", voBean.getRaceName());//联赛名称
				member.put("soldUserName", voBean.getSoldUserName());//民间高手用户名
				member.put("startTime", voBean.getStartTime());//开赛时间
				member.put("ensureMoney", voBean.getEnsureMoney());// 缴纳的保证金
				member.put("soldCnt", voBean.getSoldCnt());//购买人数
				member.put("winRatio", voBean.getWinRatio());//最近5场胜率
				jsonCurPubXinshuiList.put(k,member);
			}
			//他参与的心水json列表
			JSONArray jsonCurBuyXinshuiList = new JSONArray();
			for (int l = 0; l < curBuyXinshuiList.size(); l++) {
				JSONObject member = new JSONObject();
				AttentionXinshuiVO voBean = (AttentionXinshuiVO)curBuyXinshuiList.get(l);
				member.put("productId", voBean.getProductId());//c2c主键
				member.put("soldUserId", voBean.getSoldUserId());//民间高手用户ID
			    member.put("price", voBean.getPrice());//心水价格
				member.put("hostName", voBean.getHostName());//主队名称
				member.put("guestName", voBean.getGuestName());//客队名称
				member.put("xinshuiNo", voBean.getXinshuiNo());//心水编号
				member.put("raceName", voBean.getRaceName());//联赛名称
				member.put("soldUserName", voBean.getSoldUserName());//民间高手用户名
				member.put("startTime", voBean.getStartTime());//开赛时间
				member.put("ensureMoney", voBean.getEnsureMoney());// 缴纳的保证金
				member.put("soldCnt", voBean.getSoldCnt());//购买人数
				member.put("winRatio", voBean.getWinRatio());//最近5场胜率
				jsonCurBuyXinshuiList.put(l,member);
			}
			//他的动态列json表
			JSONArray jsonDynaInfoList = new JSONArray();
			for (int m = 0; m < dynaInfoList.size(); m++) {
				JSONObject member = new JSONObject();
				DynaInfoVO voBean = (DynaInfoVO)dynaInfoList.get(m);
				//用户名
				member.put("username", voBean.getUsername());
				//发起人用户ID
				member.put("sponsorBetUserId", voBean.getSponsorBetUserId());
				//发起人用户名
				member.put("sponsorUsername", voBean.getSponsorUsername());
				//彩种
				member.put("betCategory", voBean.getBetCategory());
				//期次号码
				member.put("phaseNo", voBean.getPhaseNo());
				//方案总金额
				member.put("allMoney", voBean.getAllMoney());
				//投注时间
				member.put("betTime", voBean.getBetTime());
				jsonDynaInfoList.put(m,member);
			}
			
			json.put("curSponBetList",jsonCurSponBetList);
			json.put("curCanyuBetList",jsonCurCanyuBetList);
			json.put("curPubXinshuiList",jsonCurPubXinshuiList);
			json.put("curBuyXinshuiList",jsonCurBuyXinshuiList);
			json.put("dynaInfoList",jsonDynaInfoList);
			ajaxForAction(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 加载"当前投注"的页面内容
	 * 
	 * @return String
	 * */
	public String loadCurrentBetContent() {
		// 获取被查看用户的id
		personalId= request.getParameter("personalId");
		try {
			//查询当前投注
			Map indexMap= spaceService.findCurrentBet(Long.parseLong(personalId));
			//他发起的合买列表
			List curSponBetList = (List)indexMap.get("curSponBetList");
			//他参与的合买列表
			List curCanyuBetList = (List)indexMap.get("curCanyuBetList");
			/*查询 本站热门方案推荐*/
			List winDorw14List = spaceService.findHotPlanList("1");
			List optional9List = spaceService.findHotPlanList("2");
			List singleFieldFootballList = spaceService.findHotPlanList("6");
			//返回用户的json结果
			JSONObject json = new JSONObject(); 
			//他发起的合买json列表
			JSONArray jsonCurSponBetList = new JSONArray();
			for (int i = 0; i < curSponBetList.size(); i++) {
				JSONObject member = new JSONObject();
				AttentionPlanVO voBean = (AttentionPlanVO)curSponBetList.get(i);
				member.put("id", voBean.getId());
				member.put("lotteryCategory", voBean.getLotteryCategory());
				member.put("lotteryType", changeBetTpye(voBean.getLotteryType()));
				//期次号
				member.put("phaseNo", voBean.getPhaseNo());
				//发起人用户名
				member.put("sponsorUsername", voBean.getSponsorUsername());
				member.put("stars", voBean.getStars());//用户投注级别
				//方案总金额
				member.put("allMoney", voBean.getAllMoney());
				//进度
				member.put("progress", voBean.getProgress());
				//投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场
				//       62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
				member.put("betCategoty", voBean.getBetCategoty());
				jsonCurSponBetList.put(i,member);
			}
			//他参与的合买json列表
			JSONArray jsonCurCanyuBetList = new JSONArray();
			for (int j = 0; j < curCanyuBetList.size(); j++) {
				JSONObject member = new JSONObject();
				AttentionPlanVO voBean = (AttentionPlanVO)curCanyuBetList.get(j);
				member.put("id", voBean.getId());
				member.put("lotteryCategory", voBean.getLotteryCategory());
				member.put("lotteryType", changeBetTpye(voBean.getLotteryType()));
				//期次号
				member.put("phaseNo", voBean.getPhaseNo());
				//发起人用户名
				member.put("sponsorUsername", voBean.getSponsorUsername());
				//用户投注级别
				member.put("stars", voBean.getStars());
				//方案总金额
				member.put("allMoney", voBean.getAllMoney());
				//进度
				member.put("progress", voBean.getProgress());
				//投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场
				//       62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
				member.put("betCategoty", voBean.getBetCategoty());
				jsonCurCanyuBetList.put(j,member);
			}
			//本站热门方案推荐胜负14 json列表
			JSONArray jsonWinDorw14List = new JSONArray();
			for (int k = 0; k < winDorw14List.size(); k++) {
				JSONObject member = new JSONObject();
				AttentionPlanVO voBean = (AttentionPlanVO)winDorw14List.get(k);
				member.put("id", voBean.getId());
				member.put("lotteryCategory", voBean.getLotteryCategory());
				member.put("lotteryType", changeBetTpye(voBean.getLotteryType()));
				//期次号
				member.put("phaseNo", voBean.getPhaseNo());
				//发起人用户名
				member.put("sponsorUsername", voBean.getSponsorUsername());
				//用户投注级别
				member.put("stars", voBean.getStars());
				//方案总金额
				member.put("allMoney", voBean.getAllMoney());
				//进度
				member.put("progress", voBean.getProgress());
				//投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场
				//       62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
				member.put("betCategoty", voBean.getBetCategoty());
				jsonWinDorw14List.put(k,member);
			}
			//本站热门方案推荐任选9场json列表
			JSONArray jsonOptional9List = new JSONArray();
			for (int l = 0; l < optional9List.size(); l++) {
				JSONObject member = new JSONObject();
				AttentionPlanVO voBean = (AttentionPlanVO)optional9List.get(l);
				member.put("id", voBean.getId());
				member.put("lotteryCategory", voBean.getLotteryCategory());
				member.put("lotteryType", voBean.getLotteryType());
				//期次号
				member.put("phaseNo", voBean.getPhaseNo());
				//发起人用户名
				member.put("sponsorUsername", voBean.getSponsorUsername());
				//用户投注级别
				member.put("stars", voBean.getStars());
				//方案总金额
				member.put("allMoney", voBean.getAllMoney());
				//进度
				member.put("progress", voBean.getProgress());
				//投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场
				//       62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
				member.put("betCategoty", voBean.getBetCategoty());
				jsonOptional9List.put(l,member);
			}
			//本站热门方案推荐足球单场json列表
			JSONArray jsonSingleFieldFootballList = new JSONArray();
			for (int m = 0; m < singleFieldFootballList.size(); m++) {
				JSONObject member = new JSONObject();
				AttentionPlanVO voBean = (AttentionPlanVO)singleFieldFootballList.get(m);
				member.put("id", voBean.getId());
				member.put("lotteryCategory", voBean.getLotteryCategory());
				member.put("lotteryType", voBean.getLotteryType());
				//期次号
				member.put("phaseNo", voBean.getPhaseNo());
				//发起人用户名
				member.put("sponsorUsername", voBean.getSponsorUsername());
				//用户投注级别
				member.put("stars", voBean.getStars());
				//方案总金额
				member.put("allMoney", voBean.getAllMoney());
				//进度
				member.put("progress", voBean.getProgress());
				//投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场
				//       62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
				member.put("betCategoty", voBean.getBetCategoty());
				jsonSingleFieldFootballList.put(m,member);
			}
			json.put("curSponBetList",jsonCurSponBetList);
			json.put("curCanyuBetList",jsonCurCanyuBetList);
			json.put("winDorw14List", jsonWinDorw14List);
			json.put("optional9List", jsonOptional9List);
			json.put("singleFieldFootballList", jsonSingleFieldFootballList);
			ajaxForAction(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 加载"当前心水"的页面内容
	 * 
	 * @return String
	 * */
	public String loadCurrentXinShuiContent() {
		// 获取被查看用户的id
		personalId= request.getParameter("personalId");
		try {
			//查询当前心水
			Map indexMap= spaceService.findCurXinshui(Long.parseLong(personalId));
			//查询 本站热门心水推荐 
			List hotXinshuiList = spaceService.findHotXinshui(new HashMap());
			//他发布的心水列表
			List curPubXinshuiList = (List)indexMap.get("curPubXinshuiList");
			//他参与的心水列表
			List curBuyXinshuiList = (List)indexMap.get("curBuyXinshuiList");
			//返回用户的json结果
			JSONObject json = new JSONObject(); 
			//他发布的心水json列表
			JSONArray jsonCurPubXinshuiList = new JSONArray();
			for (int k = 0; k < curPubXinshuiList.size(); k++) {
				JSONObject member = new JSONObject();
				AttentionXinshuiVO voBean = (AttentionXinshuiVO)curPubXinshuiList.get(k);
				member.put("productId", voBean.getProductId());//c2c主键
				member.put("soldUserId", voBean.getSoldUserId());//民间高手用户ID
			    member.put("price", voBean.getPrice());//心水价格
				member.put("hostName", voBean.getHostName());//主队名称
				member.put("guestName", voBean.getGuestName());//客队名称
				member.put("xinshuiNo", voBean.getXinshuiNo());//心水编号
				member.put("raceName", voBean.getRaceName());//联赛名称
				member.put("soldUserName", voBean.getSoldUserName());//民间高手用户名
				member.put("startTime", voBean.getStartTime());//开赛时间
				member.put("ensureMoney", voBean.getEnsureMoney());// 缴纳的保证金
				member.put("soldCnt", voBean.getSoldCnt());//购买人数
				member.put("winRatio", voBean.getWinRatio());//最近5场胜率
				jsonCurPubXinshuiList.put(k,member);
			}
			//他参与的心水json列表
			JSONArray jsonCurBuyXinshuiList = new JSONArray();
			for (int l = 0; l < curBuyXinshuiList.size(); l++) {
				JSONObject member = new JSONObject();
				AttentionXinshuiVO voBean = (AttentionXinshuiVO)curBuyXinshuiList.get(l);
				member.put("productId", voBean.getProductId());//c2c主键
				member.put("soldUserId", voBean.getSoldUserId());//民间高手用户ID
			    member.put("price", voBean.getPrice());//心水价格
				member.put("hostName", voBean.getHostName());//主队名称
				member.put("guestName", voBean.getGuestName());//客队名称
				member.put("xinshuiNo", voBean.getXinshuiNo());//心水编号
				member.put("raceName", voBean.getRaceName());//联赛名称
				member.put("soldUserName", voBean.getSoldUserName());//民间高手用户名
				member.put("startTime", voBean.getStartTime());//开赛时间
				member.put("ensureMoney", voBean.getEnsureMoney());// 缴纳的保证金
				member.put("soldCnt", voBean.getSoldCnt());//购买人数
				member.put("winRatio", voBean.getWinRatio());//最近5场胜率
				jsonCurBuyXinshuiList.put(l,member);
			}
			JSONArray jsonHotXinshuiList = new JSONArray();
			for (int k = 0; k < hotXinshuiList.size(); k++) {
				JSONObject member = new JSONObject();
				AttentionXinshuiVO voBean = (AttentionXinshuiVO)hotXinshuiList.get(k);
				member.put("productId", voBean.getProductId());//c2c主键
				member.put("soldUserId", voBean.getSoldUserId());//民间高手用户ID
			    member.put("price", voBean.getPrice());//心水价格
				member.put("hostName", voBean.getHostName());//主队名称
				member.put("guestName", voBean.getGuestName());//客队名称
				member.put("xinshuiNo", voBean.getXinshuiNo());//心水编号
				member.put("raceName", voBean.getRaceName());//联赛名称
				member.put("soldUserName", voBean.getSoldUserName());//民间高手用户名
				member.put("startTime", voBean.getStartTime());//开赛时间
				member.put("ensureMoney", voBean.getEnsureMoney());// 缴纳的保证金
				member.put("soldCnt", voBean.getSoldCnt());//购买人数
				member.put("winRatio", voBean.getWinRatio());//最近5场胜率
				jsonHotXinshuiList.put(k,member);
			}
			json.put("curPubXinshuiList",jsonCurPubXinshuiList);
			json.put("curBuyXinshuiList",jsonCurBuyXinshuiList);
			json.put("hotXinshuiList",jsonHotXinshuiList);
			ajaxForAction(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 加载"历史战绩 合买"的页面内容
	 * 
	 * @return String
	 * */
	public String loadHistoryRecordHeMaiContent() {
		// 获取被查看用户的id
		personalId= request.getParameter("personalId");
		//
		String betCategory= request.getParameter("betCategory");
		String startRow= request.getParameter("startRow");
		String pageSize= request.getParameter("pageSize");
		/*合买历史战绩 全部彩种 参数赋值*/
		Map hemaiAll = new HashMap();
		hemaiAll.put("userId", Long.parseLong(personalId));
		//投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场
		//       62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
		if (!"".equals(betCategory) && betCategory !=null) {
			if ("0".equals(betCategory)) {
				betCategory = null;
			}
		}
		hemaiAll.put("betCategory", betCategory);
		hemaiAll.put("startRow", Integer.parseInt(startRow));
		hemaiAll.put("pageSize", Integer.parseInt(pageSize));
		//返回用户的json结果
		JSONObject json = new JSONObject(); 
		try {
//			spaceService.findHistoryXinshuiList(params);
			/*查询 合买历史战绩 全部彩种 */
			Map result = spaceService.findHistoryBetList(hemaiAll);
			List resultList = (List)result.get("resultList");
//			long totalCount = Long.parseLong(result.get("totalCount").toString());
			generateResult(1, MSG_SUCCESS, result);
//			//他参与的心水json列表
//			JSONArray jsonHistoryRecordHeMaiList = new JSONArray();
//			for (int l = 0; l < resultList.size(); l++) {
//				JSONObject member = new JSONObject();
//				BetOrderVO voBean = (BetOrderVO)resultList.get(l);
//				//主键
//				member.put("betId", voBean.getBetId());
//				//
//				member.put("type", voBean.getType());
//				//
//				member.put("betCategory", voBean.getBetCategory());
//				//
//				member.put("betUsername", voBean.getBetUsername());
//				//总金额
//				member.put("hitChangci", voBean.getHitChangci());
//				//;//奖金
//				member.put("bonus", voBean.getBonus());
//				//;//参与人数
//				member.put("partiCnt", voBean.getPartiCnt());
//				//购买人数
//				member.put("status", voBean.getStatus());
//				jsonHistoryRecordHeMaiList.put(l,member);
//			}
//			json.put("heMaiList",jsonHistoryRecordHeMaiList);
//			ajaxForAction(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 加载"历史战绩 心水"的页面内容
	 * 
	 * @return String
	 * */
	public String loadHistoryRecordXinShuiContent() {
		// 获取被查看用户的id
		personalId= request.getParameter("personalId");
		// 
		String betCategory= request.getParameter("betCategory");
		String startRow= request.getParameter("startRow");
		String pageSize= request.getParameter("pageSize");
		/*合买历史战绩 全部彩种 参数赋值*/
		Map hemaiAll = new HashMap();
		hemaiAll.put("userId", Long.parseLong(personalId));
		//投注彩种 1: 胜负14场 2:任9场 3:4场进球 5:6 场半全场 61:单场半全场
		//       62:单场比分 63:单场胜平负 64:单场上下单双 65:单场总进球
		if (!"".equals(betCategory) && betCategory !=null) {
			if ("0".equals(betCategory)) {
				betCategory = null;
			}
		}
		hemaiAll.put("betCategory", betCategory);
		hemaiAll.put("startRow", Integer.parseInt(startRow));
		hemaiAll.put("pageSize", Integer.parseInt(pageSize));
		//返回用户的json结果
		JSONObject json = new JSONObject(); 
		try {
//			spaceService.findHistoryXinshuiList(params);
			/*查询 心水历史记录 胜负14  */
			Map result = spaceService.findHistoryXinshuiList(hemaiAll);
			List resultList = (List)result.get("resultList");
			generateResult(1, MSG_SUCCESS, result);
//			//他参与的心水json列表
//			JSONArray jsonHistoryRecordHeMaiList = new JSONArray();
//			for (int l = 0; l < resultList.size(); l++) {
//				JSONObject member = new JSONObject();
//				XinshuiVO voBean = (XinshuiVO)resultList.get(l);
//				//联赛
//				member.put("raceName", voBean.getRaceName());
//				//开赛时间
//				member.put("startTime", voBean.getStartTime());
//				//主客对阵
//				member.put("hostAndGuest", voBean.getHostAndGuest());
//				//保证金
//				member.put("ensureMoney", voBean.getEnsureMoney());
//				///购买人数
//				member.put("buyCnt", voBean.getBuyCnt());
//				//价格
//				member.put("price", voBean.getPrice());
//				//结果
//				member.put("result", voBean.getResult());
//				jsonHistoryRecordHeMaiList.put(l,member);
//			}
//			json.put("heMaiList",jsonHistoryRecordHeMaiList);
//			ajaxForAction(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 加载"他关注的人" 或"关注他的人"的页面内容
	 * 
	 * @return String
	 * */
	public String loadAttentionPlanContent(){
		try {
			// 获取被查看用户的id
			personalId= request.getParameter("personalId");
			// 查询起始行
			String startRow= request.getParameter("startRow");
			// 每页行数
			String pageSize= request.getParameter("pageSize");
			Map params = new HashMap();
			params.put("userId", Long.parseLong(personalId));
			params.put("startRow", Integer.parseInt(startRow));
			params.put("pageSize", Integer.parseInt(pageSize));
			//取得他关注的人列表
			List resultList1 = myAttentionService
						.findMyAttentionUserList(params);
			//取得关注他的人列表
			List resultList2 = myAttentionService
						.findMyAttentionedUserList(params);
			//返回用户的json结果
			JSONObject json = new JSONObject(); 
			//他参与的心水json列表
			JSONArray jsonList1 = new JSONArray();
			for (int i = 0; i < resultList1.size(); i++) {
				JSONObject member = new JSONObject();
				AttentionUserVO voBean = (AttentionUserVO)resultList1.get(i);
				member.put("attentionId", voBean.getAttentionId());//主键
				member.put("username", voBean.getUsername());//用户名
				member.put("userId", voBean.getUserId());
				member.put("title", voBean.getTitle());//头衔
				member.put("betMilitary", Integer.toString(voBean.getBetMilitary()));//代购合买战绩
				member.put("xinshuiMilitary", voBean.getXinshuiMilitary());//心水战绩
				member.put("targetUserId", voBean.getTargetUserId());//被关在的人用户ID
				member.put("isAutoOrder", voBean.getIsAutoOrder());//是否定制自动跟单
				member.put("participateCnt", voBean.getParticipateCnt());//参与我的方案次数
				member.put("buyMyXinshuiCnt", voBean.getBuyMyXinshuiCnt());//购买我的心水次数
				member.put("attentionCnt", voBean.getAttentionCnt());//关注人数
				jsonList1.put(i,member);
			}
			//他参与的心水json列表
			JSONArray jsonList2 = new JSONArray();
			for (int j = 0; j < resultList2.size(); j++) {
				JSONObject member = new JSONObject();
				AttentionUserVO voBean = (AttentionUserVO)resultList2.get(j);
				member.put("attentionId", voBean.getAttentionId());//主键
				member.put("username", voBean.getUsername());//用户名
				member.put("userId", voBean.getUserId());
				member.put("title", voBean.getTitle());//头衔
				member.put("betMilitary", Integer.toString(voBean.getBetMilitary()));//代购合买战绩
				member.put("xinshuiMilitary", voBean.getXinshuiMilitary());//心水战绩
				member.put("targetUserId", voBean.getTargetUserId());//被关在的人用户ID
				member.put("isAutoOrder", voBean.getIsAutoOrder());//是否定制自动跟单
				member.put("participateCnt", voBean.getParticipateCnt());//参与我的方案次数
				member.put("buyMyXinshuiCnt", voBean.getBuyMyXinshuiCnt());//购买我的心水次数
				member.put("attentionCnt", voBean.getAttentionCnt());//关注人数
				jsonList2.put(j,member);
			}
			json.put("dataList1",jsonList1);
			json.put("dataList2",jsonList2);
			ajaxForAction(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 修改用会个性签名
	 * 
	 * @return String
	 * */
	public String ajaxModifySignatrue() {
		try {
			//获取用户要修改的个性签名内容
			signature = request.getParameter("signature");
			// 获取被查看用户的id
			personalId= request.getParameter("personalId");
			//返回用户的json结果
			JSONObject json = new JSONObject(); 
			if ((!"".equals(signature)&& signature!=null)&&
					(!"".equals(personalId)&& personalId!=null)) {
				signature = new String(signature.getBytes("ISO-8859-1"),"UTF-8");   
				if(spaceService.updateSignature(signature, Long.parseLong(personalId))){
					json.put("message", SUCCESS);
					json.put("signature", signature);
				} else {
					json.put("message", ERROR);
				}
			} else {
				json.put("message", INPUT);
			}
			ajaxForAction(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 *  投注方式 字段 替换
	 * 
	 * @return String
	 * */
	private String changeBetTpye(String tpye) {
		String result="";
		if (!"".equals(tpye) && tpye!=null) {
			int intType= Integer.parseInt(tpye);
			switch (intType) {
				case 1:
					result="单式代购";
					break;
				case 2:
					result="单式合买";
					break;
				case 3:
					result="复式代购";
					break;
				case 4:
					result="复式合买";
					break;
	
				default:
					break;
			}
		}
		return result;
	}
	public String execute() throws Exception {

		return SUCCESS;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SpaceVO getSpaceVO() {
		return spaceVO;
	}

	public void setSpaceVO(SpaceVO spaceVO) {
		this.spaceVO = spaceVO;
	}

	public boolean getIsSysUser() {
		return isSysUser;
	}

	public void setIsSysUser(boolean isSysUser) {
		this.isSysUser = isSysUser;
	}
	public String[] getHistoryRecord() {
		return historyRecord;
	}
	public void setHistoryRecord(String[] historyRecord) {
		this.historyRecord = historyRecord;
	}
	public String[] getBonusList() {
		return bonusList;
	}
	public void setBonusList(String[] bonusList) {
		this.bonusList = bonusList;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
}
