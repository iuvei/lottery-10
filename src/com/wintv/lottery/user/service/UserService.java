package com.wintv.lottery.user.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.InvalidUsernamePasswordException;
import com.wintv.framework.exception.UserServiceException;
import com.wintv.framework.exception.UsernameAlreadyExistException;
import com.wintv.framework.exception.UsernameLockException;
import com.wintv.framework.pojo.Code;
import com.wintv.framework.pojo.Dictionary;
import com.wintv.framework.pojo.User;
import com.wintv.framework.pojo.VirtualAccount;

public interface UserService {
	
	/**
	 * 获得当前用户冻结资金，彩金，积分等信息
	 * 参数:
	 * userid:userid
	 * @return
	 * Map:myFrozenFund,myCaijing,myPoint
	 */
	public Map getPayInfo(Long userId) throws DaoException;
	
	/**
	 * 获得当前用户资金余额
	 * 参数:
	 * userid:userid
	 * @return
	 */
	public BigDecimal getFund(Long userId) throws DaoException;
	
	/** 
	 * 登录成功后登录次数加一
	 * 参数:
	 * userid:userid
	 * 异常：
	 * DaoException:系统异常,例如数据库操作异常等
	 * 返回:
	 */
	public void setLoginTimes(Long userid) throws DaoException;
	
	public boolean isExistUser(String username) throws DaoException;

	public boolean isExistEmail(String email) throws DaoException;
	/**
	 * 注册用户 
	 * 参数: user:用户注册信息 
	 * UserServiceException:用户接口系统异常,例如数据库操作异常等
	 * UsernameAlreadyRegisterException:用户名已注册
	 * EmailAlreadyRegisterException:用户email已注册 
	 * 返回:注册后返回的完整用户信息
	 */
	public User register(User user) throws UserServiceException,
			UsernameAlreadyExistException;

	/**
	 * 用户登录 
	 * 参数: user: 用户名 pswd:密码(网站登陆密码,不是取款密码) 
	 * UserServiceException:用户接口系统异常,例如数据库操作异常等 
	 * UserNotRegisterException:用户未注册
	 * InvalidPasswordException:用户密码不匹配 应该检查用户账号是否被锁定 
	 * 返回:完整的用户信息
	 */
	public User authLogin(String user, String pswd)
			throws UserServiceException, UsernameLockException,
			InvalidUsernamePasswordException;

	/**
	 * 用户登录 
	 * 参数: userid: 用户id pswd:取款密码
	 * 返回:完整的用户信息
	 */
	public boolean authWithdrawPassword(Long userid, String pswd) throws DaoException;
	
	/**
	 * 根据用户ID或者用户名锁定账户 
	 * 参数： params：参数 包括 可能是 userid 或username
	 * DaoException:数据库操作异常 
	 * 返回:是否锁定成功
	 */
	public boolean lockUser(Map params) throws DaoException, SQLException;

	public User findUserByUserName(String userName);
	
	/**
	 * 获取系统所有用户
	 * */
	public List<User> getAllUsers();
	
	/**
	 * 保存用户
	 * */
	public void saveUser(User user) throws DaoException;

	public void delete(Long userid);
	
	/**
	 * 按属性查找数据库,判断数据库中是否存在该属性用户
	 * */
	public User findUniqueUserByFiled(String filedName,Object filedValue);

	
	/**
	 * 用户通过邮箱取回密码
	 * @param message_subject  主题
	 * @param message_body	   邮件主体
	 * @param send_to          发送对象
	 * */
	public void sendEmail(String message_subject ,String message_body ,String send_to)throws UserServiceException;

	public User findUserByUserId(Long userid);

	public boolean activateAccount(Integer id);
	
	//密码提示问题找回密码
	int retrieveLP31(Map<String,Object> param);
	
	//查出所有密码提示问题	
	List<Dictionary> findAllPassAnswer();
	
	//查出所有省份
	List<Code> findAllProvince();
	
	//查出某个省的所有城市
	List<Code> findAllCityByProvince(Long pId);
	
	//更新用户邮箱
	int updateEmailUserInfo(Map<String, Object> param);
	
	//更新用户基本信息
	int updateUserInfo(User user,Long userId);
	
	//按地区名查找地区对象
	Code findCodeByName(String name);
	
	//查找数据字典通用方法
	Dictionary findUserPassAnswer(String code, String type);
	
	//修改用户密码
	int updateLPUserInfo(Long userId,String old_pass, String new_pass);
	
	//修改用户密码提示问题
	int updateAnswerUserInfo(Map<String, Object> param);
	
	//查找用户的虚拟帐号
	VirtualAccount findVirtualAccountByUserId(Long userId);

	//设置和修改银行
	int updateBankUserInfo(Map<String, Object> param);

	//设置和修改取款密码
	int updateBankPassUserInfo(Map<String, Object> param);

	String updateUserByParam(Map<String, Object> param);

	
	
}
