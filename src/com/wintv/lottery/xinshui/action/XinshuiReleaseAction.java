package com.wintv.lottery.xinshui.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.C2CProduct;
import com.wintv.framework.pojo.Race;
import com.wintv.lottery.admin.team.service.RaceService;
import com.wintv.lottery.xinshui.service.XinshuiService;
import com.wintv.lottery.xinshui.vo.XinshuiAgainstVO;

@SuppressWarnings("unchecked")
public class XinshuiReleaseAction extends BaseAction {
	private static final long serialVersionUID = 7752266952619983976L;
	// 赛事选择查询参数
	private Long raceId = 0L;// 联赛/杯赛名称ID
	private String orderFiled;// 排序字段
	private String orderType;// 排序类型'ASC'|'DESC'
	
	private Long againstId;//竞猜赛程ID 关联到表T_XINSHUI_AGAINST
	private String xinshuiDesc="sdb";//心水说明
	private BigDecimal ensureMoney;//缴纳的保证金
	private String type;//'1':亚盘   '2':大小盘
	private String selectType;//选主, 1：选主  2：选客 3：选大 4：选小
	
	private String aindex;// 主队或者大球指数
	private String bindex;// 客队或者小球指数
	private String panKouIndex;// 盘口
	
	private String flg;//根据flag值查询相应的 1.

	@Autowired
	private XinshuiService xinshuiService;
	@Autowired
	private RaceService raceService;
	
	/**
	 * 判断当前用户是否有权限发布心水
	 * 
	 */
	public String isCanReleaseXinshui() {
		try {
			UserCookie currentUser = (UserCookie)session.get("userCookie");
			if (isNotNull(currentUser)) {
				Boolean result = xinshuiService.isAllowPubXinshui(currentUser.getUserId());
				if (isNotNull(result)) {
					generateResult(1, MSG_SUCCESS, result);
				} else {
					generateResult(0, MSG_FAILURE, "errors");
				}
			} else {
				generateResult(-1, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 获取所有联赛名称
	 * 
	 */
	public String getAllRaces(){
		try {
			List<Race> result = null;
			if (isNotNull(flg)) {
				Map<String,Object> params=new HashMap<String,Object>();
				if(!flg.equals("2")&&!flg.equals("5")){
					UserCookie currentUser = (UserCookie)session.get("userCookie");
					if(null!=currentUser){
						params.put("userId",currentUser.getUserId());
					}
				}
				params.put("flg", flg);
				result = raceService.findSiteRaceList(params);
				if (isNotNull(result)) {
					generateResult(1, MSG_SUCCESS, result);
				} else {
					generateResult(0, MSG_FAILURE, "errors");
				}
			}else{
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
   
	/**
	 * 获取选择赛事列表
	 * 
	 */
	public String getChoiceRacesList() {
		try {
			Map params = generateQueryParamsMap();
			List result = xinshuiService.findAgainstList(params);
			if (isNotNull(result)) {
				generateResult(1, MSG_SUCCESS, result);
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
  
	/**
	 * 根据赛事ID获取赛事信息
	 * 
	 */
	public String getAgainstInfoById() {
		try {
			Long againstId=this.getAgainstId();
			if (isNotNull(againstId)) {
				XinshuiAgainstVO result=xinshuiService.loadXinshuiAgainst(againstId);
				if (isNotNull(result)) {
					generateResult(1, MSG_SUCCESS, result);
				} else {
					generateResult(0, MSG_FAILURE, "errors");
				}
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 发布心水操作
	 *  return "-1";//保证金太小  return "0";//保证金太大	return "-2";//没有权限	  
	 */
	public String saveXinshui() {
		try {
			XinshuiAgainstVO xinshuiAgainstVO=xinshuiService.loadXinshuiAgainst(this.getAgainstId());
			C2CProduct po = generateXinshuiVo(xinshuiAgainstVO); 
			String result=xinshuiService.saveC2CProduct(po);
			if (isNotNull(result)) {
				generateResult(1, MSG_SUCCESS, result.trim());
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**
	 * 拼装心水信息
	 */
	private C2CProduct generateXinshuiVo(XinshuiAgainstVO xinshuiAgainst) {
		UserCookie currentUser = (UserCookie)session.get("userCookie");
		C2CProduct vo = new C2CProduct();
		vo.setAgainstId(this.getAgainstId());
		vo.setGuestName(xinshuiAgainst.getGuestName());
		vo.setHostName(xinshuiAgainst.getHostName());
		vo.setRaceId(xinshuiAgainst.getRaceId());
		vo.setRaceName(xinshuiAgainst.getRaceName());
		vo.setSelectType(this.getSelectType());//选主 1：选主  2：选客 3：选大 4：选小
		vo.setPubTime(new Date());
		vo.setTxUserId(currentUser.getUserId());
		vo.setTxUsername(currentUser.getUsername());
		vo.setEnsureMoney(this.getEnsureMoney());
		vo.setType(this.getType());//'1':亚盘   '2':大小盘
		vo.setZhDesc(this.getXinshuiDesc());
		vo.setStatus("1");//心水状态: '1':'发布中','2':'赢','3':'负','4':'走','5':'已关闭'
		vo.setPanKouIndex(this.getPanKouIndex());
		vo.setAindex(this.getAindex());
		vo.setBindex(this.getBindex());
		return vo;
	}
	/**
	 * 后台心水赛事页面查询参数
	 */
	private Map<String, Object> generateQueryParamsMap() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		if (isNotNull(this.getRaceId()) && this.getRaceId() != -1L) {
			queryParams.put("raceId", this.getRaceId());
		}
		if (isNotNull(this.getOrderFiled(), this.getOrderType())) {
			queryParams.put("sort", this.getOrderFiled() + " "
					+ this.getOrderType() + " ");
		}
		return queryParams;
	}

	public Long getRaceId() {
		return raceId;
	}

	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}

	public String getOrderFiled() {
		return orderFiled;
	}

	public void setOrderFiled(String orderFiled) {
		this.orderFiled = orderFiled;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getXinshuiDesc() {
		return xinshuiDesc;
	}

	public void setXinshuiDesc(String xinshuiDesc) {
		this.xinshuiDesc = xinshuiDesc;
	}

	public BigDecimal getEnsureMoney() {
		return ensureMoney;
	}

	public void setEnsureMoney(BigDecimal ensureMoney) {
		this.ensureMoney = ensureMoney;
	}

	public Long getAgainstId() {
		return againstId;
	}

	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getAindex() {
		return aindex;
	}

	public void setAindex(String aindex) {
		this.aindex = aindex;
	}

	public String getBindex() {
		return bindex;
	}

	public void setBindex(String bindex) {
		this.bindex = bindex;
	}

	public String getPanKouIndex() {
		return panKouIndex;
	}

	public void setPanKouIndex(String panKouIndex) {
		this.panKouIndex = panKouIndex;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}
	
}
