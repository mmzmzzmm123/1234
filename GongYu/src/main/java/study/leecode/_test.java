package study.leecode;

public class _test {
    public static void main(String[]args){
        int n=6;
        int a=dfs(6);
        System.out.println(a);
    }
    static int dfs(int n){
        if(n==1||n==0)return 1;
        return dfs(n-1)+dfs(n-2);
    }
}
