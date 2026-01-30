package com.practice.journalApp.controller;

import java.util.Optional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.UserRepository;
import com.practice.journalApp.service.UserService;


@RestController
@RequestMapping("/user")

public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user)
	{
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String userName=auth.getName();
		User updatedUser=userService.findByUserName(userName);

		updatedUser.setUserName(user.getUserName());
		updatedUser.setPassword(user.getPassword());
		userService.saveNewEntry(updatedUser);

		return new ResponseEntity<>(user,HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteByUserId()
	{
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		userRepository.deleteByUserName(auth.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
}
