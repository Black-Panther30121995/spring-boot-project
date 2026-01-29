package com.practice.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.journalApp.entity.User;
import com.practice.journalApp.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/health-check")
	public String healthCheck()
	{
		return "Ok";
	}
	@PostMapping("/create-user")
	public ResponseEntity<User> createUser(@RequestBody User user)
	{
		return new ResponseEntity<>(userService.saveEntry(user),HttpStatus.CREATED);
	}

}
