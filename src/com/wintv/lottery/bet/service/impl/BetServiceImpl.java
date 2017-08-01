package com.wintv.lottery.bet.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.wintv.framework.common.hibernate.BaseHibernate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wintv.framework.common.Constants;
import com.wintv.framework.common.DictionaryDao;
import com.wintv.framework.common.OrderNoGenDao;
import com.wintv.framework.exception.BadInputException;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.BetHotSearch;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.BetOrderChoice;
import com.wintv.framework.pojo.MyAutoOrder;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.framework.pojo.User;
import com.wintv.lottery.admin.phase.dao.PhaseDao;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;
import com.wintv.lottery.admin.team.dao.AgainstDao;
import com.wintv.lottery.bet.dao.BetDao;
import com.wintv.lottery.bet.dao.BetRecordDao;
import com.wintv.lottery.bet.dao.BonusDao;
import com.wintv.lottery.bet.dao.HotSearchDao;
import com.wintv.lottery.bet.dao.KingSponsorCategoryDao;
import com.wintv.lottery.bet.dao.MyAutoOrderDao;
import com.wintv.lottery.bet.dao.KingSponsorDao;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.utils.RecordUtils;
import com.wintv.lottery.bet.utils.Tools;
import com.wintv.lottery.bet.vo.CoopOrder;
import com.wintv.lottery.bet.vo.MyAutoOrderVO;
import com.wintv.lottery.bet.vo.PhaseNoVO;
import com.wintv.lottery.bet.vo.SuperSponsorVO;
import com.wintv.lottery.index.vo.InGoalBetScaleVo;
import com.wintv.lottery.pay.dao.PayDao;
import com.wintv.lottery.xinshui.vo.Top10Vo;
import com.wintv.lottery.bet.vo.BetTop10Vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream; 	
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
@Service("betService")
@SuppressWarnings("unchecked")
public class BetServiceImpl implements BetService{
	/**
	 * 合买排行 
	 *  flg='1':足球单场合买排行
     *  flg='2':足球单场代购排行
     *  flg='3':胜负彩14合买排行
     *  flg='4':进球彩合买排行
     *  flg='5':胜负彩任9合买排行
     *  flg='6':胜负彩6场半全场合买排行
	 * @return
	 * @throws DaoException
	 */
	@Override
	public List<BetTop10Vo> findHmTop10List(Map params)
			throws DaoException {
		return betDao.findHmTop10List(params);
        
	}
	/**
	 * 自动跟单-我要跟单列表记录数统计  2010-04-19 15:20
	 *
	 */
	public long  findKingSize(Map params)throws DaoException{
		return this.kingSponsorDao.findKingSize(params);
	}
	/**
	 * 自动跟单-我要跟单列表  2010-04-19 15:20
	 *
	 */
	public List<KingSponsor>  findKingList(Map params)throws DaoException{
		return this.kingSponsorDao.findKingList(params);
	}
	/**
	 * 自动跟单-自动跟单人 2010-04-12  15:09
	 * 参数:
	 * kingUserId:金牌发起人用户ID
	 * betCategory:玩法
	 * @throws DaoException
	 */
	public List findAutoOrderListByCategory(Map params)throws DaoException{
		return this.myAutoOrderDao.findAutoOrderListByCategory(params);
	}
	/**我的跟单管理统计总记录数  分页使用 2010-04-12 13:31
	 * 参数:
	 * commonUserId:普通用户ID
	 **/
	public long findMyAutoOrderSize(Long commonUserId)throws DaoException{
		return this.myAutoOrderDao.findMyAutoOrderSize(commonUserId);
	}
	/**我的跟单管理  2010-04-12 13:31
	 * 参数:
	 * commonUserId:普通用户ID
	 **/
	public List<MyAutoOrderVO> findMyAutoOrderList(Long commonUserId)throws DaoException{
		return this.myAutoOrderDao.findMyAutoOrderList(commonUserId);
	}
	/**金牌发起人管理  2010-04-06 08:55**/
	public List  findKingCategory(Long userId)throws DaoException{
		return this.myAutoOrderDao.findKingCategory(userId);
	}
	/**金牌发起人资格审核管理  2010-04-06 09:25**/
	public List<KingSponsor>  findKingCategoryAudit(Long userId)throws DaoException{
		return this.myAutoOrderDao.findKingCategoryAudit(userId);
	}
	
	/**根据期次ID查询胜负彩14场的主对名称与客队名称  2010-04-01 17:22**/
	public List<Against> findAgainstList(Long phaseId)throws DaoException{
		return this.betRecordDao.findAgainstList(phaseId);
	}
	
	/**参与合买列表  2010-03-26 11:00
	 * 参数：
	 *   phaseNo:期次号  必须
	 *   startRow:起始记录数
	 *   pageSize:每一页的最大记录数
	 * 返回结果：
	 * totalCount:列表记录数
	 * resultList:分页列表 类型是:List<CoopOrder>
	 * hotSearchList:被搜索的热门用户  类型是:List<HotSearchVO>
	 * List<String> hostList 如果是胜负14场  必须返回主队名称
	 * mulDeadline:复式截止时间(单场没有，仅针对 14场   6场半全场  4场进球)
	 **/
	public Map findCoopList(Map params)throws DaoException{
		Map resultMap=new HashMap();
		Long phaseId=(Long)params.get("phaseId");
		String betCategory=(String)params.get("betCategory");
	    if(betCategory.length()==1&&"1235".indexOf(betCategory)!=-1){//获取复式截止时间
			String mulDeadline=this.betDao.getMulDeadline(phaseId);
			resultMap.put("mulDeadline", mulDeadline);
		}
		String advSearch=(String)params.get("advSearch");//高级搜索查询
		if("125".indexOf(betCategory)!=-1){
			 Map p=new HashMap();
			 p.put("phaseId", phaseId);
			 List hostList=this.betRecordDao.findHostAgainstList(p);
			 resultMap.put("hostList", hostList);
		 }else if("3".equals(betCategory)){
			 List<String> hostList=this.betRecordDao.findHGList(phaseId);
			 resultMap.put("hostList", hostList);
		 }
		if(StringUtils.isNotEmpty(advSearch)){
			  String[] advSearchList=advSearch.split(";");
			  StringBuilder sql=new StringBuilder();
			  String IdList=null;
			  if(betCategory.startsWith("6")||"2".equals(betCategory)){
			  //以下是单场的 与任9场高级搜索
			    for(int i=0;i<advSearchList.length;i++){
				  String temp=advSearchList[i];
				  if(StringUtils.isNotEmpty(temp)){
					String[] arr=temp.split(",");
				    String againstId=arr[0];
				    String plan=arr[1];
				    sql.append("select a.bet_order_id  from t_bet_order_choice a,t_bet_order b ");
			        sql.append(" where b.type='4' and  b.bet_categoty='"+betCategory+"' and instr(a.bet_plan,'"+plan+"')>0 and a.against_id=to_number('"+againstId+"') ");
			        sql.append(" intersect ");
				  }
			    }
			     String _sql=sql.toString();
				 _sql=_sql.trim();
				 _sql=_sql.substring(0,_sql.length()-9);
				IdList=this.betDao.loadBetOrderIds(_sql);
			  }else if("1".equals(betCategory)){//胜负彩14场高级搜索
				  Map map=new HashMap();
				  map.put("phaseId", phaseId);
				  map.put("betCategory", betCategory);
				  map.put("advSearch", advSearch);
				  IdList=this.betDao.advSearch14(map);
				
			  }else if("5".equals(betCategory)){
				  Map map=new HashMap();
				  map.put("phaseId", phaseId);
				  map.put("betCategory", betCategory);
				  map.put("advSearch", advSearch);
				  IdList=this.betDao.advSearch6(params);
			  }else if("3".equals(betCategory)){
				  Map map=new HashMap();
				  map.put("phaseId", phaseId);
				  map.put("betCategory", betCategory);
				  map.put("advSearch", advSearch);
				  IdList=this.betDao.advSearch4(params);
			  }
			 Integer startRow=(Integer)params.get("startRow");
			 Integer pageSize=(Integer)params.get("pageSize");
			 params=new HashMap();
			 params.put("startRow", startRow);
			 params.put("pageSize", pageSize);
			 params.put("phaseId", phaseId);
			 params.put("betCategory", betCategory);
			
			 if(IdList!=null&&IdList.endsWith(",")){
				 IdList=IdList.substring(0,IdList.length()-1);
			 }else{
				 resultMap.put("totalCount", 0);
				 resultMap.put("resultList", new ArrayList());
				 return resultMap;
			 }
			 params.put("IdList", IdList);
			 List<CoopOrder> resultList=this.betDao.findCoopList(params);
			 long totalCount=this.betDao.findCoopSize(params);
			 resultMap.put("totalCount", totalCount);
			 resultMap.put("resultList", resultList);
			 if("2".equals(betCategory)){//任9场
					for(CoopOrder po: resultList){
						String plan=this.betDao.load9Choice(po.getBetId());
						po.setPlan(plan);
					}
				}
			 List hotSearchList=this.hotSearchDao.findHotOrders(phaseId);
		     resultMap.put("hotSearchList", hotSearchList);
		   return resultMap;
		}
		long totalCount=this.betDao.findCoopSize(params);
		List<CoopOrder> resultList=this.betDao.findCoopList(params);
		Long userId=(Long)params.get("currentUserId");//当期用户的ID
		if("1".equals(betCategory)||"3".equals(betCategory)||"5".equals(betCategory)){//如果是胜负彩14场
		   resultList=RecordUtils.toList(resultList,myAutoOrderDao,userId);
		}
		resultMap.put("totalCount", totalCount);
		if("2".equals(betCategory)){//任9场
			resultList=RecordUtils.toOptional9List(resultList,myAutoOrderDao,userId,betDao);

		}
		resultMap.put("resultList", resultList);
		List hotSearchList=this.hotSearchDao.findHotOrders(phaseId);
		resultMap.put("hotSearchList", hotSearchList);
	  return resultMap;
	}
	
