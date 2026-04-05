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
	
	@Operation(summary = "Update User Info only")
	@PutMapping("/update/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User user) {
		return userService.updateUser(id, user);
		
	}

	@Operation(summary = "Update User's Role Only By ADMIN")
	@PutMapping("/update/{id}/role")
	public User updateRole(@PathVariable Long id, @RequestParam String updatedRole, HttpServletRequest request) {
		return userService.updateRole(id, updatedRole, request);

	}

	@Operation(summary = "Update User's Status Only By ADMIN")
	@PutMapping("/update/{id}/status")
	public User updateStatus(@PathVariable Long id, @RequestParam String status, HttpServletRequest request) {
		return userService.updateStatus(id, status, request);
		
	}

	@Operation(summary = "Delete User Only By ADMIN")
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id, HttpServletRequest request) {
		return userService.deleteUser(id, request);
		
	}

}
