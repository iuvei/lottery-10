package com.wintv.lottery.user.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.wintv.framework.common.hibernate.BaseHibernate;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.InvalidUsernamePasswordException;
import com.wintv.framework.exception.UserServiceException;
import com.wintv.framework.exception.UsernameLockException;
import com.wintv.framework.pojo.Dictionary;
import com.wintv.framework.pojo.User;
import com.wintv.framework.utils.OracleSqlUtil;
import com.wintv.lottery.user.dao.UserDao;

@Repository("userDao")
@SuppressWarnings("unchecked")
public class UserDaoImpl extends BaseHibernate<User,Long> implements UserDao{
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * 判断用户名是否存在
	 * 
	 * @param username：用户名
	 * @return:true 表示存在 false 表示不存在
	 * @throws DaoException：数据操作异常
	 */
	@Override
	public boolean isExistUser(String username) throws DaoException {
		// Object[] params={username};
		// List list=this.getSession().find("from User where
		// username=?",params);
		// List list=this.getSession().createQuery("from User where
		// username=?").list();
		Query query = this.getSession().createQuery(
				"from User  where  username=?");
		query.setString(0, username);
		List list = query.list();
		if (list == null || 0 == list.size()) {
			return false;
		}
		return true;
	}

	/**
	 * 注册用户 参数: user:用户注册信息 DaoException:数据库操作异常 返回:注册后返回的完整用户信息
	 */
	@Override
	public Long saveUser(User user) throws DaoException {
		return  saveObject(user);
	}

	/**
	 * 用户登录 参数: user: 用户名 pswd:密码(网站登陆密码,不是投注密码) 异常:
	 * UserServiceException:用户接口系统异常,例如数据库操作异常等
	 * InvalidUsernamePasswordException:用户或密码错误 应该检查用户账号是否被锁定 返回:完整的用户信息
	 */
	@Override
	public User authLogin(String username, String loginPassword)
			throws UserServiceException, UsernameLockException,
			InvalidUsernamePasswordException {
		Object[] params = { username, loginPassword };
		// List list=this.getHibernateTemplate().find("from User u where
		// u.username=? and u.loginPassword=?",params);
		Query query = this.getSession().createQuery(
				"from User u where u.username=? and u.loginPassword=?");
		query.setString(0, username);
		query.setString(1, loginPassword);
		List list = query.list();
		if (list == null || 0 == list.size()) {
			throw new InvalidUsernamePasswordException();
		} else {
			User user = (User) list.get(0);
			if ("2".equals(user.getStatus())) {
				throw new UsernameLockException(username + "用户已经被锁定");
			}
		}
		return (User) list.get(0);

	}

	/**
	 * 根据用户ID或者用户名锁定账户 参数： params：参数 包括 可能是 userid 或username status：状态 既可以是冻结
	 * 也可以是解冻 DaoException:数据库操作异常 返回:是否锁定成功
	 * 
	 * @throws SQLException
	 */
	// public boolean lockUser(Map params)throws DaoException, SQLException{
	// String username=(String)params.get("username");
	// Long userid=(Long)params.get("userid");
	// String status=(String)params.get("status");
	// this.getSession().update(arg0, arg1);
	// Connection conn=null;
	// PreparedStatement ps=null;
	// try{
	// conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	// StringBuilder sql=new StringBuilder("update T_USER set
	// t.status='"+status+"' where 1=1 ");
	// if(StringUtils.isNotEmpty(username)){
	// sql.append(" and t.username like'%"+username+"%'");
	// }else if(userid!=null){
	// sql.append(" and t.userid="+userid);
	// }
	// ps=conn.prepareStatement(sql.toString());
	// int num=ps.executeUpdate();
	// return num==1;
	// }catch(SQLException e){
	// throw new DaoException(e.getLocalizedMessage());
	// }finally{
	// ps.close();
	// conn.close();
	// }
	// }
	/**
	 * 根据用户ID修改用户信息 参数： params：userid 用户ID 包括 密码 等等 DaoException:数据库操作异常
	 * 返回:是否修改成功
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean updateUser(User user) throws DaoException {
		try {
			this.getSession().update(user);
			return true;
		} catch (DataAccessException e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	/**
	 * 根据用户ID加载用户信息 参数： params：userid 用户ID DaoException:数据库操作异常 返回:一个完整的用户对象
	 * 
	 */
	@Override
	public User loadUser(Long id) throws DaoException {
		try {
			return (User) getSession().load(User.class, id);
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	@Override
	public User findByUsername(String username) {
		Query query = this.getSession().createQuery("from User where username = ? ");
		query.setString(0, username);
		return (User) query.uniqueResult();
	}

	@Override
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		return this.getSession().createQuery("from User").list();
	}
	
	@Override
	public int updateUserStatus(String statusValue, Integer userid) {
		// TODO Auto-generated method stub
		return this.updateBySQL("udpate User u  set u.status = " + statusValue + " where u.userid = "+ userid);
	}
	
	@Override
	public boolean isExistEmail(String email) throws DaoException {
		Query query = this.getSession().createQuery(
		"from User u where u.email=?");
		query.setString(0, email);
		List list = query.list();
		if (list == null || 0 == list.size()) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean authWithdrawPassword (Long userid, String pswd) throws DaoException {
		Query query = this.getSession().createQuery("from User u where u.userid=? and u.withdrawPwd=?");
		query.setLong(0, userid);
		query.setString(1, pswd);
		
		List list = query.list();
		if (list == null || 0 == list.size()) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<Dictionary> findAllPassAnswer() {
		return this.getSession().createQuery("from Dictionary d where d.type = ?").setString(0, "PASS_ANSWER").list();
	}
	
	public List findUserIdsByUsername(String userList){
		List result=new ArrayList();
		userList=OracleSqlUtil.formatSqlAddCommas(userList);// 加单引号 格式化为"'name1','name2','name3'"
		List list=this.getSession().createSQLQuery("select USERID from  T_USER where  USERNAME in ("+userList+")").list();
	    for(Object id:list){
	      result.add(id);
	    }
		return result;
	}
	/**
	 * 根据用户ID查询用户等级
	 * 参数：
	 * userId：用户ID
	 * 返回：
	 *   用户等级
	 * 
	 */
	public String  getUserGrade(Long userId){
		SQLQuery q=this.getSession().createSQLQuery("select t.USER_GRADE from  T_USER t where t.USERID=?");
		q.setLong(0, userId);
		q.addScalar("USER_GRADE", Hibernate.STRING);
		return (String)q.uniqueResult();
	}
	/**
	 * 根据用户ID 查询用户的心水级别 以判断该用户是否有权限发布心水 2010-04-13 15:33
	 * @param betId
	 * @return
	 * @throws DaoException
	 */
	public int getXinshuiMilitary(long userId)throws DaoException{
		StringBuilder sql=new StringBuilder("select  t.xinshui_military from t_user  t where t.userid=?");
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	    try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, userId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				return rs.getInt("xinshui_military");
		   }
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		return 0;
	}
	
}
