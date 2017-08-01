package com.wintv.lottery.user.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserAdapter;
import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.common.Constants;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.InvalidUsernamePasswordException;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.framework.exception.UserServiceException;
import com.wintv.framework.exception.UsernameLockException;
import com.wintv.framework.pojo.Code;
import com.wintv.framework.pojo.Dictionary;
import com.wintv.framework.pojo.User;
import com.wintv.framework.pojo.VirtualAccount;
import com.wintv.framework.utils.DateUtil;
import com.wintv.framework.utils.EncryptionUtil;
import com.wintv.framework.utils.Util;
import com.wintv.framework.utils.mail.EmailFactory;
import com.wintv.framework.utils.mail.SendHtmlEmail;
import com.wintv.lottery.user.service.SecurityIpService;
import com.wintv.lottery.user.service.UserService;

public class UserAction extends BaseAction {
	private static final long serialVersionUID = 3172036547183315441L;

	public String excute() {
		return null;
	}
	
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityIpService securityIpService;
	private User user;
	private String loginPassword;
	private String rePassword;
	private String reEmail;
	private String accountId;
	private String checkcode;
	private String rememberUsername;
	private String retrieve_lp_fashion;//找回密码方式 1.邮箱 2.问题
	private Dictionary passAnswerTip;		    //密码找回问题
	private String newPassAnswerTipCode;        //新的密码找回问题 
	private String passAnswer;			   		//密码找回问题答案
	private String newPassAnswer;				//新的密码找回问题答案
	private List<Dictionary> passAnswerList;    //密码找回问题清单
	private String prefixEmail;        //邮箱前缀
	private String postfixEmail;	   //邮箱后缀
	private List<Code> provinceList;    //省份清单
	private List<Code> cityList = new ArrayList<Code>();         //城市清单
	private String provinceId;
	private VirtualAccount virtualAccount;
	//注册ajax验证字段
	private String loginName;
	private String email;
	private String retrieve_source;       //取回密码来源  rePass忘记登陆密码   reTip忘记密码提示  reWithdraw取款
	
	private String loginRedirectUrl="/mycenter.html";//用户登录成功后跳转到的url,默认跳转到用户个人中心

	public SecurityIpService getSecurityIpService() {
		return securityIpService;
	}

