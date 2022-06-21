package org.example.spring.boot2.dubbo.controller;

import org.example.spring.boot2.dubbo.consumer.HelloServiceConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/21 7:33 PM
 */
@RestController
@RequestMapping
public class HelloController {

    @Autowired
    private HelloServiceConsumer helloServiceConsumer;

    @RequestMapping("/hello")
    public String hello() {
        return helloServiceConsumer.sayHi();
    }
}