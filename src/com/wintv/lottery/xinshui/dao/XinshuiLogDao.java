package com.wintv.lottery.xinshui.dao;

import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.C2CProduct;
import com.wintv.framework.pojo.XinshuiOrder;
import com.wintv.lottery.xinshui.vo.Top10Vo;
import com.wintv.lottery.xinshui.vo.XinshuiAgainstVO;
import com.wintv.lottery.xinshui.vo.XinshuiOrderDetailVO;
import com.wintv.lottery.xinshui.vo.XinshuiOrderVO;

public interface XinshuiLogDao extends BaseDao<XinshuiOrder, Long> {
	 /**
	 * 3.1.1心水发布页面,心水发布——赛事及心水类型选择界面
	 * 民间高手根据条件查询可以发布心水的对阵信息,对应表:T_XINSHUI_SCHEDULE,T_AGAINST
	 * raceId:赛事类型 RACE_ID
	 * sortType:排序方式
	 */
    public List findAgainstList(Map params) throws DaoException;
    /**【普通用户】心水管理—订单管理
	    * 用户根据条件进行心水查询,并分页  对应表:T_XINSHUI_ORDER,T_C2C_PRODUCT
	    * 参数:
	    * raceId:赛事名称 T_AGAINST.RACE_ID
	    * payStatus:支付状态
	    * orderNo:订单编号 对应表T_XINSHUI_ORDER.ORDER_NO
	    * xinshuiNo:心水编号T_C2C_PRODUCT.XINSHUI_NO
	    * txUsername:发布人对应表   T_C2C_PRODUCT.TX_USERNAME
	    * hostTeam,guestTeam:主客队名
	    *返回：
	    *心水购买信息
	    */
	   public List findXinshuiOrderList(Map params,int startRow,int pageSize)throws DaoException;
	/**
    * 根据条件进行心水购买与销售日志的查询,并分页  对应表:T_XINSHUI_ORDER
    * fromTime:起始时间
    * toTime:结束时间
    * @param params
    * @return
    */
	public List findBuyXinshuiLogList(Map params,int startRow,int pageSize)throws DaoException;
	
	/**
	* 根据条件进行心水购买与销售日志的查询记录总条数 对应表:T_XINSHUI_ORDER
	* fromTime:起始时间
	* toTime:结束时间
	* @param params
	* @return
	*/
	public int findXinshuiOrderSize(Map params)throws DaoException;
	
	/**
	 * 民间高手胜率排行
	 * 参数:
	 *   flg:  '1':月   '2':周
	 * @return
	 * @throws DaoException
	 */
   public List<Top10Vo>  findXinshuiWinTop10List(Map params)throws DaoException;
  
	 /**
	    * 民间高手根据主键查询心水对阵信息  对应表:T_XINSHUI_AGAINST,T_AGAINST
	    * id:T_XINSHUI_AGAINST主键
	    */
   public XinshuiAgainstVO loadXinshuiAgainst(Long id)throws DaoException;
   /**
    * 【普通用户】加载心水订购页面所显示的信息
    * 参数:
    * id:T_C2C_PRODUCT.ID
    */
   public XinshuiOrderVO loadXinshuiDetail(Long id)throws DaoException;
   /**
    * 心水二次支付详情
    * @param xinshuiOrderId
    * @return
    * @throws DaoException
    */
   public XinshuiOrderDetailVO loadXinshuiOrderDetail(Long xinshuiOrderId)throws DaoException;
	 
}
