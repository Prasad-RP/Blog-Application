package com.bloggingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Prasad Pansare
 * @date 8 Aug 2023
 * @apiNote This file is for getting username and password from clinet or you
 *          can take seprately as well
 * 
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

	private Integer id;
	private String name;
	private String password;

}
