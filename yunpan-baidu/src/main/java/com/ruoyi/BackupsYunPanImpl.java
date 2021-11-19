package com.ruoyi;

import com.ruoyi.baiduyunpan.utils.FileUtilsBaidu;
import org.springframework.stereotype.Service;

/**
 * @Auther: Wang
 * @Date: 2021/11/20 00:19
 * 功能描述:
 */
@Service
public class BackupsYunPanImpl implements BackupsYunPan{


    @Override
    public String BaiDuYunBackups(String filePath, String fileName, String token) {
        return FileUtilsBaidu.save(filePath, fileName,token);
    }
}
