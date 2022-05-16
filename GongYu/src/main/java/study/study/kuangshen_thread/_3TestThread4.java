package study.study.kuangshen_thread;

//多个线程操作同一个对象
//买火车票
//多个线程操作同一个资源的情况下出现不同的线程抢到同一张票，线程不安全，数据紊乱
public class _3TestThread4 implements Runnable {

    int ticketNums = 100;//票数

    public static void main(String[] args) {
        _3TestThread4 a3TestThread4 = new _3TestThread4();
        new Thread(a3TestThread4, "1").start();//1,2,3是线程名字
        new Thread(a3TestThread4, "2").start();
        new Thread(a3TestThread4, "3").start();
    }

    @Override
    public void run() {
        while (true) {
            if (ticketNums <= 0) {
                break;
            }
            try {
                Thread.sleep(200);//模拟延时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-->拿到了第" + ticketNums-- + "张票");
        }
    }
}

