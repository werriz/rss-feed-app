package com.jurijz.rssfeed;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by jurijz on 10/4/2018.
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setSchemas("FEEDS");
        flyway.setLocations(env.getProperty("flyway.migrations.path",
                "classpath:db/migration"));
        flyway.setDataSource(dataSource());
        flyway.setIgnoreMissingMigrations(true);
        flyway.setValidateOnMigrate(false);
        flyway.migrate();
        return flyway;
    }

    @Bean
    @DependsOn("flyway")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.jurijz.rssfeed.domain");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.database.driver",
                "org.h2.Driver"));
        dataSource.setUrl(env.getProperty("jdbc.database.url",
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"));
        dataSource.setUsername(env.getProperty("jdbc.database.username", "root"));
        dataSource.setPassword(env.getProperty("jdbc.database.password", "root"));

        return dataSource;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Bean
    @Autowired
    public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate hibernateTemplate = new HibernateTemplate();
        hibernateTemplate.setSessionFactory(sessionFactory);
        return hibernateTemplate;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2dll", "validate"));
        hibernateProperties.setProperty(
                "hibernate.dialect",
                env.getProperty("hibernate.dialect",
                        "org.hibernate.dialect.H2Dialect"));

        return hibernateProperties;
    }
}
