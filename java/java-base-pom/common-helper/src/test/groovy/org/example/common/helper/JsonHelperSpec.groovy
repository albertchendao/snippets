package org.example.common.helper

import spock.lang.Specification

class JsonHelperSpec extends Specification {

    static class TestDto {
        Long id
    }

    def "test toString"() {
        given:
        TestDto dto = new TestDto()
        dto.id = 1L

        expect:
        print JsonHelper.toString(dto)
        JsonHelper.toString(dto) == '{"id":1}'
    }
}
