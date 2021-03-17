package com.kamadhenu.warehousemanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WarehouseManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseManagementSystemApplication.class, args);
    }
}
