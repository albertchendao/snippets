package org.example.test.thread;

import java.util.concurrent.atomic.AtomicBoolean;

public class SafeCloseTests extends Thread {

    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread worker;

    @Override
    public void run() {
        while (running.get()) {
            long nano = System.currentTimeMillis();
            if (nano % 10000 == 0) {
                System.out.println(Thread.currentThread().getName() + " - " + (nano / 1000));
            }
        }
    }

    public void shutdown() {
        System.out.println("shutdown");
        this.running.set(false);
    }

    public void go() {
        while (this.worker != null && this.worker.isAlive()) {
            running.set(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        worker = new Thread(this);
        running.set(true);
        worker.start();
    }

    public static void main(String[] args) {
        SafeCloseTests runner = new SafeCloseTests();
        runner.go();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runner.shutdown();
        runner.go();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runner.shutdown();
    }
}

