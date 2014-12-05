package org.andy.springdata.config;

import org.andy.springdata.repositories.jpa.neo4j.AccountNeo4JRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

/**
 *
 * @author andy
 */
@Configuration
@EnableNeo4jRepositories(basePackageClasses = AccountNeo4JRepository.class)
@PropertySource("classpath:/db.properties")
public class Neo4JConfig extends Neo4jConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name="graphDatabaseService")
    public GraphDatabaseService graphDatabaseService(@Value("${neo4j.url}") String url) {
        return new SpringRestGraphDatabase(url);
    }
}
