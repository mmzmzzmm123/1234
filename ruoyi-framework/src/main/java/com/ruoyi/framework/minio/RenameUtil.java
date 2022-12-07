package com.ruoyi.framework.minio;

import cn.hutool.core.util.RandomUtil;
import com.ruoyi.common.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class RenameUtil {

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String getFilePath(String renameFile) {
        if (StringUtils.isBlank(renameFile)) {
            throw new UtilException("文件名为空");
        }
        return LocalDateTime.now().format(dateTimeFormatter) + MinIoUtil.SEPARATOR + renameFile;
    }


    /**
     * 文件重命名
     *
     * @param fileName 文件的完整名字，包括后缀名
     * @return
     */
    public static String generateFileName(String fileName) {
        String ext = FilenameUtils.getExtension(fileName);
        if (StringUtils.isBlank(ext)) {
            throw new UtilException("文件扩展名不存在");
        }
        return StringUtils.join(RandomUtil.randomString(32), FilenameUtils.EXTENSION_SEPARATOR, ext);
    }
}
