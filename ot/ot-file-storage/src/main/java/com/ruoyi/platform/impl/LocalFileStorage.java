package com.ruoyi.platform.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.config.FileStorageProperties;
import com.ruoyi.config.FileStorageProperties.LocalConfig;
import com.ruoyi.bean.FileInfo;
import com.ruoyi.event.FormFileUploadSuccessEvent;
import com.ruoyi.platform.FileStorage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.tika.Tika;
import org.apache.tika.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.ruoyi.config.FileStorageProperties.*;
import net.coobird.thumbnailator.Thumbnails;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
@Data
@NoArgsConstructor
@Log4j2
public class LocalFileStorage implements FileStorage {
    private String platform;
    private String basePath;
    private List<String> allowMime;
    private WaterMark waterMark;
    private Thumbnail thumbnail;
    private static final int DEFAULT_THUMBNAIL_SIZE = 150;
    private static final String IMAGE = "image";
    private static final String WEBP_FILE = "webp";
    private static final String WEBP_ORIGINAL_FILE = "original.webp";
    private static final String THUMBNAIL_FILE = "thumb.jpg";
    private static final String WATERMARK_FILE = "watermark.jpg";
    private static final String DATA_FILE = "data";
    private static final String WEBP_MIME = "image/webp";
    private static final String WEBP_EXTENSION = "." + WEBP_FILE;
    private static final String WEBP_ORIGIN_EXTENSION = "." + WEBP_ORIGINAL_FILE;
    private static final String JPG_MIME = "image/jpeg";
    private static final String JPG_EXTENSION = ".jpg";
    private static final String YZY_APP_NAME = "YZY_H5";
    private static final String SERVICE_UPLOAD_CONTENT_TYPE = "application/offset+octet-stream";


    private ApplicationContext applicationContext;

