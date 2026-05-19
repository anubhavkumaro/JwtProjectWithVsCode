package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.UserRequest;
import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
public String registerUser(UserRequest userReq) {
		
		Optional<User> u = repo.findByUsername(userReq.getUsername());
		if(u.isPresent()) {
			return "User Alarady Register";
		}
		
		User us = new User(userReq.getName(),userReq.getUsername(),encoder.encode(userReq.getPassword()),"user");
		repo.save(us);
		
		return "User Register Successfully";
	}
}
