package com.test.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaoshan
 * @create 2019-04-22 9:44
 */
public class SaleTicket2 {

    private final ReentrantLock lock = new ReentrantLock();
    private int ticket = 40; //票

    public void sale(){

        try{
            lock.lock();
            if(ticket > 0){
                ticket--;
                System.out.println(Thread.currentThread().getName()+":售票，剩余票数："+ticket);
            }
        }catch(Exception e){

        }finally{
            lock.unlock();
        }

    }

}

class TestThread2{
    public static void main(String[] args) {
        SaleTicket ticket = new SaleTicket();
        new Thread(() -> {
            for(int i = 0;i < 40;i++){
                ticket.sale();
            }
        }, "AA").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i < 40;i++){
                    ticket.sale();
                }
            }
        }, "BB").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i < 40;i++){
                    ticket.sale();
                }
            }
        }, "CC").start();


    }
}
