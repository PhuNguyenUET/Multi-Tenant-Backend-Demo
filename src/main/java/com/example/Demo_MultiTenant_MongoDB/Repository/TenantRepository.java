package com.example.Demo_MultiTenant_MongoDB.Repository;

import com.example.Demo_MultiTenant_MongoDB.Model.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TenantRepository {
    @Autowired
    @Lazy
    private MongoTemplate mongoTemplate;

    public Tenant save(Tenant tenant) {
        mongoTemplate.save(tenant);

        return tenant;
    }

    public Tenant getTenantById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Tenant.class);
    }

    public Tenant getTenantByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("companyName").is(name));
        return mongoTemplate.findOne(query, Tenant.class);
    }

    public void deleteTenantById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Tenant.class);
    }
}
