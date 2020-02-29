package com.xyj.test.demo;

import java.util.Random;

/**
 * @author chang
 * @date 2020/2/28 12:53
 */
public class G1Demo {

    public static void main(String[] args) {

        String start = "start";

        while (true) {
            start += start + new Random().nextInt(77777777) + new Random().nextInt(88888888);
            start.intern();
        }
    }
}
