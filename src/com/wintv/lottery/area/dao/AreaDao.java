package com.wintv.lottery.area.dao;

import java.util.List;

import com.wintv.framework.dao.BaseDao;
import com.wintv.lottery.area.vo.AreaVo;
/**
 * 地区表DAO
 * @author Hikin Yao
 *
 * @version 1.0.0
 */
public interface AreaDao extends BaseDao<Object,Long>{
	//根据父级code获取该父级所有下属地区列表
	public List<AreaVo> getAreaByParentCode(String parentCode);
}
