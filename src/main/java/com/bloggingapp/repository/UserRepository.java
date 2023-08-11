package com.bloggingapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloggingapp.entity.UserMaster;

@Repository
public interface UserRepository extends JpaRepository<UserMaster, Integer> {
	
	Optional<UserMaster> findByEmail(String email);
	Optional<UserMaster> findByName(String name);
}
