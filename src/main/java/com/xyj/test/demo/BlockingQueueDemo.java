package com.xyj.test.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author changfei
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strings = new ArrayBlockingQueue<>(3);
//        System.out.println(strings.add("a"));
//        System.out.println(strings.add("b"));
//        System.out.println(strings.add("c"));
        // add添加满时会报错，Queue full 队列已满
       //  System.out.println(strings.add("d"));
//        strings.remove("a");
//        strings.remove("b");
//        strings.remove("c");
        // 队列没有元素时，移除会报错，NoSuchElementException
        //strings.remove();
        // offer添加，成功返回true，失败返回false
//        System.out.println(strings.offer("a"));
//        System.out.println(strings.offer("b"));
//        System.out.println(strings.offer("c"));
//        System.out.println(strings.offer("d"));
        // poll取出元素时，成功返回值，失败返回null
//        System.out.println(strings.poll());
//        System.out.println(strings.poll());
//        System.out.println(strings.poll());
//        System.out.println(strings.poll());
        // put和take存储和取出时，会出现阻塞。
        // 采用超时的策略，过了时间就退出
        System.out.println(strings.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(strings.offer("b", 2L, TimeUnit.SECONDS));
        System.out.println(strings.offer("c", 2L, TimeUnit.SECONDS));
        System.out.println(strings.offer("d", 2L, TimeUnit.SECONDS));

    }

}
