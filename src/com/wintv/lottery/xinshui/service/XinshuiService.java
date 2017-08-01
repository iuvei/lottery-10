package com.wintv.lottery.xinshui.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.exception.DaoException;

import com.wintv.framework.pojo.C2CProduct;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.XinshuiOrder;
import com.wintv.framework.pojo.XinshuiAgainst;
import com.wintv.lottery.xinshui.vo.Top10Vo;
import com.wintv.lottery.xinshui.vo.XinshuiAgainstVO;
import com.wintv.lottery.xinshui.vo.XinshuiDetailVO;
import com.wintv.lottery.xinshui.vo.XinshuiOrderDetailVO;
import com.wintv.lottery.xinshui.vo.XinshuiOrderVO;

/**
 * 心水模块接口
 * 
 * @since 1.0
 * 
 */
public interface XinshuiService {
	/**
	 * 判断当前用户是否有权限查看该心水 2010-05-06 09:31
	 * 返回结果:
	 *  1:可以查看
	 *  0:没有权限查看
	 */
    public int isAllowLook(Long c2cId,Long userId)throws DaoException;
	/**判断指定用户是否有权限发布心水  2010-04-13 15:37**/
	public boolean isAllowPubXinshui(Long userId)throws DaoException;
	/**
	 * 
	*把心水赛事里面的对阵取消，不让它作为心水的对阵 2010-03-01 17:58
	 */
	public boolean updateXinshuiAgainst(Long againstId)throws DaoException;
	public BigDecimal[]  findPayInfo(Long orderId,Long userId)throws DaoException;
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
	 * 3.1.1心水发布页面,心水发布——赛事及心水类型选择界面
	 * 【民间高手】根据条件查询可以发布心水的对阵信息,对应表:T_XINSHUI_SCHEDULE,T_AGAINST raceType:赛事类型
	 * RACE_TYPE sortType:排序方式
	 */
	public List findAgainstList(Map params) throws DaoException;

	/**
	 * 【民间高手】根据主键查询赛事信息 对应表:T_XINSHUI_AGAINST,T_AGAINST id:T_XINSHUI_SCHEDULE主键
	 */

	public XinshuiAgainstVO loadXinshuiAgainst(Long id) throws DaoException;

	/**
	 * 【民间高手】发布心水(c2c产品)对应表:T_C2C_PRODUCT 其中一些数据需要关联到心水对阵表(T_XINSHUI_AGAINST)
	 * 保证金的范围在数据字典里面配置: 表T_DICTIONARY CODE="ENSURE_MONEY" 分类TYPE="XINSHUI"
	 * 应该在Action里校验保证金是否在系统规定的范围以内 心水价格=保证金/系数 其中系数为数据字典里的一条记录 分类TYPE="XINSHUI"
	 * CODE="MARC2C_GIN_VS_PRICE" 参数： po:c2CProduct 异常： DaoException 返回： 主键
	 */
	public String saveC2CProduct(C2CProduct po) throws DaoException;

	/**
	 * 【普通用户】心水管理—订单管理,即普通用户自己的以前曾经购买的心水订单列表 用户根据条件进行心水查询,并分页
	 * 对应表:T_XINSHUI_ORDER,T_C2C_PRODUCT 参数: raceId:赛事类型 RACE_ID payStatus:支付状态
	 * orderNo:订单编号 对应表T_XINSHUI_ORDER.ORDER_NO
	 * xinshuiNO:心水编号T_C2C_PRODUCT.XINSHUI_NO txUserId:发布人对应表
	 * T_C2C_PRODUCT.TX_USER_ID hostTeam,guestTeam:主客队名 返回： 心水购买信息列表
	 */
	public List findXinshuiOrderList(Map params, int startRow, int pageSize)
			throws DaoException;

