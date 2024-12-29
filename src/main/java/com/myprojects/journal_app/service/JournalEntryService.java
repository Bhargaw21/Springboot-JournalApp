package com.myprojects.journal_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprojects.journal_app.Repository.JournalEntryRep;
import com.myprojects.journal_app.entity.JournalEntry;
import com.myprojects.journal_app.entity.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRep journalEntryRep;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRep.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            log.info("testing logger");
            log.error("An error occured while saving the enetry", e);
            log.warn(userName);
            throw new RuntimeException("An error occured while saving the enetry", e);
        }

    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRep.save(journalEntry);
    }

    public List<JournalEntry> getall() {
        return journalEntryRep.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRep.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId id, String userName) {
       try {
        User user = userService.findByUserName(userName);
        boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if (removed) {
            userService.saveUser(user);
            journalEntryRep.deleteById(id);
            System.out.println("Entry removed");
        } else {
            System.out.println("Entry not found");
        }
       } catch (Exception e) {
              System.out.println(e);
              throw new RuntimeException("An error occured while deleting the entry", e);
       }
    }

}