	public void setSecurityIpService(SecurityIpService securityIpService) {
		this.securityIpService = securityIpService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getReEmail() {
		return reEmail;
	}

	public void setReEmail(String reEmail) {
		this.reEmail = reEmail;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String getRememberUsername() {
		return rememberUsername;
	}

	public void setRememberUsername(String rememberUsername) {
		this.rememberUsername = rememberUsername;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getRetrieve_lp_fashion() {
		return retrieve_lp_fashion;
	}

	public void setRetrieve_lp_fashion(String retrieve_lp_fashion) {
		this.retrieve_lp_fashion = retrieve_lp_fashion;
	}

	public Dictionary getPassAnswerTip() {
		return passAnswerTip;
	}

	public void setPassAnswerTip(Dictionary passAnswerTip) {
		this.passAnswerTip = passAnswerTip;
	}

	public String getNewPassAnswerTipCode() {
		return newPassAnswerTipCode;
	}

	public void setNewPassAnswerTipCode(String newPassAnswerTipCode) {
		this.newPassAnswerTipCode = newPassAnswerTipCode;
	}

	public String getPassAnswer() {
		return passAnswer;
	}

	public void setPassAnswer(String passAnswer) {
		this.passAnswer = passAnswer;
	}
	
	public String getNewPassAnswer() {
		return newPassAnswer;
	}

	public void setNewPassAnswer(String newPassAnswer) {
		this.newPassAnswer = newPassAnswer;
	}

	public List<Dictionary> getPassAnswerList() {
		return passAnswerList;
	}

	public void setPassAnswerList(List<Dictionary> passAnswerList) {
		this.passAnswerList = passAnswerList;
	}

	public String getPrefixEmail() {
		return prefixEmail;
	}

	public void setPrefixEmail(String prefixEmail) {
		this.prefixEmail = prefixEmail;
	}

	public String getPostfixEmail() {
		return postfixEmail;
	}

	public void setPostfixEmail(String postfixEmail) {
		this.postfixEmail = postfixEmail;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Code> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Code> provinceList) {
		this.provinceList = provinceList;
	}

	public List<Code> getCityList() {
		return cityList;
	}

	public void setCityList(List<Code> cityList) {
		this.cityList = cityList;
	}
	

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public VirtualAccount getVirtualAccount() {
		return virtualAccount;
	}

	public void setVirtualAccount(VirtualAccount virtualAccount) {
		this.virtualAccount = virtualAccount;
	}

	public String getLoginRedirectUrl() {
		return loginRedirectUrl;
	}

	public void setLoginRedirectUrl(String loginRedirectUrl) {
		this.loginRedirectUrl = loginRedirectUrl;
	}

	public String getRetrieve_source() {
		return retrieve_source;
	}

	public void setRetrieve_source(String retrieve_source) {
		this.retrieve_source = retrieve_source;
	}

	/**
	* 登录前处理方法，主要用于登录页面是否需要验证码的判断
	* @throws Exception
	*/
	public String preLogin() throws Exception {
		Cookie[] cookies = request.getCookies(); 
		if(cookies != null) {
			for(int i=0;i<cookies.length;i++){
				if(cookies[i].getName().equals("www.tiancai.com")){  
					setLoginName(URLDecoder.decode(cookies[i].getValue(), "UTF-8"));
					request.setAttribute("rememberUsernameDiv", 1);
				}   
			}   
		}
		int errorLoginTimes = 0;
		if(request.getSession().getAttribute("errorLoginTimes") != null) {
			errorLoginTimes = (Integer)request.getSession().getAttribute("errorLoginTimes");
		} 
		if (errorLoginTimes >= 3) {
			request.setAttribute("checkcodeDiv", 1);
		}
		return SUCCESS;
	}
	
	/**
	* 登录方法
	* @return
	*/
	public String login() {
		//登录失败计数器
		int errorLoginTimes = 0;
		
		if(request.getSession().getAttribute("errorLoginTimes") != null) {
			errorLoginTimes =(Integer)request.getSession().getAttribute("errorLoginTimes");
		}
		
		try {
			if(checkcode == null) {
				User loginUser = userService.authLogin(loginName, loginPassword);
				if(loginUser != null) {
					UserCookie userCookie = new UserAdapter(loginUser);
					userService.setLoginTimes(loginUser.getUserid());
					request.getSession().setAttribute("userCookie", userCookie);
				}
			} else {
				if(request.getSession().getAttribute("loginCheckcode").toString().toLowerCase().equals(checkcode.toLowerCase())) {
					User loginUser = userService.authLogin(loginName, loginPassword);
					if(loginUser != null) {
						UserCookie userCookie = new UserAdapter(loginUser);
						userService.setLoginTimes(loginUser.getUserid());
						request.getSession().setAttribute("userCookie", userCookie);
					}
				} else {
					//验证码不正确!
					request.setAttribute("errorInfoDiv", 1);
				}
			}
		} catch (UserServiceException e) {
			e.printStackTrace();
			return ERROR;
		} catch (UsernameLockException e) {
			//用户被锁定!
			request.setAttribute("errorInfoDiv", 2);
		} catch (InvalidUsernamePasswordException e) {
			//用户名或者密码无效!
			request.setAttribute("errorInfoDiv", 3);
		} catch (DaoException e) {
			e.printStackTrace();
			return ERROR;
		}
		
		//判断登录验证错误次数，当错误超过3次时出现验证码。
		//登录成功后清除session中的错误次数。
		if(request.getAttribute("errorInfoDiv") != null) {
			errorLoginTimes = errorLoginTimes + 1;
			request.getSession().setAttribute("errorLoginTimes", errorLoginTimes);
			if(errorLoginTimes >= 3) {
				request.setAttribute("checkcodeDiv", 1);
			}
			return INPUT;
		} else {
			isRememberUsername(rememberUsername);
			request.getSession().removeAttribute("errorLoginTimes");
		}
		/**
		 * Modified By Hikin Yao 2010-02-24
		 * 
		 */
		if(null!=request.getSession().getAttribute("loginRedirectUrl")){
			loginRedirectUrl=(String)request.getSession().getAttribute("loginRedirectUrl");
		}
		return SUCCESS;
	}
	
	/**
	* 登录方法json
	* @return
	*/
	public String loginJson() {
		//登录失败计数器
		int errorLoginTimes = 0;
		
		if(request.getSession().getAttribute("errorLoginTimes") != null) {
			errorLoginTimes =(Integer)request.getSession().getAttribute("errorLoginTimes");
		}
		
		try {
			if(checkcode == null) {
				User loginUser = userService.authLogin(loginName, loginPassword);
				if(loginUser != null) {
					UserCookie userCookie = new UserAdapter(loginUser);
					userService.setLoginTimes(loginUser.getUserid());
					request.getSession().setAttribute("userCookie", userCookie);
					generateResult(1, "登录成功!", userCookie);
				}
			} else {
				if(request.getSession().getAttribute("loginCheckcode").toString().toLowerCase().equals(checkcode.toLowerCase())) {
					User loginUser = userService.authLogin(loginName, loginPassword);
					if(loginUser != null) {
						UserCookie userCookie = new UserAdapter(loginUser);
						userService.setLoginTimes(loginUser.getUserid());
						request.getSession().setAttribute("userCookie", userCookie);
						generateResult(1, "登录成功!", userCookie);
					}
				} else {
					//验证码不正确!
					generateResult(2, "验证码不正确!", null);
				}
			}
		} catch (UserServiceException e) {
			e.printStackTrace();
			return ERROR;
		} catch (UsernameLockException e) {
			generateResult(3, "用户被锁定!", null);
		} catch (InvalidUsernamePasswordException e) {
			generateResult(4, "无效的用户名或密码!", null);
		} catch (DaoException e) {
			e.printStackTrace();
			return ERROR;
		}
		
		//判断登录验证错误次数，当错误超过3次时出现验证码。
		//登录成功后清除session中的错误次数。
		if(this.result.getCode() != 1) {
			errorLoginTimes = errorLoginTimes + 1;
			request.getSession().setAttribute("errorLoginTimes", errorLoginTimes);
			if(errorLoginTimes >= 3) {
				generateResult(5, result.getMessage(), null);
			}
		} else {
			isRememberUsername(rememberUsername);
			request.getSession().removeAttribute("errorLoginTimes");
		}
		
		return SUCCESS;
	}
	
	/**
	 * 判断是否登录
	 * @return
	 */
	public String isLoginJson() {
		if(request.getSession().getAttribute("userCookie") == null) {
			generateResult(0, "没有登录", null);
		} else {
			generateResult(1, "已经登录", null);
		}
		return SUCCESS;
	}
	
	/**
	 * 获得cookie中保存的用户名
	 * @return
	 */
	public String getUsernameJson() {
		try {
			String username = null;
			Cookie[] cookies = request.getCookies(); 
			if(cookies != null) {
				for(int i=0;i<cookies.length;i++){
					if(cookies[i].getName().equals("www.tiancai.com")){
						username = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
					}   
				}   
			}
			generateResult(1, "用户名", username);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 获得当前用户资金余额
	 * @return
	 */
	public String getFundJson() {
		try {
			Long userId = ((UserCookie) request.getSession().getAttribute("userCookie")).getUserId();
			BigDecimal money = userService.getFund(userId);
			generateResult(1, "资金", money);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 获得当前用户冻结资金，彩金，积分等信息
	 * @return
	 */
	public String getPayInfoJson() {
		try {
			if(null ==request.getSession().getAttribute("userCookie") ) return "SUCCESS";
			Long userId = ((UserCookie) request.getSession().getAttribute("userCookie")).getUserId();
			String username = ((UserCookie) request.getSession().getAttribute("userCookie")).getUsername();
			Map data = userService.getPayInfo(userId);
			data.put("myUsername", username);
			generateResult(1, "用户冻结资金，彩金，积分等信息", data);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 是否记住用户名
	 */
	public void isRememberUsername(String remUsername){
		try {
			if(remUsername != null) {
				//如果勾选记住密码，则保存cookie，如果没有勾选，则下次登录时不记住用户名
				if(remUsername.equals("1")) {
					Cookie cookie = new Cookie("www.tiancai.com",URLEncoder.encode(loginName,"utf-8"));   
					cookie.setPath("/");//这里的/为客户机上cookie保存的默认路径   
					cookie.setMaxAge(Integer.MAX_VALUE);//生命周期
					response.addCookie(cookie);  
				} else {
					Cookie[] cookies = request.getCookies(); 
					if(cookies != null) {
						for(int i=0;i<cookies.length;i++){
							if(cookies[i].getName().equals("www.tiancai.com")){  
								cookies[i].setMaxAge(0);
								cookies[i].setPath("/");
								response.addCookie(cookies[i]);
							}   
						}   
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* 登录成功后跳转方法，此方法需要做isLogin的验证。
	* @return
	*/
	public String index() {
		//isLogin();
		if(request.getSession().getAttribute("userCookie") == null) {
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**
	* 注销登录
	* @return
	*/
	public String logout() {
		request.getSession().removeAttribute("userCookie");
		
		/**
		 * Modified By Hikin Yao 2010-02-24
		 */
		request.getSession().removeAttribute("loginRedirectUrl");
		return SUCCESS;
	}
	
	/**
	* ajax注销登录
	* @return
	*/
	public String logoutJson() {
		request.getSession().removeAttribute("userCookie");
		
		/**
		 * Modified By Hikin Yao 2010-02-24
		 */
		request.getSession().removeAttribute("loginRedirectUrl");
		generateResult(1, "注销登录", null);
		return SUCCESS;
	}
    
	//ajax校验用户名
	public String checkLoginName() {
		try {
			//用户名校验，长度
			boolean tag1 = false;
			if(loginName.getBytes().length >= 4 && loginName.getBytes().length <= 16) {
				tag1 = true;
			}
			//用户名校验，数字，英文和汉字
			boolean tag2 = Pattern.matches("[0-9a-zA-Z\u4E00-\u9FA5]+", loginName);
			if(tag1 && tag2) {
				if(userService.isExistUser(loginName)) {
					generateResult(1, "用户名已存在", null);
				} else {
					generateResult(0, "恭喜，用户名可用", null);
				}
			} else {
				generateResult(2, "用户名不合法", null);
			}
		} catch (Exception e) {
			generateResult(3, "服务器忙..", null);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//ajax校验邮箱
	public String checkEmail() {
		try {
			boolean tag1 = Pattern.matches("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+", email);
			if(tag1) {
				if(userService.isExistEmail(email)) {
					generateResult(1, "邮箱已存在", null);
				} else {
					generateResult(0, "恭喜，该邮箱可用", null);
				}
			} else {
				generateResult(2, "邮箱名不合法", null);
			}
		} catch (Exception e) {
			generateResult(3, "服务器忙..", null);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//ajax校验验证码
	public String checkCheckcode() {
		if(request.getSession().getAttribute("regCheckcode")!= null && checkcode != null) {
			if(request.getSession().getAttribute("regCheckcode").toString().toLowerCase().equals(checkcode.toLowerCase())) {
				generateResult(0, "验证码正确", null);
			} else {
				generateResult(1, "验证码错误", null);
			}
		} else {
			generateResult(1, "验证码错误", null);
		}
		return SUCCESS;
	}
	
	/**
	* 注册前处理方法，主要用于注册次数判断，注册次数超过3次
	* @return
	*/
	public String preRegister() {
		String ip = request.getRemoteAddr();
		int registerTimes = 0;
		
		try {
			Long times = securityIpService.getTimes(ip);
			if(times != null) {
				registerTimes = times.intValue();
			}
		} catch (DaoException e) {
			e.printStackTrace();
			return ERROR;
		}
		
		if (registerTimes >= 3) {
			request.setAttribute("checkcodeDiv", 1);
		}
		return SUCCESS;
	}
	
	/**
	* 注册
	* @return
	*/
	public String register() {
		try {
			if(userService.isExistUser(loginName)) {
				request.setAttribute("regError", 1);//用户名已经存在
				return INPUT;
			}
			if(!loginPassword.equals(rePassword)) {
				request.setAttribute("regError", 2);//密码与确认密码不一致
				return INPUT;
			}
			if(!Pattern.matches("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+", email)) {
				request.setAttribute("regError", 3);//Email不合法
				return INPUT;
			}
			if(userService.isExistEmail(email)) {
				request.setAttribute("regError", 4);//Email已经存在
				return INPUT;
			}
			if(checkcode != null) {
				if(!request.getSession().getAttribute("regCheckcode").toString().toLowerCase().equals(checkcode.toLowerCase())) {
					request.setAttribute("regError", 5);//验证码不正确
					return INPUT;
				}
			}
			User user = new User();
			user.setUsername(loginName);
			user.setLoginPassword(loginPassword);
			user.setEmail(email);
			user.setRegIp(request.getRemoteAddr());
			user.setRegTime(DateUtil.getCurrentDate());
			userService.register(user);
			
			//注册后直接帮用户登录
			UserCookie userCookie = new UserAdapter(user);
			request.getSession().setAttribute("userCookie", userCookie);
			
			//记录当前IP当天的注册次数
			securityIpService.setTimes(request.getRemoteAddr());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	//预发送邮件
	public String  preSendPLInfoToMail(){
		user = null;
		return "preSendMailForGetPassword";
	}
	//发送邮件
	public String  sendLPInfoToUserMail(){
		String email = user.getEmail();
		User tempUser = userService.findUniqueUserByFiled("email", email);
		if(null == tempUser){
			return "EmailNull";
		}
		String userInfo = "";
		try {
			userInfo = EncryptionUtil.encrypt(tempUser.getUserid()+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Object> args = new java.util.ArrayList<Object>();
		args.add(0, "***");
		args.add(1, getText("LOTTERY_DOMAIN"));
		args.add(2, userInfo);
		StringBuilder message_body = new StringBuilder();
		message_body.append(getText("PASSWORD_MESSAGE_LOGO"));
		message_body.append(getText("LOTTERY_DOMAIN"));
		message_body.append(getText("PASSWORD_MESSAGE_BODY", args));
		message_body.append(getText("PASSWORD_MESSAGE_FOOT"));
		String message_subject = getText("PASSWORD_MESSAGE_HEAD");
		
		try {
			userService.sendEmail(message_subject, message_body.toString(), user.getEmail());
		} catch (UserServiceException e) {
			e.printStackTrace();
			return "sendEmalFaild";
		}
		message_body = null;
		args = null ;
		return "sendEmalSuccess";
	}
	
	public String preBackLP() throws Exception {
		user = userService.findUserByUserId(Long.parseLong(EncryptionUtil.decrypt(this.getAccountId())));
		if(null == user)
			return "preBackPasswordEroor";
		return "preBackPassword";
	}
	
	/**
	 * 跳转到输入用户名页面
	 * */
	public String  retrieveLP(){
		return "retrieveLP1";
	}
	
	/**
	 * 跳转到选择密码取回方式页面
	 * */
	public String  retrieveLP1(){
		if(!"1".equals(validatorUserNameIsExist())){
			return "retrieveLP1";
		}
		if(retrieve_source.equals("reTip"))
			return "retrieveTip2";
		return "retrieveLP2";
	}
	
	/**
	 * 1 答案取回
	 * 2 邮箱取回
	 * */
	public String  retrieveLP2(){
		User tempUser = userService.findUniqueUserByFiled("userid", Long.parseLong(accountId));
		if("2".equals(retrieve_lp_fashion)){
			String u_email = tempUser.getEmail();
			int email_index = u_email.indexOf("@");
			this.setPostfixEmail(u_email.substring(email_index,u_email.length()));
			return "retrieveLP32";
		}
		passAnswerTip = userService.findUserPassAnswer(tempUser.getPasswordTip(), Constants.PASS_ANSWER);
		if(null == passAnswerTip){
			generateResult(5, "", null);
			return "retrieveLP2";
		}
		return "retrieveLP31";
	}
	
	/**
	 * 答案设置密码
	 * */
	public String  retrieveLP31(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("checkcode_session", (String)request.getSession().getAttribute("checkcode"));
		param.put("userId", Long.parseLong(accountId));
		param.put("checkcode", checkcode);
		param.put("passwordAnswer", user.getPasswordAnswer());
		int flag = userService.retrieveLP31(param);
		if(1 != flag ){
			generateResult(flag, "", null);
			User tempUser = userService.findUniqueUserByFiled("userid", Long.parseLong(accountId));
			passAnswerTip = userService.findUserPassAnswer(tempUser.getPasswordTip(), Constants.PASS_ANSWER);
			return "retrieveLP31";
		}
		setAccountId(EncryptionUtil.encrypt(Long.parseLong(accountId)+""));
		param = null;
		result = null;
		if(("reWithdraw").equals(retrieve_source)){
			return "retrieveWithdraw5";
		}
		return "retrieveLP5";
	}
	
	
	/**
	 * 发送邮件（重置密码）
	 * */
	public String  retrieveLP32(){
		String email_result = this.validatorUserEmailIsInvalid();
		if("0".equals(email_result)) return "retrieveLP32";
		Map<String,Object> param = null;
		try {
			param = new HashMap<String, Object>();
			param.put("email_gif_path", request.getSession().getServletContext().getRealPath("images/email/"));
			int port = request.getLocalPort();
			String url = "http://" + request.getServerName() + (port == 80 ? "" : ":"+port ) + request.getContextPath()+
						"/user/mail/resetPL/" + retrieve_source + "/" + EncryptionUtil.encrypt(accountId) + ".net";
			param.put("username", userService.findUniqueUserByFiled("userid", Long.parseLong(accountId)).getUsername());
			param.put("url", url);
			param.put("sendTo", prefixEmail+postfixEmail);
			Boolean b = SendHtmlEmail.resetPass(param);
			if(false == b){
				generateResult(0, "", null);
				return "retrieveLP32";
			}
			this.setReEmail(prefixEmail+postfixEmail);
		} catch (Exception e) {
			generateResult(0, "", null);
			return "retrieveLP32";
		}
		return "retrieveLP42";
	}
	
	/**
	 * 邮件修改密码
	 * */
	public String  resetPL(){
		if(StringUtils.isBlank(accountId))
			return "input";
		Long u_id = parseUserId(accountId);
		if(-1L == u_id )
			return "input";
		User userTemp = userService.findUserByUserId(u_id);
		if(null == userTemp)
			return "input";
		if(("reTip").equals(retrieve_source)){
			passAnswerList = userService.findAllPassAnswer();
			return "retrieveTip5";
		}else if(("reWithdraw").equals(retrieve_source)){
			return "retrieveWithdraw5";
		}
		return "retrieveLP5";
	}
	
	/**
	 * 更新用户密码
	 * */
	public String  retrieveLP5(){
		if(null == retrieve_source)
			return "input";
		if(!("reTip").equals(retrieve_source) && !"1".equals(validatorUserLPIsInvalid())){
			if(("reWithdraw").equals(retrieve_source))
				return "retrieveWithdraw5";
			return "retrieveLP5";
		}
		if(("reTip").equals(retrieve_source)){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userId", accountId);
			param.put("newPassAnswerTipCode", newPassAnswerTipCode);
			param.put("passAnswer", passAnswer.trim());
			userService.updateUserByParam(param);
			return "retrieveTip6";
		}
		try {
			userService.saveUser(user);
		} catch (DaoException e) {
			e.printStackTrace();
			return "input";
		}
		if(("reWithdraw").equals(retrieve_source))
			return "retrieveWithdraw6";
		return "retrieveLP6";
	}
	
	//验证该用户是否存在
	private String validatorUserNameIsExist(){
		if(StringUtils.isBlank(loginName)||loginName.getBytes().length < 4 
				|| loginName.getBytes().length > 16)
			return "0";
		//用户名校验，数字，英文和汉字
		if(Pattern.matches("[0-9a-zA-Z\u4E00-\u9FA5]+", loginName)) {
			User tempUser = userService.findUniqueUserByFiled("username", loginName);
				if(null != tempUser) {
					generateResult(1, "该用户可用！", null);
					accountId = tempUser.getUserid()+"";
					if(("reWithdraw").equals(retrieve_source) && null==tempUser.getWithdrawPwd()){
						generateResult(3, "该用户还没有设置取款密码！", null);
						return "3";
					}
					return "1";
				} else {
					generateResult(0, "该用户不存在！", null);
					return "0";
				}
		} else {
			generateResult(2, "用户名不合法", null);
			return "0";
		}
	}
	
	//验证邮箱是否可用
	private String validatorUserEmailIsInvalid(){
		if(StringUtils.isBlank(prefixEmail)||StringUtils.isBlank(postfixEmail)){
			generateResult(0, "该邮箱格式不正确！", null);
			return "0";
		}
		User tempUser = userService.findUniqueUserByFiled("email", prefixEmail+postfixEmail);
		if(null == tempUser){
			generateResult(2, "邮箱不存在！", null);
			return "0";
		}
		if(!tempUser.getUserid().equals(Long.valueOf(accountId))){
			generateResult(4, "该邮箱没有注册！", null);
			return "0";
		}
		generateResult(1, "邮箱可用！", null);
		return tempUser.getUsername();
	}
	
	//验证密码是否可修改
	private String validatorUserLPIsInvalid(){
		String pass = loginPassword;
		if(StringUtils.isBlank(pass)){
			generateResult(0, "密码为空", null);
			return "0";
		}
		Long u_id = parseUserId(accountId);
		if(-1L == u_id )
			return "0";
		User temp_user = userService.findUserByUserId(u_id);
		if(null == temp_user)
			return "0";
		//密码相同，则返回提示用户
		String md5_pass = Util.MD5(pass);
		if(("rePass").equals(retrieve_source) && temp_user.getLoginPassword().equals(md5_pass)){
			generateResult(2, "密码没有变动", null);
			return "2";
		}
		generateResult(1, "密码格式正确!", null);
		if(("rePass").equals(retrieve_source))
			temp_user.setLoginPassword(md5_pass);
		else if(("reWithdraw").equals(retrieve_source)){
			temp_user.setWithdrawPwd(md5_pass);
		}
		user = temp_user;
		return "1";
		
	}
	
	@SuppressWarnings("unused")
	private boolean sendEmail(String subject, String body, String to) {
		return EmailFactory.getInstance("email.xml").send(subject, body, to);
	}
	
	/**
	 * 用户ID解码
	 * */
	private Long parseUserId(String accountId){
		Long u_id = null;
		try {
			u_id = Long.parseLong(EncryptionUtil.decrypt(accountId));
		} catch (Exception e) {
			return -1L;
		}
		return u_id;
	}
	
	public String preUpdateUserInfo(){
		UserCookie userCookie = (UserCookie)request.getSession().getAttribute(Constants.USERCOOKIE);
		user = userService.findUserByUserId(userCookie.getUserId());
		List<Code> p_list = userService.findAllProvince();
		if(StringUtils.isNotBlank(user.getProvince())){
			Code p = userService.findCodeByName(user.getProvince());
			p_list.remove(p);
			this.setProvinceId(p.getId()+"");
			cityList = userService.findAllCityByProvince(p.getId());
			/*if(StringUtils.isNotBlank(user.getCity())){
				Code c = userService.findCodeByName(user.getCity());
				cityList.remove(c);
			}*/
		}
			
		this.setProvinceList(p_list);
		return SUCCESS;
	}
	
	public void updateEmailUserInfo(){
		UserCookie userCookie = (UserCookie)request.getSession().getAttribute(Constants.USERCOOKIE);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userCookie.getUserId());
		param.put("email", email);
		param.put("loginPassword", loginPassword);
		int flag = userService.updateEmailUserInfo(param);
		ajaxForAction(flag+"");
	}
	
	
	public void updateUserInfo(){
		UserCookie userCookie = (UserCookie)request.getSession().getAttribute(Constants.USERCOOKIE);
		int flag = userService.updateUserInfo(user,userCookie.getUserId());
		ajaxForAction(flag+"");
	}
	
	public String preUpdateLPUserInfo(){
		UserCookie userCookie = (UserCookie)request.getSession().getAttribute(Constants.USERCOOKIE);
		User tempUser = userService.findUserByUserId(userCookie.getUserId());
		if(StringUtils.isNotBlank(tempUser.getPasswordTip()))
			this.setPassAnswerTip(userService.findUserPassAnswer(tempUser.getPasswordTip(), 
					Constants.PASS_ANSWER));
		passAnswerList = userService.findAllPassAnswer();
		if(null != passAnswerTip)
			passAnswerList.remove(passAnswerTip);
		return "preUpdateLPUserInfo";
	}
	
	public void updateLPUserInfo(){
		result = null;
		UserCookie userCookie = (UserCookie)request.getSession().getAttribute(Constants.USERCOOKIE);
		ajaxForAction(userService.updateLPUserInfo(userCookie.getUserId(), loginPassword, rePassword)+"");
	}
	
	public void updateAnswerUserInfo(){
		UserCookie userCookie = (UserCookie)request.getSession().getAttribute(Constants.USERCOOKIE);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userCookie.getUserId());
		param.put("newPassAnswerTipCode", newPassAnswerTipCode);
		param.put("passAnswer", passAnswer);
		param.put("newPassAnswer", newPassAnswer);
		ajaxForAction(userService.updateAnswerUserInfo(param)+"");
		param = null;
	}
	
	public String preUpdateBankUserInfo(){
		UserCookie userCookie = (UserCookie)request.getSession().getAttribute(Constants.USERCOOKIE);
		User u = userService.findUserByUserId(userCookie.getUserId());
		rememberUsername = u.getName();
		reEmail = u.getIsEmailBind();
		virtualAccount = userService.findVirtualAccountByUserId(userCookie.getUserId());
		provinceList = userService.findAllProvince();
		Code code = null;
		if(StringUtils.isNotBlank(virtualAccount.getProvince())){
			for(Code c :provinceList){
				if(c.getName().equals(virtualAccount.getProvince())){
					code = c;
				}
			}
			provinceList.remove(code);
			this.setProvinceId(code.getId()+"");
			cityList = userService.findAllCityByProvince(code.getId());
			if(StringUtils.isNotBlank(virtualAccount.getCity())){
				for(Code c :cityList){
					if(c.getName().equals(virtualAccount.getCity())){
						code = c;
					}
				}
				cityList.remove(code);
			}
		}
		return "updateUserBank";
	}
	
	//设置和修改银行
	public void updateBankUserInfo(){
		UserCookie userCookie = (UserCookie)request.getSession().getAttribute(Constants.USERCOOKIE);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("rememberUsername",rememberUsername);
		param.put("userId", userCookie.getUserId());
		param.put("bankCode", virtualAccount.getBankCode());
		param.put("province", virtualAccount.getProvince());
		param.put("city", virtualAccount.getCity());
		param.put("cardNum", virtualAccount.getCardNum());
		ajaxForAction(userService.updateBankUserInfo(param)+"");
		param = null;
	}
	
	//设置和修改取款密码
	public void updateBankPassUserInfo(){
		UserCookie userCookie = (UserCookie)request.getSession().getAttribute(Constants.USERCOOKIE);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userCookie.getUserId());
		param.put("email", email);
		param.put("withdrawPwd", user.getWithdrawPwd());
		param.put("loginPassword", loginPassword);
		ajaxForAction(userService.updateBankPassUserInfo(param)+"");
		param = null;
		
	}
}
