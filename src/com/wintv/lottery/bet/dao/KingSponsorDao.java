package com.wintv.lottery.bet.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.framework.pojo.KingSponsorCategory;
import com.wintv.framework.pojo.User;

public interface KingSponsorDao extends BaseDao<KingSponsor,Long>{
	public boolean  updateKingSponsor(long kingId)throws DaoException;
	/**其他界面要用到自动跟单的详细列表通过3个参数读取一条自动跟单的详细信息  2010-04-29 09:46**/
	public KingSponsor  loadAutoOrder(Long userId,String betCategory,String flg)throws DaoException;
	/**判断用户是否已经申请某彩种的玩法   2010-04-23 11:40
	 * 返回值:
	 *   false:已经申请,本次不能再申请
	 *   true:本次可以申请
	 **/
	public boolean  isAlreadyApply(Long userId,String betCategory,String type)throws DaoException;
	/**查询用户已经申请的彩种  以及玩法 2010-04-23 11:28**/
	public List<KingSponsor>  findAlreadyApplyCategoryList(Long userId)throws DaoException;
	/**根据用户ID  查询主键  2010-04-22 17:39**/
	public Long  loadKingSponsorId(Long userId)throws DaoException;
	public long  findKingSize(Map params)throws DaoException;
	/**
	 * 自动跟单-我要跟单列表  2010-04-19 15:20
	 *
	 */
	public List<KingSponsor>  findKingList(Map params)throws DaoException;
	/**判断是否金牌发起人  2010-04-12 11:25**/
	public  long isKingSponsor(Long userId)throws DaoException;
	public User  loadUser(Long userId)throws DaoException;
	public KingSponsor  loadKingSponsorProperties(Long kingId)throws DaoException;

}
