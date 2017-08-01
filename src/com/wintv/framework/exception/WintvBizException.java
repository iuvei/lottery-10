package com.wintv.framework.exception;

/**
 * 业务逻辑相关的异常
 *
 * @author Steady Wang
 * @date 2008-5-4 10:55:07
 */
public class WintvBizException extends RuntimeException {
    private static final long serialVersionUID = -1997275272398936113L;

    public WintvBizException() {
        super();
    }

    public WintvBizException(String message) {
        super(message);
    }
}
