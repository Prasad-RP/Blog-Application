package com.bloggingapp.api;

import java.util.List;

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

	@Autowired
	private UserServices service;

	// POST-create user
	@PostMapping("/new")
	public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userdto) {
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
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Integer id) {
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
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiResponse> userDeleted(@PathVariable("id") Integer id) {
		try {
			this.service.deleteUser(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse("User deleted Succesfully", true), HttpStatus.OK);
	}

	// GET-get all user
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<UserDto>> getAllUser() {
		return ResponseEntity.ok(this.service.getAllUser());
	}

	// GET-single user
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<UserDto> getById(@PathVariable("id") Integer id) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(this.service.getById(id));
	}
}
