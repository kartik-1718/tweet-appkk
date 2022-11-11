package com.tweetapp.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tweetapp.app.entity.User;
import com.tweetapp.app.service.UserService;

import junit.framework.TestCase;

public class UserServiceTest {
		User user = new User();
		UserService service =new UserService();
		
		@Test
		//@DisplayName("Registering User to TweetApp");
		public void registerUserTest() {
			service.registerUser(user);
		}
		
/*		@Test
		public void forgotTest() {
		//	String a = service.forgot(user.getEmail(),user.getPassword());
			assertEquals("testPassword",a);
		}
		
		@Test
		public void updatePasswordTest() {
			
		}
		*/
		@Test
		public void getAllUsersTest() {
			service.getAllUsers();
		}
}
