package study.study.kuangshen_thread;

public class _15UnsafeBank {
    //不安全取钱
    public static void main(String[] args) {
        //取钱首先得有账户
        Account account = new Account(100, "结婚基金");
        Drawing you = new Drawing(account, 50, "你");
        Drawing girl = new Drawing(account, 100, "girlFriend");
        you.start();
        girl.start();
    }
}

//银行：模拟取款
class Drawing extends Thread {
    Account account;//账户
    int drawingMoney;//取多少钱
    int nMoney;//现在有多少钱

    public Drawing(Account account, int drawingMoney, String name) {
        super(name);//调用父类的方法
        this.account = account;
        this.drawingMoney = drawingMoney;
        // this.nMoney = nMoney;
    }

    @Override
    public synchronized void run() {
        //super.run();
        //判断有没有钱
        if (account.money - drawingMoney < 0) {
            System.out.println(Thread.currentThread().getName() + "钱不够，取不了");
            return;
        }
        //模拟延时
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //卡内余额
        account.money = account.money - drawingMoney;
        //现在手里的钱
        nMoney = nMoney + drawingMoney;
        System.out.println(account.name + "余额为：" + account.money);
        //this就是调用当前方法的对象，Drawing继承了Thread,所以this也是一个线程对象，可以调用Thread的getName方法来获取线程的名字
        //Thread.currentThread().getName()=this.getName()
        System.out.println(this.getName() + "手里的钱" + nMoney);
    }
}

class Account {//账户
    int money;//余额
    String name;//卡名

    public Account(int money, String name) {
        this.money = money;
        this.name = name;
    }
}
