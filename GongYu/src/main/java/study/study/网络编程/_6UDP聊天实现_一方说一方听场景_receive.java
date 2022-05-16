package study.study.网络编程;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class _6UDP聊天实现_一方说一方听场景_receive {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(1003);
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length);
        while (true) {
            datagramSocket.receive(datagramPacket);
            String msg = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            System.out.println(msg);
            if ("bye".equals(msg)) {
                break;
            }
        }
    }
}
