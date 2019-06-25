package com.base.mongo.config;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.StopWatch;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClients;

/**
 * This class will enable the MongoClient configuration during testing startup.
 *
 * @author vleon
 */
@EnableMongoRepositories(basePackages = "com.base.mongo.repository")
public class MongoDBConfiguration {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBConfiguration.class);
	
	@Value("${spring.data.mongodb.uri}") 
	String connectionString;

	@Value("${spring.data.mongodb.database}") 
	String dataBaseName;
	
	MongoClient mongoClient;
	
	/**
	 * Creates a single instance of MongoClient, all requests will return the same object
	 * 
	 * @return Mongo Client ready to use
	 */
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON) 
	public MongoClient mongoClient() {
        if(mongoClient == null) {
        	LOGGER.debug("Iniciando MongoClient...");
        	StopWatch watch = new StopWatch();
        	watch.start();
			ConnectionString connString = new ConnectionString(connectionString);
			MongoClientSettings settings = MongoClientSettings.builder()
					// .credential(MongoCredential.createCredential("user","databasename",password))
					.applyConnectionString(connString)
					.writeConcern(WriteConcern.MAJORITY.withWTimeout(2500, TimeUnit.MILLISECONDS)).build();
			mongoClient = MongoClients.create((settings));
			watch.stop();
			LOGGER.debug("MongoClient iniciado en {} ms", watch.getTotalTimeMillis());
        }
		return mongoClient;
	}

	/**
	 * Template ready to use to operate on the database
	 * 
	 * @return Mongo Template ready to use
	 */
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		LOGGER.debug("Iniciando MongoTemplate...");
        StopWatch watch = new StopWatch();
        watch.start();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), dataBaseName);
        watch.stop();
        LOGGER.debug("MongoTemplate iniciado en {} ms", watch.getTotalTimeMillis());
		return mongoTemplate;
	}
	
}
