package org.example.test.spock.gettingstart

import spock.lang.Specification
import spock.lang.Unroll

/**
 * 数据驱动示例
 */
class _5_DataDrivenSpec extends Specification {

    def "maximum of two numbers 1"() {
        expect:
        Math.max(a, b) == c

        where:
        a = 3
        b = Math.random() * 100
        c = a > b ? a : b
    }

    def "maximum of two numbers 2"() {
        expect:
        Math.max(a, b) == c

        where:
        a | b || c
        1 | 3 || 3
        7 | 4 || 7
        0 | 0 || 0
    }

    @Unroll
    def "max(#a, #b) is #c with (<<)"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [1, 7, 0]
        b << [3, 4, 0]
        c << [3, 7, 0]
    }

    @Unroll
    def "max(#a, #b) is #c with (<<) multi"() {
        expect:
        Math.max(a, b) == c

        where:
        [a, b, c] << [[1, 3, 3], [7, 4, 7], [0, 0, 0]]
    }

    @Unroll
    def "max(#a, #b) is #c with mix"() {
        expect:
        Math.max(a, b) == c

        where:
        a | b
        1 | 3
        7 | 4
        0 | 0

        c << [3, 7, 0]
    }
}
