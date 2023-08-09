package com.bloggingapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bloggingapp.config.JwtUserService;
import com.bloggingapp.entity.UserMaster;
import com.bloggingapp.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserMaster> user = repository.findByName(username);
		return user.map(JwtUserService::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found" + username));
	}

}
