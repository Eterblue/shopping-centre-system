package com.eterblue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ShoppingCentreSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingCentreSystemApplication.class, args);
    }

}
