package com.bloggingapp.api;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloggingapp.dto.AuthRequest;
import com.bloggingapp.services.UserServices;
import com.bloggingapp.utils.JwtUtils;

/**
 * 
 * @author Prasad Pansare
 *
 */

@RestController
@RequestMapping("/api")
public class LoginApi {

	private static final Logger log = LoggerFactory.getLogger(LoginApi.class);

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserServices userServices;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<Map<Object, Object>> authenticateUser(@RequestBody AuthRequest authRequest) {
		log.info("Getting logged in......");
		Map<Object, Object> map = new HashMap<>();
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));

		if (authenticate.isAuthenticated()) {
			map.put("Success", true);
			map.put("Roles", userServices.getRolesById(authRequest.getId()));
			map.put("Token", jwtUtils.generateToken(authRequest.getName()));
			return ResponseEntity.ok(map);
		}

		throw new BadCredentialsException("Invalid credentitals");
	}

}
