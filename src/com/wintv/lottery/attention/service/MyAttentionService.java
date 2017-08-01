package com.wintv.lottery.attention.service;

import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.MyAttention;
import com.wintv.lottery.attention.vo.AttentionPlanVO;
import com.wintv.lottery.attention.vo.AttentionUserVO;
import com.wintv.lottery.attention.vo.AttentionXinshuiVO;

public interface MyAttentionService {
	/**产生我的关注  2010-04-09 11:14**/
	public Long createAttention(MyAttention po)throws DaoException;
	/**关注人气榜 统计总记录数 2010-04-05 14:00**/
	public int findMyAttentionTop50Size(Map params)throws DaoException;
	/**关注人气榜 查询列表 2010-04-05 14:00**/
	public List<AttentionUserVO> findMyAttentionTop50List(Map params)throws DaoException;
	/**我关注的人  查询列表 2010-04-05 14:00**/
	public int findMyAttentionUserSize(Map params)throws DaoException;
	/**我关注的人  查询列表 2010-04-05 14:00**/
	public List<AttentionUserVO> findMyAttentionUserList(Map params)throws DaoException;
	/**我关注的方案  统计记录总数  2010-04-05 14:00**/
	public int findMyAttentionPlanSize(Map params)throws DaoException;
	/**我关注的方案  查询列表 2010-04-05 14:00**/
	public List<AttentionPlanVO> findMyAttentionPlanList(Map params)throws DaoException;
   public  boolean updateUserAttentionCnt(Long targetUserId)throws DaoException;
   
   /**关注我的人总记录数统计 2010-04-05 14:00**/
	public int findMyAttentionedUserSize(Map params)throws DaoException;
	/**关注我的人  查询列表 2010-04-05 14:00**/
	public List<AttentionUserVO> findMyAttentionedUserList(Map params)throws DaoException;
	/**取消关注  2010-04-05 15:53**/
	public boolean cancelAttention(String  attentionIdList)throws DaoException;
	
	/**我关注的人-当前方案列表 2010-04-09 10:50**/
	public List<AttentionPlanVO> findCurPlanList(Map params)throws DaoException;
	/**我关注的人-当前心水列表 2010-04-09 10:50**/
	public List<AttentionXinshuiVO> findCurXinshuiList(Map params)throws DaoException;
}
