package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.UploadFile;

import java.util.List;

public interface UploadFileMapper {
    /**
     * 插入文件记录
     *
     * @param uploadFile
     * @return
     */
    int insert(UploadFile uploadFile);

    /**
     * 获取上传文件
     *
     * @param fk
     * @return
     */
    List<UploadFile> getByFk(String fk);

}
