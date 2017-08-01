package com.wintv.lottery.bet.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.exception.BadInputException;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.BetHotSearch;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.framework.pojo.MyAutoOrder;
import com.wintv.framework.pojo.User;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.vo.MyAutoOrderVO;
import com.wintv.lottery.bet.vo.PhaseNoVO;
import com.wintv.lottery.bet.vo.SuperSponsorVO;
import com.wintv.lottery.index.vo.InGoalBetScaleVo;
import com.wintv.lottery.xinshui.vo.Top10Vo;
import com.wintv.lottery.bet.vo.BetTop10Vo;

/**
 * 投注模块业务层接口类
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public interface BetService {
	 /**其他界面要用到自动跟单的详细列表通过3个参数读取一条自动跟单的详细信息  2010-04-29 09:46**/
	public KingSponsor  loadAutoOrder(Long userId,String betCategory,String flg)throws DaoException;
	/**
	 * 投注模块  根据期次查询历史对阵列表  2010-04-23 17:50
	 */
	public List<AgainstVo> findHistoryPhaseAgainstList(Map params)throws DaoException;
	/**判断用户是否已经申请某彩种的玩法   2010-04-23 11:40
	 * 返回值:
	 *   false:已经申请,本次不能再申请
	 *   true:本次可以申请
	 **/
	public boolean  isAlreadyApply(Long userId,String betCategory,String type)throws DaoException;
	 /**查询用户已经申请的彩种以及玩法**/
	   public List<KingSponsor>  findAlreadyApplyCategoryList(Long userId)throws DaoException;
	/**
	 * 自动跟单-我要跟单列表记录数统计  2010-04-19 15:20
	 *
	 */
	public long  findKingSize(Map params)throws DaoException;
	/**
	 * 自动跟单-我要跟单列表  2010-04-19 15:20
	 *
	 */
	public List<KingSponsor>  findKingList(Map params)throws DaoException;
	/**金牌发起人管理  2010-04-14 09:25**/
	public List<KingSponsor>  findKingCategory(Long userId)throws DaoException;
	/**金牌发起人资格审核管理  2010-04-06 09:25**/
	public List<KingSponsor>  findKingCategoryAudit(Long userId)throws DaoException;
	/**
	 * 自动跟单-自动跟单人 2010-04-12  15:09
	 * 参数:
	 * kingUserId:金牌发起人用户ID
	 * betCategory:玩法
	 * @throws DaoException
	 */
	public List findAutoOrderListByCategory(Map params)throws DaoException;
	/**我的跟单管理统计总记录数  分页使用 2010-04-12 13:31
	 * 参数:
	 * commonUserId:普通用户ID
	 **/
	public long findMyAutoOrderSize(Long commonUserId)throws DaoException;
	/**我的跟单管理  2010-04-12 13:31
	 * 参数:
	 * commonUserId:普通用户ID
	 **/
	public List<MyAutoOrderVO> findMyAutoOrderList(Long commonUserId)throws DaoException;
	
	/**根据期次ID查询胜负彩14场的主对名称与客队名称  2010-04-01 17:22**/
	public List<Against> findAgainstList(Long phaseId)throws DaoException;
	/**参与合买列表  2010-03-26 11:00
	 * 参数：
	 *   phaseNo:期次号  必须
	 * 返回结果：
	 * totalCount:列表记录数
	 * resultList:分页列表 类型是:List<CoopOrder>
	 * hotSearchList:被搜索的热门用户  类型是:List<HotSearchVO>
	 **/
	public Map findCoopList(Map params)throws DaoException;
	public User  loadUser(Long userId)throws DaoException;
	public Map loadOrderDetail(Map params)throws DaoException;
	/**根据主键加载对象  2010-03-25 15:49**/
	public BetOrder loadBetOrder(Long id)throws DaoException;
	/**普通用户定制自动跟单  2010-05-07 09:35
	* 返回值
	* 1:定制成功
	* 3:已经定制过该金牌发起人的彩种,玩法
	* 2:已经定制满员 不能再定制
	* -1:系统报错
	**/
	public int  saveMyAutoOrder(MyAutoOrder po)throws DaoException;
	/**金牌发起人订单,用于自动跟单
	 * 返回:
	 *  size:记录数
	 *  resultList;返回的数据列表
	 */
	public List<SuperSponsorVO>  findSuperSponsorList(Map params)throws DaoException,BadInputException;
	/**
	 * 根据期次主键 查询该期次已经过期的对阵数量  2010-03-05 09:26
	 * id:期次主键
	 */
	public int loadDeadAgainstSize(Map params)throws DaoException;
	/**
	 * 最近若干期的期次列表
	 * 参数：
	 * phaseCategory:期次分类  '6':胜负彩期次    '9':进球彩期次 '8':半全场期次 '1':北京单场期次
	 * 
	 * @return
	 */
	public List<PhaseNoVO> findLatestPhaseList(Map params);
	 /**
	 * 查询当前某彩种的期次号  对阵列表  复式投注截止时间  2010-03-03 13:12
	 * 
	 * phaseCategory:期次分类  '6':胜负彩期次    '9':进球彩期次 '8':半全场期次 '1':北京单场期次
	 * isCurrent: 足彩期次是否是当前期次 '1':当前期次  '2':预售期次
	 * @since 2010 02-26 11:53
	 */
	public Map findPhaseAgainstList(Map params)throws DaoException;
	/**
	 * 用户查询自己的投注订单列表 2010-03-01 14:03
	 */
	public Map findMyBetOrderList(Map params)throws DaoException;
     /**
	 * 根据参数决定保存哪种彩种的投注结果 参数: o:个人的投注结果 异常: DaoException:数据库操作异常 返回:是否投注成功
	 */

	public long saveBetOrder(Map params) throws DaoException;
	 /**
	    * 保存申请金牌发起人  2010-04-27 10:23
	    * KingSponsor:
	    *  1.金牌发起人用户名
	    *  2.中文描述,给跟单人看
	    *  3.金牌发起人 用户ID
	    *  4.投注战绩
	    * KingSponsorCategory
	    * 彩种    1: 胜负14场      2:任9场  3:4场进球  4:4半全场  5:6 场半全场     
	    *        61:单场半全场  62:单场比分 63:单场胜平负  64:单场上下单双 65:单场总进球
	    *  玩法  '1':单式合买  '2':复式合买  '3':单复式合买     
	    *  1:成功
	    *  0:失败       
	    */
	public int saveKingSponsor(KingSponsor po)throws DaoException;
	   /**
	    * 定制自动跟单  2010-03-08 14:40
	    *
	    */
	 public boolean dzAutoGz(MyAutoOrder po)throws DaoException;
	 /**合买  2010-03-22 17;22
	    * 返回结果:
	    *
	    **/
	   @Transactional(rollbackFor = DaoException.class)
	   public long saveBetCoopOrder(Map params) throws DaoException;

    /**
	 * 单式代购严整
	*/
	public Map<String, Object> dsdgValidator(Map<String, Object> param);
	
	/**
	 * 单式代购------详细
	 * */
	//public Map<String, Object> detailDsdg(Map<String, Object> param);
	
	/**
	 * 单式代购------提交
	 * */
	public Map<String, Object> dsdg_submit(Map<String, Object> param);
	
	/**
	 * 单式合买------提交
	 * */
	public Map<String, Object> dshm_submit(Map<String, Object> param);
	
	/**
	 * 查找对阵
	 * */
	public List<Against> findDSDGAgainst(String against_ids);
	
	
	public List<BetOrder> findBetOrderByField(String fieldName,String fieldValue);
	
	/**
	 * 进球彩 投注比例查询
	 * */
	public List<InGoalBetScaleVo> getCurBetScale(String phase);
	
	/**
	 * 进球彩 上期冷门
	 * */
	public List<InGoalBetScaleVo> getDarkRank(String phase);
	
	/**
	 * 合买热门搜索
	 * */
	public List<BetHotSearch> hot_search();
	/**
	 * 合买排行 
	 *  flg='1':足球单场合买排行
     *  flg='2':足球单场代购排行
     *  flg='3':胜负彩胜负14合买排行
     *  flg='4':进球彩合买排行
     *  flg='5':胜负彩任9合买排行
     *  flg='6':胜负彩6场半全场合买排行
     *  
	 * @return
	 * @throws DaoException
	 */
	public List<BetTop10Vo> findHmTop10List(Map params)
			throws DaoException;


}
