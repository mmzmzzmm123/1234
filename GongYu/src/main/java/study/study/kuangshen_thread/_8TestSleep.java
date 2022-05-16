package study.study.kuangshen_thread;

import java.text.SimpleDateFormat;
import java.util.Date;

//模拟倒计时
public class _8TestSleep implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        tenDown();
        //打印当前系统时间
        Date startTime=new Date(System.currentTimeMillis());
        while(true)
        {
            Thread.sleep(1000);
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(startTime));
            startTime=new Date(System.currentTimeMillis());

        }
    }
    public static void tenDown() throws InterruptedException {//模拟倒计时
        int num=10;
        while(true)
        {
            Thread.sleep(1000);
            System.out.println(num--);
            if(num<=0)
            {
                break;
            }
        }
    }

    @Override
    public void run() {
    }
}
