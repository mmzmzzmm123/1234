package study.Demo.JUC;
//https://blog.csdn.net/weixin_43314519/article/details/110282737
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource {
    // A 1   B 2   c 3
    private int number = 1;
    // ?????????????
    private final Lock lock = new ReentrantLock();

    // ????????????????
    private final Condition condition1 = lock.newCondition();
    private final Condition condition2 = lock.newCondition();
    private final Condition condition3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            // ?????1??????????
            while (number != 1) {
                // ??????1????????
                condition1.await();
            }
            // ???
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t " + number + "\t" + i);
            }
            // ???? ?????????????B?????У?
            number = 2;
            // ??2????????
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (number != 2) {
                // ??????2????????
                condition2.await();
            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t " + number + "\t" + i);
            }
            // ???? ?????????????C?????У?
            number = 3;
            // ??2????????
            condition3.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (number != 3) {
                // ??????3????????
                condition3.await();
            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t " + number + "\t" + i);
            }

            // ???? ?????????????C?????У?
            number = 1;
            // ??1????????
            condition1.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class _10SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        int repeat=2;
        new Thread(() -> {
            for (int i = 0; i < repeat; i++) {
                shareResource.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < repeat; i++) {
                shareResource.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < repeat; i++) {
                shareResource.print15();
            }
        }, "C").start();
    }
}
