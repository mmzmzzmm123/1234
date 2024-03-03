package com.ruoyi.common.utils.file;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.ruoyi.common.config.AliyunOssConfig;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author zouhuu
 * @description 阿里云对象存储上传工具类
 * @date 2022/06/16 14:21:12
 */
@Component
public class AliyunOssUploadUtils {

    private static AliyunOssConfig aliyunOssConfig;


    /**
     * 使用构造方法注入配置信息
     */
    @Autowired
    public AliyunOssUploadUtils(AliyunOssConfig aliyunOssConfig) {
        AliyunOssUploadUtils.aliyunOssConfig = aliyunOssConfig;
    }

    /**
     * 上传文件
     * @param file
     * @return
     * @throws Exception
     */
    public static String uploadFile(MultipartFile file) throws Exception {

        // 生成 OSSClient
        OSS ossClient = new OSSClientBuilder().build(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessKeyId(), aliyunOssConfig.getAccessKeySecret());
        // 原始文件名称
        String originalFilename = file.getOriginalFilename();

        // 编码文件名
        String filePathName = FileUploadUtils.extractFilename(file);
//        System.out.println("==============filePathName"+filePathName);

        //filePathName = URLEncoder.encode(filePathName,"UTF-8");
        // 文件路径名称
        filePathName = aliyunOssConfig.getFilehost() + "/" + filePathName;

        try {
            ossClient.putObject(aliyunOssConfig.getBucketName(), filePathName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
//        System.out.println("=============================="+filePathName);
        String[] strings = StringUtils.split( filePathName,"/");
//        System.out.println("length================="+strings.length);
//        System.out.println("name name name name============"+strings[strings.length-1]);

        strings[strings.length-1] = URLEncoder.encode(strings[strings.length-1],"UTF-8");

//        System.out.println("name name name name============"+strings[strings.length-1]);

        for (String string : strings) {
            System.out.println(string);
        }

        filePathName = StringUtils.join(strings,"/");


        return aliyunOssConfig.getUrl() + "/" + filePathName;
    }
}

