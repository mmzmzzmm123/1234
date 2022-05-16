package study.Demo.javaBASE;


import java.util.Set;
import java.util.TreeMap;

public class _2ComparableTest {
    public static void main(String[] args) {
        TreeMap<Person9875, String> pdata = new TreeMap<Person9875, String>();
        pdata.put(new Person9875("张三", 30), "zhangsan");
        pdata.put(new Person9875("李四", 20), "lisi");
        pdata.put(new Person9875("王五", 10), "wangwu");
        pdata.put(new Person9875("小红", 5), "xiaohong");
        // 得到key的值的同时得到key所对应的值
        Set<Person9875> keys = pdata.keySet();
        for (Person9875 key : keys) {
            System.out.println(key.getAge() + "-" + key.getName());

        }
    }
}

// person对象没有实现Comparable接口，所以必须实现，这样才不会出错，才可以使treemap中的数据按顺序排列
// 前面一个例子的String类已经默认实现了Comparable接口，详细可以查看String类的API文档，另外其他
// 像Integer类等都已经实现了Comparable接口，所以不需要另外实现了
class Person9875 implements Comparable<Person9875> {
    private String name;
    private int age;
    public Person9875(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Person9875 o) {
        if (this.age > o.getAge()) {
            return 1;
        } else if (this.age < o.getAge()) {
            return -1;
        }
        return age;
    }
}