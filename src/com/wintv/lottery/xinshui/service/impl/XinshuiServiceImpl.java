package com.wintv.lottery.xinshui.service.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.common.Constants;
import com.wintv.framework.common.DictionaryDao;
import com.wintv.framework.common.OrderNoGenDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.UserServiceException;

import com.wintv.framework.pojo.C2CProduct;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.XinshuiOrder;

import com.wintv.lottery.admin.log.dao.WithdrawCsLogAdminDao;
import com.wintv.lottery.pay.dao.PayDao;
import com.wintv.lottery.user.dao.UserDao;
import com.wintv.lottery.xinshui.dao.B2CExpertDao;

import com.wintv.lottery.xinshui.dao.C2CProductDao;
import com.wintv.lottery.xinshui.dao.XinshuiEnhancementDao;
import com.wintv.lottery.xinshui.dao.XinshuiLogDao;
import com.wintv.lottery.xinshui.dao.XinshuiScheduleDao;
import com.wintv.lottery.xinshui.service.XinshuiService;

import com.wintv.lottery.xinshui.vo.Top10Vo;
import com.wintv.lottery.xinshui.vo.XinshuiAgainstVO;
import com.wintv.lottery.xinshui.vo.XinshuiDetailVO;
import com.wintv.lottery.xinshui.vo.XinshuiOrderDetailVO;
import com.wintv.lottery.xinshui.vo.XinshuiOrderVO;

/**
 * 心水模块接口的实现
 * @author
 * @since 1.0
 *
 */


