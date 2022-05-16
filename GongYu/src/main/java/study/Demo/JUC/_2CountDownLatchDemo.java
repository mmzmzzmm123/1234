package study.Demo.JUC;

import java.util.concurrent.CountDownLatch;
//https://blog.csdn.net/weixin_43314519/article/details/110280596
/**
 *
 */
public class _2CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // ������
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t ������ϰ���뿪����");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        //ǰ���̵߳�ִ����ɺ󣬲�ִ������������Ĵ���
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t �೤������");
    }
}
