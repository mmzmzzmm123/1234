package study.study.kuangshen_JUC._10pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// Executors 工具类、3大方法

/**
 * new ThreadPoolExecutor.AbortPolicy() // 银行满了，还有人进来，不处理这个人的，抛出异常
 * new ThreadPoolExecutor.CallerRunsPolicy() // 哪来的去哪里！
 * new ThreadPoolExecutor.DiscardPolicy() //队列满了，丢掉任务，不会抛出异常！
 * new ThreadPoolExecutor.DiscardOldestPolicy() //队列满了，尝试去和最早的竞争，也不会抛出异常！
 */
//pool-1-thread-4 ok中的thread-4就是最大有4个线程在执行，
//人数没有超过corePoolSize+deque只会用corePoolSize
//人数    超过corePoolSize+deque才会用最大核心线程池大小
public class Demo01 {
    public static void main(String[] args) {
        // 自定义线程池！工作 ThreadPoolExecutor

        // 最大线程到底该如何定义
        // 1、CPU 密集型：几核，就是几，可以保持CPu的效率最高！
        // 2、IO密集型：判断你程序中十分耗IO的线程，
        // 程序   15个大型任务  io十分占用资源！

        // 获取CPU的核数
//        System.out.println(Runtime.getRuntime().availableProcessors());
        List list = new ArrayList();

        ExecutorService threadPool = new ThreadPoolExecutor(
                2,//核心线程池大小
                Runtime.getRuntime().availableProcessors(),  //最大核心线程池大小
                3,  //超时了没有人调用就会释放
                TimeUnit.SECONDS,  //超时单位
                new LinkedBlockingDeque<>(3),  //阻塞队列
                Executors.defaultThreadFactory(),  //线程工厂，创建线程的，一般不用动
                new ThreadPoolExecutor.DiscardOldestPolicy());  //拒绝策略。队列满了，尝试去和最早的竞争，也不会抛出异常！
//-------------------------------------------------------------------------------
        try {
            // 最大承载：Deque + max
            // 超过 RejectedExecutionException
            for (int i = 1; i <= 8; i++) {
                // 使用了线程池之后，使用线程池来创建线程
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " ok");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 线程池用完，程序结束，关闭线程池
            threadPool.shutdown();
        }

    }
}
