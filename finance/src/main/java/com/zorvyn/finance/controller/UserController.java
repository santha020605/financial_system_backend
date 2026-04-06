package com.zorvyn.finance.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zorvyn.finance.dto.LoginAuthenticateDTO;
import com.zorvyn.finance.dto.UserDTO;
import com.zorvyn.finance.models.User;
import com.zorvyn.finance.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name ="User APIs", description = "Related to Users")
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping("/create")
	public User createUser(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
		return userService.createUser(userDTO, request);

	}

	
	@PostMapping("/login")
	public String login(@RequestBody LoginAuthenticateDTO dto) {
		return userService.login(dto);
		
	}
	

	@GetMapping("/all")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
		
	}
	
	
	@PutMapping("/update")
	public User updateUser(@RequestBody UserDTO user, HttpServletRequest request) {
		return userService.updateUser(user, request);
		
	}
	
	

	@Operation(summary = "UPDATE ROLE OF THE USER")
	@PutMapping("/update/{id}/role")
	public User updateRole(@PathVariable Long id, @RequestParam String updatedRole, HttpServletRequest request) {
		return userService.updateRole(id, updatedRole, request);

	}
	
	
	
	@Operation(summary = "UPDATE STATUS OF THE USER")
	@PutMapping("/update/{id}/status")
	public User updateStatus(@PathVariable Long id, @RequestParam String status, HttpServletRequest request) {
		return userService.updateStatus(id, status, request);
		
	}
	
	

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id, HttpServletRequest request) {
		return userService.deleteUser(id, request);
		
	}
	

}
