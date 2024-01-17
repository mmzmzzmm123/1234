package com.ruoyi.common.tools;

import com.ruoyi.common.constant.PlayConstants;
import com.ruoyi.common.obs.Obs;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlReplaceTools {

    public static String basename(String str) {
        if (str == null) {
            return "";
        }
        int questionIndex = str.indexOf("?");
        if (questionIndex != -1) {
            str = str.substring(0, questionIndex);
        }
        // The Unix separator character.
        char unixSeparator = '/';
        // The Windows separator character.
        char windowsSeparator = '\\';
        final int lastUnixPos = str.lastIndexOf(unixSeparator);
        final int lastWindowsPos = str.lastIndexOf(windowsSeparator);
        int lastIndex = Math.max(lastUnixPos, lastWindowsPos);
        return str.substring(lastIndex + 1);
    }

    public static String replace(String fileURL) {
        HttpURLConnection httpConn = null;
        try {

            String bName = basename(fileURL);
            URL url = new URL(fileURL);
            httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();
            // 检查HTTP响应是否正常
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpConn.getInputStream();
                // 使用 inputStream 处理文件流，例如读取内容或者做其他处理
                try {
                    return Obs.upload(PlayConstants.obs_ak, PlayConstants.obs_sk, PlayConstants.obs_bulkName,
                            PlayConstants.obs_endPoint, bName, inputStream, PlayConstants.obs_downloadUrl);
                } finally {
                    inputStream.close(); // 关闭输入流
                    httpConn.disconnect(); // 断开连接
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect(); // 断开连接
            }
        }
        return null;
    }
}
