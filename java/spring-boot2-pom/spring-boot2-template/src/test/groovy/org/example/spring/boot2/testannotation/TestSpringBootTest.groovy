package org.example.spring.boot2.testannotation


import org.example.spring.boot2.controller.CheckController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @SpringBootTest 会启动整个 ApplicationContext, 默认不会启动 tomcat 等, 而使用 mock 的 tomcat
 */
@SpringBootTest
class TestSpringBootTest extends Specification {

    @Autowired(required = false)
    private CheckController checkController

    def "__TestSpringBootTest"() {
        checkController
    }
}
