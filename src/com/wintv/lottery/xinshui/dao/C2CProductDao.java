package com.wintv.lottery.xinshui.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.C2CProduct;
import com.wintv.lottery.xinshui.vo.XinshuiOrderVO;

public interface C2CProductDao extends BaseDao<C2CProduct, Long> {
	/**
	 * 
	*把心水赛事里面的对阵取消，不让它作为心水的对阵
	 */
	public boolean updateXinshuiAgainst(Long againstId)throws DaoException;
	public BigDecimal[]  findPayInfo(Long orderId,Long userId)throws DaoException;
	/**【民间高手心水管理—发布管理】
	    * 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT,
	    * 参数：
	    *  raceId:   赛事名称 T_AGAINST.RACE_ID
	    *  startRow：起始记录数
	    *  endRow：  结束记录数
	    * 返回:
	    *  List<C2CProduct>
	    */
	   public List<C2CProduct> findC2CProductList(Map params, int startRow, int pageSize)throws DaoException;
	   public int              findC2CProductSize(Map params)throws DaoException;
	   /**【民间高手心水管理—销售订单查看】
	    * 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT,
	    * 参数：
	    *  raceId:   赛事名称 T_AGAINST.RACE_ID
	    *  startRow：起始记录数
	    *  endRow：  结束记录数
	    * 返回:
	    *  List<C2CProduct>
	    */
	   public List<XinshuiOrderVO> findSoldOrderList(Map params, int startRow, int pageSize)throws DaoException;
	   /**【民间高手心水管理—销售订单记录数统计】
	    * 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT,
	    * 参数：
	    *  raceId:   赛事名称 T_AGAINST.RACE_ID
	    *  startRow：起始记录数
	    *  endRow：  结束记录数
	    * 返回:
	    *  List<C2CProduct>
	    */
	   public int findSoldOrderSize(Map params)throws DaoException;
	   /**
		 * 【客服】选择从对阵表T_AGAINST里面 挑选若干场对阵，生成心水对阵列表
		 * @param scheduleIds
		 * @throws DaoException
		 */
	   /**
		 * 【客服】选择从对阵表T_AGAINST里面 挑选若干场对阵，生成心水对阵列表
		 * @param scheduleIds
		 * @throws DaoException
		 */
	    public String genXinshuiAgainst(String againstIds)throws DaoException;
	    public List findXinshuiList(Map params)throws DaoException;
	    public int findXinshuiSize(Map params)throws DaoException;
	    /**
	     * 判断用户是否已经购买了本心水
	     *参数:
	     *  buyUserId:当前用户ID
	     *  c2cId:心水ID
	     * 返回值：
	     *  true:表示现在可以购买该心水
	     *  false:禁止购买
	     */
	    public boolean  isAlreadyBuy(Map params)throws DaoException;
}
