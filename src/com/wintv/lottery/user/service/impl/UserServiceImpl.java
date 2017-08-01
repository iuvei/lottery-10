package com.wintv.lottery.user.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.common.Constants;
import com.wintv.framework.common.DictionaryDao;
import com.wintv.framework.dao.AreaDao;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.InvalidUsernamePasswordException;
import com.wintv.framework.exception.UserServiceException;
import com.wintv.framework.exception.UsernameAlreadyExistException;
import com.wintv.framework.exception.UsernameLockException;
import com.wintv.framework.pojo.Code;
import com.wintv.framework.pojo.Dictionary;
import com.wintv.framework.pojo.User;
import com.wintv.framework.pojo.VirtualAccount;
import com.wintv.framework.utils.PropertyCopyUtil;
import com.wintv.framework.utils.Util;
import com.wintv.framework.utils.mail.EmailFactory;
import com.wintv.lottery.pay.dao.VirtualAccountDao;
import com.wintv.lottery.user.dao.UserDao;
import com.wintv.lottery.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private VirtualAccountDao virtualAccountDao;
	@Autowired
	private DictionaryDao dictionaryDao;
	@Autowired
	private AreaDao areaDao;
	
	@Override
	public User authLogin(String user, String pswd)
			throws UserServiceException, UsernameLockException,
			InvalidUsernamePasswordException {
		return this.userDao.authLogin(user, Util.MD5(pswd));
	}

	public boolean isExistUser(String username) throws DaoException {
		return this.userDao.isExistUser(username);
	}

	public boolean lockUser(Map params) throws DaoException, SQLException {
		Long userid = (Long) params.get("userid");
		String status = (String) params.get("status");
		User user = this.userDao.loadUser(userid);
		user.setStatus(status);
		this.userDao.updateUser(user);
		return false;
	}

	/**
	 * 注册用户 参数: user:用户注册信息 UserServiceException:用户接口系统异常,例如数据库操作异常等
	 * 返回:注册后返回的完整用户信息
	 */
	@Transactional(rollbackFor = UserServiceException.class)
	public User register(User user) throws UserServiceException,
			UsernameAlreadyExistException {
		try {
			String username = user.getUsername();
			boolean isExist = this.userDao.isExistUser(username);
			if (isExist) {
				throw new UsernameAlreadyExistException("用户名" + username
						+ "已经注册");
			}
			user.setLoginPassword(Util.MD5(user.getLoginPassword()));
			Long userid = this.userDao.saveUser(user);
			user.setUserid(userid);
			
			Dictionary dictionary= dictionaryDao.loadDictionary("REG_POINT", "USER");
			Long point = Long.parseLong(dictionary.getValue());
			
			Map params = new HashMap();
			params.put("user", user);
			params.put("point", point);
			this.virtualAccountDao.createVAAccount(params);
		} catch (DaoException e) {
			throw new UserServiceException(e.getLocalizedMessage());
		}

		return user;
	}

	public User findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDao.findByUsername(userName);
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userDao.findAllUser();
	}
	@Transactional
	public void saveUser(User user) throws DaoException {
		userDao.store(user);
	}
	@Transactional
	public void delete(Long userid) {
		// TODO Auto-generated method stub
		userDao.delete(userid);
	}
	
	@Override
	public User findUniqueUserByFiled(String filedName, Object filedValue) {
		// TODO Auto-generated method stub
		return userDao.findUniqueBy(filedName,filedValue);
	}

	@Override
	public void sendEmail(String message_subject ,String message_body ,String send_to) throws UserServiceException{
		EmailFactory.getInstance("email.xml").send(message_subject, message_body, send_to);
	}

	@Override
	public User findUserByUserId(Long userid) {
		return userDao.read(userid);
	}

	@Override
	public boolean activateAccount(Integer key) {
		return userDao.updateUserStatus("3",key) > 0;
	}

	@Override
	public boolean isExistEmail(String email) throws DaoException {
		return userDao.isExistEmail(email);
	}

	@Override
	public boolean authWithdrawPassword(Long userid, String pswd) throws DaoException {
		return this.userDao.authWithdrawPassword(userid, Util.MD5(pswd));
	}

	@Override
	public List<Dictionary> findAllPassAnswer() {
		return userDao.findAllPassAnswer();
	}

	@Override
	public List<Code> findAllProvince() {
		return areaDao.findAllProvince();
	}
	@Override
	public List<Code> findAllCityByProvince(Long provId){
		return areaDao.findCityByProvID(provId);
	}
	
	@Override
	public int retrieveLP31(Map<String,Object> param) {
		User tempUser = userDao.read((Long)param.get("userId"));
		if(null == param.get("passwordAnswer"))return 0;
		if(!((String)param.get("checkcode_session")).equals((String)param.get("checkcode")))return 2;
		if(!((String)param.get("passwordAnswer")).equals(tempUser.getPasswordAnswer()))return 3;
		return 1;
	}
	
	
	@Override
	@Transactional
	public int updateEmailUserInfo(Map<String, Object> param) {
		Long userId = Long.parseLong(param.get("userId")+"");
		String email = (String)param.get("email");
		String pass = (String)param.get("loginPassword");
		User u0 = userDao.findUniqueBy("email", email);
		User user = userDao.read(userId);
		if(u0!=null && !userId.equals(u0.getUserid()))
			return 1;
		if(StringUtils.isBlank(pass))
			return 2;
		else if(!Util.MD5(pass).equals(user.getLoginPassword()))
			return 3;
		try {
			user.setEmail(email);
			userDao.saveUser(user);
		} catch (DaoException e) {
			e.printStackTrace();
			return 2;
		}
		return 4;
	}

	@Override
	@Transactional
	public int  updateUserInfo(User user, Long userId) {
		User oldUser = userDao.read(userId);
		if(StringUtils.isNotBlank(user.getProvince()))
			oldUser.setProvince(areaDao.findUniqueBy("id", Long.parseLong(user.getProvince())).getName());
		if(StringUtils.isNotBlank(user.getCity()))
			oldUser.setCity(user.getCity());
		if(StringUtils.isBlank(oldUser.getName()) && StringUtils.isNotBlank(user.getName()))
			oldUser.setName(user.getName());
		if(StringUtils.isBlank(oldUser.getIdCard()) && StringUtils.isNotBlank(user.getIdCard()))
			oldUser.setIdCard(user.getIdCard());
		String[] strs= new String[]{"mp","tel","qq","sex","birthday"};
		PropertyCopyUtil.copyProperties(user, oldUser, strs);
		strs = null;
		userDao.store(oldUser);
		//完善用户资料送100分
		if("1".equals(oldUser.getIsCompleteInfo()))
			return 1;
		boolean completeInfo = true;
		if(StringUtils.isBlank(oldUser.getName()))completeInfo = false;
		if(StringUtils.isBlank(oldUser.getIdCard()))completeInfo = false;
		if(StringUtils.isBlank(oldUser.getMp()))completeInfo = false;
		if(StringUtils.isBlank(oldUser.getTel()))completeInfo = false;
		if(StringUtils.isBlank(oldUser.getQq()))completeInfo = false;
		if(StringUtils.isBlank(oldUser.getSex()))completeInfo = false;
		if(null == oldUser.getBirthday())completeInfo = false;
		if(StringUtils.isBlank(oldUser.getProvince()))completeInfo = false;
		if(StringUtils.isBlank(oldUser.getCity()))completeInfo = false;
		if(completeInfo){
			Dictionary d = dictionaryDao.loadDictionary("1","COMPLETE_USERI");
			VirtualAccount virtualAccount = virtualAccountDao.findUniqueBy("txUserId", userId);
			virtualAccount.setPoint(virtualAccount.getPoint()+Long.parseLong(d.getValue()));
			virtualAccountDao.store(virtualAccount);
		}
		oldUser.setIsCompleteInfo("1");
		userDao.store(oldUser);
		return 1;
	}

	@Override
	public Code findCodeByName(String name) {
		// TODO Auto-generated method stub
		return areaDao.findUniqueBy("name", name);
	}

	@Override
	public Dictionary findUserPassAnswer(String code, String type) {
		// TODO Auto-generated method stub
		return dictionaryDao.loadDictionary(code,type);
	}

	
	@Override
	@Transactional
	public int updateLPUserInfo(Long userId, String old_pass, String new_pass) {
		if(StringUtils.isBlank(old_pass))
			return 0;
		User user = userDao.read(userId);
		if(!user.getLoginPassword().equals(Util.MD5(old_pass)))
			return 1;
		if(StringUtils.isBlank(new_pass))
			return 2;
		user.setLoginPassword(Util.MD5(new_pass));
		userDao.store(user);
		return 3;
	}

	@Override
	@Transactional
	public int updateAnswerUserInfo(Map<String, Object> param) {
		User user = userDao.read((Long)param.get("userId"));
		if(StringUtils.isNotBlank((String)param.get("newPassAnswer")) && StringUtils.isNotBlank(user.getPasswordAnswer())&&
				!user.getPasswordAnswer().equals((String)param.get("passAnswer")))
			return 0;
		user.setPasswordAnswer((String)param.get("newPassAnswer"));
		user.setPasswordTip((String)param.get("newPassAnswerTipCode"));
		userDao.store(user);
		return 1;
	}

	@Override
	public VirtualAccount findVirtualAccountByUserId(Long userId) {
		return virtualAccountDao.findUniqueBy("txUserId", userId);
	}

	@Override
	@Transactional
	public int updateBankUserInfo(Map<String, Object> param) {
		if(StringUtils.isBlank((String)param.get("province")))return 0;
		if(StringUtils.isBlank((String)param.get("bankCode")))return 0;
		VirtualAccount virtualAccount = virtualAccountDao.findUniqueBy("txUserId", (Long)param.get("userId"));
		Dictionary d =  dictionaryDao.loadDictionary((String)param.get("bankCode"), Constants.DICTIONARY_TYPE);
		virtualAccount.setProvince(areaDao.findUniqueBy("id", 
				(Long.parseLong((String)param.get("province")))).getName());
		virtualAccount.setCity((String)param.get("city"));
		virtualAccount.setBankCode((String)param.get("bankCode"));
		virtualAccount.setName((String)param.get("rememberUsername"));
		virtualAccount.setBankName(d.getZhDesc());
		virtualAccount.setCardNum((String)param.get("cardNum"));
		virtualAccountDao.store(virtualAccount);
		return 1;
	}

	@Override
	@Transactional
	public int updateBankPassUserInfo(Map<String, Object> param) {
		Long userId = (Long)param.get("userId");
		User u = userDao.read(userId);
		if("1".equals(u.getIsEmailBind())){
			Object pass = param.get("loginPassword");
			if(null == pass)return 0;
			if(!Util.MD5((String)pass).equals(u.getLoginPassword()))return 0;
			u.setWithdrawPwd(Util.MD5((String)param.get("withdrawPwd")));
			userDao.store(u);
			return 11;
		}else{
			String email = (String)param.get("email");
			if(StringUtils.isBlank(email))return 0;
			User temp_user = userDao.findUniqueBy("email", email);
			if(null!= temp_user && !u.getUserid().equals(temp_user.getUserid()))return 0;
			u.setEmail(email);
			u.setWithdrawPwd(Util.MD5((String)param.get("withdrawPwd")));
			u.setIsEmailBind("1");
			userDao.store(u);
			return 22;
		}
		
	}

	@Override
	@Transactional
	public void setLoginTimes(Long userid) throws DaoException {
		User user = userDao.findUniqueBy("userid", userid);
		
		if(user != null) {
			if(user.getLoginCnt() == null) {
				user.setLoginCnt(1L);
			} else {
				user.setLoginCnt(user.getLoginCnt() + 1L);
			}
			
			userDao.store(user);
		}
	}

	@Override
	public BigDecimal getFund(Long userId) throws DaoException {
		VirtualAccount virtualAccount = virtualAccountDao.getUserAccountByUserId(userId);
		return virtualAccount.getAllMoney();
	}

	@Override
	@Transactional
	public String updateUserByParam(Map<String, Object> param) {
		User user = userDao.read(Long.parseLong((String)param.get("userId")));
		if(StringUtils.isNotBlank((String)param.get("newPassAnswerTipCode")))
			user.setPasswordTip((String)param.get("newPassAnswerTipCode"));
		if(StringUtils.isNotBlank((String)param.get("passAnswer")))
			user.setPasswordAnswer((String)param.get("passAnswer"));
		userDao.store(user);
		return "1";
	}

	@Override
	public Map getPayInfo(Long userId) throws DaoException {
		VirtualAccount virtualAccount = virtualAccountDao.getUserAccountByUserId(userId);
		Map data = new HashMap();
		data.put("myAllFund", virtualAccount.getAllMoney());
		data.put("myAvailableFund", virtualAccount.getAllMoney().subtract(virtualAccount.getFrozenMoney()));
		data.put("myFrozenFund", virtualAccount.getFrozenMoney());
		data.put("myCaijin", virtualAccount.getMosaicGold());
		data.put("myAvailableCaijin", virtualAccount.getMosaicGold().subtract(virtualAccount.getFrozenMosaicGold()));
		data.put("myPoint", virtualAccount.getPoint());
		return data;
	}
}
