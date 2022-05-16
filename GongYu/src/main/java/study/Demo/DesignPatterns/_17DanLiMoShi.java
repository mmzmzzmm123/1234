package study.Demo.DesignPatterns;

import java.util.Comparator;
import java.util.PriorityQueue;

// https://dragon.blog.csdn.net/article/details/81214227
public class _17DanLiMoShi {
    public static void main(String[] args) {
        DanLiMoShi1 danli1 = DanLiMoShi1.getInstance();
        DanLiMoShi1 danli2 = DanLiMoShi1.getInstance();
        if (danli1 == danli2)
            System.out.println("我们是一样的");
    }
}
// -----------------------------------------------
// 懒汉式，线程不安全
class DanLiMoShi1 {
    private static DanLiMoShi1 instance;
    private DanLiMoShi1() {}
    public static DanLiMoShi1 getInstance() {
        if (instance == null) {   // 如果有了实例了，就不需要要创建了
            instance = new DanLiMoShi1();
        }
        return instance;
    }
}

// 懒汉式，线程安全
class DanLiMoShi2 {
    private static DanLiMoShi2 instance;
    private DanLiMoShi2() {}
    public static synchronized DanLiMoShi2 getInstance() {
        if (instance == null) {   // 如果有了实例了，就不需要要创建了
            instance = new DanLiMoShi2();
        }
        return instance;
    }
}

// 饿汉式，线程安全：
// 优点：没有加锁，执行效率会提高。
// 缺点：类加载时就初始化，浪费内存。
class DanLiMoShi3 {
    private static final DanLiMoShi3 single = new DanLiMoShi3();
    private DanLiMoShi3() {}
    public static DanLiMoShi3 getInstance() {
        return single;
    }
}


// 双检锁/双重校验锁（DCL，即 double-checked locking）
// 这种方式采用双锁机制，安全且在多线程情况下能保持高性能。
class DanLiMoShi4 {
    private volatile static DanLiMoShi4 singleton;
    private DanLiMoShi4 (){}
    public static DanLiMoShi4 getInstance() {
        // 为了只创建一个单例
        if (singleton == null) {
            synchronized (DanLiMoShi4.class) {
                // 为了并发时也只创建一个单例
                if (singleton == null) {
                    singleton = new DanLiMoShi4();
                }
            }
        }
        return singleton;
    }
}

class DanLi{
    private volatile static DanLi instance;
    private DanLi(){}
    public static DanLi getInstance(){
        if(instance==null){
            synchronized(DanLi.class){
                if(instance==null){
                    instance=new DanLi();
                }
            }
        }
        return instance;
    }
}