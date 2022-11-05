package org.example.test.benchmark;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * JSON 序列化测试
 *
 * Benchmark                               Mode  Cnt       Score        Error   Units
 * JsonBenchmark.testJsonByHashMap        thrpt    5    2331.225 ±    305.801  ops/ms
 * JsonBenchmark.testJsonByLinkedHashMap  thrpt    5    2329.783 ±    162.936  ops/ms
 * JsonBenchmark.testJsonByPure           thrpt    5  808316.194 ± 261900.340  ops/ms
 * JsonBenchmark.testJsonByHashMap         avgt    5       0.002 ±      0.001   ms/op
 * JsonBenchmark.testJsonByLinkedHashMap   avgt    5       0.002 ±      0.001   ms/op
 * JsonBenchmark.testJsonByPure            avgt    5      ≈ 10⁻⁵                ms/op
 *
 * @author Albert
 * @version 1.0
 * @since 2022/10/31 10:52 AM
 */
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JsonBenchmark extends BaseBenchmark {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String firstName;
        private String lastName;
    }

    private static final String JSON_STR = "{\"firstName\":\"Albert\",\"lastName\":\"Chen\"}";

    /**
     * 不序列化直接返回
     */
    @Benchmark
    public void testJsonByPure(Blackhole blackhole) {
        blackhole.consume(JSON_STR);
    }

    /**
     * JSON 转对象再转 JSON - LinkedHashMap
     */
    @Benchmark
    public void testJsonByLinkedHashMap(Blackhole blackhole) {
        Map<String, Object> map = JSONObject.parseObject(JSON_STR, new TypeReference<LinkedHashMap<String, Object>>() {
        });
        String s = JSON.toJSONString(map);
        blackhole.consume(s);
    }

    /**
     * JSON 转对象再转 JSON - HashMap
     */
    @Benchmark
    public void testJsonByHashMap(Blackhole blackhole) {
        Map<String, Object> map = JSONObject.parseObject(JSON_STR, new TypeReference<HashMap<String, Object>>() {
        });
        String s = JSON.toJSONString(map);
        blackhole.consume(s);
    }

    public static void main(String[] args) throws RunnerException {
        run(JsonBenchmark.class);
    }
}
