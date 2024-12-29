package com.myprojects.journal_app.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.myprojects.journal_app.Repository.UserRepo;

public class UserDetailServiceImplTests {

    @InjectMocks
    private UserDetailServiceImpl userDetailServiceImpl;

    @Mock
    private UserRepo userRepo;
/*
    void loadUserByUsernameTest(){
        when(userRepo.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().username("BhargawSingh").password("password").roles("ROLE_USER")).build());
         UserDetails user = userDetailServiceImpl.loadUserByUsername("BhargawSingh");
         Assertions.assertNotNull(user);
    } */
}

