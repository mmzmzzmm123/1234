package work.cib.sftp;

import com.jcraft.jsch.ChannelSftp;

import java.util.HashMap;
import java.util.Map;

public class GY_SFTPTest {
    public GY_SFTPChannel getSFTPChannel() {
        return new GY_SFTPChannel();
    }

    public static void main(String[] args) throws Exception {
        GY_SFTPTest test = new GY_SFTPTest();
        // 设置主机ip，端口，用户名，密码
        String filename="asdf.txt";
        String src = "E:\\cib\\"+filename; // 本地文件名
        String dst = "/"; // 目标文件名  /cib/cib_message/
        GY_SFTPChannel channel = test.getSFTPChannel();
        ChannelSftp chSftp = channel.getChannel( 6000);


//        //代码段1：采用向put方法返回的输出流中写入数据的方式来传输文件。 需要由程序来决定写入什么样的数据，这里是将本地文件的输入流写入输出流。
//        //采用这种方式的好处是，可以自行设定每次写入输出流的数据块大小
//        OutputStream out = chSftp.put(dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE);
//        byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
//        int read;
//        if (out != null) {
//            System.out.println("Start to read input stream");
//            InputStream is = new FileInputStream(src);
//            do {
//                read = is.read(buff, 0, buff.length);
//                if (read > 0) {
//                    out.write(buff, 0, read);
//                }
//                out.flush();
//            } while (read >= 0);
//            System.out.println("input stream read done.");
//        }

// 代码段2：直接将本地文件名为src的文件上传到目标服务器，目标文件名为dst。（注：使用这个方法时，dst可以是目录，当dst是目录时，上传后的目标文件名将与src文件名相同）
        chSftp.put(src, dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE); // 代码段2

// 代码段3：将本地文件名为src的文件输入流上传到目标服务器，目标z文件名为dst。
//        chSftp.put(new FileInputStream(src), dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE);

        chSftp.quit();
        channel.closeChannel();
    }
}
