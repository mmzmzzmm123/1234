package study.Demo.sort;

//https://www.cnblogs.com/onepixel/articles/7674659.html
public class selectionSort {
    public static void main(String[] args) {
        int[] arr = new int[10];
        int len = arr.length;
        int minIndex, temp;
        for (int i = 0; i < len - 1; i++) {
            minIndex = i;
            // 每次找到一个最大值排在前面
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;                 // 将最小数的索引保存
                }
            }
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
}
