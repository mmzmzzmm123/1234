package com.ruoyi.platform.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.common.constant.ServicePathConstant;
import com.ruoyi.common.webp.exc.WebpEncodeUtil;
import com.ruoyi.config.FileStorageProperties;
import com.ruoyi.bean.FileInfo;
import com.ruoyi.event.FormFileUploadSuccessEvent;
import com.ruoyi.platform.FileStorage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.Tika;
import org.apache.tika.utils.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    private String fileName;
    private List<String> allowExtension;
    private MultipartFile source;

    public LocalFileStorage(FileStorageProperties config) {
        platform = config.getLocal().getPlatform();
        basePath = config.getLocal().getBasePath();
        allowMime = config.getAllowMime();
        waterMark = config.getWaterMark();
        thumbnail = config.getThumbnail();
        allowExtension = config.getAllowExtension();
    }

    @Override
    public FileStorage setName(String name) {
        this.fileName = name;
        return this;
    }

    @Override
    public FileStorage serFile(MultipartFile source, String fileName) {
        this.source = source;
        this.fileName = fileName;
        return this;
    }


    @Override
    public FileInfo upload(MultipartFile file) {
        FileInfo fileInfo = new FileInfo();

        String name = StringUtils.isBlank(fileName) ? file.getName() : this.fileName;
        String extension = getExtension(name, allowExtension);
        String newKey = getFileKey(basePath, fileInfo);
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
        // fileInfo id 保存在 getFileKey方法中
        fileInfo.setExtension(extension);
        fileInfo.setPath(String.join("/", "/service-file" + UPLOAD_PATH, fileInfo.getId()));
        fileInfo.setDiskPath(newKey);
        try (InputStream is = Files.newInputStream(dataFile.toFile().toPath())) {
            String md5 = DigestUtils.md5Hex(is);
            // 设置文件指纹
            fileInfo.setFingerprint(md5);
        } catch (Exception e) {
            log.error("", e);
        }
        return fileInfo;
    }

    @Override
    public FileInfo upload() {
        return this.upload(source);
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
            try {
                processWebp(formFileUploadSuccessEvent.getId(), dataFile);
            } catch (Exception e) {
                log.error("Process WebP Error:{}", formFileUploadSuccessEvent.getId(), e);
            }
            try {
                processWebpOriginal(formFileUploadSuccessEvent.getId(), dataFile);
            } catch (Exception e) {
                log.error("Process WebP Original Error:{}", formFileUploadSuccessEvent.getId(), e);
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

    private void processWebp(String id, Path dataFile) throws IOException {
        log.debug("Start Process Webp:{}", id);
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
        log.debug("Finish Process Webp:{}", id);
    }

    private void processWebpOriginal(String id, Path dataFile) throws IOException {
        log.debug("Start Process Webp Original:{}", id);
        Path webpOriginalFile = getTransFile(dataFile, WEBP_ORIGINAL_FILE);
        WebpEncodeUtil.toWebp(dataFile, webpOriginalFile);
        log.debug("Finish Process Webp Original:{}", id);
    }

    private boolean exists(Path file) {
        return Files.exists(file) && file.toFile().length() > 0;
    }
}
