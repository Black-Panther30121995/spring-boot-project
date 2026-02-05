package com.practice.journalApp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Disabled
	@Test
	void testSendMail()
	{
		//redisTemplate.opsForValue().set("email","6jggfhf@gamil.com");
		Object email=redisTemplate.opsForValue().get("name");
		Assertions.assertNotNull(email);
		int a=1;
	}
}
