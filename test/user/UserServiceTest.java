package user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.annotation.Rollback;

import com.wintv.framework.pojo.User;
import com.wintv.lottery.user.service.UserService;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = {"/spring/applicationContext-lottery.xml"})
	@TransactionConfiguration(transactionManager="transactionManager") 
    @Transactional
    public class UserServiceTest {
	 
	    @Autowired
	    private UserService userService;
	    
	    @Test
	    @Rollback(false) 
	    public void testUser()throws Exception{
	    	User user=new User();
	    	user.setUsername("�����û�12");
	    	user.setEmail("zs2010@126.com");
	    	user.setLoginPassword("123456");
	    	user.setMp("123");
	    	user.setUserGrade("1");
	    	user.setName("����");
	    	this.userService.saveUser(user);
	    }
	}

