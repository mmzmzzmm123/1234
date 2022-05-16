package study.Demo.javaBASE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class _1CollectionsSort {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(-1);
        arrayList.add(3);
        arrayList.add(3);
        arrayList.add(-5);
        arrayList.add(7);
        arrayList.add(4);
        arrayList.add(-9);
        arrayList.add(-7);
        System.out.println("原始数组:" + arrayList);

        Collections.reverse(arrayList);
        System.out.println("Collections.reverse(arrayList):" + arrayList);

        Collections.sort(arrayList);
        System.out.println("按自然排序的升序排序Collections.sort(arrayList):" + arrayList);

        Collections.shuffle(arrayList);
        System.out.println("随机排序Collections.shuffle(arrayList):" + arrayList);
        Collections.sort(arrayList,(o1,o2) ->(o2-o1));
        // 定制排序的用法
        Collections.sort(arrayList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("定制排序后：" + arrayList);
    }

}
