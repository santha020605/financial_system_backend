package com.zorvyn.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zorvyn.finance.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
