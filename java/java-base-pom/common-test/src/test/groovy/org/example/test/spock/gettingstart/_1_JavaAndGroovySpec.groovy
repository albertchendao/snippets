package org.example.test.spock.gettingstart

import spock.lang.Specification

class _1_JavaAndGroovySpec extends Specification {

    def testByJava() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.remove("1");

        expect:
        list.size() == 1
    }

    def "test by Groovy"() {
        def list = ["1", "2"]
        list.remove("1")

        expect:
        list.size() == 1
    }
}
