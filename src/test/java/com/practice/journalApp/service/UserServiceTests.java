package com.practice.journalApp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;

//	@ParameterizedTest
//	@ValueSource(strings={
//		"alice",
//		"Ram",
//		"Shyam"
//	})
//	@ArgumentsSource(UserArgumentsProvider.class)
//	public void testSaveNewUser(User user)
//	{
//		assertTrue(userService.saveNewEntry(user));
//		
//	}
	
//	@BeforeEach
//	@AfterAll
	
//	@Disabled
	@ParameterizedTest
	@CsvSource({
		"1,1,2",
		"2,10,12",
		"3,3,6"
	})
	public void test(int a,int c, int expected)
	{
		assertEquals(expected,a+c);
	}
	
	
	
}
