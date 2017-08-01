package com.wintv.lottery.admin.bet.service;

import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.lottery.admin.bet.vo.BetAdminDetailVO;
import com.wintv.lottery.admin.bet.vo.PhaseVO;

public interface BetAdminService {
	/**
	 * 根据彩种查询期次信息  2010-04-13 16:49
	 * 参数：
	 *  betCategory：彩种
	 * @return
	 * @throws DaoException
	 */
	public List<PhaseVO> findPhaseList(Long betCategory)throws DaoException;
	/**
	 * 3.2.5投注系统后台：*投注订单管理列表  文档54页  2010-03-07  14:00
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public  Map findList(Map params)throws DaoException;
	/**
	 * 3.2.6合买系统后台：2010-03-08 09:14 合买方案列表
	 */
	public  Map findCoopBuyList(Map params)throws DaoException;
	/**3.2.7自动跟单后台管理 文档 58页**/
	public Map findKingList(Map params)throws DaoException;
	/**
	 * 投注订单详情 对应文档55页  2010-03-09 13:43
	 * @param betId
	 * @return
	 * @throws DaoException
	 */
	public BetAdminDetailVO  loadBetAdminDetailVO(Long betId)throws DaoException;
	/**
	 * 投注订单详情-认购信息（收起认购信息） 对应文档55页 2010-03-09 15:34
	 * @param betId
	 * @return
	 * @throws DaoException
	 */
	public Map  findSubscribeList(Map p)throws DaoException;
	
	/**
	 * 3.2.6合买系统后台 合买方案详情 57页 2010-03-08 10:12
	 * type 类型 :'1'单代 '2' 单合  '3 '复代 '4'复合
	 * @param betId
	 * @return
	 * @throws DaoException
	 */
	public BetAdminDetailVO  loadCoopByDetailVO(Long betId,String type,String betCategory,String wiciType)throws DaoException;
	/**
	 * 3.2.5投注系统后台：合买认购信息（收起认购信息）  文档57页  2010-03-07  16:00
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public  Map findCoopBuySubscribeList(Map params)throws DaoException;
	/**金牌发起人管理详情  59页 2010-03-11 14:07**/
	public KingSponsor  loadKingSponsor(Map params)throws DaoException;
	/***金牌发起人管理详情  59页**/
	public Map findAutoOrderList(Map params)throws DaoException;
	/**后台客服取消定制**/
	public boolean updateMyAutoOrder(Long autoOrderId)throws DaoException;
	

}
