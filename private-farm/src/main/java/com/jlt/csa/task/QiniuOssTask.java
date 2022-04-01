package com.jlt.csa.task;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("qiniuTask")
public class QiniuOssTask {
    private QiniuConfiguration config;
    private UploadManager uploadManager;
    private String upToken;

    public int amount = 0;

    @Autowired
    public void setConfig(QiniuConfiguration config) {
        this.config = config;
    }

    /**
     * 无参任务测试
     */
    public void ryNoParams()
    {
        System.out.println("[实例任务] ==> 执行无参方法，第 " + (++amount) + " 次.");
    }

    /**
     * 供定时任务调用的Bean方法
     */
    public void testUpload() {
        getUploadManager();
        makeToken();
        DefaultPutRet putRet = uploadFile(null, "d:/time.jpg");
        System.out.println(putRet.hash);
    }

    /**
     * 使用类Bean的上传管理器、upToken上传指定key和路径的文件
     * @param key key作为上传文件的云存储文件名，如果为null则使用hash作为文件名
     * @param filePath 上传的文件路径
     * @return 七牛云上传结果类
     */
    public DefaultPutRet uploadFile(String key, String filePath) {
        return uploadFile(this.uploadManager, this.upToken, key, filePath);
    }

    /**
     * 使用指定的UploadManager、upToken、key和filePath上传文件
     * @param uploadManager 上传管理器
     * @param token 上传token
     * @param key key作为上传文件的云存储文件名，如果为null则使用hash作为文件名
     * @param filePath 上传的文件路径
     * @return 七牛云上传结果类
     */
    public DefaultPutRet uploadFile(UploadManager uploadManager, String token, String key, String filePath) {
        try {
            Response response = uploadManager.put(filePath, key, token);
            String bodyStr = response.bodyString();
            DefaultPutRet putRet = Json.decode(bodyStr, DefaultPutRet.class);
            return putRet;
        } catch (QiniuException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取上传管理器，并保存在类成员中
     */
    public UploadManager getUploadManager() {
        Configuration cfg = new Configuration(Region.huabei());
        cfg.useHttpsDomains = false;
        uploadManager = new UploadManager(cfg);

        return uploadManager;
    }


    /**
     * 获取七牛云上传Token，并保存在类成员中
     * @return
     */
    public String makeToken() {
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        upToken = auth.uploadToken(config.getBucket());

        return upToken;
    }
}
