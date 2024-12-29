package com.myprojects.journal_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myprojects.journal_app.Repository.UserRepo;
import com.myprojects.journal_app.entity.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService{


    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepo.findByUserName(username);
       if(user!=null){
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                                .username(user.getUserName())
                                .password(user.getPassword())
                                .roles(user.getRoles().toArray(new String[0]))
                                .build();

                                return userDetails;
       }

       throw new UsernameNotFoundException("User not found with username" + username); 
    }
    
}
