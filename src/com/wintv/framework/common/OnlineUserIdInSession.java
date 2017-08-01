package com.wintv.framework.common;

import java.util.HashSet;
import java.util.Set;

/**
 * 维护在线用户，为了降低系统消耗，通过监听用户的session情况，只将在线用户的id，放到在线维护列表中去。
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
public class OnlineUserIdInSession {
	private static Set<Long> store = new HashSet<Long>();
	private OnlineUserIdInSession() {
	}

	/**
	 * 判断用户是否在线
	 * 
	 * @param userId
	 * @return
	 */
	public static boolean isUserOnline(Long userId) {
		return store.contains(userId);
	}

	/**
	 * 获取用户总在线人数
	 * 
	 * @return 返回总在线人数
	 */
	public static Integer getTotalOnlineUserCount() {
		return store.size();
	}

	public static synchronized void addUserId(Long userId) {
		if (userId != null) {
			store.add(userId);
		}
	}

	public static synchronized void removeUserId(Long userId) {
		if (userId != null)
			store.remove(userId);
	}

	public static Set<Long> getList() {
		return store;
	}

	public String toString() {
		return store.toString();
	}
}
