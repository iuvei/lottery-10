package com.wintv.framework.exception;

/**
 * 业务组件异常，如数据库异常
 * @author 王金阶
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UserServiceException extends Exception {
   public UserServiceException(String msg){
	   super(msg);
   }
}
