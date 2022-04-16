package org.example.mvc.controller;

import org.example.mvc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CheckController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/check.do")
    @ResponseBody
    public String ok() {
        return helloService.say();
    }
}
