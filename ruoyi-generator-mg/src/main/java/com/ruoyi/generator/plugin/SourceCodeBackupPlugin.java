package com.ruoyi.generator.plugin;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.mybatis.generator.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 源码备份
 */
public class SourceCodeBackupPlugin extends AbstractClassPlugin {

    private Logger log = LoggerFactory.getLogger(SourceCodeBackupPlugin.class);

    @Override
    public void setContext(Context context) {
        super.setContext(context);

        String sourcePath = context.getProperty(Backup.CODE_SOURCE_PATH);
        String targetPath = context.getProperty(Backup.CODE_TARGET_PATH);

        Validate.isTrue(StringUtils.isNotEmpty(sourcePath), "源码备份配置-来源为null");
        Validate.isTrue(StringUtils.isNotEmpty(targetPath), "源码备份配置-目标为null");

        String date = DateUtil.today();

        backup(sourcePath, targetPath + '/' + date);
    }

    private void backup(String sourcePath, String targetPath) {
        // 先备份目录
        File targetFile = new File(targetPath);
        File sourceFile = new File(sourcePath);
        try {
            log.warn("开始备份原始文件, 目录={}", targetPath);
            copy(sourceFile, targetFile);
        } catch (IOException e) {
            log.error("备份源码文件异常", e);
        }
    }

    private void copy(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdirs();
        }
        FileUtils.copyFile(source, target, true);
    }

    private void del(String path) throws IOException {
        del(new File(path));
    }

    private void del(File path) throws IOException {
        if (path.isFile()) {
            path.delete();
            log.info("执行命令=del {}", path.getCanonicalPath());
        } else {
            for (File file : path.listFiles()) {
                del(file);
            }
        }
    }

}
