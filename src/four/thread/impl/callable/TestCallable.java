package four.thread.impl.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaoshan
 * @create 2019-04-22 11:47
 */
public class TestCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Ticket ticket = new Ticket();
        final ReentrantLock reentrantLock = new ReentrantLock();


        MyCallable myCallable = new MyCallable(ticket,reentrantLock);


        FutureTask<Integer> ft = new FutureTask<>(myCallable);
        Thread t1 = new Thread(ft,"AA");
        t1.start();
        Integer ft_val = ft.get();//获取call方法的返回值


        MyCallable myCallable2 = new MyCallable(ticket,reentrantLock);
        FutureTask<Integer> ft2 = new FutureTask<>(myCallable2);
        Thread t2 = new Thread(ft2,"BB");
        t2.start();
        Integer ft2_val = ft2.get();//获取call方法的返回值


//        System.out.println();

    }
}

class MyCallable implements Callable<Integer> {

    private Ticket ticket;
    private ReentrantLock reentrantLock;

    public MyCallable(Ticket ticket, ReentrantLock reentrantLock){
        this.ticket = ticket;
        this.reentrantLock = reentrantLock;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("执行一次call()");
        try{
            reentrantLock.lock();
            for(int i = 0;i < 40;i++){
                ticket.sale();
            }
        }catch(Exception e){

        }finally {
            reentrantLock.unlock();
        }
        return 200;
    }
}

class Ticket{
    private int ticket = 30;

    public void sale(){
        if(ticket > 0){
            ticket--;
            System.out.println(Thread.currentThread().getName()+":出票。当前剩余票数："+ticket);
        }
    }
}