    public LocalFileStorage(LocalConfig config) {
        platform = config.getPlatform();
        basePath = config.getBasePath();
        allowMime = config.getAllowMime();
        waterMark = config.getWaterMark();
        thumbnail = config.getThumbnail();
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Override
    public void close() {
        System.out.println("localFileStorage close");
    }

    @Override
    public FileInfo upload(MultipartFile file) {
        String newKey = getFileKey(basePath, file.getName());
        Path dataFile = Paths.get(newKey);
        if (Files.notExists(dataFile)) {
            //noinspection ResultOfMethodCallIgnored
            dataFile.getParent().toFile().mkdirs();
        }
        try {
            file.transferTo(dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FileInfo();
    }

    @Override
    public String detectMime(FileInfo fileInfo) {
        Path dataFile = Paths.get(fileInfo.getDiskPath());
        if (Files.exists(dataFile)) {
            if (dataFile.toFile().length() > 0) {
                Tika tika = new Tika();
                try {
                    String detect = tika.detect(dataFile);
                    log.debug("Detect Mime:{}", detect);
                    if (!StringUtils.isBlank(detect)) {
                        String flag = ";";
                        String mime = detect;
                        if (detect.contains(flag)) {
                            mime = detect.split(flag)[0];
                        }
                        if (CollectionUtil.isNotEmpty(allowMime) && !allowMime.contains(mime)) {
                            throw new RuntimeException(detect);
                        }
                    } else {
                        log.warn("Detect Mime Blank:{}", dataFile);
                    }
                    return detect;
                } catch (IOException e) {
                    log.error("", e);
                }
            } else {
                log.debug("Data File Size 0:{}", dataFile);
            }
        } else {
            log.debug("Data File Not Exists:{}", dataFile);
        }
        return null;
    }

    @Override
    public void publishEvent(FileInfo fileInfo) {
        Path dataFile = Paths.get(fileInfo.getDiskPath());
        FormFileUploadSuccessEvent formFileUploadSuccessEvent = new FormFileUploadSuccessEvent(dataFile, fileInfo.getId(), fileInfo.getCreateUserId(), fileInfo.getMimeType());
        applicationContext.publishEvent(formFileUploadSuccessEvent);
    }

    @EventListener
    @Async
    public void handleFormFileUploadSuccessEvent(FormFileUploadSuccessEvent formFileUploadSuccessEvent) {
        log.debug("handleFormFileUploadSuccessEvent:{}", formFileUploadSuccessEvent);
        Path dataFile = (Path) formFileUploadSuccessEvent.getSource();
        String fileMimeType = formFileUploadSuccessEvent.getMimeType();
        if (fileMimeType != null && fileMimeType.startsWith(IMAGE)) {
            log.debug("Start Process Image Ext:{}", formFileUploadSuccessEvent.getId());
            try {
                processWatermark(formFileUploadSuccessEvent.getId(), formFileUploadSuccessEvent.getUserId(), dataFile);
            } catch (Exception e) {
                log.error("Process Thumbnail Error:{}", formFileUploadSuccessEvent.getId(), e);
            }
            try {
                processThumbnail(formFileUploadSuccessEvent.getId(), dataFile);
            } catch (Exception e) {
                log.error("Process Thumbnail Error:{}", formFileUploadSuccessEvent.getId(), e);
            }
            log.debug("Finish Process Image Ext:{}", formFileUploadSuccessEvent.getId());
        }

    }

    private void processWatermark(String id, String userId, Path dataFile) throws IOException {
        log.debug("Start Process Watermark:{},{},{}", id, userId, dataFile);
        if (waterMark == null || !waterMark.isEnabled()) {
            return;
        }
        int minWidth = 300;
        int minHeight = 100;
        if (waterMark.getMinWidth() > 0) {
            minWidth = waterMark.getMinWidth();
        }
        if (waterMark.getMinHeight() > 0) {
            minHeight = waterMark.getMinHeight();
        }
        int[] imgSize = getImgSize(dataFile.toFile());
        if (imgSize[0] > minWidth && imgSize[1] > minHeight) {
            log.debug("Start Process Watermark:{}", id);
            Path targetFile = getTransFile(dataFile, WATERMARK_FILE);
            Thumbnails.Builder<BufferedImage> builder = Thumbnails.of(ImageIO.read(dataFile.toFile()));
            BufferedImage bufferedImage = getWatermark(userId);
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
            builder.scale(1).toFile(targetFile.toFile());
            log.debug("Finish Process Watermark:{}", id);
        }
    }

    private void processThumbnail(String id, Path dataFile) throws IOException {
        Path thumbFile = getTransFile(dataFile, THUMBNAIL_FILE);
        log.debug("Start Process Thumbnail:{}", thumbFile);
        int width = DEFAULT_THUMBNAIL_SIZE;
        int height = DEFAULT_THUMBNAIL_SIZE;
        float quality = 0.5f;
        if (thumbnail != null) {
            width = thumbnail.getWidth() <= 0 ? DEFAULT_THUMBNAIL_SIZE : thumbnail.getWidth();
            height = thumbnail.getHeight() <= 0 ? DEFAULT_THUMBNAIL_SIZE : thumbnail.getHeight();
            quality = thumbnail.getQuality();
        }
        Thumbnails.of(dataFile.toFile())
                .size(width, height)
                .outputQuality(quality)
                .toFile(thumbFile.toFile());
        log.debug("Finish Process Thumbnail:{}", id);
    }

    private int[] getImgSize(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            return new int[]{img.getWidth(), img.getHeight()};
        } catch (IOException e) {
            log.error("", e);
            return new int[]{0, 0};
        }
    }

    private Path getTransFile(Path dataFile, String type) {
        return Paths.get(dataFile.getParent().toString(), String.join(".", DATA_FILE, type));
    }

    private BufferedImage getWatermark(String userId) {
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
        log.debug("Create Watermark Txt:{},{}", userId, txt);
        g.drawString(txt, 0, y + divider30);
        g.dispose();
        return image;
    }

    private boolean exists(Path file) {
        return Files.exists(file) && file.toFile().length() > 0;
    }
}
