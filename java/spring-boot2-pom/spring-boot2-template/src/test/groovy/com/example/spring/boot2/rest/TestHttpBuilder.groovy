package com.example.spring.boot2.rest

import groovyx.net.http.RESTClient
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * 使用 http-builder
 */
class TestHttpBuilder extends Specification {

    def '__TestHttpBuilder'() {
        given:
        def client = new RESTClient("https://www.baidu.com")

        when:
        def response = client.get(path: '/')

        then:
        assert response.status == 200
        print(response.responseData)
    }
}
