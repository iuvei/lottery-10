package com.wintv.lottery.admin.team.dao;

import java.util.List;
import java.util.Map;

import org.ecside.table.limit.Limit;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.Race;
import com.wintv.lottery.admin.team.vo.DistrictVo;

public interface RaceDao extends BaseDao<Race, Long>{
	/**
	 * 根据国家查询所有联赛或杯赛 以及其他赛事
	 * @param countryId
	 * @return
	 * @throws DaoException
	 */
	public List<Race> findRaceByCountry(Long countryId);
	/**
	 * 查询对应的赛季  轮次,赛季与轮次设计成树形结构
	 * @param parentId
	 * @return
	 * @throws DaoException
	 */

	public List<Race> findByParent(Long parentId)throws DaoException;
	/**
	 * 查询所有联赛或者杯赛
	 * @return
	 * @throws DaoException
	 */
	public List<Race> findAllRace()throws DaoException;
	
	/**
	 * 前台页面查询所有联赛或者杯赛
	 */
	public List<Race> findSiteRaceList(Map params)throws DaoException;

	public List<Race> findRaceByTree(String type, Long id);
	
	public Race findUniqueLeagueRace(Map<String, String> param);
	
	public Page findAllLeagueRace(Limit limit, Map<String, String> param);
	
	public List<DistrictVo> findAllLeagueRaceCount(Map<String,String> param);
	
	public Page findLRWheel(Limit limit, Map<String, String> param);
	
	public Race findUniqueCupRace(Map<String, String> param);
	
	public Page findCupRace(Limit limit, Map<String, String> param);
	//=======================ADDED BY wjj   2010-02-25 15:46==============
	public List findRaceList(String p);
	
	public List<Race> findLeagueByCountry(long country_select);

	public Page findAllLeagueTeam(Limit limit, Map<String, String> param);
	
	public List<DistrictVo> findAllLeagueTeamCount(Map<String, String> param);
	
	public Page findLeagueTeamBySession(Limit limit, Map<String, String> param);
	
	public List<Race> findCupByPrefix(Map<String, String> param);
	
	public Page findCupTeam(Limit limit, Map<String, String> param);
}
