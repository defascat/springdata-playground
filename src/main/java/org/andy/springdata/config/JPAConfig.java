package org.andy.springdata.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.andy.springdata.repositories.jpa.AccountJPARepository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 *
 * @author andy
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = AccountJPARepository.class)
// @EnableMongoRepositories(basePackageClasses = AccountMongoRepository.class)
@PropertySource("classpath:/db.properties")
public class JPAConfig {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource(
            @Value("${jpa.driver}") String driver,
            @Value("${jpa.url}") String url,
            @Value("${jpa.user}") String user,
            @Value("${jpa.pwd}") String pwd
    ) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        final BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriver((java.sql.Driver) Class.forName(driver).newInstance());
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(pwd);
        return basicDataSource;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        final LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(context.getBean(DataSource.class));
        localSessionFactoryBean.setPackagesToScan("org.andy.springdata.domain");
        localSessionFactoryBean.setHibernateProperties(hibernateProperties());
        return localSessionFactoryBean;
    }
    
    @Bean 
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(context.getBean(DataSource.class));
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(context.getBean(JpaVendorAdapter.class));
        localContainerEntityManagerFactoryBean.setPackagesToScan("org.andy.springdata.domain");
        return localContainerEntityManagerFactoryBean;
    }

    @Bean 
    public JpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(Boolean.parseBoolean(env.getProperty("hibernate.show_sql")));
        return hibernateJpaVendorAdapter;
    }

    @Bean 
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager(context.getBean(EntityManagerFactory.class));
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
                setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
                setProperty("hibernate.globally_quoted_identifiers", "true");
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
            }
        };
    }
}
