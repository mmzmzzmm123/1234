package study.Demo.sort;

//https://blog.csdn.net/xdzhouxin/article/details/80070839
public class mergeSort {
    public static void merSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            merSort(arr, left, mid);  // 左边归并排序，使得左子序列有序
            merSort(arr, mid + 1, right);  // 右边归并排序，使得右子序列有序
            merge(arr, left, mid, right);  // 合并两个子序列
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        //也可以从开始就申请一个与原数组大小相同的数组，因为重复new数组会频繁申请内存
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        // 把上面操作剩下的元素放到temp中

        // 将左边剩余元素填充进temp中
        while (i <= mid) {temp[k++] = arr[i++];}
        // 将右序列剩余元素填充进temp中
        while (j <= right) {temp[k++] = arr[j++];}

        // 将temp中的元素全部拷贝到原数组中
        for (int z = 0; z < temp.length; z++) {
            arr[z + left] = temp[z];
        }

    }

    public static void main(String args[]) {
        int[] test = {9, 2, 6, 3, 5, 7, 10, 11, 12};
        merSort(test, 0, test.length - 1);
        for (int i = 0; i < test.length; i++) {
            System.out.print(test[i] + " ");
        }
    }

}

