package com.xyj.test.demo;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareSource {
    // 一把锁，三把钥匙，满足条件时打开
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            // 1.判断
            while (number != 1) {
                c1.await();
            }
            // 2.干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            number = 2;
            // 3.唤醒,精确通知下一个线程
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            // 1.判断
            while (number != 2) {
                c2.await();
            }
            // 2.干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            // 3.唤醒,精确通知下一个线程
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            // 1.判断
            while (number != 3) {
                c3.await();
            }
            // 2.干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            // 3.唤醒,精确通知下一个线程
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SynchronousReentrantLockDemo {

    /**
     * A线程打印5次，B线程打印10次，C线程打印15次
     * 打印10轮
     *
     * @param args
     */
    public static void main(String[] args) {
        ShareSource shareSource = new ShareSource();

        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                shareSource.print5();
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                shareSource.print10();
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                shareSource.print15();
            }
        }, "CC").start();
    }
}
