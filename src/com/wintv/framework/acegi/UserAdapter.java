package com.wintv.framework.acegi;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import com.wintv.framework.common.OnlineUserIdInSession;
import com.wintv.framework.pojo.User;

public class UserAdapter implements UserCookie,HttpSessionBindingListener{
	private static final long serialVersionUID = 3126813748689935992L;
	private User user;
	
	public UserAdapter(User user) {
		if (user == null)
			throw new IllegalArgumentException( "Cannot pass null object to user.");
		this.user = user;
	}
	/**
	 * 绑定session时执行的操作
	 */
	public void valueBound(HttpSessionBindingEvent e) {
		try {
			OnlineUserIdInSession.addUserId(user.getUserid());
		
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * session remove或time out 时所执行的操作
	 */
	public void valueUnbound(HttpSessionBindingEvent e) {
		try {
			OnlineUserIdInSession.removeUserId(user.getUserid());
			//System.out.println(OnlineUserIdInSession.getList().toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserAdapter))
			return false;
		UserAdapter userAdapter = (UserAdapter) o;
		return user.equals(userAdapter.user);

	}
	
	public int hashCode() {
		return user.hashCode();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public Long getUserId() {
		// TODO Auto-generated method stub
		return user.getUserid();
	}

	@Override
	public String getMilitaryScore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return user.getName();
	}

	@Override
	public String getUserGrade() {
		// TODO Auto-generated method stub
		return null;
	}
}
