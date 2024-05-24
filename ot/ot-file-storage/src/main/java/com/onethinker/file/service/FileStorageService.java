package com.onethinker.file.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.onethinker.common.enums.DeleteFlagEnum;
import com.onethinker.common.enums.FileRealtionStatusEnum;
import com.onethinker.common.enums.FileRelationTypeEnum;
import com.onethinker.common.utils.Tools;
import com.onethinker.file.domain.FileInfo;
import com.onethinker.file.config.FileStorageProperties;
import com.onethinker.file.domain.FileRelation;
import com.onethinker.file.dto.FileInfoDTO;
import com.onethinker.file.mapper.FileMapper;
import com.onethinker.file.mapper.FileRelationMapper;
import com.onethinker.file.platform.FileStorage;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.bean.BeanUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author yangyouqi
 * @date 2024/5/14
 * 用来处理文件存储，对接多个平台
 */
@Slf4j
@Getter
@Setter
public class FileStorageService {

    private FileStorageProperties properties;

    private CopyOnWriteArrayList<FileStorage> fileStorageList;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileRelationMapper relationMapper;

    /**
     * 获取默认的存储平台
     */
    public <T extends FileStorage> T getFileStorage() {
        return this.getFileStorage(properties.getDefaultPlatform());
    }

    /**
     * 获取对应的存储平台
     */
    public <T extends FileStorage> T getFileStorage(String platform) {
        for (FileStorage fileStorage : fileStorageList) {
            if (fileStorage.getPlatform().equals(platform)) {
                return Tools.cast(fileStorage);
            }
        }
        return null;
    }

    /**
     * 获取对应的存储平台，如果存储平台不存在则抛出异常
     */
    public <T extends FileStorage> T getFileStorageVerify(String platform) {
        T fileStorage = this.getFileStorage(platform);
        if (fileStorage == null)
            throw new RuntimeException(StrUtil.format("没有找到对应的存储平台！platform:{}", platform));
        return fileStorage;
    }

    /**
     * 保存实体
     *
     * @param fileRelation
     * @param fileInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveFileInfo(FileRelation fileRelation, FileInfo fileInfo) {
        fileRelation.setCreateTime(DateUtils.getNowDate());
        fileRelation.setStatus(FileRealtionStatusEnum.INIT.getCode());
        fileInfo.setCreateTime(DateUtils.getNowDate());
        fileMapper.insert(fileInfo);
        relationMapper.insert(fileRelation);
    }

    /**
     * 获取 文件集合信息
     * @param fileInfo fileInfo
     * @return
     */
    public List<FileInfoDTO> selectFileList(FileInfo fileInfo) {
        List<FileInfo> fileInfos = fileMapper.selectFileList(fileInfo);

        return fileInfos.parallelStream().map(e -> {
            FileInfoDTO fileInfoFileInfoDTO = new FileInfoDTO();
            BeanUtils.copyProperties(e,fileInfoFileInfoDTO);
            // 判断来源，取对于的访问地址
            fileInfoFileInfoDTO.setDomain(this.getFileStorage(e.getPlatform()).getDomain());
            return fileInfoFileInfoDTO;
        }).collect(Collectors.toList());

    }

    /**
     * 根据文件类型获取有效文件数据
     */
    public List<FileInfoDTO> selectFileList(FileRelationTypeEnum typeEnum,FileRealtionStatusEnum statusEnum) {
        LambdaQueryWrapper<FileRelation> relationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        relationLambdaQueryWrapper.eq(FileRelation::getRelationTarget,typeEnum.name()).eq(FileRelation::getStatus, statusEnum.getCode());
        List<FileRelation> fileRelations = relationMapper.selectList(relationLambdaQueryWrapper);
        if (fileRelations.isEmpty()) {
            return Lists.newArrayList();
        }
        List<String> fileIds = fileRelations.parallelStream().map(FileRelation::getFileId).collect(Collectors.toList());
        LambdaQueryWrapper<FileInfo> fileInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fileInfoLambdaQueryWrapper.in(FileInfo::getId, fileIds).eq(FileInfo::getDelFlag, DeleteFlagEnum.enabled.getCode());
        List<FileInfo> fileInfos = fileMapper.selectList(fileInfoLambdaQueryWrapper);
        return fileInfos.parallelStream().map(e -> {
            FileInfoDTO fileInfoFileInfoDTO = new FileInfoDTO();
            BeanUtils.copyProperties(e,fileInfoFileInfoDTO);
            // 判断来源，取对于的访问地址
            fileInfoFileInfoDTO.setDomain(this.getFileStorage(e.getPlatform()).getDomain());
            return fileInfoFileInfoDTO;
        }).collect(Collectors.toList());
    }

    /**
     * 更新文件状态
     * @param id
     * @param orgStatus
     * @param updateStatus
     */
    public void updateFileRegionStatusByFileId(String id, FileRealtionStatusEnum orgStatus, FileRealtionStatusEnum updateStatus) {
        relationMapper.updateFileRegionStatusByFileId(id,orgStatus.getCode(),updateStatus.getCode());
    }
}
