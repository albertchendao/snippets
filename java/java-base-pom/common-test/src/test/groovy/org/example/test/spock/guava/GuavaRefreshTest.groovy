package org.example.test.spock.guava

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.ListeningExecutorService
import com.google.common.util.concurrent.MoreExecutors
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class GuavaRefreshTest {

     static ListeningExecutorService backgroundRefreshPools = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));
     static LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    // 每次刷新缓存需要耗时 2 秒
                    Thread.sleep(2000);
                    String v = LocalDateTime.now().toString();
                   log("load to  " + v);
                    return v;
                }

                @Override
                public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                    return backgroundRefreshPools.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            // 每次刷新缓存需要耗时 2 秒
                            Thread.sleep(2000);
                            String v = LocalDateTime.now().toString();
                           log("reload to " + v);
                            return v;
                        }
                    });
                }
            });

    static void log(msg) {
        println "" + LocalDateTime.now() + " : " + Thread.currentThread().getName() + " : " + msg
    }

    static void main(String[] args) {
        // 先初始化
        cache.put("", LocalDateTime.now().toString());
        // 每秒刷新一次
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                try {
                   log("begin refresh");
                    String v = cache.get("");
                   log("end refresh with " + v);
                } catch (ExecutionException e) {

                }
            }
        };
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
//        executorService.scheduleWithFixedDelay(runner, 0, 1, TimeUnit.SECONDS);
        (1..10).each {
            log(cache.get("") + " : " + it);
            TimeUnit.SECONDS.sleep(1);
        }
//        executorService.shutdownNow()
        System.exit(0)
    }

}
