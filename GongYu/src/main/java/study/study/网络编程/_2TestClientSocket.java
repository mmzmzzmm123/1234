package study.study.网络编程;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

//客户
public class _2TestClientSocket {
    public static void main(String[] args) throws IOException {
        //要知道服务器地址
        InetAddress sAddress = InetAddress.getByName("127.0.0.1");
        int port = 9999;
        Socket socket = new Socket(sAddress, port);
        //发送消息，IO流
        OutputStream stream = socket.getOutputStream();
        stream.write("hello".getBytes());
        stream.write("asdf".getBytes());
        //关闭资源
        stream.close();
        socket.close();
    }
}
