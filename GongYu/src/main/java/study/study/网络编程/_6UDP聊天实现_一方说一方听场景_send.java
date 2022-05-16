package study.study.网络编程;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class _6UDP聊天实现_一方说一方听场景_send {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(1002);
        String tempMsg = "";
        while (true) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            tempMsg = bufferedReader.readLine();
            byte[] msg = tempMsg.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(msg, 0, msg.length, InetAddress.getByName("127.0.0.1"), 1003);
            datagramSocket.send(datagramPacket);
            if ("bye".equals(tempMsg)) {
                break;
            }
            datagramSocket.close();
        }
    }
}
