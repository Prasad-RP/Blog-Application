package com.bloggingapp.api;

import static com.bloggingapp.config.AppConstant.ROLE_ADMIN;
import static com.bloggingapp.config.AppConstant.ROLE_USER;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bloggingapp.dto.ApiResponse;
import com.bloggingapp.dto.UserDto;
import com.bloggingapp.services.UserServices;

import jakarta.validation.Valid;

/**
 * @author Prasad Pansare
 * 
 */
@RestController
@RequestMapping("/api/users")
public class UserApis {

	private static final Logger log = LoggerFactory.getLogger(UserApis.class);

	@Autowired
	private UserServices service;

	// POST-create user
	@PostMapping("/new")
	public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userdto) {
		log.info("Adding new user...");
		UserDto dto = null;
		try {
			dto = this.service.addUser(userdto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<UserDto>(dto, HttpStatus.CREATED);
	}

	// PUT-Update Data

	@PutMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Integer id) {
		log.info("updating user with id: " + id);
		UserDto updatedUser = null;
		try {
			updatedUser = this.service.updateUser(userDto, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(updatedUser);
	}

	// DELETE-delete user
	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<ApiResponse> userDeleted(@PathVariable("id") Integer id) {
		log.info("Deleting user with id: " + id);
		try {
			this.service.deleteUser(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse("User deleted Succesfully", true), HttpStatus.OK);
	}

	// GET-get all user
	@GetMapping("/all")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<List<UserDto>> getAllUser() {
		log.info("Getting all users...");
		return ResponseEntity.ok(this.service.getAllUser());
	}

	// GET-single user
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<UserDto> getById(@PathVariable("id") Integer id) {
		log.info("Getting user with id:" + id);
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(this.service.getById(id));
	}
}
