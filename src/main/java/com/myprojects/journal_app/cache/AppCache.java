package com.myprojects.journal_app.cache;

import java.io.ObjectInputFilter.Config;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myprojects.journal_app.Repository.ConfigJournalAppRepo;
import com.myprojects.journal_app.entity.ConfigJournalAppEntity;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;

    public Map<String, String> App_Cache = new HashMap<>();

    @PostConstruct
    public void init(){
        List<ConfigJournalAppEntity> configJournalAppList = configJournalAppRepo.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : configJournalAppList){
            App_Cache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

}
