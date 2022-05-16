package study.Demo.JUC;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class _12AtomicIntegerArray {
    static int[] value = new int[] { 1,2 };
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);
    public static void main(String[] args) {
        ai.addAndGet(0,5);
        System.out.println(ai.get(0));
        System.out.println(value[0]);
    }
}
