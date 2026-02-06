package com.practice.journalApp.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.practice.journalApp.cache.AppCache;
import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.entity.User;
import com.practice.journalApp.enums.Sentiment;
import com.practice.journalApp.model.SentimentData;
import com.practice.journalApp.repository.UserRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Autowired
    private AppCache appCache;

    // 🔥 IMPORTANT: Transactional REQUIRED
    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void fetchUsersAndSendSaMail() {

        log.info("Scheduler triggered");

        List<User> users = userRepositoryImpl.getUserForSA();
        log.info("Users fetched for SA: {}", users.size());

        for (User user : users) {

            log.info("Processing user id={}, email={}", user.getId(), user.getEmail());

            List<JournalEntry> journalEntries = user.getJournalEntries();
            log.info("Journal entries count: {}", journalEntries.size());

            for (JournalEntry entry : journalEntries) {
                log.info("Entry id={}, sentiment={}", entry.getId(), entry.getSentiment());
            }

            List<Sentiment> sentiments = journalEntries.stream()
                    .map(JournalEntry::getSentiment)
                    .collect(Collectors.toList());

            log.info("Collected sentiments: {}", sentiments);

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment,
                            sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            log.info("Sentiment frequency map: {}", sentimentCounts);

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;

            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            log.info("Most frequent sentiment: {}", mostFrequentSentiment);

            if (mostFrequentSentiment != null) {

                SentimentData sentimentData = SentimentData.builder()
                        .email(user.getEmail())
                        .sentiment("Sentiment for last 7 days " + mostFrequentSentiment)
                        .build();

                log.info("Sending to Kafka topic=weekly-sentiments payload={}", sentimentData);

                kafkaTemplate.send(
                        "weekly-sentiments",
                        user.getEmail(),
                        sentimentData
                ).whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka send FAILED", ex);
                    } else {
                        log.info("Kafka sent | partition={} offset={}",
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });

                kafkaTemplate.flush();
            } else {
                log.warn("No dominant sentiment found for user {}", user.getId());
            }
        }
    }

    @Scheduled(cron = "* 5 * * * *")
    public void clearAppCache() {
        log.info("Clearing app cache");
        appCache.init();
    }
}
