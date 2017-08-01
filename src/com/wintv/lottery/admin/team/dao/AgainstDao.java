package com.wintv.lottery.admin.team.dao;

import java.util.List;
import java.util.Map;

import org.ecside.table.limit.Limit;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.Against;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.admin.team.vo.TeamVo;

@SuppressWarnings("unchecked")
public interface AgainstDao extends BaseDao<Against,Long>{
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
	public List<Against> findScheduleList(Map params);
	public AgainstVo loadAgainstById(String category,Long id);
	public Page againstList(Limit limit, Map<String, String> param);
	public Page findLeagueAgainstList(Limit limit, Map<String, String> param);
	public Page findCupAgainstTeam(Limit limit, Map<String, String> param);
	public Page findAllTeamManager(Map<String,Object> param);
	/**
	 * @param updown     上 'u' 下 'd' 半场,或全场 'f'
	 * @param latest     查最新的 就输入 'y'  同时输入 raceId
	 * @param raceId     联赛或杯赛ID
	 * @param seasonId   赛季ID
	 * @param conditionSelect   查询条件     
	 * 格式: and t.AREA_ID = 1 and t.COUNTRY_ID = 16 and t.RACE_SEASON_ID 278 and t.RACE_ID = 5 
	 * */
	public List<TeamVo> findAllTeamManager(String updown,String latest,Long raceId,Long seasonId,String conditionSelect,String sortField,String sortOrder);
	public Long findTeamsById(Long teamId,Long seasonId);
	

}
