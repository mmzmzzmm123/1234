package com.ruoyi.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.ruoyi.framework.config.FileConfig;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ByFileUtil {

    /**
     * File转成编码成BASE64
     *
     * @param file 文件对象
     * @return String
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        try {
            byte[] b = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            return Base64.getEncoder().encodeToString(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }


    /**
     * 将文件上传到七牛云
     */
    public static String uploadQNImg(InputStream file, String key, FileConfig constantQiniu) {
        // 构造一个带指定Zone对象的配置类 华北  2020-02-06 已改用华东
        Configuration cfg = new Configuration(Zone.zone0());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传
        try {
            Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
            String upToken = auth.uploadToken(constantQiniu.getBucket());
            try {
                Response response = uploadManager.put(file, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                String returnPath = constantQiniu.getDomain() + "" + putRet.key;
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
