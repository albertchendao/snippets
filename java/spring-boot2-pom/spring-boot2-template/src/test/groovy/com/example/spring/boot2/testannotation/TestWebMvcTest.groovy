package com.example.spring.boot2.testannotation

import com.example.spring.boot2.controller.CheckController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

/**
 * @WebMvcTest 只会测试 Spring MVC 中的 Controller, 注入 Service 会失败
 */
@WebMvcTest(CheckController)
class TestWebMvcTest extends Specification {

    @Autowired
    private MockMvc mvc

    def "__TestWebMvcTest"() {
        expect:
        mvc.perform(MockMvcRequestBuilders.get("/check.do"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .response
                .contentAsString == "ok"
    }
}
