package study.study.kuangshen_thread;

public class _2TestThread3 implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("子线程"+i);
        }
    }

    public static void main(String[] args) {
        //创建Runnable接口实现类的对象
        _2TestThread3 a2TestThread3 = new _2TestThread3();
//创建线程类对象,通过线程对象开启我们的线程（代理）
//        Thread thread=new Thread(testThread3);
//        thread.start();
        new Thread(a2TestThread3).start();
        for (int i = 0; i < 20; i++) {
            System.out.println("主线程"+i);
        }
    }
}
