package com.wintv.lottery.xinshui.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.XinshuiAgainst;
import com.wintv.lottery.xinshui.vo.XinshuiDetailVO;

public interface XinshuiScheduleDao extends BaseDao<XinshuiAgainst, Long>  {
	 /**
     * 关闭心水 即把表T_C2C_PRODUCT.STATUS设置为'5'
     *参数:
     *c2cId:对阵表主键
     */
   public void closeXinshui(Long c2cId,Long csUserId,String csName,String ip)throws DaoException;
	/**
	    * 【发布人管理 文档第13页】   2010-02-01 12:25
	    * @param params
	    * @return
	    * @throws DaoException
	    */
	public List findBackendXinshuiPubList(Map params)throws DaoException;
	public int findBackendXinshuiPubSize(Map params)throws DaoException;
	public List findScheduleList(Map params) throws DaoException;
	/**
	  * 【后台客服】根据条件查询选择赛事信息,为进一步生成心水对阵做准备,对应表:T_SCHEDULE
	  * raceId:赛事名称 RACE_ID
	  * raceSeason:赛季
	  * sortType:排序方式
	  * @param params
	  * @return
	  */
	 public List findBackendAgainstList(Map params)throws DaoException;
	 /**
	    * 赛事管理 2010-02-01 10:49 文档12页
	    * 参数:
	    * raceId:赛事ID
	    */
	   public List findBackendXinshuiAgainstList(Map params)throws DaoException;
	   public int findBackendXinshuiAgainstSize(Map params)throws DaoException;
	   /**
		 * 【心水后台管理——心水管理界面】数据分页
		 */
		public List findBackendXinshuiPubRecordList(Map params,int startRow,int pageSize)throws DaoException;
		/**
		 * 【心水后台管理——心水管理界面】分页记录统计
		 */
		public int findBackendXinshuiPubRecordSize(Map params)throws DaoException;
		  /**
		 * 【心水后台管理——心水订单管理界面】  分页统计2010-02-01 16:30
		 * 参数:
		 *  raceId:联赛名称
		 *  startRow:起始记录数
	    *  pageSize:每页最大记录数
	    *  orderUsername:订购人用户名
	    *  flg:'1':"订购人”    '2':“订单编号”  '3':“心水编号  '4':“发布人”  '5'
	    *  type:'1':亚盘 '2':大小盘 '3':欧盘
		 */
		public int findBackendXinshuiOrderSize(Map params)throws DaoException;
		/**
		 * 【心水后台管理——心水订单管理界面】2010-02-01 16:30
		 * 参数:
		 *  raceId:联赛名称
		 *  startRow:起始记录数
	     *  pageSize:每页最大记录数
	     *  orderUsername:订购人用户名
	     *  flg:'1':"订购人”    '2':“订单编号”  '3':“心水编号  '4':“发布人”  '5'
	     *  type:'1':亚盘 '2':大小盘 '3':欧盘
		 */
		public List findBackendXinshuiOrderList(Map params)throws DaoException;
		 /**
	     * 加载心水详情
	     * @param id
	     * @return
	     * @throws DaoException
	     */
	    public XinshuiDetailVO loadXinshuiDetailVO(Long c2cId)throws DaoException;
	    
	    /**
	     * 客服取消心水订单
	     * @param orderId
	     * @param log
	     * @throws DaoException
	     */
	    public void cancelXinshuiOrder(Long orderId,CSHandleLog  log)throws DaoException;
	    /**
	     * 客服取消赛事  2010-02-05 16:33
	     * 参数:
	     *  againstId:赛事ID
	     * @param log
	     * @throws DaoException
	     */
	    public void cancelAgainst(Long againstId,CSHandleLog  log)throws DaoException;
}
