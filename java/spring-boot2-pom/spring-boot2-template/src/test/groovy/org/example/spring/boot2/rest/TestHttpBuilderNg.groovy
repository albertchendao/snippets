package org.example.spring.boot2.rest

import groovyx.net.http.HttpBuilder
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * 使用 http-builder-ng
 */
class TestHttpBuilderNg extends Specification {

    def '__TestHttpBuilderNg'() {
        given:
        def httpBin = HttpBuilder.configure {
            request.uri = 'https://www.baidu.com'
        }

        when:
        def response = httpBin.get {
            request.uri.path = '/'
        }

        then:
        assert response != null
    }
}
