package study.Demo.javaBASE;

class Grandpa {
    static {
        System.out.println("爷爷在静态代码块");
    }
}

class Father extends Grandpa {
    static {
        System.out.println("爸爸在静态代码块");
    }
    public static int factor = 25;
    public Father() {
        System.out.println("我是爸爸~");
    }
}

class Son extends Father {
    static {
        System.out.println("儿子在静态代码块");
    }
    //因为没有创建实例，所以不会用到这一段代码
    public Son() {
        System.out.println("我是儿子~");
    }
}

//没有输出「儿子在静态代码块」这个字符串？
//通过其子类来引用父类中定义的静态字段，只会触发父类的初始化而不会触发子类的初始化。
public class _5InitializationDemo {
    public static void main(String[] args) {
        System.out.println("爸爸的岁数:" + Son.factor);  //入口
    }
}
