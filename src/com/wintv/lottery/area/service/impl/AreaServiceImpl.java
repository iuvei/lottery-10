package com.wintv.lottery.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wintv.framework.dao.AreaDao;
import com.wintv.lottery.area.service.AreaService;
import com.wintv.lottery.area.vo.AreaVo;
@Service("areaService")
@SuppressWarnings("unchecked")
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDao areaDao;
	//根据父级code获取该父级所有下属地区列表
	public List<AreaVo> getAreaByParentCode(String parentCode){
		return areaDao.getAreaByParentCode(parentCode);
	}
}