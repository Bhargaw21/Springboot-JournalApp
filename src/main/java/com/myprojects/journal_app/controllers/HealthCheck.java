package com.myprojects.journal_app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    
    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }
}
