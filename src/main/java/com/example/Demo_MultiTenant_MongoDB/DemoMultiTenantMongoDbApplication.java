package com.example.Demo_MultiTenant_MongoDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {
		MongoAutoConfiguration.class,
		MongoDataAutoConfiguration.class,
		DataSourceAutoConfiguration.class
})
public class DemoMultiTenantMongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMultiTenantMongoDbApplication.class, args);
	}

}
