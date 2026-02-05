package com.practice.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.practice.journalApp.api.response.WeatherResponse;
import com.practice.journalApp.cache.AppCache;
import com.practice.journalApp.constants.Placeholders;


@Service
public class WeatherService {
	@Value("${weather_api_key}")
	private String apiKey;
	
	@Autowired
	private AppCache appCache;

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public WeatherResponse getWeather(String city)
	{
		WeatherResponse weatherResponse=redisService.get("weather_of_"+city,WeatherResponse.class);
		if(weatherResponse!=null)
		{
			return weatherResponse;
		}
		else {
			String finalAPI=appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);
			ResponseEntity<WeatherResponse> response=restTemplate.exchange(finalAPI,HttpMethod.GET,null,WeatherResponse.class);
			
			WeatherResponse body= response.getBody();
			if(body!=null)
			{
				redisService.set("weather_of_"+city, body, 500l);
			}
			return body;
			
		}

		
		//if we  want to send header from java:
		//HttpHeaders httpHeaders=new HttpHeaders();
		//httpHeaders.set("key","value"); //like Aith and value
		//if we want to send a body, suppose:
		//User user =User.builder().userName("ABC").password("abc").build()
		//HttpEntity<User> httpEntitty=new HttpEntity<>(User,httpHeaders);
		//ResponseEntity<WeatherResponse> response=restTemplate.exchange(finalAPI,HttpMethod.POST,httpEntity,WeatherResponse.class);
		
		
		
		

	}
}
