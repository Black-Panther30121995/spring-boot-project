package com.practice.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.practice.journalApp.model.SentimentData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SentimentConsumerService {
	
	@Autowired
	private EmailService emailService;
	
	@KafkaListener(topics="weekly-sentiments",groupId="weekly-sentiment-group")
	public void consume(SentimentData sentimentData) {sendEmail(sentimentData);}
	
	private void sendEmail(SentimentData sentimentData)
	{
		emailService.sendEmail(sentimentData.getEmail(),"Sentiment for previosu week", sentimentData.getSentiment());
		log.info("Consumed sentiment");
	}
	
}