	public User  loadUser(Long userId)throws DaoException{
		return this.kingSponsorDao.loadUser(userId);
	}
	private static final Log log=LogFactory.getLog(BetServiceImpl.class);
	/**根据主键加载对象  2010-03-25 15:49**/
	public BetOrder loadBetOrder(Long id)throws DaoException{
		return betDao.loadBetOrder(id);
	}
	public List loadBetOrderChoiceList(Long id)throws DaoException{
		return null;
	}
	/**普通用户定制自动跟单  2010-05-07 09:35
	* 返回值
	* 1:定制成功
	* 3:已经定制过该金牌发起人的彩种,玩法
	* 2:已经定制满员 不能再定制
	* -1:系统报错
	**/
	@Transactional(rollbackFor = DaoException.class)
	public synchronized int  saveMyAutoOrder(MyAutoOrder po)throws DaoException{
	   return this.myAutoOrderDao.saveMyAutoOrder(po);
	}
	/**普通用户申请超级发起人  2010-03-05 16:33*/
	public Long  saveSuperSponsor(KingSponsor po)throws DaoException{
		
		return this.myAutoOrderDao.saveObject(po);
	}
	/**超级用户列表,用于自动跟单
	 * 返回:
	 *  size:记录数
	 *  resultList;返回的数据列表
	 */
	public List<SuperSponsorVO>  findSuperSponsorList(Map params)throws DaoException,BadInputException{
		return this.betDao.findSuperSponsorList(params);
	}
	/**
	 * 根据期次主键 查询该期次已经过期的对阵数量  2010-03-05 09:26
	 * id:期次主键
	 */
	public int loadDeadAgainstSize(Map params)throws DaoException{
		return this.phaseDao.loadDeadAgainstSize(params);
	}
	/**
	 * 最近若干期的期次列表
	 * 参数：
	 * phaseCategory:期次分类  '6':胜负彩期次    '9':进球彩期次 '8':半全场期次 '1':北京单场期次
	 * 
	 * @return
	 */
	public List<PhaseNoVO> findLatestPhaseList(Map params){
		return this.betDao.findLatestPhaseList(params);
	}
	/**
	 * 投注模块  根据期次查询历史对阵列表  2010-04-23 17:50
	 */
	public List<AgainstVo> findHistoryPhaseAgainstList(Map params)throws DaoException{
		Long phaseId=(Long)params.get("phaseId");//期次表ID
		String kjNo=this.phaseDao.loadKjNo(phaseId);
		List<AgainstVo> list=this.phaseDao.findLotteryPhaseAgainstList(params);
		if(StringUtils.isNotEmpty(kjNo)){
		  for(int i=0;i<list.size();i++){
			AgainstVo po=(AgainstVo)list.get(i);
			String resultNo=kjNo.substring(i,i);
			po.setResultNo(resultNo);
		  }
		}
		return list;
	}
     /**
	 * 查询某彩种的期次号  对阵列表  复式投注截止时间  2010-03-03 13:12
	 * phaseCategory:期次分类  '6':胜负彩期次    '9':进球彩期次 '8':半全场期次 '1':北京单场期次
	 * isCurrent: 足彩期次是否是当前期次 '1':当前期次  '2':预售期次 3:历史期次
	 * phaseId:期次ID,如果有期次ID 则直接安装主键去查询期次信息已经相应的对阵列表
	 * @since 2010 02-26 11:53
	 */
	
