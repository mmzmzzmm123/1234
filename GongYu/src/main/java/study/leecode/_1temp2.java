package study.leecode;

import java.util.*;

class Solution2 {
    public static void main(String[]args){
        Scanner sc=new Scanner(System.in);
        int len=sc.nextInt();
        int [][] shuxing = new int[len][2];

        for(int i=0;i<len;i++){
            for(int j=0;j<2;j++){
                shuxing[i][j]=sc.nextInt();
            }
        }

        Arrays.sort(shuxing,new Comparator<int[]>(){
           public int compare(int []a,int []b){
               return a[0]-b[0];
           }
        });

//        for(int []i:shuxing){
//            for(int j:i){
//                System.out.print(j+" ");
//            }
//            System.out.println();
//        }

        int ans=shuxing[0][0];
        int temp=shuxing[0][0]+shuxing[0][1];
        for(int i=1;i<len;i++){
            if(temp<shuxing[i][0]){
                ans+=(shuxing[i][0]-temp);
                temp=shuxing[i][0];
            }
            temp+=shuxing[i][1];
        }

        System.out.println(ans);
//        3
//        100 100
//        200 700
//        1000 100

//        3
//        100 100
//        300
//        100
//        500
//        100
    }
}