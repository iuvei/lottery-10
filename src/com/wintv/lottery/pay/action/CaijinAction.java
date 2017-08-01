package com.wintv.lottery.pay.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.utils.DateUtil;
import com.wintv.lottery.pay.service.PayService;
import com.wintv.lottery.pay.vo.MosaicGoldVo;

@SuppressWarnings("unchecked")
public class CaijinAction extends BaseAction {
	private static final long serialVersionUID = -5253000382767221213L;

	@Autowired
	private PayService payService;
	
	private Integer pageSize = 20;// 页面显示数据条数
	private Integer startRow = 1;// 查询开始行
	private Long isCount = 0L;// 是否统计标志 1.统计 0.不统计
	private String caijinBeginTime;// 取款时间查询区间开始时间 格式:"yyyy-MM-dd"
	private String caijinEndTime;// 取款时间查询区间结束时间 格式:"yyyy-MM-dd"
	private String txType = "0";// 交易类型：0.全部类型
								//9.彩金赠送 4.购买彩票 6.购买心水
								
	/**
	 * 查询我的彩金明细
	 * @return
	 */
	public String searchCaijinList() {
		try {
			Map params = this.getParams();
			
			Long totalCount = 0L;//总记录数
			Long incomeCount = 0L;//收入交易笔数
			Long expendCount = 0L;//支出交易笔数
			BigDecimal incomeSum = new BigDecimal(0);//收入金额合计
			BigDecimal expendSum = new BigDecimal(0);//支出金额合计
			 
			if (this.isCount == 1) {
				totalCount = payService.findMosaicGoldListCount(params);
			}
			
			Map incomeMap = payService.findMosaicGoldIncomeSumCount(params);
			Map expendMap = payService.findMosaicGoldExpendSumCount(params);
			
			if(incomeMap != null) {
				incomeCount = (Long)incomeMap.get("mosaicGoldIncomeCount");
				incomeSum = (BigDecimal)incomeMap.get("mosaicGoldIncomeSum");
			}
			if(expendMap != null) {
				expendCount = (Long)expendMap.get("mosaicGoldExpendCount");
				expendSum = (BigDecimal)expendMap.get("mosaicGoldExpendSum");
			}
			
			//查询资金明细
			List<MosaicGoldVo> caijinList = payService.findMosaicGoldList(params, startRow, pageSize);
			
			if (isNotNull(caijinList, totalCount, incomeCount, incomeSum, expendCount, expendSum)) {
				Map result = new HashMap();
				result.put("totalCount", totalCount);
				result.put("incomeCount", incomeCount);
				result.put("incomeSum", incomeSum);
				result.put("expendCount", expendCount);
				result.put("expendSum", expendSum);
				result.put("caijinList", caijinList);

				generateResult(1, MSG_SUCCESS, result);
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 获取页面参数
	 * @return
	 */
	public Map getParams() {
		Map params = new HashMap();
		Long userid = ((UserCookie) request.getSession().getAttribute("userCookie")).getUserId();
		params.put("userid", userid);
		
		if(!StringUtils.isEmpty(caijinBeginTime)) {
			params.put("startDay", DateUtil.parseDate(this.caijinBeginTime));
		}
		if(!StringUtils.isEmpty(caijinEndTime)) {
			params.put("endDay",  DateUtil.parseDate(this.caijinEndTime));		
		}
		
		if(isNotNull(txType)) {
			if(!"0".equals(txType)) {
				params.put("transactionType", txType);
			}
		}
		
		return params;
	}

	/**----------------------------------------get&set----------------------------------------**/
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

	public Long getIsCount() {
		return isCount;
	}

	public void setIsCount(Long isCount) {
		this.isCount = isCount;
	}

	public String getCaijinBeginTime() {
		return caijinBeginTime;
	}

	public void setCaijinBeginTime(String caijinBeginTime) {
		this.caijinBeginTime = caijinBeginTime;
	}

	public String getCaijinEndTime() {
		return caijinEndTime;
	}

	public void setCaijinEndTime(String caijinEndTime) {
		this.caijinEndTime = caijinEndTime;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}
}
