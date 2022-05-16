package study.Demo.JUC;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class LiuLiangKongZhi {
    public static void main(String[] args) {
        FlowControl2 web = new FlowControl2();
        for (int i = 0; i < 10; i++) {
            web.access(i);

        }

    }
    /**
     * 阻塞访问的线程，直到获取了访问令牌
     * @author xuexiaolei
     * @version 2017年11月15日
     */

    public static class FlowControl2 {
        private final static int MAX_COUNT = 10;
        private final Semaphore semaphore = new Semaphore(MAX_COUNT);
        private final ExecutorService exec = Executors.newCachedThreadPool();
        public void access(int i) {
            exec.submit(new Runnable() {
                @Override
                public void run() {
                    semaphore.acquireUninterruptibly();
                    doSomething(i);
                    semaphore.release();
                }
            });
        }

        public void doSomething(int i) {
            try {
                Thread.sleep(new Random().nextInt(100));
                System.out.println(String.format("%s 通过线程:%s 访问成功", i, Thread.currentThread().getName()));
            } catch (InterruptedException e) {

            }

        }


    }
}
