package com.test.demos;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xiaoshan
 * @create 2019-04-22 16:12
 *
 *  读写锁：
 *
 *  1个线程写，100个线程读
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {

        ReadWriteThread rwt = new ReadWriteThread();//资源类

        new Thread(()->{
            rwt.writeObj("Big Data refuel");
        },"AA").start();

        for(int i = 1;i <= 100;i++){
            new Thread(()->{
                rwt.readObj();
            },i+"").start();
        }
    }

}

class ReadWriteThread{

    private ReadWriteLock rw = new ReentrantReadWriteLock();
    private Object obj;

    public void writeObj(Object obj){
        rw.writeLock().lock();
        try{

            this.obj = obj;
            //写操作
            System.out.println(Thread.currentThread().getName()+"写入的内容是："+obj);
        }finally{
            rw.writeLock().unlock();
        }
    }

    public void readObj(){

        rw.readLock().lock();
        try{
            //读操作
            System.out.println(Thread.currentThread().getName()+"：读到的内容："+obj);

        }finally {
            rw.readLock().unlock();
        }
    }

}