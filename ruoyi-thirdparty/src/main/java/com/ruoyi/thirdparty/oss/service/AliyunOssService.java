package com.ruoyi.thirdparty.oss.service;

import java.io.InputStream;
import com.ruoyi.thirdparty.oss.domain.FileUploadResponse;
import com.ruoyi.thirdparty.oss.enums.FileCategoryType;

public interface AliyunOssService {

    FileUploadResponse uploadFile(InputStream inputStream, String path, FileCategoryType type);
}
