package study.study.kuangshen_thread;

import java.util.concurrent.CopyOnWriteArrayList;

public class _16testJuuc implements Runnable {
    static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();

    public static void main(String[] args) throws InterruptedException {
        _16testJuuc thread = new _16testJuuc();
        for (int i = 0; i < 10; i++) {
            new Thread(thread).start();
        }
        Thread.sleep(3000);
        System.out.println(list.size());
        System.out.println(list.toString());
    }

    @Override
    public void run() {
        list.add(Thread.currentThread().getName());
    }
}
