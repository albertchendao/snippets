package com.example.spring.boot2.service;

import org.springframework.stereotype.Service;

/**
 * 测试 service
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/31 19:12
 */
@Service
public class WorldService {

    public String sayNo() {
        return "no";
    }

    public String sayWorld() {
        return "world";
    }
}