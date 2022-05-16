package study.study.kuangshen_thread;

import java.util.concurrent.locks.ReentrantLock;

public class _18TestLock implements Runnable {
    int ticketNums = 20;
    //定义lock锁
    private final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        _18TestLock testLock = new _18TestLock();
        // new Thread(testLock,"1").start();
        new Thread(testLock, "2").start();
        new Thread(testLock, "3").start();
        new Thread(testLock, "4").start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                if (ticketNums > 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "获得了第" + ticketNums-- + "票");
                } else {
                    break;
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
