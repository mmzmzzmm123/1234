package com.stdiet.common.utils.oss;

import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.stdiet.common.config.AliyunOSSConfig;
import com.stdiet.common.utils.file.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AliyunOSSUtils {

    //下载链接默认有效期
    private static final Long expire = 3600L * 1000 * 24;

    public static OSS getOssClient() {
        return new OSSClientBuilder().build(AliyunOSSConfig.EndPoint, AliyunOSSConfig.AccessKeyID, AliyunOSSConfig.AccessKeySecret);
    }

    /**
     * 获取图片的URL头信息
     *
     * @return 返回url头信息
     */
    private static String getURLHead() {
        //从哪个位置截取
        int cutPoint = AliyunOSSConfig.EndPoint.lastIndexOf('/') + 1;
        //http头
        String head = AliyunOSSConfig.EndPoint.substring(0, cutPoint);
        //服务器地址信息
        String tail = AliyunOSSConfig.EndPoint.substring(cutPoint);
        //返回结果
        return head + AliyunOSSConfig.Buckets + "." + tail + "/";
    }

    /**
     * 获取存储在服务器上的地址
     *
     * @param oranName 文件名
     * @return 文件URL
     */
    private static String getRealName(String oranName) {
        return getURLHead() + oranName;
    }

    /**
     * 获取一个随机的文件名
     *
     * @param oranName 初始的文件名
     * @return 返回加uuid后的文件名
     */
    private static String getRandomImageName(String prefix, String oranName) {
        //获取一个uuid 去掉-
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //查一下是否带路径
        int cutPoint = oranName.lastIndexOf("/") + 1;
        //如果存在路径
        if (cutPoint != 0) {
            //掐头 如果开头是/ 则去掉
            String head = oranName.indexOf("/") == 0 ? oranName.substring(1, cutPoint) : oranName.substring(0, cutPoint);
            //去尾
            String tail = oranName.substring(cutPoint);
            //返回正确的带路径的图片名称
            return prefix + head + uuid + "_" + tail;
        }
        //不存在 直接返回
        return prefix + uuid + "_" + oranName;
    }

    /**
     * MultipartFile2File
     * @param multipartFile
     * @return
     */
    private static File transferToFile(MultipartFile multipartFile) {
        //选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            //获取文件名
            String originalFilename = multipartFile.getOriginalFilename();
            //获取最后一个"."的位置
            int cutPoint = originalFilename.lastIndexOf(".");
            //获取文件名
            String prefix = originalFilename.substring(0,cutPoint);
            //获取后缀名
            String suffix = originalFilename.substring(cutPoint + 1);
            //创建临时文件,prefix最少三位
            file = File.createTempFile((prefix != null && prefix.length() >= 3 ? prefix : prefix+"ab"), suffix);
            //multipartFile2file
            multipartFile.transferTo(file);
            //删除临时文件
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 通过文件URL反向解析文件名
     *
     * @param fileURL 文件URL
     * @return 原文件名
     */
    private static String getObjectName(String fileURL) {
        return fileURL.substring(getURLHead().length());
    }

    /**
     * 上传文件流
     * @param prefix 路径的前缀路径目录
     * @param oranFileName 上传到服务器上的文件路径和名称
     * @param file         来自本地的文件或者文件流
     */
    public static String uploadFileInputSteam(String prefix, String oranFileName, MultipartFile file) {

        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
        String objectName = getRandomImageName(prefix, oranFileName);

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 上传文件流
        try (InputStream inputStream = new FileInputStream(transferToFile(file))) {
            //上传到OSS
            ossClient.putObject(AliyunOSSConfig.Buckets, objectName, inputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回文件在服务器上的全路径+名称
        return getRealName(objectName);
    }


    /**
     * 上传文件流
     * @param prefix 路径的前缀路径目录
     * @param oranFileName 上传到服务器上的文件路径和名称
     * @param file         来自本地的文件或者文件流
     */
    public static String uploadFileInputSteam(String prefix, String oranFileName, File file) {

        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
        String objectName = getRandomImageName(prefix, oranFileName);

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 上传文件流。
        try (InputStream inputStream = new FileInputStream(file);) {
            //上传到OSS
            ossClient.putObject(AliyunOSSConfig.Buckets, objectName, inputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回文件在服务器上的全路径+名称
        return getRealName(objectName);
    }

    /**
     * 从OSS中下载一个文件
     *
     * @param fileURL       文件的url
     * @param localFileName 下载到本地的文件名称
     */
    public static void downloadFileToLoacal(String fileURL, String localFileName) {

        //将url解析成objectName
        String objectName = getObjectName(fileURL);

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(AliyunOSSConfig.Buckets, objectName), new File(localFileName));

        // 关闭OSSClient。
        ossClient.shutdown();

    }

    /**
     * 从OSS中下载一个文件流
     *
     * @param fileURL       文件的url
     */
    public static void downloadFile(String fileURL, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {

        //将url解析成objectName
        String objectName = getObjectName(fileURL);

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        OSSObject ossObject = ossClient.getObject(AliyunOSSConfig.Buckets, objectName);

        //获取流
        InputStream streamData = ossObject.getObjectContent();

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));
        FileUtils.writeBytes(streamData , response.getOutputStream());

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     *
     * @param fileUrl
     * @return
     */
    public static String generatePresignedUrl(String fileUrl){
        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        Date expiration = new Date(System.currentTimeMillis() + expire);

        String url = ossClient.generatePresignedUrl(AliyunOSSConfig.Buckets, getObjectName(fileUrl), expiration).toString();

        // 关闭OSSClient。
        ossClient.shutdown();

        return url;
    }

    /**
     *
     * @param fileUrl
     * @return
     */
    public static List<String> generatePresignedUrl(List<String> fileUrlList){
        List<String> downUrlList = new ArrayList<>();

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        Date expiration = new Date(System.currentTimeMillis() + expire);

        for (String fileUrl : fileUrlList) {
            String url = ossClient.generatePresignedUrl(AliyunOSSConfig.Buckets, getObjectName(fileUrl), expiration).toString();
            downUrlList.add(url);
        }

        // 关闭OSSClient。
        ossClient.shutdown();

        return downUrlList;
    }

    /**
     * 删除指定路径下的一个文件
     *
     * @param fileURL 文件的全称
     */
    public static void deleteFile(String fileURL) {

        // 反向解析文件名
        String objectName = getObjectName(fileURL);

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(AliyunOSSConfig.Buckets, objectName);

        //删除成功 打印文件存储地址
        printDeleteSuccessInfo(fileURL);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 打印文件的存储地址
     *
     * @param fileURL 文件URL
     */
    private static void printUploadSuccessInfo(String fileURL) {
        //上传成功
        System.out.println("upload success， path = " + getRealName(fileURL));
    }

    /**
     * 打印文件的存储地址
     *
     * @param fileURL 文件URL
     */
    private static void printDeleteSuccessInfo(String fileURL) {
        //上传成功
        System.out.println("delete success， path = " + getRealName(fileURL));
    }

    /**
     * 通过文件的URL 判断文件是否存在
     *
     * @param fileURL 文件的URL
     * @return 文件是否存在
     */
    public static boolean exists(String fileURL) {

        // 反向解析文件名
        String objectName = getObjectName(fileURL);

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
        boolean found = ossClient.doesObjectExist(AliyunOSSConfig.Buckets, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();

        //  返回是否存在
        return found;
    }

}
