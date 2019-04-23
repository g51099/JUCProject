package com.test.mianshiti;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaoshan
 * @create 2019-04-22 20:45
 *
 * 两个线程，一个线程打印1-52，另一个打印字母A-Z打印顺序为12A34B...5152Z,
要求用线程间通信

 */
public class TestMianShiTi {

    public static void main(String[] args) {
        Resource rs = new Resource(1,'A');

        new Thread(()->{
            for(int i = 0;i < 64;i++){
                rs.printNumber(52);
            }

        },"数字线程").start();
        new Thread(()->{
            for(int i = 0;i < 64;i++){
                rs.printLetter('Z');
            }
        },"字母").start();



    }
}

class Resource{

    private Lock lock = new ReentrantLock();
    Condition cdNumber = lock.newCondition();
    Condition cdLetter = lock.newCondition();
    int threadNo = 1;//1 代表数字线程   2 代表字母线程
    int number ;
    char letter ;

    public Resource(int initNumber,char initLetter){
        number = initNumber;
        letter = initLetter;
    }


    //打印数字1-52
    public void printNumber(int endNumber){

        lock.lock();
        try{
            if(threadNo != 1){
                cdNumber.await(); //等待
            }

//            if(number <= endNumber){
                //打印两个数字 12A 34B 56C
                System.out.println(Thread.currentThread().getName()+"：正在打印数字："+number);
                number++;

                if(number % 2 != 0){
                    threadNo = 2;
                    cdLetter.signal();
                }
//            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    //打印字母A-Z
    public void printLetter(char endLetter){

        lock.lock();
        try{
            if(threadNo != 2){
                cdLetter.await(); //等待
            }

//            if(letter != endLetter+1){
                //打印两个数字 12A 34B 56C
                System.out.println(Thread.currentThread().getName()+"：正在打印字母《"+letter+"》");
                letter++;

                threadNo = 1;
                cdNumber.signal();
//            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}