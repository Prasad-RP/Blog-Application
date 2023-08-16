package com.bloggingapp.services;

import java.util.List;
import java.util.Optional;

import com.bloggingapp.dto.UserDto;
import com.bloggingapp.entity.UserMaster;

import jakarta.transaction.Transactional;

@Transactional
public interface UserServices {
	
	UserDto addUser(UserDto userDto);
	
	void deleteUser(Integer id);
	
	UserDto getById(Integer id);
	
	List<UserDto> getAllUser();
	
	@Transactional
	UserDto updateUser(UserDto userDto,Integer Id);
		
	Optional<UserMaster> getByUserName(String name);

}
