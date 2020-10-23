package com.diego.finances.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diego.finances.Repository.UserRepository;
import com.diego.finances.model.User;

@Service
public class AuthenticationService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent())
			return user.get();
		throw new UsernameNotFoundException("Dados inválidos"); 
	}

}
