package com.example.spring.boot2.rest

import groovyx.net.http.HttpBuilder
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * 使用 http-builder-ng
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestHttpBuilderNg extends Specification {

    def '__TestHttpBuilderNg'() {
        given:
        def httpBin = HttpBuilder.configure {
            request.uri = 'http://localhost:8080'
        }

        when:
        def response = httpBin.get {
            request.uri.path = '/check.do'
        }

        then:
        assert response == "ok"
    }
}
