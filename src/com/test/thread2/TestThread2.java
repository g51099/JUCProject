package com.test.thread2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaoshan
 * @create 2019-04-22 14:44
 */
public class TestThread2 {
    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                ticket.print5(i);
            }
        }, "AA").start();

        new Thread(()->{
            for(int i = 1;i <= 10;i++){
                ticket.print10(i);
            }
        },"BB").start();

        new Thread(()->{
            for(int i = 1;i <= 10;i++){
                ticket.print15(i);
            }
        },"CC").start();

    }

}

class Ticket {

    private Lock lock = new ReentrantLock();
    Condition cd = lock.newCondition();
    Condition cd2 = lock.newCondition();
    Condition cd3 = lock.newCondition();
    private int number = 1;

    public void print5(int totalCount) {
        lock.lock();
        try{
            if (number != 1) {
                try {
                    cd.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + ":第" + totalCount + "轮，第" + i + "次");
            }

            //切换到下一个线程
            number = 2;

            //唤醒
            cd2.signal();
        }catch(Exception e){

        }finally{
            lock.unlock();
        }

    }

    public void print10(int totalCount) {
        lock.lock();
        try{
            if (number != 2) {
                try {
                    cd2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":第" + totalCount + "轮，第" + i + "次");
            }

            //切换到下一个线程
            number = 1;

            //唤醒
            cd3.signal();
        }catch(Exception e){

        }finally{
            lock.unlock();
        }

    }

    public void print15(int totalCount) {
        lock.lock();
        try{
            if (number != 3) {
                try {
                    cd3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + ":第" + totalCount + "轮，第" + i + "次");
            }

            //切换到下一个线程
            number = 1;

            //唤醒
            cd.signal();
        }catch(Exception e){

        }finally{
            lock.unlock();
        }

    }

}

