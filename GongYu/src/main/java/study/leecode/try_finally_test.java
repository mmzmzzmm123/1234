package study.leecode;


import java.util.*;


class try_finally_test {

    public static void main(String[] args) {
        try {
//正常业务逻辑
            System.out.println("I am try");
            throw new RuntimeException("I am RuntimeExecption");
        } catch (Exception e) {
//异常处理
            System.out.println("I am Exception -> " + e.getMessage());
        } finally {
//释放资源等
            System.out.println("I am Finally");
        }
    }
}
