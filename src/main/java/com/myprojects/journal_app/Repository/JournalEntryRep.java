package com.myprojects.journal_app.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.myprojects.journal_app.entity.JournalEntry;

public interface JournalEntryRep extends MongoRepository<JournalEntry , ObjectId>{

    
} 