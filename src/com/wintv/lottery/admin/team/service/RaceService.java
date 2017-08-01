package com.wintv.lottery.admin.team.service;

import java.util.List;
import java.util.Map;

import org.ecside.table.limit.Limit;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.District;
import com.wintv.framework.pojo.Race;
import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.Team;
import com.wintv.framework.pojo.TeamRace;
import com.wintv.lottery.admin.team.vo.DistrictVo;

/**
 * 后台客服--洲 国家 管理 
 * 以及 赛事   届  轮次管理
 * @author Administrator
 */
public interface RaceService {

	
	//分页查询 区域
	public Page findAllDistrict(Limit limit,Map<String,String> param);
	
	//分页查询 球队
	public Page findAllTeam(Limit limit, Map<String, String> param);
	
	//对阵
	public Page againstList(Limit limit, Map<String, String> param);
	
	//分页查询 联赛
	public Page findAllLeagueRace(Limit limit, Map<String, String> param);
	
	//分页查询 联赛轮次
	public Page findLRWheel(Limit limit, Map<String, String> param);
	
	//分页查询 杯赛
	public Page findCupRace(Limit limit, Map<String, String> param);
	
	//分页查询 联赛球队
	public Page findAllLeagueTeam(Limit limit, Map<String, String> param);
	
	//分页查询杯赛球队
	public Page findCupTeam(Limit limit, Map<String, String> param);
	
	//球队管理
	public Page findAllTeamManager(Map<String,Object> param);
	
	//----------------------------区域----------------------------------------------------------------
	
	public List<District> findBigDistrict();
	
	public List<District> findSmallDisByBig(Long id);
	
	public Long saveDistrict(District district)throws DaoException;
	
	public String addDistrict(String name);
	
	//根据主键删除洲 国家信息
	public int districtDeleteDis(String  ids)throws DaoException;
	
	public String updateDistrict(Map<String, String> param);
	
	public String addCountry(Map<String, String> param);

	public int districtDeleteCountry(String deleteFlag);
	
	public List<DistrictVo> findAllDistrictCount();
	
	//----------------------------对阵--------------------------------------------------------------
	//保存对阵信息  详情见表T_AGAINST
	public Long saveAgainst(Against against)throws DaoException;
	
	public void deleteAgainst(Long id)throws DaoException;
    
    public List<Against>  findAgainstList(Map<String, Object> params);
    

	//-------------------------------球队---------------------------------------------------------
    //根据国家ID查询所有球队
    public List<Team> findTeamList(Map<String, Object> params);
    
    //保存洲 联赛杯赛信息 以及赛季 轮次等  这里是个树形结构
	public Long saveRace(Race race)throws DaoException;
	
	//根据主键删除 赛事  赛季（届） 轮次 信息
	public void deleteRace(Long id)throws DaoException;
    
	public String addTeam(Map<String, String> param);

	public int teamAdd(Team team);
	
	//根据球队主键查询球队信息
    public Team loadTeam(Long id);
    
	//修改球队信息
    public void updateTeam(String sql)throws DaoException;
	
	public Map<String,Object> teamPreUpdate(String recordKey);
	
	public int teamUpdate(Team team, String recordKey);
	
	public String teamDelete(String deleteFlag);
	
	public Team teamDetail(String deleteFlag);
	
	//------------------------------联赛-----------------------------------
	/**
	 * 查询所有联赛或者杯赛 2010-02-03 09:25  对应表:T_RACE 判断 PARENT_ID 为空
	 * @return
	 * @throws DaoException
	 */
	public List<Race> findAllRace()throws DaoException;
	
	/**
	 * 前台页面查询所有联赛或者杯赛
	 * 1.民间用户管理自己的心水   USERID
     *2.民间高手搜索对阵列表  
     *3.普通用户管理心水订单 USERID
     *4.民间高手销售列表 USERID
      *5.购买心水,可能未登录
	 */
	public List<Race> findSiteRaceList(Map params)throws DaoException;
	
	/**
	 * 查询对应的赛季  轮次  2010-02-03 09:25  对应表:T_RACE  三级树形菜单
	 * @param parentId
	 * @return
	 * @throws DaoException
	 */
	public List<Race> findByParent(Long parentId)throws DaoException;
	
	public List<DistrictVo> findAllLeagueRaceCount(Map<String,String> param);
	
	public String leagueRaceDeleteRace(String deleteFlag);

	public String leagueRaceUpdateName(Map<String, String> param);

	public String leagueRaceAddSession(Map<String,String> param);
	
	public String leagueRaceAdd(Map<String, String> param);

	public String leagueRaceAddWheel(Map<String, String> param);
	
	public int leagueRaceDeleteLeagueWheel(String ids);

	//-----------------------------杯赛--------------------------------
	public String cupRaceAdd(Map<String, String> param);

	public String cupRaceDeleteRace(String deleteFlag);

	public String cupRaceDeleteSchedule(String deleteFlag);
	
	public String cupRaceUpdateName(Map<String, String> param);

	public String dueRaceAdd(Map<String, String> param);

	public String scheduleRaceAdd(Map<String, String> param);

	//-----------------------------联赛球队--------------------------------
	public List<Race> findLeagueByCountry(String country_select);

	public List<Race> findTeamByLeague(String race_season);
	
	public List<DistrictVo> findAllLeagueTeamCount(Map<String, String> param);

	public Page findLeagueTeamBySession(Limit limit, Map<String, String> param);

	public String leagueTeamAddTeam(Map<String,String> param);

	public String deleteLeagueTeam(String deleteFlag);

	//-----------------------------杯赛球队--------------------------------
	public List<Race> findCupByPrefix(Map<String, String> param);

	public List<Race> findCupDue(Map<String, String> param);

	public String cupTeamAddTeam(Map<String, String> param);
	

	//-----------------------------联赛队阵--------------------------------
	public Page findLeagueAgainstList(Limit limit, Map<String, String> param);

	public List<TeamRace> leagueAgainstFindTeamAjax(String race_season);

	public String leagueAgainstAdd(Map<String, String> param);

	public void leagueAgainstDelete(String deleteFlag);

	public Against leagueAgainstPreUpdate(String recordKey);

	public String leagueAgainstUpdate(Against against);
	
	//-----------------------------杯赛队阵--------------------------------
	public Page findCupAgainstTeam(Limit limit, Map<String, String> param);
	
	public String cupAgainstAdd(Map<String, String> param);
	
	//=======================ADDED BY wjj   2010-02-25 15:46==============
	@SuppressWarnings("unchecked")
	public List findRaceList(String p);

	



}
