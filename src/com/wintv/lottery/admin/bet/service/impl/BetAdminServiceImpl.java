package com.wintv.lottery.admin.bet.service.impl;


import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.lottery.admin.bet.dao.BetAdminDao;
import com.wintv.lottery.admin.bet.dao.KingAdminSponsorDaoDao;
import com.wintv.lottery.admin.bet.dao.MyAdminAutoOrderDao;
import com.wintv.lottery.admin.bet.service.BetAdminService;
import com.wintv.lottery.admin.bet.vo.BetAdminDetailVO;
import com.wintv.lottery.admin.bet.vo.PhaseVO;
@Service("betAdminService")
public class BetAdminServiceImpl implements BetAdminService{
	/**
	 * 根据彩种查询期次信息  2010-04-13 16:49
	 * 参数：
	 *  betCategory：彩种
	 * @return
	 * @throws DaoException
	 */
	public List<PhaseVO> findPhaseList(Long betCategory)throws DaoException{
		return this.betAdminDao.findPhaseList(betCategory);
		
	}
	/**
	 * 3.2.5投注系统后台：*投注订单管理列表  文档54页  2010-03-07  14:00
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public  Map findList(Map params)throws DaoException{
		return this.betAdminDao.findList(params);
	}
	/**
	 * 投注订单详情 对应文档55页
	 * @param betId
	 * @return
	 * @throws DaoException
	 */
	public BetAdminDetailVO  loadBetAdminDetailVO(Long betId)throws DaoException{
		return this.betAdminDao.loadBetAdminDetailVO(betId);
	 
	}
	/**
	 * 投注订单详情-认购信息（收起认购信息） 对应文档55页 2010-03-09 15:34
	 * @param betId
	 * @return
	 * @throws DaoException
	 */
	public Map  findSubscribeList(Map p)throws DaoException{	
		Map resultMap=this.betAdminDao.findSubscribeList(p);
		
		return resultMap;
	}
	//----------------------------------3.2.6合买系统后台-------------------------------------------------------------------
	/**
	 * 3.2.6合买系统后台：2010-03-08 09:14 合买方案列表
	 */
	public  Map findCoopBuyList(Map params)throws DaoException{
		return this.betAdminDao.findCoopBuyList(params);
	}
	/**
	 * 3.2.6合买系统后台 合买方案详情 57页 2010-03-08 10:12
	 * type 类型 :'1'单代 '2' 单合  '3 '复代 '4'复合
	 * @param betId
	 * @return
	 * @throws DaoException
	 */
	public BetAdminDetailVO  loadCoopByDetailVO(Long betId,String type,String betCategory,String wiciType)throws DaoException{
		return this.betAdminDao.loadCoopByDetailVO(betId, type, betCategory, wiciType);
	}
	/**
	 * 3.2.5投注系统后台：合买认购信息（收起认购信息）  文档57页  2010-03-07  16:00
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public  Map findCoopBuySubscribeList(Map params)throws DaoException{
		return this.betAdminDao.findCoopBuySubscribeList(params);
	}
	
	
	//--------------------------------------3.2.7自动跟单后台管理-----------------------------------------------------------------------------------
	/**3.2.7自动跟单后台管理-金牌发起人管理列表   文档58页**/
	public Map findKingList(Map params)throws DaoException{
		return this.kingAdminSponsorDaoDao.findList(params);
	}
	/**金牌发起人管理详情  59页 2010-03-11 14:07**/
	public KingSponsor  loadKingSponsor(Map params)throws DaoException{
		return this.kingAdminSponsorDaoDao.loadKingSponsor(params);
	}
	/***金牌发起人管理详情  59页**/
	public Map findAutoOrderList(Map params)throws DaoException{
		return this.myAdminAutoOrderDao.findAutoOrderList(params);
	}
	/**后台客服取消定制**/
	public boolean updateMyAutoOrder(Long autoOrderId)throws DaoException{
		return this.myAdminAutoOrderDao.updateBySql("update T_MY_AUTO_ORDER t  set t.status='2'  where  t.AUTO_ORDER_ID="+autoOrderId);
	}
	@Autowired
	private BetAdminDao betAdminDao;
	@Autowired
	private KingAdminSponsorDaoDao kingAdminSponsorDaoDao;
	@Autowired
	private MyAdminAutoOrderDao myAdminAutoOrderDao;
}
