package org.example.test.spock.gettingstart

import spock.lang.Specification

/**
 * Mock, Stub, Spy 示例
 */
class _6_MockAndStubAndSpySpec extends Specification {

    /**
     * Mock() 方法创造虚拟对象, 用于验证方法调用次数
     */
    def "test Mock use invocation"() {
        given:
//        def list = Mock(List.class)
        List<Integer> list = Mock()

        when:
        list.add(1)

        then:
        1 * list.add(1)

//        0 * subscriber.receive("hello")      // zero calls
//        (1..3) * subscriber.receive("hello") // between one and three calls (inclusive)
//        (1.._) * subscriber.receive("hello") // at least one call
//        (_..3) * subscriber.receive("hello") // at most three calls
//        _ * subscriber.receive("hello")      // any number of calls, including zero

//        1 * subscriber.receive("hello") // a call to 'subscriber'
//        1 * _.receive("hello")          // a call to any mock object

//        1 * subscriber.receive("hello") // a method named 'receive'
//        1 * subscriber./r.*e/("hello")  // a method whose name matches the given regular expression

//        1 * subscriber.receive("hello")        // an argument that is equal to the String "hello"
//        1 * subscriber.receive(!"hello")       // an argument that is unequal to the String "hello"
//        1 * subscriber.receive()               // the empty argument list (would never match in our example)
//        1 * subscriber.receive(_)              // any single argument (including null)
//        1 * subscriber.receive(*_)             // any argument list (including the empty argument list)
//        1 * subscriber.receive(!null)          // any non-null argument
//        1 * subscriber.receive(_ as String)    // any non-null argument that is-a String
//        1 * subscriber.receive(endsWith("lo")) // an argument matching the given Hamcrest matcher
        // a String argument ending with "lo" in this case
//        1 * subscriber.receive({ it.size() > 3 && it.contains('a') })

//        1 * subscriber._(*_)     // any method on subscriber, with any argument list
//        1 * subscriber._         // shortcut for and preferred over the above

//        1 * _._                  // any method call on any mock object
//        1 * _                    // shortcut for and preferred over the above

//        1 * subscriber.receive("hello") // demand one 'receive' call on 'subscriber'
//        _ * auditing._                  // allow any interaction with 'auditing'
//        0 * _                           // don't allow any other interaction

        // 调用顺序验证
//        then:
//        2 * subscriber.receive("hello")
//        then:
//        1 * subscriber.receive("goodbye")
    }

    /**
     * Mock() 方法创造虚拟对象, 指定调用次数及返回值
     */
    def "test Mock use return"() {
        given:
        List<Integer> list = Mock()
        list.add(_) >> true >>> [false, true, false]
        2 * list.remove(_) >> true

        expect:
        list.add(1) == true
        list.add(2) == false
        list.add(3) == true
        list.add(4) == false
        list.remove(1) == true
        list.remove(2) == true
    }

    /**
     * Stub() 方法创造虚拟对象, 指定返回值, 不能指定调用次数
     */
    def "test Stub use return"() {
        given:
        List<Integer> list = Stub()
        list.add(_) >> true >>> [false, true, false]

        expect:
        list.add(1) == true
        list.add(2) == false
        list.add(3) == true
        list.add(4) == false
    }

    /**
     * Stub() 方法创造模拟对象
     */
    def "test Stub use thrown"() {
        given:
        List<Integer> list = Stub()
        list.add(_) >> { throw new IndexOutOfBoundsException() }

        when:
        list.add(1)

        then:
        thrown(IndexOutOfBoundsException.class)
    }

    /**
     * Spy() 方法包装真实对象
     */
    def "test Spy"() {
        given:
        List<Integer> list = Spy(ArrayList.class) {
            addAll(_) >> false
        }
        list.remove(_) >> false

        when:
        list.add(1)

        then:
        list.size() == 1
        list.get(0) == 1

        list.addAll([2, 3]) == false
        list.size() == 1
        list.get(0) == 1

        list.remove(0) == false
        list.size() == 1
        list.get(0) == 1
    }
}
