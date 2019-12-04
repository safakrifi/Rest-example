package com.example.springbootrestserver.jwt.models;

public class AuthenticationRequest {
	
	private String userName ;
	private String Password ;
	
	
	
	public AuthenticationRequest() {
	}



	public AuthenticationRequest(String userName, String password) {
		this.userName = userName;
		this.Password = password;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return Password;
	}



	public void setPassword(String password) {
		Password = password;
	}
	
	
	
	

}
