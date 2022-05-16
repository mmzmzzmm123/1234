package study.study.网络编程;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

// 文件上传
public class _4TCP实现文件上传_client {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("E:\\cib\\picture\\Spring.png"));
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 1111);
        // ----------------------------
        OutputStream outputStream = socket.getOutputStream();
        byte[] bytes = new byte[8];
        int len = 0;
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        System.out.println("outputStream=====" + outputStream);
        socket.shutdownOutput();
        // -------------------------------
        InputStream inputStream = socket.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((len = inputStream.read(bytes)) != -1) {
            System.out.println("new String(bytes)===========" + new String(bytes));
//            byteArrayOutputStream.write(bytes, 0, len);
            byteArrayOutputStream.write(bytes);
            System.out.println("byteArrayOutputStream=====" + byteArrayOutputStream);
        }
        System.out.println("last--------new String(bytes)===========" + new String(bytes));
        System.out.println("last--------byteArrayOutputStream=====" + byteArrayOutputStream);
        byteArrayOutputStream.close();
        // --------------------
        inputStream.close();
        outputStream.close();
        socket.close();
        fileInputStream.close();
    }
}
