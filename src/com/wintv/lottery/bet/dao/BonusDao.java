package com.wintv.lottery.bet.dao;

import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Bonus;
/**中奖**/
public interface BonusDao extends BaseDao<Bonus,Long>{
	public Map  findMyBonus(Long userId)throws DaoException;

}
