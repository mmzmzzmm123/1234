package work.cib.xml_socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Test_server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(999);
        Socket accept = serverSocket.accept();
        // InputStream-----------------------------------------
        InputStream inputStream = accept.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] bytes = new byte[1024];
        int len = 0;
        //inputStream才是client发送的数据，fileOutputStream是为了保存文件
        while ((len = inputStream.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes, 0, len);
        }
        // OutputStream------------------------------------------
        OutputStream outputStream = accept.getOutputStream();
        outputStream.write("服务端收到！此消息由server传来".getBytes());
        outputStream.close();
        //-----------------------------------------
        byteArrayOutputStream.close();
        inputStream.close();
        accept.close();
        serverSocket.close();
    }

}
