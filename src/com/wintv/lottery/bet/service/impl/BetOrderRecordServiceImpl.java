package com.wintv.lottery.bet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.BetOrderChoice;
import com.wintv.framework.pojo.User;
 import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.dao.BetDao;
import com.wintv.lottery.bet.dao.BetRecordDao;
import com.wintv.lottery.bet.dao.BonusDao;
import com.wintv.lottery.bet.dao.KingSponsorDao;
import com.wintv.lottery.bet.service.BetOrderRecordService;
import com.wintv.lottery.bet.vo.CanyuVO;

 

@Service("betOrderRecordService")
public class BetOrderRecordServiceImpl implements BetOrderRecordService{
	//=====================购买用户分页显示  2010-04-01 16:53=================================
	public long findParticipateSize(Map params)throws DaoException{
		return this.betRecordDao.findParticipateSize(params);
	}
	public List<CanyuVO> findParticipateList(Map params)throws DaoException{
		return this.betRecordDao.findParticipateList(params);
	}
	public List<AgainstVo> findAgainstList(Map params)throws DaoException{//
		return this.betRecordDao.findAgainstList(params);
	}
	public String getTxtFile(Long id)throws DaoException{
		return this.betRecordDao.getTxtFile(id);
	}
	public Map findParticipates(Map params)throws DaoException{
		return null;
	}
	public Map loadMyBetRecord(Long betOrderId)throws DaoException{
		 BetOrder  betOrder=this.betDao.loadBetOrder(new Long(betOrderId));
		 Long phaseId=betOrder.getPhaseId();
		 String sponsorType=betOrder.getSponsorType();//发起类型 '1':发起人 '2':参与合买  '0':代购
		 Map map=new HashMap();
		 if("1".equals(sponsorType)){//当前订单即为发起人
			 Long userId=betOrder.getBetUserid();
			 User user=this.kingSponsorDao.loadUser(userId);
			 String betCategory=betOrder.getBetCategory();
			 
			 if(betCategory.startsWith("6")){//单场
				 List<BetOrderChoice>  orderChoiceList=this.betDao.loadMyBetOrderChoiceList(betOrderId,phaseId);
				 map.put("orderChoiceList", orderChoiceList);
			 }
			 double  baodi=betOrder.getFloorCopys().longValue()*betOrder.getSingleMoney().doubleValue();
			 map.put("baodi", baodi);
			 double baodiPer=baodi/betOrder.getAllMoney().longValue();
			 baodiPer*=100;
			 map.put("baodiPer", baodiPer);
			 double progress=betOrder.getAlreadyBuyCopys()*100/betOrder.getDivideCopys();
			
			 map.put("progress",progress);
			 String orderStatus=betOrder.getOrderStatus();
			 if("1".equals(orderStatus)){
				 orderStatus="未支付";
			 }else if("2".equals(orderStatus)){
				 orderStatus="待出票";
			 }else if("3".equals(orderStatus)){
				 orderStatus="已出票";
			 }else if("4".equals(orderStatus)){
				 orderStatus="已取消";
			 }else if("5".equals(orderStatus)){
				 orderStatus="已过期";
			 }else{
				 orderStatus="未支付";
			 }
			 map.put("orderStatus", orderStatus);
			 map.put("betOrder", betOrder);
			 long betMilitary=user.getBetMilitary();
			 StringBuilder stars=new StringBuilder();
			 for(long i=0;i<betMilitary;i++){
				 stars.append("★");
			 }
			 stars.append("<span>");
			 for(long j=0;j<10-betMilitary;j++){
				 stars.append("☆");
			 }
			 stars.append("</span>");
			 map.put("stars", stars.toString());
		 }else if("0".equals(sponsorType)){//代购
			 map.put("betOrder", betOrder);
			 Long userId=betOrder.getBetUserid();
			 User user=this.kingSponsorDao.loadUser(userId);
			 long betMilitary=user.getBetMilitary();
			 StringBuilder stars=new StringBuilder();
			 for(long i=0;i<betMilitary;i++){
				 stars.append("★");
			 }
			 stars.append("<span>");
			 for(long j=0;j<10-betMilitary;j++){
				 stars.append("☆");
			 }
			 stars.append("</span>");
			 map.put("stars", stars.toString());
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
			 String orderStatus=betOrder.getOrderStatus();
			 if("1".equals(orderStatus)){
				 orderStatus="未支付";
			 }else if("2".equals(orderStatus)){
				 orderStatus="待出票";
			 }else if("3".equals(orderStatus)){
				 orderStatus="已出票";
			 }else if("4".equals(orderStatus)){
				 orderStatus="已取消";
			 }else if("5".equals(orderStatus)){
				 orderStatus="已过期";
			 }else{
				 orderStatus="未支付";
			 }
			 map.put("orderStatus", orderStatus);
		 }
		 return map;
		
	}
	/**取消订单  2010-03-27 16:01**/
	public boolean canvelMyOrder(Long  id)throws DaoException{
		return this.betDao.updateBySql("update T_BET_ORDER t set t.ORDER_STATUS='4'  where t.BET_ID="+id);
	}
	  @Autowired
	   private BetDao betDao;
	  @Autowired
	  private KingSponsorDao kingSponsorDao;
	  @Autowired
	  private BonusDao bonusDao;
	  @Autowired
	  private BetRecordDao betRecordDao;

}
