package com.wintv.lottery.bet.dao;

import java.util.List;
import java.util.Map;
import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.admin.bet.vo.PhaseVO;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.vo.BetOrderVO;
public interface EnhancementDao extends BaseDao {
	/**
	 * --网站购彩大厅单场期次列表 2010-05-06 13:49
	 *
	 */
	public PhaseVO  findCurSinglePhase()throws DaoException;
	/**
	 * --网站购彩大厅期次列表 2010-05-06 13:49
	 * @param category
	 * @return
	 * @throws DaoException
	 */
	public Map  findInAdvancePhaseMap(String category)throws DaoException;
	public boolean deleteRen9ChoiceList(Long betOrderId)throws DaoException;
	public boolean updateBetOrder(Map params)throws DaoException;
	/**
	 * 根据期次ID查询对阵列表 2010-04-21 11:16
	 */
	public List<AgainstVo> findAgainstList(Map params)throws DaoException;
	/**
	 * 稍后保存文件到数据库 2010-04-20 11;16
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public boolean uploadTxtPath(Map map)throws DaoException;
	/**
	 * -撤单   2010-04-16 17:57
	 * 返回值:   1.撤单成功  2.进度超度50%不能撤单  4 非法操作    -1报错
	 *
	 */
	 public int cancelSpOrder(long userId,long betId)throws DaoException;
	  /**
	   * 保底   2010-04-16 16:11
	   * 返回 4为非法操作   返回1 正常保底  2 保底份数太少 3保底份数太多  
	   *
	   */
	  public int floorBetOrder(long userId,long betId,long flooCopys)throws DaoException;
	  public BetOrderVO loadBetOrder(Long betId)throws DaoException;
	  /**统计某一个人针对某一方案认购总份数  2010-04-21 09:56**/
	  public long getAllSubscribeCopys(Long userId,Long betId)throws DaoException;
	  

}
