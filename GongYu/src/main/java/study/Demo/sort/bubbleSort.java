package study.Demo.sort;
//https://www.cnblogs.com/onepixel/articles/7674659.html
public class bubbleSort {
    public static void main(String[]args){
        int [] arr = new int[10];
        int len = arr.length;
        for(int i = 0; i <len-1;i++) {
            for(int j = 0; j < len - 1 - i; j++) {
                if(arr[j] > arr[j+1]) {         // 相邻元素两两对比
                    int temp = arr[j+1];        // 元素交换
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
