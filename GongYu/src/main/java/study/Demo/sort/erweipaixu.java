package study.Demo.sort;


import java.util.Arrays;
import java.util.Comparator;

public class erweipaixu {
    public static void main(String[] args) {
        erweipaixu so = new erweipaixu();
        int[][] heroes = new int[][]{{60, 35, 20}, {30, 40, 30}, {40, 50, 40}, {50, 60, 50}, {55, 45, 60}};
//        int[][]heroes=new int[][]{{4,5,1,1},{6,4,2,2},{7,3,3,3}};
        int row = heroes.length;
        int column = heroes[0].length;
        for (int i = 0; i < row; i++) {
            Arrays.sort(heroes[i]);
        }

        Arrays.sort(heroes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                // 按照column进行排序，如果column相等，就用0列进行排序
                if (o1[column - 1] == o2[column - 1]) return o1[0] - o2[0];
                return o1[column - 1] - o2[column - 1];
            }
        });

        for(int []i:heroes){
            for(int j:i){
                System.out.print(j+" ");
            }
            System.out.println();
        }
    }
}
