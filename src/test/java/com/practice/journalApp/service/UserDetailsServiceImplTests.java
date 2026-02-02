package com.practice.journalApp.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.UserRepository;

@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTests {

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	void loadUserByUsernameTest()
	{
		when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Ram").password("insdbsdvbc").roles(new ArrayList<>()).build());
		UserDetails user=userDetailsService.loadUserByUsername("ram");
		Assertions.assertNotNull(user);
	}
}
