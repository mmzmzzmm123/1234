package study.Demo.DesignPatterns;

public class _1MySingleton {
    volatile private static _1MySingleton instance = null;
    private _1MySingleton() {}
    public static _1MySingleton getInstance() {
        try {
            if (instance != null) {
            } else {
                Thread.sleep(300);
                synchronized (_1MySingleton.class) {
                    if (instance == null) {
                        _1MySingleton instance = new _1MySingleton();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static void main(String[] args) {
        _1MySingleton singleton1 = _1MySingleton.getInstance();
        _1MySingleton singleton2 = _1MySingleton.getInstance();
        System.out.println(singleton1 == singleton2);
    }
}
