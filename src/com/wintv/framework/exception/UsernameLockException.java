package com.wintv.framework.exception;

/**
 * 用户锁定异常
 * @author 王金阶
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UsernameLockException extends Exception {
 public UsernameLockException(String msg){
	 super("该用户已经被锁定");
 }
}
