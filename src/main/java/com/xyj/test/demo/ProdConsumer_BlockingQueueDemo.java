package com.xyj.test.demo;

import ch.qos.logback.classic.turbo.TurboFilter;

import javax.naming.PartialResultException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MySource {
    // 开始的标志，这个值应该对所有的线程有可见性
    private volatile boolean FLAG = true;
    // 初始是0
    private AtomicInteger atomicInteger = new AtomicInteger();
    // 传入一个队列,此处应该设计成接口，适配所有的队列类型
    private BlockingQueue<String> blockingQueue = null;

    public MySource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        // 打印传入的队列名称
        System.out.println(blockingQueue.getClass().getName());
    }

    // 生产者
    public void prod() throws Exception {
        // 1.判断
        String data = null;
        boolean result;
        while (FLAG) {
            // 条件成立，执行++i
            data = atomicInteger.incrementAndGet() + "";
            // 生产完成后放入队列中
            result = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (result) {
                System.out.println(Thread.currentThread().getName() + "\t 加入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 加入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 生产停止" + FLAG);
    }

    // 消费者
    public void consumer() throws Exception {

        String poll = null;
        while (FLAG) {
            // 消费
            poll = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (poll == null || poll.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过时间没有消费成功，退出消费");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列" + poll + "成功");
        }
    }

    public void stop() throws Exception {
        this.FLAG = false;
    }
}


public class ProdConsumer_BlockingQueueDemo {

    public static void main(String[] args) throws Exception{
        // 初始有队列大小为10
        MySource mySource = new MySource(new ArrayBlockingQueue<>(10));
        // 生产线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
            try {
                mySource.prod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Prod").start();
        // 消费线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
            try {
                mySource.consumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒钟时间到了，生产停止");
        mySource.stop();
    }
}
