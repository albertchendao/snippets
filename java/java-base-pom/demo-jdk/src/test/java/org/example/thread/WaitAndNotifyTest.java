package org.example.thread;

/**
 * 对象 wati, notify 测试
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/8 8:25 PM
 */
public class WaitAndNotifyTest {

    static class Source {
        private int num = 0;

        public synchronized void increase() throws InterruptedException {
            if (num != 0) {
                this.wait();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            this.notifyAll();
        }

        public synchronized void decrease() throws InterruptedException {
            if (num == 0) {
                this.wait();
            }
            num--;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            this.notifyAll();
        }
    }

    public static void main(String[] args) {
        Source s = new Source();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    s.increase();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Producer A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    s.decrease();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Consumer A").start();

    }
}
