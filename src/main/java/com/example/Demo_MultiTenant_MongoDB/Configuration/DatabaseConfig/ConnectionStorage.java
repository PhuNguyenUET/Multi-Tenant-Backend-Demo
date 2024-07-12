package com.example.Demo_MultiTenant_MongoDB.Configuration.DatabaseConfig;

public class ConnectionStorage {
    private static final ThreadLocal<String> storage = new ThreadLocal<>();

    public static String getConnection() {
        return storage.get();
    }

    public static void setConnection(String connectionString) {
        storage.set(connectionString);
    }

    public static void clear() {
        storage.remove();
    }
}
