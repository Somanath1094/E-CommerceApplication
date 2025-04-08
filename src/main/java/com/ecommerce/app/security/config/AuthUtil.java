package com.ecommerce.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ecommerce.app.domain.User;
import com.ecommerce.app.repository.UserRepository;

public class AuthUtil {

	@Autowired
	private UserRepository userRepository;

	public String loggedInEmail(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + authentication.getName()));

		return user.getEmail();
	}

	public Long loggedInUserId(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + authentication.getName()));

		return user.getId();
	}

	public User loggedInUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + authentication.getName()));
		return user;

	}
}
