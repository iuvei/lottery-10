package com.wintv.lottery.bet.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.vo.CanyuVO;


public interface BetRecordDao extends BaseDao {
	 public BetOrder findProperties(Long betId)throws DaoException;
	/**我的认购明细 参与和买时  2010-04-08 17:43**/
	public List<CanyuVO> findMyBuyList(Long userId,Long betId)throws DaoException;
	/**根据期次ID,查询主客队**/
	public List<String> findHGList(Long phaseId)throws DaoException;
	/**
	 * 根据期次ID查询14场对阵列表
	 */
	public List<String> findHostAgainstList(Map params)throws DaoException;
	public List<AgainstVo> findAgainstList(Map params)throws DaoException;
	public String getTxtFile(Long id)throws DaoException;
	
	//=====================购买用户分页显示  2010-04-01 16:53=================================
	public long findParticipateSize(Map params)throws DaoException;
	public List<CanyuVO> findParticipateList(Map params)throws DaoException;
	public List<Against> findAgainstList(Long phaseId)throws DaoException;

}
