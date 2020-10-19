package dev.suvera.scim2.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author: suvera
 * date: 10/18/2020 4:24 PM
 */
@SpringBootApplication(scanBasePackages = {"dev.suvera.scim2.example.server", "dev.suvera.scim2.server"})
public class Scim2ExampleServer {

    public static void main(String[] args) {
        SpringApplication.run(Scim2ExampleServer.class, args);
    }
}
