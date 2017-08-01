package com.wintv.framework.domain.base;


/**
 * @author Steady Wang
 * @date 2008-5-4 14:09:03
 */
public class Result extends BaseVO {
	private static final long serialVersionUID = 1479533934025392748L;
	private Integer code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
