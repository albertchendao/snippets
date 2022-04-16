package org.example.mvc

import org.example.mvc.controller.CheckController
import org.example.mvc.service.HelloService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@WebAppConfiguration
//@ContextConfiguration(loader = MyContextLoader)
@ContextConfiguration("classpath*:spring-context.xml")
class SpringJunit4Test extends Specification {

    @Autowired
    HelloService helloService
//    @Autowired
//    CheckController checkController

    def "load"() {
        expect:
        helloService != null
//        checkController != null
    }
}
