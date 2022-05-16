package study.study.kuangshen_JUC._14tvolatile;

import java.util.concurrent.atomic.AtomicInteger;

// volatile 不保证原子性
public class VDemo02 {
    // volatile 不保证原子性
//    private volatile static int num=0;
        private  static AtomicInteger num = new AtomicInteger();    // 原子类的 Integer
    public static void add(){
//         num++; // 不是一个原子性操作
        num.getAndIncrement(); // AtomicInteger + 1 方法， CAS
    }

    public static void main(String[] args) {
        //理论上num结果应该为 2 万
        //有20个线程
        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000 ; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount()>2){ // main  gc
            Thread.yield(); //
        }

        System.out.println(Thread.currentThread().getName() + " " + num);


    }
}
