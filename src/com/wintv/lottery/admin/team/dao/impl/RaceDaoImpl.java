package com.wintv.lottery.admin.team.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.ecside.table.limit.Limit;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.Race;
import  com.wintv.lottery.admin.team.dao.RaceDao;
import com.wintv.lottery.admin.team.vo.DistrictVo;

@Repository("raceDao")

public class RaceDaoImpl extends BaseHibernate<Race,Long> implements RaceDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 根据国家查询所有联赛或杯赛 以及其他赛事
	 * @param countryId
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<Race> findRaceByCountry(Long countryId){
		Query q=getSession().createQuery("from Race t where t.countryId=?");
		q.setLong(0, countryId);
		return q.list();
	}
	/**
	 * 查询对应的赛季  轮次  否则会影响性能
	 * @param parentId
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<Race> findByParent(Long parentId){
		Query q=getSession().createQuery("select new com.wintv.framework.pojo.Race(t.id,t.name) from Race t where t.parentId=? order by t.name desc");
		q.setLong(0, parentId);
		return q.list();
	}
	/**
	 * 查询所有联赛或者杯赛,只查询需要的字段  否则会影响性能
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<Race> findAllRace()throws DaoException{
		Query q=getSession().createQuery("select new com.wintv.framework.pojo.Race(t.id,t.name)" +
				"  from Race t where t.parentId is null order by t.districtId asc");
		return q.list();
	}
	
	/**
	 * 前台页面查询所有联赛或者杯赛
	 *1.民间用户管理自己的心水   USERID
     *2.民间高手搜索对阵列表  
     *3.普通用户管理心水订单 USERID
     *4.民间高手销售列表 USERID
     *5.购买心水,可能未登录
	 */
	@SuppressWarnings("unchecked")
	public List<Race> findSiteRaceList(Map params)throws DaoException{
		String flg=(String)params.get("flg");
		Long userId=(Long)params.get("userId");
		StringBuilder sql=null;
		if("1".equals(flg)){//民间用户管理自己的心水
		    sql=new StringBuilder("select distinct a.race_name,a.race_id from T_AGAINST a,T_XINSHUI_AGAINST b,T_C2C_PRODUCT c  where a.against_id=b.against_id");
			sql.append(" and b.against_id=c.against_id  and c.TX_USER_ID="+userId);
		}else if("2".equals(flg)){//民间高手搜索对阵列表,发布心水
			sql=new StringBuilder("select distinct a.race_name,a.race_id from T_AGAINST a,T_XINSHUI_AGAINST b where a.against_id=b.against_id");
			sql.append(" and a.START_TIME >sysdate");
		}else if("3".equals(flg)){//普通用户管理心水订单
			sql=new StringBuilder("select distinct a.race_name,a.race_id from  T_C2C_PRODUCT a,T_XINSHUI_ORDER b where a.C2C_ID=b.PRODUCT_ID");
			sql.append(" and b.BUY_USER_ID="+userId);
		}else if("4".equals(flg)){//民间高手销售列表
			sql=new StringBuilder("select distinct a.race_name,a.race_id from  T_C2C_PRODUCT a,T_XINSHUI_ORDER b where a.C2C_ID=b.PRODUCT_ID");
			sql.append(" and b.SOLD_USER_ID="+userId);
		}else if("5".equals(flg)){//购买心水,可能未登录
			sql=new StringBuilder("select distinct a.race_name,a.race_id from  T_C2C_PRODUCT a  where  a.STATUS='1'");
		}
		
        SQLQuery q=this.getSession().createSQLQuery(sql.toString());
       
        
        List<Object[]> list=q.list();
        List raceList=new ArrayList();
        Race po=null;
        for(Object[] o :list){
        	po=new Race();
        	Long raceId=new Long(o[1].toString());
        	String raceName=o[0].toString();
        	po.setId(raceId);
        	po.setName(raceName);
        	raceList.add(po);
        }
        return raceList;
	}
	
	public List<Race> findRaceByTree(String type, Long id){
		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append(" select t.id,t.name,t.parent_id from t_race t where  t.id <>? and t.type =? start with id=? connect by prior id = parent_id ");
		list.add(id);
		list.add(type);
		list.add(id);
		final List<Race> list_r = new ArrayList<Race>();
		jdbcTemplate.query(sb.toString(), list.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				Race race = new Race();
				race.setId(rs.getLong("id"));
				race.setName(rs.getString("name"));
				race.setParentId(rs.getLong("parent_id"));
				list_r.add(race);
			}
		});
		return list_r;
	}
	
	public Race findUniqueLeagueRace(Map<String, String> param){
		StringBuilder sb = new StringBuilder("select r from Race r where r.type = 1  ");
		sb.append(" and r.districtId = ?  and r.countryId = ? and r.name = ?");
		if(StringUtils.isBlank(param.get("parentId"))){
			sb.append(" and r.parentId is null ");
		}else{
			sb.append(" and r.parentId = ?");
		}
		Query query = getSession().createQuery(sb.toString())
			.setLong(0, Long.parseLong(param.get("district_select")))
			.setLong(1, Long.parseLong(param.get("country_select")))
			.setString(2, param.get("race_name"));
		if(StringUtils.isNotBlank(param.get("parentId")))
			query.setLong(3, Long.parseLong((String)param.get("parentId")));
		return (Race) query.uniqueResult();
	}
	
	@Override
	public Page findAllLeagueRace(Limit limit, Map<String, String> param) {
		List<Object> arg = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select r from Race r where r.parentId is Null and r.type = 1 ");
		if(StringUtils.isNotBlank(param.get("recordKey"))){
			String s =" select r from Race r where r.type = 1 and r.parentId = ? "; 
			arg.add(Long.parseLong(param.get("recordKey")));
			return pagedQuery(s,arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
		}
		if(StringUtils.isNotBlank(param.get("district_select"))){
			sql.append(" and r.districtId = ? ");
			arg.add(Long.parseLong(param.get("district_select")));
		}
		if(StringUtils.isNotBlank(param.get("country_select"))){
			sql.append(" and r.countryId = ? ");
			arg.add(Long.parseLong(param.get("country_select")));
		}
		if(StringUtils.isNotBlank(param.get("race_name"))){
			sql.append(" and r.name like ? ");
			arg.add("%" + param.get("race_name") + "%");
		}
		return pagedQuery(sql.toString(),arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
	}
	
	@Override
	public List<DistrictVo> findAllLeagueRaceCount(Map<String,String> param) {
		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append(" select a.id,a.name,v.cnt from T_RACE a,V_LEAUE_QUERY v where " +
				" a.id=v.parent_id(+) and a.type =1 and  a.parent_id is null ") ;
		if(StringUtils.isNotBlank(param.get("district_select"))){
			sb.append(" and a.district_id = ? ");
			list.add(Long.parseLong(param.get("district_select")));
		}
		if(StringUtils.isNotBlank(param.get("country_select"))){
			sb.append(" and a.country_id = ? ");
			list.add(Long.parseLong(param.get("country_select")));
		}
		if(StringUtils.isNotBlank(param.get("race_name"))){
			sb.append(" and a.name like ? ");
			list.add("%" + param.get("race_name") +  "%");
		}
		final List<DistrictVo> list_r = new ArrayList<DistrictVo>();
		jdbcTemplate.query(sb.toString(), list.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				DistrictVo d = new DistrictVo();
				d.setId(rs.getLong("id"));
				d.setName(rs.getString("name"));
				d.setCount(rs.getLong("cnt"));
				list_r.add(d);
			}
		});
		list = null;
		sb = null;
		return list_r;
	}
	
	@Override
	public Page findLRWheel(Limit limit, Map<String, String> param) {
		/*StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append(" select t.* from t_race t where t.parent_id = ?  start with t.id = ? connect by prior t.id = t.parent_id ");*/
		String recordKey = param.get("recordKey");
		String sql = "select r from Race r where r.parentId = ? ";
		List<Object> arg = new ArrayList<Object>();
		arg.add(Long.parseLong(recordKey));
		return pagedQuery(sql,arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
	
	}
	
	@Override
	public Race findUniqueCupRace(Map<String, String> param) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Race r where r.type = 2 and r.name = ? and r.prefix = ?");
		if(StringUtils.isNotBlank(param.get("parentId"))){
			sql.append(" and r.parentId = ? ");
		}else{
			sql.append(" and r.parentId is null ");
		}	
		Query query = getSession().createQuery(sql.toString()).setString(0, param.get("cup_name"))
				.setString(1, param.get("cup_prefix"));
		if(StringUtils.isNotBlank(param.get("parentId"))){
			return (Race) query.setLong(2, Long.parseLong(param.get("parentId"))).uniqueResult();
		}
		return (Race) query.uniqueResult();
	}
	
	@Override
	public Page findCupRace(Limit limit, Map<String, String> param) {
		StringBuilder sql = new StringBuilder("select r from Race r where  r.type = 2  ");
		List<Object> arg = new ArrayList<Object>();
		if(StringUtils.isNotBlank(param.get("recordKey"))){
			sql.append(" and r.parentId = ?");
			arg.add(Long.parseLong(param.get("recordKey")));
			return pagedQuery(sql.toString(),arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
		}
		sql.append(" and r.parentId is Null ");
		if(StringUtils.isNotBlank(param.get("cup_prefix"))){
			sql.append(" and r.prefix = ?");
			arg.add(param.get("cup_prefix"));
		}
		return pagedQuery(sql.toString(),arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
	}
	//======================================ADDED  BY  wjj=================================================
	public List<Race> findRaceList(String p){
		String sql="select ID,NAME name  from T_RACE  where PARENT_ID is null and   NAME like '%"+p+"%'";
		SQLQuery q=this.getSession().createSQLQuery(sql);
		List<Object[]> list=q.list();
		List<Race> result=new ArrayList<Race>();
		Race po=null;
		for(Object[] o:list){
			po=new Race();
			po.setId(new Long(o[0].toString()));
			po.setName(o[1].toString());
			result.add(po);
		}
	  return result;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Race> findLeagueByCountry(long country_select) {
		StringBuilder sql = new StringBuilder();
		sql.append("from Race r where r.type = 1 and r.parentId is null and r.countryId = ? ");
		return getSession().createQuery(sql.toString()).setLong(0, country_select).list();
	}
	
	@Override
	public Page findAllLeagueTeam(Limit limit, Map<String, String> param) {
		StringBuilder sql = new StringBuilder("select tr from TeamRace tr where  tr.type = 1 ");
		List<Object> arg = new ArrayList<Object>();
		
		if(StringUtils.isNotBlank(param.get("recordKey"))){
			sql.append(" and tr.raceSeasonId = ? ");
			arg.add(Long.parseLong(param.get("recordKey")));
			return pagedQuery(sql.toString(),arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
		}
		if(StringUtils.isBlank(param.get("league_select"))){
			return new Page();
		}else{
			sql.append(" and tr.areaId = ? ").append(" and tr.countryId = ? ").append(" and tr.raceId = ? ");
			arg.add(Long.parseLong(param.get("district_select")));
			arg.add(Long.parseLong(param.get("country_select")));
			arg.add(Long.parseLong(param.get("league_select")));
		}
		/*if(StringUtils.isNotBlank(param.get("district_select"))){
			sql.append(" and tr.areaId = ? ");
			arg.add(Long.parseLong(param.get("district_select")));
		}
		if(StringUtils.isNotBlank(param.get("country_select"))){
			sql.append(" and tr.countryId = ? ");
			arg.add(Long.parseLong(param.get("country_select")));
		}
		if(StringUtils.isNotBlank(param.get("league_select"))){
			sql.append(" and tr.raceId = ? ");
			arg.add(Long.parseLong(param.get("league_select")));
		}*/
		return pagedQuery(sql.toString(),arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
	}
	
	@Override
	public List<DistrictVo> findAllLeagueTeamCount(Map<String, String> param) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) as count_t,tr.race_season_id,tr.race_season_name from t_team_race tr ");
		sb.append(" where tr.type = 1 and tr.race_id = ? ");
		sb.append(" group by tr.race_season_id,tr.race_season_name ");
		final List<DistrictVo> list_r = new ArrayList<DistrictVo>();
		List<Long> list = new ArrayList<Long>();
		list.add(Long.parseLong(param.get("league_select")));
		jdbcTemplate.query(sb.toString(), list.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				DistrictVo d = new DistrictVo();
				d.setId(rs.getLong("race_season_id"));
				d.setName(rs.getString("race_season_name"));
				d.setCount(rs.getLong("count_t"));
				list_r.add(d);
			}
		});
		list = null;
		sb = null;
		return list_r;
	}
	
	@Override
	public Page findLeagueTeamBySession(Limit limit, Map<String, String> param) {
		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append("select t.name, t.id from t_race r,t_team t ");
		sb.append(" where r.id = ?  and r.country_id(+)=t.country_id");
		list.add(Long.parseLong(param.get("race_season")));
		if(StringUtils.isNotBlank(param.get("team_type"))){
			sb.append(" and t.type=? ");
			list.add(param.get("team_type"));
		}
		final List<DistrictVo> list_r = new ArrayList<DistrictVo>();
		jdbcTemplate.query(sb.toString(), list.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				DistrictVo d = new DistrictVo();
				d.setId(rs.getLong("id"));
				d.setName(rs.getString("name"));
				list_r.add(d);
			}
		});
		list = null;
		sb = null;
		return new Page(Page.getStartOfPage(limit.getPage(), limit.getCurrentRowsDisplayed()), 
				list_r != null ? list_r.size() : 0,limit.getCurrentRowsDisplayed(), list_r);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Race> findCupByPrefix(Map<String, String> param) {
		Query q=getSession().createQuery("from Race t where t.prefix = ? ");
		q.setString(0, param.get("cup_prefix"));
		return q.list();
	}
	
	@Override
	public Page findCupTeam(Limit limit, Map<String, String> param) {
		StringBuilder sql = new StringBuilder();
		List<Object> arg = new ArrayList<Object>();
		sql.append("select tr from TeamRace tr where tr.type = 2 ");
		if(StringUtils.isNotBlank(param.get("cupId"))){
			sql.append(" and tr.raceSeasonId = ? ");
			arg.add(Long.parseLong(param.get("cupId")));
		}
		return pagedQuery(sql.toString(),arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
	}
	

}
