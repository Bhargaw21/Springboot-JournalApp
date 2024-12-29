package com.myprojects.journal_app.Repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.myprojects.journal_app.entity.User;

public class UserRepositoryImpl {


    private MongoTemplate mongoTemplate;

    public List<User> getAllUsersForSA(){
        Query query = new Query();
       // query.addCriteria(Criteria.where("userName").is("BhargawSingh"));
       query.addCriteria(Criteria.where("email").exists(true)); 
       query.addCriteria(Criteria.where("email").ne(null).ne(""));
       query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users =  mongoTemplate.find(query, User.class);
        return users;
    }

}
