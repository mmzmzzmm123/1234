package study.study.kuangshen_thread;

public class _13TestDaemon {
    public static void main(String[] args) {
        God god = new God();
        You you = new You();
        Thread thread = new Thread(god);
        thread.setDaemon(true);//默认为flase 为用户线程，  true为守护线程
        thread.start();
        new Thread(you).start();
    }
}

class God implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("上帝守护着你-------");
        }
    }
}

class You implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("开心着活着每一天------");
        }
        System.out.println("----goodbye!Beautiful World!!!------");
    }
}
