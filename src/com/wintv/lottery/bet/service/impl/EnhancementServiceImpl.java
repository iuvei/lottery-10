package com.wintv.lottery.bet.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.BetOrder;
import com.wintv.framework.pojo.BetOrderChoice;
import com.wintv.framework.pojo.User;
import com.wintv.lottery.admin.bet.vo.PhaseVO;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.bet.dao.BetDao;
import com.wintv.lottery.bet.dao.EnhancementDao;
import com.wintv.lottery.bet.dao.KingSponsorDao;
import com.wintv.lottery.bet.service.EnhancementService;
import com.wintv.lottery.bet.vo.BetOrderVO;
/**
 * 2010-04-16 16:17
 *
 */
@Service("enhancementService")
public class EnhancementServiceImpl implements EnhancementService{
	private static final Log log=LogFactory.getLog(EnhancementServiceImpl.class);
	/**
	 * --网站购彩大厅单场期次列表 2010-05-06 13:49
	 *
	 */
	public PhaseVO  findCurSinglePhase()throws DaoException{
		return this.enhancementDao.findCurSinglePhase();
	}
	/**
	 * --网站购彩大厅('6':胜负彩14场  任9场   '5':6场半全场   '3':4场进球)期次列表 2010-05-06 13:49
	 * @param category
	 * @return
	 * @throws DaoException
	 */
	public Map  findInAdvancePhaseMap(String category)throws DaoException{
		return this.enhancementDao.findInAdvancePhaseMap(category);
	}
	/**
	 * 更改投注内容  2010-04-22 10:05
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	@Transactional(rollbackFor = DaoException.class)
	public boolean updateBetOrder(Map params)throws DaoException{
		try{
		String betCategory=(String)params.get("betCategory");
		Long betId=(Long)params.get("betId");
		String plan=(String)params.get("plan");
		
		if("2".equals(betCategory)){
			boolean  isDelete=this.enhancementDao.deleteRen9ChoiceList(betId);
			if(isDelete){
				 if(StringUtils.isNotEmpty(plan)){
					   String[]  betContents=plan.split(";");
					   if(betContents!=null&&betContents.length>0){
						     BetOrderChoice  choice=null;
						     List<BetOrderChoice> betContentList=new ArrayList<BetOrderChoice>();
						    
						     for(int i=0;i<betContents.length;i++){
					    		 choice=new BetOrderChoice();
					    		 String betContentValue=betContents[i];
					    		 
					    		 String[] betContentArr=betContentValue.split(",");
					    		 String  againstID=betContentArr[0];
						         Long againstId=Long.parseLong(againstID);//对阵ID
						         choice.setAgainstId(againstId);
						         String betPlan=betContentArr[1];;//用户投注结果
						         choice.setBetPlan(betPlan);
							     String changci=betContentArr[2];//场次
							     choice.setChangci(new Long(changci));
							     choice.setBetOrderId(betId);
							   betContentList.add(choice);
						     }
						  
						     this.enhancementDao.storeAll(betContentList);
						     return true;
					   }
				 }
			}
			
		}else{
		return this.enhancementDao.updateBetOrder(params);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 根据期次ID查询对阵列表
	 * 参数:
	 *  phaseId:期次ID
	 */
	public List<AgainstVo> findAgainstList(Map params)throws DaoException{
		return this.enhancementDao.findAgainstList(params);
	}
	/**获取发起订单 以便用于修改投注内容  2010-04-21 10:05
	 * 参数:
	 *   Long betOrderId:投注订单ID
	 *   Long userId:发起人用户ID
	 * 返回值:
	 * long allSubscribeCopys:总共购买的份数
	 * BetOrder betOrder：方案订单
	 **/
	public Map loadOrderInfo(Long  betOrderId)throws DaoException{
		
		
		BetOrder betOrder= this.betDao.loadBetOrder(new Long(betOrderId));
		if("2".equals(betOrder.getBetCategory())){
			String plan=this.betDao.load9Choice(betOrder.getBetId());
			betOrder.setPlan(plan);
		}
		long allSubscribeCopys=this.enhancementDao.getAllSubscribeCopys(betOrder.getBetUserid(), betOrderId);
		Map resultMap=new HashMap();
		resultMap.put("allSubscribeCopys", allSubscribeCopys);
		resultMap.put("betOrder", betOrder);
	   return resultMap;
	}
	/**
	 * 稍后保存文件到数据库 2010-04-20 11;16
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public boolean uploadTxtPath(Map map)throws DaoException{
		return this.enhancementDao.uploadTxtPath(map);
	}
	public BetOrderVO loadBetOrder(Long betId)throws DaoException{
		return this.enhancementDao.loadBetOrder(betId);
	}
	/**
	 * -撤单   2010-04-16 17:57
	 * 返回值:   1.撤单成功  2.进度超度50%不能撤单  4 非法操作    -1报错
	 *
	 */
	 public int cancelSpOrder(long userId,long betId)throws DaoException{
		 return this.enhancementDao.cancelSpOrder(userId, betId);
	 }
	 /**
	   * 保底   2010-04-16 16:11
	   * 返回 4为非法操作   返回1 正常保底  2 保底份数太少 3保底份数太多  
	   *
	   */
	  public int floorBetOrder(long userId,long betId,long flooCopys)throws DaoException{
		  return this.enhancementDao.floorBetOrder(userId, betId, flooCopys);
	  }
	  @Resource
	  private EnhancementDao enhancementDao;
	  @Autowired
	   private BetDao betDao;
	  @Autowired
	  private KingSponsorDao kingSponsorDao;
}
