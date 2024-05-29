package com.onethinker.file.platform;

import cn.hutool.core.collection.CollectionUtil;
import com.onethinker.common.webp.exc.WebpEncodeUtil;
import com.onethinker.common.config.FileStorageProperties.*;
import com.onethinker.file.domain.FileInfo;
import com.onethinker.file.dto.FileInfoDTO;
import com.onethinker.file.event.FormFileUploadSuccessEvent;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static java.nio.file.Files.exists;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
public interface FileStorage {

    Logger log = LoggerFactory.getLogger(FileStorage.class);

    String FILE_EXTENSION_FLAG = ".";
    String DATA_FILE = "data";
    int DEFAULT_THUMBNAIL_SIZE = 150;
    String IMAGE = "image";
    String WEBP_FILE = "webp";
    String WEBP_ORIGINAL_FILE = "original.webp";
    String THUMBNAIL_FILE = "thumb.jpg";
    String WATERMARK_FILE = "watermark.jpg";
    String UPLOAD_PATH = "/file-server";

    /**
     * 获取平台
     *
     * @return 返回平台内容
     */
    String getPlatform();

    /**
     * 获取访问链接
     *
     * @return 返回域名信息
     */
    String getDomain();

    /**
     * 上传文件
     *
     * @param file 文件内容
     * @return 返回文件信息
     */
    FileInfo upload(MultipartFile file);

    /**
     * 上传文件
     *
     * @return 返回文件信息
     */
    FileInfo upload();

    /**
     * 设置文件来源信息
     *
     * @param source 文件资源
     * @return 返回对应平台文件处理
     */
    FileStorage serFile(MultipartFile source);

    /**
     * 下载文件信息
     *
     * @param fileInfoDTO 文件信息
     * @return 返回文件下载
     * @throws FileNotFoundException 文件不存在
     */
    FileInputStream download(FileInfoDTO fileInfoDTO) throws FileNotFoundException;

    /**
     * 生成其他图片信息
     *
     * @param formFileUploadSuccessEvent formFileUploadSuccessEvent
     */
    void handleFormFileUploadSuccessEvent(FormFileUploadSuccessEvent formFileUploadSuccessEvent);

