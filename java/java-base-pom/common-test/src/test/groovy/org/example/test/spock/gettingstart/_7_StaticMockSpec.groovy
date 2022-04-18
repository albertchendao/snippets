package org.example.test.spock.gettingstart

import org.apache.commons.lang3.StringUtils
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.powermock.reflect.Whitebox
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

/**
 * 静态方法 mock
 */
@RunWith(PowerMockRunner)
@PowerMockRunnerDelegate(Sputnik)
@PrepareForTest([StringUtils])
class _7_StaticMockSpec extends Specification {

    def setup() {
        PowerMockito.mockStatic(StringUtils)
    }

    def "test static field"() {
        given:
        def result = "hello"
        Whitebox.setInternalState(StringUtils, "EMPTY", result)

        expect:
        StringUtils.EMPTY == result
    }

    def "test static method"() {
        given:
        PowerMockito.when(StringUtils.isEmpty(Mockito.any())).thenReturn(true)

        expect:
        StringUtils.isEmpty("") == true
    }
}
