package com.xyj.test.demo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock测试
 */
class MyCache{
    private volatile Map<String, String> map = new HashMap<>();
    private ReentrantReadWriteLock readWriteLock =new ReentrantReadWriteLock();
    public void put(String key,String value){

        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入:"+key);
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成:");
        } catch (Exception e) {

        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取:");
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String s = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成:"+s);
        } catch (Exception e){

        } finally {
            readWriteLock.readLock().unlock();
        }
        List list = new ArrayList();
    }
}



public class Test {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i <= 5; i++) {
            final int temInt=i;
            new Thread(() -> {
                myCache.put(temInt+"",temInt+"");
            },String.valueOf(i)).start();
        }

        for (int i = 0; i <= 5; i++) {
            final int temInt=i;
            new Thread(() -> {
                myCache.get(temInt+"");
            },String.valueOf(i)).start();
        }
    }
}
