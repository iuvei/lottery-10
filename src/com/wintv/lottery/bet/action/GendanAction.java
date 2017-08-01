package com.wintv.lottery.bet.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.KingSponsorCategory;
import com.wintv.framework.pojo.MyAutoOrder;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.vo.MyAutoOrderVO;

/**
 * 自动跟单
 * 
 * @author weiss
 * 
 * @version 1.0.0
 */
public class GendanAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3971538358036480204L;

	/**
	 * 
	 */
	@Autowired
	private BetService betService;
	
	private String betCategory ;//玩法类型
	private String betCategoryList;//多种玩法字符串
	private String betType;//投注类型
	private String isFull;//是否满额
	private String kingUserName; //金牌发起人名称
	private Long kingUserId;
	private Integer pageSize = 30;// 页面显示数据条数
	private Integer startRow = 1;// 查询开始行
	private String planDesc="";
	private String autoOrderId;//自动跟单号
	private String isLackOrder;//认购金额不足是否认购
	private Long orderNum;//定制人序号(金牌发起人发起合买后，按照定制人序号依次自动跟单购买)
	private BigDecimal minMoney;//金额最小值
	private BigDecimal maxMoney;//金额最大值
	private BigDecimal txMoney; //认购金额
	private Long kingId;  //金牌发起人主键
	public String findKingList() {
		
		try {
			Map params = new HashMap();
			
			params.put("gameType", betCategory);
			
			params.put("betType", betType);
			params.put("isFull", isFull);
			params.put("kingUserName", kingUserName);
			
			params.put("startRow", startRow);
			
			params.put("pageSize", pageSize);
			
			List<KingSponsor> result = null; 
			result = betService.findKingList(params);//查询金牌发起人
			generateResult(1, MSG_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	public String loadAutoOrderDetail(){
	
		try {
			
			//UserCookie currentUser = (UserCookie) session.get("userCookie");
		    //Long UserId=currentUser.getUserId();
			
			
		    List<KingSponsor> result = new ArrayList<KingSponsor>(); 
		    KingSponsor kingDetail = betService.loadAutoOrder(kingUserId, betCategory,betType);
			result.add(kingDetail);//查询金牌发起人
			
			generateResult(1, MSG_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	/**我的跟单管理  2010-04-15
	 * 参数:
	 * commonUserId:普通用户ID
	 **/
	public String findMyAutoOrderList()
	{
		try {
			
			UserCookie currentUser = (UserCookie) session.get("userCookie");
		    Long UserId=currentUser.getUserId();
			
			
			List<MyAutoOrderVO> result = null; 
			result = betService.findMyAutoOrderList(UserId);//查询金牌发起人
			generateResult(1, MSG_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	//**金牌发起人管理  2010-04-15 **/
	public String findKingCategory() {
		
		try {
			
			UserCookie currentUser = (UserCookie) session.get("userCookie");
		    Long UserId=currentUser.getUserId();
			
			
			List<KingSponsor> result = null; 
			result = betService.findKingCategory(UserId);//查询金牌发起人
			generateResult(1, MSG_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	//**金牌发起人资格审核管理  2010-04-15 **/
	public String findKingCategoryAudit() {
		
		try {
			
			UserCookie currentUser = (UserCookie) session.get("userCookie");
		    Long UserId=currentUser.getUserId();
			
			
			List<KingSponsor> result = null; 
			result = betService.findKingCategoryAudit(UserId);//查询金牌发起人
			generateResult(1, MSG_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	public String excute() {
		return SUCCESS;
	}
	/**
	 * 后台参数
	 */
	private Map<String, Object> generateParamsMapAuto() {
		UserCookie currentUser = (UserCookie) session.get("userCookie");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("kingUserId", this.getKingUserId());
		queryParams.put("betCategory", this.getBetCategory());
		queryParams.put("type", this.getBetType());
		queryParams.put("orderNum", this.getOrderNum());
		queryParams.put("minMoney", this.getMinMoney());
		queryParams.put("maxMoney", this.getMaxMoney());
		queryParams.put("txMoney", this.getTxMoney());
		queryParams.put("isLackOrder", this.getIsLackOrder());
		
		if (isNotNull(currentUser)) {
			queryParams.put("userid", currentUser.getUserId());
			queryParams.put("username", currentUser.getUsername());
		}
		return queryParams;
	}
	/*private <MyAutoOrder> generateAutoOrder(){
		MyAutoOrder po = new MyAutoOrder();
		return po;
	}*/
	private Map<String, Object> generateParamsMapKing() {
		UserCookie currentUser = (UserCookie) session.get("userCookie");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		
		queryParams.put("betCategory", this.getBetCategory());
		
		queryParams.put("type", this.getBetType());
		queryParams.put("planDesc", this.getPlanDesc());
		
		if (isNotNull(currentUser)) {
			queryParams.put("betUserid", currentUser.getUserId());
			queryParams.put("betUsername", currentUser.getUsername());
		}
		return queryParams;
	}
	/**
	 * 自动跟单 保存
	 * 
	 * @return  1:投注成功 -1:系统报错,不成功 4:余额不足 ，但仍然能投注 5:账户锁定
	 */
	public String saveAutoOrder() {
		try {
			//Map params = generateParamsMapAuto();
			//初始化
			MyAutoOrder po = new MyAutoOrder();
			po.setKingUserId(kingUserId);
			po.setCategory(betCategory);
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser)) {
				po.setUserid(currentUser.getUserId());
				po.setUsername(currentUser.getUsername());
				
			}
			po.setAutoOrderId(orderNum);
			po.setType(betType);
			po.setIsLackOrder(isLackOrder);
			po.setMaxMoney(maxMoney);
			po.setMinMoney(minMoney);
			po.setTxMoney(txMoney);
			po.setStatus("1");
			po.setKingId(kingId);
			
			
			//---------------------------------
			long result = betService.saveMyAutoOrder(po);
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
	 * 自动跟单 金牌申请发起人是否已存在
	 * 
	 * @return  1:投注成功 -1:系统报错,不成功 
	 */
	/*public String isAlreadyApply(String betCategory,String type){
		
	}*/
	/**
	 * 自动跟单 保存金牌申请发起人
	 * 
	 * @return  1:投注成功 -1:系统报错,不成功 
	 */
	public String saveKingSponsor() {
		try {
			//Map params = generateParamsMapKing();
			//kingsponsor类初始化函数
			KingSponsor po = new KingSponsor();
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser)) {
				po.setUserid(currentUser.getUserId());
				po.setUsername(currentUser.getUsername());
				
			}
			po.setZhDesc(this.getPlanDesc());
			po.setBetCategory(this.getBetCategory());
			boolean blResult = true;
			String[] lstBetCategory = betType.split("\\,");		
			for (int i = 0; i < lstBetCategory.length; i++) 
			{
				
				
				if (!betService.isAlreadyApply(currentUser.getUserId(), betCategory, lstBetCategory[i])){
					blResult = false;
				};
				
			}
			if (blResult)
			{	
				for (int i = 0; i < lstBetCategory.length; i++) 
				{
					po.setType(lstBetCategory[i]);
					long result = betService.saveKingSponsor(po);
					if (isNotNull(result)) {
						generateResult(1, MSG_SUCCESS, result);
					} else {
						generateResult(0, MSG_FAILURE, "errors");
					}
				}
			}else
			{
				return "2";
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	//我要跟单列表
	public String findAutoOrderList() {
		
		try {
			Map params = new HashMap();
			
			params.put("kingUserId", kingUserId);
			
			params.put("betCategory", betCategory);
			
			List<MyAutoOrderVO> result = null; 
			result = betService.findAutoOrderListByCategory(params);//查询金牌发起人
			generateResult(1, MSG_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public String getBetCategory() {
		return betCategory;
	}

	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}

	public Long getKingUserId() {
		return kingUserId;
	}

	public void setKingUserId(Long kingUserId) {
		this.kingUserId = kingUserId;
	}
	public Long getKingId() {
		return kingId;
	}

	public void setKingId(Long kingId) {
		this.kingId = kingId;
	}
	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}
	public String getPlanDesc() {
		return planDesc;
	}

	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}
	
	public String getBetCategoryList() {
		return betCategoryList;
	}

	public void setBetCategoryList(String betCategoryList) {
		this.betCategoryList = betCategoryList;
	}
	public String getIsLackOrder() {
		return isLackOrder;
	}
	public void setIsLackOrder(String isLackOrder) {
		this.isLackOrder = isLackOrder;
	}
	public String GetAutoOrderId() {
		return autoOrderId;
	}
	public void setAutoOrderId(String autoOrderId) {
		this.autoOrderId = autoOrderId;
	}
	public Long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	public BigDecimal getMinMoney() {
		return minMoney;
	}
	public void setMinMoney(BigDecimal minMoney) {
		this.minMoney = minMoney;
	}
	public BigDecimal getMaxMoney() {
		return maxMoney;
	}
	public void setMaxMoney(BigDecimal maxMoney) {
		this.maxMoney = maxMoney;
	}
	public BigDecimal getTxMoney() {
		return txMoney;
	}
	public void setTxMoney(BigDecimal txMoney) {
		this.txMoney = txMoney;
	}
	
}
