package com.myprojects.journal_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myprojects.journal_app.entity.User;
import com.myprojects.journal_app.service.UserService;

@RequestMapping("/public")
@RestController
public class PublicController {

     @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.saveNewEntry(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }
}
