package study.Demo.javaBASE;
//https://blog.csdn.net/QAQ123666/article/details/104863941
public class _7InitializationDemo2 {
    public static void main(String[] args) {
        staticFunction();
    }

    static _7InitializationDemo2 book = new _7InitializationDemo2();

    static {
        System.out.println("书的静态代码块");
    }

    {
        System.out.println("书的普通代码块");
    }

    _7InitializationDemo2() {
        System.out.println("书的构造方法");
        System.out.println("price=" + price + ",amount=" + amount);
    }

    public static void staticFunction() {
        System.out.println("书的静态方法");
    }

    int price = 110;
    static int amount = 112;
}

