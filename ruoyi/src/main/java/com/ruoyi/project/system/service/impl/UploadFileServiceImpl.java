package com.ruoyi.project.system.service.impl;

import com.ruoyi.project.system.domain.*;
import com.ruoyi.project.system.mapper.*;
import com.ruoyi.project.system.service.IUploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService {
    private static final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
    @Autowired
    private UploadFileMapper fileMapper;


    @Override
    public int insert(UploadFile uploadFile) {
        return fileMapper.insert(uploadFile);
    }

    @Override
    public List<UploadFile> getByFk(String fk) {
        return fileMapper.getByFk(fk);
    }
}
