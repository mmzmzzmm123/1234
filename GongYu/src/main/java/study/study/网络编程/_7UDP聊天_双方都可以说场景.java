package study.study.网络编程;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

//抽象出来的一个工具类
public class _7UDP聊天_双方都可以说场景 {
    public void say() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(sayPort);
        while (true) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String msg = bufferedReader.readLine();
            //System.out.println("您的好友"+Thread.currentThread().getName()+"说："+msg);
            byte[] msgs = msg.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(msgs, 0, msgs.length, ip, toPort);
            datagramSocket.send(datagramPacket);
            if ("bye".equals(msg)) {
                break;
            }
        }
        datagramSocket.close();
        System.out.println(Thread.currentThread().getName() + "end");

    }

    public void listen() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(listenPort);
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length);
        while (true) {
            datagramSocket.receive(datagramPacket);
            String receiveMsg = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            System.out.println("您的好友" + datagramPacket.getSocketAddress() + "说：" + receiveMsg);
            if ("bye".equals(receiveMsg)) {
                break;
            }
        }
        datagramSocket.close();
        System.out.println(Thread.currentThread().getName() + "end");
    }

    InetAddress ip;
    int sayPort;
    int listenPort;
    int toPort;
    //构造器
    public _7UDP聊天_双方都可以说场景(String ip, int sayPort, int listenPort, int toPort) throws UnknownHostException {
        this.ip = InetAddress.getByName(ip);
        this.sayPort = sayPort;
        this.listenPort = listenPort;
        this.toPort = toPort;
    }
}


