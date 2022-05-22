package org.example.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableEurekaServer
public class Application {

    public static void main(String[] args) {
        final ApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
