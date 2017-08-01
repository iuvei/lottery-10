package com.wintv.framework.exception;

/**
* 表示dao处理异常
* 封装底层数据库访问异常
* @author 王金阶
* @since 1.0
*/
@SuppressWarnings("serial")
public class DaoException extends Exception{
	
	public DaoException(Exception e){
		super(e);
		
	}
	
	public DaoException(String s){
		super(s);
	}
	

}