package com.translogistics.hub.logistics_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class LogisticsHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsHubApplication.class, args);
    }

}
