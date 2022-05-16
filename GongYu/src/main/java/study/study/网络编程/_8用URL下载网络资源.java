package study.study.网络编程;

import javax.net.ssl.HttpsURLConnection;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class _8用URL下载网络资源 {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:8080/helloworld/index.jsp?username=gy&password=123");
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\Codes\\kuang.study\\JUC\\src\\main\\java\\com\\kuang\\net\\pic\\aa.mp3");
        byte[] bytes = new byte[1024];
        int temp = 0;
        while ((temp = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, temp);
        }

        //这里关闭流简写，正常需要try-catch-finally
        fileOutputStream.close();
        inputStream.close();
        urlConnection.disconnect();
    }
}