	/**
	 * 【普通用户】心水管理—订单管理 用户根据条件进行心水查询记录总条数 对应表:T_XINSHUI_ORDER,T_C2C_PRODUCT 参数:
	 * raceType:赛事类型 RACE_TYPE payStatus:支付状态 orderNo:订单编号
	 * 对应表T_XINSHUI_ORDER.ORDER_NO xinshuiNO:心水编号T_C2C_PRODUCT.XINSHUI_NO
	 * txUserId:发布人对应表 T_C2C_PRODUCT.TX_USER_ID hostTeam,guestTeam:主客队名 返回:
	 * 符合条件的记录总数
	 */
	public int findXinshuiOrderSize(Map params) throws DaoException;

	/**
	 * 【民间高手心水管理—发布管理】 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT, 参数： raceId: 赛事名称
	 * T_AGAINST.RACE_ID startRow：起始记录数 endRow： 结束记录数 返回: List<C2CProduct>
	 */
	public List<C2CProduct> findC2CProductList(Map params, int startRow,
			int pageSize) throws DaoException;

	/**
	 * 【民间高手心水管理—发布管理】 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT, 参数： raceId: 赛事名称
	 * T_AGAINST.RACE_ID startRow：起始记录数 endRow： 结束记录数 返回: List<C2CProduct>
	 */
	public int findC2CProductSize(Map params) throws DaoException;

	/**
	 * 【普通用户】购买B2c或者c2c产品 参数 log：购买日志 返回： 主键
	 * 
	 * @throws DaoException
	 */
	public Long saveXinshuiLog(XinshuiOrder log) throws DaoException;

	/**
	 * 【普通用户】搜索心水 涉及到的表T_C2C_PRODUCT, 参数：
	 * 
	 * raceType：赛事类型(RACE_TYPE) key:关键字 发布人.. 返回: List<C2CProduct>
	 */
	public List<C2CProduct> findC2CProductList(Map params) throws DaoException;

	/**
	 * 【普通用户】民间高手胜率排行 参数: flg: '1':月 '2':周
	 * 
	 * @return
	 * @throws DaoException
	 */
	public List<Top10Vo> findXinshuiWinTop10List(Map params)
			throws DaoException;

	/**
	 * 【普通用户】加载心水订购页面所显示的信息 参数: id:T_C2C_PRODUCT.ID
	 */
	public XinshuiOrderVO loadXinshuiDetail(Long id) throws DaoException;

	// ============================================以下是后台客服部分===================================================================================
	/**
	 * 【赛事选择 2010-02-01 10:04】心水后台管理——赛事选择界面图示
	 * 【后台客服】根据条件查询选择赛事信息,为进一步生成心水对阵做准备,对应视图:v_findbackendagainstlist 参数:
	 * raceId:赛事名称 RACE_ID raceSeason:赛季 rounds:轮次
	 * status:状态,'已选择','未选择','已过期','已完成' from:起始时间 to:结束时间 teamName:球队名称
	 * 
	 * @return
	 */
	public List findBackendAgainstList(Map params) throws DaoException;

	public String genXinshuiSchedule(String scheduleIds) throws DaoException;

	public int findBackendXinshuiAgainstSize(Map params) throws DaoException;

	/**
	 * 赛事管理 本功能应该分页 2010-02-01 10:49 文档12页 参数: raceId:赛事ID startRow:起始记录数
	 * pageSize:每页最大记录数 操作： 心水:点击后即进入心水管理页面，列出对应赛事的所有心水 取消:
	 */
	public List findBackendXinshuiAgainstList(Map params) throws DaoException;

	/**
	 * 取消心水 参数: againstId:对阵表主键
	 */
	public void cancelXinshui(Long againstId, CSHandleLog csLog)
			throws DaoException;

	/**
	 * 【心水后台管理——心水管理界面】 文档第12页
	 */
	public int findBackendXinshuiPubRecordSize(Map params) throws DaoException;

