package com.wintv.lottery.admin.team.dao;

import java.util.List;
import java.util.Map;

import org.ecside.table.limit.Limit;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.Team;

public interface TeamDao extends BaseDao<Team,Long>{
	/**
	 * 根据国家ID查询所有球队
	 * 参数：
	 * countryId：国家ID
	 * @return
	 * @throws DaoException
	 */
    @SuppressWarnings("unchecked")
	public List<Team> findTeamList(Map params);
    
    //根据球队所属区域,国家,名称去查找唯一球队
    public Team findUniqueByName(Map<String,String> params);

	public Page findAllTeam(Limit limit, Map<String, String> param);
    
}
