package com.wintv.lottery.attention.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.MyAttention;
import com.wintv.lottery.attention.dao.MyAttentionDao;
import com.wintv.lottery.attention.service.MyAttentionService;
import com.wintv.lottery.attention.vo.AttentionPlanVO;
import com.wintv.lottery.attention.vo.AttentionUserVO;
import com.wintv.lottery.attention.vo.AttentionXinshuiVO;
@Service("myAttentionService")
@SuppressWarnings("unchecked")
public class MyAttentionServiceImpl implements MyAttentionService{
	/**我关注的人-当前心水列表 2010-04-09 10:50**/
	public List<AttentionXinshuiVO> findCurXinshuiList(Map params)throws DaoException{
		return this.myAttentionDao.findCurXinshuiList(params);
	}
	/**产生我的关注  2010-04-09 11:14
	 *  
	 **/
	@Transactional(rollbackFor = DaoException.class)
	public Long createAttention(MyAttention po)throws DaoException{
		String isAttention=myAttentionDao.isAttention(po.getUserId(),po.getTargetUserId());
		if("1".equals(isAttention)){
			return -2L;
		}
		    String targetUsername=this.myAttentionDao.getUsernameByUserId(po.getTargetUserId());
		    po.setTargetUsername(targetUsername);
		 return this.myAttentionDao.saveObject(po);
		
	}
	/**我关注的人-当前方案列表 2010-04-09 10:50**/
	public List<AttentionPlanVO> findCurPlanList(Map params)throws DaoException{
		return this.myAttentionDao.findCurPlanList(params);
	}
	/**我关注的人  查询列表 2010-04-05 14:00**/
	public int findMyAttentionUserSize(Map params)throws DaoException{
		return myAttentionDao.findMyAttentionUserSize(params);
	}
	/**我关注的人  查询列表 2010-04-05 14:00**/
	public List<AttentionUserVO> findMyAttentionUserList(Map params)throws DaoException{
		return myAttentionDao.findMyAttentionUserList(params);
	}
	/**我关注的方案  统计记录总数  2010-04-05 14:00**/
	public int findMyAttentionPlanSize(Map params)throws DaoException{
		return myAttentionDao.findMyAttentionPlanSize(params);
	}
	/**我关注的方案  查询列表 2010-04-05 14:00**/
	public List<AttentionPlanVO> findMyAttentionPlanList(Map params)throws DaoException{
		return myAttentionDao.findMyAttentionPlanList(params);
	}
   public  boolean updateUserAttentionCnt(Long targetUserId)throws DaoException{
	   return myAttentionDao.updateUserAttentionCnt(targetUserId);
   }
  
   /**关注人气榜 查询列表 2010-04-05 14:00**/
  public List<AttentionUserVO> findMyAttentionTop50List(Map params)
		throws DaoException {
	return this.myAttentionDao.findMyAttentionTop50List(params);
  }
  /**关注人气榜 统计总记录数 2010-04-05 14:00**/
  public int findMyAttentionTop50Size(Map params) throws DaoException {
	return this.myAttentionDao.findMyAttentionTop50Size(params);
  }
  /**关注我的人  查询列表 2010-04-05 14:00**/
  public List<AttentionUserVO> findMyAttentionedUserList(Map params)
		throws DaoException {
	return this.myAttentionDao.findMyAttentionedUserList(params);
  }
  /**关注我的人总记录数统计 2010-04-05 14:00**/
  public int findMyAttentionedUserSize(Map params) throws DaoException {
	return this.myAttentionDao.findMyAttentionedUserSize(params);
  }
 
  /**取消关注  2010-04-05 15:53**/
  public boolean cancelAttention(String  attentionIdList)throws DaoException{
	  if(StringUtils.isNotEmpty(attentionIdList)){
		  if(attentionIdList.startsWith(",")){
			  attentionIdList=attentionIdList.substring(1,attentionIdList.length());
		  }
		  attentionIdList=attentionIdList.trim();
		  if(attentionIdList.endsWith(",")){
			  attentionIdList=attentionIdList.substring(0,attentionIdList.length()-1);
		  }
		  return this.myAttentionDao.cancelAttention(attentionIdList);
	  }
	return false;
  }
  @Resource(name="myAttentionDao")
  private MyAttentionDao myAttentionDao;

}
