package study.study.网络编程;

import java.net.InetAddress;
import java.net.UnknownHostException;

//测试
public class _1Test1 {
    public static void main(String[] args) throws UnknownHostException {
        ///127.0.0.1
        InetAddress inetAddress1 = InetAddress.getByName("127.0.0.1");
        //localhost/127.0.0.1
        InetAddress inetAddress2 = InetAddress.getByName("localhost");
        //DESKTOP-R3NE1E5/192.168.117.3
        InetAddress inetAddress3 = InetAddress.getLocalHost();

        //这个需要连网，否则会报错：java.net.UnknownHostException
        //www.baidu.com/36.152.44.96
        InetAddress inetAddress4 = InetAddress.getByName("www.baidu.com");
        System.out.println(inetAddress1);
        System.out.println(inetAddress2);
        System.out.println(inetAddress3);
        System.out.println(inetAddress4);
        System.out.println("----------------------");

        //获取此IP地址的完全限定域名：36.152.44.96
        System.out.println(inetAddress4.getCanonicalHostName());
        //显示IP地址字符串：36.152.44.96
        System.out.println(inetAddress4.getHostAddress());
        //获取此IP地址的主机名:域名，或者自己电脑的名字:www.baidu.com
        System.out.println(inetAddress4.getHostName());
        //返回此 InetAddress对象的原始IP地址:[B@677327b6
        System.out.println(inetAddress4.getAddress());
    }
}

