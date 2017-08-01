package com.wintv.lottery.admin.team.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.ecside.table.limit.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.dao.DistrictDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.District;
import com.wintv.framework.pojo.Race;
import com.wintv.framework.pojo.Team;
import com.wintv.framework.pojo.TeamRace;
import com.wintv.framework.utils.DateUtil;
import com.wintv.framework.utils.PropertyCopyUtil;
import com.wintv.lottery.admin.team.dao.AgainstDao;
import com.wintv.lottery.admin.team.dao.RaceDao;
import com.wintv.lottery.admin.team.dao.TeamDao;
import com.wintv.lottery.admin.team.dao.TeamRaceDao;
import com.wintv.lottery.admin.team.service.RaceService;
import com.wintv.lottery.admin.team.vo.DistrictVo;

@Service("raceService")
public class RaceSercviceImpl implements RaceService{
	
	@Autowired
	private RaceDao raceDao;
	@Autowired
	private TeamDao teamDao;
	@Autowired  
	private AgainstDao againstDao;
	@Autowired
	private DistrictDao districtDao;
	@Autowired
	private TeamRaceDao teamRaceDao;
	
	@Override
	public Page findAllDistrict(Limit limit,Map<String,String> param) {
		return districtDao.findAllDistrict(limit,param);
	}
	
	@Override
	public Page againstList(Limit limit, Map<String, String> param) {
		return againstDao.againstList(limit,param);
	}
	
	@Override
	public Page findAllTeam(Limit limit, Map<String, String> param) {
		return teamDao.findAllTeam(limit,param);
	}
	
	@Override
	public Page findAllLeagueRace(Limit limit, Map<String, String> param){
		return raceDao.findAllLeagueRace(limit, param);
	}
	
	@Override
	public Page findLRWheel(Limit limit, Map<String, String> param){
		return raceDao.findLRWheel(limit, param);
	}
	
	@Override
	public Page findCupRace(Limit limit, Map<String, String> param) {
		return raceDao.findCupRace(limit, param);
	}
	
	@Override
	public Page findAllLeagueTeam(Limit limit, Map<String, String> param) {
		return raceDao.findAllLeagueTeam(limit, param);
	}
	
	@Override
	public Page findAllTeamManager(Map<String,Object> param) {
		return againstDao.findAllTeamManager(param);
	}
	//----------------------------------------区域----------------------------------------
	public List<District> findBigDistrict(){
		return districtDao.findBy("parentId", 0L);
	}

	@Override
	public List<District> findSmallDisByBig(Long id) {
		return districtDao.findBy("parentId", id);
	}
	
	/**保存洲 国家 */
	@Transactional
	public Long saveDistrict(District district)throws DaoException{
		return districtDao.saveObject(district);
	}
	
	//删除区域,如果该区域下有国家存在,则先不删除
	@Override
	@Transactional
	public int districtDeleteDis(String ids)throws DaoException{
		Long[] id_1 = ArrayUtils.toObject((long[])ConvertUtils.convert(ids.split(", "), Long.TYPE));
		List<District> list_1 = null;
		for(Long id : id_1){
			list_1 = districtDao.findBy("parentId", id);
			if(list_1.size()>0)
				return 2;
		}
		districtDao.delete(id_1);
		return 1;
		/*Long[] id_1 = ArrayUtils.toObject((long[])ConvertUtils.convert(ids.split(", "), Long.TYPE));
		List<District> list_1 = new ArrayList<District>();
		List<District> list_2 = new ArrayList<District>();
		for(Long id : id_1){
			list_2 = districtDao.findBy("parentId", id);
			list_1.addAll(list_2);
		}
		Long[] id_2 = new Long[list_1.size()];
		for(int i=0; i<list_1.size();i++){
			id_2[i] = list_1.get(i).getId();
		}
		Long[] id_3 = (Long[])ArrayUtils.addAll(id_1, id_2);
		list_1 = null;
		list_2 = null;
		id_2 = null;
		districtDao.delete(id_3);*/
		
	}
	
