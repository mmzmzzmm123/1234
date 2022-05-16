package study.study.kuangshen_thread;

public class _14UnsafeBuyTicket {
    //不安全的买票
    public static void main(String[] args) {
        BuyTicket buyTicket = new BuyTicket();
        new Thread(buyTicket, "1").start();
        new Thread(buyTicket, "2").start();
        new Thread(buyTicket, "3").start();
    }
}

class BuyTicket implements Runnable {

    private int tickNums = 10;
    boolean flag = true;  //标志位，用于线程的外部停止方式

    @Override
    public void run() {
        //买票
        while (flag) {
            buy();
        }
    }

    private synchronized void buy() {
        //判断是否有票
        if (tickNums <= 0) {
            flag = false;
            return;
        }
        //模拟延时
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //买票
        System.out.println(Thread.currentThread().getName() + "拿到" + tickNums--);
    }

}
