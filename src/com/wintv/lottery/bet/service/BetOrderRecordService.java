package com.wintv.lottery.bet.service;

import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.vo.CanyuVO;

@SuppressWarnings("unchecked")
public interface BetOrderRecordService {
	public Map loadMyBetRecord(Long betOrderId)throws DaoException;
	/**取消订单  2010-03-27 16:01**/
	public boolean canvelMyOrder(Long  id)throws DaoException;
	public List<AgainstVo> findAgainstList(Map params)throws DaoException;
	public String getTxtFile(Long id)throws DaoException;
	//=====================购买用户分页显示  2010-04-01 16:53=================================
	public long findParticipateSize(Map params)throws DaoException;
	public List<CanyuVO> findParticipateList(Map params)throws DaoException;

}
