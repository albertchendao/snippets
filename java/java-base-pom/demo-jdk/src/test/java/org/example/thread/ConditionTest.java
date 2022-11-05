package org.example.thread;

import org.junit.Test;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition可以通俗的理解为条件队列。
 * 当一个线程在调用了await方法以后，直到线程等待的某个条件为真的时候才会被唤醒。
 * 这种方式为线程提供了更加简单的等待/通知模式。
 * Condition必须要配合锁一起使用，因为对共享状态变量的访问发生在多线程环境下。
 * 一个Condition的实例必须与一个Lock绑定，因此Condition一般都是作为Lock的内部实现。
 */
public class ConditionTest {

    private static final int queueSize = 10;
    private static final PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);
    private static final Lock lock = new ReentrantLock();
    private static final Condition notFull = lock.newCondition();
    private static final Condition notEmpty = lock.newCondition();

    @Test
    public void testQueue() throws Exception {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        producer.start();
        consumer.start();
        Thread.sleep(0);
        producer.interrupt();
        consumer.interrupt();
    }

    static class Producer extends Thread {
        @Override
        public void run() {
            produce();
        }

        volatile boolean flag = true;

        private void produce() {
            while (flag) {
                lock.lock();
                try {
                    while (queue.size() == queueSize) {
                        try {
                            System.out.println("队列满，等待有空余空间");
                            notFull.await();
                        } catch (InterruptedException e) {
                            flag = false;
                        }
                    }
                    queue.offer(1);        //每次插入一个元素
                    notEmpty.signal();
                    System.out.println("向队列取中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class Consumer extends Thread {
        @Override
        public void run() {
            consume();
        }

        volatile boolean flag = true;

        private void consume() {
            while (flag) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("队列空，等待数据");
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            flag = false;
                        }
                    }
                    queue.poll();                //每次移走队首元素
                    notFull.signal();
                    System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
