package study.Demo.javaBASE;
//抽象方法
//https://blog.csdn.net/QAQ123666/article/details/104684170
interface Dance {
    void dance();
}

abstract class person {
    String eye = "黑色大眼睛";//随便定义为String类型
    String mouth = "血盆大口";
    abstract void eat();//抽象方法
    void breath()//普通方法
    {
        System.out.println("喘气，呼哧~");
    }
}
public class _8man extends person implements Dance{
    @Override
    void eat() {
        System.out.println("我用筷子吃饭");
    }

    @Override
    public void dance() {
        System.out.println("Let's dance!!!");
    }

    public static void main(String[] args) {
        _8man man = new _8man();
        man.eat();
        man.breath();
        System.out.println(man.eye);
        man.eye = "蓝色大眼睛";
        System.out.println(man.eye);
        man.dance();
    }
}
