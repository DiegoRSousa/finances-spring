package com.diego.finances.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserRequest {

	private String username;
	private String password;
	
	public UsernamePasswordAuthenticationToken toAuthToken() {
		return new UsernamePasswordAuthenticationToken(username, password);
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}
