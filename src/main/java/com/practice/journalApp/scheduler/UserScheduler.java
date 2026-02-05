package com.practice.journalApp.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.practice.journalApp.cache.AppCache;
import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.entity.User;
import com.practice.journalApp.enums.Sentiment;
import com.practice.journalApp.repository.UserRepositoryImpl;
import com.practice.journalApp.service.EmailService;
import com.practice.journalApp.service.SentimentAnalysisService;

@Component
public class UserScheduler {

	@Autowired
	private SentimentAnalysisService sentimentAnalysisService;
	
	@Autowired
	private UserRepositoryImpl userRepositoryImpl;

	@Autowired
	private EmailService emailService;
	
	@Autowired 
	private AppCache appCache;
	
	@Scheduled(cron="* * 9 * * SUN")
	public void fetchUsersAndSendSaMail() {
		List<User> users=userRepositoryImpl.getUserForSA();
		for(User user:users)
		{
			List<JournalEntry> journalEntries=user.getJournalEntries();
			List<Sentiment> sentiments=journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7,ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
			Map<Sentiment,Integer> sentimentCounts=new HashMap<>();
			
			for(Sentiment sentiment: sentiments)
			{
				if(sentiment!=null)
				{
					sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment, 0)+1);
				}
			}
			
			Sentiment mostFrequentSentiment=null;
			int maxCount=0;
			for(Map.Entry<Sentiment,Integer>entry :sentimentCounts.entrySet())
			{
				if(entry.getValue()>maxCount)
				{
					maxCount=entry.getValue();
					mostFrequentSentiment=entry.getKey();
				}
			}
			
			if (mostFrequentSentiment !=null)
			{
				emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days", mostFrequentSentiment.toString());
			}
			
			
//			String entry=String.join(". ",filteredEntries);
//			String sentiment=sentimentAnalysisService.getSentiment(entry);
//			emailService.sendEmail("shivanikumari20050320@gmail.com","Sentiment for week!!", sentiment);
		}
	}
	
	@Scheduled(cron="* 5 * * * *")
	public void clearAppCache()
	{
		appCache.init();
	}
}
