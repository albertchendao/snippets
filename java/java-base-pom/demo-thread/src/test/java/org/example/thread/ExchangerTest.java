package org.example.thread;

import org.junit.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * Exchanger 测试
 * Exchanger 就是线程之间的数据交换器，只能用于两个线程之间的数据交换。
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/27 7:31 PM
 */
public class ExchangerTest {

    /**
     * 普通交换, 线程一直阻塞，直到其他任意线程和它交换数据，或者被线程中断；
     */
    @Test
    public void testExchange() throws Exception {
        Exchanger exchanger = new Exchanger();

        new Thread(() -> {
            try {
                Object data = "-AAA";
                System.out.println(Thread.currentThread().getName() + data);
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Object data = "-BBB";
                System.out.println(Thread.currentThread().getName() + data);
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 测试超时
     */
    @Test
    public void testExchangeTimeout() throws Exception {
        Exchanger exchanger = new Exchanger();

        Thread thread = new Thread(() -> {
            try {
                Object data = "-AAA";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data, 2000L, TimeUnit.MILLISECONDS);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        Thread.sleep(3000L);
    }

    /**
     * 测试中断
     */
    @Test
    public void testExchangeInterrupt() throws Exception {
        Exchanger exchanger = new Exchanger();

        Thread thread = new Thread(() -> {
            try {
                Object data = "-AAA";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();

        // 线程中断
        Thread.sleep(3000L);
        thread.interrupt();
    }
}