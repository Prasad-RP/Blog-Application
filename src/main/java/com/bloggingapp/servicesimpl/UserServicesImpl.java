package com.bloggingapp.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bloggingapp.dto.UserDto;
import com.bloggingapp.entity.UserMaster;
import com.bloggingapp.exception.ResourceNotFoundException;
import com.bloggingapp.repository.UserRepository;
import com.bloggingapp.services.UserServices;

@Service
public class UserServicesImpl implements UserServices {

	@Autowired
	UserRepository userRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public UserDto addUser(UserDto userDto) {
		UserMaster user = null;
		UserMaster saveUser = null;
		try {
			user = this.userDtoToUser(userDto);
			user.setPassword(encoder.encode(user.getPassword()));
			saveUser = this.userRepo.save(user);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.userToUserDto(saveUser);
	}

	@Override
	public void deleteUser(Integer id) {
		try {
			this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(" User ", " id ", id));
			this.userRepo.deleteById(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserDto getById(Integer id) {
		UserMaster user = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(" User", " id ", id));

		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<UserMaster> allUser = null;
		List<UserDto> userDto = null;

		try {
			allUser = this.userRepo.findAll();
			userDto = allUser.stream().map(user -> this.userToUserDto(user)).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		UserMaster user = null;
		UserMaster updateId = null;
		UserDto userDto1 = null;
		try {
			user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(" user ", " id ", id));
			user.setId(userDto.getId());
			user.setName(userDto.getName());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());

			updateId = this.userRepo.save(user);
			userDto1 = this.userToUserDto(updateId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userDto1;
	}

	public UserMaster userDtoToUser(UserDto userdto) {
		UserMaster user = this.modelMapper.map(userdto, UserMaster.class);
		return user;
	}

	public UserDto userToUserDto(UserMaster user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

//	User userDtoToUser(UserDto userDto) {
//		User user=new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		return user;
//	}
//	
//	UserDto userToUserDto(User user) {
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		return userDto;
//	}

}
