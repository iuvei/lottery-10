package com.wintv.lottery.bet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.BetOrderChoice;

import com.wintv.framework.pojo.User;
import com.wintv.lottery.admin.phase.dao.PhaseDao;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.attention.dao.MyAttentionDao;
import com.wintv.lottery.bet.dao.BetDao;
import com.wintv.lottery.bet.dao.BetRecordDao;
import com.wintv.lottery.bet.dao.BonusDao;
import com.wintv.lottery.bet.dao.KingSponsorDao;
import com.wintv.lottery.bet.dao.MyAutoOrderDao;
import com.wintv.lottery.bet.service.CanyuBuyService;
import com.wintv.lottery.bet.vo.CanyuVO;

@Service("canyuBuyService")
@SuppressWarnings("unchecked")
public class CanyuBuyServiceImpl implements CanyuBuyService{
	/**投注记录  单场:投注内容 2010-03-26 15:32**/
    public List<BetOrderChoice> loadMyBetOrderChoiceList(Long betOrderId,Long phaseId)throws DaoException{
    	return this.betDao.loadMyBetOrderChoiceList(betOrderId, phaseId);
    }
	/**北京单场   2010-04-14 15:22**/
	public Map loadBeijingOrderInfo(Long betOrderId)throws DaoException{
		 BetOrder  betOrder=this.betDao.loadBetOrder(new Long(betOrderId));
		 Long phaseId=betOrder.getPhaseId();
		 Map resultMap=new HashMap();
		 Long userId=betOrder.getBetUserid();
		 User user=this.kingSponsorDao.loadUser(userId);
		 long betMilitary=user.getBetMilitary();
		 StringBuilder stars=new StringBuilder();
		 for(long i=0;i<betMilitary;i++){//战绩
				 stars.append("★");
		 }
		 stars.append("<span>");
		 for(long j=0;j<10-betMilitary;j++){
				 stars.append("☆");
		 }
		 stars.append("</span>");
		 resultMap.put("stars", stars.toString());
		 resultMap.put("betOrder", betOrder);
		 Map bonusMap=this.bonusDao.findMyBonus(userId);
		 long zjCnt=(Long)bonusMap.get("zjCnt");//中奖次数
		 resultMap.put("zjCnt", zjCnt);
		 
		 long isKingSponsor=this.kingSponsorDao.isKingSponsor(userId);
		 if(isKingSponsor>0){//大于1表示是金牌发起人
			 resultMap.put("isKingSponsor", isKingSponsor);
		 }
		
		 long myattentionCnt=myAttentionDao.findMyAttentionSize(userId);//统计关注我的人数量
		 resultMap.put("myattentionCnt", myattentionCnt);
		 
		 resultMap.put("isKingSponsor", isKingSponsor);
		
		 long  kingGDCnt=this.myAutoOrderDao.loadGDCntByKingUserId(userId);
		 resultMap.put("kingGDCnt", kingGDCnt);//统计金牌发起人的跟单总次数
		 String kjNo=this.phaseDao.loadKjNo(phaseId);//查询开奖号码
		 resultMap.put("kjNo", kjNo);
		 String  planStatus=betOrder.getOrderStatus();
		 if("4".equals(planStatus)){//已撤单
			 planStatus="3";
		 }else{
			 long alreadyBuyCopys=betOrder.getAlreadyBuyCopys();
			 long allCopys=betOrder.getDivideCopys();
			 if(alreadyBuyCopys==allCopys){
				 planStatus="1";
			 }else if(alreadyBuyCopys<allCopys){
				 planStatus="2";
			 }
		 }
		 resultMap.put("planStatus", planStatus);
		
	  return resultMap;
	}
	/**4场进球  2010-04-13 16:14**/
	public Map loadGoal4Info(Long betOrderId)throws DaoException{
		 BetOrder  betOrder=this.betDao.loadBetOrder(new Long(betOrderId));
		 Long phaseId=betOrder.getPhaseId();
		 Map resultMap=new HashMap();
		 Long userId=betOrder.getBetUserid();
		 User user=this.kingSponsorDao.loadUser(userId);
		 long betMilitary=user.getBetMilitary();
		 StringBuilder stars=new StringBuilder();
		 for(long i=0;i<betMilitary;i++){//战绩
				 stars.append("★");
		 }
		 stars.append("<span>");
		 for(long j=0;j<10-betMilitary;j++){
				 stars.append("☆");
		 }
		 stars.append("</span>");
		 resultMap.put("stars", stars.toString());
		 resultMap.put("betOrder", betOrder);
		 Map bonusMap=this.bonusDao.findMyBonus(userId);
		 long zjCnt=(Long)bonusMap.get("zjCnt");//中奖次数
		 resultMap.put("zjCnt", zjCnt);
		 
		 long isKingSponsor=this.kingSponsorDao.isKingSponsor(userId);
		 if(isKingSponsor>0){//大于1表示是金牌发起人
			 resultMap.put("isKingSponsor", isKingSponsor);
		 }
		
		 long myattentionCnt=myAttentionDao.findMyAttentionSize(userId);//统计关注我的人数量
		 resultMap.put("myattentionCnt", myattentionCnt);
		 
		 resultMap.put("isKingSponsor", isKingSponsor);
		
		 long  kingGDCnt=this.myAutoOrderDao.loadGDCntByKingUserId(userId);
		 resultMap.put("kingGDCnt", kingGDCnt);//统计金牌发起人的跟单总次数
		 String kjNo=this.phaseDao.loadKjNo(phaseId);//查询开奖号码
		 resultMap.put("kjNo", kjNo);
		 String  planStatus=betOrder.getOrderStatus();
		 if("4".equals(planStatus)){//已撤单
			 planStatus="3";
		 }else{
			 long alreadyBuyCopys=betOrder.getAlreadyBuyCopys();
			 long allCopys=betOrder.getDivideCopys();
			 if(alreadyBuyCopys==allCopys){
				 planStatus="1";
			 }else if(alreadyBuyCopys<allCopys){
				 planStatus="2";
			 }
		 }
		 resultMap.put("planStatus", planStatus);
		 BigDecimal bonus=betOrder.getBonus();
		 if(bonus!=null){
		   if(bonus.longValue()>0){
		     BigDecimal tcRate=bonus.multiply(new BigDecimal(betOrder.getTcRate()));
		     resultMap.put("tcRate", tcRate);
		 
		     bonus=betOrder.getBonus();
		     BigDecimal bonusPerCopy=bonus.divide(new BigDecimal(betOrder.getDivideCopys()));
		     resultMap.put("bonusPerCopy", bonusPerCopy);
		   }
		 }
		 resultMap.put("planStatus", planStatus);
		 
		 
	  return resultMap;
	}
	/**6场半全场  2010-04-13 16:14**/
	public Map loadHalfComplete6Info(Long betOrderId)throws DaoException{
		 BetOrder  betOrder=this.betDao.loadBetOrder(new Long(betOrderId));
		 Long phaseId=betOrder.getPhaseId();
		 Map resultMap=new HashMap();
		 Long userId=betOrder.getBetUserid();
		 User user=this.kingSponsorDao.loadUser(userId);
		 long betMilitary=user.getBetMilitary();
		 StringBuilder stars=new StringBuilder();
		 for(long i=0;i<betMilitary;i++){//战绩
				 stars.append("★");
		 }
		 stars.append("<span>");
		 for(long j=0;j<10-betMilitary;j++){
				 stars.append("☆");
		 }
		 stars.append("</span>");
		 resultMap.put("stars", stars.toString());
		 resultMap.put("betOrder", betOrder);
		 Map bonusMap=this.bonusDao.findMyBonus(userId);
		 long zjCnt=(Long)bonusMap.get("zjCnt");//中奖次数
		 resultMap.put("zjCnt", zjCnt);
		 
		 long isKingSponsor=this.kingSponsorDao.isKingSponsor(userId);
		 if(isKingSponsor>0){//大于1表示是金牌发起人
			 resultMap.put("isKingSponsor", isKingSponsor);
		 }
		
		 long myattentionCnt=myAttentionDao.findMyAttentionSize(userId);//统计关注我的人数量
		 resultMap.put("myattentionCnt", myattentionCnt);
		 
		 resultMap.put("isKingSponsor", isKingSponsor);
		
		 long  kingGDCnt=this.myAutoOrderDao.loadGDCntByKingUserId(userId);
		 resultMap.put("kingGDCnt", kingGDCnt);//统计金牌发起人的跟单总次数
		 String kjNo=this.phaseDao.loadKjNo(phaseId);//查询开奖号码
		 resultMap.put("kjNo", kjNo);
		 String  planStatus=betOrder.getOrderStatus();
		 if("4".equals(planStatus)){//已撤单
			 planStatus="3";
		 }else{
			 long alreadyBuyCopys=betOrder.getAlreadyBuyCopys();
			 long allCopys=betOrder.getDivideCopys();
			 if(alreadyBuyCopys==allCopys){
				 planStatus="1";
			 }else if(alreadyBuyCopys<allCopys){
				 planStatus="2";
			 }
		 }
		 resultMap.put("planStatus", planStatus);
		 
		 BigDecimal bonus=betOrder.getBonus();
		 if(bonus!=null){
		   if(bonus.longValue()>0){
		     BigDecimal tcRate=bonus.multiply(new BigDecimal(betOrder.getTcRate()));
		     resultMap.put("tcRate", tcRate);
		 
		     bonus=betOrder.getBonus();
		     BigDecimal bonusPerCopy=bonus.divide(new BigDecimal(betOrder.getDivideCopys()));
		     resultMap.put("bonusPerCopy", bonusPerCopy);
		   }
		 }
	  return resultMap;
	}
	/**任9场  2010-04-13 13:37**/
	public Map loadOptional9OrderInfo(Long betOrderId)throws DaoException{
		 BetOrder  betOrder=this.betDao.loadBetOrder(new Long(betOrderId));
		 Long phaseId=betOrder.getPhaseId();
		 Map resultMap=new HashMap();
		 Long userId=betOrder.getBetUserid();
		 User user=this.kingSponsorDao.loadUser(userId);
		 long betMilitary=user.getBetMilitary();
		 StringBuilder stars=new StringBuilder();
		 for(long i=0;i<betMilitary;i++){//战绩
				 stars.append("★");
		 }
		 stars.append("<span>");
		 for(long j=0;j<10-betMilitary;j++){
				 stars.append("☆");
		 }
		 stars.append("</span>");
		 resultMap.put("stars", stars.toString());
		 String plan=this.betDao.load9Choice(betOrder.getBetId());
		 betOrder.setPlan(plan);
		 resultMap.put("betOrder", betOrder);
		 Map bonusMap=this.bonusDao.findMyBonus(userId);
		 long zjCnt=(Long)bonusMap.get("zjCnt");//中奖次数
		 resultMap.put("zjCnt", zjCnt);
		 
		 long isKingSponsor=this.kingSponsorDao.isKingSponsor(userId);
		 if(isKingSponsor>0){//大于1表示是金牌发起人
			 resultMap.put("isKingSponsor", isKingSponsor);
		 }
		
		 long myattentionCnt=myAttentionDao.findMyAttentionSize(userId);//统计关注我的人数量
		 resultMap.put("myattentionCnt", myattentionCnt);
		 
		 resultMap.put("isKingSponsor", isKingSponsor);
		
		 long  kingGDCnt=this.myAutoOrderDao.loadGDCntByKingUserId(userId);
		 resultMap.put("kingGDCnt", kingGDCnt);//统计金牌发起人的跟单总次数
		 String kjNo=this.phaseDao.loadKjNo(phaseId);//查询开奖号码
		 resultMap.put("kjNo", kjNo);
		 String  planStatus=betOrder.getOrderStatus();
		 if("4".equals(planStatus)){//已撤单
			 planStatus="3";
		 }else{
			 long alreadyBuyCopys=betOrder.getAlreadyBuyCopys();
			 long allCopys=betOrder.getDivideCopys();
			 if(alreadyBuyCopys==allCopys){
				 planStatus="1";
			 }else if(alreadyBuyCopys<allCopys){
				 planStatus="2";
			 }
		 }
		 resultMap.put("planStatus", planStatus);
		 BigDecimal bonus=betOrder.getBonus();
		 if(bonus!=null){
		   if(bonus.longValue()>0){
		     BigDecimal tcRate=bonus.multiply(new BigDecimal(betOrder.getTcRate()));
		     resultMap.put("tcRate", tcRate);
		 
		     bonus=betOrder.getBonus();
		     BigDecimal bonusPerCopy=bonus.divide(new BigDecimal(betOrder.getDivideCopys()));
		     resultMap.put("bonusPerCopy", bonusPerCopy);
		   }
		 }
	  return resultMap;
	}
	 public BetOrder findProperties(Long betId)throws DaoException{
		 return this.betRecordDao.findProperties(betId);
	 }
	/**判断用户是否被当前用户关注 2010-04-12 15:18**/
	public String isAttention(Long userId,Long targetUserId)throws DaoException{
		return this.myAttentionDao.isAttention(userId, targetUserId);
	}
	/**判断当前用户是否为跟单用户  2010-04-02 11:16**/
	public boolean  isGD(Long sponsorUserId,Long currentUserId)throws DaoException{
		return myAutoOrderDao.isGenDanUser(sponsorUserId, currentUserId);
	}
	/**我的认购明细 参与和买时  2010-04-08 17:43**/
	public List<CanyuVO> findMyBuyList(Long userId,Long betId)throws DaoException{
    	return this.betRecordDao.findMyBuyList(userId,betId);
    }
	/**
	 * 根据期次ID查询对阵列表
	 * 参数:
	 *  phaseId:期次ID
	 */
	public List<AgainstVo> findAgainstList(Map params)throws DaoException{
		return this.betRecordDao.findAgainstList(params);
	}
	/**
	 * 根据发起订单ID查询已经参加合买的用户记录数
	 */
	public long findParticipateSize(Map params)throws DaoException{
		return this.betRecordDao.findParticipateSize(params);
	}
	/**
	 * 根据发起订单ID查询已经参加合买的用户列表
	 */
	public List<CanyuVO> findParticipateList(Map params)throws DaoException{
		return this.betRecordDao.findParticipateList(params);
	}
	/**获取胜负彩14场单式发起订单
	 * betOrderId:投注订单ID
	 * 
	 * stars:★☆☆☆
	 * allBonus: 总奖金
	 **/
	public Map loadSingle14OrderInfo(Long betOrderId)throws DaoException{
		 BetOrder  betOrder=this.betDao.loadBetOrder(new Long(betOrderId));
		 Long phaseId=betOrder.getPhaseId();
		 Map resultMap=new HashMap();
		 Long userId=betOrder.getBetUserid();
		 User user=this.kingSponsorDao.loadUser(userId);
		 long betMilitary=user.getBetMilitary();
		 StringBuilder stars=new StringBuilder();
		 for(long i=0;i<betMilitary;i++){//战绩
				 stars.append("★");
		 }
		 stars.append("<span>");
		 for(long j=0;j<10-betMilitary;j++){
				 stars.append("☆");
		 }
		 stars.append("</span>");
		 resultMap.put("stars", stars.toString());
		 resultMap.put("betOrder", betOrder);
		 Map bonusMap=this.bonusDao.findMyBonus(userId);
		 long zjCnt=(Long)bonusMap.get("zjCnt");//中奖次数
		 resultMap.put("zjCnt", zjCnt);
		 
		 long isKingSponsor=this.kingSponsorDao.isKingSponsor(userId);
		 if(isKingSponsor>0){//大于1表示是金牌发起人
			 resultMap.put("isKingSponsor", isKingSponsor);
		 }
		
		 long myattentionCnt=myAttentionDao.findMyAttentionSize(userId);//统计关注我的人数量
		 resultMap.put("myattentionCnt", myattentionCnt);
		 
		 resultMap.put("isKingSponsor", isKingSponsor);
		
		 long  kingGDCnt=this.myAutoOrderDao.loadGDCntByKingUserId(userId);
		 resultMap.put("kingGDCnt", kingGDCnt);//统计金牌发起人的跟单总次数
		 String kjNo=this.phaseDao.loadKjNo(phaseId);//查询开奖号码
		 resultMap.put("kjNo", kjNo);
		 String  planStatus=betOrder.getOrderStatus();
		 if("4".equals(planStatus)){//已撤单
			 planStatus="3";
		 }else{
			 long alreadyBuyCopys=betOrder.getAlreadyBuyCopys();
			 long allCopys=betOrder.getDivideCopys();
			 if(alreadyBuyCopys==allCopys){
				 planStatus="1";
			 }else if(alreadyBuyCopys<allCopys){
				 planStatus="2";
			 }
		 }
		 resultMap.put("planStatus", planStatus);
		 BigDecimal bonus=betOrder.getBonus();
		 if(bonus!=null){
		   if(bonus.longValue()>0){
		     BigDecimal tcRate=bonus.multiply(new BigDecimal(betOrder.getTcRate()));
		     resultMap.put("tcRate", tcRate);
		 
		     bonus=betOrder.getBonus();
		     BigDecimal bonusPerCopy=bonus.divide(new BigDecimal(betOrder.getDivideCopys()));
		     resultMap.put("bonusPerCopy", bonusPerCopy);
		   }
		 }
	  return resultMap;
	}
	
