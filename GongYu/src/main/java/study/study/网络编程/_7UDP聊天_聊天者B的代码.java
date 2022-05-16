package study.study.网络编程;

import java.io.IOException;
import java.net.UnknownHostException;

public class _7UDP聊天_聊天者B的代码 {
    public static void main(String[] args) throws UnknownHostException {
        /**
         *           A          B
         * say      1500      1600
         * listen   1501      1601
         */
        _7UDP聊天_双方都可以说场景 chatter = new _7UDP聊天_双方都可以说场景("127.0.0.1", 1600, 1601, 1501);
        new Thread(() -> {
            try {
                chatter.say();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "聊天者B说话线程").start();

        new Thread(() -> {
            try {
                chatter.listen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "聊天者B倾听线程").start();
    }
}
