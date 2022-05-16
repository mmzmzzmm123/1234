package study.study.kuangshen_thread;

public class _7TestStop implements Runnable {
    private boolean flag=true;
    @Override
    public void run() {
        int i=0;
        while (flag){
            System.out.println("run.....Thread"+(i++));
        }
    }
    public void stop(){
        this.flag=false;
    }

    public static void main(String[] args) {
        _7TestStop testStop = new _7TestStop();
        new Thread(testStop).start();
        for (int i = 0; i <1000 ; i++) {
            System.out.println("main"+i);
            if (i==900){
                testStop.stop();
                System.out.println("run线程停止了！");
            }
        }
    }
}
