package work.cib.xml_socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Test_client {
    private static final Logger _log = LoggerFactory.getLogger(Test_client.class);

    /**
     * 将报文发送到指定地址
     */
    public static String sendMessToClinet(String sendMess) {
        Socket clinetSocket = null;
        OutputStream out = null;
        InputStream in = null;
        //应该知道：getOutputStream、getInputStream
        try {
            String ip = "127.0.0.1";
            String port = "999";
            String timeout = "6000";
            clinetSocket = getClinetSocket(ip, port, timeout);
            // OutputStream----------------------------------------------------------------------
            // getOutputStream()→对面的InputStream
            out = clinetSocket.getOutputStream();
            String xmlLength = String.format("%08d", sendMess.getBytes("GBK").length);
            String sendmsg = xmlLength + sendMess;
            System.out.println("发往社保卡开卡前置的报文是:" );
            System.out.println(sendmsg);
            out.write(sendmsg.getBytes("GBK"));
            out.flush();
            // InputStream--------------------------------------------------------------------------
            // getInputStream()←对面的OutputStream传送过来
            in = clinetSocket.getInputStream();
            byte[] buf = new byte[128];
            in.read(buf);
            String headS = new String(buf);
            System.out.println("headS====" + headS);
            // 龚宇将headS改为了headS.length()
            int resLength = Integer.valueOf(headS.length());
//            int resLength=152;
            System.out.println("resLength===" + resLength);
            if (resLength == 0) {
                _log.info("报文头长度【" + resLength + "】异常");
                throw new Exception("报文头长度异常!");
            }
            byte[] resBody = new byte[resLength];
            int nIdx = 0;
            int nReadLen;
            // 循环读取直到读到报文头长度的数据
            while (nIdx < resLength) {
                // 没有in会报错
                nReadLen = in.read(resBody, nIdx, resLength - nIdx);
//                System.out.println("nReadLen======"+nReadLen);
                if (nReadLen > 0) {
                    // 求出这个nIdx干什么？后面也没有使用
                    nIdx = nIdx + nReadLen;
                } else {
                    break;
                }
            }
            String resXml = new String(resBody, "GBK");
            return resXml;
        } catch (NumberFormatException e) {
            _log.error("数字转换操作失败", e);
        } catch (IOException e) {
            _log.error("IO操作失败", e);
        } catch (Exception e) {
            _log.error("Error", e);
        } finally {
            close(out, in, clinetSocket);
        }
        return null;

    }

    private static Socket getClinetSocket(String ip, String port, String timeout)
            throws Exception {
        Socket clientSocket = new Socket();
        InetSocketAddress bankAddress = new InetSocketAddress(ip, Integer.parseInt(port));
        clientSocket.connect(bankAddress, Integer.parseInt(timeout));
        _log.info("连接成功！");
        return clientSocket;
    }

    /**
     * 关闭资源
     */
    public static void close(OutputStream out, InputStream in,
                             Socket clinetSocket) {
        if (null != out) {
            try {
                out.close();
            } catch (IOException e) {
                _log.error("输出流关闭异常", e);
            }
        }
        if (null != in) {
            try {
                in.close();
            } catch (IOException e) {
                _log.error("输入流关闭异常", e);
            }
        }
        if (null != clinetSocket) {
            try {
                clinetSocket.close();
            } catch (IOException e) {
                _log.error("clinetSocket关闭异常", e);
            }
        }
    }

}
