package com.zorvyn.finance.dto;

import lombok.Data;

@Data
public class LoginAuthenticateDTO {
	
	private String email;
	private String password;
	
	//DUE TO LOMBOK IMPLEMENTATION ISSUES ON MY PC ,SO I MANUALY ADDED THE GETTERS AND SETTERS
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
