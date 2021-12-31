package com.example.spring.boot2

import com.example.spring.boot2.controller.CheckController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ApplicationTest extends Specification {

    @Autowired(required = false)
    private CheckController checkController

    def "when context is loaded then all expected beans are created"() {
        expect: "the WebController is created"
        checkController
    }
}
