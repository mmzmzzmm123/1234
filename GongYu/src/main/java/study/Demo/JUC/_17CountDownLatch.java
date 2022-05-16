package study.Demo.JUC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//https://huanglei.blog.csdn.net/article/details/101147669
public class _17CountDownLatch {
    public static void main(String[] Args) throws InterruptedException {
        // 医院闭锁
        CountDownLatch hospitalLatch = new CountDownLatch(1);
        // 患者闭锁
        CountDownLatch patientLatch = new CountDownLatch(5);
        System.out.println("患者排队");
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            executorService.execute(() -> {
                try {
//                    System.out.println("11111111111");
                    hospitalLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("体检：" + j);
                patientLatch.countDown();
            });
        }
        System.out.println("医生上班");
        hospitalLatch.countDown();
        patientLatch.await();
        System.out.println("医生下班");
        executorService.shutdown();
    }
}
