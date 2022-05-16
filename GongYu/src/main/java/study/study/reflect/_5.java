package study.study.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class _5 {
    public static void main(String[] args) throws Exception {
        // 普通new创建对象
        User student = new Student("mingming");
        System.out.println("这个人是：" + student.name);

        // 方式1：通过类的class属性获取，该方法最为安全可靠，程序性能最高（获取Student的Class对象）
        Class c1 = Student.class;
        System.out.println(c1.hashCode());

        // 方式2：通过类的实例中的getClass()方法获取（获取Student的Class对象）
        Class c2 = student.getClass();
        System.out.println(c2.hashCode());

        // 方式3：通过类的全限定类名获取（获取Student的Class对象）
        Class c3 = Class.forName("反射.Student");
        System.out.println(c3.hashCode());

        // 方式4：通过基本内置类型的包装类的Type属性（了解）
        Class c4 = Integer.TYPE;


        // 方法5：通过ClassLoader类加载器获取
        Constructor constructor = c1.getDeclaredConstructor(String.class);
        Class c5 = constructor.getDeclaringClass();
        System.out.println(c5.hashCode());

        // 获取父类类型
        Class c6 = c1.getSuperclass();
        System.out.println(c6);

        // 获取Student对象
        Student s1 = (Student) c1.newInstance(); // 本质调用了类的无参构造器
        System.out.println(s1);

        // 使用构造器创建Student对象
        Student s2 = (Student) constructor.newInstance("mingming");
        System.out.println(s2);

        // 通过反射调用普通方法
        // 其中hello是方法名称，String.class代表方法需要的参数的Class对象，c1是Student类对应的Class对象，s1代表Student类的实例对象，“tiantian”代表具体的参数值
        Method method = c1.getDeclaredMethod("hello", String.class);
        method.invoke(s1, "tiantian");

        // 通过反射给属性赋值
        Field age = c1.getDeclaredField("age");
        // 无论是调用私有方法还是给私有属性赋值，都需要关闭程序的安全监测，才能调用方法或者设置属性
        age.setAccessible(true);
        age.set(s1,12);
        System.out.println(s1.getAge());

    }
}

class User {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Student extends User {
    private Integer age;
    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public void hello(String str) {
        System.out.println("Hello!!! " + str);
    }

    public Integer getAge(){
        return this.age;
    }
}
