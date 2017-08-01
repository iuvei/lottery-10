package com.wintv.framework.interceptor;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.wintv.framework.acegi.UserCookie;
/**
 * 
 * @author Hikin Yao && arix04
 *
 * @version 1.0.0
 */
public class IsLoginHandleInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 5798418340087409690L;
	private static final ArrayList mycenterUrlList = new ArrayList();
	
	private Log log = LogFactory.getLog(IsLoginHandleInterceptor.class);
    public void destroy() {}

    public void init() {
    	mycenterUrlList.add("/mylottery.html");
    	mycenterUrlList.add("/pay/fund_list.html");
    	mycenterUrlList.add("/pay/charge.html");
    	mycenterUrlList.add("/pay/withdraw.html");
    	mycenterUrlList.add("/pay/caijin_list.html");
    	mycenterUrlList.add("/attention/index.html");
    	mycenterUrlList.add("/b2c/myB2cOrders.html");
    	mycenterUrlList.add("/user/preUpdateUserInfo.html");
    	mycenterUrlList.add("/user/preUpdateLPUserInfo.html");
    	mycenterUrlList.add("/user/preUpdateBankUserInfo.html");
    	mycenterUrlList.add("/bet/mybetrecord.html");
    	mycenterUrlList.add("/bet/gendan/mylistGendan.html");
    	mycenterUrlList.add("/xinshui/buyManage.html");
    	mycenterUrlList.add("/xinshui/saleManage.html");
    	mycenterUrlList.add("/xinshui/releaseManage.html");
    }
    
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext ctx = invocation.getInvocationContext();
        UserCookie userSession = (UserCookie)ctx.getSession().get("userCookie");
        
        if (null !=userSession) { 
            return invocation.invoke();
        } else {
        	//取得请求的Action名 
            String name = invocation.getInvocationContext().getName();//action 
            //namespace的名称，在xml中配置的 
            String namespace = invocation.getProxy().getNamespace();//获取到namespace，还能够获取到要执行的方法，class等 

            if((namespace != null) && (namespace.trim().length() > 0)){ 
    	        if("/".equals(namespace.trim())){ 
    		        //说明是根路径，不需要再增加反斜杠了。 
    		        }else{ 
    		        namespace += "/"; 
    	        } 
            } 
            String URL = namespace+invocation.getProxy().getActionName(); 

            URL += ".html";
            
            if(isMycenterUrl(URL)) {
            	URL = "/mycenter.html?targetUrl="+URL;
            } else {
            	HttpServletRequest request= (HttpServletRequest)invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
                
                if(request.getParameter("targetUrl") != null) {
                	URL += "?targetUrl="+request.getParameter("targetUrl");
                }
            }
            
            //获取未登录前需要执行的url;
            ctx.getSession().put("loginRedirectUrl", URL);
        	return ActionSupport.LOGIN;    
        }  
    }
    
    /***
     * 判断url是否是个人中心的url
     * @param URL
     * @return
     */
    private boolean isMycenterUrl(String URL) {
    	return mycenterUrlList.contains(URL);
    }
}
