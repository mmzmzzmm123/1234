package study.study.网络编程;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//服务
public class _3TestServerSocket2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket accept = serverSocket.accept();
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            System.out.println("before:" + byteArrayOutputStream);
            inputStream = accept.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = (inputStream.read(bytes))) != -1) {
                byteArrayOutputStream.write(bytes, 0, len);
                System.out.println("len:" + len);
            }
            System.out.println("after:" + byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            byteArrayOutputStream.close();
            inputStream.close();
        }
    }
}
