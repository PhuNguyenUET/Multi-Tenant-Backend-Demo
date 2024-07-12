package com.example.Demo_MultiTenant_MongoDB.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantDatabaseService {

    @Autowired
    private MongoClient mongoClient;

    public void createDatabaseForTenant(String tenantId) {
        MongoDatabase database = mongoClient.getDatabase(tenantId);
    }

    public void dropDatabaseForTenant(String tenantId) {
        MongoDatabase database = mongoClient.getDatabase(tenantId);
        database.drop();
    }
}
