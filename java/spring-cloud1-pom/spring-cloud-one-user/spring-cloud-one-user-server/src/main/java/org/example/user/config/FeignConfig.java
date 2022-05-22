package org.example.user.config;

import feign.Logger;
import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfig {

    @Value("${feign.client.connectTimeoutMillis:1000}")
    Integer connectTimeoutMillis;
    @Value("${feign.client.readTimeoutMills:2000}")
    Integer readTimeoutMills;

    @Bean
    Request.Options feignOptions() {
        return new Request.Options(connectTimeoutMillis, readTimeoutMills);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
