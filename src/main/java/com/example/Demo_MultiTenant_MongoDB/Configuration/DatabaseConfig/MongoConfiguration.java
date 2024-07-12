package com.example.Demo_MultiTenant_MongoDB.Configuration.DatabaseConfig;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    @Bean
    @Lazy
    public MongoTemplate mongoTemplate() {
        ConnectionString connectionString = new ConnectionString(ConnectionStorage.getConnection());
        return new MongoTemplate(new DatabaseConfiguration(connectionString));
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://SkyLawson:RaidenIsEternal@mongo:27017");
        //return MongoClients.create("mongodb://localhost:27017");
    }
}
