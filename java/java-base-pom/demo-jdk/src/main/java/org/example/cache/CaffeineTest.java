package org.example.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CaffeineTest {

    private static String[] keys = {"albert", "chen"};

    private static AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .maximumSize(100)
            .recordStats()
            .buildAsync(key -> {
                System.out.println(System.currentTimeMillis() + " Begin Loading for " + key);
                Thread.sleep(500);
                System.out.println(System.currentTimeMillis() + " End   Loading for " + key);
                return key + " me";
            });

    public static class Runner implements Runnable {

        private Random random = new Random();

        @Override
        public void run() {
            while (true) {
                try {
                    String key = random.nextBoolean() ? keys[0] : keys[1];
                    CompletableFuture<String> result = cache.get(key);
                    result.get();
                    Thread.sleep(100);
                } catch (Exception e) {

                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
