package com.zorvyn.finance;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.zorvyn.finance.models.Role;
import com.zorvyn.finance.models.Status;
import com.zorvyn.finance.models.User;
import com.zorvyn.finance.repository.UserRepository;

import jakarta.annotation.PostConstruct;

//INITIALIZE THE DEFAULT ADMIN

@Component  
public class DataInitializer {
	
	
	private final UserRepository userRepo;
	
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    
    
    

    @PostConstruct
    public void init() {

        if (userRepo.count() == 0) { // no users exist

            User admin = new User();
            admin.setName("santha");
            admin.setEmail("santha@gmail.com");
            admin.setPassword(passwordEncoder.encode("santha$123"));
            admin.setRole(Role.ADMIN);
            admin.setStatus(Status.ACTIVE);

            userRepo.save(admin); // default admin created
        }
    }

}
