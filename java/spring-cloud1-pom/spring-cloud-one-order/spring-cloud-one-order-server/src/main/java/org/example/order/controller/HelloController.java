package org.example.order.controller;

import org.example.common.vo.RespVo;
import org.example.order.client.UserFeignClient;
import org.example.order.helper.HttpContextHelper;
import org.example.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/user")
    public ResponseEntity<RespVo<UserDto>> user(@RequestParam("name") String name) {
        final UserDto info = userFeignClient.info(name);
        return HttpContextHelper.successEntity(info);
    }

    @GetMapping("/list")
    public ResponseEntity<RespVo<List<ServiceInstance>>> list() {
        List<ServiceInstance> result = new ArrayList<>();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> sis = discoveryClient.getInstances(service);
            result.addAll(sis);
        }
        return HttpContextHelper.successEntity(result);
    }
}
