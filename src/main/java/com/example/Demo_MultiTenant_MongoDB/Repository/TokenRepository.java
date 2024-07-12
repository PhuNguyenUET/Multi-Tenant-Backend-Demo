package com.example.Demo_MultiTenant_MongoDB.Repository;

import com.example.Demo_MultiTenant_MongoDB.Model.Auth.Token;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {
    //private final MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/central?readPreference=primary"));
    private final MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://SkyLawson:RaidenIsEternal@mongo:27017/central?authSource=admin&readPreference=primary"));
    public Token save (Token token) {
        mongoTemplate.save(token);

        return token;
    }

    public Token findByToken(String token) {
        Query query = new Query();
        query.addCriteria(Criteria.where("token").is(token));
        return mongoTemplate.findOne(query, Token.class);
    }

    public void deleteByToken(String token) {
        Query query = new Query();
        query.addCriteria(Criteria.where("token").is(token));
        mongoTemplate.remove(query, Token.class);
    }
}
