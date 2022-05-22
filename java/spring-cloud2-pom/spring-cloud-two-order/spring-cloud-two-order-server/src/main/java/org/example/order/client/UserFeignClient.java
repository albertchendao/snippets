package org.example.order.client;

import org.example.user.client.UserClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "spring-cloud-two-user")
public interface UserFeignClient extends UserClient {
}
