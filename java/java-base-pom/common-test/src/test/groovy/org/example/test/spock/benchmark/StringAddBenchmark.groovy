package org.example.test.spock.benchmark

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole

import java.util.concurrent.TimeUnit

/**
 * 字符串追加基准测试
 *
 * @author Albert
 * @version 1.0
 * @since 2022/3/15 3:29 下午
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class StringAddBenchmark {

    @Param(value = ["10", "50", "100"])
    int length

    @Benchmark
    void testStringAdd(Blackhole blackhole) {
        String a = ""
        for (int i = 0; i < length; i++) {
            a += i
        }
        blackhole.consume(a)
    }

    @Benchmark
    void testStringBuilderAdd(Blackhole blackhole) {
        StringBuilder sb = new StringBuilder()
        for (int i = 0; i < length; i++) {
            sb.append(i)
        }
        blackhole.consume(sb.toString())
    }
}
