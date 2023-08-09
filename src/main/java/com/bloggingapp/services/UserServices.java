package com.bloggingapp.services;

import java.util.List;

import com.bloggingapp.dto.UserDto;

public interface UserServices {
	
	UserDto addUser(UserDto userDto);
	
	void deleteUser(Integer id);
	
	UserDto getById(Integer id);
	
	List<UserDto> getAllUser();
	
	UserDto updateUser(UserDto userDto,Integer Id);
		
	

}
