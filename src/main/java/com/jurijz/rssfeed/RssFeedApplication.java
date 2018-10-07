package com.jurijz.rssfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Created by jurijz on 10/2/2018.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class RssFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssFeedApplication.class, args);
    }
}