@Service("xinshuiService")
public class XinshuiServiceImpl implements XinshuiService {
	/**
	 * 判断当前用户是否有权限查看该心水 2010-05-06 09:31
	 * 返回结果:
	 *  1:可以查看
	 *  0:没有权限查看
	 */
    public int isAllowLook(Long c2cId,Long userId)throws DaoException{
    	return this.xinshuiEnhanceDao.isAllowLook(c2cId, userId);
    }
	/**判断指定用户是否有权限发布心水  2010-04-13 15:37**/
	public boolean isAllowPubXinshui(Long userId)throws DaoException{
		   int xinshuiMilitary=this.userDao.getXinshuiMilitary(userId);
		   if(xinshuiMilitary<Integer.parseInt(Constants.MIN_MINJIAN_GAOSHOU_GRADE)){//没有权限发布心水
			   return false;
		   }
		   return true;
	}
	/**
	 * 
	*把心水赛事里面的对阵取消，不让它作为心水的对阵 2010-03-01 17:58
	 */
	public boolean updateXinshuiAgainst(Long againstId)throws DaoException{
		return this.c2CProductDao.updateXinshuiAgainst(againstId);
	}
	public BigDecimal[]  findPayInfo(Long orderId,Long userId)throws DaoException{
		return this.c2CProductDao.findPayInfo(orderId,userId);
	}
	//================================以下是民间高手方法========================================================
	/**
	 * TEST
	 * 【3.1.1心水发布页面,心水发布——赛事及心水类型选择界面】
	 * 民间高手根据条件查询可以发布心水的对阵信息,对应表:T_XINSHUI_SCHEDULE,T_AGAINST
	 * raceId:赛事类型 RACE_ID
	 * sort:排序方式,参数可能是 START_TIME DESC
	 */
	public List findAgainstList(Map params) throws DaoException {
		return this.xinshuiLogDao.findAgainstList(params);
	}
	/**【民间高手】根据主键查询心水对阵信息**/
	public XinshuiAgainstVO loadXinshuiAgainst(Long id) throws DaoException {
		return this.xinshuiLogDao.loadXinshuiAgainst(id);
	}
	/**
	 * 【民间高手】发布心水(c2c产品),即把发布的信息保存到T_C2C_PRODUCT表里面
	 * 对应表:T_C2C_PRODUCT 其中一些数据需要关联到心水对阵表(T_XINSHUI_AGAINST)
	 * 保证金的范围在数据字典里面配置:
	 * 表T_DICTIONARY   CODE="ENSURE_MONEY" 分类TYPE="XINSHUI"  应该在Action里校验保证金是否在系统规定的范围以内
	 * 心水价格=保证金/系数        其中系数为数据字典里的一条记录 分类TYPE="XINSHUI"  CODE="MARC2C_GIN_VS_PRICE"
	 * 参数：
	 *   po:c2CProduct
	 * 异常：
	 *  DaoException
	 * 返回：
	 *  主键
	 */
	public String saveC2CProduct(C2CProduct po) throws DaoException {
		   int xinshuiMilitary=this.userDao.getXinshuiMilitary(po.getTxUserId());
		   if(xinshuiMilitary<Integer.parseInt(Constants.MIN_MINJIAN_GAOSHOU_GRADE)){//没有权限发布心水
			   return "-2";
		   }
		   String value=dictionaryDao.getValue("ENSURE_MONEY", "XINSHUI");//查询出来的是 保证金的最小值与最大值
		   String[]  values=value.split("-");//心水保证金范围 最小值与最大值之间必须用-分割 否则程序可能会出现问题
		   int min=Integer.parseInt(values[0]);
		   int max=Integer.parseInt(values[1]);
		   BigDecimal ensureMoney=po.getEnsureMoney();
		   int ensureMoneyValue=ensureMoney.intValue();
		   if(ensureMoneyValue<min){
			   return "-1";//保证金太小
		   }
		   if(ensureMoneyValue>max){
			   return "0";//保证金太大
		   }
		 //计算价格
		 String xishu=dictionaryDao.getValue("MARC2C_GIN_VS_PRICE", "XINSHUI");
		 BigDecimal price=ensureMoney.divide(new BigDecimal(xishu));
		 po.setPrice(price);
		 String xinshuiNo=this.orderNoGenDao.gen("");
		 po.setXinshuiNo(xinshuiNo);
		 //计算亚盘 大小盘指数
		 
		 //加载对阵开赛时间 2010-04-24 17:11
		 Date raceStartTime=this.xinshuiEnhanceDao.loadStartTime(po.getAgainstId());
		 po.setRaceStartTime(raceStartTime);
		 //
		 c2CProductDao.saveObject(po);
		 return "1";
	}
	 /**【民间高手心水管理—发布管理】
	    * 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT,
	    * 参数：
	    *  raceId:   赛事名称 T_AGAINST.RACE_ID
	    *  startRow：起始记录数
	    *  endRow：  结束记录数
	    * 返回:
	    *  List<C2CProduct>
	    */
	public List<C2CProduct> findC2CProductList(Map params, int startRow,
			int pageSize) throws DaoException {
		return c2CProductDao.findC2CProductList(params, startRow, pageSize);
	}
	@Override
	public int findC2CProductSize(Map params) throws DaoException {
		return c2CProductDao.findC2CProductSize(params);
	}
	 /**【民间高手心水管理—销售订单查看】
	    * 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT,
	    * 参数：
	    *  raceId:   赛事名称 T_AGAINST.RACE_ID
	    *  startRow：起始记录数
	    *  endRow：  结束记录数
	    * 返回:
	    *  List<C2CProduct>
	    */
	   public List<XinshuiOrderVO> findSoldOrderList(Map params, int startRow, int pageSize)throws DaoException{
		   return this.c2CProductDao.findSoldOrderList(params, startRow, pageSize);
	   }
	   /**【民间高手心水管理—销售订单记录数统计】
	    * 民间高手查询c2c产品列表:主表对应表T_C2C_PRODUCT,
	    * 参数：
	    *  raceId:   赛事名称 T_AGAINST.RACE_ID
	    *  startRow：起始记录数
	    *  endRow：  结束记录数
	    * 返回:
	    *  List<C2CProduct>
	    */
	   public int findSoldOrderSize(Map params)throws DaoException{
		   return this.c2CProductDao.findSoldOrderSize(params);
	   }
	//==================================以下是普通用户的方法====================================================================================
	   /**
	    * 用户购买心水时弹出的确认信息界面 用于加载心水信息
	    */
	   public C2CProduct loadC2CProduct(Long c2cId)throws DaoException{
		    C2CProduct po=  this.c2CProductDao.read(c2cId);
		    String userGrade=this.userDao.getUserGrade(po.getTxUserId());//特别注意  因为T_C2C_PRODUCT表没有用户等级这个字段 所以用 心水描述代替用户等级
		    po.setZhDesc(userGrade);
		   return po;
	   }
	   /**
	    * 【普通用户】加载心水订购页面所显示的信息
	    * 参数:
	    * id:T_C2C_PRODUCT.ID
	    */
	 public XinshuiOrderVO loadXinshuiDetail(Long id)throws DaoException{
		 return this.xinshuiLogDao.loadXinshuiDetail(id);
	 }
	/**【普通用户】心水管理—订单管理 
	    * 用户根据条件进行心水查询,并分页  对应表:T_XINSHUI_ORDER,T_C2C_PRODUCT
	    * 参数:
	    * raceId:赛事类型 RACE_ID
	    * payStatus:支付状态
	    * orderNo:订单编号 对应表T_XINSHUI_ORDER.ORDER_NO
	    * xinshuiNO:心水编号T_C2C_PRODUCT.XINSHUI_NO
	    * txUserId:发布人对应表   T_C2C_PRODUCT.TX_USER_ID
	    * hostTeam,guestTeam:主客队名
	    *返回：
	    *心水购买信息列表
	    */
	public List findXinshuiOrderList(Map params, int startRow, int pageSize)
			throws DaoException {
		return this.xinshuiLogDao.findXinshuiOrderList(params, startRow, pageSize);
	}
	@Override
	public int findXinshuiOrderSize(Map params) throws DaoException {
		return xinshuiLogDao.findXinshuiOrderSize(params);
	}
	/**【普通用户】购买B2c或者c2c产品**/
	public Long saveXinshuiLog(XinshuiOrder log) throws DaoException {
		/**2010-04-24 17:30 新增加一个自动**/
		Date raceStartTime=this.xinshuiEnhanceDao.loadStartTimeByC2C(log.getProductId());
		log.setRaceStartTime(raceStartTime);
		return xinshuiLogDao.saveObject(log);
	}

   

	
    /**
     * 已经实现
     */
	@Override  
	public List<C2CProduct> findC2CProductList(Map params) throws DaoException {
		
		return null;
	}
	 /**
     * 已经实现
     * 民间高手胜率排行
	 * 参数:
	 *   flg:  '1':民间高手胜率排行（月）   
	 *         '2':民间高手胜率排行（周）
	 *         '3':
	 *         '4':
	 * @return
	 * @throws DaoException
	 */
	@Override
	public List<Top10Vo> findXinshuiWinTop10List(Map params)
			throws DaoException {
		return xinshuiLogDao.findXinshuiWinTop10List(params);
		
	}
	//=========================================以下是客服部分===========================================================================
	 /**
	 * 【客服】选择从对阵表T_AGAINST里面 挑选若干场对阵，生成心水对阵列表
	 * 
	 * 返回值为"10"则表示当天不能再生成心水赛事
	 * @throws DaoException
	 */
    public String genXinshuiSchedule(String  scheduleIds)throws DaoException{
		   return this.c2CProductDao.genXinshuiAgainst(scheduleIds);
    	
	}
   
