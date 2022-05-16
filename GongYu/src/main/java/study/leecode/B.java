package study.leecode;

public class B {
    class Cat extends A{

    }
    class Rat extends A{
        public void crowl(){
            crowl("zhi zhi"); // 没有问题，继承了Animal中的protected方法——crowl(String)
            Cat cat=new Cat();
            cat.crowl("miao miao"); // wrong, The method crowl(String) from the type Animal is not visible
        }
    }

    public static void main(String[] args) {

    }
}
