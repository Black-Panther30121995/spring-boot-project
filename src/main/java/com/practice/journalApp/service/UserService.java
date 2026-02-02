package com.practice.journalApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public boolean saveNewEntry(User user)
	{
		try {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER"));
		userRepository.save(user);
		return true;
		}
		catch(Exception e)
		{
			log.error("Error occured for {} :",user.getUserName(),e);
//			logger.warn("hahahaha");
//			logger.info("hahahaha");
//			logger.debug("hahahah");
//			logger.trace("hahaha");
			return false;
		}

	}
	
	public User saveAdmin(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER","ADMIN"));
		return userRepository.save(user);

	}
	
	public void saveUser(User user)
	{
		userRepository.save(user);
	}
	
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
