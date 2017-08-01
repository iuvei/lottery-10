package com.wintv.lottery.attention.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.MyAttention;
import com.wintv.lottery.attention.service.MyAttentionService;

/**
 * 我的关注模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
public class MyAttentionAction extends BaseAction {
	private static final long serialVersionUID = 7946861952522782222L;
	@Autowired
	private MyAttentionService myAttentionService;
	private Long userId;//用户Id 注：主要用于用户个人中心模块

	private String orderFiled;// 排序字段
	private String orderType;// 排序类型'ASC'|'DESC'

	private Integer pageSize = 10;// 页面显示数据条数
	private Integer startRow = 0;// 查询开始行
	private Integer isCount = 0;// 是否统计标志 1.统计 0.不统计
	private String attentionIdList;// 关注Id字符串，多个用逗号隔开
	
	private Long targetUserId;//目标用户Id

	public String excute() {
		return SUCCESS;
	}

	/**
	 * 获取我关注的方案列表
	 */
	public String getMyAttentionPlanList() {
		try {
			Map params = generateQueryParamsMap();
			// 判断用户是否已登录
			Boolean isLogin = (Boolean) params.get("isLogin");
			if (isLogin) {// 用户登录了执行保存操作
				Integer total = 0;
				if (this.isCount == 1) {
					total = myAttentionService.findMyAttentionPlanSize(params);
				}
				List dataList = myAttentionService
						.findMyAttentionPlanList(params);
				if (isNotNull(dataList, total)) {
					Map result = new HashMap();
					result.put("totalCount", total);
					result.put("dataList", dataList);
					generateResult(1, MSG_SUCCESS, result);
				} else {
					generateResult(0, MSG_FAILURE, "errors");
				}
			} else {
				generateResult(0, MSG_FAILURE, "尚未登录");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 获取我关注的人列表
	 */
	public String getMyAttentionPeopleList() {
		try {
			Map params = generateQueryParamsMap();
			// 判断用户是否已登录
			Boolean isLogin = (Boolean) params.get("isLogin");
			if (isLogin) {// 用户登录了执行保存操作
				Integer total = 0;
				if (this.isCount == 1) {
					total = myAttentionService.findMyAttentionUserSize(params);
				}
				List dataList = myAttentionService
						.findMyAttentionUserList(params);
				if (isNotNull(dataList, total)) {
					Map result = new HashMap();
					result.put("totalCount", total);
					result.put("dataList", dataList);
					generateResult(1, MSG_SUCCESS, result);
				} else {
					generateResult(0, MSG_FAILURE, "errors");
				}
			} else {
				generateResult(0, MSG_FAILURE, -1);
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 获取关注我的人列表
	 */
	public String getAttentionMePeopleList() {
		try {
			Map params = generateQueryParamsMap();
			// 判断用户是否已登录
			Boolean isLogin = (Boolean) params.get("isLogin");
			if (isLogin) {// 用户登录了执行保存操作
				Integer total = 0;
				if (this.isCount == 1) {
					total = myAttentionService
							.findMyAttentionedUserSize(params);
				}
				List dataList = myAttentionService
						.findMyAttentionedUserList(params);
				if (isNotNull(dataList, total)) {
					Map result = new HashMap();
					result.put("totalCount", total);
					result.put("dataList", dataList);
					generateResult(1, MSG_SUCCESS, result);
				} else {
					generateResult(0, MSG_FAILURE, "errors");
				}
			} else {
				generateResult(0, MSG_FAILURE, "尚未登录");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 获取关注度最高的50人列表
	 */
	public String getAttentionTop50List() {
		try {
			Map params = generateQueryParamsMap();
			// 判断用户是否已登录
			Boolean isLogin = (Boolean) params.get("isLogin");
			if (isLogin) {// 用户登录了执行保存操作
				Integer total = 0;
				if (this.isCount == 1) {
					total = myAttentionService.findMyAttentionTop50Size(params);
				}
				List dataList = myAttentionService
						.findMyAttentionTop50List(params);
				if (isNotNull(dataList, total)) {
					Map result = new HashMap();
					result.put("totalCount", total);
					result.put("dataList", dataList);
					generateResult(1, MSG_SUCCESS, result);
				} else {
					generateResult(0, MSG_FAILURE, "errors");
				}
			} else {
				generateResult(0, MSG_FAILURE, "尚未登录");
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}

	/**
	 * 取消关注
	 */
	public String cancelAttention() {
		try {
			if (isNotNull(attentionIdList)&&!attentionIdList.equals("")) {
			boolean result = myAttentionService.cancelAttention(attentionIdList.trim());
			if (result) {
					generateResult(1, MSG_SUCCESS, "ok");
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
	 * 产生我的关注
	 * rs.code -1=当前用户不能关注自己 0=系统错误 1=关注成功 -2=已经关注过
	 */
	public String createAttention() {
		try {
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser,targetUserId)) {
				Long curUserId=currentUser.getUserId();
				if(curUserId.equals(targetUserId)){//当前用户不能关注自己的
					generateResult(-1, MSG_FAILURE, -1);
				}else{
					MyAttention po= new MyAttention();
					po.setUserId(curUserId);
					po.setTargetUserId(targetUserId);
					po.setUsername(currentUser.getUsername());
					Long result = myAttentionService.createAttention(po);
					if (isNotNull(result)&&result>0L) {
						generateResult(1, MSG_SUCCESS, result);
					} else {
						if(result.equals(-2L)){//已经关注过
							generateResult(-2, MSG_FAILURE, result);
						}else{
							generateResult(0, MSG_FAILURE, result);
						}
					}
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
	 * 生成查询参数
	 */
	private Map<String, Object> generateQueryParamsMap() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		if(isNotNull(this.getUserId())){//用户个人中心模块 直接传userId过来
			queryParams.put("userId", this.getUserId());
			queryParams.put("isLogin", true);
		}else{//我的关注模块 从session中拿userId
			UserCookie currentUser = (UserCookie) session.get("userCookie");
			if (isNotNull(currentUser)) {
				queryParams.put("userId", currentUser.getUserId());
				queryParams.put("isLogin", true);
			} else {
				queryParams.put("isLogin", false);
			}
		}
		if (isNotNull(this.getPageSize(), this.getStartRow())) {
			queryParams.put("startRow", this.getStartRow());
			queryParams.put("pageSize", this.getPageSize());
		}
		return queryParams;
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

	public Integer getIsCount() {
		return isCount;
	}

	public void setIsCount(Integer isCount) {
		this.isCount = isCount;
	}

	public String getAttentionIdList() {
		return attentionIdList;
	}

	public void setAttentionIdList(String attentionIdList) {
		this.attentionIdList = attentionIdList;
	}

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
