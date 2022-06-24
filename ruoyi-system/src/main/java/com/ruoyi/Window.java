package com.ruoyi;

import java.util.LinkedList;

public class Window<T> extends LinkedList<T> implements FIFO<T> {

    private int maxSize = Integer.MAX_VALUE;
    private final Object synObj = new Object();

    public Window() {
        super();
    }

    public Window(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    @Override
    public T sPush(T addLast) {
        synchronized (synObj) {
            T head = null;
            while (size() >= maxSize) {
                head = poll();
            }
            addLast(addLast);
            return head;
        }
    }

    @Override
    public T sPoll() {
        synchronized (synObj) {
            return poll();
        }
    }

    @Override
    public int getMaxSize() {
        return this.maxSize;
    }

    @Override
    public int size() {
        return super.size();
    }


    public static void main(String[] args) {
        Window<Integer> t = new Window<>(10);
        t.sPush(1);
        t.clear();
        System.out.println(t.size());
    }
}