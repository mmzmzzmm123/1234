package com.ruoyi.project.system.service;

import com.ruoyi.project.system.domain.UploadFile;

import java.util.List;

/**
 * 通用文件保存 业务层
 * 
 * @author purple
 */
public interface IUploadFileService
{
    /**
     * 记录上传文件
     * @param uploadFile
     */
    int insert(UploadFile uploadFile);

    /**
     * 获取文件上传
     * @param fk
     * @return
     */
    List<UploadFile> getByFk(String fk);
}
