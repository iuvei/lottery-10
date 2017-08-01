package com.wintv.framework.exception;

/**
 * 用户注册时，如果用户名已经存在 则抛出此异常
 * @author 王金阶
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UsernameAlreadyExistException extends Exception{
	public UsernameAlreadyExistException(Exception e){
		super(e);
		
	}
	
	public UsernameAlreadyExistException(String s){
		super(s);
	}
}
