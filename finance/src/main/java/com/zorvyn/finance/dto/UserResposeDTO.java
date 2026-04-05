package com.zorvyn.finance.dto;

import lombok.Data;

@Data
public class UserResposeDTO {
	
	private Long id;
	private String name;
	private String email;
	
	//DUE TO LOMBOK IMPLEMENTATION ISSUES ON MY PC ,SO I MANUALLY ADDED THE GETTERS AND SETTERS
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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

}
