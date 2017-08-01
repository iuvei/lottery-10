package com.wintv.lottery.admin.phase.service;

import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.District;
import com.wintv.framework.pojo.LotteryPhase;
import com.wintv.framework.pojo.Race;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;

/**
 * 
 * 期次模块
 *
 */
public interface PhaseService {
	
	
	
	//=====================================================================================================
	
	/**
	 * 保存期次
	 * @param po
	 * @return
	 */
	public int savePhase(Map<String,String[]> params)throws DaoException;
	public AgainstVo loadAgainstById(String category,Long id)throws DaoException;
	
	/**
	 * 查询所有联赛或杯赛
	 * @return
	 * @throws DaoException
	 */
	public List findRaceList()throws DaoException;
	/**
	 * 根据条件查询对阵信息
	 * @param params
	 * @return
	 */
	public List findAgainst(Map params);
	/**
	 * 【根据条件查询对阵记录总数 文档第7页】 2010-02-11 09:57
	 * @param params
	 * @return
	 */
	public int findAgainsSize(Map params);
    public List<District> findDistrict(Long parentId);
	/**
	 * 期次发布时 选择区域，国家
	 */
	public List<Race> findRaceList(Map params);
	/**
	 * 【3.2.2.2足彩期次录入后台列表】对应文档第10页
	 * 参数:
	 *   flg="1":添加时间
	 *   flg="2":开售时间
	 *   from:起始时间
	 *   to:结束时间
	 *   startRow:起始记录数
	 *   pageSize:每页最大记录数
	 * @return
	 * @throws DaoException
	 */
	public List<LotteryPhasePO> findPhaseList(Map params,int startRow,int pageSize )throws DaoException;
	/**
	 * 【3.2.2.2足彩期次录入后台列表】 分页记录数统计对应文档第10页
	 * 参数:
	 *   flg="1":添加时间
	 *   flg="2":开售时间
	 *   from:起始时间
	 *   to:结束时间
	 *   startRow:起始记录数
	 *   pageSize:每页最大记录数
	 * @return
	 * @throws DaoException
	 */
	public int findPhaseSize(Map params)throws DaoException;
	public Map  loadLotteryPhase(Long id)throws DaoException;
	public boolean updatePhase(Map params)throws DaoException;
	public Long saveCSHandleLog(CSHandleLog log);

}