	/**
	 * 【心水后台管理——心水管理界面】 文档第12页
	 *  * 参数：
	 * againstId：对阵ID
	 * pubUserId:发布人用户ID
	 */
	public List findBackendXinshuiPubRecordList(Map params, int startRow,
			int pageSize) throws DaoException;
	
	/**
	    * 发布人管理 文档第13页   2010-02-04 13:45
	    * @param params
	    * @return
	    * @throws DaoException
	    */
	public List findBackendXinshuiPubList(Map params)throws DaoException;
	/**
	    * 发布人管理 分页统计 文档第13页   2010-02-04 13:45
	    * @param params
	    * @return
	    * @throws DaoException
	    */
	   public int findBackendXinshuiPubSize(Map params)throws DaoException;
	/**
     * 关闭心水 即把表T_C2C_PRODUCT.STATUS设置为'5'
     *参数:
     *c2cId:对阵表主键
     */
    public void closeXinshui(Long c2cId,Long csUserId,String csName,String ip)throws DaoException;
    /**
     * 加载心水详情
     * @param id
     * @return
     * @throws DaoException
     */
    public XinshuiDetailVO loadXinshuiDetailVO(Long c2cId)throws DaoException;
    //==================================
    
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
	 * 【心水后台管理——心水订单管理界面】  分页统计2010-02-01 16:30
	 * 参数:
	 *  raceId:联赛名称
	 *  orderUsername:订购人用户名
     *  flg:'1':"订购人”    '2':“订单编号”  '3':“心水编号  '4':“发布人”  '5'
     *  type:'1':亚盘 '2':大小盘 '3':欧盘
	 */
	public int findBackendXinshuiOrderSize(Map params)throws DaoException;
	/**
	 * 【心水后台管理——心水编辑界面图示】2010-02-01 16:27
	 * 参数:
	 * c2cId:心水主键
	 * zhDesc:心水说明
	 */
	public boolean updateXinshui(Map params)throws DaoException;
	 /**
     * 关闭心水订单 即把表
     * 对于已支付用户
     *   T_C2C_PRODUCT.STATUS设置为'5',对于已支付用户则:
     *   1.改订单状态
     *   对应已支付的心水订单，解冻账户(T_VIRTUAL_ACCOUNT)
     *   2.产生解冻日志 3.更改订单状态,4.产生客服操作日志
     *   
     * 对于未支付用户:
     *   仅修改订单状态
     *参数:
     *c2cId:对阵表主键
     */
    @Transactional(rollbackFor =Exception.class)
    public void cancelXinshuiOrder(Long orderId,CSHandleLog  log)throws DaoException;
    /**
     * 客服取消赛事  2010-02-05 16:33
     * 参数:
     *  againstId:赛事ID
     * @param log
     * @throws DaoException
     */
    public void cancelAgainst(Long againstId,CSHandleLog  log)throws DaoException;
    /**
	  * 心水后台确认支付
	  *参数：
	  *  orderId：心水订单ID,对应表T_XINSHUI_ORDER的主键
	  *返回值:
	  * 5:账户已经被锁定
	  * 4:余额不足
	  * 1:支付成功
	  */
    public String backendXinshuiConfirmPay( Long orderId,CSHandleLog  csLog) throws DaoException;
    /**
     * 
     * @param params
     * startRow
	 *pageSize
     * @return
     * @throws DaoException
     */
    public List findXinshuiList(Map params)throws DaoException;
    public int findXinshuiSize(Map params)throws DaoException;
    /**
     * 判断用户是否已经购买了本心水  2010-02-24 15:16
     *参数:
     *  buyUserId:当前用户ID
     *  c2cId:心水ID
     * 返回值：
     *  true:表示现在可以购买该心水
     *  false:禁止购买
     */
    public boolean  isAllowBuy(Map params)throws DaoException;
    /**
     * 心水二次支付详情
     * @param xinshuiOrderId
     * @return
     * @throws DaoException
     */
    public XinshuiOrderDetailVO loadXinshuiOrderDetail(Long xinshuiOrderId)throws DaoException;
}
