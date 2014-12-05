package org.andy.springdata;

import org.andy.springdata.config.JPAConfig;
import org.andy.springdata.config.MongoConfig;
import org.andy.springdata.config.Neo4JConfig;
import org.andy.springdata.domain.Account;
import org.andy.springdata.repositories.AccountCrudRepository;
import org.andy.springdata.repositories.jpa.AccountJPARepository;
import org.andy.springdata.repositories.jpa.neo4j.AccountNeo4JRepository;
import org.andy.springdata.repositories.mongo.AccountMongoRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author andy
 */
public class App {
    public static void main(String[] args) {
        validateRepository(JPAConfig.class, AccountJPARepository.class);
        validateRepository(MongoConfig.class, AccountMongoRepository.class);
        // validateRepository(Neo4JConfig.class, AccountNeo4JRepository.class);
    }

    private static void validateRepository(final Class appContextClass, Class<? extends AccountCrudRepository> clazz) throws IllegalStateException, BeansException {
        final ApplicationContext context = new AnnotationConfigApplicationContext(appContextClass);
        final AccountCrudRepository repository = context.getBean(clazz);
        for(int i = 0; i < 100; i++) {
            final Account account = new Account();
            account.setId((long) i);
            account.setName("User " + i);
            repository.save(account);
        }
        
        if(!repository.exists(0l) || repository.exists(100l)) {
            throw new IllegalStateException("Something is wrong");
        }

        if(repository.findByName("User 85") == null || repository.findByName("User 101") != null) {
            throw new IllegalStateException("Something is wrong");
        }
        /*
        // Works in JPA but not in MongoDB
        if(repository.findByNameLike("%8%").size() != 19 || repository.findByNameLike("201").size() > 0) {
            throw new IllegalStateException("Something is wrong");
        }
        */
    }
}
