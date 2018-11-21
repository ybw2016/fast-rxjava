package com.fast.rxjava2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public abstract class BaseRunClass {
    protected static final Logger logger = LoggerFactory.getLogger(BaseRunClass.class);

    public static void main(String[] args) {

    }

    public static void printTitle(String title) {
        System.out.println(title + ":");
    }

    public static void printLine() {
        System.out.println("=======================================");
    }

    public static void printLine(String newLineTitle) {
        printLine();
        printTitle(newLineTitle);
    }

    public static void sleep(int seconds) {
        try {
            System.out.printf("sleep %s seconds\n", seconds);
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepMillSeconds(int mill) {
        try {
            TimeUnit.MILLISECONDS.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void await(CountDownLatch countDownLatch) {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
