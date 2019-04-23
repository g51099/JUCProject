package four.thread.impl.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaoshan
 * @create 2019-04-22 10:57
 */
public class TestThread {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        MyThread myThread = new MyThread(ticket);
        myThread.setName("AA");
        myThread.start();
        MyThread myThread2 = new MyThread(ticket);
        myThread2.setName("BB");
        myThread2.start();
    }
}

class MyThread extends Thread{

    private Ticket ticket;

    public MyThread(Ticket ticket){
        this.ticket = ticket;
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
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public void sale(){
        try{
            reentrantLock.lock();
            if(ticket > 0){
                ticket--;
                System.out.println(Thread.currentThread().getName()+":出票。当前剩余票数："+ticket);
            }

        }catch(Exception e){

        }finally{
            reentrantLock.unlock();
        }

    }
}