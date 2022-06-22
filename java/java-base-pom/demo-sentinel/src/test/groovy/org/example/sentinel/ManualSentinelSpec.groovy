package org.example.sentinel

import com.alibaba.csp.sentinel.Entry
import com.alibaba.csp.sentinel.SphU
import com.alibaba.csp.sentinel.slots.block.BlockException
import com.alibaba.csp.sentinel.slots.block.RuleConstant
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager
import spock.lang.Specification

import java.util.concurrent.atomic.LongAdder

/**
 * Sentinel 测试
 */
class ManualSentinelSpec extends Specification {

    def helloService

    def setup() {
        helloService = new HelloService()
        def rule = new FlowRule(
                resource: "HelloWorld",
                grade: RuleConstant.FLOW_GRADE_QPS,
                // Set limit QPS to 20.
                count: 20
        )
        FlowRuleManager.loadRules([rule])
    }

    def "test sentinel by manual"() {
        given:
        def total = 100
        def successCounter = new LongAdder()
        def errorCounter = new LongAdder()

        (1..total).forEach {
            Entry entry
            try {
                entry = SphU.entry("HelloWorld")
                helloService.say()
                successCounter.increment()
            } catch (Exception ex) {
                errorCounter.increment()
            } finally {
                if (entry != null) {
                    entry.exit()
                }
            }
        }

        expect:
        successCounter.longValue() == 20
        errorCounter.longValue() == 80
    }
}