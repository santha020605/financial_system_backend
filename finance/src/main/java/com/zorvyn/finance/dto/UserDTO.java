package com.zorvyn.finance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

	@NotBlank(message = "Name is required")
	private String name;
	
	@Email(message = "Enter the valid mail")
	@NotBlank(message = "Email is required")
	private String email;
	
	@Size(min = 8,message = "Password must be atleast eight characters")
	private String password;
	
	//DUE TO LOMBOK IMPLEMENTATION ISSUES ON MY PC ,SO I MANUALLY ADDED THE GETTERS AND SETTERS
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
