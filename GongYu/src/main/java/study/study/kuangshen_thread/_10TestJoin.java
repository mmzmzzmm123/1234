package study.study.kuangshen_thread;

public class _10TestJoin implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        _10TestJoin testJoin=new  _10TestJoin();
        Thread thread=  new Thread( testJoin);
        thread.start();

        for (int i = 0; i < 50; i++) {
            if(i==25)
            {
                thread.join();
            }
            System.out.println("主线程"+i);
        }
    }
    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            System.out.println("join线程"+i);
        }
    }
}

