package com.wintv.lottery.bet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.bet.dao.BetHallDao;
import com.wintv.lottery.bet.service.HallService;
import com.wintv.lottery.personal.vo.BetOrderVO;

/**
 * 购彩大厅首页
 *
 */
@Service("hallService")
public class HallServiceImpl  implements HallService{
	/**足球单场 合买推荐方案(含置顶) 针对杨总的界面  2010-04-26 14:00**/
	public List<BetOrderVO> loadDanchangAll()throws DaoException{
		return this.betHallDao.loadDanchangAll();
	}
	 /**
	    * 胜负彩14场 购彩大厅  2010-04-22 17:08
	    */
	   public List<BetOrderVO> loadWinLose14List()throws DaoException{
		   return this.betHallDao.loadWinLose14();
	   }
	   /**
	    * 任选9场 购彩大厅  2010-04-22 17:08
	    */
	   public List<BetOrderVO> loadloadRen9List()throws DaoException{
		   return this.betHallDao.loadRen9();
	   }
	   /**足球单场 合买推荐方案(含置顶)  2010-04-24 14:25
		 * 参数：
		 *     betCategory   61:单场半全场  62:单场比分 63:单场让球胜平负  64:单场上下单双 65:单场总进球
		 */
	    public List<BetOrderVO> loadDanchangList(String betCategory)throws DaoException{
	    	return this.betHallDao.loadDanchang(betCategory);
	    }
	   @Autowired
	   private BetHallDao betHallDao;
}
