package com.wintv.lottery.admin.pay.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.Bank;
import com.wintv.lottery.pay.service.PayService;
/**
 * 银行后台管理
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class BankAdminAction extends BaseAction {
	private static final long serialVersionUID = 24606779797006824L;
	private String checkedIds;
	private String status;
	private Integer bankType=0;
	@Autowired
	private PayService payService;

	public String excute() {
		return SUCCESS;
	}
	/**
	 * 获取所有银行
	 * @return
	 */
	public String findAllBank(){
		try {
			List<Bank> result = null;
				result = payService.findAllBank();
			if (null == result) {
				generateResult(0, MSG_FAILURE, "errors");
			} else {
				generateResult(1, MSG_SUCCESS, result);
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}

		return SUCCESS;
	}
	/**
	 * 更改银行状态
	 * 
	 * checkedIds 银行Id
	 * @param status
	 *            '1':可用  '2':不可用
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public String updateBankStatus(){
		try {
			Integer result = null;
			if (isNotNull(this.getCheckedIds(),this.getStatus(),this.getBankType())) {
				result = payService.updateBankStatus(this.getCheckedIds(), this.getStatus(), this.getBankType());
			}
			if (null == result) {
				generateResult(0, MSG_FAILURE, "errors");
			} else {
				generateResult(1, MSG_SUCCESS, result);
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			throw new LotteryBizException(e.getLocalizedMessage());
		}

		return SUCCESS;
	}
	public String getCheckedIds() {
		return checkedIds;
	}
	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getBankType() {
		return bankType;
	}
	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}
	
}
