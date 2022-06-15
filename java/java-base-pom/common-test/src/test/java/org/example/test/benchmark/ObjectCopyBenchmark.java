package org.example.test.benchmark;

import com.esotericsoftware.kryo.Kryo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 对象拷贝性能测试
 */
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DeepCopyBenchmark {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Address implements Cloneable, Serializable {
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
    static class User implements Cloneable, Serializable {
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
        kryo.register(User.class);
        kryo.register(Address.class);
        return kryo;
    });

    /**
     * 浅拷贝 - 构造函数
     */
    @Benchmark
    public void testShallowCopyByConstructor(Blackhole blackhole) {
        User target = new User(origin.getFirstName(), origin.getLastName(), origin.getAddress());
        blackhole.consume(target);
    }

    /**
     * 浅拷贝 - setter
     */
    @Benchmark
    public void testShallowCopyBySetter(Blackhole blackhole) {
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
    public void testDeepCopyByConstructor(Blackhole blackhole) {
        Address targetAddress = new Address(origin.getAddress());
        User target = new User(origin.getFirstName(), origin.getLastName(), targetAddress);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - setter
     */
    @Benchmark
    public void testDeepCopyBySetter(Blackhole blackhole) {
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
    public void testDeepCopyByClone(Blackhole blackhole) {
        User target = (User) origin.clone();
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - Kryo
     */
    @Benchmark
    public void testDeepCopyByKryo(Blackhole blackhole) {
        User target = kryo.get().copy(origin);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - SerializationUtils
     */
    @Benchmark
    public void testDeepCopyBySerializationUtils(Blackhole blackhole) {
        User target = SerializationUtils.clone(origin);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - Gson
     */
    @Benchmark
    public void testDeepCopyByGson(Blackhole blackhole) {
        User target = gson.fromJson(gson.toJson(origin), User.class);
        blackhole.consume(target);
    }

    /**
     * 深拷贝 - Jackson
     */
    @Benchmark
    public void testDeepCopyByJackson(Blackhole blackhole) throws IOException {
        User target = objectMapper.readValue(objectMapper.writeValueAsString(origin), User.class);
        blackhole.consume(target);
    }
}