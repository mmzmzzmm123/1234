package study.Demo.javaBASE;

public class _4FinallyTest {
    public static void main(String[] args) {
        System.out.println(new _4FinallyTest().test());
    }
    static int test() {
        int x = 1;
        try {
            x++;
            return x;
        } finally {
            ++x;
        }
    }
}


