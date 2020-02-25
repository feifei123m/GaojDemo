package com.xyj.test.demo;

import java.util.concurrent.*;

public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {
        // 固定线程数为5的线程池
        //ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        // 单线程的线程池
        //ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        // 多线程的线程池
        //ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        // 一般要手动创建一个线程池,上面三种线程池可能会造成oom
        /**
         * 拒绝策略：
         * AbortPolicy执行不了直接报异常
         * CallerRunsPolicy执行不了的返回给调用者
         * DiscardOldestPolicy丢弃等待时间最长的
         * DiscardPolicy执行不了的直接丢弃
          */
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        try {
            for (int i = 1; i <=12 ; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"执行任务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
    }
}
