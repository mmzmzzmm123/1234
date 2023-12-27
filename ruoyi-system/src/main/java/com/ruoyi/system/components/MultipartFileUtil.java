package com.ruoyi.system.components;

import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.Ids;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author ZhuShaobo
 */
@Slf4j
@Component
public abstract class MultipartFileUtil {

    /**
     * 获取文件后缀名
     *
     * @param file 文件类型
     * @return 文件后缀名
     */
    public static String getSuffixName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            throw new GlobalException("文件名不能为空");
        }

        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }


    public static String getSuffixName(File file) {
        String originalFilename = file.getName();
        if (StringUtils.isBlank(originalFilename)) {
            throw new GlobalException("文件名不能为空");
        }

        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    public static String getAccountId(File file) {
        if (file == null) {
            return null;
        }
        String name = file.getName();
        return name.substring(0, name.lastIndexOf("."));
    }


    public static void unzipFile(String localFilePath) {
        try (ZipFile zipFile = new ZipFile(localFilePath)) {

            // 获取Zip中的所有文件
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                // 暂不可用
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<String> analyseTextFile(MultipartFile file) {
        String suffixName = MultipartFileUtil.getSuffixName(file);
        if (!".txt".equals(suffixName)) {
            throw new IllegalArgumentException("文件不是txt类型");
        }

        try (InputStream is = file.getInputStream()){
            @Cleanup BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            return bufferedReader.lines().distinct().collect(Collectors.toList());
        } catch (IOException e) {
            throw new GlobalException("服务繁忙,稍后重试");
        }
    }


    public static List<File> transferFiles(List<MultipartFile> files,String root) {
        if (CollectionUtils.isEmpty(files)) {
            throw new GlobalException("上传文件不能为空");
        }

        String localFilePath = null;
        List<File> localFiles = new ArrayList<>();

        // 文件夹路径 - 同一批次文件放在同一目录下
        String folderName = Ids.getId();

        for (MultipartFile file : files) {

            File localFile = null;

            // 将网络文件转存到本地
            try (InputStream inputStream = file.getInputStream()) {
                String folderPath = root + "/" + folderName + "/";
                localFilePath = folderPath + file.getOriginalFilename();
                localFile = new File(localFilePath);

                // 创建文件路径
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                if (!localFile.exists()) {
                    localFile.createNewFile();
                }

                // 写本地文件
                try (FileOutputStream fileOutputStream = new FileOutputStream(localFile)) {
                    byte[] bytes = new byte[4096];

                    while (inputStream.read(bytes) > 0) {
                        fileOutputStream.write(bytes);
                    }
                    fileOutputStream.flush();
                }
            } catch (IOException e) {
                log.error("转存上传文件至服务器失败 {}", e.getMessage(), e);
                throw new GlobalException("转存上传文件至服务器失败");
            }
            localFiles.add(localFile);
        }

        return localFiles;
    }


    public static boolean isExcel(MultipartFile file) {
        String suffixName = MultipartFileUtil.getSuffixName(file);
        return ".xls".equals(suffixName) || ".xlsx".equals(suffixName);
    }


    public static boolean isText(MultipartFile file) {
        String suffixName = MultipartFileUtil.getSuffixName(file);
        return ".txt".equals(suffixName);
    }

}
