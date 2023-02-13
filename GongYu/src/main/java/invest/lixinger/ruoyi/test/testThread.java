package invest.lixinger.ruoyi.test;

import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;


// https://blog.csdn.net/trusause/article/details/125747447    很好的文章
// https://www.cnblogs.com/lwj0126/p/16505959.html
public class testThread {
    @Test
    public void requestTest() throws ExecutionException, InterruptedException {
        List<String> ids = Lists.newArrayList();
        ids.add("1");
        ids.add("2");
        ids.add("3");
        ids.add("4");
        System.out.println(Runtime.getRuntime().availableProcessors());
        //有返回值的情况，定义接收返回值
        ThreadPoolTaskExecutor thread = testThread.threadPoolTaskExecutorasd();
        List<hgda1YgjbxxVO> futureList2 = Lists.newArrayList();
        //分布式计数器，若业务不需要则可以不定义
        CountDownLatch countDownLatch = new CountDownLatch(ids.size());
        for (String id : ids) {
            //调用线程池的线程执行任务
            thread.submit(new Runnable() {
                @Override
                public void run() {
                    test(Lists.newArrayList(), futureList2);
                    //计数器-1
                    countDownLatch.countDown();
                }
            });
        }
        //await阻塞，直到计数器为0
        countDownLatch.await();
        System.out.println("主线程" + "====");
    }

    public List<hgda1YgjbxxVO> test(List<String> ids, List<hgda1YgjbxxVO> list2) {
        //随便写的业务逻辑代码，无实际意义，仅作演示
        System.out.println("线程体" + "====");
        List<hgda1YgjbxxVO> accountRecordVOS = Lists.newArrayList();
        int i = 0;
        hgda1YgjbxxVO accountRecordVO = new hgda1YgjbxxVO();
        for (String id : ids) {
            accountRecordVOS.add(accountRecordVO);
            list2.add(accountRecordVO);
        }
        try {
            Thread.sleep(Long.valueOf("1000"));
        } catch (Exception e) {
        }
        System.out.println("线程体结束" + "====");
        return accountRecordVOS;
    }

    public static ThreadPoolTaskExecutor threadPoolTaskExecutorasd() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int i = Runtime.getRuntime().availableProcessors();
        //核心线程数目
        executor.setCorePoolSize(i * 2);
        //指定最大线程数
        executor.setMaxPoolSize(i * 2);
        //队列中最大的数目
        executor.setQueueCapacity(i * 2 * 10);
        //线程名称前缀
        executor.setThreadNamePrefix("ThreadPoolTaskExecutor-");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //当调度器shutdown被调用时等待当前被调度的任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(60);
        //加载
        executor.initialize();
        return executor;
    }

}
