package org.example.spring.boot2.service;

import org.springframework.stereotype.Service;

/**
 * 测试 service
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/31 19:12
 */
@Service
public class HelloService {

    public String sayHi() {
        return "hi";
    }
}