	public Map findPhaseAgainstList(Map params)throws DaoException{
		Map resultMap=new HashMap();
		LotteryPhasePO po=this.phaseDao.loadCurrentPhase(params);
		String type=(String)params.get("type");
		if(po==null){
			params.put("isCurrent", "4");
			po=this.phaseDao.loadCurrentPhase(params);
		}
	   if(po!=null){
		   Long phaseId=po.getId();
		   Map p=new HashMap();
		   p.put("phaseId", phaseId);
		   List<AgainstVo> againstList= this.phaseDao.findLotteryPhaseAgainstList(p);
		   String betCategory=(String)params.get("betCategory");
		   if("4".equals(type)){
			   if(betCategory!=null){
				   if("1".equals(betCategory)||"2".equals(betCategory)){
					 String[] statList=this.betDao.statPlan(betCategory,phaseId);
			         for(int i=0;i<againstList.size();i++){
			       
			    	   AgainstVo vo=(AgainstVo)againstList.get(i);
			    	   String statPlan=statList[i];
			    	   String[] statArr=statPlan.split("%");
			    	   StringBuilder sb=new StringBuilder();
			    	   String p3=statArr[0];
			    	   sb.append("<font color='blue'>3</font>(");
			    	   sb.append(p3+"%)");
			    	   String p1=statArr[1];
			    	   sb.append("<font color='blue'>1</font>(");
			    	   sb.append(p1+"%)");
			    	   String p0=statArr[2];
			    	   sb.append("<font color='blue'>0</font>(");
			    	   sb.append(p0+"%)");
			    	   vo.setStatPlan(sb.toString());
			      
			         }
			    
				  }
			   }
			   if("5".equals(betCategory)){
                     
			         String[] statList=this.betDao.statPlan(betCategory,phaseId);
			         for(int i=0;i<againstList.size();i++){
			    	   AgainstVo vo=(AgainstVo)againstList.get(i);
			    	   String statPlan=statList[i];
			    	   String[] tempArr=statPlan.split("-");
			    	   StringBuilder sb=new StringBuilder();
			    	 
			    		
			    		 String _statPlan=tempArr[0];
			    	     String[] statArr=_statPlan.split("%");
			    	     String p3=statArr[0];
			    	     sb.append("<font color='blue'>3</font>(");
			    	     StringBuilder space=new StringBuilder();
			    	     for(int k=0;k<5-p3.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	    
			    	     sb.append(space.toString()+p3+"%)");
			    	     String p1=statArr[1];
			    	     sb.append("<font color='blue'>1</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p1.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p1+"%)");
			    	     String p0=statArr[2];
			    	     sb.append("<font color='blue'>0</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p0.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p0+"%)");
			    	     sb.append("</br>");
			    	     _statPlan=tempArr[1];
			    	     statArr=_statPlan.split("%");
			    	     p3=statArr[0];
			    	     sb.append("<font color='blue'>3</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p3.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p3+"%)");
			    	     p1=statArr[1];
			    	     sb.append("<font color='blue'>1</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p1.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p1+"%)");
			    	     p0=statArr[2];
			    	     sb.append("<font color='blue'>0</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p0.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p0+"%)");
			    	     
			    
			    	   vo.setStatPlan(sb.toString());
			        }
				   
			   }
			 
			   if("3".equals(betCategory)){
				   String[] statList=this.betDao.statPlan(betCategory,phaseId);
			         for(int i=0;i<againstList.size();i++){
			    	   AgainstVo vo=(AgainstVo)againstList.get(i);
			    	   String statPlan=statList[i];
			    	   String[] tempArr=statPlan.split("-");
			    	   StringBuilder sb=new StringBuilder();
			    	 
			    		
			    		 String _statPlan=tempArr[0];
			    	     String[] statArr=_statPlan.split("%");
			    	     String p3=statArr[0];
			    	     sb.append("<font color='blue'>3+</font>(");
			    	     StringBuilder space=new StringBuilder();
			    	     for(int k=0;k<5-p3.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	    
			    	     sb.append(space.toString()+p3+"%)");
			    	     String p2=statArr[1];
			    	     sb.append("<font color='blue'>2</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p2.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     
			    	     sb.append(space.toString()+p2+"%)");
			    	     //
			    	     String p1=statArr[2];
			    	     sb.append("<font color='blue'>1</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p1.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p1+"%)");
			    	     
			    	     //
			    	     
			    	     String p0=statArr[3];
			    	     sb.append("<font color='blue'>0</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p0.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p0+"%)");
			    	     sb.append("</br>");
			    	     
			    	     //客队
			    	     _statPlan=tempArr[1];
			    	     statArr=_statPlan.split("%");
			    	     p3=statArr[0];
			    	     sb.append("<font color='blue'>3+</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p3.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p3+"%)");
			    	     
			    	     
			    	     p2=statArr[1];
			    	     sb.append("<font color='blue'>2</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p2.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p2+"%)");
			    	     
			    	     p1=statArr[2];
			    	     sb.append("<font color='blue'>1</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p1.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p1+"%)");
			    	     
			    	     p0=statArr[3];
			    	     sb.append("<font color='blue'>0</font>(");
			    	     space=new StringBuilder();
			    	     for(int k=0;k<5-p0.length();k++){
			    	    	 space.append("&nbsp;");
			    	     }
			    	     sb.append(space.toString()+p0+"%)");
			    
			    	   vo.setStatPlan(sb.toString());
			        }
				   
				   
				   
				   
			   }
			   
			   
			   
			   //
		   }
		   
		   resultMap.put("po", po);
		   resultMap.put("againstList", againstList);
		 }
		return resultMap;
	}
	/**
	 * 用户查询自己的投注订单列表 2010-03-01 14:03
	 */
	public Map findMyBetOrderList(Map params)throws DaoException{
		return this.betDao.findMyBetOrderList(params);
	}
	public Map loadOrderDetail(Map params)throws DaoException{
		Long userId=(Long)params.get("userId");
		User user=this.kingSponsorDao.loadUser(userId);
		Map bonusMap=this.bonusDao.findMyBonus(userId);
		long gdCnt=this.myAutoOrderDao.loadGDCnt(userId);//统计某一超级发起人的跟单总数
		return null;
	}
	
	
	   /** 
	    * 根据参数决定保存哪种彩种的投注结果
	    * 参数:
	    * 投注订单
	    * 代购 :
	    *   BET_USERID:投注人用户ID
	    *   TYPE=   '1'单代 '2' 单合  '3 '复代 '4'复合
	    *   PLAN:方案
	    *   BET_MULTI:倍数
	    *   PHASE_NO:足彩期次
	    *   BET_CATEGOTY:投注彩种    1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     61:单场半全场  62:单场比分 63:单场胜平负  64:单场上下单双 65:单场总进球
	    *   ALL_MONEY:投注金额
	    *   SUBSCRIBE_MONEY：已经付的金额（可能是冻结  也有可能是直接扣款）
	    *   categoryType:(1:足球单场,6:胜负彩,7:任九,8:6场半全场,9:4场进球) |购买b2c(10:) |购买c2c(11) 12:充值 13:取款   14:缴纳保证金15:心水保证金解冻
	    *   Long betUserid 投注人用户ID
	    *   String betUsername  投注人用户名
	    * 异常: 
	    * DaoException:数据库操作异常
	    * 返回:是否投注成功
	    * 1:投注成功
	    * -1:系统报错,不成功
	    * 4:余额不足 ，但仍然能投注
	    *  
	   */
	   @Transactional(rollbackFor = DaoException.class)
	   public long saveBetOrder(Map params) throws DaoException{
		  
		   BetOrder po=new BetOrder(params);
		   po.setZjStatus("1");//中奖状态 '1':未开奖
		   String useCaijin=(String)params.get("useCaijin");
		   String type=po.getType();
		   //购买彩票(1:足球单场,6:胜负彩,7:任九,8:6场半全场,9:4场进球) |购买b2c(10:) |购买c2c(11) 12:充值 13:取款   14:缴纳保证金15:心水保证金解冻
		   String categoryType=(String)params.get("categoryType");//保存交易日志或冻结日志使用
		   //投注彩种      1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     61:单场半全场  62:单场比分 63:单场胜平负  64:单场上下单双 65:单场总进球
		   String betContent=(String)params.get("betContent");
		   String betCategory=po.getBetCategory();
		   long payResult=-1;
		   BigDecimal alreadyPay=null;
		   long betOrderId=0;
		   try{
			   String orderNo=this.orderNoGenDao.gen("ZC");
			   po.setOrderNo(orderNo);
			   
			   if("1".equals(type)||"3".equals(type)){//代购支付
			    
			     Object betNum=params.get("betNum");
			     BigDecimal allMoney=(BigDecimal)params.get("allMoney");
			     if(betNum!=null){//针对周友明的方法
			    	 Long betMulti=po.getBetMulti();
			    	 if(betMulti==null){
			    		 betMulti=1L;
			    	 }
			    	 po.setBetNumber(new Long(betNum.toString()));//注数
			    	 po.setSubscribeMoney(allMoney);//应该支付的金额,这里为实际支付或冻结
			    	 po.setAllMoney(allMoney);//应该支付的金额
			    }
			     if(StringUtils.isNotEmpty(betContent)&&betNum==null){
			    	     String wiciType=po.getWiciType();
			    	     long betNumber=0;
			    	
			    	     
			    	     if("3".equals(wiciType)){//自由过关
			    	    	 String wiciWay=po.getViciWay();
			    	    	 String[] wiciWayArr=wiciWay.split(",");
			    	    	 for(int i=0;i<wiciWayArr.length;i++){
			    	    		 String myWicWay=wiciWayArr[i].trim();
			    	    		 if(myWicWay!=null&&myWicWay.length()>0){
			    	    		     betNumber+=Tools.getBetNumber(betContent,wiciWayArr[i]);
			    	    		 }
			    	    	 }
			    	     }else if("1".equals(wiciType)||"2".equals(wiciType)){//普通过关或者组合过关
				    	    betNumber=Tools.getBetNumber(betContent,po.getViciWay());
			    	     }
				    	 Long _betMulti=po.getBetMulti();
				    	 if(_betMulti==null){
				    		 _betMulti=1L;
				    	 }
				    	 po.setBetNumber(betNumber);//注数
				    	 BigDecimal two=new BigDecimal(2);
				    	 BigDecimal _allMoney=new BigDecimal(betNumber).multiply(two);
				    	 _allMoney=_allMoney.multiply(new BigDecimal(_betMulti));
				    	 po.setAllMoney(_allMoney);
				    	 po.setSubscribeMoney(_allMoney);
			     }
			     Long betUserid=po.getBetUserid();
				 Map resultArray=this.payDao.lotteryDaiGouPay(betUserid, 
			    		 po.getSubscribeMoney(), useCaijin,categoryType);
			     payResult=(Long)resultArray.get("payResult");
			     alreadyPay=(BigDecimal)resultArray.get("alreadyPay");//已经支付或者已经冻结的金额
			     betOrderId=(Long)resultArray.get("betOrderId");//投注订单ID
			   }
			   String orderStatus=null;//订单状态:'1':未支付、'2':待出票、'3':已出票、'4':已取消、'5':已过期  
			   if(payResult==4){//余额不足  不足以本次支付
				   orderStatus="1";//订单状态:'1':未支付
			   }else if(payResult==1){//扣款成功
				   orderStatus="2";//订单状态:'2':待出票
			   }
			   //最后必须更新订单状态,以及实际付款的金额
			   po.setBetId(betOrderId);
			   List<BetOrderChoice> betContentList=new ArrayList<BetOrderChoice>();
			   if(betCategory.startsWith("6")||"2".equals(betCategory)){//如果是足球单场代购
				 if(StringUtils.isNotEmpty(betContent)){
				   String[]  betContents=betContent.split(";");//71,310,1;82,3,0;
				   if(betContents!=null&&betContents.length>0){
					     BetOrderChoice  choice=null;
					     for(int i=0;i<betContents.length;i++){
				    	   String betContentValue=betContents[i];
				    	   String[] betContentArr=betContentValue.split(",");
				    	   if(betContentArr!=null&&betContentArr.length>0){
				    		 choice=new BetOrderChoice();
				    		 String  againstID=betContentArr[0];
					         Long againstId=Long.parseLong(againstID);//对阵ID
					         //取得场次 让球
					         
					         //
					         choice.setAgainstId(againstId);
					         String betPlan=betContentArr[1];;//用户投注结果
					         choice.setBetPlan(betPlan);
						     String danCode=betContentArr[2];//胆码
						     choice.setDanCode(danCode);
						     choice.setBetOrderId(betOrderId);
						    betContentList.add(choice);
						   }
				    	
				        }
				    }
			      }
			   }
			   po.setOrderStatus(orderStatus);
			   po.setSubscribeMoney(alreadyPay);
			   this.betDao.saveObject(po);
			   for(BetOrderChoice c:betContentList){
				   c.setBetOrderId(betOrderId);
			   }
			   if(betContentList!=null&&betContentList.size()>0){
			      this.betDao.storeAll(betContentList);
			   }
			 return payResult;
		   }catch(Exception e){
			   e.printStackTrace();
			  throw new DaoException(e.getLocalizedMessage());
		   }
	   }
	   /**合买  2010-03-22 17;22
	    * betContent:传递的投注内容 againstId,310,1;....
	    * 返回结果:
	    * 1:正常
	    * 5:账户已经被锁定 
	    * 4:余额不足 
	    * 6:剩余份数为0
	    **/
	   @Transactional(rollbackFor = DaoException.class)
	   public long saveBetCoopOrder(Map params) throws DaoException{
		   //投注彩种  1: 胜负14场  2:任9场  3:4场进球  5:6 场半全场   61:单场半全场  62:单场比分 63:单场胜平负  64:单场上下单双 65:单场总进球
		   
		   BetOrder po=new BetOrder(params);
		   po.setZjStatus("1");//中奖状态 '1':未开奖
		   String useCaijin=(String)params.get("useCaijin");
		   String categoryType=(String)params.get("categoryType");
		   String betCategory=po.getBetCategory();
		   
		   String subscribeCopys=(String)params.get("subscribeCopys");//发起人认购份数
		   po.setSubscribeCopys(new Long(subscribeCopys));
		   long result=-1;
		   long[] resultArr=null;
		   long betOrderId=0;
		   String type=po.getType();
		   String betContent=(String)params.get("betContent");
		   try{
			   String orderNo=this.orderNoGenDao.gen("ZC");
			   po.setOrderNo(orderNo);
		
			   if("2".equals(type)||"4".equals(type)){//以下是合买
				 Long betUserId=po.getBetUserid();
				 Long sponsorBetId=po.getSponsorBetId();//发起人投注ID
				 if(sponsorBetId!=null){//参与合买
					   Long subScribeCpoys=po.getSubscribeCopys();//认购份数
					   synchronized(sponsorBetId){
						 Map surplusCopysMap=this.betDao.getSurplusCopys(sponsorBetId);
						 long surplusCopys=(Long)surplusCopysMap.get("surplusCopys");
						 if(surplusCopys==0){
							 return  6;
						 }
						 BigDecimal singleMoney=(BigDecimal)surplusCopysMap.get("singleMoney");
						 if(subScribeCpoys>surplusCopys){//必须检查
							 subScribeCpoys=surplusCopys;
						 }
						 BigDecimal _subScribeCpoys=new BigDecimal(subScribeCpoys);
						 BigDecimal  _frozenMoney=singleMoney.multiply(_subScribeCpoys);
						 resultArr=this.payDao.lotteryCoopPay(betUserId,_frozenMoney, useCaijin, categoryType);
						 result=resultArr[0];
						 betOrderId=resultArr[1];
						 if(result==1){
							 po.setSubscribeMoney(_frozenMoney);
						     this.betDao.updateBySql("update T_BET_ORDER t set t.ALREADY_BUY_COPYS=ALREADY_BUY_COPYS+"+subScribeCpoys+"  where t.BET_ID="+sponsorBetId);
						     long betId=resultArr[1];
						     po.setBetId(betId);
						     po.setSponsorType("2");//发起类型'2':参与合买
						     po.setBetId(betOrderId);//设置主键
						     String planCode=(String)surplusCopysMap.get("planCode");//设置方案编号(即为发起人的方案编号)
						     po.setPlanCode(planCode);
						    
						     this.betDao.saveObject(po);
						     //如果进度达到100% 应该解冻发起人的保底金额
						     
						     //统一在扣款时解决
//						     if(subScribeCpoys==surplusCopys){
//						    
//						    	 BetOrder  sponsorOrder=this.betDao.loadBetOrderById(sponsorBetId);
//						    	 long floorCopys=sponsorOrder.getFloorCopys();
//						    	 
//						    	 long userId=sponsorOrder.getBetUserid();
//						    	 if(floorCopys>0){
//						    	    this.betDao.updateBySql("update  T_VIRTUAL_ACCOUNT t set t.frozen_money=t.frozen_money-"+new BigDecimal(floorCopys).multiply(singleMoney)+"  where  t.tx_user_id="+userId);
//						    	 }
//						     }
						   return result;
						 }else{
							 return result;
						 }
					}
				}else if(sponsorBetId==null){//发起合买
					 //1.生成订单  并且再冻结资金或彩金
					 po.setSponsorType("1");//发起类型 '1':发起人
					 String floorCopys=(String)params.get("floorCopys");//保底份数
					 po.setFloorCopys(new Long(floorCopys));
					 int frozenCopys=Integer.parseInt(subscribeCopys);
					 if(StringUtils.isNotEmpty(floorCopys)){
						   frozenCopys+=Integer.parseInt(floorCopys);
					 }
					 String recruitUsers=(String)params.get("recruitUsers");//招股对象
					 if(StringUtils.isNotEmpty(recruitUsers)){
						 po.setRecruitUsers(recruitUsers);
					 }
					 if(betCategory.startsWith("6")){//如果是足球单场
						 if(StringUtils.isNotEmpty(betContent)){
						   String[]  betContents=betContent.split(";");//71,310,1;82,3,0;
						   if(betContents!=null&&betContents.length>0){
							     long _betNumber=Tools.getBetNumber(betContent, po.getViciWay());
						    	 Long _betMulti=po.getBetMulti();
						    	 if(_betMulti==null){
						    		 _betMulti=1L;
						    	 }
						    	 po.setBetNumber(_betNumber);
						    	 BigDecimal two=new BigDecimal(2);
						    	 BigDecimal _allMoney=new BigDecimal(_betNumber).multiply(two);
						    	 _allMoney=_allMoney.multiply(new BigDecimal(_betMulti));
						    	 po.setAllMoney(_allMoney);
						    	 BigDecimal _divideCopys=new BigDecimal(po.getDivideCopys());
						    	 BigDecimal _singleMoney=_allMoney.divide(_divideCopys);
						    	 po.setSingleMoney(_singleMoney);
						   }
						 }
					 }else if("1".equals(betCategory)||"2".equals(betCategory)||"3".equals(betCategory)||"5".equals(betCategory)){//胜负彩 14场 在Action里已经计算好
						 Object betNum=params.get("betNum");//注数
						 po.setBetNumber(new Long(betNum.toString()));
						 Object allMoney=params.get("allMoney");//总金额
						 po.setAllMoney(new BigDecimal(allMoney.toString()));
						 Object singleMoney=params.get("singleMoney");//每一份金额
						 po.setSingleMoney(new BigDecimal(singleMoney.toString()));
						 Object subscribeMoney=params.get("subscribeMoney");//认购金额
						 po.setSubscribeMoney(new BigDecimal(subscribeMoney.toString()));
					  }
					 BigDecimal frozenMoney=new BigDecimal(frozenCopys).multiply(po.getSingleMoney());
					 BigDecimal subscribeMoney=new BigDecimal(po.getSubscribeCopys()).multiply(po.getSingleMoney());
					 po.setSubscribeMoney(subscribeMoney);
					 resultArr=this.payDao.lotteryCoopPay(betUserId, frozenMoney, useCaijin, categoryType);
					 result=resultArr[0];
					 betOrderId=resultArr[1];
					 po.setBetId(betOrderId);//设置主键
					 if(result==1){
					   List<BetOrderChoice> betContentList=new ArrayList<BetOrderChoice>();
					   if("4".equals(type)&&(betCategory.startsWith("6")||"2".equals(betCategory))){//如果是足球单场代购,或者是任9场
						  if(StringUtils.isNotEmpty(betContent)){
						   String[]  betContents=betContent.split(";");//71,310,1;82,3,0;
						   if(betContents!=null&&betContents.length>0){
							     BetOrderChoice  choice=null;
							     for(int i=0;i<betContents.length;i++){
						    		 choice=new BetOrderChoice();
						    		 String betContentValue=betContents[i];
						    		 String[] betContentArr=betContentValue.split(",");
						    		 String  againstID=betContentArr[0];
							         Long againstId=Long.parseLong(againstID);//对阵ID
							         choice.setAgainstId(againstId);
							         String betPlan=betContentArr[1];;//用户投注结果
							         choice.setBetPlan(betPlan);
								     String danCode=betContentArr[2];//胆码
								     choice.setDanCode(danCode);
								
								     if("2".equals(betCategory)&&betContentArr.length==4){
								       String changci=betContentArr[3];//场次
								       choice.setChangci(new Long(changci));
								     }
								     choice.setBetOrderId(betOrderId);
								    
								   betContentList.add(choice);
						        }
						    	
						   }
					    }
					   }
					   po.setOrderStatus("2");//订单状态:'1':未支付、'2':待出票、'3':已出票、'4':已取消、'5':已过期
					   String planCode=this.orderNoGenDao.gen("FA");
					   po.setPlanCode(planCode);
					   po.setAlreadyBuyCopys(po.getSubscribeCopys());//发起合买时 总进度为自己的认购份数
					   this.betDao.saveObject(po);
					   for(BetOrderChoice c:betContentList){
						   c.setBetOrderId(betOrderId);
					   }
					   if(betContentList!=null&&betContentList.size()>0){
					      this.betDao.storeAll(betContentList);
					   }
					   //2.查看自动跟单列表  并且按照自动跟单定制的先后顺序 把这些定制用户加入到合买计划中,   并且再冻结资金或彩金
					   Map gdMap=new HashMap();
					   gdMap.put("spOrderId",po.getBetId());
					   gdMap.put("sponsorUserId",po.getBetUserid());
					   gdMap.put("singleMoney",po.getSingleMoney());
					   gdMap.put("type", po.getType());
					   gdMap.put("categoryType", categoryType);
					   myAutoOrderDao.executeGz(gdMap);
					 }
					}
				 }
			return result;
		   }catch(Exception e){
			   e.printStackTrace();
			  throw new DaoException(e.getLocalizedMessage());
		   }
		   //return 0;
	   }
	   /**判断用户是否已经申请某彩种的玩法   2010-04-23 11:40
		 * 返回值:
		 *   false:已经申请,本次不能再申请
		 *   true:本次可以申请
		 **/
		public boolean  isAlreadyApply(Long userId,String betCategory,String type)throws DaoException{
			return this.kingSponsorDao.isAlreadyApply(userId, betCategory, type);
		}
	   /**查询用户已经申请的彩种以及玩法
	    * 投注彩种    1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     61:单场半全场  62:单场比分 63:单场让球胜平负  64:单场上下单双 65:单场总进球
	    * 类型 :'1'单代 '2' 单合  '3 '复代 '4'复合
	    * 
	    * 如果某一彩种的单式与复式都已经申请 则必须把选择框灰掉
	    **/
	   public List<KingSponsor>  findAlreadyApplyCategoryList(Long userId)throws DaoException{
		   return this.kingSponsorDao.findAlreadyApplyCategoryList(userId);
	   }
	   /**其他界面要用到自动跟单的详细列表通过3个参数读取一条自动跟单的详细信息  2010-04-29 09:46**/
		public KingSponsor  loadAutoOrder(Long userId,String betCategory,String flg)throws DaoException{
			return this.kingSponsorDao.loadAutoOrder(userId, betCategory, flg);
		}
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
	   @Transactional(rollbackFor = DaoException.class)
	   public int saveKingSponsor(KingSponsor po)throws DaoException{
		   try{
			boolean isAllowApply=this.kingSponsorDao.isAlreadyApply(po.getUserid(), po.getBetCategory(), po.getType());
			if(isAllowApply){
		       String dzCnt=this.dictionaryDao.getValue("DZ_CNT", "BET");//在字典表里查询金牌发起人可定制多少人
		       po.setDzCnt(new Long(dzCnt));
		       po.setAlreadyDzCnt(0L);
		       String type=po.getType();
		       KingSponsor po1=po;
		       KingSponsor po2=po;
		       if(type.indexOf(",")!=-1){
		    	   String[] types=type.split(",");
		    	   po1.setTypeValue(types[0]);
		    	   po2.setTypeValue(types[1]);
		       }else{
		    	   this.kingSponsorDao.saveObject(po1);
		       }
		       this.kingSponsorDao.saveObject(po1);
		       this.kingSponsorDao.saveObject(po2);
		       return 1;
			}
		  }
		  catch(Exception e){
			  e.printStackTrace();
		  }
		  return 0;
	   }
	   /**
	    * 我要跟单
	    * 定制自动跟单  2010-03-08 14:40
	    *
	    */
	   @Transactional(rollbackFor = DaoException.class)
	   public boolean dzAutoGz(MyAutoOrder po)throws DaoException{
		    Long kingId=po.getKingId();
		    this.myAutoOrderDao.saveObject(po);
		    boolean s=false;
		    synchronized(kingId){//必须同步
		    	s=this.kingSponsorDao.updateBySql("update T_KING_SPONSOR t set t.ALREADY_DZ_CNT=t.ALREADY_DZ_CNT+1  where  t.id="+kingId);
		    }
		    return s;
	   }
	 
	   
	   
   /**
	 * 上传文件AJAX严整
	 * */
	@Override
	public Map<String, Object> dsdgValidator(Map<String, Object> param) {
		param = uploadFileValidator(param);
		if (null != param.get("error")) {return param;}
		return fileContentValidator(param);
	}

	
	/**
	 * 文件合法严整 
	 * */
	public Map<String, Object> uploadFileValidator(Map<String, Object> param) {
		File file = (File) param.get("file");
		String fileFileName = (String) param.get("fileFileName");
		if (file.length() > Constants.FILE_SIZE) {
			param.put("error", "上传方案太大!");
			return param;
		}
		if (null == fileFileName) {
			param.put("error", "方案名称有误!");
			return param;
		} else {
			int j = fileFileName.indexOf(".");
			if (-1 == j) {
				param.put("error", "方案名称有误!");
				return param;
			} else if (!Constants.FILE_TYPE.equals(fileFileName.substring(j + 1, fileFileName.length()))) {
				param.put("error", "请使用 .txt 文件!");
				return param;
			}
		}
		return param;
	}

	/**
	 * 文件内容校验 
	 * */
	public Map<String, Object> fileContentValidator(Map<String, Object> param) {
		File file             = (File) param.get("file");
		String validator_str  = (String) param.get("upload_input");
		Integer against_count = (Integer) param.get("against_count");
		String from           = (String) param.get("from");
		String pass_fashion = (String) param.get("pass_fashion");
		String content = null;
		try {
			content = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
			param.put("error", "文件读取失败");
			return param;
		}
		if(StringUtils.isBlank(content)){
			param.put("error", "文件不能为空!");
			return param;
		}
		int file_count = 1;         //文件有效行数
		String bet_content = null;
		int bet_count = 0;
		
		String validatorString = null;
		String splitString = null;
		
		if("scores".equals(from) && !checkUploadStrForScores(content)){
			param.put("error", "比分格式不正确");
			return param;
		}
		String[] contents = content.split("\r\n");
		int pass_fash = 0;
		if("单关".equals(pass_fashion)){
			pass_fash = 1;
		}else{
			pass_fash = Integer.valueOf(pass_fashion.substring(0,1));
		}
		
		for(int i = 0;i < contents.length;i++){
			int upload_count = 0;             //有效注数
			bet_content = contents[i].trim();
			if(StringUtils.isBlank(bet_content)) continue;
			//获取分割符
			splitString = getSplitString(bet_content,against_count);
			if("-1".equals(splitString)){
				param.put("error", "上传文件格式或分割符错误<br>请您对照标准格式重新上传");
				return param;
			}
			bet_content = bet_content.replaceAll(splitString+"\\*", "").replaceAll("\\*", "")
									 .replaceAll(splitString+"#", "").replaceAll("#", "");
			//有效投注场次 校验
			if("".equals(splitString)){
				upload_count = bet_content.length();
			}else{
				upload_count = bet_content.split(splitString).length;
			}
			if(upload_count < pass_fash){
				param.put("error", "投注场次小于选择的过关方式,您的过关方式为:"+ pass_fashion +",上传了"+ 
						upload_count +"场.少投了"+ (pass_fash-upload_count) +"场,出错的投注是:<br>" + 
						contents[i].replace("<", "").replace(">", ""));
				return param;
			}
			validatorString = this.getValidatorString(splitString,validator_str,upload_count,from);
			if("".equals(validatorString)){
				param.put("error", "选择场次与投注内容不符合!");
				return param;
			}
			if (!validatorFasion(bet_content, validatorString)) {
				param.put("error", "格式不正确,出错单注是第" + file_count + "行的:<br>"+ contents[i].replace("<", "").replace(">", ""));
				return param;
			}
			
			//计算总注数
			//普通过关
			if(pass_fash == against_count){
				if(upload_count != against_count){
					param.put("error", "您的投注场次与选择的场次不相等<br>您选择了" + against_count + "场,投注了" + upload_count +
							  "场,出错单注是第" + file_count + "行的:<br>"+ contents[i].replace("<", "").replace(">", ""));
					return param;
				}
				bet_count += against_count;
			}else{  //自由过关	
				String contents_trim = contents[i].trim();
				if(contents_trim.indexOf("*") == -1 &&  contents_trim.indexOf("#") == -1){
					if(upload_count != against_count){
						param.put("error", "组合投注场次与所选串法不符!<br>出错单注是:" + contents[i].replace("<", "").replace(">", ""));
						return param;
					}
				}else{
					String cout_trim_r = contents_trim.replaceAll("\\*", "#");
					int split_count = cout_trim_r.split("#").length -1;
					if(cout_trim_r.charAt(cout_trim_r.length()-1) == '#')
						split_count ++;
					if(split_count + upload_count != against_count){
						param.put("error", "组合投注场次与所选串法不符!<br>出错单注是:" + contents[i].replace("<", "").replace(">", ""));
						return param;
					}
				}
				bet_count += combiningAlgorithms(pass_fash,upload_count);
				
			}
			file_count++;
		}
		if(1 == file_count){
			param.put("error", "上传文件内容或格式不正确!");
			return param;
		}
		param.put("count", bet_count);
		return param;
	}
	
	//获取文件分割符
	private String getSplitString(String str,int against_count) {
		if(str.length() == against_count)return "";
		if(str.indexOf(",") != -1) return ",";
		else if(str.indexOf("，") != -1) return "，";
		else if(str.indexOf("-") != -1) return "-";
		else if(str.indexOf(" ") != -1) return " ";
		return "-1";
	}

	/**
	 * 文件上传
	 * */
	
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> fileUpload(Map<String, Object> param) {
		File file           = (File) param.get("file");
		String fileFileName = (String) param.get("fileFileName");
		String savePath     = (String) param.get("savePath");
		
		String phaseNo = (String) param.get("phaseNo");
		String dsdg_type =  (String) param.get("betCategory");
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		String root = ServletActionContext.getServletContext().getInitParameter("uploadPath") + "/"+ Constants.UPLOAD_PALN_FILE + dsdg_type+   "/" + phaseNo + "/";
        if(! (new File(root)).exists()) {
        	new File(root).mkdirs();
        }
		OutputStream fos = null;
		String path = null;
		
		try {
			path = root + Calendar.getInstance().getTime().getTime() + new Random().nextInt(1000) + fileFileName;
			fos = new FileOutputStream(path);
			
			IOUtils.copy(fis, fos);
		} catch (IOException e) {
			param.put("error", "文件格式不正确!");
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(fos);
			e.printStackTrace();
			return param;
		} finally {
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(fos);
		}
		param.put("path", path);
		return param;
	}
	
	/**
	 * 比分 格式 单独 校验 排除 43 ,44 ,34 
	 * */
	private boolean checkUploadStrForScores(String content) {
		if(content.indexOf("43") != -1){
			return false;
		}else if(content.indexOf("44") != -1){
			return false;
		}else if(content.indexOf("34") != -1){
			return false;
		}
		return true;
	}

	/**
	 * 获取校验格式
	 * */
	public String getValidatorString(String splitString, String validator_str,Integer against_count, String from){
		String regex = null;
		StringBuilder validator_sb = new StringBuilder();
		if("wdlose".equals(from) || "updown".equals(from) || "totalGoal".equals(from)){
			if(against_count ==1){
				validator_sb.append("["+validator_str+"]{1}");
				return validator_sb.toString();
			}else{
				regex = "["+validator_str+"]{1}" + splitString;
				for(int i = 1; i<against_count; i++){
					validator_sb.append(regex);
				}
				validator_sb.append("["+validator_str+"]{1}");
			}
			return validator_sb.toString();
		}else if("scores".equals(from)){
			validator_str = "(([0-4]{2})|([3A|1A|0A]{2}))";
		}else if("half_full".equals(from)){
			validator_str = "[0-3]{2}";
		}else{
			return "";
		}
		if(against_count ==1){
			validator_sb.append(validator_str);
		}else{
			for(int i = 1; i<against_count; i++){
				validator_sb.append(validator_str + splitString);
			}
			validator_sb.append(validator_str);
		}
		return validator_sb.toString();
	}
		
	/**
	 * 各类单式上传方案严整
	 * @param   str                将要验证的字符
	 * @param 	validator_str      用户(自定义)匹配模式
	 * */
	public boolean validatorFasion(String str , String validator_str){
		return Pattern.compile( validator_str ).matcher(str).matches();
	} 
	
	/**
	 * 组合算法
	 * */
	public int combiningAlgorithms(int up, int down){
		if(up ==0 || down ==0)return -1;
		int u = 1;
		int d = 1;
		for(int i=1; i< up+1;i++ ){
			u *= down--;
		}
		while( up>0 ){
			d *= up--;
		}
		return u/d;
	}   
	
	/**
	 * 单式代购 投注--(保存)
	 * */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> dsdg_submit(Map<String, Object> param) {
		param = uploadFileValidator(param);
		if (null != param.get("error")) {return param;}
		param = fileContentValidator(param);
		if (null != param.get("error")) {return param;}
		param =  fileUpload(param);
		if (null == param.get("path")) {return param;}
		Long count = Long.parseLong(param.get("count")+"");
		Long multiple = Long.parseLong(param.get("betMulti")+"");
		String against_ids = ((String)param.get("against_ids"));
		//String allMoney = count * multiple * 2+"";
		BigDecimal allMoney = new BigDecimal(count * multiple * 2);
		param.put("betNum", count);
		param.put("allMoney", allMoney);
		param.put("formatStr", param.get("upload_input"));
		param.put("txtPath", param.get("path"));
		param.put("plan", against_ids.substring(0, against_ids.length()-1));
		
		Long flag = null;
		try {
			flag = saveBetOrder(param);
		} catch (DaoException e) {
			e.printStackTrace();
			param.put("error", "投注失败！");
		}
		//投注生成记录结果
		param.put("flag", flag+"");
		allMoney = null;
		return param;
	}
	
	//单试合买
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> dshm_submit(Map<String, Object> param) {
		param = uploadFileValidator(param);
		if (null != param.get("error")) {return param;}
		param = fileContentValidator(param);
		if (null != param.get("error")) {return param;}
		param =  fileUpload(param);
		if (null == param.get("path")) {return param;}
		Long count = Long.parseLong(param.get("count")+"");
		Long multiple = Long.parseLong(param.get("betMulti")+"");
		String against_ids = ((String)param.get("against_ids"));
		//String allMoney = count * multiple * 2 + "";
		BigDecimal allMoney = new BigDecimal(count * multiple * 2);
		param.put("betNumber", param.get("count")+"");
		param.put("allMoney", allMoney);
		param.put("formatStr", param.get("upload_input"));
		param.put("txtPath", param.get("path"));
		param.put("plan", against_ids.substring(0, against_ids.length()-1));
		Long flag = null;
		try {
			flag = saveBetCoopOrder(param);
		} catch (DaoException e) {
			e.printStackTrace();
			param.put("error", "投注失败！");
		}
		//投注生成记录结果
		param.put("flag", flag+"");
		return param;
	}
	
	
	
	/**
	 * 获取投注--上下半场--详情
	 * @param	content			客户上传的文件内容
	 * @param	against_ids		对阵IDS
	 * @return  554,08,0;555,27,0;
	 * */
	@Deprecated
	private String getBetContentForUpDown(String content, String against_ids) {
		String betResults="012345678";
		String[] format_target = {"33","31","30","13","11","10","03","01","00"};
		int ft_length = format_target.length;
		for(int i=0; i < ft_length; i++){
			content.replaceAll(format_target[i], Character.toString(betResults.charAt(i)));
		}
		String[] contents = content.split("\r\n");
		String[] a_ids = against_ids.split(",");
		StringBuilder betContent = new StringBuilder();
		int id_len = a_ids.length;
		int con_len = contents.length;
		String[] contents_str = null;
		String splitString = null;
		for(int i = 0; i< id_len; i++){
			betContent.append(a_ids[i]+",");
			for(int j = 0; j< con_len; j++){
				if( "" ==contents[j]) continue;
				splitString = getSplitString(contents[j].trim(),id_len);
				if("".equals(splitString)){
					betContent.append(contents[j].charAt(i));
				}else{
					contents_str = contents[j].split(splitString);
					betContent.append(contents_str[i]);
				}
			}	
			betContent.append(",0;");
		}
		return betContent.toString();
	}

	/**
	 * 获取投注--比分--详情
	 * @param	content			客户上传的文件内容
	 * @param	against_ids		对阵IDS
	 * @return  554,fw,0;555,bt,0;
	 * */
	@Deprecated
	private String getBetContentForScores(String content, String against_ids){
		String betResults="abcdefghigklmnopqrstuvwxyz";
		String[] format_target = {"3A","1:0","2:0","2:1","3:0","3:1","3:2","4:0","4:1","4:2","1A","0:0","1:1","2:2","3:3","0A","0:1","0:2","1:2","0:3","1:3","2:3","0:4","1:4","2:4"};
		int ft_length = format_target.length;
		for(int i=0; i < ft_length; i++){
			content.replaceAll(format_target[i], Character.toString(betResults.charAt(i)));
		}
		String[] contents = content.split("\r\n");
		String[] a_ids = against_ids.split(",");
		StringBuilder betContent = new StringBuilder();
		int id_len = a_ids.length;
		int con_len = contents.length;
		String[] contents_str = null;
		String splitString = null;
		
		for(int i = 0; i< id_len; i++){
			betContent.append(a_ids[i]+",");
			for(int j = 0; j< con_len; j++){
				if( "" ==contents[j]) continue;
				splitString = getSplitString(contents[j].trim(),id_len);
				if("".equals(splitString)){
					betContent.append(contents[j].charAt(i));
				}else{
					contents_str = contents[j].split(splitString);
					betContent.append(contents_str[i]);
				}
			}	
			betContent.append(",0;");
		}
		return betContent.toString();
	}
	
	
	/**
	 * 获取投注---详情
	 * @param	content			客户上传的文件内容
	 * @param	against_ids		对阵IDS
	 * @param	formatStr		 客户自定义格式
	 * @param	type             单式种类
	 * @return  554,03,0;555,01,0;
	 * */
	@Deprecated
	private String getBetContent(String content, String against_ids, String formatStr, String type){
		if("wdlose".equals(type) && !"310".equals(formatStr))           //让球
			content = replaceFormatStr(formatStr,content,type);
		else if("updown".equals(type) && !"3210".equals(formatStr)){	//上下单双
			content = replaceFormatStr(formatStr,content,type);
		}else if("totalGoal".equals(type) && !"76543210".equals(formatStr)){ //总进球
			content = replaceFormatStr(formatStr,content,type);
		}
		if("".equals(content))return "";
		String[] contents = content.split("\r\n");
		String[] a_ids = against_ids.split(",");
		StringBuilder betContent = new StringBuilder();
		int id_len = a_ids.length;
		int con_len = contents.length;
		String[] contents_str = null;
		String splitString = null;
		for(int i = 0; i< id_len; i++){
			betContent.append(a_ids[i]+",");
			for(int j = 0; j< con_len; j++){
				if( "" ==contents[j]) continue;
				splitString = getSplitString(contents[j].trim(),id_len);
				if("".equals(splitString)){
					betContent.append(contents[j].charAt(i));
				}else{
					contents_str = contents[j].split(splitString);
					betContent.append(contents_str[i]);
				}
			}	
			betContent.append(",0;");
		}
		return betContent.toString();
	}   
	
	/**
	 *  替换格式化字符串
	 * */
	@Deprecated
	private String replaceFormatStr(String formatStr, String content, String type) {
		String format_target = null;
		if("wdlose".equals(type)){          //让球胜平负
			format_target = "310";
		}else if("updown".equals(type)){	//上下单双
			format_target = "3210";
		}else if("totalGoal".equals(type)){
			format_target = "76543210";
		}
		if(null == format_target)return "";
		StringBuffer sb = null;
		for(int k = 0;k<formatStr.length();k++){
			sb = new StringBuffer();
			Pattern p = Pattern.compile(formatStr.substring(k,k+1));
			Matcher m = p.matcher(content);
			while (m.find()) {
				m.appendReplacement(sb, format_target.substring(k,k+1));
			}
			m.appendTail(sb);
			content = sb.toString();
		}
		return sb.toString();
	}
	
	@Override
	public List<Against> findDSDGAgainst(String against_ids) {
		Long[] id_1 = ArrayUtils.toObject((long[])ConvertUtils.convert(against_ids.split(","), Long.TYPE));
		return againstDao.read(id_1);
	}
	
	@Override
	public List<BetOrder> findBetOrderByField(String fieldName,String fieldValue) {
		return betDao.findByLike(fieldName, fieldValue);
	}
	
	@Override
	public List<InGoalBetScaleVo> getCurBetScale(String phase) {
		return betDao.getCurBetScale(phase);
	}
	
	@Override
	public List<InGoalBetScaleVo> getDarkRank(String phase) {
		List<InGoalBetScaleVo> list = betDao.getCurBetScale(phase);
		List<InGoalBetScaleVo> result = new ArrayList<InGoalBetScaleVo>();
		if(null == list)return null;
		String no = (phaseDao.read(Long.parseLong(phase))).getKjNo();
		
		InGoalBetScaleVo guest = null;
		InGoalBetScaleVo vo = null;
		for(int i = 0; i <list.size(); i++){
			vo = list.get(i);
			vo.setHost_in_goal(no.substring(2*i, 2*i+1));
			guest = new InGoalBetScaleVo();
			guest.setHost_team_name(vo.getGuest_team_name());
			guest.setBet_scale_h_0(vo.getBet_scale_g_0());
			guest.setBet_scale_h_1(vo.getBet_scale_g_1());
			guest.setBet_scale_h_2(vo.getBet_scale_g_2());
			guest.setBet_scale_h_3(vo.getBet_scale_g_3());
			guest.setHost_in_goal(no.substring(2*i+1, 2*(i+1)));
			result.add(guest);
			result.add(vo);
		}
		list = null;
		Collections.sort(result);
		return result;
		
	}
	
	@Override
	/**
	 * 合买热门搜索
	 * */
	public List<BetHotSearch> hot_search(){
		return hotSearchDao.findByLike("t.status", "1");
	}
	

	@Autowired
	private BetDao betDao;
	@Autowired
	private OrderNoGenDao orderNoGenDao;
	@Autowired
	private PhaseDao phaseDao;
	@Autowired
	private PayDao payDao;
	@Autowired
	private MyAutoOrderDao myAutoOrderDao;
	@Autowired
	private KingSponsorDao kingSponsorDao;
	@Autowired
	private DictionaryDao dictionaryDao;
	@Autowired
	private BonusDao bonusDao;
	@Autowired
	private HotSearchDao hotSearchDao;
	@Autowired
	private AgainstDao againstDao;
	@Autowired
	private BetRecordDao betRecordDao;
	@Resource
	private KingSponsorCategoryDao kingSpCatDao;
	
	 
}
