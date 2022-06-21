package org.example.spring.boot2.testannotation


import org.example.spring.boot2.controller.HelloController
import org.example.spring.boot2.service.HelloService
import org.example.spring.boot2.service.WorldService
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@WebMvcTest(HelloController)
class TestMockBean extends Specification {

    @Autowired
    private MockMvc mvc
    /**
     * @MockBean 会被完全替换
     */
    @MockBean
    private HelloService helloService
    /**
     * @SpyBean 支持部分替换
     */
    @SpyBean
    private WorldService worldService;

    def "__TestMockBean_MockBean"() {
        given:
        Mockito.when(helloService.sayHi()).thenReturn("hello")

        when:
        def respone = mvc.perform(MockMvcRequestBuilders.get("/hello"))

        then:
        respone.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .response
                .contentAsString == "hello"
    }

    def "__TestMockBean_SpyBean"() {
        given:
        Mockito.when(worldService.sayNo()).thenReturn("yes")

        when:
        def response = mvc.perform(MockMvcRequestBuilders.get("/world"))

        then:
        print(worldService.sayNo())
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .response
                .contentAsString == "world"
    }
}
