package com.practice.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(DemoApplication.class, args);
		System.out.println(context.getEnvironment().getActiveProfiles()[0]);
		
	}
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory)
	{
		return new MongoTransactionManager(dbFactory);
	}
	
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

}
