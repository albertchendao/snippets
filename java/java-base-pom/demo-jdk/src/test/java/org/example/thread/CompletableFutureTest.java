package org.example.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * CompletableFuture 测试
 *
 * @author Albert
 * @version 1.0
 * @since 2023/5/4 4:11 PM
 */
@Slf4j
public class CompletableFutureTest {

    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static class RunA implements Runnable {
        private int num;

        public RunA(int num) {
            this.num = num;
        }

        @Override
        @SneakyThrows
        public void run() {
            System.out.println(System.currentTimeMillis() + " : " + num + " begin");
            Thread.sleep(1000);
            System.out.println(System.currentTimeMillis() + " : " + num + "   end");
        }
    }

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        List<CompletableFuture> futures = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            futures.add(CompletableFuture.runAsync(new RunA(i), executor));
        }
        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            all.get(4, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("", e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public <T> List<T> getAllCompleted(List<CompletableFuture<T>> futuresList, long timeout, TimeUnit unit) {
        CompletableFuture<Void> allFuturesResult = CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
        try {
            allFuturesResult.get(timeout, unit);
        } catch (Exception e) {
            // you may log it
        }
        return futuresList
                .stream()
                .filter(future -> future.isDone() && !future.isCompletedExceptionally()) // keep only the ones completed
                .map(CompletableFuture::join) // get the value from the completed future
                .collect(Collectors.<T>toList()); // collect as a list
    }

}
