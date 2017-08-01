package com.wintv.lottery.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.InvalidUsernamePasswordException;
import com.wintv.framework.exception.UserServiceException;
import com.wintv.framework.exception.UsernameLockException;
import com.wintv.framework.pojo.Dictionary;
import com.wintv.framework.pojo.User;

/**
 * 用户注册, 登录接口
 * 
 * @author 王金阶
 * @since 1.0
 */
public interface UserDao extends BaseDao<User, Long> {
	/**
	 * 根据用户ID 查询用户的心水级别 以判断该用户是否有权限发布心水 2010-04-13 15:33
	 * @param betId
	 * @return
	 * @throws DaoException
	 */
	public int getXinshuiMilitary(long userId)throws DaoException;
	/**
	 * 根据用户ID查询用户等级
	 * 参数：
	 * userId：用户ID
	 * 返回：
	 *   用户等级
	 * 
	 */
	public String  getUserGrade(Long userId);
	/**
	 * 判断用户名是否存在
	 * 
	 * @param username：用户名
	 * @return:true 表示存在 false 表示不存在
	 * @throws DaoException：数据操作异常
	 */
	public boolean isExistUser(String username) throws DaoException;

	/**
	 * 判断邮箱是否存在
	 * 
	 * @param email：邮箱名
	 * @return:true 表示存在 false 表示不存在
	 * @throws DaoException：数据操作异常
	 */
	public boolean isExistEmail(String email) throws DaoException;

	/**
	 * 注册用户 参数: user:用户注册信息 UserServiceException:用户接口系统异常,例如数据库操作异常等
	 * 返回:注册后返回的完整用户信息
	 */
	public Long saveUser(User user) throws DaoException;

	/**
	 * 用户登录 参数: user: 用户名 pswd:密码(网站登陆密码,不是投注密码) 异常:
	 * UserServiceException:用户接口系统异常,例如数据库操作异常等
	 * InvalidUsernamePasswordException:用户或密码错误 应该检查用户账号是否被锁定 返回:完整的用户信息
	 */
	public User authLogin(String user, String pswd)
			throws UserServiceException, UsernameLockException,
			InvalidUsernamePasswordException;

	/**
	 * 用户登录 参数: user: 用户名 pswd:取款密码 返回:完整的用户信息
	 */
	public boolean authWithdrawPassword(Long userid, String pswd)
			throws DaoException;

	// /**
	// * 根据用户ID或者用户名锁定账户
	// * 参数：
	// * params：参数 包括 可能是 userid 或username status：状态 既可以是冻结 也可以是解冻
	// * DaoException:数据库操作异常
	// * 返回:是否锁定成功
	// * @throws SQLException
	// */
	// public boolean lockUser(Map params)throws DaoException, SQLException;
	/**
	 * 根据用户ID修改用户信息 参数： params：userid 用户ID 可能包括 密码 等等 DaoException:数据库操作异常
	 * 返回:是否修改成功
	 * 
	 */
	public boolean updateUser(User user) throws DaoException, SQLException;

	/**
	 * 根据用户ID加载用户信息 参数： params：userid 用户ID DaoException:数据库操作异常 返回:一个完整的用户对象
	 * 
	 */
	public User loadUser(Long userid) throws DaoException;

	public User findByUsername(String userName);

	public List<User> findAllUser();

	int updateUserStatus(String statusValue, Integer userid);

	List<Dictionary> findAllPassAnswer();

	/**
	 * 通过用户名获取相应的用户Id;
	 * 
	 * @param userList
	 */
	public List<Long> findUserIdsByUsername(String userList);

}
