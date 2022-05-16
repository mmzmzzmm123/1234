package study.Demo.DesignPatterns;

//https://dragon.blog.csdn.net/article/details/80746215
public class _1_1DoubleCheckedLocking {
    private volatile static Instance instance;
    public static Instance getInstance() {
        if (instance == null) {
            synchronized (_1_1DoubleCheckedLocking.class) {
                if (instance == null)
                    instance = new Instance();
            }
        }
        return instance;
    }
}

class Instance{

}