        /**
        * 【赛事选择 2010-02-01 10:04】心水后台管理——赛事选择界面图示
	    * 【后台客服】根据条件查询选择赛事信息,为进一步生成心水对阵做准备,对应视图:v_findbackendagainstlist
	    * 参数:
	    * raceId:赛事名称 RACE_ID
	    * raceSeason:赛季
	    * rounds:轮次
	    *status:状态,'已选择','未选择','已过期','已完成'
	    *from:起始时间
	    *to:结束时间
	    *teamName:球队名称
	    * @return
	    */
     
	   public List findBackendAgainstList(Map params)throws DaoException{
		   return  this.xinshuiScheduleDao.findBackendAgainstList(params);
	   }
	   /**
	    * 赛事管理 本功能应该分页 2010-02-01 10:49 文档12页
	    * 参数:
	    * raceId:赛事ID
	    * startRow:起始记录数
	    * pageSize:每页最大记录数
	    * 操作：
	    *  心水:点击后即进入心水管理页面，列出对应赛事的所有心水
	    *  取消:
	    */
	   public List findBackendXinshuiAgainstList(Map params)throws DaoException{
		   return this.xinshuiScheduleDao.findBackendXinshuiAgainstList(params);
	   }
	   /**
	    * 赛事管理分页统计 2010-02-01 10:49 文档12页
	    * 参数:
	    * raceId:赛事ID
	    * startRow:起始记录数
	    * pageSize:每页最大记录数
	    * 操作：
	    *  心水:点击后即进入心水管理页面，列出对应赛事的所有心水
	    *  取消:
	    */
	   public int findBackendXinshuiAgainstSize(Map params)throws DaoException{
		   return this.xinshuiScheduleDao.findBackendXinshuiAgainstSize(params);
	   }
	   /**
	    * 发布人管理 文档第13页   2010-02-01 12:25
	    * @param params
	    * @return
	    * @throws DaoException
	    */
	   public List findBackendXinshuiPubList(Map params)throws DaoException{
		   int size=this.xinshuiScheduleDao.findBackendXinshuiPubSize(params);
		   if(size==0){
			   return new ArrayList();
		   }
		   String minjianGaoshouGrade=this.dictionaryDao.getValue("MARC2C_GIN_VS_PRICE","XINSHUI");//民间高手最近级别
		   params.put("minjianGaoshouGrade", minjianGaoshouGrade);
		   return this.xinshuiScheduleDao.findBackendXinshuiPubList(params);
		   
	   }
	   /**
	    * 发布人管理 分页统计 文档第13页   2010-02-01 12:25
	    * @param params
	    * @return
	    * @throws DaoException
	    */
	   public int findBackendXinshuiPubSize(Map params)throws DaoException{
		   
		   return this.xinshuiScheduleDao.findBackendXinshuiPubSize(params);
		   
	   }
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
	   public List findBackendXinshuiOrderList(Map params)throws DaoException{
		   return this.xinshuiScheduleDao.findBackendXinshuiOrderList(params);
	   }
	   /**
		 * 【心水后台管理——心水订单管理界面】  分页统计2010-02-01 16:30
		 * 参数:
		 *  raceId:联赛名称
		
	    *  orderUsername:订购人用户名
	    *  flg:'1':"订购人”    '2':“订单编号”  '3':“心水编号  '4':“发布人”  '5'
	    *  type:'1':亚盘 '2':大小盘 '3':欧盘
		 */
	   public int findBackendXinshuiOrderSize(Map params)throws DaoException{
		   return this.xinshuiScheduleDao.findBackendXinshuiOrderSize(params);
		                               
	   }
	   /**
		 * 【心水后台管理——心水管理界面】 文档第12页
		 *参数：
	     * againstId：对阵ID
	     * minPrice:最小价格
	     * maxPrice:最大价格
	     * pubUserId:发布人用户ID
		 */
		public List findBackendXinshuiPubRecordList(Map params,int startRow,int pageSize)throws DaoException{
			return this.xinshuiScheduleDao.findBackendXinshuiPubRecordList(params, startRow, pageSize);
		}
		
