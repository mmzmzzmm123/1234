package study.Demo.javaBASE;

class Outer {
    public int num = 1;
    class Inner {
        public int num = 2;
        public void show() {
            int num = 3;
            System.out.println(num);
            System.out.println(this.num);
            System.out.println(Outer.this.num);
        }
    }
}
public class _27InnerTest {
    public static void main(String[] args) {
        new Outer().new Inner().show();
    }
}
