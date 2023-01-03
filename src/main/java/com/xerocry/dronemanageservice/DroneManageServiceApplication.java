package com.xerocry.dronemanageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
public class DroneManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroneManageServiceApplication.class, args);
    }

}
