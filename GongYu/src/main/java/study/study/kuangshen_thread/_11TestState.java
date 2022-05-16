package study.study.kuangshen_thread;

public class _11TestState {
        public static void main(String[] args) throws InterruptedException {
            Thread thread=new Thread(()->{
                for (int i = 0; i <5 ; i++) {
                    try {
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("");
            });
            Thread.State state = thread.getState();
            System.out.println(state);//new
            thread.start();//启动线程
            state=thread.getState();//runnable
            System.out.println(state);
            while (state!= Thread.State.TERMINATED){//只要线程不终止就输出线程状态
                Thread.sleep(100);
                System.out.println(thread.getState());//TIME_WAITING

            }
        }
}
