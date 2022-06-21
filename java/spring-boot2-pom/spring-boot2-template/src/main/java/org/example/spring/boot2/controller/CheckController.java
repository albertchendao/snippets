package org.example.spring.boot2.controller;

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
public class CheckController {

    /**
     * http -v :8080/check.do
     */
    @GetMapping("/check.do")
    public String check() {
        return "ok";
    }
}