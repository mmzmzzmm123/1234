package com.ruoyi.onethinker.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * @author : yangyouqi
 * @date : 2023/10/25 0025 10:32
 */
@Data
public class SysFileInfoReqDTO {
    /**
     * 当前上传唯一标识
     */
    private String uid;
    /**
     * 文件名
     */
    private String name;
    /**
     * MD5
     */
    private String md5;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件归属模块
     *
     */
    private String module;

    /**
     * 文件类型
     * @return
     */
    private Integer fileType;

    /**
     * 文件内容
     */
    private MultipartFile file;
}