	/**加载单场合买信息**/
	public Map loadDanChangOrderInfo(Long betOrderId)throws DaoException{
		 BetOrder  betOrder=this.betDao.loadBetOrder(new Long(betOrderId));
		 Long phaseId=betOrder.getPhaseId();
		 Map map=new HashMap();
		 Long userId=betOrder.getBetUserid();
		 User user=this.kingSponsorDao.loadUser(userId);
		 long betMilitary=user.getBetMilitary();
		 StringBuilder stars=new StringBuilder();
		 for(long i=0;i<betMilitary;i++){//战绩
				 stars.append("★");
		 }
		 stars.append("<span>");
		 for(long j=0;j<10-betMilitary;j++){
				 stars.append("☆");
		 }
		 stars.append("</span>");
		 map.put("stars", stars.toString());
		 map.put("betOrder", betOrder);
		 Map bonusMap=this.bonusDao.findMyBonus(userId);
		 long zjCnt=(Long)bonusMap.get("zjCnt");//中奖次数
		 map.put("zjCnt", zjCnt);
		 String allBonus=(String)map.get("allBonus");//总奖金
		 if(StringUtils.isNotEmpty(allBonus)){
			if(allBonus.indexOf(".00")==-1){
				allBonus+=".00";
			}
		    map.put("allBonus", allBonus);
		 }else{
			 map.put("allBonus", "0"); 
		 }
		 String betCategory=betOrder.getBetCategory();
		 if(betCategory.startsWith("6")){//单场
			 List<BetOrderChoice>  orderChoiceList=this.betDao.loadMyBetOrderChoiceList(betOrderId,phaseId);
			 map.put("orderChoiceList", orderChoiceList);
		 }
		return map;
	}
	 @Autowired
	   private BetDao betDao;
	  @Autowired
	  private KingSponsorDao kingSponsorDao;
	  @Autowired
	  private BonusDao bonusDao;
	  @Autowired
	  private BetRecordDao betRecordDao;
	  @Autowired
	  private MyAutoOrderDao  myAutoOrderDao;
	  @Autowired
	  private MyAttentionDao  myAttentionDao;
	  @Autowired
	  private PhaseDao phaseDao;
	  
}
