package study.Demo.javaBASE;

import java.io.*;

public class _23SerializableTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 对象赋值
        User user = new User();
        user.setName("大冰");
        user.setAge(20);
        System.out.println(user);

        // 创建输出流（序列化内容到磁盘）
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lixinger.mybatis.test.out"));
        // 序列化对象
        oos.writeObject(user);
        oos.flush();
        oos.close();

        // 创建输入流（从磁盘反序列化）
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("lixinger.mybatis.test.out"));
        // 反序列化
        User user2 = (User) ois.readObject();
        ois.close();
        System.out.println(user2);
    }
}
class User implements Serializable {
    private static final long serialVersionUID = 3831264392873197003L;
    private String name;
    private int age;
    @Override
    public String toString() {
        return "{name:" + name + ",age:" + age + "}";
    }
    // setter/getter...
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
}
