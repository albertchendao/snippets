package org.example.test.spock.caffeine

import com.github.benmanes.caffeine.cache.AsyncCacheLoader
import com.github.benmanes.caffeine.cache.AsyncLoadingCache
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.CacheLoader
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import spock.lang.Specification

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.LongAdder
import java.util.function.Supplier

/**
 * Caffeine 缓存测试
 */
class CaffeineSpec extends Specification {

    /**
     * 手动读写缓存
     */
    def "Manual data"() {
        given:
        // 初始化缓存，设置了1分钟的写过期，100的缓存最大个数
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build()
        int key1 = 1

        expect:
        // 使用getIfPresent方法从缓存中获取值。如果缓存中不存指定的值，则方法将返回 null：
        cache.getIfPresent(key1) == null
        // 如果缓存中不存在该 key, 则该函数将用于提供默认值，该值在计算后插入缓存中：
        cache.get(key1, { 2 }) == 2
        // 检查是否已加入缓存
        cache.getIfPresent(key1) == 2
        // 手动放入缓存
        cache.put(key1, 1)
        cache.getIfPresent(key1) == 1
    }

    /**
     * 同步加载数据
     */
    def "Loading data without loadAll"() {
        given:
        LongAdder adder = new LongAdder();
        // 初始化缓存，设置了1分钟的写过期，100的缓存最大个数
        LoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(new CacheLoader<Integer, Integer>() {
                    @Override
                    public Integer load(Integer key) {
                        adder.increment();
                        return key == 10 ? null : key;
                    }
                });

        expect:
        // get数据，取不到则从数据库中读取相关数据，该值也会插入缓存中：
        cache.get(1) == 1
        adder.intValue() == 1
        // 支持直接get一组值，支持批量查找
        cache.getAll([1, 2, 3]) == [1: 1, 2: 2, 3: 3]
        adder.intValue() == 3

        cache.get(10) == null
        adder.intValue() == 4
        // 不会缓存 null
        cache.get(10) == null
        adder.intValue() == 5

        // 会异常
//        cache.get(null) == null
    }

    /**
     * 同步加载数据
     */
    def "Loading data with loadAll"() {
        given:
        LongAdder adder = new LongAdder();
        // 初始化缓存，设置了1分钟的写过期，100的缓存最大个数
        LoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(new CacheLoader<Integer, Integer>() {
                    @Override
                    public Integer load(Integer key) {
                        adder.increment();
                        return key;
                    }

                    @Override
                    public Map<Integer, Integer> loadAll(Iterable<Integer> keys) {
                        adder.increment();
                        Map<Integer, Integer> result = new HashMap<>();
                        keys.forEach({ k -> result.put(k, k) })
                        return result;
                    }
                });

        expect:
        // get数据，取不到则从数据库中读取相关数据，该值也会插入缓存中：
        cache.get(1) == 1
        adder.intValue() == 1
        // 支持直接get一组值，支持批量查找
        cache.getAll([1, 2, 3]) == [1: 1, 2: 2, 3: 3]
        adder.intValue() == 2
    }

    /**
     * 异步加载数据
     */
    def "Loading data with Async"() {
        given:
        // 初始化缓存，设置了1分钟的写过期，100的缓存最大个数
        AsyncLoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .buildAsync(new CacheLoader<Integer, Integer>() {
                    @Override
                    public Integer load(Integer key) {
                        sleep(2000)
                        return key;
                    }
                });

        expect:
        // get数据，取不到则从数据库中读取相关数据，该值也会插入缓存中：
        println System.currentTimeMillis();
        cache.get(1).get() == 1
        println System.currentTimeMillis();
        cache.get(1).get() == 1
        println System.currentTimeMillis();
        cache.get(1).get() == 1
    }

    /**
     * 异步加载数据
     */
    def "Loading data with Async2"() {
        given:
        Random random = new Random();
        // 初始化缓存，设置了1分钟的写过期，100的缓存最大个数
        AsyncLoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(2000, TimeUnit.MILLISECONDS)
                .refreshAfterWrite(1500, TimeUnit.MILLISECONDS)
                .buildAsync(new AsyncCacheLoader<Integer, Integer>() {
                    @Override
                    CompletableFuture<Integer> asyncLoad(Integer key, Executor executor) {
                        return CompletableFuture.supplyAsync(new Supplier<Integer>() {
                            @Override
                            Integer get() {
                                def nextBoolean = random.nextBoolean()
                                Integer result = nextBoolean ? key : null;
                                sleep(1000);
                                println System.currentTimeMillis() + " " + nextBoolean + " cached " + result + ", expire at" + (System.currentTimeMillis() + 2000)
                                return result;
                            }
                        }, executor);
                    }

                    @Override
                    CompletableFuture<Integer> asyncReload(Integer key, Integer oldValue, Executor executor) {
                        return CompletableFuture.supplyAsync(new Supplier<Integer>() {
                            @Override
                            Integer get() {
                                def nextBoolean = random.nextBoolean()
                                Integer result = nextBoolean ? key : oldValue;
                                sleep(1000);
                                println System.currentTimeMillis() + " " + nextBoolean + " reload " + result + ", expire at" + (System.currentTimeMillis() + 2000)
                                return result;
                            }
                        }, executor);
                    }
                });

        when:
        // get数据，取不到则从数据库中读取相关数据，该值也会插入缓存中：
        100.times {
            println System.currentTimeMillis();
            sleep(500)
            println System.currentTimeMillis() + " get " + cache.get(1).get()
        }

        then:
        1 == 1
    }
}
