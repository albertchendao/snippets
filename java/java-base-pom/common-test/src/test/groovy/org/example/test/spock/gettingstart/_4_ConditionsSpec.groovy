package org.example.test.spock.gettingstart

import spock.lang.Specification

/**
 * 判断条件示例
 */
class _4_ConditionsSpec extends Specification {

    /**
     * 1. 使用返回 boolean 的语句
     * 2. 使用 assert 语句
     * 3. 使用返回 boolean 的方法
     * 4. 使用包括 assert 语句的方法
     */
    def "test conditions"() {
        given:
        List<Integer> list = new ArrayList<>()

        when:
        list.add(1)

        then:
        list.size() == 1
        list.get(0) == 1
        assert list.size() == 1
        assert list.get(0) == 1
        matchList(list)
        matchListAssert(list)
    }

    def matchList(list) {
        return list.size() == 1 && list.get(0) == 1
    }

    void matchListAssert(list) {
        assert list.size() == 1
        assert list.get(0) == 1
    }

    /**
     * 通过 with 省略被调用对象
     */
    def "test conditions use with"() {
        given:
        List<Integer> list = new ArrayList<>()

        when:
        list.add(1)

        then:
        with(list) {
            size() == 1
            get(0) == 1
        }
    }

    /**
     * 通过 verifyAll() 在失败时继续运行其他条件
     */
    def "test conditions use verifyAll"() {
        given:
        List<Integer> list = new ArrayList<>()

        when:
        list.add(1)

        then:
        verifyAll(list) {
            size() == 1
            get(0) == 1
        }
    }

    /**
     * 通过 number * method() 验证 Mock 对象方法调用次数
     * 更多用法参考 Mock()
     */
    def "test conditions use invocation"() {
        given:
        List<Integer> list = Mock()

        when:
        list.add(1)
        list.add(2)
        list.add(2)

        then:
        1 * list.add(1)
        2 * list.add(2)
    }

    /**
     * 通过 thrown 验证异常抛出, 注意只能在 then 标签中使用
     */
    def "test conditions use thrown"() {
        given:
        List<Integer> list = new ArrayList<>()

        when:
        println list.get(0)

        then:
        thrown(IndexOutOfBoundsException)
//        def ex = thrown(IndexOutOfBoundsException)
//        ex.message == "Array index out of range: " + 0
    }

    /**
     * 通过 thrown 验证异常抛出, 注意只能在 then 标签中使用
     */
    def "test conditions use notThrown"() {
        given:
        List<Integer> list = new ArrayList<>()
        list.add(1)

        when:
        println list.get(0)

        then:
        notThrown(IndexOutOfBoundsException)
    }
}
