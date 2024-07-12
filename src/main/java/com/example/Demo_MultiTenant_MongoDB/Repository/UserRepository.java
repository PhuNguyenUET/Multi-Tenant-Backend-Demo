package com.example.Demo_MultiTenant_MongoDB.Repository;

import com.example.Demo_MultiTenant_MongoDB.Model.Auth.Token;
import com.example.Demo_MultiTenant_MongoDB.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    @Lazy
    private MongoTemplate mongoTemplate;

    public User save(User user) {
        mongoTemplate.save(user);

        return user;
    }

    public User getUserById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, User.class);
    }

    public User getUserByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, User.class);
    }

    public void deleteByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        mongoTemplate.remove(query, Token.class);
    }
}
