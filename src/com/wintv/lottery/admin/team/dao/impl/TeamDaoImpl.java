package com.wintv.lottery.admin.team.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.ecside.table.limit.Limit;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.Team;
import com.wintv.lottery.admin.team.dao.TeamDao;
@Repository("teamDao")
public class TeamDaoImpl extends BaseHibernate<Team,Long> implements TeamDao{
	/**
	 * 根据国家ID查询所有球队基本信息
	 * 参数：
	 * countryId：国家ID
	 * @return
	 * @throws DaoException
	 */
    @SuppressWarnings("unchecked")
	public List<Team> findTeamList(Map params){
    	Long countryId=(Long)params.get("countryId");
    	int firstRow=(Integer)params.get("firstRow");
    	int maxRow=(Integer)params.get("maxRow");
    	Query q=getSession().createQuery("select new com.wintv.framework.pojo.Team(t.country,t.countryId,t.type) " +
    			" from Team t where t.countryId=?");
    	q.setFirstResult(firstRow);
    	q.setMaxResults(maxRow);
    	q.setLong(0, countryId);
    	return q.list();
    }
    /**
	 * 根据条件查询所有球队详细信息
	 * 参数：
	 * countryId：国家ID
	 * @return
	 * @throws DaoException
	 */
    @SuppressWarnings("unchecked")
	public List<Team> findTeamDetailList(Map params){
    	Long countryId=(Long)params.get("countryId");
    	Long areaId=(Long)params.get("areaId");
    	int firstRow=(Integer)params.get("firstRow");
    	int maxRow=(Integer)params.get("maxRow");
    	StringBuilder sb=new StringBuilder("from Team t where 1=1 ");
    	if(countryId!=null){
    		sb.append(" and t.countryId='"+countryId+"'");
    	}
    	if(areaId!=null){
    		sb.append(" and t.areaId='"+areaId+"'");
    	}
    	Query q=getSession().createQuery(sb.toString());
    	q.setFirstResult(firstRow);
    	q.setMaxResults(maxRow);
    	
    	return q.list();
    }
    /**
     * 根据区域查询国家
     * 参数:
     * zhouId:洲ID
     * @return
     * @throws DaoException
     */
    @SuppressWarnings("unchecked")
	public List<Team> findCountryList(Map params)throws DaoException{
    	Long zhouId=(Long)params.get("zhouId");
    	Query q=getSession().createQuery("from Code t where t.parentId=?");
    	q.setLong(0, zhouId);
    	return q.list();
    }
	@Override
	public Team findUniqueByName(Map<String, String> params) {
		Object district_select = params.get("district_select");
    	Object country_select = params.get("country_select");
    	Object team_text = params.get("team_text");
		return (Team) this.getSession()
			.createQuery(" from Team t where t.name = ? and t.countryId = ? and t.areaId = ?  ")
			.setParameter(0, team_text)
			.setLong(1, Long.valueOf((String)country_select)) 
			.setLong(2, Long.valueOf((String)district_select))
			.uniqueResult();
	}
	
	
	@Override
	public Page findAllTeam(Limit limit,Map<String,String> param) {
		List<Object> arg = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select t from Team t where 1=1 ");
		if(StringUtils.isNotBlank(param.get("district_select"))){
			sql.append(" and t.districtId = ? ");
			arg.add(Long.parseLong(param.get("district_select")));
		}
		if(StringUtils.isNotBlank(param.get("country_select"))){
			sql.append(" and t.countryId = ? ");
			arg.add(Long.parseLong(param.get("country_select")));
		}
		if(StringUtils.isNotBlank(param.get("team_text"))){
			sql.append(" and t.name like ? ");
			arg.add("%" + param.get("team_text") + "%");
		}
		if(StringUtils.isNotBlank(param.get("team_type"))){
			sql.append(" and t.type = ? ");
			arg.add(param.get("team_type"));
		}
		return pagedQuery(sql.toString(),arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
	}
}
