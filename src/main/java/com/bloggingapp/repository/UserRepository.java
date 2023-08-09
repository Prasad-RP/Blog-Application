package com.bloggingapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloggingapp.entity.UserMaster;

public interface UserRepository extends JpaRepository<UserMaster, Integer> {
	
	Optional<UserMaster> findByEmail(String email);
	Optional<UserMaster> findByName(String userName);
}
