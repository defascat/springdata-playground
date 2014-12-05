package org.andy.springdata.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author andy
 */
@Configuration
@EnableMongoRepositories(basePackages = "org.andy.springdata.repositories.mongo")
@PropertySource("classpath:/db.properties")
public class MongoConfig extends AbstractMongoConfiguration {
    @Autowired
    private Environment env;
    
    @Override
    protected String getDatabaseName() {
        return env.getProperty("mongo.db");
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(env.getProperty("mongo.host"));
    }
}
