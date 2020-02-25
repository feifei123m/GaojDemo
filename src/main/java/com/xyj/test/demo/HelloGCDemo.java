package com.xyj.test.demo;


import java.util.concurrent.TimeUnit;

public class HelloGCDemo {

    public static void main(String[] args) {
        System.out.println("HelloGC...");
        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        long totalMemory = Runtime.getRuntime().totalMemory();
//        long maxMemory = Runtime.getRuntime().maxMemory();
//        System.out.println("初始堆内存："+(totalMemory/(double)1024/1024)+"MB");
//        System.out.println("最大堆内存："+(maxMemory/(double)1024/1024)+"MB");

    }
}
