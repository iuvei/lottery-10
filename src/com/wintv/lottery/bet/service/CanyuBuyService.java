package com.wintv.lottery.bet.service;

import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.BetOrderChoice;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.vo.CanyuVO;


public interface CanyuBuyService {
	/**投注记录  单场:投注内容 2010-03-26 15:32**/
    public List<BetOrderChoice> loadMyBetOrderChoiceList(Long betOrderId,Long phaseId)throws DaoException;
	/**北京单场   2010-04-14 15:22**/
	public Map loadBeijingOrderInfo(Long betOrderId)throws DaoException;
	/**4场进球  2010-04-13 16:14**/
	public Map loadGoal4Info(Long betOrderId)throws DaoException;
	/**6场半全场  2010-04-13 16:14**/
	public Map loadHalfComplete6Info(Long betOrderId)throws DaoException;
	/**任9场  2010-04-13 13:37**/
	public Map loadOptional9OrderInfo(Long betOrderId)throws DaoException;
	 public BetOrder findProperties(Long betId)throws DaoException;
	/**判断用户是否被当前用户关注 2010-04-12 15:18**/
	public String isAttention(Long userId,Long targetUserId)throws DaoException;
	/**判断当前用户是否为跟单用户  2010-04-02 11:16**/
	public boolean  isGD(Long sponsorUserId,Long currentUserId)throws DaoException;
	/**我的认购明细 参与和买时  2010-04-08 17:43**/
	public List<CanyuVO> findMyBuyList(Long userId,Long betId)throws DaoException;
	/**
	 * 根据期次ID查询对阵列表
	 * 参数:
	 *  phaseId:期次ID
	 */
	public List<AgainstVo> findAgainstList(Map params)throws DaoException;
	/**获取胜负彩14场单式发起订单**/
	public Map loadSingle14OrderInfo(Long betOrderId)throws DaoException;
	public long findParticipateSize(Map params)throws DaoException;
	public List<CanyuVO> findParticipateList(Map params)throws DaoException;
}
