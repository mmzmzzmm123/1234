package com.ruoyi.web.controller.common;

import com.qcloud.cos.model.UploadResult;
import com.ruoyi.common.utils.cos.COSClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/app/cos")
public class CommonCosController
{
    private static final Logger log = LoggerFactory.getLogger(CommonCosController.class);

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public UploadResult fileDownload(@RequestParam("file") MultipartFile file, @RequestParam("module") String module)
    {
        UploadResult upload = null;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            upload = COSClientFactory.upload(inputStream, file.getOriginalFilename(), module);
        } catch (IOException e) {
            log.error("文件上传失败：{}" ,e);
        }
        return upload;
    }

    @GetMapping("/getUrl/{key}")
    public String getUrl(@PathVariable("key")String key, @RequestParam("module") String module){
        return COSClientFactory.getObjUrl(key, module);
    }

}
