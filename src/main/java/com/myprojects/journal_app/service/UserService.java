package com.myprojects.journal_app.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional; 

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.myprojects.journal_app.Repository.UserRepo;
import com.myprojects.journal_app.entity.JournalEntry;
import com.myprojects.journal_app.entity.User;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public void saveNewEntry(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Validate roles from the user object and set them directly
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Arrays.asList("USER")); // Default to "USER" role if none provided
        }
        userRepo.save(user);
    }
    

    public void saveUser(User user){
        userRepo.save(user);
    } 

    public List<User> getall(){
        return userRepo.findAll();
     }


    public Optional<User> findById(ObjectId id) {
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId id){
         userRepo.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }
};
