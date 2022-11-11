package com.tweetapp.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tweetapp.app.entity.Tweet;

public class TweetDao {
	private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
   
    
	public TweetDao(){
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp?"
		                    + "user=root&password=password");
		    String createStatement = "CREATE TABLE IF NOT EXISTS TWEET"
		            + "   (id  INTEGER AUTO_INCREMENT PRIMARY KEY,"
		            + "   userid INTEGER,"
		            + "   tweet VARCHAR(100),"
		            + "   created DATE)";
				    Statement statement = connection.createStatement();
				    statement.execute(createStatement);
		    	}catch(Exception e) {
		    		System.out.println("Table has not created");
		    	}
	}
	
	
	public boolean toCreateTweet(Tweet tweet) {
		try {
			preparedStatement = connection.prepareStatement("insert into tweet(userid,tweet,created) values(?,?,?)");
			preparedStatement.setInt(1, tweet.getUserId());
			preparedStatement.setString(2, tweet.getTweet());
			preparedStatement.setDate(3, tweet.getCreated());
			preparedStatement.executeUpdate();
			System.out.println("Tweet uploaded successfully");
		} catch (SQLException e) {
			System.out.println("Tweet has not uploaded");
		}
		return true;
	}
	
	public ResultSet getAllTweets(){
		
		try {
		 statement = connection.createStatement();
	     String sql = "SELECT * FROM tweet";
	     resultSet = statement.executeQuery(sql);
	     return resultSet;
		}catch(Exception e) {
			System.out.println("Please try Again");
			return null;
		}
	}
	
	public ResultSet toGetTweetsByUserId(int userId){
		
		try {
			 statement = connection.createStatement();
		     String sql = "SELECT * FROM tweet where userid = "+userId;
		     
		     resultSet = statement.executeQuery(sql);
		     
		     return resultSet;
		}catch(Exception e) {
				System.out.println("Please Try again");
				return null;
		}
	}
}
