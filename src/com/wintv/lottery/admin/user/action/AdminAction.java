package com.wintv.lottery.admin.user.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.pojo.BackendUser;
import com.wintv.framework.pojo.Dictionary;
import com.wintv.lottery.admin.user.service.AdminService;
import com.wintv.lottery.pay.service.PayService;

/**
 * 后台管理员管理模块
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class AdminAction extends BaseAction {
	private static final long serialVersionUID = 2900619385700438512L;
	private String role = "2";
	private String deptCode = "";// 部门code
	private String filed = "";
	private String filedValue = "";
	private Long adminId = 164L;
	@Autowired
	private AdminService adminService;
	@Autowired
	private PayService payService;

	public String excute() {
		return SUCCESS;
	}

	public String index() {
		BackendUser admin = adminService.getAdminByUserId(adminId);
		session.put("admin", admin);
		return SUCCESS;
	}

	public String top() {
		return SUCCESS;
	}

	public String left() {
		return SUCCESS;
	}

	/**
	 * 根据角色获取相应管理用户列表
	 */
	public String getAdminUsersByRole() {
		String currentRole = this.getRole();
		if (null == currentRole) {
			generateResult(0, MSG_FAILURE, "errors");
			return SUCCESS;
		}
		List<BackendUser> result = adminService.getAdminUsersByRole(this
				.getRole());
		if (isNotNull(result)) {
			generateResult(1, MSG_SUCCESS, result);
		} else {
			generateResult(0, MSG_FAILURE, "errors");
		}
		return SUCCESS;
	}

	/**
	 * 根据查询字段名与值获取相应管理用户列表
	 * 
	 * @return
	 */
	public String getAdminUsersByQueryFiled() {
		if (isNotNull(this.getFiled(), this.getFiledValue())) {
			List<BackendUser> result = adminService.getAdminUsersByQueryFiled(
					this.filed, this.filedValue);
			if (isNotNull(result)) {
				generateResult(1, MSG_SUCCESS, result);
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} else {
			generateResult(0, MSG_FAILURE, "errors");
		}
		return SUCCESS;
	}
	/**
	 * 获取部门下面的用户列表
	 */
	public String getDeptsBackendUsers() {
		try {
			if (isNotNull(this.getDeptCode())) {
				List<BackendUser> result = adminService
						.getAdminUsersByQueryFiled("deptCode", this
								.getDeptCode());
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
	 * 获取后台用户部门列表
	 */
	public String getBackendUserDepts() {
		try {
			List<Dictionary> result = payService
					.getDictionaryListByType("DEPT");
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getFiledValue() {
		return filedValue;
	}

	public void setFiledValue(String filedValue) {
		this.filedValue = filedValue;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

}
