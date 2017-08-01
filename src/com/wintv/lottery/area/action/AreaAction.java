package com.wintv.lottery.area.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.wintv.lottery.area.service.AreaService;
import com.wintv.framework.common.BaseAction;
import com.wintv.lottery.area.vo.AreaVo;

/**
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public class AreaAction extends BaseAction {
	private static final long serialVersionUID = 48153536117786634L;
	@Autowired
	private AreaService areaService;
	private String code;// 区域id
	// 根据父级id获取该父级所有下属地区列表
	public String getAreaList() {
		List<AreaVo> result = null;
		if (null != code) {
			result = areaService.getAreaByParentCode(code);
		}
		if (null == result) {
			generateResult(0, MSG_FAILURE, "errors");
		} else {
			generateResult(1, MSG_SUCCESS, result);
		}
		return SUCCESS;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
