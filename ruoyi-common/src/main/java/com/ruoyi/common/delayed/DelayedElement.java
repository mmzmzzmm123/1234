package com.ruoyi.common.delayed;


import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 一个简单的延迟队列实现
 * element 对应数据
 * extensionCount:履约次数 每次履约的时候自增
 * time:每次履约的时间
 * delayTime:到期时间
 * <p>
 * 目前的履约规则比较简单 只是延长 extensionCount*time
 *
 * @param <T>
 */

public class DelayedElement<T> implements Delayed {
    private T element;
    private int extensionCount = 1;
    private long time;
    private long delayTime;
    private boolean needExtension = false;

    private Action taskAction;//类似于回调函数 在队列到期的时候可以执行这个函数 取决于调用方

    public DelayedElement(T element, long time, Action taskAction, boolean needExtension) {
        this(element, time, taskAction);
        this.needExtension = needExtension;
    }

    public DelayedElement() {
    }

    public DelayedElement(T element, long time, Action taskAction) {
        this.element = element;
        this.time = time;
        this.delayTime = System.currentTimeMillis() + this.time * extensionCount;
        this.taskAction = taskAction;
    }

    public DelayedElement(T element, long time) {
        this.element = element;
        this.time = time;
        this.delayTime = System.currentTimeMillis() + this.time * extensionCount;
    }

    public void extension() {
        this.delayTime = System.currentTimeMillis() + this.time * extensionCount;
        ;
        this.extensionCount++;
    }


    @Override
    public long getDelay(TimeUnit unit) {
        long diff = delayTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.delayTime - ((DelayedElement<?>) o).getDelayTime());
    }


    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public int getExtensionCount() {
        return extensionCount;
    }

    public void setExtensionCount(int extensionCount) {
        this.extensionCount = extensionCount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    public boolean isNeedExtension() {
        return needExtension;
    }

    public void setNeedExtension(boolean needExtension) {
        this.needExtension = needExtension;
    }

    public Action getTaskAction() {
        return taskAction;
    }
}