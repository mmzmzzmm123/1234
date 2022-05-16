package study.Demo.JUC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



class Walk {
    public static int num = 100;
    public static Walk walk = new Walk();
    // 静态  可以把static去掉看看什么结果
    public synchronized static int run(){
        int i = 0;
        while (i < 5) {
            try {
                num --;
                i++;
                System.out.println(Thread.currentThread().getName()+":"+num);
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return num ;
    }
    // 非静态
    public  synchronized int  walk(){
        int i = 0;
        while (i < 5) {
            try {
                num --;
                i++;
                System.out.println(Thread.currentThread().getName()+":"+num);
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return num ;
    }
}

class T3 implements Runnable {
    @Override
    public void run() {
//        Walk walk = new Walk();
        Walk walk = Walk.walk;
        walk.walk();
    }
}
class T1 implements Runnable{
    @Override
    public void run() {
//        Walk walk = new Walk();
        Walk walk = Walk.walk;
        // 这里我依然用的new
        walk.run();
    }
}
class _23leisuo_duixiangsuo {
    public static void main(String[] args) {
        Thread t1 = new  Thread(new T1());
        Thread t3 = new  Thread(new T3());
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(t1);
        es.execute(t3);
        es.shutdown();
    }
}