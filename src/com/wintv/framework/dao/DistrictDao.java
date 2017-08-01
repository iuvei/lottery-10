package com.wintv.framework.dao;


import java.util.List;
import java.util.Map;

import org.ecside.table.limit.Limit;

import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.District;
import com.wintv.lottery.admin.team.vo.DistrictVo;


/**
 * 球赛对阵 区域,国家DAO 
 * */
public interface DistrictDao extends BaseDao<District, Long>{

	Page findAllDistrict(Limit limit,Map<String,String> param);

	List<DistrictVo> findAllDistrictCount();
	
	District findUniqueDistrict(Map<String,String> param);
	
}
