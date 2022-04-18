package org.example.test.spock.gettingstart

import spock.lang.Shared
import spock.lang.Specification

/**
 * 固定方法示例
 *
 * <pre>
 *  FixMethodsSpec.setupSpec()
 * for (testMethod in testMethods) {
 *     Specification spec = new FixMethodsSpec()
 *     spec.setup()
 *     spec.testMethod()
 *     spec.cleanup()
 * }
 * FixMethodsSpec.cleanupSpec()
 *</pre>
 */
class _2_FixMethodsSpec extends Specification {

    def NO_SHARED = new Object()
    @Shared
    def SHARED = new Object()

    def setupSpec() {
        println "setupSpec() 在 Specification 的所有测试前只执行一次, 类似 @BeforeClass"
    }

    def setup() {
        println "setup() 在 Specification 的每个测试前执行一次, 类似 @Before"
    }

    def cleanup() {
        println "cleanup() 在 Specification 的每个测试后执行一次, 类似 @After"
    }

    def cleanupSpec() {
        println "cleanupSpec() 在 Specification 的所有测试后只执行一次, 类似 @AfterClass"
    }

    def "test fix method 1"() {
        expect:
        println "测试方法 1, 类似 @Test, ${this.hashCode()}, ${NO_SHARED.hashCode()}, ${SHARED.hashCode()}"
    }

    def "test fix method 2"() {
        expect:
        println "测试方法 2, 类似 @Test, ${this.hashCode()}, ${NO_SHARED.hashCode()}, ${SHARED.hashCode()}"
    }
}