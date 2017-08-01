package com.wintv.lottery.b2c.service;

import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.C2CProduct;
import com.wintv.framework.pojo.Expert;
import com.wintv.framework.pojo.Race;
import com.wintv.lottery.b2c.vo.B2COrderVO;
import com.wintv.lottery.b2c.vo.B2CProductVO;
import com.wintv.lottery.b2c.vo.ExpertOrderVO;
import com.wintv.lottery.b2c.vo.ExpertVO;

public interface B2CService {
	
	public long saveExpert(ExpertVO po)throws DaoException;
	/**
	 * 您的心水订单：2010-04-16 15:24 expertName:专家名称 orderTypeZH :订购类型 endTime: 有效截止时间
	 * price : 价格
	 */
	public Map loadDataInfo(Map params)
			throws DaoException;

	// b2c第二次支付 2010-04-16 11:12
	public int b2cPayContinue(Long b2cOrderId) throws DaoException;

	// 前台普通用户取消b2c心水订单2010-04-16 12:56
	public int cancelB2COrder(Long xinshuiOrderId) throws DaoException;

	/**
	 * b2c第一次支付 2010-04-16 11:12 返回值: 1:正常扣款 4:余额不足 但仍然产生订单 5:账户冻结
	 * 
	 */
	public int b2cPayAtFirst(Map params) throws DaoException;

	// ------------专家--专家首页----------2010-04-15 13:41--------------------
	/** 查询联赛名称 ID 2010-04-15 16:31* */
	public List<Race> findRaceList(Long expertId) throws DaoException;

	/** 专家首页 左边的数据 2010-04-15 11:01* */
	public Map loadExpertHomeData(Long expertId) throws DaoException;

	/** 专家-专家首页-右边-某内参列表数据量统计： 2010-04-15 13:39* */
	public long findXinshuiSize(Map params) throws DaoException;

	/** 专家-专家首页--右边-某内参列表分页： 2010-04-15 13:39* */
	public List<B2CProductVO> findXinshuiList(Map params) throws DaoException;

	/** B2C 是否允许查看某专家的心水 2010-04-15 14:29* */
	public boolean isAllowLook(Long userId, Long expertId) throws DaoException;

	/**
	 * 投资超值包 2010-04-13 10:11
	 * 
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public List<ExpertOrderVO> findValuePackList(Map params)
			throws DaoException;

	/**
	 * 专家推荐 2010-04-13 10:11
	 * 
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public List<ExpertOrderVO> findRecommendExpertList(Map params)
			throws DaoException;

	public boolean saveB2C(C2CProduct po) throws DaoException;

	/**
	 * 您的内参订单 2010-04-13 09:22
	 * 
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public List findMyOrderExpertList(Map params) throws DaoException;

	/** B2C订购 按时间查询、按状态查询 文档第4页 * */
	public Map findMyB2COrderList(Map params) throws DaoException;

	/**
	 * 内参订购页面1 用户点击专家“订购”后弹出如下页面，用户点击“确认”则后台产生对应订单，此时订单为“未支付”状态。 文档第5页
	 * 2010-03-12 09:34
	 * 
	 * @param orderId
	 * @return
	 * @throws DaoException
	 */
	public B2COrderVO loadB2COrder(Long orderId) throws DaoException;

	// ----------------------------------------内参后台管理——专家管理界面
	// 文档第8页---------------------------------------------------------------------------------------
	/**
	 * 内参后台管理——专家管理界面说明：“发布记录”点击后即进入内参管理页面，列出对应专家的所有内参列表 8页面
	 */
	public Map findExpertList(Map params) throws DaoException;

	/** 专家内参发布——赛事及内参类型选择界面 文档第9页 2010-03-12 17:40* */
	public List findB2CAgainstList(Map params) throws DaoException;

	/** 获得b2c下拉框的联赛名称下列列表 2010-03-14 14:18* */
	public List<Race> findB2CRaceList() throws DaoException;

	/** 内参订购管理 2010-03-15 11:18* */
	public Map findB2COrderList(Map params) throws DaoException;

	public List<Expert> findExpertList() throws DaoException;

	/** 保存内参信息时,产生内参编号* */
	public String genExpertCode() throws DaoException;

	public B2CProductVO loadB2CProduct(Long againstId, Long expertUserId);

	// --------------------------- Add By Hikin Yao
	// 2010-04-16----------------------------------------
	/**
	 * 我的内参-内参订单管理列表数据
	 * 
	 * @author Hikin Yao
	 * @param params
	 *            [buyUserId,startRow,pageSize]
	 * @return
	 * @throws DaoException
	 */
	public List<B2COrderVO> getMyB2COrderList(Map params) throws DaoException;

	/**
	 * 我的内参-统计内参订单管理纪录条数
	 * 
	 * @author Hikin Yao
	 * @param params
	 *            params [pageSize]
	 * @return 返回用户查询列表
	 */
	public Long countMyB2cOrderListSize(Map params) throws DaoException;

	/**
	 * 判断用户是否允许购买
	 * 
	 * @author Hikin Yao
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public Long isAlreadyBuyTheProduct(Map params) throws DaoException;
}
