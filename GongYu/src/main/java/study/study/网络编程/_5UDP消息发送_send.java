package study.study.网络编程;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class _5UDP消息发送_send {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(1212);
        String msg = "大笨狗最爱他的小猫咪！";
        //建立一个包
        DatagramPacket datagramPacket = new DatagramPacket(
                msg.getBytes(),
                0,
                msg.getBytes().length,
                InetAddress.getByName("127.0.0.1"),
                1213);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }
}
