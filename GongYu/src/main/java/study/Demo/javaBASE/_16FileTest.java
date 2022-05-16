package study.Demo.javaBASE;

import java.io.*;

public class _16FileTest {
    public static void main(String[] Args) throws IOException {
        File file = new File(".\\lixinger.mybatis.test.txt");
        InputStream is = new FileInputStream(file);
        byte[] b = new byte[1024];
        int a = is.read(b);
        String[] str = new String(b, 0, a).split("");
        int count = 0;
        for (int i = 0; i < str.length; i++) {
            if ("a".equals(str[i])) count++;

        }
        System.out.println(count);
    }
}
