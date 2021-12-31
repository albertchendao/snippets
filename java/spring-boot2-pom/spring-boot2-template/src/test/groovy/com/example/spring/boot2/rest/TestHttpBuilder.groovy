package com.example.spring.boot2.rest

import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * 使用 http-builder
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestHttpBuilder extends Specification {

    def '__TestHttpBuilder'() {
        given:
        def client = new RESTClient("http://localhost:8080")

        when:
        def response = client.get(path: '/check.do')

        then:
        assert response.status == 200: 'response code should be 200 when tried to authenticate with valid credentials'
    }
}
