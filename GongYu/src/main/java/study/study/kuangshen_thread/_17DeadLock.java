package study.study.kuangshen_thread;

public class _17DeadLock {
    public static void main(String[] args) {
        Makeup girl1 = new Makeup(0, "灰姑凉");
        Makeup girl2 = new Makeup(1, "白雪公主");
        girl1.start();
        girl2.start();
    }
}


class Makeup extends Thread {
    //需要的资源只有一份，用static修饰来保证只有一份
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();
    int choice;
    String girlName;//使用化妆品的人

    public Makeup(int choice, String girlName) {
        this.choice = choice;
        this.girlName = girlName;
    }

    @Override
    public void run() {
        try {
            makeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //-----------------------平行的两把锁就不会产生死锁
    private void makeup() throws InterruptedException {
        if (choice == 0) {
            synchronized (lipstick) {//获得口红的锁
                System.out.println(this.girlName + "获得口红的锁");
                Thread.sleep(1000);
            }
            synchronized (mirror) {//一秒中后获得镜子的锁
                System.out.println(this.girlName + "获得镜子的锁");
            }
        } else {
            synchronized (mirror) {//获得镜子的锁
                System.out.println(this.girlName + "获得镜子的锁");
                Thread.sleep(2000);
            }
            synchronized (lipstick) {//一秒中后获得口红的锁
                System.out.println(this.girlName + "获得口红的锁");
            }
        }
    }
//-------------------产生死锁，一个锁里面套另外一个锁
//    private void makeup() throws InterruptedException {
//        if (choice == 0) {
//            synchronized (lipstick) {//获得口红的锁
//                System.out.println(this.girlName + "获得口红的锁");
//                Thread.sleep(1000);
//                synchronized (mirror) {//一秒中后获得镜子的锁
//                    System.out.println(this.girlName + "获得镜子的锁");
//                }
//            }
//        } else {
//            synchronized (mirror) {//获得镜子的锁
//                System.out.println(this.girlName + "获得镜子的锁");
//                Thread.sleep(1000);
//                synchronized (lipstick) {//一秒中后获得口红的锁
//                    System.out.println(this.girlName + "获得口红的锁");
//                }
//            }
//        }
//    }



}

//口红
class Lipstick {
}

//镜子
class Mirror {
}
