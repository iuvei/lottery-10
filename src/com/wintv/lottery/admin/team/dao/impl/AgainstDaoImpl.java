package com.wintv.lottery.admin.team.dao.impl;


import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;
import oracle.sql.STRUCT;

import org.apache.commons.lang.StringUtils;
import org.ecside.table.limit.Limit;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.Constants;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.Race;
import com.wintv.framework.utils.DateUtil;
import com.wintv.lottery.admin.team.dao.AgainstDao;
import com.wintv.lottery.admin.team.vo.TeamVo;

@Repository("againstDao")
public class AgainstDaoImpl  extends BaseHibernate<Against,Long> implements AgainstDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public AgainstVo loadAgainstById(String category,Long id){
		StringBuilder sql=new StringBuilder("select AGAINST_ID,HOST_NAME,GUEST_NAME,to_char(START_TIME,'yy-mm-dd hh24:mi') START_TIME,RACE_NAME,decode(TYPE,'1','联赛','2','杯赛','其他') TYPE ");
		if(Constants.ORDER_CATEGORY_FBSINGLE.equals(category)){//北京单场
	    	sql.append(",CONCEDE ");
	    }
		sql.append(" from T_AGAINST where AGAINST_ID="+id);
		SQLQuery q=this.getSession().createSQLQuery(sql.toString());
		
