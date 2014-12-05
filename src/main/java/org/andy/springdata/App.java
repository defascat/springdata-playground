package org.andy.springdata;

import org.andy.springdata.config.AppConfig;
import org.andy.springdata.domain.Account;
import org.andy.springdata.domain.AccountRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author andy
 */
public class App {
    public static void main(String[] args) {
        final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        final AccountRepository repository = context.getBean(AccountRepository.class);
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

        if(repository.findByNameLike("%8%").size() != 19 || repository.findByNameLike("201").size() > 0) {
            throw new IllegalStateException("Something is wrong");
        }
    }
}
