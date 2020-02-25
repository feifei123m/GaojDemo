package com.xyj.test.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    // 生产者
    public void increment() throws InterruptedException {
        lock.lock();
        try {
            // 1.判断 注意，多线程时不能用if判断，会造成虚假唤醒；
            while (number != 0) {
                // 线程等待，不能生产
                condition.await();
            }
            // 2.干活
            number++;
            // 3.唤醒
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 消费者
    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            // 1.判断
            while (number == 0) {
                // 不能消费，等待
                condition.await();
            }
            // 2.干活
            number--;
            // 3.唤醒
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


public class ProdConsumer_01 {

    public static void main(String[] args) {
        // 两个线程同时操作
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 0; i <= 5; i++) {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i <= 5; i++) {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}
