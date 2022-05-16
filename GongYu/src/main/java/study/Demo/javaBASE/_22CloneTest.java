package study.Demo.javaBASE;

public class _22CloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        DogChild dogChild = new DogChild();
        dogChild.name = "小A";

        Dog dog4 = new Dog();
        dog4.name = "A";
        dog4.dogChild = dogChild;

        // 注意下面这段代码
        Dog dog5 = (Dog) dog4.clone();
        dog5.name = "B";
        dog5.dogChild.name = "小B";

        System.out.println("dog name 4："+dog4.name);
        System.out.println("dog name 5："+dog5.name);
        System.out.println("dog child name 4："+dog4.dogChild.name);
        System.out.println("dog child name 5："+dog5.dogChild.name);
    }
}
class Dog implements Cloneable {
    public String name;
    // 因为dogChild是另外一个对象
    public DogChild dogChild;
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
class DogChild {
    public String name;
}
