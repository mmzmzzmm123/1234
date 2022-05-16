package study.Demo.JUC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class _16interruptLock {
    public static void main(String[]Args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        //因为有两把锁，所以会出错
        lock.lock();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    lock.lock();
                    lock.lockInterruptibly();  // java.lang.InterruptedException
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Over");
        System.exit(0);

    }
}
