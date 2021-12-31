package com.example.spring.boot2.controller;

import com.example.spring.boot2.service.HelloService;
import com.example.spring.boot2.service.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查接口
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/31 17:44
 */
@RestController
@RequestMapping
public class HelloController {

    @Autowired
    private HelloService helloService;
    @Autowired
    private WorldService worldService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.sayHi();
    }

    @GetMapping("/world")
    public String world() {
        return worldService.sayWorld();
    }
}