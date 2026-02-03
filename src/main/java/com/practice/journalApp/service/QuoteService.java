package com.practice.journalApp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.practice.journalApp.api.response.QuoteResponse;

@Service
public class QuoteService {

    private static final String API_KEY = "5WoeLh8QqCNzfLFBMFMBBFSau9NpK3ewr2mIiQYo";
    private static final String API = "https://api.api-ninjas.com/v2/quotes?categories=CATEGORIES";

    @Autowired
    private RestTemplate restTemplate;

    public QuoteResponse getQuote(List<String> categories) {

        String categoryParam = String.join(",", categories);
        String finalAPI = API.replace("CATEGORIES", categoryParam);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", API_KEY);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<QuoteResponse>> response = restTemplate.exchange(
                finalAPI,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<QuoteResponse>>() {}
        );

        return response.getBody().get(0); 
    }
}
