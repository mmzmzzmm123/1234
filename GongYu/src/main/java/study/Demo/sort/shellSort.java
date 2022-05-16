package study.Demo.sort;

import java.util.Arrays;
//https://www.cnblogs.com/luomeng/p/10592830.html
public class shellSort {
    private static void shellSort(int[] arr) {
        //step:步长
        for (int step = arr.length / 2; step > 0; step /= 2) {
            //对一个步长区间进行比较 [step,arr.length)
            for (int i = step; i < arr.length; i++) {
                int temp = arr[i];
                int j=i-step;
                //对步长区间中具体的元素进行比较
                while (j >= 0 && arr[j] > temp) {
                    //j为左区间的取值，j+step为右区间与左区间的对应值。
                    arr[j + step] = arr[j];
                    j -= step;
                }
                //此时step为一个负数，[j + step]为左区间上的初始交换值
                arr[j + step] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
