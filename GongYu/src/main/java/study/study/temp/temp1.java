package study.study.temp;

public class temp1 {
    String str=new String("good");
    char[]ch={'a','b','c'};

    public static void main(String[] args) {
        temp1 so=new temp1();
        so.change(so.str,so.ch);
        System.out.println(so.str+" and ");
        System.out.println(so.ch);
    }

    private void change(String str, char[] ch) {
        str="lixinger.mybatis.test of";
        ch[0]='g';
    }
}
