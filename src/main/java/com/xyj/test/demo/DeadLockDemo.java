package com.xyj.test.demo;

import java.util.concurrent.TimeUnit;

class HoldLock implements Runnable {
    private String lockA;
    private String lockB;

    public HoldLock(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName()+"持有"+lockA+"等待"+lockB);
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName()+"持有"+lockB+"等待"+lockA);
            }
        }
    }
}


public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLock(lockA,lockB), "ThreadAAA").start();
        new Thread(new HoldLock(lockB,lockA), "ThreadBBB").start();
    }
}
