package study.Demo.JUC;

public class _15JoinTest {
    public static void main(String []Args) throws InterruptedException {
        Thread joinThread = new Thread(() -> {
            try {
                System.out.println("执行前");
                Thread.sleep(1000);
                System.out.println("执行后");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        joinThread.start();
        joinThread.join();
        System.out.println("主程序");

    }
}
