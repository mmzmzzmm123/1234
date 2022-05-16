package study.study.网络编程;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// 服务器端,等待客户端的数据包
public class _4TCP实现文件上传_server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1111);
        Socket accept = serverSocket.accept();
        //-----------------------------------------
        InputStream inputStream = accept.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\cib\\picture\\SpringOut.jpg");
        byte[] bytes = new byte[1024];
        int len = 0;
        //inputStream才是client发送的数据，fileOutputStream是为了保存文件
        while ((len = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, len);
        }
        //------------------------------------------
        OutputStream outputStream = accept.getOutputStream();
        outputStream.write("服务端收到图片啦！此消息由server传来".getBytes());
        outputStream.close();
        //-----------------------------------------
        fileOutputStream.close();
        inputStream.close();
        accept.close();
        serverSocket.close();
    }
}
