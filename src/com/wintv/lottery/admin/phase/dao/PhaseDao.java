package com.wintv.lottery.admin.phase.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.District;
import com.wintv.framework.pojo.LotteryPhase;
import com.wintv.framework.pojo.Race;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;

@SuppressWarnings("unchecked")
public interface PhaseDao extends BaseDao<LotteryPhase,Long>{
	/**根据期次ID查询开奖号码**/
	public String loadKjNo(Long phaseId)throws DaoException;
	//------------------------投注模块 2010-02-26 13:15------------------------
	/**
	 * 根据汽车主键 查询该期次已经过期的对阵数量
	 */
	public int loadDeadAgainstSize(Map params)throws DaoException;
	/**
	 * 投注模块  根据期次查询对阵列表
	 */
	public List<AgainstVo> findLotteryPhaseAgainstList(Map params)throws DaoException;
	/**
	 * 根据彩种  加载当前期次 2010-02-26 13:26
	 */
	public LotteryPhasePO loadCurrentPhase(Map params)throws DaoException;
	//------------------------------------------------
	public boolean updateStatus(Map params);
	/**
	 * 
	 * 参数:
	 *   flg="1":添加时间
	 *   flg="2":开售时间
	 * @return
	 * @throws DaoException
	 */
	public List<LotteryPhasePO> findPhaseList(Map params,int startRow,int pageSize )throws DaoException;
	public  List<AgainstVo>  loadAgainstList(Long phaseId)throws DaoException;
	public LotteryPhasePO loadLotteryPhase(Long phaseId);
	public void pubRaceResult(Map params)throws DaoException;
	/**
	 * 
	 * 保存期次  对于同一彩种(注意保持当期期次的时候  必须校验 系统中是否存在当期期次)
	 */
	public int savePhase(Map<String,String[]> params)throws DaoException;
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
	/**
	 * 期次发布时 选择区域，国家
	 */
	public List<District> findDistrict(Long parentId);
	/**
	 * 期次发布时 选择联赛 赛季  轮次
	 */
	public List<Race> findRaceList(Map params);
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
	public Long saveCSHandleLog(CSHandleLog log);

}
