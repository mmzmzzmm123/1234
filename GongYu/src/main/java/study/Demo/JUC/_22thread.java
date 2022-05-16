package study.Demo.JUC;

public class _22thread {
    private int i=1;
    private Object object =new Object();
    public static void main(String[]args){
        _22thread test=new _22thread();
        MyThread t1=test.new MyThread();
        MyThread t2=test.new MyThread();
        t1.start();
        t2.start();
    }
    class MyThread extends Thread{
        public void run(){
            synchronized (object){
                i++;
                System.out.print(i);
                try{
                    sleep(10);
                }catch(Exception e){
                }
                i+=2;
                System.out.print(i);
            }
        }
    }
}
