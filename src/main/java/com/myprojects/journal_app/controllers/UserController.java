package com.myprojects.journal_app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.myprojects.journal_app.Api_Response.WeatherResponse;
import com.myprojects.journal_app.Repository.UserRepo;
import com.myprojects.journal_app.entity.User;
import com.myprojects.journal_app.service.UserService;
import com.myprojects.journal_app.service.WeatherService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WeatherService weatherService;

    // Helper method to get the authenticated user's username
    private String getAuthenticatedUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Update the authenticated user's details
     * @param updatedUser The user details to update
     * @return ResponseEntity indicating success or failure
     */
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User updatedUser) {
        String userName = getAuthenticatedUserName();
        User existingUser = userService.findByUserName(userName);

        if (existingUser != null) {
            // Update fields if they are not null
            if (updatedUser.getUserName() != null && !updatedUser.getUserName().isEmpty()) {
                existingUser.setUserName(updatedUser.getUserName());
            }
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(updatedUser.getPassword()); // Ensure encryption if needed
            }

            userService.saveNewEntry(existingUser);
            return ResponseEntity.ok("User updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    /**
     * Delete the authenticated user's account
     * @return ResponseEntity indicating success
     */
    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        String userName = getAuthenticatedUserName();

        try {
            userRepo.deleteByUserName(userName);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete user: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";

        if(weatherResponse != null){
            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("hii " + authentication.getName() + greeting  , HttpStatus.OK);
    }


}
