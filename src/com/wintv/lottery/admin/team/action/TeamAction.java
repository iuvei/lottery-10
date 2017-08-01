package com.wintv.lottery.admin.team.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.ecside.table.limit.Limit;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.common.Constants;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.page.ExtremeTablePage;
import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.District;
import com.wintv.framework.pojo.Race;
import com.wintv.framework.pojo.Team;
import com.wintv.framework.pojo.TeamRace;
import com.wintv.framework.utils.DateUtil;
import com.wintv.lottery.admin.team.service.RaceService;
import com.wintv.lottery.admin.team.vo.DistrictVo;

@SuppressWarnings("serial")
public class TeamAction extends BaseAction{
	
	public String index(){ return "index"; }
	
	public String left(){ return "left"; }
	
	//区域列表
	public String districtList(){
		Map<String,String> param = null;
		//查询所有区域里所有国家的所有球队数量
		topDis = raceService.findAllDistrictCount();
		//分页查询,为国家分页查询做预期准备
		Limit limit = ExtremeTablePage.getLimit(request,"ec01",5);
		Page page = raceService.findAllDistrict(limit,param);
		request.setAttribute("disList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		return "index";
	}
	
	//国家列表
	public String districtCountryList(){
		if(StringUtils.isNotBlank(district_select))
			recordKey = district_select.split(",")[0];
		if(StringUtils.isBlank(recordKey))
			return "index";
		String district_id = recordKey;
		Map<String,String> param = null;
		if(StringUtils.isNotBlank(district_id)){
			param = new HashMap<String,String>();
			param.put("district_id", district_id);
		}
		Limit limit = ExtremeTablePage.getLimit(request,"ec02",10);
		Page page = raceService.findAllDistrict(limit,param);
		request.setAttribute("countryList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		return "index";
	}
	
	//球队列表
	public 	String teamList(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("district_select", district_select);
		param.put("country_select", country_select);
		param.put("team_text", team_text);
		param.put("team_type", team_type);
		Limit limit = ExtremeTablePage.getLimit(request,"ec01",10);
		Page page = raceService.findAllTeam(limit,param);
		request.setAttribute("teamList", page.getResult());
		disList_big = raceService.findBigDistrict();
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		return "index";
	}
	
	//球队管理
	public String teamManagerList(){
		Map<String,Object> param = new HashMap<String,Object>();
		Limit limit = ExtremeTablePage.getLimit(request,"ec",10);
		param.put("country_select", country_select);
		param.put("raceId", raceId);
		param.put("race_season", race_season);
		param.put("team_type", team_type);
		param.put("page_size", limit.getCurrentRowsDisplayed());
		param.put("page_no", limit.getPage());
		if(null != limit.getSort().getProperty() && limit.getSort().getProperty().indexOf("ShowingRate") != -1){
			param.put("sort", limit.getSort().getProperty());
			param.put("sortOrder", limit.getSort().getSortOrder());
		}
		Page page = raceService.findAllTeamManager(param);
		request.setAttribute("teamManagerList", page.getResult());
		disList_big = raceService.findBigDistrict();
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		param = null;
		return "index";
	}
	
	//对阵管理
	public String againstList(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("startTime", startTime);
		param.put("isConcede", isConcede);
		param.put("race_name", race_name);
		param.put("race_season", race_season);
		param.put("wheel_select", wheel_select);
		param.put("hostName", hostName);
		param.put("guestName", guestName);
		Limit limit = ExtremeTablePage.getLimit(request,"ec",10);
		Page page = raceService.againstList(limit,param);
		request.setAttribute("againstList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		return "index";
	}
	
	
	//联赛列表
	public String leagueRaceList(){
		disList_big = raceService.findBigDistrict();
		if(StringUtils.isBlank(country_select))
			return "index";
		Map<String,String> param = new HashMap<String,String>();
		param.put("district_select", district_select);
		param.put("country_select", country_select);
		param.put("race_name", race_name);
		Limit limit = ExtremeTablePage.getLimit(request,"ec01",5);
		Page page = raceService.findAllLeagueRace(limit,param);
		request.setAttribute("leagueRaceList", page.getResult());
		//查询某一区域里某一国家的所有联赛总数
		topDis = raceService.findAllLeagueRaceCount(param);
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		recordKey = null;
		return "index";
	}
	
	//赛季列表
	public String leagueRaceSessionList(){
		disList_big = raceService.findBigDistrict();
		if(StringUtils.isBlank(recordKey))
			return "index";
		if(StringUtils.isNotBlank(raceId))
			recordKey = raceId.split(",")[0];
		Map<String,String> param = new HashMap<String,String>();
		param.put("recordKey", recordKey);
		Limit limit = ExtremeTablePage.getLimit(request,"ec02",5);
		Page page = raceService.findAllLeagueRace(limit,param);
		request.setAttribute("sessionRaceList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		recordKey = null;
		return "index";
	}
	
	//轮次列表
	public String leagueRaceLeagueWheelView(){
		if(StringUtils.isBlank(recordKey))return leagueRaceList();
		Map<String,String> param = new HashMap<String,String>();
		param.put("recordKey", recordKey);
		Limit limit = ExtremeTablePage.getLimit(request,"ec03",10);
		Page page = raceService.findLRWheel(limit,param);
		request.setAttribute("leagueWheelList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		param = null;
		return leagueRaceList();
	}
	
	//杯赛列表
	public String cupRaceList(){
		cup_prefix_map = getCup_prefix_map();
		Map<String,String> param = new HashMap<String,String>();
		param.put("cup_prefix", cup_prefix);
		Limit limit = ExtremeTablePage.getLimit(request,"ec_c1",5);
		Page page = raceService.findCupRace(limit,param);
		request.setAttribute("cupRaceList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		param = null;
		return "index";
	}
	
	//届列表
	public String cupRaceDueList(){
		cup_prefix_map = getCup_prefix_map();
		if(StringUtils.isNotBlank(cupId))
			recordKey = cupId.split(",")[0];
		if(StringUtils.isBlank(recordKey))
			return "index";
		Map<String,String> param = new HashMap<String,String>();
		param.put("recordKey", recordKey);
		//request.setAttribute("cupId", recordKey);
		Limit limit = ExtremeTablePage.getLimit(request,"ec_c2",5);
		Page page = raceService.findCupRace(limit,param);
		request.setAttribute("cupRaceDueList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		param = null;
		recordKey = null;
		return "index";
	}
	
	//赛程列表
	public String cupRaceScheduleList(){
		if(StringUtils.isNotBlank(scheduleId))
			recordKey = scheduleId.split(",")[0];
		if(StringUtils.isBlank(recordKey))
			return "index";
		Map<String,String> param = new HashMap<String,String>();
		param.put("recordKey", recordKey);
		Limit limit = ExtremeTablePage.getLimit(request,"ec_c3",5);
		Page page = raceService.findCupRace(limit,param);
		request.setAttribute("scheduleList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		param = null;
		recordKey = null;
		return "index";
	}
	
	//联赛球队
	public String leagueTeamList(){
		disList_big = raceService.findBigDistrict();
		Map<String,String> param = new HashMap<String,String>();
		param.put("district_select", district_select);
		param.put("country_select", country_select);
		param.put("league_select", league_select);
		if(StringUtils.isNotBlank(recordKey))
			param.put("recordKey", recordKey.split(",")[0]);
		Limit limit = ExtremeTablePage.getLimit(request,"ec01",10);
		Page page = raceService.findAllLeagueTeam(limit,param);
		request.setAttribute("leagueTeamList", page.getResult());
		//查询某一区域里某一国家的所有联赛总数
		topDis = raceService.findAllLeagueTeamCount(param);
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		recordKey = null;
		return "index";
	}
	
	//杯赛球队
	public String cupTeamList(){
		cup_prefix_map = getCup_prefix_map();
		if(StringUtils.isBlank(cupId))
			return "index";
		Map<String,String> param = new HashMap<String,String>();
		param.put("cupId", cupId);
		Limit limit = ExtremeTablePage.getLimit(request,"ec",10);
		Page page = raceService.findCupTeam(limit,param);
		request.setAttribute("cupTeamList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		param = null;
		return "index";
	}
	
	//联赛队阵
	public String leagueAgainstList(){
		disList_big = raceService.findBigDistrict();
		Map<String,String> param = new HashMap<String,String>();
		param.put("district_select", district_select);
		param.put("country_select", country_select);
		param.put("league_select", league_select);
		param.put("race_season", race_season);
		param.put("wheel_select", wheel_select);
		param.put("hostName", hostName);
		param.put("guestName", guestName);
		param.put("startTime", startTime);
		Limit limit = ExtremeTablePage.getLimit(request,"ec",10);
		Page page = raceService.findLeagueAgainstList(limit,param);
		request.setAttribute("leagueAgainstList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		param = null;
		return "index";
	}
	
	//杯赛队阵
	public String cupAgainstList(){
		cup_prefix_map = getCup_prefix_map();
		Map<String,String> param = new HashMap<String,String>();
		param.put("raceId", raceId);
		param.put("race_season", race_season);
		param.put("hostName", hostName);
		param.put("guestName", guestName);
		param.put("startTime", startTime);
		if(null != wheelId)
			param.put("wheelId", wheelId+"");
		Limit limit = ExtremeTablePage.getLimit(request,"ec",10);
		Page page = raceService.findCupAgainstTeam(limit,param);
		request.setAttribute("cupAgainstList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		param = null;
		return "index";
	}
	//------------------------------------------------------区域国家-------------------------------------
	//增加 区域
	public void districtAddDistrict(){
		String rusult = raceService.addDistrict(district_text);
		ajaxForAction(rusult);
	}
	
	//删除 区域
	public void districtDeleteDis(){
		try {
			int flag = raceService.districtDeleteDis(deleteFlag);
			ajaxForAction(flag+"");
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	//更新区域
	public void districtUpdateDistrict(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("recordKey", recordKey);
		param.put("district_text", district_text);
		ajaxForAction(raceService.updateDistrict(param));
		param = null;
	}
	
	//增加 国家
	public void districtAddCountry(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("recordKey", district_select.split(",")[0]);
		param.put("name", country_text);
		ajaxForAction(raceService.addCountry(param));
		param = null;
	}
	
	//删除区域下的国家
	public void districtDeleteCountry(){
		int flag = raceService.districtDeleteCountry(deleteFlag);
		ajaxForAction(flag+"");
	}
	
	//-----------------------------------------------------球队--------------------------------
	//添加球队
	public void districtAddTeam(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("district_select", district_select);
		param.put("country_select", country_select);
		param.put("team_text", team_text);
		ajaxForAction(raceService.addTeam(param));
		param = null;
	}
	
	//增加球队页面跳转
	public String teamPreAdd(){
		disList_big = raceService.findBigDistrict();
		if(null != team)
			disList = raceService.findSmallDisByBig(team.getDistrictId());
		return "preAdd";
	}
	
	//增加球队
	public String teamAdd(){
		int flag = raceService.teamAdd(team);
		generateResult(flag, "", null);
		return teamPreAdd();
	}
	
	//更新球队信息页面跳转
	@SuppressWarnings("unchecked")
	public String teamPreUpdate(){
		Map<String,Object> param = raceService.teamPreUpdate(recordKey);
		disList_big = (List<District>)param.get("disList_big");
		disList = (List<District>)param.get("disList");
		team = (Team)param.get("team");
		return "preUpdate";
	}
	
	//更新球队
	public String teamUpdate(){
		result = null;
		int flag = raceService.teamUpdate(team,recordKey);
		generateResult(flag, "", null);
		return teamPreUpdate();
	}
	
	//删除球队
	public void teamDelete(){
		ajaxForAction(raceService.teamDelete(deleteFlag));
	}
	
	//查看球队详细
	public String teamDetail(){
		team = raceService.teamDetail(recordKey);
		return "detail";
	}
	//------------------------------------------联赛----------------------------------------------
	//新增联赛  如:中超
	public void leagueRaceAdd(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("district_select", district_select);
		param.put("country_select", country_select);
		param.put("race_name", race_name);
		ajaxForAction(raceService.leagueRaceAdd(param));
	}
	
	//新增赛季
	public void leagueRaceAddSession(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("raceId", raceId);
		param.put("session_name", session_name);
		String flag = raceService.leagueRaceAddSession(param);
		ajaxForAction(flag);
	}
	
	//联赛删除
	public void leagueRaceDeleteRace(){
		ajaxForAction(raceService.leagueRaceDeleteRace(deleteFlag));
	}
	
	//赛季删除
	public void leagueRaceDeleteSeason(){
		String flag = raceService.leagueRaceDeleteRace(deleteFlag);
		if(flag.equals("1")){
			ajaxForAction(flag);
		}else{
			ajaxForAction("3");
		}
	}
	//更新联赛名称
	public void leagueRaceUpdateName(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("race_name", race_name);
		param.put("recordKey", recordKey);
		String flag = raceService.leagueRaceUpdateName(param);
		ajaxForAction(flag);
		param = null;
	}
	
	//新增联赛轮次
	public void leagueRaceAddWheel(){
		if(StringUtils.isBlank(race_season))return;
		Map<String,String> param = new HashMap<String,String>();
		param.put("wheel_name", wheel_name);
		param.put("race_season", race_season);
		String flag = raceService.leagueRaceAddWheel(param);
		ajaxForAction(flag);
	}
	
	//删除轮次
	public void leagueRaceDeleteLeagueWheel(){
		ajaxForAction(raceService.leagueRaceDeleteLeagueWheel(deleteFlag)+"");
	}
	
	//---------------------------------------杯赛---------------------------------------------
	//新增杯赛
	public void cupRaceAddCup(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("cup_name", cup_name);
		param.put("cup_prefix", cup_prefix);
		ajaxForAction(raceService.cupRaceAdd(param));
		param = null;
	}
	//删除杯赛
	public void cupRaceDeleteRace(){
		ajaxForAction(raceService.cupRaceDeleteRace(deleteFlag));
	}
	//删除届
	public void cupRaceDeleteDue(){
		String flag = raceService.cupRaceDeleteRace(deleteFlag);
		if(flag.equals("1")){
			ajaxForAction(flag);
		}else
			ajaxForAction("3");
	}
	
	//删除赛程
	public void cupRaceDeleteSchedule(){
		ajaxForAction(raceService.cupRaceDeleteSchedule(deleteFlag));
	}
	
	//更新杯赛名称
	public void cupRaceUpdateName(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("cup_name", cup_name);
		param.put("recordKey", recordKey);
		ajaxForAction(raceService.cupRaceUpdateName(param));
		param = null;
	}
	//新增届
	public void cupRaceAddDue(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("due_name", due_name);
		param.put("cupId", cupId);
		ajaxForAction(raceService.dueRaceAdd(param));
		param = null;
	}
	//新增赛程
	public void cupRaceAddSchedule(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("schedule_name", schedule_name);
		param.put("scheduleId", scheduleId.split(",")[0]);
		ajaxForAction(raceService.scheduleRaceAdd(param));
		param = null;
	}
	
	//-------------------------------联赛球队------------------------------
	//联赛球队第二级联动
	public void leagueTeamLeagueAjax(){
		List<Race> rList = raceService.findLeagueByCountry(country_select);
		JSONArray js = new JSONArray();
		JSONObject obj = null;
		for(Race race : rList){
			obj = new JSONObject();
			obj.put("id", race.getId());
			obj.put("name", race.getName());
			js.add(obj);
		}
		ajaxForAction(js.toString());
	}
	//联赛球队第三级联动
	public void leagueTeamChooseTeamAjax(){
		List<Race> rList = raceService.findTeamByLeague(race_season);
		JSONArray js = new JSONArray();
		JSONObject obj = null;
		for(Race race : rList){
			obj = new JSONObject();
			obj.put("id", race.getId());
			obj.put("name", race.getName());
			js.add(obj);
		}
		ajaxForAction(js.toString());
	}
	
	//选择联赛球队页面
	public String leagueTeamPreAdd(){
		if(StringUtils.isBlank(race_season)){
			disList_big = raceService.findBigDistrict();
			return "addTeam";
		}
		Map<String,String> param = new HashMap<String,String>();
		param.put("race_season", race_season.split(",")[0]);
		param.put("team_type", team_type);
		Limit limit = ExtremeTablePage.getLimit(request,"ec",10);
		Page page = raceService.findLeagueTeamBySession(limit,param);
		request.setAttribute("teamList", page.getResult());
		limit.setRowAttributes(page.getTotalCount(), limit.getCurrentRowsDisplayed());
		return "addTeam";
	}
	
	//新增联赛球队
	public void leagueTeamAddTeam(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("race_season", race_season);
		param.put("team_text", team_text);
		ajaxForAction(raceService.leagueTeamAddTeam(param));
	}
	
	//删除联赛球队
	public void leagueTeamDelete(){
		ajaxForAction(raceService.deleteLeagueTeam(deleteFlag));
	}
	
	//-------------------------------杯赛球队------------------------------
	//按杯赛前缀查询杯赛
	public void cupTeamFindCupRaceAjax(){
		if(StringUtils.isBlank(cup_prefix))
			return ;
		Map<String,String> param = new HashMap<String,String>();
		param.put("cup_prefix", cup_prefix);
		List<Race> cupRaceList = raceService.findCupByPrefix(param);
		param = null;
		JSONArray js = new JSONArray();
		JSONObject obj = null;
		for(Race race : cupRaceList){
			obj = new JSONObject();
			obj.put("id", race.getId());
			obj.put("name", race.getName());
			js.add(obj);
		}
		ajaxForAction(js.toString());
	}
	
	//根据杯赛查询赛季
	public void cupTeamFindCupDueAjax(){
		if(StringUtils.isBlank(cupId))
			return ;
		Map<String,String> param = new HashMap<String,String>();
		param.put("cupId", cupId);
		List<Race> cupRaceList = raceService.findCupDue(param);
		param = null;
		JSONArray js = new JSONArray();
		JSONObject obj = null;
		for(Race race : cupRaceList){
			obj = new JSONObject();
			obj.put("id", race.getId());
			obj.put("name", race.getName());
			js.add(obj);
		}
		ajaxForAction(js.toString());
	}
	//新增杯赛球队
	public void cupTeamAddTeam(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("cupId", cupId);
		param.put("team_text", team_text);
		ajaxForAction(raceService.cupTeamAddTeam(param));
		param = null;
	}
	
	//-----------------------------联赛队阵--------------------------------
	//根据联赛赛季查询联赛球队
	public void leagueAgainstFindTeamAjax(){
		List<TeamRace> trList = raceService.leagueAgainstFindTeamAjax(race_season);
		JSONArray js = new JSONArray();
		JSONObject obj = null;
		for(TeamRace tr : trList){
			obj = new JSONObject();
			obj.put("teamId", tr.getTeamId());
			obj.put("teamName", tr.getTeamName());
			js.add(obj);
		}
		ajaxForAction(js.toString());
	}
	
	//新增联赛对阵
	public void leagueAgainstAdd(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("district_select", district_select);
		param.put("country_select", country_select);
		param.put("league_select", league_select);
		param.put("race_season", race_season);
		param.put("wheel_select", wheel_select);
		param.put("hostName", hostName);
		param.put("guestName", guestName);
		param.put("startTime", startTime);
		ajaxForAction(raceService.leagueAgainstAdd(param));
	}
	
	//删除联赛对阵
	public void leagueAgainstDelete(){
		raceService.leagueAgainstDelete(deleteFlag);
	}
	
	//跳转到编辑联赛对阵页面
	public String  leagueAgainstPreUpdate(){
		against = raceService.leagueAgainstPreUpdate(recordKey);
		hostList = raceService.leagueAgainstFindTeamAjax(against.getRaceSeasonId()+"");
		startTime = DateUtil.formatDateTimemm(against.getStartTime());
		guestList = hostList;
		return "edit";
	}
	
	//编辑联赛对阵
	public String leagueAgainstUpdate(){
		against.setStartTime(DateUtil.parseDate(startTime+":00", "yyyy-MM-dd HH:mm:ss"));
		generateResult(Integer.valueOf(raceService.leagueAgainstUpdate(against)), "", null);
		return leagueAgainstPreUpdate();
	}
	
	
	//-----------------------------杯赛队阵--------------------------------
	//杯赛球队AJAX查询
	public void cupAgainstFindCupTeamAjax(){
		List<Race> rList = raceService.findTeamByLeague(race_season);
		JSONArray js = new JSONArray();
		JSONObject obj = null;
		for(Race race : rList){
			obj = new JSONObject();
			obj.put("id", race.getId());
			obj.put("name", race.getName());
			js.add(obj);
		}
		ajaxForAction(js.toString());
	}
	
	//新增杯赛对阵
	public void cupAgainstAdd(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("raceId", raceId);
		param.put("race_season", race_season);
		param.put("wheelId", wheelId+"");
		param.put("hostName", hostName);
		param.put("guestName", guestName);
		param.put("startTime", startTime);
		ajaxForAction(raceService.cupAgainstAdd(param));
	}
	
	
	
	@Autowired
	private RaceService raceService;
	private List<DistrictVo> topDis;
	private List<District> disList_big;      //区域 select
	private List<District> disList;          //国家 select
	
	private String recordKey;
	private String deleteFlag;
	
	private String district_text;
	private String district_select;
	private String country_text;
	private String country_select;
	private String league_select;
	private String wheel_select;
	private Team team;
	private String team_text;                //球队名称
	private String team_type;
	
	private String raceId;                   //联赛ID
	private String race_name;                //联赛名称
	private String session_name;              //赛季名称
	private String race_season;              //赛季
	private Long   wheelId;                  //轮次Id
	private String wheel_name;               //轮次名称
	
	private String cupId;
	private String cup_name;                 //杯赛名称
	private String cup_prefix;               //杯赛首字母
	private String[] cup_prefixs;            //杯赛首字母集合
	private Map<String,String> cup_prefix_map;
	
	private String due_name;                 //届名称
	private String scheduleId;
	private String schedule_name;            //赛程名称
	
	private Against against;
	private String hostName;				//主队
	private String guestName;				//客队
	private List<TeamRace> hostList;
	private List<TeamRace> guestList;
	private String startTime;               //开赛时间
	private String endTime;
	private String isConcede;               //是否让球
	public List<DistrictVo> getTopDis() {
		return topDis;
	}
	public void setTopDis(List<DistrictVo> topDis) {
		this.topDis = topDis;
	}
	public List<District> getDisList_big() {
		return disList_big;
	}
	public void setDisList_big(List<District> disList_big) {
		this.disList_big = disList_big;
	}
	public List<District> getDisList() {
		return disList;
	}
	public void setDisList(List<District> disList) {
		this.disList = disList;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public String getRecordKey() {
		return recordKey;
	}
	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getDistrict_text() {
		return district_text;
	}
	public void setDistrict_text(String district_text) {
		this.district_text = district_text;
	}
	public String getDistrict_select() {
		return district_select;
	}
	public void setDistrict_select(String district_select) {
		this.district_select = district_select;
	}
	public String getCountry_text() {
		return country_text;
	}
	public void setCountry_text(String country_text) {
		this.country_text = country_text;
	}
	public String getCountry_select() {
		return country_select;
	}
	public void setCountry_select(String country_select) {
		this.country_select = country_select;
	}
	public String getLeague_select() {
		return league_select;
	}
	public void setLeague_select(String league_select) {
		this.league_select = league_select;
	}
	public String getWheel_select() {
		return wheel_select;
	}
	public void setWheel_select(String wheel_select) {
		this.wheel_select = wheel_select;
	}
	public String getTeam_text() {
		return team_text;
	}
	public void setTeam_text(String team_text) {
		this.team_text = team_text;
	}
	public String getTeam_type() {
		return team_type;
	}
	public void setTeam_type(String team_type) {
		this.team_type = team_type;
	}
	public String getRace_season() {
		return race_season;
	}
	public void setRace_season(String race_season) {
		this.race_season = race_season;
	}
	public String getRaceId() {
		return raceId;
	}
	public void setRaceId(String raceId) {
		this.raceId = raceId;
	}
	public String getRace_name() {
		return race_name;
	}
	public void setRace_name(String race_name) {
		this.race_name = race_name;
	}
	public String getSession_name() {
		return session_name;
	}
	public void setSession_name(String session_name) {
		this.session_name = session_name;
	}
	public Long getWheelId() {
		return wheelId;
	}
	public void setWheelId(Long wheelId) {
		this.wheelId = wheelId;
	}
	public String getWheel_name() {
		return wheel_name;
	}
	public void setWheel_name(String wheel_name) {
		this.wheel_name = wheel_name;
	}
	public String getCupId() {
		return cupId;
	}
	public void setCupId(String cupId) {
		this.cupId = cupId;
	}
	public String getCup_name() {
		return cup_name;
	}
	public void setCup_name(String cup_name) {
		this.cup_name = cup_name;
	}
	public String getCup_prefix() {
		return cup_prefix;
	}
	public void setCup_prefix(String cup_prefix) {
		this.cup_prefix = cup_prefix;
	}
	public String[] getCup_prefixs() {
		return Constants.CUP_TEAM_PREFIX.split(",");
	}
	public void setCup_prefixs(String[] cup_prefixs) {
		this.cup_prefixs = cup_prefixs;
	}
	public Map<String, String> getCup_prefix_map() {
		cup_prefixs = getCup_prefixs();
		Map <String,String> param = new TreeMap<String,String>();
		for(String s : cup_prefixs){
			param.put(s, s);
		}
		return param;
	}
	public void setCup_prefix_map(Map<String, String> cup_prefix_map) {
		this.cup_prefix_map = cup_prefix_map;
	}

	public String getDue_name() {
		return due_name;
	}
	public void setDue_name(String due_name) {
		this.due_name = due_name;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getSchedule_name() {
		return schedule_name;
	}
	public void setSchedule_name(String schedule_name) {
		this.schedule_name = schedule_name;
	}
	public Against getAgainst() {
		return against;
	}
	public void setAgainst(Against against) {
		this.against = against;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public List<TeamRace> getHostList() {
		return hostList;
	}
	public void setHostList(List<TeamRace> hostList) {
		this.hostList = hostList;
	}
	public List<TeamRace> getGuestList() {
		return guestList;
	}
	public void setGuestList(List<TeamRace> guestList) {
		this.guestList = guestList;
	}

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsConcede() {
		return isConcede;
	}
	public void setIsConcede(String isConcede) {
		this.isConcede = isConcede;
	}
	
}
