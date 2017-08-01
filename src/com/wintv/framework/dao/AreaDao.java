package com.wintv.framework.dao;

import java.util.List;

import com.wintv.framework.pojo.Code;
import com.wintv.lottery.area.vo.AreaVo;

public interface AreaDao extends BaseDao<Code, Long> {

	List<Code> findAllProvince();

	Code findProvinceById(Long provId);

	List<Code> findCityByProvID(Long provId);

	Code findCityById(Long cityId);

	/**
	 * 根据父级code获取该父级所有下属地区列表
	 * 
	 * @author hikin yao
	 */
	public List<AreaVo> getAreaByParentCode(String parentCode);
}
