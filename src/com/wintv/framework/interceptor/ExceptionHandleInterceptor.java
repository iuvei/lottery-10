package com.wintv.framework.interceptor;

import com.wintv.framework.exception.WintvBizException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author agilejava
 */
public class ExceptionHandleInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 5798418340087409690L;
	private Log log = LogFactory.getLog(ExceptionHandleInterceptor.class);

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();
        ActionContext ctx = invocation.getInvocationContext();

        if (action instanceof ValidationAware) {
            ValidationAware tempAction = (ValidationAware) action;
            try {
                return invocation.invoke();
            } catch (Exception exception) {
                String message;
                // 如果为 WintvBizException 则向页面中加入出错信息
                if (exception instanceof WintvBizException) {
                    WintvBizException be = (WintvBizException) exception;
                    if (StringUtils.isNotEmpty(be.getMessage())) {
                        message = getLocaleText(be.getMessage().trim(), ctx);
                        tempAction.addActionError(message);
                    } else {
                        message = getLocaleText("errors.cnmon.unexpected", ctx);
                        tempAction.addActionError(message);
                    }
                    log.error("Business Exception occured: " + exception.getMessage());
                } else {
                    message = getLocaleText("errors.cnmon.unexpected", ctx);
                    tempAction.addActionError(message);
                    log.error("Unexpected exception occured: ", exception);
                }
            }
            return ActionSupport.INPUT;
        } else {
            if (log.isInfoEnabled()) {
                log.warn("Current action is not subclass of ValidationAware");
            }
            return invocation.invoke();
        }
    }

    private String getLocaleText(String key, ActionContext ctx) {
        return LocalizedTextUtil.findDefaultText(key, ctx.getLocale());
    }
}
