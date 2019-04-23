package com.test.demos;

import java.util.concurrent.CountDownLatch;

/**
 * @author xiaoshan
 * @create 2019-04-22 16:21
 *
 *      减少计数：
 *
 * 班长锁门例子。
 *
 * 6个线程依次执行完毕，最后才会锁门
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(6);


        for(int i = 1;i <= 6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"同学走了。");
                cdl.countDown();
            },i+"").start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"班长锁门。");
    }

}