		 /**
		 * 【心水后台管理——心水管理界面】 文档第12页
		 */
		public int findBackendXinshuiPubRecordSize(Map params)throws DaoException{
			return this.xinshuiScheduleDao.findBackendXinshuiPubRecordSize( params);
		}
		
		
		/**
		 * 【心水后台管理——心水编辑界面图示】2010-02-01 16:27
		 * 参数:
		 * c2cId:心水主键
		 * zhDesc:心水说明
		 */
		public boolean updateXinshui(Map params)throws DaoException{
			 Long c2cId=(Long)params.get("c2cId");
			 String zhDesc=(String)params.get("zhDesc");
			 StringBuilder sql=new StringBuilder();
			 sql.append(" update T_C2C_PRODUCT t  set  t.ZH_DESC='"+zhDesc+"'");
			 sql.append(" where  t.C2C_ID="+c2cId);
			return this.xinshuiScheduleDao.updateBySql(sql.toString());
			
		}
		/**
	     * 关闭心水 即把表T_C2C_PRODUCT.STATUS设置为'5'
	     *参数:
	     *c2cId:对阵表主键
	     */
	    @Transactional(rollbackFor =Exception.class)
	    public void closeXinshui(Long c2cId,Long csUserId,String csName,String ip)throws DaoException{
	    	this.xinshuiScheduleDao.closeXinshui(c2cId, csUserId, csName, ip);
	    }
	    
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
	    public void cancelXinshuiOrder(Long orderId,CSHandleLog  log)throws DaoException{
	    	//1。对应已支付的心水订单，解冻账户(T_VIRTUAL_ACCOUNT) 
	    	//2.产生解冻日志
	    	//3.更改订单状态
	    	//4.产生客服操作日志
	    	this.xinshuiScheduleDao.cancelXinshuiOrder(orderId,log);
	    	
	    }
	    /**
	     * 取消心水 即把表T_AGAINST.STATUS设置为'3'
	     *参数:
	     *againstId:对阵表主键
	     */
	    @Deprecated
	    @Transactional(rollbackFor =Exception.class)
	    public void  cancelXinshui(Long againstId,CSHandleLog csLog)throws DaoException{
	    	this.xinshuiScheduleDao.updateBySql("update  T_AGAINST t  set t.STATUS='3' where t.AGAINST_ID="+againstId);
	    	this.withdrawCsLogAdminDao.saveObject(csLog);
	    }
	    
