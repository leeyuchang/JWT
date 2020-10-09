package com.home.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class AppConfig {
  
  @Value("${mongodb.uri}")
  private String MONGODB_ENDPOINT;
  
  public @Bean MongoClient mongoClient() {
    return MongoClients.create(MONGODB_ENDPOINT);
  }

}
