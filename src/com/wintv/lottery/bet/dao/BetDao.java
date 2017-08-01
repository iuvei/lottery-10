package com.wintv.lottery.bet.dao;

import java.util.List;
import java.util.Map;
import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.BadInputException;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.BetOrderChoice;
import com.wintv.lottery.bet.vo.BetTop10Vo;
import com.wintv.lottery.bet.vo.CoopOrder;
import com.wintv.lottery.bet.vo.PhaseNoVO;
import com.wintv.lottery.bet.vo.SuperSponsorVO;
import com.wintv.lottery.bet.vo.BetTop10Vo;
import com.wintv.lottery.index.vo.InGoalBetScaleVo;

@SuppressWarnings("unchecked")
public interface BetDao extends BaseDao {
	
	 public String advSearch4(Map params)throws DaoException;
	 public String advSearch6(Map params)throws DaoException;
	public BetOrder loadBetOrderById(Long betId)throws DaoException;
	public String load9Choice(long betId)throws DaoException;
	/**胜负彩14场高级搜索  2010-04-07 11:07**/
    public String advSearch14(Map params)throws DaoException;
	public String[] statPlan(String betCategory,Long phaseId)throws DaoException;
	
	/***根据期次ID查询复式截止时间  2010-04-01 1:00*/
	public String  getMulDeadline(Long phaseId)throws DaoException;
	/**投注记录  单场:投注内容 2010-03-26 15:32**/
    public List<BetOrderChoice> loadMyBetOrderChoiceList(Long betOrderId,Long phaseId)throws DaoException;
	public BetOrder loadBetOrder(Long id)throws DaoException;
	/**金牌发起人订单,用于自动跟单
	 * 返回:
	 *  size:记录数
	 *  resultList;返回的数据列表
	 */
	public List<SuperSponsorVO>  findSuperSponsorList(Map params)throws DaoException,BadInputException;
	public Map findMyBetOrderList(Map params)throws DaoException;
	/**
	 * 最近15期的期次列表
	 * @param params
	 * @return
	 */
	public List<PhaseNoVO> findLatestPhaseList(Map params);
	/**查询合买时剩余的份数  2010-03-22 10:38**/
	public Map getSurplusCopys(Long betOrderId)throws DaoException;
	/**发起合买列表  2010-03-23 11:28**/
	public List<CoopOrder> findCoopList(Map params)throws DaoException;
	/**参与合买列表  2010-03-26 11:00**/
	public long findCoopSize(Map params)throws DaoException;
	public String loadBetOrderIds(String sql)throws DaoException;
	/**进球彩 投注比例查询 zym */
	public List<InGoalBetScaleVo> getCurBetScale(String phase);
	/**合买搜索*/
	public String hm_search(String search_content, String betCategory);
	/**
	 * 合买排行 
	 *  flg='1':足球单场合买排行
     *  flg='2':足球单场代购排行
     *  flg='3':胜负彩合买排行
     *  flg='4':进球彩合买排行
	 * @return
	 * @throws DaoException
	 */
	
	public List<BetTop10Vo> findHmTop10List(Map params) throws DaoException;
	
}