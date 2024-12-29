package com.myprojects.journal_app.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.myprojects.journal_app.entity.JournalEntry;
import com.myprojects.journal_app.entity.User;
import com.myprojects.journal_app.service.JournalEntryService;
import com.myprojects.journal_app.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    private String getAuthenticatedUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            String userName = getAuthenticatedUserName();
            journalEntryService.saveEntry(journalEntry, userName);
            return ResponseEntity.status(HttpStatus.CREATED).body(journalEntry);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create journal entry: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        String userName = getAuthenticatedUserName();
        try {
            User user = userService.findByUserName(userName);
            List<JournalEntry> allEntries = user.getJournalEntries();

            if (allEntries == null || allEntries.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No journal entries found for user: " + userName);
            }

            return ResponseEntity.ok(allEntries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching journal entries: " + e.getMessage());
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId) {
        String userName = getAuthenticatedUserName();
        try {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);

            if (journalEntry.isPresent()) {
                return ResponseEntity.ok(journalEntry.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No journal entry found with ID: " + myId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching journal entry: " + e.getMessage());
        }
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId) {
        String userName = getAuthenticatedUserName();
        try {
            journalEntryService.deleteById(myId, userName);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete journal entry: " + e.getMessage());
        }
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newJournalEntry) {

        String userName = getAuthenticatedUserName();
        try {
            Optional<JournalEntry> optionalEntry = journalEntryService.findById(myId);

            if (optionalEntry.isPresent()) {
                JournalEntry oldEntry = optionalEntry.get();

                if (newJournalEntry.getTitle() != null && !newJournalEntry.getTitle().isEmpty()) {
                    oldEntry.setTitle(newJournalEntry.getTitle());
                }
                if (newJournalEntry.getContent() != null && !newJournalEntry.getContent().isEmpty()) {
                    oldEntry.setContent(newJournalEntry.getContent());
                }

                journalEntryService.saveEntry(oldEntry);
                return ResponseEntity.ok(oldEntry);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No journal entry found with ID: " + myId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update journal entry: " + e.getMessage());
        }
    }
}
