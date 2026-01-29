package com.practice.journalApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.UserRepository;

@Component
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User saveEntry(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER"));
		return userRepository.save(user);

	}
	
//	public void saveNewUser(User user)
//	{
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		userRepository.save(user);
//	}
	
	public List<User> getAll()
	{
		return userRepository.findAll();
	}
	
	public Optional<User> findById(ObjectId id)
	{
		return userRepository.findById(id);
	}
	
	public void deleteById(ObjectId id)
	{
		userRepository.deleteById(id);
	}
	public User findByUserName(String userName)
	{
		return userRepository.findByUserName(userName);
	}
	
}
