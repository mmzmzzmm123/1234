package work.cib.sftp;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


/**
 * 短信平台工具类
 *
 * @author wzh
 */
public class SMSUtils {
    /**
     * 使用短信模板
     *
     * @return “短信模板”变为“将要发送”的字符串
     */
    public static String SMSTemplate(String messageTemplate, String telephone,
                                     String name) {
        String LoginUserNotesid = "190825";
        String ans = messageTemplate.replace("{name}", name)
                .replace("{telephone}", telephone)
                .replace("{LoginUserNotesid}", LoginUserNotesid);
        System.out.println(ans);
        return ans;
    }


    /**
     *
     * @param state 是否使用短信模板(0-不使用，1-使用)
     * @param url FTP服务器地址
     * @param username 用户名
     * @param password 密码
     * @param port 手机号
     * @param sms_sftp_rootpath sftp存储的本地地址
     * @param sms_sftp_timeout 超时时间
     * @param tel 手机号码
     * @param content 短信内容
     * @return 发送状态（1-成功，0-失败）
     */
    public static int sendByFTP(int state, String url, String username,
                                String password, String port, String sms_sftp_rootpath, String sms_sftp_timeout, String tel, String content) {
        int timeout = Integer.valueOf(sms_sftp_timeout);
        if (state == 1) {
            content = SMSUtils.SMSTemplate(content, tel, username);
        }
        SMSUtils.String2TxtUtil(content, sms_sftp_rootpath);
        System.out.println("将要发送的短信为" + content);
        System.out.println("------------开始使用sftp发送短信----------");

        return 1;
    }


    /**
     *
     * @param state 是否使用短信模板(0-不使用，1-使用)
     * @param url 请求路径
     * @param tel 机号
     * @param content 短信内容
     * @return 发送状态（0-成功，1-失败）
     */
    public static int sendByAPI(int state, String url, String tel, String content) {
        return 1;
    }

    /**
     * FTP方式时，将String存储在本地
     *
     * @param message 消息
     * @return
     */
    public static String String2TxtUtil(String message, String rootPath) {
        OutputStreamWriter fwriter = null;
        String authorname = "GongYu";
        File dir = new File(rootPath);
        SMSUtils.judgeDirExists(dir);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        // System.out.println(formatter.format(date));
        String filePath = rootPath + authorname + "_" + formatter.format(date)
                + "_" + uuid + ".txt";
//        System.out.println("文件路径+文件名为：" + filePath);
        try {
            fwriter=new OutputStreamWriter(new FileOutputStream(filePath),"GBK");
//            System.out.println(fwriter.getEncoding());
            fwriter.write(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return filePath;
    }


    /**
     * 判断文件夹是否存在
     * @param file 文件夹路径
     */
    public static void judgeDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out
                        .println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it.....");
            file.mkdirs();
        }
    }

    public static void main(String args[]) {
        SMSUtils.sendByFTP(1, "http://127.0.0.1:3234", "wzh", "wzh", "22",
                "E:\\gy\\cib_message\\", "600000", "13983997173", "测试短信");
        // TODO 自动生成的方法存根

        String telephone = "15528606339";
        String name = "龚宇";
        String Notesid = "190825";
        String messageTemplate = "{telephone},尊敬的{name}(公文号：{Notesid})，您好，您的密码已经被公文号为{LoginUserNotesid}修改，如有问题，请及时联系科技部 "; // 修改密码
        String messageTemplate1 = "{telephone},尊敬的{name}，您好，您的短信验证码为{checkcode}"; // 验证码发送
        String messageTemplate2 = "资金变动";

        String checkcode = String.format("%06d", new Random().nextInt(999999));
        System.out.println(checkcode);

        SMSUtils.SMSTemplate(messageTemplate, telephone, name);
        SMSUtils.SMSTemplate(messageTemplate1, telephone, name);
    }
}
