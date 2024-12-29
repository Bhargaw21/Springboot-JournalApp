package com.myprojects.journal_app.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.myprojects.journal_app.entity.User;

import com.myprojects.journal_app.Repository.UserRepositoryImpl;
import com.myprojects.journal_app.cache.AppCache;
import com.myprojects.journal_app.entity.JournalEntry;
import com.myprojects.journal_app.enums.Sentiment;
import com.myprojects.journal_app.service.EmailService;
import com.myprojects.journal_app.service.SentimentAnalysisService;
import com.myprojects.journal_app.service.UserDetailServiceImpl;
import java.util.Map;

public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    private SentimentAnalysisService sentimentAnalysisService;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSentimentAnalysisMail() {

        List<User> users = userRepository.getAllUsersForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            String entry = String.join("", filteredEntries.stream().map(Sentiment::toString).collect(Collectors.toList()));
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);
               
}
}      

       @Scheduled(cron = "0 0 9 * * SUN")
       public void clearAppCache(){
        appCache.init();
       }
}
