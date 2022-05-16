package work.cib.xml_socket;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class simple_test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String a="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        System.out.println(a.getBytes(StandardCharsets.UTF_8).length);
    }
}
