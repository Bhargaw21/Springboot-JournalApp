package com.myprojects.journal_app.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.myprojects.journal_app.entity.User;
import java.util.List;


public interface UserRepo extends MongoRepository<User , ObjectId>{
    
    User  findByUserName(String userName);

    void deleteByUserName(String name);
}
