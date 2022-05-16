package study.Demo.javaBASE;

public class _31TryCatchTest {
    public static void main(String []Args){
        try {
            int i = 10 / 0;
            System.out.println("try");
        } catch (Exception e) {
            int j = 2 / 0;
            System.out.println("catch");
        } finally {
            System.out.println("finally");
        }
        System.out.println("main");

    }
}
