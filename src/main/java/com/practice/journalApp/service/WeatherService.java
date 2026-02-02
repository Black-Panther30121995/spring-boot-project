package com.practice.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.practice.journalApp.api.response.WeatherResponse;

@Component
public class WeatherService {
	private static final String apiKey="abebbd9a174cc0b82deecedac896ef48";
	private static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
	

	
	@Autowired
	private RestTemplate restTemplate;
	
	public WeatherResponse getWeather(String city)
	{
		String finalAPI=API.replace("CITY", city).replace("API_KEY", apiKey);
		ResponseEntity<WeatherResponse> response=restTemplate.exchange(finalAPI,HttpMethod.GET,null,WeatherResponse.class);
		
		WeatherResponse body= response.getBody();
		return body;
	}
}