    /**
     * 设置文件指纹
     *
     * @param multipartFile fileInfo
     * @return 返回MD5
     */
    default String queryFingerprint(MultipartFile multipartFile) {
        try (InputStream is = multipartFile.getInputStream()) {
            return DigestUtils.md5Hex(is);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 检测文件的MIME类型
     *
     * @param file      fileInfo
     * @param allowMime allowMime
     * @return 返回文件扩展名
     */
    default String queryDetectMime(MultipartFile file, List<String> allowMime) {
        Tika tika = new Tika();
        try (InputStream inputStream = file.getInputStream()) {
            String detect = tika.detect(inputStream);
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
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件全路径（相对存储平台的存储路径）
     *
     * @param basePath 基本路径
     * @param fileInfo 文件信息
     * @return 返回文件路径
     */
    default String getFileKey(String basePath, FileInfo fileInfo) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        fileInfo.setId(id);
        // 获取文件全路径（相对存储平台的存储路径）
        return String.join(File.separator, basePath, "form", String.valueOf(now.getYear()), String.valueOf(now.getMonthValue()), String.valueOf(now.getDayOfMonth()), id, "data");
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

    /**
     * 获取文件扩展名
     *
     * @param fileName   文件全名
     * @param extensions 文件校验
     * @return 文件扩展名
     */
    default String getExtension(String fileName, List<String> extensions) {
        String extension = getExtension(fileName);
        if (CollectionUtils.isNotEmpty(extensions) && !extensions.contains(extension)) {
            throw new RuntimeException("不允许上传类型：" + extension);
        }
        return StringUtils.lowerCase(extension);
    }

    /**
     * 获取图片信息
     *
     * @param file 文件
     * @return 图片信息
     */
    default int[] getImgSize(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            return new int[]{img.getWidth(), img.getHeight()};
        } catch (IOException e) {
            return new int[]{0, 0};
        }
    }


    /**
     * 获取图片信息
     *
     * @param file 文件
     * @return 图片信息
     */
    default int[] getImgSize(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage img = ImageIO.read(inputStream);
            return new int[]{img.getWidth(), img.getHeight()};
        } catch (IOException e) {
            return new int[]{0, 0};
        }
    }

    /**
     * 获取图片信息
     *
     * @param dataFile 文件
     * @param type     文件类型
     * @return 图片信息
     */
    default Path getTransFile(Path dataFile, String type) {
        return Paths.get(dataFile.getParent().toString(), String.join(".", DATA_FILE, type));
    }

    /**
     * 获取图片信息
     *
     * @param path 文件
     * @param type     文件类型
     * @return 图片信息
     */
    default String getTransFile(String path, String type) {
        if (ObjectUtils.isNotEmpty(path) && !path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        return path + String.join(".", DATA_FILE, type);
    }

    /**
     * 获取图片信息
     * @param userId 用户id
     * @return 图片信息
     */
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

    /**
     * 获取图片流信息
     * @param source 文件来源
     * @param fileInfo 文件信息
     * @param waterMark 图片信息
     * @return 图片流
     * @throws IOException io异常
     */
    default InputStream processWatermark(MultipartFile source, FileInfo fileInfo, WaterMark waterMark) throws IOException {
        log.debug("Start Process Watermark:{},{}", fileInfo.getId(), fileInfo.getCreateUserId());
        if (waterMark == null || !waterMark.isEnabled()) {
            throw new RuntimeException("Process Watermark is null");
        }
        int minWidth = 300;
        int minHeight = 100;
        if (waterMark.getMinWidth() > 0) {
            minWidth = waterMark.getMinWidth();
        }
        if (waterMark.getMinHeight() > 0) {
            minHeight = waterMark.getMinHeight();
        }
        int[] imgSize = getImgSize(source);
        if (imgSize[0] > minWidth && imgSize[1] > minHeight) {
            log.debug("Start Process Watermark:{}", fileInfo.getId());
            Thumbnails.Builder<BufferedImage> builder;
            try (InputStream is = source.getInputStream()) {
                builder = Thumbnails.of(ImageIO.read(is));
            }
            BufferedImage bufferedImage = getWatermark(fileInfo.getCreateUserId());
            builder.watermark(Positions.CENTER, bufferedImage, 0.5f);
            if (imgSize[0] > (minWidth + minWidth)) {
                builder.watermark(Positions.BOTTOM_LEFT, bufferedImage, 0.5f)
                        .watermark(Positions.BOTTOM_RIGHT, bufferedImage, 0.5f)
                        .watermark(Positions.TOP_LEFT, bufferedImage, 0.5f)
                        .watermark(Positions.TOP_RIGHT, bufferedImage, 0.5f);
            } else {
                builder.watermark(Positions.BOTTOM_CENTER, bufferedImage, 0.5f)
                        .watermark(Positions.TOP_CENTER, bufferedImage, 0.5f);
            }
            // 将图像写入到 ByteArrayOutputStream 中
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            builder.scale(1).outputFormat(fileInfo.getExtension().replace(".", "")).toOutputStream(os);
            return new ByteArrayInputStream(os.toByteArray());
        }
        return null;
    }


    /**
     * 获取图片流信息
     * @param source 文件来源
     * @param fileInfo 文件信息
     * @param thumbnail 图片信息
     * @return 图片流
     * @throws IOException io异常
     */
    default InputStream processThumbnail(MultipartFile source, FileInfo fileInfo, Thumbnail thumbnail) throws IOException {
        log.debug("Start Process Thumbnail:{}", fileInfo.getId());
        int width = DEFAULT_THUMBNAIL_SIZE;
        int height = DEFAULT_THUMBNAIL_SIZE;
        float quality = 0.5f;
        if (thumbnail != null) {
            width = thumbnail.getWidth() <= 0 ? DEFAULT_THUMBNAIL_SIZE : thumbnail.getWidth();
            height = thumbnail.getHeight() <= 0 ? DEFAULT_THUMBNAIL_SIZE : thumbnail.getHeight();
            quality = thumbnail.getQuality();
        }
        // 将图像写入到 ByteArrayOutputStream 中
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (InputStream is = source.getInputStream()) {
            Thumbnails.of(is).size(width, height).outputQuality(quality).toOutputStream(os);
        }
        return new ByteArrayInputStream(os.toByteArray());
    }

    /**
     * 获取图片路径信息
     * @param source 文件来源
     * @param fileInfo 文件信息
     * @param filePath 图片路径
     * @return 图片路径信息
     * @throws IOException io异常
     */
    default Path processWebp(MultipartFile source, FileInfo fileInfo, String filePath) throws IOException {
        if (isWindows()) {
            filePath = "C:\\" + filePath;
        }
        log.debug("Start Process Webp:{},{}", fileInfo.getId(), filePath);
        File tempFile = new File(filePath + File.separator + DATA_FILE);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
            // 创建临时文件
            source.transferTo(tempFile);
        }
        Path dataFile = tempFile.toPath();
        Path waterMarkFile = getTransFile(dataFile, WATERMARK_FILE);
        if (exists(waterMarkFile)) {
            dataFile = waterMarkFile;
        }
        Path webpFile = getTransFile(dataFile, WEBP_FILE);
        int[] imgSize = getImgSize(dataFile.toFile());
        int minWidth = 400;
        int minHeight = 300;
        if (imgSize[0] > minWidth && imgSize[1] > minHeight) {
            WebpEncodeUtil.toWebp(dataFile, webpFile, (int) (imgSize[0] * 0.8), (int) (imgSize[1] * 0.8));
        } else {
            WebpEncodeUtil.toWebp(dataFile, webpFile);
        }
        log.debug("Finish Process Webp:{}", fileInfo.getId());
        return webpFile;
    }

    /**
     * 获取图片路径信息
     * @param source 文件来源
     * @param fileInfo 文件信息
     * @param filePath 图片路径
     * @return 图片路径信息
     * @throws IOException io异常
     */
    default Path processWebpOriginal(MultipartFile source, FileInfo fileInfo, String filePath) throws IOException {
        // 创建临时文件
        if (isWindows()) {
            filePath = "C:\\" + filePath;
        }
        log.debug("Start Process Webp Original:{},{}", fileInfo.getId(), filePath);
        File tempFile = new File(filePath + File.separator + DATA_FILE);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
            // 将MultipartFile转换为临时文件
            source.transferTo(tempFile);
        }
        Path dataFile = tempFile.toPath();
        Path webpOriginalFile = getTransFile(dataFile, WEBP_ORIGINAL_FILE);
        if (exists(webpOriginalFile)) {
            dataFile = webpOriginalFile;
        }
        WebpEncodeUtil.toWebp(dataFile, webpOriginalFile);
        log.debug("Finish Process Webp Original:{}", fileInfo.getId());
        return webpOriginalFile;
    }

    /**
     * 判断是否是win环境
     * @return 是否是win环境
     */
    default boolean isWindows() {
        return (System.getProperty("os.name").toLowerCase().contains("win"));
    }

    /**
     * 删除指定路径的目录及其所有内容
     *
     * @param dirPath 目录的路径
     */
    default void deleteDirectory(String dirPath) {
        if (isWindows()) {
            dirPath = "C:\\" + dirPath;
        }
        Path path = Paths.get(dirPath);
        try {
            // 递归删除目录及其内容
            deleteRecursively(path);
        } catch (IOException e) {
            System.err.println("删除目录时发生错误: " + e.getMessage());
        }
    }

    /**
     * 删除指定路径的目录及其所有内容
     * @param path 目录的路径
     * @throws IOException io异常
     */
    default void deleteRecursively(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteRecursively(entry);
                }
            }
        }
        Files.delete(path);
    }

    /**
     * 获取临时文件流信息
     *
     * @param inputStream 文件流
     * @return 文件信息
     * @throws IOException io异常
     */
    default File queryFileInputStream(InputStream inputStream) throws IOException {
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            return tempFile;
        } catch (Exception e) {
            log.error("文件流处理异常");
            throw new RuntimeException("文件流处理异常：" + e.getMessage());
        }
    }
}
