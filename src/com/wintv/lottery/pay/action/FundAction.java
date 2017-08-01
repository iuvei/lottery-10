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
import com.wintv.lottery.pay.vo.MoneyDetailVo;

@SuppressWarnings("unchecked")
public class FundAction extends BaseAction {
	private static final long serialVersionUID = 2588391222490035250L;

	@Autowired
	private PayService payService;
	
	private Integer pageSize = 20;// 页面显示数据条数
	private Integer startRow = 1;// 查询开始行
	private Long isCount = 0L;// 是否统计标志 1.统计 0.不统计
	private String fundBeginTime;// 取款时间查询区间开始时间 格式:"yyyy-MM-dd"
	private String fundEndTime;// 取款时间查询区间结束时间 格式:"yyyy-MM-dd"
	private String txType = "0";// 交易类型：0.全部类型
								//1.账户充值 2.账户取款 3.中奖奖金 4.购买彩票 
								//5.心水收入 6.购买心水 7.心水补偿 8.保证金扣除
	/**
	 * 查询我的资金明细
	 * @return
	 */
	public String searchFundList() {
		try {
			Map params = this.getParams();
			
			Long totalCount = 0L;//总记录数
			Long incomeCount = 0L;//收入交易笔数
			Long expendCount = 0L;//支出交易笔数
			BigDecimal incomeSum = new BigDecimal(0);//收入金额合计
			BigDecimal expendSum = new BigDecimal(0);//支出金额合计
			 
			if (this.isCount == 1) {
				totalCount = payService.findMoneyDetailListCount(params);
			}
			
			Map incomeMap = payService.findMoneyIncomeSumCount(params);
			Map expendMap = payService.findMoneyExpendSumCount(params);
			
			if(incomeMap != null) {
				incomeCount = (Long)incomeMap.get("moneyIncomeCount");
				incomeSum = (BigDecimal)incomeMap.get("moneyIncomeSum");
			}
			if(expendMap != null) {
				expendCount = (Long)expendMap.get("moneyExpendCount");
				expendSum = (BigDecimal)expendMap.get("moneyExpendSum");
			}
			
			//查询资金明细
			List<MoneyDetailVo> moneyDetailList = payService.findMoneyDetailList(params, startRow, pageSize);
			
			if (isNotNull(moneyDetailList, totalCount, incomeCount, incomeSum, expendCount, expendSum)) {
				Map result = new HashMap();
				result.put("totalCount", totalCount);
				result.put("incomeCount", incomeCount);
				result.put("incomeSum", incomeSum);
				result.put("expendCount", expendCount);
				result.put("expendSum", expendSum);
				result.put("moneyDetailList", moneyDetailList);

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
		
		if(!StringUtils.isEmpty(fundBeginTime)) {
			params.put("startDay", DateUtil.parseDate(this.fundBeginTime));
		}
		if(!StringUtils.isEmpty(fundEndTime)) {
			params.put("endDay",  DateUtil.parseDate(this.fundEndTime));		
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

	public String getFundBeginTime() {
		return fundBeginTime;
	}

	public void setFundBeginTime(String fundBeginTime) {
		this.fundBeginTime = fundBeginTime;
	}

	public String getFundEndTime() {
		return fundEndTime;
	}

	public void setFundEndTime(String fundEndTime) {
		this.fundEndTime = fundEndTime;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}
}
