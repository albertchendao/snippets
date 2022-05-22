package org.example.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEurekaServer
public class Application {

    public static void main(String[] args) {
        final SpringApplication springApplication = new SpringApplication(Application.class);
        final ConfigurableApplicationContext context = springApplication.run(args);
    }
}
