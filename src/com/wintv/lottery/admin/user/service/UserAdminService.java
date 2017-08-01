package com.wintv.lottery.admin.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.admin.user.vo.UserAccountInfoVO;
import com.wintv.lottery.admin.user.vo.UserAdminVO;
import com.wintv.lottery.admin.user.vo.UserInfoVO;
import com.wintv.lottery.admin.user.vo.UserXinshuiVO;

/**
 * 后台管理 用户管理模块
 * 
 * @author Hikin Yao
 * @version 1.0.0
 */
@SuppressWarnings("unchecked")
public interface UserAdminService {
	/**
	 * 更改用户状态
	 * 
	 * @param checkedIds
	 *            用户Id
	 * @param status
	 *            用户锁定状态 1：正常 2.登陆锁定 3.资金锁定
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	@Transactional(rollbackFor = DaoException.class)
	public int updateUserStatus(String checkedIds, Integer status)throws DaoException;
	/**
	 * 更改用户登录状态
	 * 
	 * @param checkedIds
	 *            用户Id
	 * @param status
	 *            1.正常 2.登录锁定
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateUserLoginStatus(String checkedIds, String status)
			throws DaoException;

	/**
	 * 更改用户资金状态
	 * 
	 * @param checkedIds
	 *            用户Id
	 * @param status
	 *            1.正常 2.资金锁定
	 * @throws DaoException：数据操作异常
	 */
	public int updateUserAccountStatus(String checkedIds, String status)
			throws DaoException;
	
	/**
	 * 更改用户在线状态
	 * 
	 * @userId checkedIds
	 * @param status
	 *            1.在线 2.不在线
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateUserOnlineStatus(String checkedIds, String status)
			throws DaoException;
	/**
	 * 按照条件对用户进行查询
	 * 
	 * @param queryCondition
	 *            查询条件字段有:注册时间区间,账户金额区间,投注金额区间,会员地区,关键字,用户等级,用户头衔,锁定状态
	 * @param orderFiled
	 *            排序字段 后台界面用户可选排序字段有：注册时间,账户金额,
	 * @param orderType
	 *            排序类型 “ASC|DESC”
	 * @param startRow
	 *            查询开始行
	 * @param pageSize
	 *            页面记录数大小
	 * @return 返回用户查询列表
	 */
	public List<UserAdminVO> searchUserList(StringBuilder queryCondition,
			String orderFiled, String orderType, Integer startRow,
			Integer pageSize);

	/**
	 * 统计符合查询条件的用户记录数
	 * 
	 * @param queryCondition
	 *            查询条件字段有:注册时间区间,账户金额区间,投注金额区间,会员地区,关键字,用户等级,用户头衔,锁定状态
	 * @return 返回根据检索条件统计当前会员数 默认统计总会员数
	 */
	public Integer countUsersBySearch(StringBuilder queryCondition);

	/**
	 * 获取用户基本信息与附加信息
	 * 
	 * @param userId
	 * @return 返回用户对象
	 */
	public UserInfoVO getUserInfo(Long userId) throws DaoException;

	/**
	 * 获取会员账户信息
	 * 
	 * @param userId
	 *            所查询用户ID
	 * 
	 * @return 返回用户对象
	 */
	public UserAccountInfoVO getUserAccountInfo(Long userId);

	/**
	 * 根据用户选中的多个用户id 获取相应的多个用户
	 * 
	 * @param checkedIds
	 *            选择的多个用户Id，中间用逗号分隔
	 * @return 返回相应的用户名串，多个用户用逗号分隔
	 */
	public String getUserListByUserIds(String checkedIds);
	
	/**
	 * 获取会员心水信息
	 * 
	 * @param userId
	 *            所查询用户ID
	 * 
	 * @return 返回用户对象
	 */
	public UserXinshuiVO loadUserXinshuiInfo(Long userId);


}