	    /**
	     * 客服取消赛事  2010-02-05 16:33
	     * 参数:
	     *  againstId:赛事ID
	     * @param log
	     * @throws DaoException
	     */
	    
	    @Transactional(rollbackFor =Exception.class)
	    public void cancelAgainst(Long againstId,CSHandleLog  log)throws DaoException{
	    	this.xinshuiScheduleDao.cancelAgainst(againstId, log);
	    }
	    
	    
	    /**
	     * 加载心水详情
	     * @param id
	     * @return
	     * @throws DaoException
	     */
	    public XinshuiDetailVO loadXinshuiDetailVO(Long c2cId)throws DaoException{
	    	
	    	return  this.xinshuiScheduleDao.loadXinshuiDetailVO(c2cId);
	    }
	    /**
		  * 心水后台确认支付
		  *参数：
		  *  orderId：心水订单ID,对应表T_XINSHUI_ORDER的主键
		  *返回值:
		  * 5:账户已经被锁定
		  * 4:余额不足
		  * 1:支付成功
		  */
	    @Transactional(rollbackFor =Exception.class)
	     public String backendXinshuiConfirmPay( Long orderId,CSHandleLog  csLog) throws DaoException{
	    	 String result=this.payDao.backendXinshuiConfirmPay(orderId);
	    	 if("1".equals(result.trim())){
	    	   this.withdrawCsLogAdminDao.saveObject(csLog);
	    	 }
	    	 return result;
	     }
	    public List findXinshuiList(Map params)throws DaoException{
	    	return this.c2CProductDao.findXinshuiList(params);
	    }
	    public int findXinshuiSize(Map params)throws DaoException{
	    	return this.c2CProductDao.findXinshuiSize(params);
	    }
	    /**
	     * 判断用户是否已经购买了本心水  2010-02-24 15:16
	     *参数:
	     *  buyUserId:当前用户ID
	     *  c2cId:心水ID
	     * 返回值：
	     *  true:表示现在可以购买该心水
	     *  false:禁止购买
	     */
	    public boolean  isAllowBuy(Map params)throws DaoException{
	    	return this.c2CProductDao.isAlreadyBuy(params);
	    }
	    /**
	     * 心水二次支付详情
	     * @param xinshuiOrderId
	     * @return
	     * @throws DaoException
	     */
	    public XinshuiOrderDetailVO loadXinshuiOrderDetail(Long xinshuiOrderId)throws DaoException{
	    	return this.xinshuiLogDao.loadXinshuiOrderDetail(xinshuiOrderId);
	    }
	

	
	@Autowired
	private B2CExpertDao b2CExpertDao;
	
	@Autowired
	private C2CProductDao c2CProductDao;
	@Autowired
	private XinshuiLogDao xinshuiLogDao;
	@Autowired
	private DictionaryDao dictionaryDao;
	@Autowired
	private XinshuiScheduleDao xinshuiScheduleDao;
	@Autowired
	private WithdrawCsLogAdminDao  withdrawCsLogAdminDao;
	@Autowired
	private PayDao payDao;
	@Autowired
	private OrderNoGenDao orderNoGenDao;
	@Autowired
	private UserDao userDao;
	@Resource
	private XinshuiEnhancementDao xinshuiEnhanceDao;

}
