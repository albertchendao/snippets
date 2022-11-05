package org.example.test.benchmark;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.kryo.Kryo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.nustaq.serialization.FSTConfiguration;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 对象拷贝性能测试
 * <p>
 * Kryo 不忽略注册
 * Benchmark                                          Mode  Cnt      Score       Error   Units
 * ObjectCloneBenchmark.testDeepCloneByClone           thrpt    5      0.149 ±     0.027  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByCloneable       thrpt    5      0.005 ±     0.001  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByConstructor     thrpt    5      0.200 ±     0.025  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByFST             thrpt    5      0.002 ±     0.001  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByFastjson        thrpt    5      0.003 ±     0.001  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByGson            thrpt    5      0.001 ±     0.001  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByJackson         thrpt    5      0.002 ±     0.001  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByKryo            thrpt    5      0.011 ±     0.012  ops/ns
 * ObjectCloneBenchmark.testDeepCloneBySerializable    thrpt    5     ≈ 10⁻⁴              ops/ns
 * ObjectCloneBenchmark.testDeepCloneBySetter          thrpt    5      0.075 ±     0.008  ops/ns
 * ObjectCloneBenchmark.testShallowCloneByConstructor  thrpt    5      0.242 ±     0.237  ops/ns
 * ObjectCloneBenchmark.testShallowCloneBySetter       thrpt    5      0.128 ±     0.109  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByClone            avgt    5     32.664 ±    20.544   ns/op
 * ObjectCloneBenchmark.testDeepCloneByCloneable        avgt    5    866.573 ±   103.993   ns/op
 * ObjectCloneBenchmark.testDeepCloneByConstructor      avgt    5     55.672 ±   133.602   ns/op
 * ObjectCloneBenchmark.testDeepCloneByFST              avgt    5   4192.740 ±  4952.356   ns/op
 * ObjectCloneBenchmark.testDeepCloneByFastjson         avgt    5   2031.484 ±   807.410   ns/op
 * ObjectCloneBenchmark.testDeepCloneByGson             avgt    5   5238.344 ±  1802.211   ns/op
 * ObjectCloneBenchmark.testDeepCloneByJackson          avgt    5   3937.188 ±  2033.422   ns/op
 * ObjectCloneBenchmark.testDeepCloneByKryo             avgt    5    657.580 ±  1465.793   ns/op
 * ObjectCloneBenchmark.testDeepCloneBySerializable     avgt    5  31990.462 ± 35850.332   ns/op
 * ObjectCloneBenchmark.testDeepCloneBySetter           avgt    5     68.826 ±     8.624   ns/op
 * ObjectCloneBenchmark.testShallowCloneByConstructor   avgt    5     17.870 ±    13.091   ns/op
 * ObjectCloneBenchmark.testShallowCloneBySetter        avgt    5     48.773 ±   126.757   ns/op
 *
 * Kryo 忽略注册
 * Benchmark                                            Mode  Cnt      Score       Error   Units
 * ObjectCloneBenchmark.testDeepCloneByClone           thrpt    5      0.087 ±     0.107  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByCloneable       thrpt    5      0.003 ±     0.006  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByConstructor     thrpt    5      0.177 ±     0.015  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByFST             thrpt    5      0.002 ±     0.001  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByFastjson        thrpt    5      0.003 ±     0.001  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByGson            thrpt    5      0.001 ±     0.001  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByJackson         thrpt    5      0.002 ±     0.001  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByKryo            thrpt    5      0.013 ±     0.003  ops/ns
 * ObjectCloneBenchmark.testDeepCloneBySerializable    thrpt    5     ≈ 10⁻⁴              ops/ns
 * ObjectCloneBenchmark.testDeepCloneBySetter          thrpt    5      0.026 ±     0.043  ops/ns
 * ObjectCloneBenchmark.testShallowCloneByConstructor  thrpt    5      0.276 ±     0.086  ops/ns
 * ObjectCloneBenchmark.testShallowCloneBySetter       thrpt    5      0.148 ±     0.078  ops/ns
 * ObjectCloneBenchmark.testDeepCloneByClone            avgt    5     27.715 ±    10.924   ns/op
 * ObjectCloneBenchmark.testDeepCloneByCloneable        avgt    5    617.493 ±  1072.770   ns/op
 * ObjectCloneBenchmark.testDeepCloneByConstructor      avgt    5     38.348 ±    32.586   ns/op
 * ObjectCloneBenchmark.testDeepCloneByFST              avgt    5   3686.089 ±  4626.033   ns/op
 * ObjectCloneBenchmark.testDeepCloneByFastjson         avgt    5   1805.038 ±   142.984   ns/op
 * ObjectCloneBenchmark.testDeepCloneByGson             avgt    5   3605.362 ±   332.386   ns/op
 * ObjectCloneBenchmark.testDeepCloneByJackson          avgt    5   2524.917 ±   318.746   ns/op
 * ObjectCloneBenchmark.testDeepCloneByKryo             avgt    5    469.024 ±   353.284   ns/op
 * ObjectCloneBenchmark.testDeepCloneBySerializable     avgt    5  33461.071 ± 20407.874   ns/op
 * ObjectCloneBenchmark.testDeepCloneBySetter           avgt    5     63.610 ±    45.378   ns/op
 * ObjectCloneBenchmark.testShallowCloneByConstructor   avgt    5     12.517 ±     0.735   ns/op
 * ObjectCloneBenchmark.testShallowCloneBySetter        avgt    5     30.310 ±     3.728   ns/op
 */
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ObjectCloneBenchmark extends BaseBenchmark {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address implements Cloneable, Serializable {
        private static final long serialVersionUID = -7468783862363768419L;
        private String street;
        private String city;
        private String country;

        public Address(Address address) {
            this(address.street, address.city, address.country);
        }

        @Override
        public Object clone() {
            try {
                return (Address) super.clone();
            } catch (CloneNotSupportedException e) {
                return new Address(this.street, this.getCity(), this.getCountry());
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User implements Cloneable, Serializable {
        private static final long serialVersionUID = -6684826629561606228L;
        private String firstName;
        private String lastName;
        private Address address;

        @Override
        public Object clone() {
            User user = null;
            try {
                user = (User) super.clone();
            } catch (CloneNotSupportedException e) {
                user = new User(
                        this.getFirstName(), this.getLastName(), this.getAddress());
            }
            user.address = (Address) this.address.clone();
            return user;
        }
    }

    /**
     * 被拷贝对象
     */
    private static final User origin = new User("Prime", "Minister",
            new Address("Downing St 10", "London", "England"));
    /**
     * GSON 工具, Gson 类线程安全
     */
    private static final Gson gson = new Gson();
    /**
     * Jackson 工具, ObjectMapper 类线程安全
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Kryo 工具,  Kryo 类不是线程安全的
     */
    private static final ThreadLocal<Kryo> kryo = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
//        kryo.register(User.class);
//        kryo.register(Address.class);
        kryo.setRegistrationRequired(false);
        return kryo;
    });
    /**
     * FST 工具, 是线程安全的，但是为了防止频繁调用时其成为性能瓶颈，一般会使用TreadLocal为每个线程分配一个FSTConfiguration
     */
    private final ThreadLocal<FSTConfiguration> configuration = ThreadLocal.withInitial(FSTConfiguration::createDefaultConfiguration);

    /**
     * 浅拷贝 - 构造函数
     */
    @Benchmark
    public void testShallowCloneByConstructor(Blackhole blackhole) {
        User target = new User(origin.getFirstName(), origin.getLastName(), origin.getAddress());
        blackhole.consume(target);
    }

    /**
     * 浅拷贝 - setter
     */
    @Benchmark
    public void testShallowCloneBySetter(Blackhole blackhole) {
        User target = new User();
        target.setFirstName(origin.getFirstName());
        target.setLastName(origin.getLastName());
        target.setAddress(origin.getAddress());
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - 构造函数
     */
    @Benchmark
    public void testDeepCloneByConstructor(Blackhole blackhole) {
        Address targetAddress = new Address(origin.getAddress());
        User target = new User(origin.getFirstName(), origin.getLastName(), targetAddress);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - setter
     */
    @Benchmark
    public void testDeepCloneBySetter(Blackhole blackhole) {
        Address address = origin.getAddress();
        Address targetAddress = new Address();
        targetAddress.setStreet(address.getStreet());
        targetAddress.setCity(address.getCity());
        targetAddress.setCountry(address.getCountry());

        User target = new User();
        target.setFirstName(origin.getFirstName());
        target.setLastName(origin.getLastName());
        target.setAddress(targetAddress);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - Clone 接口
     */
    @Benchmark
    public void testDeepCloneByClone(Blackhole blackhole) {
        User target = (User) origin.clone();
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - ObjectUtils.clone
     */
    @Benchmark
    public void testDeepCloneByCloneable(Blackhole blackhole) {
        User target = ObjectUtils.clone(origin);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - Kryo
     */
    @Benchmark
    public void testDeepCloneByKryo(Blackhole blackhole) {
        User target = kryo.get().copy(origin);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - FST
     */
    @Benchmark
    public void testDeepCloneByFST(Blackhole blackhole) {
        User target = configuration.get().deepCopy(origin);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - Serializable
     */
    @Benchmark
    public void testDeepCloneBySerializable(Blackhole blackhole) {
        User target = SerializationUtils.clone(origin);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - Gson
     */
    @Benchmark
    public void testDeepCloneByGson(Blackhole blackhole) {
        User target = gson.fromJson(gson.toJson(origin), User.class);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - Jackson
     */
    @Benchmark
    public void testDeepCloneByJackson(Blackhole blackhole) throws IOException {
        User target = objectMapper.readValue(objectMapper.writeValueAsString(origin), User.class);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - Fastjson
     */
    @Benchmark
    public void testDeepCloneByFastjson(Blackhole blackhole) {
        User target = JSON.parseObject(JSON.toJSONString(origin), User.class);
        blackhole.consume(target);
    }

    public static void main(String[] args) throws RunnerException {
        run(ObjectCloneBenchmark.class);
//        User target = ObjectUtils.clone(origin);
//        System.out.println(target);

//        boolean isPublic = Modifier.isPublic(User.class.getModifiers());
//        System.out.println(isPublic);
    }

}