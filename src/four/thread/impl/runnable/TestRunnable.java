package four.thread.impl.runnable;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaoshan
 * @create 2019-04-22 11:08
 */
public class TestRunnable {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        final ReentrantLock lock = new ReentrantLock();

        MyRunnable r1 = new MyRunnable(ticket,lock);
        new Thread(r1,"AA").start();
        MyRunnable r2 = new MyRunnable(ticket,lock);
        new Thread(r2,"BB").start();

    }
}

class MyRunnable implements Runnable{

    private Ticket ticket;
    private ReentrantLock reentrantLock;

    public MyRunnable(Ticket ticket,ReentrantLock reentrantLock){
        this.ticket = ticket;
        this.reentrantLock = reentrantLock;
    }

    @Override
    public void run() {
        for(int i = 0;i < 40;i++){
            ticket.sale();
        }
    }
}

class Ticket{
    private int ticket = 30;

    public void sale(){
        while(ticket > 0){
            ticket--;
            System.out.println(Thread.currentThread().getName()+":出票。当前剩余票数："+ticket);
        }
    }
}