	@Override
	@Transactional
	public String addDistrict(String name) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", name);
		District  district = districtDao.findUniqueDistrict(param);
		if(null != district) 
			return "0";
		District d = new District();
		d.setName(name);
		d.setParentId(0L);
		districtDao.store(d);
		District dd = districtDao.findUniqueDistrict(param);
		return dd.getId() + "," + dd.getName();
	}
	
	@Override
	@Transactional
	public String addCountry(Map<String, String> param){
		District  d = districtDao.findUniqueDistrict(param);
		if(null != d ) return "0";
		District district = new District();
		district.setName(param.get("name"));
		district.setParentId(Long.parseLong(param.get("recordKey")));
		districtDao.store(district);
		return "1";
	}
	
	@Override
	@Transactional
	public int districtDeleteCountry(String ids){
		Long[] id_1 = ArrayUtils.toObject((long[])ConvertUtils.convert(ids.split(", "), Long.TYPE));
		List<Team> list_1 = null;
		for(Long id : id_1){
			list_1 = teamDao.findBy("countryId", id);
			if(list_1.size() >0)
				return 3;
		}
		districtDao.delete(id_1);
		return 1;
		
	}
	
	@Override
    @Transactional
	public String updateDistrict(Map<String, String> param) {
		Long id = Long.parseLong((String)param.get("recordKey"));
		District d = districtDao.read(id);
		if(d.getName().equals((String)param.get("district_text")))
			return "0";
		d.setName((String)param.get("district_text"));
		districtDao.store(d);
		return "1";
	}
	
	//----------------------------------------对阵----------------------------------------
	/**保存对阵信息,详情见表T_AGAINST*/
	@Transactional
	public Long saveAgainst(Against against)throws DaoException{
		return againstDao.saveObject(against);
	}
	
	/**删除对阵信息*/
	@Transactional
	public void deleteAgainst(Long id)throws DaoException{
		againstDao.delete(id);
	}
	
    public List<Against>  findAgainstList(Map<String, Object> params){
		return null;
	}
    
    //----------------------------------------球队----------------------------------------
    /**根据国家ID查询所有球队 
	 * 参数：
	 * countryId：国家ID
	 */
    public List<Team> findTeamList(Map<String, Object> params){
    	return this.teamDao.findTeamList(params);
    }
    
    /**根据球队主键查询球队信息*/
    public Team loadTeam(Long id){
    	return teamDao.read(id);
    }
    
    /**修改球队信息*/
    @SuppressWarnings("deprecation")
	@Transactional
    public void updateTeam(String sql)throws DaoException{
    	 teamDao.updateBySQL(sql);
    }
    
    @Transactional
    public void deleteTeam(Long id)throws DaoException{
   	 teamDao.delete(id);
   }
    
    @Override
    @Transactional
    public String addTeam(Map<String, String> param){
    	Object team_text = param.get("team_text");
    	Team t = teamDao.findUniqueByName(param);
    	if(null != t)return "0";
    	Team team = new Team();
    	team.setName((String)team_text);
    	teamDao.store(team);
    	return "1";
    }

	@Override
	public List<DistrictVo> findAllDistrictCount() {
		return districtDao.findAllDistrictCount();
	}
	
	@Override
	@Transactional
	public int teamAdd(Team team){
		team.setCountry(districtDao.read(team.getCountryId()).getName());
		team.setDistrict(districtDao.read(team.getDistrictId()).getName());
		teamDao.store(team);
		return 1;
	}
	
	@Override
	@Transactional
	public Map<String,Object> teamPreUpdate(String recordKey ){
		Team team = teamDao.read(Long.parseLong(recordKey));
		District d = districtDao.read(team.getCountryId());
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("disList_big", districtDao.findBy("parentId", 0L));
		param.put("disList", districtDao.findBy("parentId", d.getParentId()));
		param.put("team", team);
		return param;
	}
	
	@Override
	@Transactional
	public int teamUpdate(Team team,String recordKey){
		Team old_team = teamDao.read(Long.parseLong(recordKey));
		PropertyCopyUtil.copyProperties(team,old_team,
				new String[]{"name","districtId","countryId","foundDate","city","webSite",
				"contactAddr","stadium","stadiumVolume","type","fullName","email",
				"introduction","honor","specialty"});
		old_team.setCountry(districtDao.read(team.getCountryId()).getName());
		old_team.setDistrict(districtDao.read(team.getDistrictId()).getName());
		teamDao.store(old_team);
		return 1;
	}
	
	@Override
	@Transactional
	public String teamDelete(String deleteFlag){
		Long[] id_1 = ArrayUtils.toObject((long[])ConvertUtils.convert(deleteFlag.split(", "), Long.TYPE));
		for(Long id : id_1){
			Long i = againstDao.findTeamsById(id,null);
			if(i != 0)
				return "2";
		}
		teamDao.delete(id_1);
		return "1";
	}
	
	@Override
	public Team teamDetail(String deleteFlag) {
		return teamDao.read(Long.parseLong(deleteFlag));
	}

	//----------------------------------------联赛----------------------------------------
	/**保存 联赛杯赛信息 以及赛季 轮次等  这里是个树形结构*/
	@Transactional(rollbackFor = DaoException.class)
	public Long saveRace(Race race)throws DaoException{
		return raceDao.saveObject(race);
	}
	/**根据主键删除 赛事  赛季（届） 轮次 信息*/
	@Transactional
	public void deleteRace(Long id)throws DaoException{
		raceDao.delete(id);
	}
	
	/**
	 * 查询所有联赛或者杯赛 对应表:T_RACE 判断 PARENT_ID 为空
	 * @return
	 * @throws DaoException
	 */
	public List<Race> findAllRace()throws DaoException{
		return this.raceDao.findAllRace();
	}
	
	/**
	 * 前台页面查询所有联赛或者杯赛
	 * 1.民间用户管理自己的心水   USERID
     *2.民间高手搜索对阵列表  
     *3.普通用户管理心水订单 USERID
     *4.民间高手销售列表 USERID
      *5.购买心水,可能未登录
	 */
	public List<Race> findSiteRaceList(Map params)throws DaoException{
		return raceDao.findSiteRaceList(params);
	}
	
	/**
	 * 查询对应的赛季  轮次  对应表:T_RACE  三级树形菜单
	 * @param parentId
	 * @return
	 * @throws DaoException
	 */
	public List<Race> findByParent(Long parentId)throws DaoException{
		return this.raceDao.findByParent(parentId);
	}
	
	@Override
	public List<DistrictVo> findAllLeagueRaceCount(Map<String,String> param) {
		if(StringUtils.isBlank(param.get("district_select")) && 
				StringUtils.isBlank(param.get("country_select")) &&
				StringUtils.isBlank(param.get("race_name")))
			return null;
		List<DistrictVo> r_result = raceDao.findAllLeagueRaceCount(param);
		return r_result;
	}
	
	@Override
	@Transactional
	public String leagueRaceDeleteRace(String ids) {
		Long[] id_1 = ArrayUtils.toObject((long[])ConvertUtils.convert(ids.split(", "), Long.TYPE));
		List<Race> list_1 = null;
		for(Long id : id_1){
			list_1 = raceDao.findRaceByTree("1", id);
			if(list_1.size()>0)
				return "2";
		}
		raceDao.delete(id_1);
		return "1";
	}

	@Override
	@Transactional
	public String leagueRaceUpdateName(Map<String, String> param) {
		Long id = Long.parseLong((String)param.get("recordKey"));
		Race race = raceDao.read(id);
		Map<String,String> r_param = new HashMap<String,String>();
		r_param.put("district_select", race.getDistrictId()+"");
		r_param.put("country_select", race.getCountryId()+"");
		r_param.put("race_name", (String)param.get("race_name"));
		if(null != race.getParentId())
			r_param.put("parentId", race.getParentId()+"");
		Race r = raceDao.findUniqueLeagueRace(r_param);
		r_param = null;
		if(null != r ){return "0";}
		race.setName((String)param.get("race_name"));
		raceDao.store(race);
		return "1";
	}
	
	@Override
	@Transactional
	public String leagueRaceAddSession(Map<String,String> param){
		Long race_pid = Long.parseLong(param.get("raceId"));
		Race r = raceDao.read(race_pid);
		Long district_select = r.getDistrictId();
		Long country_select = r.getCountryId();
		param.put("district_select", district_select + "");
		param.put("country_select", country_select + "");
		String session_name = param.get("session_name");
		param.put("race_name", session_name);
		param.put("parentId", r.getId()+"");
		Race r2 = raceDao.findUniqueLeagueRace(param);
		if(null != r2 && null != r2.getParentId())return "2";
		Race race = new Race();
		race.setDistrict(r.getDistrict());
		race.setDistrictId(r.getDistrictId());
		race.setCountry(r.getCountry());
		race.setCountryId(r.getCountryId());
		race.setParentId(r.getId());
		race.setType("1");
		race.setName(session_name);
		raceDao.store(race);
		race = null;
		return "1";
	}
	
	@Override
	@Transactional
	public String leagueRaceAdd(Map<String, String> param) {
		Long district_select = Long.parseLong(param.get("district_select"));
		Long country_select = Long.parseLong(param.get("country_select"));
		String race_name = param.get("race_name");
		Race r = raceDao.findUniqueLeagueRace(param);
		if(null != r)return "0";
		Race race = new Race();
		race.setDistrict(districtDao.read(district_select).getName());
		race.setDistrictId(district_select);
		race.setCountry(districtDao.read(country_select).getName());
		race.setCountryId(country_select);
		race.setName(race_name);
		race.setType("1");
		raceDao.store(race);
		return "1";
	}

	@Override
	@Transactional
	public String leagueRaceAddWheel(Map<String, String> param) {
		Long s_id = Long.parseLong(param.get("race_season"));
		String s_name = param.get("wheel_name");
		Race s_race = raceDao.read(s_id);
		List<Race> s_list = raceDao.findBy("parentId", s_id);
		for(Race r : s_list){
			if(r.getName().equals(s_name))return "0";
		}
		Race race = new Race();
		race.setName(s_name);
		race.setDistrict(s_race.getDistrict());
		race.setDistrictId(s_race.getDistrictId());
		race.setCountry(s_race.getCountry());
		race.setCountryId(s_race.getCountryId());
		race.setParentId(s_id);
		race.setType("1");
		raceDao.store(race);
		return "1";
	}
	
	@Override
	@Transactional
	public int leagueRaceDeleteLeagueWheel(String ids) {
		Long[] id = ArrayUtils.toObject((long[])ConvertUtils.convert(ids.split(", "), Long.TYPE));
		raceDao.delete(id);
		return 1;
	}
	
	//----------------------------------------杯赛----------------------------------------
	@Override
	@Transactional
	public String cupRaceAdd(Map<String, String> param) {
		String cup_name = param.get("cup_name");
		String cup_prefix = param.get("cup_prefix");
		Race r = raceDao.findUniqueCupRace(param);
		if(null != r && null == r.getParentId()) return "0";
		Race race = new Race();
		race.setName(cup_name);
		race.setPrefix(cup_prefix);
		race.setType("2");
		raceDao.store(race);
		return "1";
	}

	@Override
	@Transactional
	public String cupRaceDeleteRace(String ids) {
		Long[] id_1 = ArrayUtils.toObject((long[])ConvertUtils.convert(ids.split(", "), Long.TYPE));
		List<Race> list_1 = null;
		for(Long id : id_1){
			list_1 = raceDao.findRaceByTree("2", id);
			if(list_1.size()>0)
				return "2";
		}
		raceDao.delete(id_1);
		return "1";
	}
	
	@Override
	@Transactional
	public String cupRaceDeleteSchedule(String deleteFlag) {
		raceDao.delete(ArrayUtils.toObject((long[])ConvertUtils.convert(deleteFlag.split(", "), Long.TYPE)));
		return "1";
	}

	@Override
	@Transactional
	public String cupRaceUpdateName(Map<String, String> param) {
		Long id = Long.parseLong((String)param.get("recordKey"));
		Race race = raceDao.read(id);
		Map<String,String> r_param = new HashMap<String,String>();
		r_param.put("cup_name", (String)param.get("cup_name"));
		if(null != race.getParentId())
			r_param.put("parentId", race.getParentId()+"");
		Race r = raceDao.findUniqueCupRace(r_param);
		r_param = null;
		if(null != r ){return "0";}
		race.setName((String)param.get("cup_name"));
		raceDao.store(race);
		return "1";
	}

	@Override
	@Transactional
	public String dueRaceAdd(Map<String, String> param) {
		String due_name = param.get("due_name");
		Race race = new Race();
		race.setName(due_name);
		race.setParentId(Long.parseLong(param.get("cupId")));
		race.setType("2");
		raceDao.store(race);
		return "1";
	}

	@Override
	@Transactional
	public String scheduleRaceAdd(Map<String, String> param) {
		String schedule_name = param.get("schedule_name");
		Race race = new Race();
		race.setName(schedule_name);
		race.setParentId(Long.parseLong(param.get("scheduleId")));
		race.setType("2");
		raceDao.store(race);
		return "1";
	}

	//-----------------------------联赛球队--------------------------------
	@Override
	public List<Race> findLeagueByCountry(String country_select) {
		return raceDao.findLeagueByCountry(Long.parseLong(country_select));
	}

	@Override
	public List<Race> findTeamByLeague(String race_season) {
		return raceDao.findBy("parentId", Long.parseLong(race_season));
	}
	
	@Override
	public List<DistrictVo> findAllLeagueTeamCount(Map<String, String> param) {
		if(StringUtils.isBlank(param.get("district_select")) && 
				StringUtils.isBlank(param.get("country_select")) &&
				StringUtils.isBlank(param.get("league_select")))
			return null;
		return raceDao.findAllLeagueTeamCount(param);
	}

	@Override
	public Page findLeagueTeamBySession(Limit limit, Map<String, String> param) {
		return raceDao.findLeagueTeamBySession(limit,param);
	}

	@Override
	@Transactional
	public String leagueTeamAddTeam(Map<String,String> param) {
		Long race_season = Long.parseLong(param.get("race_season"));
		Race r = raceDao.read(race_season);
		List<TeamRace> trOldList = teamRaceDao.findBy("raceSeasonId", race_season);
		Long[] id_long = ArrayUtils.toObject((long[])ConvertUtils.convert(param.get("team_text").split(","), Long.TYPE));
		List<Team> teamList = null;
		List<TeamRace> traceList = new ArrayList<TeamRace>();
		TeamRace tr = null;
		if(trOldList.size()==0){
			teamList = teamDao.read(id_long);
			for(Team team : teamList){
				tr = new TeamRace();
				tr.setAreaId(team.getDistrictId());
				tr.setAreaName(team.getDistrict());
				tr.setCountryId(team.getCountryId());
				tr.setCountryName(team.getCountry());
				tr.setRaceId(r.getParentId());
				tr.setRaceSeasonId(race_season);
				tr.setRaceSeasonName(r.getName());
				tr.setTeamId(team.getId());
				tr.setTeamName(team.getName());
				tr.setType("1");
				traceList.add(tr);
			}
			teamRaceDao.storeAll(traceList);
			return "1";
		}
		Set<Long> tempOldSet = new HashSet<Long>();
		Long[] tempNoExist = new Long[10];
		for(TeamRace trOld : trOldList){
			tempOldSet.add(trOld.getTeamId());
		}
		int num = 0;
		for(Long id : id_long){
			if(!tempOldSet.contains(id))
				tempNoExist[num++] =id;
		}
		
		teamList = teamDao.read(tempNoExist);
		for(Team team : teamList){
			tr = new TeamRace();
			tr.setAreaId(team.getDistrictId());
			tr.setAreaName(team.getDistrict());
			tr.setCountryId(team.getCountryId());
			tr.setCountryName(team.getCountry());
			tr.setRaceId(r.getParentId());
			tr.setRaceSeasonId(race_season);
			tr.setRaceSeasonName(r.getName());
			tr.setTeamId(team.getId());
			tr.setTeamName(team.getName());
			tr.setType("1");
			traceList.add(tr);
		}
		teamRaceDao.storeAll(traceList);
		tempOldSet = null;
		tempNoExist = null;
		return "1";
	}

	@Override
	@Transactional
	public String deleteLeagueTeam(String ids) {
		Long[] id_l = ArrayUtils.toObject((long[])ConvertUtils.convert(ids.split(", "), Long.TYPE));
		for(Long id : id_l){
			TeamRace tr = teamRaceDao.read(id);
			Long againsts = againstDao.findTeamsById(tr.getTeamId(),tr.getRaceSeasonId());
			if(0 != againsts)
				return "2";
		}
		teamRaceDao.delete(id_l);
		return "1";
	}
	//=======================ADDED BY wjj   2010-02-25 15:46==============
	@SuppressWarnings("unchecked")
	public List findRaceList(String p){
		return this.raceDao.findRaceList(p);
	}
	

	
	//-----------------------------杯赛球队--------------------------------
	@Override
	public List<Race> findCupByPrefix(Map<String, String> param) {
		return raceDao.findCupByPrefix(param);
	}

	@Override
	public List<Race> findCupDue(Map<String, String> param) {
		return raceDao.findBy("parentId", Long.parseLong(param.get("cupId")));
	}
	
	@Override
	public Page findCupTeam(Limit limit, Map<String, String> param) {
		return raceDao.findCupTeam(limit,param);
	}

	@Override
	@Transactional
	public String cupTeamAddTeam(Map<String, String> param) {
		Long raceId = Long.parseLong(param.get("cupId"));
		String team_name = param.get("team_text");
		Team team = teamDao.findUniqueBy("name", team_name);
		if(null == team)
			return "0";
		List<TeamRace> trList = teamRaceDao.findBy("raceSeasonId", raceId);
		for(TeamRace teamRace : trList){
			if(teamRace.getTeamName().equals(param.get("team_text")))
				return "2";
		}
		TeamRace tr = new TeamRace();
		Race race = raceDao.read(raceId);
		tr.setRaceId(race.getParentId());
		tr.setRaceSeasonId(raceId);
		tr.setRaceSeasonName(race.getName());
		tr.setTeamId(team.getId());
		tr.setTeamName(team.getName());
		tr.setType("2");
		teamRaceDao.store(tr);
		return "1";
	}
	
	@Override
	public Page findLeagueAgainstList(Limit limit, Map<String, String> param) {
		return againstDao.findLeagueAgainstList(limit,param);
	}

	@Override
	public List<TeamRace> leagueAgainstFindTeamAjax(String race_season) {
		return teamRaceDao.findBy("raceSeasonId", Long.parseLong(race_season));
	}

	@Override
	@Transactional
	public String leagueAgainstAdd(Map<String, String> param) {
		Long hostId = Long.parseLong(param.get("hostName"));
		Long guestId = Long.parseLong(param.get("guestName"));
		Long leagueRaceId = Long.parseLong(param.get("league_select"));
		Long sessionRaceId = Long.parseLong(param.get("race_season"));
		Long roundId = Long.parseLong(param.get("wheel_select"));
		String ids = leagueRaceId+","+sessionRaceId+","+roundId+",";
		List<Race> raceList = raceDao.read(ArrayUtils.toObject((long[])ConvertUtils.convert(ids.split(","), Long.TYPE)));
		StringBuilder raceName = new StringBuilder();
		StringBuilder raceSeasonName = new StringBuilder();
		StringBuilder roundName = new StringBuilder();
		for(Race race : raceList){
			if(race.getId().equals(leagueRaceId))raceName.append(race.getName());
			if(race.getId().equals(sessionRaceId))raceSeasonName.append(race.getName());
			if(race.getId().equals(roundId))roundName.append(race.getName());
		}
		Against against = new Against();
		against.setRaceId(leagueRaceId);
		against.setRaceName(raceName.toString());
		against.setHostId(hostId);
		against.setHostName(teamDao.read(hostId).getName());
		against.setGuestId(guestId);
		against.setGuestName(teamDao.read(guestId).getName());
		against.setAreaId(Long.parseLong(param.get("district_select")));
		against.setCountryId(Long.parseLong(param.get("country_select")));
		against.setRaceSeasonId(sessionRaceId);
		against.setRaceSeasonName(raceSeasonName.toString());
		against.setRounds(Long.parseLong(param.get("wheel_select")));
		against.setRoundName(roundName.toString());
		against.setStartTime(DateUtil.parseDate(param.get("startTime")+":00","yyyy-MM-dd hh:mm:ss"));
		against.setType("1");
		against.setStatus("1");
		againstDao.store(against);
		hostId = null;
		guestId = null;
		leagueRaceId = null;
		sessionRaceId = null;
		roundId = null;
		raceList = null;
		raceName = null;
		raceSeasonName = null;
		roundName = null;
		return "1";
	}

	@Override
	@Transactional
	public void leagueAgainstDelete(String deleteFlag) {
		againstDao.delete(ArrayUtils.toObject((long[])ConvertUtils.convert(deleteFlag.split(", "), Long.TYPE)));
	}

	@Override
	public Against leagueAgainstPreUpdate(String recordKey) {
		return againstDao.read(Long.parseLong(recordKey));
	}

	@Override
	@Transactional
	public String leagueAgainstUpdate(Against against) {
		Against again =  againstDao.read(against.getAgainstId());
		Team host = teamDao.read(against.getHostId());
		Team guest = teamDao.read(against.getGuestId());
		again.setHostId(host.getId());
		again.setGuestId(guest.getId());
		again.setHostName(host.getName());
		again.setGuestName(guest.getName());
		
		if(null != against.getStartTime()) again.setStartTime(against.getStartTime());
		if(StringUtils.isNotBlank(against.getAsiaPei()))again.setAsiaPei(against.getAsiaPei());
		if(StringUtils.isNotBlank(against.getBigSmall()))again.setBigSmall(against.getBigSmall());
		if(StringUtils.isNotBlank(against.getStatus()))again.setStatus(against.getStatus());
		if(StringUtils.isNotBlank(against.getScore()))again.setScore(against.getScore());
		if(StringUtils.isNotBlank(against.getScoreA()))again.setScoreA(against.getScoreA());
		if(StringUtils.isNotBlank(against.getScoreB()))again.setScoreB(against.getScoreB());
		//if(StringUtils.isNotBlank(against.getRaceResult()))again.setRaceResult(against.getRaceResult());
		if(StringUtils.isNotBlank(against.getConcede()))again.setConcede(against.getConcede());
		//if(StringUtils.isNotBlank(against.getConcedeResult()))again.setConcedeResult(against.getConcedeResult());
		String score = against.getScore();
		if(StringUtils.isNotBlank(score)){
			Integer result = Integer.valueOf(score.substring(0, score.indexOf(":")))
				- Integer.valueOf(score.substring(score.indexOf(":")+1,score.length()));
			if(result == 0)
			again.setRaceResult("1");
			else if (result > 0){
			again.setRaceResult("3");
			}else
			again.setRaceResult("0");
		}
		againstDao.store(again);
		return "1";
	}

	@Override
	public Page findCupAgainstTeam(Limit limit, Map<String, String> param) {
		return againstDao.findCupAgainstTeam(limit,param);
	}

	@Override
	@Transactional
	public String cupAgainstAdd(Map<String, String> param) {
		Long hostId = Long.parseLong(param.get("hostName"));
		Long guestId = Long.parseLong(param.get("guestName"));
		Long cupRaceId = Long.parseLong(param.get("raceId"));
		Long sessionRaceId = Long.parseLong(param.get("race_season"));
		Long roundId = Long.parseLong(param.get("wheelId"));
		String ids = cupRaceId+","+sessionRaceId+","+roundId+",";
		
		List<Race> raceList = raceDao.read(ArrayUtils.toObject((long[])ConvertUtils.convert(ids.split(","), Long.TYPE)));
		StringBuilder raceName = new StringBuilder();
		StringBuilder raceSeasonName = new StringBuilder();
		StringBuilder roundName = new StringBuilder();
		for(Race race : raceList){
			if(race.getId().equals(cupRaceId))raceName.append(race.getName());
			if(race.getId().equals(sessionRaceId))raceSeasonName.append(race.getName());
			if(race.getId().equals(roundId))roundName.append(race.getName());
		}
		Against against = new Against();
		against.setRaceId(cupRaceId);
		against.setRaceName(raceName.toString());
		against.setHostId(hostId);
		against.setHostName(teamDao.read(hostId).getName());
		against.setGuestId(guestId);
		against.setGuestName(teamDao.read(guestId).getName());
		against.setRaceSeasonId(sessionRaceId);
		against.setRaceSeasonName(raceSeasonName.toString());
		against.setRounds(Long.parseLong(param.get("wheelId")));
		against.setRoundName(roundName.toString());
		against.setStartTime(DateUtil.parseDate(param.get("startTime")+":00","yyyy-MM-dd hh:mm:ss"));
		against.setType("2");
		against.setStatus("1");
		againstDao.store(against);
		hostId = null;
		guestId = null;
		cupRaceId = null;
		sessionRaceId = null;
		roundId = null;
		raceList = null;
		raceName = null;
		raceSeasonName = null;
		roundName = null;
		return "1";
	}

	

}
