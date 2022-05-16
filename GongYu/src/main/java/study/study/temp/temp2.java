package study.study.temp;

public class temp2 {

    {
        S=3;
    }
    private static int M=1;
    private int S=M+1;
    public temp2(int var){
        System.out.println(M);
        System.out.println(S);
        this.M=var;
        System.out.println(M++);
        System.out.println(S);
    }

    public static void main(String[] args) {
        new temp2(6);
    }
}
