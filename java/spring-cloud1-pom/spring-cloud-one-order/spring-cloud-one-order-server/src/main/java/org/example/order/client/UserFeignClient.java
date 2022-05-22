package org.example.order.client;

import org.example.order.config.FeignConfig;
import org.example.user.client.UserClient;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "spring-cloud-one-user", configuration = FeignConfig.class)
public interface UserFeignClient extends UserClient {
}
