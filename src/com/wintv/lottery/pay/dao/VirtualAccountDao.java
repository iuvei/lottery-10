package com.wintv.lottery.pay.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.User;
import com.wintv.framework.pojo.VirtualAccount;

/**
 * 虚拟账户操作接口
 * @author 王金阶
 * @since 1.0
 */
public interface VirtualAccountDao extends BaseDao<VirtualAccount,Long> {
	/** 
	 * 为用户创建虚拟账户
	 * 参数:
	 * account:用户基本信息  用户ID
	 * UserServiceException:用户接口系统异常,例如数据库操作异常等
	 * 返回:注册后返回的完整用户信息
	 */
	public VirtualAccount createVAAccount(Map params)throws DaoException;
	/**
	 * 根据后台彩金捐赠订单和用户名列表 批量给用户增加彩金金额
	 * 
	 * @author hikin yao
	 * @param userNameList 
	 *            用户名列表 中间逗号分隔 格式为"name1,name2,name3"
	 * @param money
	 *            彩金金额
	 * @throws DaoException
	 */
	public Integer bathAddCaiJinDonateByUserNamesAdmin(String userNameList,BigDecimal money) throws DaoException;
	/**
	 * 根据用户ID返回一个用户账户信息
	 * @author hikin yao
	 * @param userId 账户Id
	 * @return
	 * @throws DaoException
	 */
	public VirtualAccount getUserAccountByUserId(Long userId) throws DaoException;
}