package com.jurijz.rssfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by jurijz on 10/2/2018.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories
public class RssFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssFeedApplication.class, args);
    }
}
