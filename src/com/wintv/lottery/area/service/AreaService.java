package com.wintv.lottery.area.service;

import java.util.List;
import com.wintv.lottery.area.vo.AreaVo;
/**
 * 
 * @author Hikin Yao
 *
 * @version 1.0.0
 */
public interface AreaService {
	//根据父级code获取该父级所有下属地区列表
	public List<AreaVo> getAreaByParentCode(String parentCode);
}
