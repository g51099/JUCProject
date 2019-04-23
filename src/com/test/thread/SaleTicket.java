package com.test.thread;

/**
 * @author xiaoshan
 * @create 2019-04-22 9:44
 */
public class SaleTicket {

    private int ticket = 40; //票

    public synchronized void sale(){
        if(ticket > 0){
            ticket--;
            System.out.println(Thread.currentThread().getName()+":售票，剩余票数："+ticket);
        }

    }

}

class TestThread{
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
