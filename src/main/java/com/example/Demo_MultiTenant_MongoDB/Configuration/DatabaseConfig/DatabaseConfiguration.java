package com.example.Demo_MultiTenant_MongoDB.Configuration.DatabaseConfig;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoDatabase;
import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.Objects;

public class DatabaseConfiguration extends SimpleMongoClientDatabaseFactory {
    public DatabaseConfiguration(ConnectionString connectionString) {
        super(connectionString);
    }

    @Override
    @NonNull
    protected MongoDatabase doGetMongoDatabase(@NonNull String dbName) {
        ConnectionString connectionString = new ConnectionString(ConnectionStorage.getConnection());
        return super.doGetMongoDatabase(Objects.requireNonNull(connectionString.getDatabase()));
    }
}
