package com.example.spring.boot2.testannotation

import com.example.spring.boot2.resp.UserResp
import com.example.spring.boot2.service.DetailsServiceClient
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import spock.lang.Specification

/**
 * @RestClientTest 测试对 REST 客户端的操作
 */
@RestClientTest(DetailsServiceClient)
class TestRestClientTest extends Specification {

    @Autowired
    private DetailsServiceClient client;
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private ObjectMapper objectMapper;

    def "__TestRestClientTest"() {
        given:
        def v = new UserResp("John Smith", "john")
        def s = objectMapper.writeValueAsString(v)
        server.expect(MockRestRequestMatchers.requestTo("/john/details"))
                .andRespond(MockRestResponseCreators.withSuccess(s, MediaType.APPLICATION_JSON))

        expect:
        client.getUserDetails("john") == v
    }
}
