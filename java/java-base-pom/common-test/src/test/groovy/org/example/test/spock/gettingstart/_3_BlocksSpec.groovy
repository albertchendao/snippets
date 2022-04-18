package org.example.test.spock.gettingstart

import org.apache.commons.io.IOUtils
import spock.lang.Specification

/**
 * 标签示例
 * 完整标签: given, when, then, expect, cleanup, where
 */
class _3_BlocksSpec extends Specification {

    /**
     * 通过 expect 标签进行断言
     * 隐式声明 given 标签
     */
    def "test expect"() {
        int a = 1
        int b = 2

        expect:
        Math.max(a, b) == b
    }

    /**
     * 通过 expect 标签进行断言
     * 显式声明 given 标签
     */
    def "test given-expect"() {
        given:
        int a = 1
        int b = 2

        expect:
        Math.max(a, b) == b
    }

    /**
     * 通过 then 标签进行断言
     */
    def "test when-then"() {
        when:
        int a = 1
        int b = 2

        then:
        Math.max(a, b) == b
    }

    /**
     * 通过 then 标签进行断言
     */
    def "test given-when-then"() {
        given:
        int a
        int b

        when:
        a = 1
        b = 2

        then:
        Math.max(a, b) == b
    }

    /**
     * 通过 cleanup 标签回收资源
     */
    def "test cleanup"() {
        given: "open InputStream"
        String input = "hello"
        InputStream stream = IOUtils.toInputStream(input)

        when:
        List<String> txts = IOUtils.readLines(stream)

        then:
        input == txts.get(0)

        cleanup: "close InputStream"
        stream.close()
    }
}