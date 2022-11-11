package com.tweetapp.app;

import java.util.Map;
import java.util.Scanner;

import com.tweetapp.app.utils.RegisterUtil;

public class App 
{
	static Scanner scanner = new Scanner(System.in);
	public static boolean Status = false;
	public static int loginId;
	
    public static void main( String[] args )
    {	
    	RegisterUtil userUtil = new RegisterUtil();
    	
    	if(!Status) {
    		System.out.println("Welcome to TweetApp.\nPlease choose from the options below:\n");
	        System.out.println( "1.Register\n2.Login\n3.Forgot password");
	        
	        int choice = scanner.nextInt();
	        switch(choice) {
	        case 1 : userUtil.registerUser();
        			main(args);
        			 break;
	        case 2 : Map<String,Integer> res = userUtil.login();
	        		 if(res.get("status")==1) {
	        			Status = true;
	        			loginId = res.get("userId");
	        		 }else {
	        			 System.out.println("Invalid credentials");
	        		 }
	        		 main(args);
	        		 break;
	        		 
	        case 3 : userUtil.forgotPassword();
	        		 main(args);
	        		 break;
	        default : System.out.println("Invalid Option.");
	        		  main(args);
	        	
	        }
    	}else {
    		System.out.println(" Please choose from the options below:\n");
    		System.out.println( "1.Post a tweet\n2.View my tweets\n3.View all tweets\n4.View all users\n5.Reset password\n6.Logout");
	       
	        int choice = scanner.nextInt();
	        switch(choice) {
	        case 1 : userUtil.createTweet(loginId);
        			 main(args);
        			 break;
	        case 2 : userUtil.getMyTweets(loginId);
	        		 main(args);
	        		 break;
	        case 3 : userUtil.getAllTweets();
	        		 main(args);
	        		 break;
	        case 4 : userUtil.getAllUsers();
	        		 main(args);
	        		 break;
	        case 5 : userUtil.updateUserPassword(loginId);
	        		 main(args);
	        		 break;
	        case 6 : if(userUtil.logout(loginId)) {
	        			Status = false;
	        		 };
	        		 main(args);
	        		 break;
	        default : System.out.println("Invalid option.");
	        		  main(args);
	        	
	        }
    	}
    }
}
