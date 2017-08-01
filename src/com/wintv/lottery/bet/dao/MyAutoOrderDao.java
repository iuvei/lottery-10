package com.wintv.lottery.bet.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.KingSponsor;
import com.wintv.framework.pojo.MyAutoOrder;
import com.wintv.lottery.bet.vo.MyAutoOrderVO;

@SuppressWarnings("unchecked")
public interface MyAutoOrderDao extends BaseDao {
	/**普通用户定制自动跟单  2010-05-07 09:35**/
	public int  saveMyAutoOrder(MyAutoOrder po)throws DaoException;
	/**是否允许跟单**/
	  public int  isAllowGD(Long userId)throws DaoException;
	/**自动跟单-自动跟单人 2010-04-12  15:09**/
	   public List findAutoOrderListByCategory(Map params)throws DaoException;
	/**我的跟单管理统计总记录数  分页使用 2010-04-12 13:31
	 * 参数:
	 * commonUserId:普通用户ID
	 **/
	public long findMyAutoOrderSize(Long commonUserId)throws DaoException;
	/**我的跟单管理分页列表  2010-04-12 13:31
	 * 参数:
	 * commonUserId:普通用户ID
	 **/
	public List<MyAutoOrderVO> findMyAutoOrderList(Long commonUserId)throws DaoException;
	/**返回某一金牌发起人的被跟单总次数
	 * 参数:
	 * kingUserId:金牌发起人用户ID
	 */
	public long  loadGDCntByKingUserId(Long kingUserId)throws DaoException;
	/**判断当前用户是否为跟单用户  2010-04-02 11:16**/
	public boolean  isGenDanUser(Long sponsorUserId,Long currentUserId)throws DaoException;
	/**添加自动跟单  2010-03-22 16:34**/
	public boolean  executeGz(Map params)throws DaoException;
	/**统计某一超级发起人的跟单总数**/
	public long  loadGDCnt(Long userId)throws DaoException;
	
	//------------------自动跟单管理------------------------------------------
	/**金牌发起人管理  2010-04-06 08:55**/
	public List  findKingCategory(Long userId)throws DaoException;
	/**金牌发起人资格审核管理  2010-04-06 09:25**/
	public List<KingSponsor>  findKingCategoryAudit(Long userId)throws DaoException;

}
