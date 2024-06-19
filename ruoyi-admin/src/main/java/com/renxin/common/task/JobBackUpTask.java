package com.renxin.common.task;

import com.qcloud.cos.model.UploadResult;
import com.renxin.common.config.RuoYiConfig;
import com.renxin.common.utils.cos.COSClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component("JobBackUpTask")
public class JobBackUpTask {

    // 0 12,0 * * * /root/pyfile/backup_mysql.sh
    public void backUp()
    {
         String directoryPath = RuoYiConfig.getBackupPath();
        try {
            // 获取目录下的所有文件
            Path directory = Paths.get(directoryPath);
            List<Path> files = Files.list(directory).collect(Collectors.toList());

            // 遍历并打印文件名
            for (Path filePath : files) {
                String fileName = filePath.getFileName().toString();
                if (fileName.contains(".gz")) {
                    File file = filePath.toFile();
                    InputStream inputStream = new FileInputStream(file);
                    //  调用文件服务器方法，实现文件上传改写
                    UploadResult upload = COSClientFactory.upload(inputStream, fileName, "backup");
                    String key = upload.getKey();
                    String url = COSClientFactory.getObjUrl(key, "backup");
                    // 上传成功后删除文件
                    boolean delete = file.delete();
                    log.info("已成功备份远程MySQL数据库::::::" + url);
                    // 已成功备份远程MySQL数据库::::::https://backup-1316267898.cos.ap-beijing.myqcloud.com/psychology-test-202308281446.sql.gz
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
