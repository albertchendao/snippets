package org.example.demo;

import org.example.demo.condition.GoodService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        final ConfigurableApplicationContext context = springApplication.run("--say=TRUE");
        final GoodService service = context.getBean(GoodService.class);
        if (service != null) service.say();
    }
}
