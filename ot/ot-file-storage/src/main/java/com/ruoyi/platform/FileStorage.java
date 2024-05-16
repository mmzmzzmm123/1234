package com.ruoyi.platform;

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.bean.FileInfo;
import com.ruoyi.event.FormFileUploadSuccessEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
public interface FileStorage {

    String FILE_EXTENSION_FLAG = ".";
    String DATA_FILE = "data";

    /**
     * 获取平台
     */
    String getPlatform();

    /**
     * 设置平台
     *
     * @param platform 平台标识
     */
    void setPlatform(String platform);

    /**
     * 设置文件名称
     */
    FileStorage setName(String name);

    /**
     * 上传文件
     *
     * @param file 文件内容
     * @return
     */
    FileInfo upload(MultipartFile file);


    /**
     * 上传文件
     *
     * @return
     */
    FileInfo upload();

    /**
     * 设置文件来源信息
     *
     * @param source   文件资源
     * @param fileName 文件名称
     * @return
     */
    FileStorage serFile(MultipartFile source, String fileName);

    /**
     * 检测文件的MIME类型
     *
     * @param fileInfo fileInfo
     * @return
     */
    String detectMime(FileInfo fileInfo);

    /**
     * 生成其他图片信息
     *
     * @param formFileUploadSuccessEvent formFileUploadSuccessEvent
     */
    void handleFormFileUploadSuccessEvent(FormFileUploadSuccessEvent formFileUploadSuccessEvent);

    /**
     * 获取文件全路径（相对存储平台的存储路径）
     *
     * @param basePath 基本路径
     */
    default String getFileKey(String basePath, FileInfo fileInfo) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        fileInfo.setId(id);
        // 获取文件全路径（相对存储平台的存储路径）
        return String.join("/", basePath, "uploads", "form", String.valueOf(now.getYear()), String.valueOf(now.getMonthValue()), String.valueOf(now.getDayOfMonth()), id, "data");
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件全名
     * @return 文件扩展名
     */
    default String getExtension(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        String ext = ".tar.gz";
        if (fileName.endsWith(ext)) {
            return ext;
        }
        if (StringUtils.contains(fileName, FILE_EXTENSION_FLAG)) {
            return fileName.substring(fileName.lastIndexOf(FILE_EXTENSION_FLAG));
        }
        return null;
    }

    default String getExtension(String fileName, List<String> extensions) {
        String extension = getExtension(fileName);
        if (CollectionUtils.isNotEmpty(extensions) && !extensions.contains(extension)) {
            throw new RuntimeException("不允许上传类型：" + extension);
        }
        return StringUtils.lowerCase(extension);
    }

    default String queryDetectMime(MultipartFile file, List<String> allowMime) {
        Tika tika = new Tika();
        try {
            String detect = tika.detect(file.getInputStream());
            if (!StringUtils.isBlank(detect)) {
                String flag = ";";
                String mime = detect;
                if (detect.contains(flag)) {
                    mime = detect.split(flag)[0];
                }
                if (CollectionUtil.isNotEmpty(allowMime) && !allowMime.contains(mime)) {
                    throw new RuntimeException(detect);
                }
            }
            return detect;
        } catch (IOException e) {
        }
        return null;
    }

    default int[] getImgSize(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            return new int[]{img.getWidth(), img.getHeight()};
        } catch (IOException e) {
            return new int[]{0, 0};
        }
    }

    default int[] getImgSize(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedImage img = ImageIO.read(inputStream);
            return new int[]{img.getWidth(), img.getHeight()};
        } catch (IOException e) {
            return new int[]{0, 0};
        }
    }

    default Path getTransFile(Path dataFile, String type) {
        return Paths.get(dataFile.getParent().toString(), String.join(".", DATA_FILE, type));
    }

    default String getTransFile(String path, String type) {
        if (ObjectUtils.isNotEmpty(path) && !path.endsWith("/")) {
            path = path + "/";
        }
        return path + String.join(".", DATA_FILE, type);
    }

    default String getUrlSafeBase64(String input) {
        // 使用Java的Base64类进行编码
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
        String base64 = new String(encodedBytes, StandardCharsets.UTF_8);

        // 什么是 URL 安全的 BASE64 编码？
        // 在数据万象的处理操作中，有很多参数需要进行 URL 安全的 BASE64 编码，例如文字水印的文字内容、颜色、字体设置和图片水印的水印图链接。URL 安全的 BASE64 编码具体规则为：
        // 1. 将普通 BASE64 编码结果中的加号（+）替换成连接号（-）；
        // 2. 将编码结果中的正斜线（/）替换成下划线（_）；
        // 3. 将编码结果中的“=”去掉。
        // 相关文档 https://cloud.tencent.com/document/product/460/86595#1b54a43c-d985-441d-9c89-81ac6c4adc39
        // 根据规则进行替换
        base64 = base64.replaceAll("\\+", "-").replaceAll("/", "_").replaceAll("=", "");

        return base64;
    }

    default BufferedImage getWatermark(String userId) {
        final Font font = new Font(null, Font.PLAIN, 12);
        BufferedImage image = new BufferedImage(262, 35, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        image = g.getDeviceConfiguration().createCompatibleImage(260, 35, Transparency.TRANSLUCENT);

        int y = 0;
        int divider30 = 30;

        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.YELLOW);
        g.setFont(font);
        String txt = "IOPS" + userId;
        g.drawString(txt, 0, y + divider30);
        g.dispose();
        return image;
    }
}
