package com.diego.finances.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.finances.dto.TokenResponse;
import com.diego.finances.dto.UserRequest;
import com.diego.finances.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private TokenService tokenService;
	@PostMapping
	public ResponseEntity<?> authenticate(@RequestBody @Valid UserRequest userRequest) {
		UsernamePasswordAuthenticationToken authToken = userRequest.toAuthToken();
		try {
			Authentication authentication = authManager.authenticate(authToken);
			var token = tokenService.getToken(authentication);
			return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
