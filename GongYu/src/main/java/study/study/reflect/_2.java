package study.study.reflect;

import lombok.Data;

public class _2 {
    public static void main(String[] args) throws ClassNotFoundException {
        // 通过反射返回类的Class对象，这里的Class就是Object类中getClass()方法的返回值Class是一样的，获取的c1是Class类的对象
        Class c1 =  Class.forName("study.reflect.User1");
        System.out.println(c1);

        // 一个类在方法区中只有一个Class对象
        // 一个类被加载之后，类的整个结构(构造器、方法、属性等等)都会被封装在Class对象中
        Class c2 =  Class.forName("study.reflect.User1");
        System.out.println(c1.hashCode());
        System.out.println(c2.hashCode());
    }
}

//实体类：pojo entity
@Data
class User1{
    private String name;
    private int id;
    private int age;

}