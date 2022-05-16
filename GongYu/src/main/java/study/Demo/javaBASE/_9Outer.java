package study.Demo.javaBASE;

public class _9Outer {
    public static void main(String[] Args){
        // ��̬�ڲ���-------------------------
        StaticInner staticinner = new StaticInner();
        staticinner.visit();
        // ��Ա�ڲ���----------------------------
        _9Outer outer = new _9Outer();
        Inner inner = outer.new Inner();
        inner.visit();
        // �ֲ��ڲ���--------------------
    }
    private static int radius = 1;
    private int count =2;
    // ��̬�ڲ���-------------------------
    static class StaticInner {
        public void visit() {
            System.out.println("visit outer static  variable:" + radius);
        }
    }
    // �ڲ���----------------------------
    class Inner {
        public void visit() {
            System.out.println("visit outer static  variable:" + radius);
            System.out.println("visit outer   variable:" + count);
        }
    }
    // �ֲ��ڲ���(�����ڷ����е��ڲ���)--------------------
    private final int out_a = 1;
    private static final int STATIC_b = 2;
    public void testFunctionClass(){
        int inner_c =3;
        class Inner {
            private void fun(){
                System.out.println(out_a);
                System.out.println(STATIC_b);
                System.out.println(inner_c);
            }
        }
        Inner  inner = new Inner();
        inner.fun();
    }
    // �����ڲ���-------------------------------------------
    private void test(final int i) {//�β���Ҫ�������ڲ���ʹ��ʱ����������Ϊ final��
        new Service() {
            public void method() {
                for (int j = 0; j < i; j++) {
                    System.out.println("�����ڲ���" );
                }
            }
        }.method();
    }
}
interface Service{
    void method();
}
