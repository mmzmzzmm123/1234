package com.ruoyi.common.delayed;


import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 任务的简单实现
 * 内置延迟队列 跟线程池 包含回调函数
 *
 * @param <T>
 */
public abstract class Task<T>  {
    private final ExecutorService executor;
    private final DelayQueue<DelayedElement<T>> delayQueue;
    private boolean isRunning = false;

    public Task(Integer size, boolean runWhenNew) {
        this.executor = Executors.newFixedThreadPool(size);
        this.delayQueue = new DelayQueue<>();
        if (runWhenNew) {
            executor.submit(this::run);
            this.isRunning = true;
        }
    }

    public void start() {
        executor.submit(this::run);
        this.isRunning = true;
    }

    public abstract void run();

    public void stop() {
        ExecutorService executor = this.getExecutor();
        executor.shutdownNow();
    }

    public void add(DelayedElement<T> element) {
        if (!isRunning) {
            start();
        }
        this.delayQueue.add(element);
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public DelayQueue<DelayedElement<T>> getDelayQueue() {
        return delayQueue;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
