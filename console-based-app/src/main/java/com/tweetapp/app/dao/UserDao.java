package com.tweetapp.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tweetapp.app.entity.User;

public class UserDao {
	
	private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    List<User> userList = new ArrayList<User>();
	
    public UserDao(){
    	try {
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp?"
                    + "user=root&password=password");
    String createStatement = "CREATE TABLE IF NOT EXISTS USER"
            + "   (id            INTEGER AUTO_INCREMENT PRIMARY KEY,"
            + "   firstname          VARCHAR(20),"
            + "   lastname           VARCHAR(20),"
            + "   gender           VARCHAR(20),"
            + "   dob     VARCHAR(15),"
            + "   email VARCHAR(30),"
            + "	  password VARCHAR(15),"
            + "	  status BOOLEAN)";

		    Statement statement = connection.createStatement();
		    statement.execute(createStatement);
    	}catch(Exception e) {
    		System.out.println("User table not created.");
    	}
    }
    
	//Register user
	public Boolean registerUser(User user) {
		
		try {
			PreparedStatement statement = connection.prepareStatement("insert into user(firstname,lastname,gender,dob,email,password,status) values (?,?,?,?,?,?,?)");
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getGender());
			statement.setString(4, user.getDob());
			statement.setString(5, user.getEmail());
			statement.setString(6, user.getPassword());
			statement.setBoolean(7, false);
			statement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("User is not registered.");
		}
		return true;
	}
	
	// Login 
	public Map<String,Integer> login(String email,  String password) {
		Map<String,Integer> result = new HashMap<String, Integer>();
		result.put("status", -1);
		try {
		 statement = connection.createStatement();
	     String sql = "SELECT id,email,password from user";
	     resultSet = statement.executeQuery(sql);
	     while(resultSet.next()) {
	    	 if(email.equals(resultSet.getString("email"))&&password.equals(resultSet.getString("password"))) {
	    		 result.put("userId", resultSet.getInt("id"));
	    		 result.put("status", 1);
	    		 preparedStatement = connection.prepareStatement("UPDATE user SET status = ? WHERE id = ?");
	    		 preparedStatement.setBoolean(1, true);
	    		 preparedStatement.setInt(2, resultSet.getInt("id"));
	    		 preparedStatement.executeUpdate();
	    		 return result;
	    	 }
	     }
		}catch(Exception e) {
			System.out.println("Something went wrong. Please try again..");
		}
		return result;
	}
	
	// Forgot password
	public void forgotPassword(String email,String newPassword) {
		try {
			preparedStatement = connection.prepareStatement("UPDATE user SET password = ? where email = ?");
			preparedStatement.setString(1, newPassword);
			preparedStatement.setString(2, email);
			if(preparedStatement.executeUpdate() == 1) {
				System.out.println("Password has been Changed successfully.");
			}else {
				System.out.println("Invalid credentials");
			}
		}catch(Exception e){
			System.out.println("Please try again.");
		}
		
	}
	
	// Update password
	public Boolean updatePassword(int userId,String oldPassword, String newPassword) {
		try {
			preparedStatement = connection.prepareStatement("UPDATE user SET password = ? where id = ? AND password = ?");
			preparedStatement.setString(1, newPassword);
			preparedStatement.setInt(2, userId);
			preparedStatement.setString(3, oldPassword);
			if(preparedStatement.executeUpdate() == 1) {
				System.out.println("Password changed successfully.");
			}else {
				System.out.println("Invalid password.");
			}
		}catch(Exception e){
			System.out.println("Please try again.");
		}
		return true;
	}
	
	//To Retrieve all users
	public ResultSet getAllUsers(){
		try {
		statement = connection.createStatement();
	    String sql = "SELECT firstname, lastname FROM user";
	    resultSet = statement.executeQuery(sql);
	    return resultSet;
		}catch(Exception e) {
			System.out.println("Please try again.");
			return null;
		}
	}
	
	//Logout
	public boolean logout(int userId) {
		try {
			preparedStatement = connection.prepareStatement("UPDATE user SET status = ? where id = ?");
			preparedStatement.setBoolean(1, false);
			preparedStatement.setInt(2, userId);
			if(preparedStatement.executeUpdate() == 1) {
				System.out.println("Logged out.");
				return true;
			}else {
				System.out.println("Invalid credentials.");
				return false;
			}
			}catch(Exception e){
				System.out.println("Please try again.");
				return false;
			}
	}
	
	
}
