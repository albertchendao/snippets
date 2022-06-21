package org.example.spring.boot2.controller


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@WebMvcTest(CheckController)
class CheckControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    def "check.do"() {
        expect: "Status is 200 and the response is 'ok'"
        mvc.perform(MockMvcRequestBuilders.get("/check.do"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .response
                .contentAsString == "ok"
    }
}
