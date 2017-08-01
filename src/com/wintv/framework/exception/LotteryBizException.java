package com.wintv.framework.exception;

public class LotteryBizException extends RuntimeException  {
	  private static final long serialVersionUID = -1997275272398936113L;

	    public LotteryBizException() {
	        super();
	    }

	    public LotteryBizException(String message) {
	        super(message);
	    }
}
