package com.example.spring.boot2.service;

import com.example.spring.boot2.resp.UserResp;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/31 19:22
 */
@Service
public class DetailsServiceClient {

    private final RestTemplate restTemplate;

    public DetailsServiceClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public UserResp getUserDetails(String name) {
        return restTemplate.getForObject("/{name}/details", UserResp.class, name);
    }
}