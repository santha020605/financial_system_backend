package com.zorvyn.finance.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zorvyn.finance.configuration.JwtUtility;
import com.zorvyn.finance.dto.LoginAuthenticateDTO;
import com.zorvyn.finance.dto.UserDTO;
import com.zorvyn.finance.exceptions.AccessDeniedException;
import com.zorvyn.finance.models.Role;
import com.zorvyn.finance.models.Status;
import com.zorvyn.finance.models.User;
import com.zorvyn.finance.repository.FinancialRecordRepository;
import com.zorvyn.finance.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    private final FinancialRecordRepository recordRepo;
    
    private final JwtUtility jwtUtility;
    
    private final PasswordEncoder passwordEncoder;
    
    
    

    public UserService(UserRepository userRepository, FinancialRecordRepository recordRepo, JwtUtility jwtUtility, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.recordRepo = recordRepo;
        this.jwtUtility = jwtUtility;
        this.passwordEncoder = passwordEncoder;
    }
    
    
    

    public User createUser(UserDTO userDTO, HttpServletRequest request) {
		
    	User user = new User();
    	
    	user.setName(userDTO.getName());
    	user.setEmail(userDTO.getEmail());
    	user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    	
    	user.setRole(Role.VIEWER);
    	user.setStatus(Status.ACTIVE);
    	
        return userRepository.save(user);
    }
    
    
    
    
    
    public String login(LoginAuthenticateDTO dto) {
    	User user = userRepository.findByEmail(dto.getEmail());
    	
    	if(user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
    		throw new RuntimeException("Invalid Login");
    	}
    	
    	return jwtUtility.generateToken(user.getEmail(),user.getRole().name());
    }
    
    
    
    
    

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    
    
    
    

    // Accessed by anyone for update their info
    public User updateUser(UserDTO updatedUser, HttpServletRequest request) {
    	
    	String email = (String) request.getAttribute("email");
        User user = userRepository.findByEmail(email);

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        return userRepository.save(user);
    }
    
    
    
    
    
    
    
    //Accessed only by admin
    
    public User updateRole(Long id, String updatedRole,HttpServletRequest request) {
    	
    	String role = (String) request.getAttribute("role");
    	checkAccess(role, Role.ADMIN.name());
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(Enum.valueOf(com.zorvyn.finance.models.Role.class, updatedRole));
        return userRepository.save(user);
    }
    
    
    
    
    

    public User updateStatus(Long id, String status, HttpServletRequest request) {
    	
    	String role = (String) request.getAttribute("role");
    	checkAccess(role, Role.ADMIN.name());
    	
        User user = userRepository.findById(id).orElseThrow();
        user.setStatus(Enum.valueOf(com.zorvyn.finance.models.Status.class, status));
        return userRepository.save(user);
    }
    
    
    
    
    
    
    @Transactional
    public String deleteUser(Long id, HttpServletRequest request) {
    	
    	String role = (String) request.getAttribute("role");
    	checkAccess(role, Role.ADMIN.name());
    	
    	 User user = userRepository.findById(id).orElseThrow();
 
    	recordRepo.deleteByUser(user);
    	userRepository.deleteById(id);
    	return "User Info Deleted";
    }
    
    
    
    
    
// ROLE BASED CHECKING FUNCTION
    
    private void checkAccess(String role, String... allowedRoles) {
    	for(String r : allowedRoles) {
    		if(r.equals(role)) return;
    	}
    	throw new AccessDeniedException("Access Denied");
    }
}