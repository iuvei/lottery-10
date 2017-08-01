package com.wintv.framework.exception;

/**
 * @author Steady Wang
 */
public class PermissionException extends WintvBizException {

	private static final long serialVersionUID = 4152952491191094632L;

	public PermissionException() {
        super();
    }

    public PermissionException(String message) {
        super(message);
    }
}

