package com.stdiet.custom.utils;

import com.alibaba.fastjson.JSONObject;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.http.HttpUtils;
import com.stdiet.custom.domain.WxXmlData;
import com.stdiet.custom.domain.wechat.WxAccessToken;
import com.stdiet.custom.domain.wechat.WxFileUploadResult;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WxTokenUtils {

    public static final String KEY_ACCESS_TOKEN = "wx:access_token";
    public static final String KEY_ACCESS_TOKEN_WATHER = "wx:access_token_watcher";

    // 与接口配置信息中的Token要一致
    private static String token = "shengtangdiet";
    // 胜唐体控
    private static String appId = "wx4a9c1fc9dba53202";
    private static String appSecret = "fff029ade5d3575df755f4cf9e52f8da";
    // 胜唐体控李晓
//    private static String appId = "wxaf10fe560ea043a0";
//    private static String appSecret = "afb47e477337df23b7562c3c1f965826";
    private static String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
    private static String uploadMaterialUrl = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=image";


    public static WxAccessToken fetchAccessToken() {
        try {
            String resStr = HttpUtils.sendGet(tokenUrl, "grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);
            if (StringUtils.isEmpty(resStr)) {
                return null;
            }
            JSONObject obj = JSONObject.parseObject(resStr);

            WxAccessToken token = JSONObject.toJavaObject(obj, WxAccessToken.class);
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 模拟form表单的形式 ，上传文件 以输出流的形式把文件写入到url中，然后用输入流来获取url的响应
     *
     * @return String url的响应信息返回值
     * @throws IOException
     */
    public static WxFileUploadResult uploadImage(File file, String fileName, String accessToken) throws Exception {
        String url = uploadMaterialUrl.replaceAll("ACCESS_TOKEN", accessToken);;//微信上传图片的url
        String result = null;
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        // 第一部分
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        // 设置关键值
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        WxFileUploadResult result1 = JSONObject.toJavaObject(jsonObject, WxFileUploadResult.class);
        result1.setUrl(result);
        result1.setUrl(fileName);
        return result1;
    }

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        // Arrays.sort(arr);
        sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    public static void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    public static WxXmlData resolveXmlData(InputStream in) {
        WxXmlData wxXmlData = null;
        try {
            String xmlData = IOUtils.toString(in, StandardCharsets.UTF_8.name());
            XStream xstream = new XStream();
            //这个必须要加 不然无法转换成WxXmlData对象
            xstream.setClassLoader(WxXmlData.class.getClassLoader());
            xstream.processAnnotations(WxXmlData.class);
            xstream.alias("xml", WxXmlData.class);
            wxXmlData = (WxXmlData) xstream.fromXML(xmlData);
//            log.info("【wxXmlData: {}】 ", wxXmlData);
        } catch (Exception e) {
//            log.error("【error】{}", e.getMessage());
        }
        return wxXmlData;
    }
}
