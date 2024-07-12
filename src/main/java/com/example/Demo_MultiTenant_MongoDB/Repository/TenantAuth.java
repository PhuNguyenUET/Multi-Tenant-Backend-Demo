package com.example.Demo_MultiTenant_MongoDB.Repository;

import com.example.Demo_MultiTenant_MongoDB.Model.Tenant;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TenantAuth {
    //private final MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/central?readPreference=primary"));
    private final MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://SkyLawson:RaidenIsEternal@mongo:27017/central?authSource=admin&readPreference=primary"));

    public Tenant getTenantById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Tenant.class);
    }
}