		Object[] arr=(Object[])q.uniqueResult();
		AgainstVo vo=new AgainstVo();
		vo.setAgainstId(new Long(arr[0].toString()));
		vo.setHostName((String)arr[1]);
		vo.setGuestName((String)arr[2]);
		vo.setStartTime((String)arr[3]);
		vo.setRaceName((String)arr[4]);
		vo.setType((String)arr[5]);
		if(Constants.ORDER_CATEGORY_FBSINGLE.equals(category)){//北京单场
			vo.setConcede((String)arr[6]);
		}
		return vo;
	}
	/**后台球赛资料管理-对阵管理-第8页*/
    @SuppressWarnings("unchecked")
	public List  findAgainstList(Map params)throws DaoException{
		//StringBuilder sql=new StringBuilder("select a.GUEST_NAME,a.HOST_NAME,a.SCORE,a.GUEST_NAME,a.CONCEDE,a.START_TIME  from  T_AGAINST  a");
		return null;
	}
    
    //对阵管理
    @Override
	public Page againstList(Limit limit, Map<String, String> param) {
    	StringBuilder sql = new StringBuilder();
    	StringBuilder count_sql = new StringBuilder();
		List<Object> arg = new ArrayList<Object>();
		Long isConcede = null;
		int high_row = limit.getPage() * limit.getCurrentRowsDisplayed();
		int low_row	 = (limit.getPage()-1) * limit.getCurrentRowsDisplayed() + 1;
		if(StringUtils.isNotBlank(param.get("isConcede")))
			isConcede = Long.parseLong(param.get("isConcede"));
		sql.append("select rownum r,a.against_id,a.race_name, a.host_name,a.guest_name,a.score,a.concede,a.start_time, ");
		sql.append(" (select count(*) from t_against against, t_c2c_product c2c where c2c.against_id = a.against_id and against.against_id = c2c.against_id(+)) as is_concede ");
		sql.append(" from t_against a where 1=1 ");
		if(StringUtils.isNotBlank(param.get("startTime"))){
			sql.append(" and a.start_time  >= to_date('"+param.get("startTime")+"','yyyy-MM-dd HH24:mi:ss') ");
			//arg.add(param.get("startTime"));
		}
		if(StringUtils.isNotBlank(param.get("endTime"))){
			sql.append(" and a.start_time  <= to_date('"+param.get("endTime")+"','yyyy-MM-dd HH24:mi:ss') ");
		}
		if(null != isConcede){
			if(1L == isConcede) sql.append(" and a.concede != 0 ");
			else if(0L == isConcede) sql.append(" and a.concede  = 0 ");
		}
		
		if(StringUtils.isNotBlank(param.get("race_name"))){
			sql.append(" and a.race_name  like ? ");
			arg.add("%"+ param.get("race_name") +"%");
		}
		if(StringUtils.isNotBlank(param.get("race_season"))){
			sql.append(" and a.race_season_id  = ? ");
			arg.add(Long.parseLong(param.get("race_season")));
		}
		if(StringUtils.isNotBlank(param.get("wheel_select"))){
			sql.append(" and a.rounds  = ? ");
			arg.add(Long.parseLong(param.get("wheel_select")));
		}
		
		if(StringUtils.isNotBlank(param.get("hostName"))){
			sql.append(" and a.host_name  like ? ");
			arg.add("%"+param.get("hostName")+"%");
		}
		if(StringUtils.isNotBlank(param.get("guestName"))){
			sql.append(" and a.guest_name like ? ");
			arg.add("%"+param.get("guestName")+"%");
		}
		final Map<String,Integer> against_count = new HashMap<String, Integer>();
		against_count.put("count",0);
		final List<AgainstVo> list_r = new ArrayList<AgainstVo>();
		jdbcTemplate.query(sql.toString(), arg.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				against_count.put("count", rs.getInt("r"));
			}
		});
		
		if(against_count.get("count").equals(0)){
			arg = null;
			sql = null;
			count_sql= null;
			return new Page(0,0,limit.getCurrentRowsDisplayed(), new ArrayList<AgainstVo>());
		}
		count_sql.append("select *  from (").append(sql).append(" and rownum <= ?  ) ttt where ttt.r >= ? ");
		arg.add(high_row);
		arg.add(low_row);
		jdbcTemplate.query(count_sql.toString(), arg.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				AgainstVo against = new AgainstVo();
				against.setAgainstId(rs.getLong("against_id"));
				against.setRaceName(rs.getString("race_name"));
				against.setHostName(rs.getString("host_name"));
				against.setGuestName(rs.getString("guest_name"));
				against.setConcede(rs.getString("concede"));
				against.setIsxinshui(rs.getLong("is_concede"));
				against.setScore(rs.getString("score"));
				against.setStartTime(DateUtil.formatDateTime(rs.getTimestamp("start_time")));
				list_r.add(against);
			}
		});	
		arg = null;
		sql = null;
		count_sql= null;
		return new Page(Page.getStartOfPage(limit.getPage(), limit.getCurrentRowsDisplayed()), 
				against_count.get("count"),limit.getCurrentRowsDisplayed(), list_r);
	}
	 /**
     * 根据条件查询对阵
     * 参数:
     * areaId:区域
     * countryId:国家
     * raceId:赛事
     * raceSeason:赛季
     * rounds:轮次
     * @return
     * @throws DaoException
     */
	@SuppressWarnings("unchecked")
	public List<Against> findScheduleList(Map params){
		StringBuilder hql=new StringBuilder("select new com.wintv.framework.pojo.Against(t.againstId,t.hostName,t.guestName)  from Against where 1=1");
		Long areaId=(Long)params.get("areaId");
		Long countryId=(Long)params.get("countryId");
		Long raceId=(Long)params.get("raceId");
		Long raceSeason=(Long)params.get("raceSeason");
		Long rounds=(Long)params.get("rounds");
		if(areaId!=null){
		  hql.append(" and t.areaId="+areaId);
		}
		if(countryId!=null){
			hql.append(" and t.countryId="+countryId);
		}
		if(raceId!=null){
			hql.append(" and t.raceId="+raceId);
		}
		if(raceSeason!=null){
			hql.append(" and t.raceSeason="+raceSeason);
		}
		if(rounds!=null){
			hql.append(" and t.rounds="+rounds);
		}
	   return getSession().createQuery(hql.toString()).list();
	}
	
	//联赛队阵 分页查询
	@Override
	public Page findLeagueAgainstList(Limit limit, Map<String, String> param) {
		StringBuilder sql = new StringBuilder();
		List<Object> arg = new ArrayList<Object>();
		sql.append("select a from Against a where a.type = 1 ");
		if(StringUtils.isNotBlank(param.get("district_select"))){
			sql.append(" and a.areaId = ? ");
			arg.add(Long.parseLong(param.get("district_select")));
		}
		if(StringUtils.isNotBlank(param.get("country_select"))){
			sql.append(" and a.countryId = ? ");
			arg.add(Long.parseLong(param.get("country_select")));
		}
		if(StringUtils.isNotBlank(param.get("league_select"))){
			sql.append(" and a.raceId = ? ");
			arg.add(Long.parseLong(param.get("league_select")));
		}
		if(StringUtils.isNotBlank(param.get("race_season"))){
			sql.append(" and a.raceSeasonId = ? ");
			arg.add(Long.parseLong(param.get("race_season")));
		}
		if(StringUtils.isNotBlank(param.get("wheel_select"))){
			sql.append(" and a.rounds = ? ");
			arg.add(Long.parseLong(param.get("wheel_select")));
		}
		if(StringUtils.isNotBlank(param.get("hostName"))){
			sql.append(" and a.hostId = ? ");
			arg.add(Long.parseLong(param.get("hostName")));
		}
		if(StringUtils.isNotBlank(param.get("guestName"))){
			sql.append(" and a.guestId = ? ");
			arg.add(Long.parseLong(param.get("guestName")));
		}
		if(StringUtils.isNotBlank(param.get("startTime"))){
			sql.append(" and a.startTime  >= ?  ");
			Date startDate = DateUtil.parseDate(param.get("startTime"),"yyyy-MM-dd");
			Date endDate = DateUtil.add(startDate,1);
			arg.add(startDate);
			sql.append(" and a.startTime  < ? ");
			arg.add(endDate);
		}
		return pagedQuery(sql.toString(),arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
	}
	//杯赛对阵
	@Override
	public Page findCupAgainstTeam(Limit limit, Map<String, String> param) {
		StringBuilder sql = new StringBuilder();
		List<Object> arg = new ArrayList<Object>();
		sql.append("select a from Against a where a.type = 2 ");
		if(StringUtils.isNotBlank(param.get("raceId"))){
			sql.append(" and a.raceId = ? ");
			arg.add(Long.parseLong(param.get("raceId")));
		}
		if(StringUtils.isNotBlank(param.get("race_season"))){
			sql.append(" and a.raceSeasonId = ? ");
			arg.add(Long.parseLong(param.get("race_season")));
		}
		if(StringUtils.isNotBlank(param.get("wheelId"))){
			sql.append(" and a.rounds = ? ");
			arg.add(Long.parseLong(param.get("wheelId")));
		}
		if(StringUtils.isNotBlank(param.get("hostName"))){
			sql.append(" and a.hostId = ? ");
			arg.add(Long.parseLong(param.get("hostName")));
		}
		if(StringUtils.isNotBlank(param.get("guestName"))){
			sql.append(" and a.guestId = ? ");
			arg.add(Long.parseLong(param.get("guestName")));
		}
		if(StringUtils.isNotBlank(param.get("startTime"))){
			sql.append(" and a.startTime  >= ?  ");
			Date startDate = DateUtil.parseDate(param.get("startTime"),"yyyy-MM-dd");
			Date endDate = DateUtil.add(startDate,1);
			arg.add(startDate);
			sql.append(" and a.startTime  < ? ");
			arg.add(endDate);
		}
		return pagedQuery(sql.toString(),arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
	}
	
	@Override
	public Page findAllTeamManager(Map<String,Object> param) {
		String country_select = (String)param.get("country_select");
		String raceId = (String)param.get("raceId");
		String race_season = (String)param.get("race_season");
		String sortField  = (String)param.get("sort");
		String sortOrder  = (String)param.get("sortOrder");
		if(StringUtils.isBlank(country_select) && StringUtils.isBlank(raceId) && StringUtils.isBlank(race_season))
			return  new Page(0,0,(Integer)param.get("page_size"), new ArrayList<TeamVo>());
		StringBuilder sb = new StringBuilder();
		List<TeamVo> teamList = null;
		
		if(StringUtils.isNotBlank(country_select))
			sb.append(" and t.COUNTRY_ID = ").append(Long.parseLong(country_select));
		if(StringUtils.isNotBlank(raceId))
			sb.append(" and t.RACE_ID = ").append(Long.parseLong(raceId));
		
		if(StringUtils.isNotBlank((String)param.get("team_type")))
			sb.append(" and t.type = ").append((String)param.get("team_type"));
		if(StringUtils.isNotBlank(race_season)){
			sb.append(" and t.RACE_SEASON_ID = ").append(Long.parseLong(race_season));
			teamList = this.findAllTeamManager("f","n",Long.parseLong(raceId),Long.parseLong(race_season),sb.toString(),sortField,sortOrder);
		}else if(StringUtils.isBlank(race_season) && StringUtils.isNotBlank(raceId)){
			teamList = this.findAllTeamManager("f","y",Long.parseLong(raceId),0L,sb.toString(),sortField,sortOrder);
		}else
			teamList = this.findAllTeamManager("f","n",0L,0L,sb.toString(),sortField,sortOrder);
	
		if(null == teamList)
			return  new Page(0,0,(Integer)param.get("page_size"), new ArrayList<TeamVo>());
	    Integer start = ((Integer)param.get("page_no")-1) * (Integer)param.get("page_size") +1;
		return new Page(start,teamList.size(),(Integer)param.get("page_size"),teamList);
	}
	
	/**
	 * @param updown     上 'u' 下 'd' 半场,或全场 'f'
	 * @param latest     查最新的 就输入 'y'  同时输入 raceId
	 * @param raceId     联赛或杯赛ID
	 * @param seasonId   赛季ID
	 * @param conditionSelect   查询条件     
	 * 格式: and t.AREA_ID = 1 and t.COUNTRY_ID = 16 and t.RACE_SEASON_ID 278 and t.RACE_ID = 5 
	 * */
	@Override
	public List<TeamVo> findAllTeamManager(String updown,String latest,Long raceId,Long seasonId,
				String conditionSelect,String sortField,String sortOrder){
		Connection conn = null;
		CallableStatement stmt = null;
		List<TeamVo> teamList = null;
		TeamVo teamVo = null;
		Object[] objs = null;
		String sql = "{ call team.team_manager(?,?,?,?,?,?)}";
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			stmt = conn.prepareCall(sql);
			if(null != updown)stmt.setString(1, updown);
			if(null != latest)stmt.setString(2, latest);
			if(null != raceId)stmt.setLong(3, raceId);
			if(null != seasonId)stmt.setLong(4, seasonId);
			if(null != conditionSelect)stmt.setString(5, conditionSelect.replace("%", "'").replace("'", ""));
			stmt.registerOutParameter(6,OracleTypes.ARRAY,"LOTTERY.TEAMVOS");
			stmt.execute();
			Object[] obj = (Object[])stmt.getArray(6).getArray();
			if(obj != null){
				teamList = new ArrayList<TeamVo>();
				for(Object o : obj){
					objs = ((STRUCT)o).getAttributes();
					teamVo = new TeamVo();
					teamVo.setHostName((String)objs[0]);
					teamVo.setReceName((String)objs[1]);
					teamVo.setShowing((BigDecimal)objs[2]);
					teamVo.setRaceSeasonName((String)objs[3]);
					teamVo.setTeamType((String)objs[4]);
					teamVo.setTeamScores((BigDecimal)objs[5]);
					teamVo.setWinShowing((BigDecimal)objs[6]);
					teamVo.setEqualShowing((BigDecimal)objs[7]);
					teamVo.setLostShowing((BigDecimal)objs[8]);
					teamVo.setWinShowingRate(new BigDecimal(Double.valueOf((String)objs[9])).setScale(2, BigDecimal.ROUND_HALF_UP));
					teamVo.setEqualShowingRate(new BigDecimal(Double.valueOf((String)objs[10])).setScale(2, BigDecimal.ROUND_HALF_UP));
					teamVo.setLostShowingRate(new BigDecimal(Double.valueOf((String)objs[11])).setScale(2, BigDecimal.ROUND_HALF_UP));
					teamVo.setInGoals((BigDecimal)objs[12]);
					teamVo.setLostGoals((BigDecimal)objs[13]);
					teamVo.setInGoals_avg((String)objs[14]);
					teamVo.setLostGoals_avg((String)objs[15]);
					teamVo.setTeam_gd((BigDecimal)objs[16]);
					teamList.add(teamVo);
				}
			}
			
		} catch (SQLException e) {
			conn = null;
			stmt = null;
			teamList = null;
			e.printStackTrace();
			return null;
		} finally{
			conn = null;
			stmt = null;
		}
		if(null != teamList && teamList.size() >0){
			Collections.sort(teamList,Collections.reverseOrder());
			for(int i = 0;i<teamList.size();i++){
				teamList.get(i).setRanking(i+1);
			}
		}
		return teamList;
	}
	
	
	
	@Override
	public Long findTeamsById(Long teamId,Long seasonId) {
		StringBuilder sb = new StringBuilder("select count(a) from Against a ");
		if(null != teamId){
			sb.append(" where a.hostId = ? or a.guestId = ?");
			if(null != seasonId){
				sb.append(" and a.raceSeasonId = ? ");
				return (Long)this.getSession().createQuery(sb.toString()).setLong(0, teamId).
					setLong(1, teamId).setLong(2, seasonId).uniqueResult();
			}
			return (Long)this.getSession().createQuery(sb.toString()).setLong(0, teamId).setLong(1, teamId).uniqueResult();
		}	
		return null;
	}
	
}
