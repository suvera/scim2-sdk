package dev.suvera.scim2.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * author: suvera
 * date: 10/18/2020 4:24 PM
 */
@SpringBootApplication(scanBasePackages = {"dev.suvera.scim2.example.server", "dev.suvera.scim2.server"})
@EnableTransactionManagement
@EntityScan(basePackages = {"dev.suvera.scim2.example.server.jpa.entity"})
public class Scim2ExampleServer {

    public static void main(String[] args) {
        SpringApplication.run(Scim2ExampleServer.class, args);
    }
}
