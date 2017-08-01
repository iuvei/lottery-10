package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;
import com.wintv.framework.utils.DateUtil;
/**
 * 
 * 网站用户表
 *
 */
@SuppressWarnings("serial")
public class User implements Serializable {
    private Long userid;//用户ID
	private String username;//用户名
	private String email;//邮件
	private String loginPassword;//网站登录密码
	private String idCard;    // 身份证号
	private String sex;       // 性别
	private String tel;       // 联系电话
	private String province;  // 省份
	private String city;      // 城市
	private Date birthday;    //出生日期
	private String userGrade;//用户等级
	private String withdrawPwd;//取款密码
	private String name;//真实姓名
	private String passwordTip;//当密码忘记时:密码提示
	private String passwordAnswer;//当密码忘记时:密码答案
	private String mp;//手机
	private Long militaryScore;//心水战绩
	private String status;//用户状态  3是未激活
	private String regIp;//注册IP地址
	private Long loginCnt;//登录次数
	private String qq;//QQ
	private Date regTime;//注册时间
	private String isEmailBind;//邮箱是否绑定
	private String isOnline;//判断用户是否在线
	private String isCompleteInfo;
    private Long betMilitary;//投注战绩
    private Long attentionCnt;//关注人数
    private String title;//头衔
    private String signature;//个人中心的个人签名
	public Long getBetMilitary() {
		return betMilitary;
	}

	public void setBetMilitary(Long betMilitary) {
		this.betMilitary = betMilitary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	 
	public User() {
	}
	 
	public User(String username, String email, String loginPassword,
			String userGrade, String withdrawPwd, String name,
			Long militaryScore) {
		this.username = username;
		this.email = email;
		this.loginPassword = loginPassword;
		this.userGrade = userGrade;
		this.withdrawPwd = withdrawPwd;
		this.name = name;
		this.militaryScore = militaryScore;
	}

	 
	public User(String status, String username, String email,
			String loginPassword, String userGrade, String withdrawPwd,
			String name, String passwordTip, String passwordAnswer, String mp,
			Long militaryScore) {
		this.status = status;
		this.username = username;
		this.email = email;
		this.loginPassword = loginPassword;
		this.userGrade = userGrade;
		this.withdrawPwd = withdrawPwd;
		this.name = name;
		this.passwordTip = passwordTip;
		this.passwordAnswer = passwordAnswer;
		this.mp = mp;
		this.militaryScore = militaryScore;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday ;
	}

	public String getUserGrade() {
		return this.userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswordTip() {
		return this.passwordTip;
	}

	public void setPasswordTip(String passwordTip) {
		this.passwordTip = passwordTip;
	}

	public String getPasswordAnswer() {
		return this.passwordAnswer;
	}

	public void setPasswordAnswer(String passwordAnswer) {
		this.passwordAnswer = passwordAnswer;
	}

	public String getMp() {
		return this.mp;
	}

	public void setMp(String mp) {
		this.mp = mp;
	}

	public Long getMilitaryScore() {
		return this.militaryScore;
	}

	public void setMilitaryScore(Long militaryScore) {
		this.militaryScore = militaryScore;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public Long getLoginCnt() {
		return loginCnt;
	}

	public void setLoginCnt(Long loginCnt) {
		this.loginCnt = loginCnt;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getIsEmailBind() {
		return isEmailBind;
	}

	public void setIsEmailBind(String isEmailBind) {
		this.isEmailBind = isEmailBind;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getWithdrawPwd() {
		return withdrawPwd;
	}

	public void setWithdrawPwd(String withdrawPwd) {
		this.withdrawPwd = withdrawPwd;
	}

	public String getIsCompleteInfo() {
		return isCompleteInfo;
	}

	public void setIsCompleteInfo(String isCompleteInfo) {
		this.isCompleteInfo = isCompleteInfo;
	}

	public Long getAttentionCnt() {
		return attentionCnt;
	}

	public void setAttentionCnt(Long attentionCnt) {
		this.attentionCnt = attentionCnt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	
}