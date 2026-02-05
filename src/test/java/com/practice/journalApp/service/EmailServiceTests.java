package com.practice.journalApp.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class EmailServiceTests {
	@Autowired
	private EmailService emailService;
	
	@Test
	void testSendMail()
	{
		emailService.sendEmail("shivanikumari20050320@gmail.com", "Testing Java Mail Sender", "Hello!\nHow are you?");
	}
}
