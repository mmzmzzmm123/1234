package study.Demo.JUC;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class _14AtomicIntegerFieldUpdater {
    // 创建原子更新器，并设置需要更新的对象类和对象的属性
    private static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class,"age");
    public static void main(String[] args) {
        // 设置柯南的年龄是10岁
        User conan = new User("conan",10);
        // 柯南长了一岁，但是仍然会输出旧的年龄
        System.out.println(a.getAndIncrement(conan));
        // 输出柯南现在的年龄
        System.out.println(a.get(conan));
    }

    public static class User {
        private String name;
        public volatile int age;
        public User(String name,int age) {
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }
    }
}
